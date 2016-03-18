package org.sz.mbay.base.model;

import java.io.Serializable;

/**
 * 
 * @ClassName: BaseModel
 * 
 * @Description: 定义抽象基类,主要是让继承者们重新实现期方法
 * 
 * @author <a href="mailto:fenlonxiong@gmail.com">Fenlon</a>
 * 
 * @date 2014年12月3日 下午9:20:25
 * 
 */
@SuppressWarnings("serial")
public abstract class BaseModel implements Serializable {

    /**
     * Returns a multi-line String with key=value pairs.
     * 
     * @return a String representation of this class.
     */
    public abstract String toString();

    /**
     * Compares object equality. When using Hibernate, the primary key should
     * not be a part of this comparison.
     * 
     * @param o
     *            object to compare to
     * @return true/false based on equality tests
     */
    public abstract boolean equals(Object o);

    /**
     * When you override equals, you should override hashCode. See "Why are
     * equals() and hashCode() importation" for more information:
     * http://www.hibernate.org/109.html
     * 
     * @return hashCode
     */
    public abstract int hashCode();
}
