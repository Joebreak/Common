package org.joe.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONTool {

	private static ObjectMapper JSON_MAPPER = new ObjectMapper();

	public static String writeJSON(Object object) {
		try {
			return JSON_MAPPER.writeValueAsString(object);
		} catch (Exception ex) { // ignore ex
		}
		return "";
	}

	public static <T> T readJSON(String jsonString, Class<T> clazz) {
		if (jsonString != null) {
			try {
				return JSON_MAPPER.readValue(jsonString, clazz);
			} catch (Exception ex) { // ignore ex
			    System.out.println(ex);
			}
		}
		return null;
	}

}
