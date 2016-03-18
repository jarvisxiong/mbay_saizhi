package org.sz.mbay.channel.action.util;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeUtil {
	
	public static final int WIDTH = 300;
	public static final int HEIGHT = 300;
	// public static final int LOGO_SIZE = 60;
	public static final String QRCODE_FILETYPE = "jpg";
	public static final int BUFFER_SIZE = 1024 * 6;
	
	static final Logger logger = LoggerFactory.getLogger(QRCodeUtil.class);
	
	/**
	 * 得到二维码图片，并保存到给出的路径
	 * 
	 * @param content
	 *            编码内容
	 * @param qrCodePath
	 *            二维码保存路径
	 * @throws IOException
	 */
	public static void generateQRCode(String content, String qrCodePath, Integer width, Integer height)
			throws IOException {
		if (width == null) {
			width = WIDTH;
		}
		if (height == null) {
			height = HEIGHT;
		}
		// 得到二维码图片的bufferImage
		BufferedImage bim = getQR_CODEBufferedImage(content,
				BarcodeFormat.QR_CODE, width, height, getDecodeHintType());
		if (bim == null) {
			return;
		}
		// 构造文件路径
		File file = new File(qrCodePath);
		if (file.exists()) {
			logger.warn("二维码位置文件存在，将被覆盖!" + "--文件位置为：" + qrCodePath);
		}
		ImageIO.write(bim, "jpeg", file);
	}
	
	/**
	 * 得到带有logo图片的二维码图片，并写到给出的路径
	 * 
	 * @param content
	 *            编码内容
	 * @param qrCodePath
	 *            二维码保存路径
	 * @param logoPath
	 *            logo 路径
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void generateQRCodeWithLogo(String content,
			String qrCodePath, String logoPath, Integer width, Integer height) throws IOException,
			InterruptedException {
		
		if (width == null) {
			width = WIDTH;
		}
		if (height == null) {
			height = HEIGHT;
		}
		
		// 得到二维码图片的bufferImage
		BufferedImage bim = getQR_CODEBufferedImage(content,
				BarcodeFormat.QR_CODE, width, height, getDecodeHintType());
		if (bim == null) {
			return;
		}
		File logo = new File(logoPath);
		bim = addLogo_QRCode(bim, logo, new LogoConfig());
		File qrCode = null;
		qrCode = new File(qrCodePath);
		if (qrCode.exists()) {
			logger.warn("二维码位置文件存在，将被覆盖!" + "--文件位置为：" + qrCodePath);
		}
		ImageIO.write(bim, "jpeg", qrCode);
	}
	
	/**
	 * 得到带有logo的二维码图片字节数组
	 * 
	 * @param content
	 *            编码内容
	 * @param logoPath
	 *            logo的路径
	 * @return
	 */
	public static byte[] getQRCodeBytes(String content, String logoPath, Integer width, Integer height) {
		if (width == null) {
			width = WIDTH;
		}
		if (height == null) {
			height = HEIGHT;
		}
		// 得到二维码图片的bufferImage
		BufferedImage bim = getQR_CODEBufferedImage(content,
				BarcodeFormat.QR_CODE, width, height, getDecodeHintType());
		if (bim == null) {
			return null;
		}
		File logo = new File(logoPath);
		bim = addLogo_QRCode(bim, logo, new LogoConfig());
		ByteArrayOutputStream bos = new ByteArrayOutputStream(BUFFER_SIZE);
		try {
			ImageIO.write(bim, QRCODE_FILETYPE, bos);
		} catch (IOException e) {
			logger.error("getQRCodeBytes", e.fillInStackTrace());
		}
		return bos.toByteArray();
	}
	
	public static byte[] getQRCodeBytes(String content, Integer width, Integer height) {
		if (width == null) {
			width = WIDTH;
		}
		if (height == null) {
			height = HEIGHT;
		}
		// 得到二维码图片的bufferImage
		BufferedImage bim = getQR_CODEBufferedImage(content,
				BarcodeFormat.QR_CODE, width, height, getDecodeHintType());
		if (bim == null) {
			return null;
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream(BUFFER_SIZE);
		try {
			ImageIO.write(bim, QRCODE_FILETYPE, bos);
		} catch (IOException e) {
			logger.error("getQRCodeBytes", e.fillInStackTrace());
		}
		return bos.toByteArray();
	}
	
	/**
	 * 生成二维码bufferedImage图片
	 * 
	 * @param content
	 *            编码内容
	 * @param barcodeFormat
	 *            编码类型
	 * @param width
	 *            图片宽度
	 * @param height
	 *            图片高度
	 * @param hints
	 *            设置参数
	 * @return
	 */
	private static BufferedImage getQR_CODEBufferedImage(String content,
			BarcodeFormat barcodeFormat, int width, int height,
			Map<EncodeHintType, ?> hints) {
		MultiFormatWriter multiFormatWriter = null;
		BitMatrix bm = null;
		BufferedImage image = null;
		try {
			multiFormatWriter = new MultiFormatWriter();
			// 参数分别为：编码內容，编码类型，生成图片宽度，生成图片高度，设置参数
			bm = multiFormatWriter.encode(content, barcodeFormat, width,
					height, hints);
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			
			// 开始利用二维码数据创建爱你Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFCCDDEE);
				}
			}
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	/**
	 * 设置二维码的格式参数
	 * 
	 * @return
	 */
	private static Map<EncodeHintType, Object> getDecodeHintType() {
		// 用于设置QR二维码参数
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		// 设置QR二维码的纠错级别（H为最该级别），具体级别信息
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 設置編碼方式
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MAX_SIZE, 350);
		hints.put(EncodeHintType.MIN_SIZE, 100);
		hints.put(EncodeHintType.MARGIN, 1);
		return hints;
	}
	
	/**
	 * 给二维码图片添加Logo
	 * 
	 * @param qrPic
	 *            二维码图片
	 * @param logoPic
	 *            logo图片
	 */
	private static BufferedImage addLogo_QRCode(BufferedImage qrCodeImg,
			File logoPic, LogoConfig logoConfig) {
		try {
			if (qrCodeImg == null) {
				logger.error("addLogo_QRCode", "二维码BufferedImage不存在");
				return null;
			}
			if (!logoPic.isFile()) {
				logger.error("addLogo_QRCode", "logo图片文件不存在");
				return qrCodeImg;
			}
			Graphics2D g = qrCodeImg.createGraphics();
			/**
			 * 读取logo圖片
			 */
			BufferedImage logo = ImageIO.read(logoPic);
			// 同比例重置图片大小（依据logo设置的大小）
			// logo = resetImageSize(logo, WIDTH/ LogoConfig.DEFAULT_LOGOPART);
			
			int widthLogo = logo.getWidth(), heightLogo = logo.getHeight();
			
			// 计算图片放置位置
			int x = (qrCodeImg.getWidth() - widthLogo) / 2;
			int y = (qrCodeImg.getHeight() - logo.getHeight()) / 2;
			
			// 开始绘制图片
			g.drawImage(logo, x, y, widthLogo, heightLogo, null);
			g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
			g.setStroke(new BasicStroke(logoConfig.getBorder()));
			g.setColor(logoConfig.getBorderColor());
			g.drawRect(x, y, widthLogo, heightLogo);
			g.dispose();
			return qrCodeImg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qrCodeImg;
	}
	
	/**
	 * 二维码的解析
	 * 
	 * @param file
	 */
	public static void parseQR_CODEImage(File file) {
		try {
			MultiFormatReader formatReader = new MultiFormatReader();
			
			// File file = new File(filePath);
			if (!file.exists()) {
				return;
			}
			
			BufferedImage image = ImageIO.read(file);
			
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			
			Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			
			Result result = formatReader.decode(binaryBitmap, hints);
			
			System.out.println("result = " + result.toString());
			System.out.println("resultFormat = " + result.getBarcodeFormat());
			System.out.println("resultText = " + result.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 重置图片大小（因为logo图片不能太大，太大的话容易挡住二维码重要信息，以至于无法识别）
	 * 
	 * @param imagesrc
	 *            原图片
	 * @param size
	 *            改变后的大小
	 * @return
	 */
	@SuppressWarnings("unused")
	private static BufferedImage resetImageSize(BufferedImage imagesrc, int size) {
		double width = imagesrc.getWidth();
		double height = imagesrc.getHeight();
		if (width <= size && height <= size) {
			return imagesrc;
		}
		int newWidth = size;
		int newHeight = size;
		BufferedImage image = new BufferedImage(newWidth, newHeight,
				BufferedImage.TYPE_INT_BGR);
		
		Graphics g = null;
		g = image.createGraphics();
		g.drawImage(imagesrc, 0, 0, newWidth, newHeight, null);
		g.dispose();
		return image;
	}
}
