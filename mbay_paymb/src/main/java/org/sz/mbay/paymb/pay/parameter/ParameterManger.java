package org.sz.mbay.paymb.pay.parameter;

import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.paymb.pay.annotation.TradePolicy;

/**
 * @author han.han
 *         <P>
 *         The basic service for managing a set of ParameterRequirs.<br>
 *
 */
public final class ParameterManger {

	private static final Logger LOGGER = LoggerFactory.getLogger(ParameterManger.class);
	

	private static final Map<TradePolicy, ParameterRequir> parameterRequirCache = new HashMap<TradePolicy, ParameterRequir>(
			8);

	static {
		loadInitialParameterRequirs();
		LOGGER.info("ParameterManger initialized");
	}

	private ParameterManger() {
	}

	public static void registerParameterRequir(TradePolicy tradePolicy, ParameterRequir parameterRequir) {
		Assert.notNull(tradePolicy, "registerParameterRequir tradePolicy can't be null");
		Assert.notNull(parameterRequir, "registerParameterRequir parameterRequir can't be null");
		parameterRequirCache.put(tradePolicy, parameterRequir);

	}

	public static boolean isMatch(Map<String, String[]> requestParmeters, TradePolicy policy) {
		Assert.notNull(policy, "请指定交易策略");
		List<Parameter> requirParameters = parameterRequirCache.get(policy).getRequiredParameters();
		if (CollectionUtils.isEmpty(requirParameters))
			return true;
		if (MapUtils.isEmpty(requestParmeters))
			return false;

		boolean match = true;
		for (Parameter parameter : requirParameters) {
			String parameterName = parameter.getName();
			boolean isContain = requestParmeters.containsKey(parameterName);
			if (isContain) {
				String[] values = requestParmeters.get(parameterName);
				if (values.length == 1) {
					String value = values[0];
					if (!StringUtils.isEmpty(value)) {

						boolean typeMatch = StringUtils.isEmpty(parameter.getRegExp()) ? true
								: value.matches(parameter.getRegExp());
						if (typeMatch) {
							continue;
						}

					}
				}
			}
			match = false;
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn("请求参数{}:\"{}\"不合法", parameterName,
						requestParmeters.get(parameterName) == null ? "" : requestParmeters.get(parameterName)[0]);
			}
			break;
		}
		return match;
	}

	/**
	 * sign and sign_type 不参与签名
	 * 
	 * @param paramenters
	 * @param signKey
	 * @return
	 */
	public static String signRequest(Map<String, String[]> paramenters, String signKey) {
		Assert.notNull(paramenters, "signRequest paramenters can't be null");
		Assert.notNull(signKey, "signKey paramenters can't be null");
		List<String> parameterList = new ArrayList<String>();
		String _input_charset = paramenters.get(AbstractParameterRequir._INPUT_CHARSET)[0];
		for (Map.Entry<String, String[]> parameter : paramenters.entrySet()) {
			String parameterName = parameter.getKey();
			// sign and sign_type 不参与签名
			if (AbstractParameterRequir.SIGN.equals(parameterName)
					|| AbstractParameterRequir.SIGN_TYPE.equals(parameterName)) {
				continue;
			}

			String value;
			try {
				value = new String(parameter.getValue()[0].getBytes("ISO-8859-1"), _input_charset);
				parameterList.add(parameterName + "=" + value);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		}
		Collections.sort(parameterList);
		StringBuilder strBuilder = new StringBuilder(50);
		for (String str : parameterList) {
			strBuilder.append("&" + str);
		}
		String standbySign = strBuilder.toString().substring(1) + signKey;
		LOGGER.info("请求加密串：" + standbySign);
		String encrypt = DigestUtils.md5Encrypt(standbySign);
		return encrypt;
	}

	private static void loadInitialParameterRequirs() {
		try {
			AccessController.doPrivileged(new PrivilegedAction<Void>() {
				public Void run() {
					ServiceLoader<ParameterRequir> loadedParameterRequirs = ServiceLoader.load(ParameterRequir.class);
					Iterator<ParameterRequir> parameterRequirs = loadedParameterRequirs.iterator();

					try {
						while (parameterRequirs.hasNext()) {
							// load povider class
							ParameterRequir pr = parameterRequirs.next();
							if (LOGGER.isInfoEnabled()) {
								LOGGER.info("ParameterRequir 实现类：" + pr.getClass().getSimpleName() + " have loaded");
							}
						}
					} catch (Throwable t) {

					}
					return null;
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	@Override
	public String toString() {
		return "PayParameters []";
	}

	/*
	 * static class CastManager { private static CastString cast = new
	 * DefualutStringCast();
	 * 
	 * public static void registCast(CastString cast) { CastManager.cast = cast;
	 * }
	 * 
	 * public static CastString getCastString() { return cast; } }
	 * 
	 * private interface CastString {
	 * 
	 * public boolean canCast(String value, Class<?> targetType);
	 * 
	 * }
	 * 
	 * private static class DefualutStringCast implements CastString {
	 * 
	 * @Override public boolean canCast(String value, Class<?> targetType) { if
	 * (targetType == Integer.class) { try { Integer.parseInt(value); } catch
	 * (NumberFormatException e) { return false; }
	 * 
	 * } if (targetType == String.class) { } return true;
	 * 
	 * }
	 * 
	 * }
	 */
}
