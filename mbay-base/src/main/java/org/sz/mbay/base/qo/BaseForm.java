package org.sz.mbay.base.qo;

import org.joda.time.DateTime;


/**
 * @author user
 *
 */
public class BaseForm {
    /**交易记录起始时间***/
    private DateTime starttime;
    /**交易记录终止时间**/
    private DateTime endtime;

    public DateTime getStarttime() {
        return starttime;
    }
    public void setStarttime(DateTime starttime) { 
        this.starttime = starttime;
    }

    public DateTime getEndtime() {
        return endtime;
    }

    public void setEndtime(DateTime endtime) {
        this.endtime = endtime;
    }
}
