package org.sz.mbay.wallet.action.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.annotation.CacheControl;
import org.sz.mbay.base.annotation.CachePolicy;
import org.sz.mbay.fs.FSClient;
import org.sz.mbay.fs.FSClientFactory;
import org.sz.mbay.fs.FSClientFactory.ClientType;
import org.sz.mbay.fs.fastdfs.FDFSFileInfo;
import org.sz.mbay.wallet.bean.ChannelCampaign;
import org.sz.mbay.wallet.context.WalletContext;
import org.sz.mbay.wallet.entity.SessionUser;
import org.sz.mbay.wallet.service.ChannelCampaignService;

import net.sf.json.JSONObject;

@Controller("Web_CampaignAction")
@RequestMapping("web/campaign")
public class CampaignAction {
	
	@Autowired
	private ChannelCampaignService campaignService;
	
	// fastdfs文件管理
	private FSClient fsClient = FSClientFactory.getClient(ClientType.FDFS);
	
	/**
	 * 活动界面
	 * 
	 * @param model
	 * @return
	 */
	@CacheControl(policy = CachePolicy.NO_CACHE)
	@RequestMapping("list/ui")
	public String campaignUi(
			@RequestParam(value = "mobile", required = false) String defMobile,
			Model model) {
		SessionUser suser = WalletContext.getSessionUser();
		String mobile = null;
		if (suser != null) {
			mobile = suser.getMobile();
		} else {
			mobile = defMobile;
		}
		model.addAttribute("mobile", mobile);
		return "campaign/list";
	}
	
	/**
	 * 获取数据
	 * 
	 * @param pageinfo
	 * @return
	 */
	@RequestMapping("list/data/get")
	@ResponseBody
	public JSONObject getData() {
		JSONObject json = new JSONObject();
		List<ChannelCampaign> list = campaignService
				.findAllChannelCampaign(null);
		if (list != null && list.size() > 0) {
			for (ChannelCampaign bean : list) {
				FDFSFileInfo imageInfo = (FDFSFileInfo) fsClient
						.getFileInfo(bean.getImage());
				bean.setImage(imageInfo.getFullPath());
			}
		}
		json.put("list", list);
		return json;
	}
}
