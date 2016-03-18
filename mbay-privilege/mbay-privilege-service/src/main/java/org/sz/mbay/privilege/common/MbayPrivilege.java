package org.sz.mbay.privilege.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.sz.mbay.base.context.SpringApplicationContext;
import org.sz.mbay.privilege.bean.Buser;
import org.sz.mbay.privilege.bean.BuserRole;
import org.sz.mbay.privilege.bean.Privilege;
import org.sz.mbay.privilege.bean.Role;
import org.sz.mbay.privilege.bean.RolePrivilege;
import org.sz.mbay.privilege.service.BuserRoleService;
import org.sz.mbay.privilege.service.BuserService;
import org.sz.mbay.privilege.service.PrivilegeService;
import org.sz.mbay.privilege.service.RolePrivilegeService;
import org.sz.mbay.privilege.service.RoleService;
import org.sz.mbay.redis.common.MbayRedisCommon;

/**
 * mbayPrivilege调用入口
 * 
 * @author frank.zong
 * 
 */
public class MbayPrivilege {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MbayPrivilege.class);
	
	private MbayPrivilege(){}
	
	/**
	 * 根据用户名从redis中获取用户数据
	 * @param name
	 * @return 如果查询失败返回null
	 */
	public static Buser findBuser(String name){
		//判断用户名是否为空
		if(StringUtils.isEmpty(name)){
			LOGGER.error("查询用户数据失败,原因:用户名为空");
			return null;
		}
		Buser bean = null;//MbayRedisCommon.getOne("buser_" + name);
		//如果在redis中没有找到对应的信息，则从数据库中读取，读取完后存入redis中
		if(bean == null){
			LOGGER.info("没有在redis中找到对应的用户数据,开始从数据库中读取,name:" + name);
			BuserService service = (BuserService) SpringApplicationContext.getBean(BuserService.class.getSimpleName());
			bean = service.findBuser(name);
			/*if(bean != null){
				LOGGER.info("把从数据库中获取的用户数据存入到redis中,name:" + name);
				boolean result = MbayRedisCommon.setOne("buser_" + name, bean);
				if(result){
					LOGGER.info("用户数据存入到redis中成功,name:" + name);
				}else{
					LOGGER.error("用户数据存入到redis中失败,name:" + name);
				}
			}*/
		}
		return bean;
	}
	
	/**
	 * 根据用户id从redis中获取用户-角色对应关系数据
	 * @param userId
	 * @return 如果查询失败返回null
	 */
	public static List<BuserRole> findAllBuserRoleByUserId(int userId){
		//验证userId是否正确
		if(userId <= 0){
			LOGGER.error("查询用户-角色对应关系数据失败,原因:userId验证失败");
			return null;
		}
		List<BuserRole> list = null;//MbayRedisCommon.getList("buser_role_" + userId);
		//如果在redis中没有找到对应的信息，则从数据库中读取，读取完后存入redis中
		if(list == null || list.size() == 0){
			LOGGER.info("没有在redis中找到对应的用户-角色对应关系数据,开始从数据库中读取,userId:" + userId);
			BuserRoleService service = (BuserRoleService) SpringApplicationContext.getBean(BuserRoleService.class.getSimpleName());
			list = service.findAllBuserRoleByUserId(userId);
			/*if(list != null && list.size() > 0){
				LOGGER.info("把从数据库中获取的用户-角色对应关系数据存入到redis中,userId:" + userId);
				boolean result = MbayRedisCommon.setList("buser_role_" + userId, list);
				if(result){
					LOGGER.info("用户-角色对应关系数据存入到redis中成功,userId:" + userId);
				}else{
					LOGGER.error("用户-角色对应关系数据存入到redis中失败,userId:" + userId);
				}
			}*/
		}
		return list;
	}
	
	/**
	 * 根据角色id从redis中获取角色数据
	 * @param roleId
	 * @return 如果查询失败返回null
	 */
	public static Role findRole(int roleId){
		//验证角色id
		if(roleId <= 0){
			LOGGER.error("查询角色数据失败,原因:roleId验证失败");
			return null;
		}
		Role bean = null;MbayRedisCommon.getOne("role_" + roleId);
		//如果在redis中没有找到对应的信息，则从数据库中读取，读取完后存入redis中
		if(bean == null){
			LOGGER.info("没有在redis中找到对应的角色数据,开始从数据库中读取,roleId:" + roleId);
			RoleService service = (RoleService) SpringApplicationContext.getBean(RoleService.class.getSimpleName());
			bean = service.findRole(roleId);
			/*if(bean != null){
				LOGGER.info("把从数据库中获取的角色数据存入到redis中,roleId:" + roleId);
				boolean result = MbayRedisCommon.setOne("role_" + roleId, bean);
				if(result){
					LOGGER.info("角色数据存入到redis中成功,roleId:" + roleId);
				}else{
					LOGGER.error("角色数据存入到redis中失败,roleId:" + roleId);
				}
			}*/
		}
		return bean;
	}
	
	/**
	 * 根据角色id从redis中获取角色-权限对应关系数据
	 * @param roleId
	 * @return 如果查询失败返回null
	 */
	public static List<RolePrivilege> findAllRolePrivilegeByRoleId(int roleId){
		//验证roleId是否正确
		if(roleId <= 0){
			LOGGER.error("查询角色-权限对应关系数据失败,原因:roleId验证失败");
			return null;
		}
		List<RolePrivilege> list = null;MbayRedisCommon.getList("role_privilege_" + roleId);
		//如果在redis中没有找到对应的信息，则从数据库中读取，读取完后存入redis中
		if(list == null || list.size() == 0){
			LOGGER.info("没有在redis中找到对应的角色-权限对应关系数据,开始从数据库中读取,roleId:" + roleId);
			RolePrivilegeService service = (RolePrivilegeService) SpringApplicationContext.getBean(RolePrivilegeService.class.getSimpleName());
			list = service.findAllRolePrivilegeByRoleId(roleId);
			/*if(list != null && list.size() > 0){
				LOGGER.info("把从数据库中获取的角色-权限对应关系数据存入到redis中,roleId:" + roleId);
				boolean result = MbayRedisCommon.setList("role_privilege_" + roleId, list);
				if(result){
					LOGGER.info("角色-权限对应关系数据存入到redis中成功,roleId:" + roleId);
				}else{
					LOGGER.error("角色-权限对应关系数据存入到redis中失败,roleId:" + roleId);
				}
			}*/
		}
		return list;
	}
	
	/**
	 * 根据权限id从redis中获取权限数据
	 * @param privilegeId
	 * @return 如果查询失败返回null
	 */
	public static Privilege findPrivilege(int privilegeId){
		//验证权限id
		if(privilegeId <= 0){
			LOGGER.error("查询权限数据失败,原因:privilegeId验证失败");
			return null;
		}
		Privilege bean = null;MbayRedisCommon.getOne("privilege_" + privilegeId);
		//如果在redis中没有找到对应的信息，则从数据库中读取，读取完后存入redis中
		if(bean == null){
			LOGGER.info("没有在redis中找到对应的权限数据,开始从数据库中读取,privilegeId:" + privilegeId);
			PrivilegeService service = (PrivilegeService) SpringApplicationContext.getBean(PrivilegeService.class.getSimpleName());
			bean = service.findPrivilege(privilegeId);
			/*if(bean != null){
				LOGGER.info("把从数据库中获取的权限数据存入到redis中,privilegeId:" + privilegeId);
				boolean result = MbayRedisCommon.setOne("privilege_" + privilegeId, bean);
				if(result){
					LOGGER.info("权限数据存入到redis中成功,privilegeId:" + privilegeId);
				}else{
					LOGGER.error("权限数据存入到redis中失败,privilegeId:" + privilegeId);
				}
			}*/
		}
		return bean;
	}
}