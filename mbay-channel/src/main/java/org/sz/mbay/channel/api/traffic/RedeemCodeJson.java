package org.sz.mbay.channel.api.traffic;

public class RedeemCodeJson {

    public static final String FAIL = "F";

    public static final String SUCCESS = "T";

    public static final RedeemCodeJson INVALID_PARAMETER = new RedeemCodeJson(
            FAIL, "INVALID_PARAMETER", "无效的参数信息", "");

    public static final RedeemCodeJson INVALID_MOBILE = new RedeemCodeJson(
            FAIL, "INVALID_MOBILE", "无效的手机号码", "");

    public static final RedeemCodeJson NONSUPPORT_MOBILE = new RedeemCodeJson(
            FAIL, "NONSUPPORT_MOBILE", "不支持的手机号码", "");

    public static final RedeemCodeJson SYSTEM_ERROR = new RedeemCodeJson(FAIL,
            "SYSTEM_ERROR", "系统异常", "");

    public static final RedeemCodeJson ACTIVE_NOTSTART = new RedeemCodeJson(
            FAIL, "ACTIVE_NOTSTART", "活动尚未开始", "");

    public static final RedeemCodeJson ACTIVE_OVER = new RedeemCodeJson(FAIL,
            "ACTIVE_OVER", "活动已结束", "");

    public static final RedeemCodeJson REDEEMCODE_OVER = new RedeemCodeJson(
            FAIL, "REDEEMCODE_OVER", "兑换码发放完毕", "");

    public RedeemCodeJson(String is_success, String errorcode, String faildesc,
            String redeemcode) {
        this.error_code = errorcode;
        this.is_success = is_success;
        this.faildesc = faildesc;
        this.redeemcode = redeemcode;
    }

    private String redeemcode;

    private String error_code;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getRedeemcode() {
        return redeemcode;
    }

    public void setRedeemcode(String redeemcode) {
        this.redeemcode = redeemcode;
    }

    public String getIs_success() {
        return is_success;
    }

    public void setIs_success(String is_success) {
        this.is_success = is_success;
    }

    public String getFaildesc() {
        return faildesc;
    }

    public void setFaildesc(String faildesc) {
        this.faildesc = faildesc;
    }

    private String is_success;

    public String faildesc;

}
