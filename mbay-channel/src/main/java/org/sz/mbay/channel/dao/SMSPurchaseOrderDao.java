package org.sz.mbay.channel.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.channel.bean.SMSPurchaseOrder;
import org.sz.mbay.channel.form.SMSPurchaseOrderForm;

public interface SMSPurchaseOrderDao extends BaseDao<SMSPurchaseOrder> {

    /**
     * 根据用户编号查询购买短信订单列表
     * @param  userNumber
     *         用户编号
     *
     * @param  pageinfo
     *         分页信息
     *
     * @param  sMSPurchaseOrderCriteria
     *         查询条件信息
     * @return
     */
    public List<SMSPurchaseOrder> findAllSMSPurchaseOrder(
            String userNumber, PageInfo pageinfo,
            SMSPurchaseOrderForm sMSPurchaseOrderCriteria);

    /**
     * 保存用户购买短信订单
     * @param  sMSPurchaseOrder
     *         用户购买短信订单信息
     *
     * @return
     */
    public boolean saveSMSPurchaseOrder(SMSPurchaseOrder sMSPurchaseOrder);
    
    /**
     * 根据订单号查找订单
     * 
     * @param orderId
     * @return
     */
    public SMSPurchaseOrder findSMSPurchaseOrderByOrderId(String orderId);
}
