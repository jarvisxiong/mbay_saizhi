package org.sz.mbay.duiba.service;

import org.sz.mbay.duiba.bean.DuiBaMall;

public interface DuiBaMallService {

    /**
     * 查询-单条
     * @param id
     * @return
     */
    public DuiBaMall findOne(int id);
    
}