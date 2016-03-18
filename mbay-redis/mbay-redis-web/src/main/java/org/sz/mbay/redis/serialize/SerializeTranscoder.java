package org.sz.mbay.redis.serialize;

import java.io.Closeable;
import org.apache.log4j.Logger;

/**
 * 对象序列化和反序列化操作父类
 * @author frank.zong
 *
 */
public abstract class SerializeTranscoder {
	
	private static Logger LOGGER = Logger.getLogger(SerializeTranscoder.class);
	
	public abstract byte[] serialize(Object value);
	
	public abstract Object deserialize(byte[] in);
	
	public void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception e) {
				LOGGER.error("Unable to close " + closeable, e);
			}
		}
	}
}
