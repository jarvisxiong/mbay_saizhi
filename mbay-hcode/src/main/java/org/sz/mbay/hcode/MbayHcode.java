package org.sz.mbay.hcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.sz.mbay.base.context.SpringApplicationContext;
import org.sz.mbay.base.regex.pattern.RegExp;
import org.sz.mbay.hcode.bean.HcodeInfo;
import org.sz.mbay.hcode.service.HcodeInfoService;
import org.sz.mbay.redis.common.MbayRedisCommon;

/**
 * mbayhcode调用入口
 * 
 * @author frank.zong
 * 
 */
public class MbayHcode {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MbayHcode.class);
	
	private MbayHcode(){}
	
	/**
	 * 根据手机号从redis中获取相应hcode数据
	 * @param mobile
	 * @return 如果查询失败返回null
	 */
	public static HcodeInfo getHcodeInfo(String mobile){
		//判断手机号是否为空
		if(StringUtils.isEmpty(mobile)){
			LOGGER.error("查询hcode数据失败,原因:手机号为空");
			return null;
		}
		//判断是否为正确的手机号格式
		if(!RegExp.mobile.matcher(mobile).matches()){
			LOGGER.error("查询hcode数据失败,原因:不是正确的手机号格式");
			return null;
		}
		String hcode = mobile.substring(0,7);
		HcodeInfo bean =null;// MbayRedisCommon.getOne(hcode);
		//如果在redis中没有找到对应的信息，则从数据库中读取，读取完后存入redis中
		if(bean == null){
			LOGGER.info("没有在redis中找到对应的hcode数据,开始从数据库中读取,hcode:" + hcode);
			HcodeInfoService service = (HcodeInfoService) SpringApplicationContext.getBean(HcodeInfoService.class.getSimpleName());
			bean = service.getHcodeInfo(hcode);
//			if(bean != null){
//				LOGGER.info("把从数据库中获取的hcode数据存入到redis中,hcode:" + hcode);
////				boolean result = MbayRedisCommon.setOne(hcode, bean);
////				if(result){
////					LOGGER.info("hcode数据存入到redis中成功,hcode:" + hcode);
////				}else{
////					LOGGER.error("hcode数据存入到redis中失败,hcode:" + hcode);
////				}
//				
//			}
		}
		return bean;
	}
}