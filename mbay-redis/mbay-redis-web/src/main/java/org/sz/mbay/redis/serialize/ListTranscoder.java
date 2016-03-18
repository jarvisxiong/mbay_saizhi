package org.sz.mbay.redis.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * List对象序列化和反序列化操作类
 * 
 * @author frank.zong
 * 
 * @param <M>
 */
public class ListTranscoder<M extends Serializable> extends SerializeTranscoder {
	
	protected static Logger LOGGER = Logger.getLogger(ListTranscoder.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public byte[] serialize(Object value) {
		List<M> values = (List<M>) value;
		byte[] results = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream os = null;
		try {
			bos = new ByteArrayOutputStream();
			os = new ObjectOutputStream(bos);
			for (M m : values) {
				os.writeObject(m);
			}
			os.writeObject(null);//解决抛出eofexception问题
			os.close();
			bos.close();
			results = bos.toByteArray();
		} catch (Exception e) {
			LOGGER.error("对象没有实现Serializable接口", e);
		} finally {
			close(os);
			close(bos);
		}
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public List<M> deserialize(byte[] in) {
		List<M> list = new ArrayList<>();
		ByteArrayInputStream bis = null;
		ObjectInputStream is = null;
		try {
			if (in != null) {
				bis = new ByteArrayInputStream(in);
				is = new ObjectInputStream(bis);
				Object object = null;
				while ((object = is.readObject()) != null) {
					M m = (M) object;
					list.add(m);
				}
				is.close();
				bis.close();
			}
		} catch (Exception e) {
			LOGGER.error("对象反序列化失败", e);
		} finally {
			close(is);
			close(bis);
		}
		return list;
	}
}
