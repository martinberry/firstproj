package com.travelzen.swordfish.thrift.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.util.TZBeanUtils;

public class JacksonUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public JacksonUtil() {

    }

    public static ObjectMapper getInstance() {

        return objectMapper;
    }

    /**
     * obj 转 json
     */
    public static String obj2json(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * json 转 obj
     */
    public static <T> T json2pojo(String jsonStr, Class<T> clazz) throws Exception {
        if(jsonStr == null) return null;
        return objectMapper.readValue(jsonStr, clazz);
    }

    /**
     * json 转 obj
     */
    public static <T> T json2pojo(String jsonStr, Class<T> clazz, Class<?>... genericClass) throws Exception {
    	JavaType javaType = getCollectionType(clazz, genericClass);
        if(jsonStr == null) return null;
        return objectMapper.readValue(jsonStr, javaType);
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
     }

    /**
     * json 字符串转map
     */
    @SuppressWarnings("unchecked")
	public static <T> Map<String, Object> json2map(String jsonStr) throws Exception {
        return objectMapper.readValue(jsonStr, Map.class);
    }

    /**
     * json 字符串指定value类型map
     */
    public static <T> Map<String, T> json2map(String jsonStr, Class<T> clazz) throws Exception {
        Map<String, Map<String, Object>> map = objectMapper.readValue(jsonStr,new TypeReference<Map<String, T>>() {});
        Map<String, T> result = new HashMap<String, T>();
        for (Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), map2pojo(entry.getValue(), clazz));
        }
        return result;
    }

    /**
     * json array 转list
     */
    public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz) throws Exception {
        List<Map<String, Object>> list = objectMapper.readValue(jsonArrayStr,new TypeReference<List<T>>() {});
        List<T> result = new ArrayList<T>();
        for (Map<String, Object> map : list) {
            result.add(map2pojo(map, clazz));
        }
        return result;
    }

    /**
     * json array 转list
     */
    public static List<Object> json2list(String jsonArrayStr, Class<?>[] classes) throws Exception {
        List<Object> list = objectMapper.readValue(jsonArrayStr,objectMapper.getTypeFactory().constructCollectionType(List.class, Object.class));
        List<Object> result = new ArrayList<Object>();

        for(int i = 0 ; i< list.size(); i++) {
        	result.add(json2pojo(obj2json(list.get(i)).toString(), classes[i]));
        }
        return result;
    }

    /**
     * map 转javabean
     */
    public static <T> T map2pojo(Map<?, ?> map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    public static void main(String[] args) throws Exception{
        Pagination pagination = new Pagination(1,4);
        String json = obj2json(pagination);
        System.out.println(json);
        Pagination p = json2pojo(json, Pagination.class);
        System.out.println(TZBeanUtils.describe(p));
    }

}
