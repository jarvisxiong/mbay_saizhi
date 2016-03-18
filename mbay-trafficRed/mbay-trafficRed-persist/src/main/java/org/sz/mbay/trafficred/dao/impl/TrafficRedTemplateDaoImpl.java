package org.sz.mbay.trafficred.dao.impl;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.dao.utils.PKgen;
import org.sz.mbay.redis.common.aop.RedisCache;
import org.sz.mbay.redis.common.aop.RedisUpdate;
import org.sz.mbay.trafficred.bean.TrafficRedTemplate;
import org.sz.mbay.trafficred.dao.TrafficRedTemplateDao;

@Repository
public class TrafficRedTemplateDaoImpl extends BaseDaoImpl<TrafficRedTemplate>
		implements TrafficRedTemplateDao {
		
	@Override
	public int create(TrafficRedTemplate template) {
		template.setId(PKgen.getInstance().nextPK());
		return this.template.insert("TrafficRedTemplate.create", template);
	}
	
	@RedisCache("0")
	@Override
	public TrafficRedTemplate selectById(Long id) {
		return this.template.selectOne("TrafficRedTemplate.selectById", id);
	}
	
	@RedisUpdate("selectById${0.id}")
	@Override
	public int updateByIdSelective(TrafficRedTemplate template) {
		return this.template.update("TrafficRedTemplate.updateByIdSelective",
				template);
	}
	
	@Override
	public int clearShakeCustomize(Long id) {
		return template.update("TrafficRedTemplate.clearShakeCustomize", id);
	}
	
	@Override
	public int createShakeCustomize(TrafficRedTemplate bean) {
		return template.update("TrafficRedTemplate.createShakeCustomize", bean);
	}
	
	@Override
	public boolean isExistShakeCustomize(Long cid) {
		return template.selectOne("TrafficRedTemplate.isExistShakeCustomize",
				cid);
	}
	
}
