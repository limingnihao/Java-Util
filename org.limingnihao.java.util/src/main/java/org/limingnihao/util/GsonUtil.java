package org.limingnihao.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

public class GsonUtil {

	private static Gson GSON = new Gson();

    /**
     * json转对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
	public static <T> T fromJson(String json, Class<T> clazz) {
		if (json == null || json.equals("") || json.equals("null")) {
			return null;
		}
		try{
			return GSON.fromJson(json, clazz);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public static String toJson(Object src) {
		return GSON.toJson(src);
	}

	public static String toJsonTree(Object src) {
		return GSON.toJsonTree(src).toString();
	}

    /**
     * 获取json中某一个key对应的value
     * @param json
     * @param key
     * @return
     */
    public static String getValue(String json, String key){
    	try{
			List<Map<String,String>> list= GSON.fromJson(json,new TypeToken<List<Map<String,String>>>() { }.getType());
			for(Map<String,String> map : list){
				if(map.containsKey(key)){
					return map.get(key);
				}
			}
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
        return null;
    }

}
