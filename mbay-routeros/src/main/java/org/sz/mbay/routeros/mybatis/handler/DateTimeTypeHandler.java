package org.sz.mbay.routeros.mybatis.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.routeros.util.MbayDateFormat.DateFormatter;

/**
 * 数据库 datetime 转换为joda DateTime
 * 
 * @author han.han
 */
public class DateTimeTypeHandler extends BaseTypeHandler<DateTime> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DateTimeTypeHandler.class);
	
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			DateTime parameter, JdbcType jdbcType) throws SQLException {
		ps.setTimestamp(i, new Timestamp(parameter.getMillis()));// 将DateTime类型转换为java.sql.TimeStamp类型
	}
	
	@Override
	public DateTime getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		try {
			String datatime = rs.getString(columnName);
			if (datatime == null) {
				return null;
			}
			if (datatime.length() > 19) {
				datatime = datatime.substring(0, 19);
			}
			DateTimeFormatter format = DateFormatter.timeFormat;
			if (datatime.length() == 10) {
				format = DateFormatter.dateFormat;
			}
			DateTime sqlDate = DateTime.parse(datatime, format);
			return sqlDate;
		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return null;
		}
	}
	
	@Override
	public DateTime getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		try {
			String datatime = rs.getString(columnIndex);
			if (datatime == null) {
				return null;
			}
			if (datatime.length() > 19) {
				datatime = datatime.substring(0, 19);
			}
			DateTimeFormatter format = DateFormatter.timeFormat;
			if (datatime.length() == 10) {
				format = DateFormatter.dateFormat;
			}
			DateTime sqlDate = DateTime.parse(datatime, format);
			return sqlDate;
		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return null;
		}
	}
	
	@Override
	public DateTime getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		try {
			String datatime = cs.getString(columnIndex);
			if (datatime == null) {
				return null;
			}
			if (datatime.length() > 19) {
				datatime = datatime.substring(0, 19);
			}
			DateTimeFormatter format = DateFormatter.timeFormat;
			if (datatime.length() == 10) {
				format = DateFormatter.dateFormat;
			}
			DateTime sqlDate = DateTime.parse(datatime, format);
			return sqlDate;
		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return null;
		}
	}
	
}
