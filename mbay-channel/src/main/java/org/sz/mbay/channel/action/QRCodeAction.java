package org.sz.mbay.channel.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.sz.mbay.base.config.ResourceConfig;
import org.sz.mbay.channel.action.util.LogoConfig;
import org.sz.mbay.channel.action.util.QRCodeUtil;

/**
 * 二维码处理类，实则应该为图片处理类，不过貌似已经给出了，先这样吧
 * 
 * @author Fenlon
 * 
 */
@Controller
@RequestMapping("/qrcode/")
public class QRCodeAction {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 获得门店二维码
	 * 
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/{storeId}/get", method = RequestMethod.GET)
	public void getStoreQRcode(@PathVariable("storeId") long id,
			@RequestParam(required = false) Integer size,
			HttpServletRequest request, HttpServletResponse response) {
		String baseURL = ResourceConfig.getWebDomain()
				+ ResourceConfig.getContextPath();
		// String url = "http://www.mbpartner.cn";
		String url = baseURL + "/store/" + id + "/listCampaign.mbay";
		// byte[] data = QRCodeUtil.getQRCodeBytes(url, null, null);
		String logoname = "";
		Integer width = null;
		if (size == null) {
			logoname = "meibei-60.jpg";
		} else {
			logoname = "meibei-" + size / LogoConfig.DEFAULT_LOGOPART + ".jpg";
			width = size;
		}
		String path = (this.getClass().getClassLoader()
				.getResource("../../images/logo/" + logoname)) + "";
		String logopath = path.substring(path.indexOf("/") + 1);
		byte[] data = QRCodeUtil.getQRCodeBytes(url, logopath, width, width);
		Map<String, String> map = new HashMap<String, String>();
		map.put("Content-Disposition", "filename=" + id + "."
				+ QRCodeUtil.QRCODE_FILETYPE);
		writePic(data, response, map);
	}
	
	/**
	 * 生成兑换码二维码
	 * 
	 * @param content
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public void create(String content, HttpServletResponse response) {
		content = ResourceConfig.getWebDomain()
				+ ResourceConfig.getContextPath() + content;
		byte[] data = QRCodeUtil.getQRCodeBytes(content, null, null);
		Map<String, String> map = new HashMap<String, String>();
		map.put("Content-Disposition", "filename=" + content + "."
				+ QRCodeUtil.QRCODE_FILETYPE);
		writePic(data, response, map);
	}
	
	/**
	 * 生成二维码
	 * 
	 * @param content
	 * @param response
	 */
	@RequestMapping(value = "generate", method = RequestMethod.GET)
	public void genearte(String content, HttpServletResponse response) {
		byte[] data = QRCodeUtil.getQRCodeBytes(content, null, null);
		Map<String, String> map = new HashMap<String, String>();
		map.put("Content-Disposition", "filename=" + content + "."
				+ QRCodeUtil.QRCODE_FILETYPE);
		writePic(data, response, map);
	}
	
	/**
	 * 生成兑换码二维码
	 * 
	 * @param content
	 */
	@RequestMapping(value = "get", method = RequestMethod.GET)
	public void generate(String code, HttpServletResponse response) {
		String content = null;
		if (code == null) {
			return;
		}
		String redeem_code_url = ResourceConfig.getWebDomain()
				+ ResourceConfig.getContextPath()
				+ "/store_ope/redeemUI.mbay?code=";
		content = redeem_code_url + code;
		System.out.println("兑换码URL---->>" + content);
		// byte[] data = QRCodeUtil.getQRCodeBytes(content,
		// "/images/logo/logo-small.png", null, null);
		byte[] data = QRCodeUtil.getQRCodeBytes(content, null, null);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("Content-Disposition", "filename=" + content + "."
				+ QRCodeUtil.QRCODE_FILETYPE);
		writePic(data, response, map);
	}
	
	/**
	 * 下载门店二维码
	 * 
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/{storeId}/download", method = RequestMethod.GET)
	public void download(@PathVariable("storeId") long id,
			HttpServletRequest request, HttpServletResponse response) {
		String basURL = ResourceConfig.getWebDomain()
				+ ResourceConfig.getContextPath();
		// String url = "http://www.mbpartner.cn";
		String url = basURL + "/store/" + id + "/listCampaign.mbay";
		// System.out.println(url);
		byte[] data = QRCodeUtil.getQRCodeBytes(url, null, null);
		QRCodeUtil.getQRCodeBytes(url, "/images/logo/logo-small.png", null,
				null);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("Content-Disposition", "attachment;filename=" + id + "."
				+ QRCodeUtil.QRCODE_FILETYPE);
		writePic(data, response, map);
	}
	
	/**
	 * 生成二维码（通用）
	 * @param url
	 * @param response
	 */
	@RequestMapping("generateQrCode")
	public void generateQrCode(@RequestParam("url") String url, HttpServletResponse response) {
		if (StringUtils.isEmpty(url)) {
			return;
		}
		byte[] data = QRCodeUtil.getQRCodeBytes(url, 400, 400);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("Content-Disposition", "filename=" + url + "." + QRCodeUtil.QRCODE_FILETYPE);
		writePic(data, response, map);
	}
	
	/**
	 * 文件流写出
	 * 
	 * @param data
	 *            图片字节
	 * @param response
	 * @param filename
	 *            图片文件名
	 */
	private void writePic(byte[] data, HttpServletResponse response,
			Map<String, String> headers) {
		try {
			response.setContentType("image/" + QRCodeUtil.QRCODE_FILETYPE);
			response.setCharacterEncoding("UTF-8");
			
			Set<Entry<String, String>> entries = headers.entrySet();
			for (Entry<String, String> entry : entries) {
				response.setHeader(entry.getKey(), entry.getValue());
			}
			OutputStream outputSream = response.getOutputStream();
			InputStream in = new ByteArrayInputStream(data);
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = in.read(buf, 0, 1024)) != -1) {
				outputSream.write(buf, 0, len);
			}
			outputSream.flush();
			outputSream.close();
		} catch (IOException ex) {
			this.logger.info("getFile error", ex.fillInStackTrace());
		}
	}
}
