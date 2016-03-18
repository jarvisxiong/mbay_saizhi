package org.sz.mbay.redis.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * Object对象序列化和反序列化操作类
 * @author frank.zong
 *
 * @param <M>
 */
public class ObjectsTranscoder<M extends Serializable> extends SerializeTranscoder {
	
	protected static Logger LOGGER = Logger.getLogger(ObjectsTranscoder.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public byte[] serialize(Object value) {
		byte[] result = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream os = null;
		try {
			bos = new ByteArrayOutputStream();
			os = new ObjectOutputStream(bos);
			M m = (M) value;
			os.writeObject(m);
			os.close();
			bos.close();
			result = bos.toByteArray();
		} catch (Exception e) {
			LOGGER.error("对象没有实现Serializable接口",e);
		} finally {
			close(os);
			close(bos);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public M deserialize(byte[] in) {
		M result = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream is = null;
		try {
			if (in != null) {
				bis = new ByteArrayInputStream(in);
				is = new ObjectInputStream(bis);
				result = (M) is.readObject();
				is.close();
				bis.close();
			}
		} catch (Exception e) {
			LOGGER.error("对象反序列化失败", e);
		} finally {
			close(is);
			close(bis);
		}
		return result;
	}
}