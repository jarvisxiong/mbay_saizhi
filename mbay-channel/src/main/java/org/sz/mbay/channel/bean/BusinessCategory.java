package org.sz.mbay.channel.bean;

/**
 * 企业分类
 * @author meibei-hrain
 *
 */
public class BusinessCategory {

    private int id;
    //类别代码（例：18）
    private String enterpriseTypeCode;
    //类别名称（例：教育/培训）
    private String enterpriseTypeName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnterpriseTypeCode() {
        return enterpriseTypeCode;
    }

    public void setEnterpriseTypeCode(String enterpriseTypeCode) {
        this.enterpriseTypeCode = enterpriseTypeCode;
    }

    public String getEnterpriseTypeName() {
        return enterpriseTypeName;
    }

    public void setEnterpriseTypeName(String enterpriseTypeName) {
        this.enterpriseTypeName = enterpriseTypeName;
    }
}
