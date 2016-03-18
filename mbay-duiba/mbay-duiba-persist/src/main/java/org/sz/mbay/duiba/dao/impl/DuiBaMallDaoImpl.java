package org.sz.mbay.duiba.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.duiba.bean.DuiBaMall;
import org.sz.mbay.duiba.dao.DuiBaMallDao;

@Repository
public class DuiBaMallDaoImpl extends BaseDaoImpl<DuiBaMall> implements DuiBaMallDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DuiBaMallDaoImpl.class);

    @Override
    public DuiBaMall findOne(int id) {
    	DuiBaMall bean = null;
        try{
        	bean = this.template.selectOne("findDuiBaMallById",id);
        }catch(Exception e){
            LOGGER.error("DuiBaMallDaoImpl findOne Error",e.fillInStackTrace());
        }
        return bean;
    }
    
    @Override
	public List<DuiBaMall> findList(String name, PageInfo pageinfo){
    	List<DuiBaMall> list= null;
        try {
            list = super.findList(name, pageinfo, "DuiBaMall");
        } catch (Exception e) {
            LOGGER.error("DuiBaMallDaoImpl findList Error", e.fillInStackTrace());
        }
        return list;
	}
    
    @Override
	public List<DuiBaMall> findEnabledList(){
    	List<DuiBaMall> list= null;
        try {
            list = template.selectList("findEnabledDuiBaMall");
        } catch (Exception e) {
            LOGGER.error("DuiBaMallDaoImpl findList Error", e.fillInStackTrace());
        }
        return list;
	}
}