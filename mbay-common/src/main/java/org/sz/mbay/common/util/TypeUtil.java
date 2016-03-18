package org.sz.mbay.common.util;

public class TypeUtil {
	
	/**
	 * 检测是否是java基本类型及其包装类型、枚举
	 */
	public static boolean isPrimitive(Object val) {
		// 排除null
		if (val == null) {
			return false;
		}
		
		// 基本类型
		Class<?> clazz = val.getClass();
		if (clazz.isPrimitive()) {
			return true;
		}
		
		try {
			if (clazz.isAssignableFrom(String.class)) {
				return true;
			}
			if (val instanceof Enum) {
				return true;
			}
			
			// 基本类型的包装类型
			if (((Class<?>) clazz.getField("TYPE").get(null)).isPrimitive()) {
				return true;
			}
		} catch (Exception e) {
		}
		
		return false;
	}
}
