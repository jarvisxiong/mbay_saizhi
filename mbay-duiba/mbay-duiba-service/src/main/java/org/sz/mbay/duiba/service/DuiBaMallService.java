package org.sz.mbay.duiba.service;

import java.util.List;

import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.duiba.bean.DuiBaMall;

public interface DuiBaMallService {

    /**
     * 删除
     * @param id
     * @return
     */
    public boolean del(int id);

    /**
     * 新增
     * @param bean
     */
    public void add(DuiBaMall bean);

    /**
     * 更新
     * @param bean
     */
    public void update(DuiBaMall bean);
    
    /**
     * 查询-单条
     * @param id
     * @return
     */
    public DuiBaMall findOne(int id);
    
    /**
     * 查询-分页
     * @param name
     * @param pageinfo
     * @return
     */
    public List<DuiBaMall> findList(String name,PageInfo pageinfo);
    
    /**
     * 查询
     * @param name
     * @return
     */
    public List<DuiBaMall> findEnabledList();
    
    /**
     * 修改状态 id->id,status->状态
     * @param id
     * @param status
     */
    public ExecuteResult change(int id,EnableState status);
}