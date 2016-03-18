package org.sz.mbay.channel.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.sz.mbay.base.config.ResourceConfig;

@Controller
@RequestMapping("filedownload")
public class FileDownlodAction {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FileDownlodAction.class);

	@RequestMapping(value = "/files/{file_name}", method = RequestMethod.GET)
	public void getFile(@PathVariable("file_name") String fileName,
			HttpServletRequest request, HttpServletResponse response) {
		String filepath = request.getSession().getServletContext()
				.getRealPath("/");
		String path = ResourceConfig.getProperty(fileName);
		filepath = filepath + path;
		File file = new File(filepath);
		try (InputStream inputStream = new FileInputStream(file);
				OutputStream outputStream = response.getOutputStream()) {
			response.setContentType("application/force-download");
			String filename = file.getName();
			response.addHeader("Content-Disposition", "attachment; filename="
					+ URLEncoder.encode(filename, "UTF-8"));
			response.setHeader("Set-Cookie", "fileDownload=true; path=/");
			response.setContentLength((int) file.length());
			IOUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			LOGGER.error("psd文件下载失败!", e.fillInStackTrace());
		}

	}

}
