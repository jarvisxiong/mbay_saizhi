package org.sz.mbay.channel.service.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.google.zxing.common.BitMatrix;

public class ZXingPic {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		QRCodeUtil.generateQRCode("http://www.baidu.com", "d:/test/qrcode.jpg");
		System.out.println("finish 1");
		QRCodeUtil.generateQRCodeWithLogo("http://www.baidu.com",
				"d:/test/qrcodelogo.jpg", "d:/test/logo3.png");
		System.out.println("finish 2");
		byte[] data = QRCodeUtil.getQRCodeBytes("http://www.baidu.com");
		System.out.println(data.toString());
		System.out.println("finish 3");
		byte[] data3 = QRCodeUtil.getQRCodeBytes("http://www.baidu.com",
				"d:/test/logo3.png");
		System.out.println(data3.toString());
		System.out.println("finish 4");
	}

	/**
	 * 将二维码生成为文件
	 * 
	 * @param bm
	 * @param imageFormat
	 * @param file
	 */
	public void decodeQR_CODE2ImageFile(BitMatrix bm, String imageFormat,
			File file) {
		try {
			if (null == file || file.getName().trim().isEmpty()) {
				throw new IllegalArgumentException("文件异常，或者宽展名有问题！");
			}
			BufferedImage bi = fileToBufferedImage(bm);
			ImageIO.write(bi, "jpeg", file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将二维码生成为输出流
	 * 
	 * @param content
	 * @param imageFormat
	 * @param os
	 */
	public void decodeQR_CODE2OutputStream(BitMatrix bm, String imageFormat,
			OutputStream os) {
		try {
			BufferedImage image = fileToBufferedImage(bm);
			ImageIO.write(image, imageFormat, os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构建初始化二维码
	 * 
	 * @param bm
	 * @return
	 */
	public BufferedImage fileToBufferedImage(BitMatrix bm) {
		BufferedImage image = null;
		try {
			int w = bm.getWidth(), h = bm.getHeight();
			image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFCCDDEE);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}
}
