package com.monefy.utils;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class RequestHelper {

	public static String getJsonString(Object o) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.getSerializationConfig().withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
		try {
			return objectMapper.writeValueAsString(o);
		}catch(IOException exp) {
			exp.printStackTrace();
		}
		return "";
	}
}
