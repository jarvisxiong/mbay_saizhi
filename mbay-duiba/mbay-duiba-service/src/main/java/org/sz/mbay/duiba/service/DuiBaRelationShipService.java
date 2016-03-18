package org.sz.mbay.duiba.service;

import java.util.List;

import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.duiba.bean.DuiBaRelationShip;

public interface DuiBaRelationShipService {

    /**
     * 删除
     * @param usernumber
     * @param mallId
     * @return
     */
    public boolean del(String usernumber, int mallId);

    /**
     * 新增
     * @param bean
     */
    public void add(DuiBaRelationShip bean);

    /**
     * 更新
     * @param bean
     */
    public void update(DuiBaRelationShip bean);
    
    /**
     * 查询-单条
     * @param id
     * @return
     */
    public DuiBaRelationShip findOne(int id);
    
    /**
     * 查询-分页
     * @param name
     * @param pageinfo
     * @return
     */
    public List<DuiBaRelationShip> findList(String usernumber,PageInfo pageinfo);
    
}