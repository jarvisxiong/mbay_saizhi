package org.sz.mbay.base.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * Long在json序列化时输出为字符串
 * 
 * PS: PKgen获取的id为19，而javascript最大整数为15位，会丢失精度，需要处理为字符串
 *
 * @author jerry
 */
public class LongSerialize extends JsonSerializer<Long> {
	
	@Override
	public void serialize(Long value, JsonGenerator gen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		gen.writeString(String.valueOf(value));
	}
	
}
