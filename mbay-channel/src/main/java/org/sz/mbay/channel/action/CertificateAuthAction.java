package org.sz.mbay.channel.action;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.sz.mbay.base.annotation.CacheControl;
import org.sz.mbay.base.annotation.CachePolicy;
import org.sz.mbay.base.annotation.Token;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.base.web.servlet.tags.token.TokenProcessor;
import org.sz.mbay.base.web.utils.RequestUtil;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.captcha.CaptchaResult;
import org.sz.mbay.captcha.SMSAuthCodeUtil;
import org.sz.mbay.channel.action.util.FileUtil;
import org.sz.mbay.channel.bean.BusinessCategory;
import org.sz.mbay.channel.bean.PhotoEntity;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.costant.error.CertificateAuthError;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.channel.service.BusinessCategoryService;
import org.sz.mbay.channel.user.bean.Certificate;
import org.sz.mbay.channel.user.bean.ChannelInfo;
import org.sz.mbay.channel.user.enums.CertStatus;
import org.sz.mbay.channel.user.enums.ChannelProperty;
import org.sz.mbay.channel.user.service.CertificateAuthService;
import org.sz.mbay.channel.user.service.UserService;
import org.sz.mbay.fs.FSClient;
import org.sz.mbay.fs.FSClientFactory;
import org.sz.mbay.fs.FSClientFactory.ClientType;
import org.sz.mbay.fs.fastdfs.FDFSFileInfo;
import org.sz.mbay.sms.client.MbaySMS;
import org.sz.mbay.sms.template.enums.CaptchaSMSType;

/**
 * 企业/个体 实名认证信息提交
 * 
 * @author ONECITY
 * 		
 */
@Controller
@RequestMapping("certificate")
public class CertificateAuthAction extends BaseAction {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CertificateAuthAction.class);
			
	// /实名认证保存cookie用户名
	public static final String COOKIE_CERT_USERNUMBER = "cert_user_number";
	
	public static final String PERSONAL_AUTH_APPLY = "account/identify/personal/auth_apply";
	
	public static final String ENTERPRISE_AUTH_APPLY = "account/identify/enterprise/auth_apply";
	
	public static final String REDIRECT_AUTH_APPLY_SUCCESS = "redirect:/certificate/auth/apply_success.mbay";
	public static final String AUTH_APPLY_SUCCESS = "account/identify/personal/apply_success";
	
	public static final String REDIRECT_ERP_APPLY_SUCCESS = "redirect:/certificate/auth/apply_erp_success.mbay";
	public static final String ERP_APPLY_SUCCESS = "account/identify/enterprise/apply_success";
	
	// 实名认证结果页
	public static final String CERT_AUTH_RESULT = "account/identify/cert_auth_result";
	
	public static final String REDIRECT_TO_LOGIN = "redirect:/channel/index.mbay";
	// 重定向到实名认证
	public static final String REDIRECT_TO_ID_AUTH_APPLY = "redirect:/certificate/auth/id_auth_apply.mbay";
	
	// 上传头像
	public static final String TO_PORTRAIT = "account/identify/portrait";
	// 重定向头像页面
	public static final String REDIRECT_TO_PORTRAIT = "redirect:/certificate/toPortrait.mbay";
	
	// fastdfs文件管理
	private FSClient fsClient = FSClientFactory.getClient(ClientType.FDFS);
	
	@Autowired
	CertificateAuthService certificateService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	BusinessCategoryService businessCategoryService;
	
	/**
	 * goto申请账户实名认证
	 * 
	 * @return
	 */
	@RequestMapping("auth/id_auth_apply")
	@Token(save = true)
	@CacheControl(policy = CachePolicy.NO_CACHE)
	public String auth(HttpServletRequest request, Model model) {
		ChannelProperty channelProperty = null;
		String userNumber = "";
		if (ChannelContext.getSessionChannel() != null) {
			channelProperty = ChannelContext.getSessionChannel().getProperty();
			userNumber = ChannelContext.getSessionChannel().getUserNumber();
		} else {
			Cookie cookie = RequestUtil.getCookie(request,
					COOKIE_CERT_USERNUMBER);
			if (cookie == null) {// cookie 过期
				return REDIRECT_TO_LOGIN;
			}
			userNumber = cookie.getValue();
			channelProperty = this.userService.findUserProperty(userNumber);
			if (channelProperty == null) {
				return REDIRECT_TO_LOGIN;
			}
		}
		CertStatus certstatus = this.certificateService
				.getUserCertStatus(userNumber);
		if (!CertStatus.UNAPPLY.equals(certstatus)
				&& !CertStatus.UNPASSED.equals(certstatus)) {
			String authInfo = "";
			if (CertStatus.APPROVED.equals(certstatus)) {
				authInfo = "您已通过实名认证！";
			} else if (CertStatus.UNDERREVIEW.equals(certstatus)) {
				authInfo = "您的实名认证资料正在审核中！";
			}
			model.addAttribute("state", certstatus);
			model.addAttribute("authInfo", authInfo);
			return CERT_AUTH_RESULT;
		}
		if (ChannelProperty.PERSONAL.equals(channelProperty)) {
			return PERSONAL_AUTH_APPLY;
		} else {
			// 企业实名认证申请，查询所需的企业所属行业类型
			List<BusinessCategory> businessCategoryList = businessCategoryService
					.findAllBusinessCategory();
			model.addAttribute("businessCategoryList", businessCategoryList);
			return ENTERPRISE_AUTH_APPLY;
		}
	}
	
	/**
	 * 认证不通过重新申请实名认证
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("auth/reapply")
	@Token(save = true)
	public String authReapply(HttpServletRequest request, Model model) {
		ChannelProperty property = ChannelContext.getSessionChannel()
				.getProperty();
		if (ChannelProperty.PERSONAL.equals(property)) {
			Certificate certificate = certificateService
					.findCertificate(
							ChannelContext.getSessionChannel().getId());
			model.addAttribute("certificate", certificate);
		} else {
			ChannelInfo channelInfo = certificateService
					.findChannelInfo(ChannelContext.getSessionChannel()
							.getUserNumber());
			model.addAttribute("enterprise", channelInfo);
		}
		return this.auth(request, model);
	}
	
	/**
	 * 个人实名认证提交
	 * 
	 * @param certificate
	 * @return
	 */
	@RequestMapping("auth/auth_apply_sub")
	public String authsub(Certificate certificate, MultipartFile frontfile,
			MultipartFile backfile, Model model, HttpServletRequest request) {
		if (ChannelContext.getSessionChannel() == null) {
			Cookie cookie = RequestUtil.getCookie(request,
					COOKIE_CERT_USERNUMBER);
			if (cookie == null) {
				return REDIRECT_TO_LOGIN;
			}
			int userid = userService.getIDByUserNumber(cookie.getValue());
			if (userid == 0) {
				return REDIRECT_TO_LOGIN;
			}
			certificate.setUserid(userid);
		} else {
			certificate.setUserid(ChannelContext.getSessionChannel().getId());
		}
		String imagetype = "jpg,jpeg,bmp,gif,png";
		String frontfilename = frontfile.getOriginalFilename();
		String backfilename = backfile.getOriginalFilename();
		String extensionFrontFileName = frontfilename.substring(
				frontfilename.indexOf(".") + 1, frontfilename.length())
				.toLowerCase();
		String extensionBackFileName = backfilename.substring(
				backfilename.indexOf(".") + 1, backfilename.length())
				.toLowerCase();
		if (imagetype.indexOf(extensionFrontFileName) == -1) {
			model.addAttribute("error_msg", "证件正面图片格式不正确");
			model.addAttribute("certificate", certificate);
			return PERSONAL_AUTH_APPLY;
		}
		if (imagetype.indexOf(extensionBackFileName) == -1) {
			model.addAttribute("error_msg", "证件反面图片格式不正确");
			model.addAttribute("certificate", certificate);
			return PERSONAL_AUTH_APPLY;
		}
		/***
		 * String shorturl = certificate.getUserid() + "/" +
		 * System.currentTimeMillis() + "/"; String fronturl =
		 * MultiFileUpload.uplodPersonalCert(frontfile, shorturl + "1." +
		 * extensionFrontFileName, extensionFrontFileName);
		 ****/
		PhotoEntity certfront = new PhotoEntity();
		certfront.setFileName("P_CERT_FRONT." + extensionFrontFileName);
		try {
			certfront.setPhotoData(frontfile.getBytes());
		} catch (Exception e) {
			throw new ServiceException(CertificateAuthError.UPLOAD_IMG_ERROR);
		}
		/*
		 * if (fid == 0) {
		 * model.addAttribute("error_msg", "证件上传失败");
		 * model.addAttribute("certificate", certificate);
		 * return auth(request, model);
		 * }
		 */
		PhotoEntity certback = new PhotoEntity();
		certback.setFileName("P_CERT_BACK." + extensionFrontFileName);
		long bid = 0;
		try {
			certback.setPhotoData(backfile.getBytes());
			// bid = this.photoService.createPhoto(certback);
		} catch (Exception e) {
			throw new ServiceException(CertificateAuthError.UPLOAD_IMG_ERROR);
		}
		if (bid == 0) {
			model.addAttribute("error_msg", "证件上传失败");
			model.addAttribute("certificate", certificate);
			return auth(request, model);
		}
		/***
		 * String backurl = MultiFileUpload.uplodPersonalCert(backfile, shorturl
		 * + "2." + extensionBackFileName, extensionBackFileName); if (backurl
		 * == null) { model.addAttribute("error_msg", "证件上传失败");
		 * model.addAttribute("certificate", certificate); return auth(); }
		 ****/
		// certificate.setFrontimgurl(fid + "");
		certificate.setBackimgurl(bid + "");
		certificate.setCreatetime(new Timestamp(System.currentTimeMillis()));
		certificateService.addCertiticate(certificate);
		
		return REDIRECT_AUTH_APPLY_SUCCESS;
	}
	
	@RequestMapping("auth/apply_success")
	public String authPersonSuccess() {
		return AUTH_APPLY_SUCCESS;
	}
	
	/**
	 * 企业信息认证提交
	 * 
	 * @param certificate
	 * @return
	 */
	@RequestMapping("auth/auth_erp_apply_sub")
	@Token(reset = true)
	public String auth_enterprise_sub(ChannelInfo channelinfo,
			MultipartFile licensefile, MultipartFile taxfile,
			MultipartFile codefile, Model model, HttpServletRequest request) {
		if (ChannelContext.getSessionChannel() == null) {
			Cookie cookie = RequestUtil.getCookie(request,
					COOKIE_CERT_USERNUMBER);
			if (cookie == null) {
				return REDIRECT_TO_LOGIN;
			}
			int userid = userService.getIDByUserNumber(cookie.getValue());
			if (userid == 0) {
				return REDIRECT_TO_LOGIN;
			}
			channelinfo.setUserid(userid);
		} else {
			channelinfo.setUserid(ChannelContext.getSessionChannel().getId());
		}
		String imagetype = "jpg,jpeg,bmp,png,gif,tiff,tif,svg";
		// 营业执照 税务登记 组织机构代码证副本
		String licenseExtension = FileUtil.getFileExtension(
				licensefile.getOriginalFilename()).toLowerCase();
		String taxExtension = FileUtil.getFileExtension(
				taxfile.getOriginalFilename()).toLowerCase();
		String codeExtension = FileUtil.getFileExtension(
				codefile.getOriginalFilename()).toLowerCase();
		if (imagetype.indexOf(licenseExtension) == -1) {
			model.addAttribute("error_msg", "营业执照 格式不正确");
			model.addAttribute("certificate", channelinfo);
			return ENTERPRISE_AUTH_APPLY;
		}
		if (imagetype.indexOf(taxExtension) == -1) {
			model.addAttribute("error_msg", "税务登记证格式不正确");
			model.addAttribute("certificate", channelinfo);
			return ENTERPRISE_AUTH_APPLY;
		}
		if (imagetype.indexOf(codeExtension) == -1) {
			model.addAttribute("error_msg", "组织机构代码证件格式不正确");
			model.addAttribute("certificate", channelinfo);
			return ENTERPRISE_AUTH_APPLY;
		}
		try {
			channelinfo.setCopyLicense(fsClient.uploadFile(licensefile));
			channelinfo.setCopyTax(fsClient.uploadFile(taxfile));
			channelinfo.setCopyCode(fsClient.uploadFile(codefile));
		} catch (Exception e) {
			throw new ServiceException(CertificateAuthError.UPLOAD_IMG_ERROR);
		}
		channelinfo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		ExecuteResult result = certificateService.addChannelinfo(channelinfo);
		if (!result.isSuccess()) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, result.getMessage());
			TokenProcessor.getInstance().saveToken(request);// 重置token
			return this.auth(request, model);
		}
		return REDIRECT_ERP_APPLY_SUCCESS;
	}
	
	@RequestMapping("auth/apply_erp_success")
	public String authEnterpriseSuccess() {
		return ERP_APPLY_SUCCESS;
	}
	
	/**
	 * 实名认证状态
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("incomplete-switch")
	@RequestMapping("auth_result")
	public String auth_result(HttpServletRequest request, Model model) {
		long userId = ChannelContext.getSessionChannel().getId();
		String authInfo = "";
		CertStatus state = this.certificateService
				.getUserCertStatus(ChannelContext.getSessionChannel()
						.getUserNumber());
		if (state.equals(CertStatus.UNAPPLY)) {
			// 重定向到实名认证
			return REDIRECT_TO_ID_AUTH_APPLY;
		} else if (state.equals(CertStatus.APPROVED)) {
			authInfo = "您已通过实名认证！";
		} else if (state.equals(CertStatus.UNDERREVIEW)) {
			authInfo = "您的实名认证资料正在审核中！";
		} else if (state.equals(CertStatus.UNPASSED)) {
			switch (ChannelContext.getSessionChannel().getProperty()) {
				case PERSONAL:
					authInfo = certificateService.findCertificate(userId)
							.getAuthinfo();
					break;
				case ENTERPRISE:
					authInfo = certificateService.findChannelInfo(
							ChannelContext.getSessionChannel().getUserNumber())
							.getAuthinfo();
					break;
			}
		}
		model.addAttribute("state", state);
		model.addAttribute("authInfo", authInfo);
		return CERT_AUTH_RESULT;
	}
	
	/**
	 * 根据输入手机号发送验证码
	 * 
	 * @param request
	 * @author tom
	 * @time 2014-7-28下午11:19:57
	 */
	@RequestMapping("auth/send_authcode_msg")
	public void sendAuthCodeMsg(@RequestParam("telephone") String telephone,
			HttpServletRequest request) {
		String code = SMSAuthCodeUtil
				.generateAuthCode(CaptchaSMSType.CERTIFICATE, telephone);
		MbaySMS.sendCaptchaSMS(CaptchaSMSType.CERTIFICATE, telephone, code);
	}
	
	/**
	 * 根据输入手机号和验证码发送语音验证码
	 * 
	 * @param request
	 */
	@RequestMapping("auth/send_voiceyzm")
	public void sendVoiceYzm(@RequestParam("telephone") String telephone,
			HttpServletRequest request) {
		String code = SMSAuthCodeUtil
				.generateAuthCode(CaptchaSMSType.CERTIFICATE, telephone);
		MbaySMS.sendVoiceCode(telephone, code);
	}
	
	/**
	 * 对比输入的手机获取的验证码！
	 * 
	 * @param request
	 * @author tom
	 * @time 2014-7-28下午11:20:05
	 */
	@RequestMapping("auth/varity_authcod")
	@ResponseBody
	public String varity_authcod(HttpServletRequest request) {
		String requestCode = request.getParameter("param");
		CaptchaResult authResult = SMSAuthCodeUtil.isAuthCodeValid(
				CaptchaSMSType.CERTIFICATE, requestCode);
		if (authResult.isSuccess()) {
			SMSAuthCodeUtil.removeAuthCode(CaptchaSMSType.CERTIFICATE);
			return "y";
		}
		return authResult.getMessage();
	}
	
	/**
	 * 上传头像
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toPortrait")
	@Token(save = true)
	public String toPortrait(Model model) {
		String portrait = userService.findPortrait(
				ChannelContext.getSessionChannel().getUserNumber());
		String value = "";
		if (!StringUtils.isEmpty(portrait)) {
			FDFSFileInfo info = (FDFSFileInfo) fsClient.getFileInfo(portrait);
			value = info.getFullPath();
		}
		model.addAttribute("portrait", value);
		return TO_PORTRAIT;
	}
	
	/**
	 * 保存头像
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("portrait")
	@Token(reset = true)
	public String portrait(@RequestParam(value = "portraitFile",
			required = true) MultipartFile portraitFile,
			@RequestParam(value = "x", required = true) int x,
			@RequestParam(value = "y", required = true) int y,
			@RequestParam(value = "w", required = true) int w,
			@RequestParam(value = "h", required = true) int h,
			@RequestParam(value = "width", required = true) int width,
			@RequestParam(value = "height", required = true) int height,
			HttpServletRequest request, Model model) {
		String fileName = portraitFile.getOriginalFilename();
		String extendtion = fileName.substring(fileName.indexOf(".") + 1,
				fileName.length());
		try {
			// 根据尺寸width和height重新生成图片
			BufferedImage image = ImageIO.read(portraitFile.getInputStream());
			Image prevImage = image.getScaledInstance(width, height,
					Image.SCALE_SMOOTH);
			int type = BufferedImage.TYPE_INT_RGB;
			// 如果是半透明文件则更换类型为TYPE_INT_ARGB
			if (extendtion.equals("png")) {
				type = BufferedImage.TYPE_INT_ARGB;
			}
			BufferedImage tag = new BufferedImage(width, height, type);
			Graphics g = tag.createGraphics();
			g.drawImage(prevImage, 0, 0, width, height, null);
			g.dispose();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(tag, extendtion, bos);
			/*
			 * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。
			 * 参数：formatName - 包含非正式格式名称 . （例如 "jpeg" 或 "tiff"）等 。
			 */
			Iterator<ImageReader> it = ImageIO
					.getImageReadersByFormatName(extendtion);
			while (it.hasNext()) {
				ImageReader reader = it.next();
				// 获取图片流
				ImageInputStream iis = ImageIO.createImageInputStream(
						new ByteArrayInputStream(bos.toByteArray()));
				/*
				 * iis:读取源.true:只向前搜索.将它标记为 ‘只向前搜索’。
				 * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader
				 * 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
				 */
				reader.setInput(iis, true);
				/*
				 * 描述如何对流进行解码的类.用于指定如何在输入时从 Java Image I/O
				 * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 将从其 ImageReader 实现的
				 * getDefaultReadParam 方法中返回 ImageReadParam 的实例。
				 */
				ImageReadParam param = reader.getDefaultReadParam();
				/*
				 * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
				 * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
				 */
				Rectangle rect = new Rectangle(x, y, w, h);
				// 提供一个 BufferedImage，将其用作解码像素数据的目标。
				param.setSourceRegion(rect);
				/*
				 * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的
				 * BufferedImage 返回。
				 */
				BufferedImage bi = reader.read(0, param);
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				// 保存新图片
				ImageIO.write(bi, extendtion, os);
				InputStream is = new ByteArrayInputStream(os.toByteArray());
				// 上传图片
				String path = fsClient.uploadFile(is, extendtion);
				// 更新头像信息
				userService.uploadPortrait(path,
						ChannelContext.getSessionChannel().getUserNumber());
				bos.close();
				iis.close();
				os.close();
			}
		} catch (Exception e) {
			LOGGER.error("上传头像失败", e.fillInStackTrace());
		}
		return REDIRECT_TO_PORTRAIT;
	}
}
