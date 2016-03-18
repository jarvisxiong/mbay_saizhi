package org.sz.mbay.base.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Long反序列化
 * 
 * @author jerry
 */
public class LongDeserialize extends JsonDeserializer<Long> {
	
	@Override
	public Long deserialize(JsonParser jp, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		return Long.parseLong(jp.getText());
	}
	
}
