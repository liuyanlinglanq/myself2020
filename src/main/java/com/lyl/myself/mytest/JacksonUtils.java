package com.lyl.myself.mytest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * json工具 <br>
 */
public class JacksonUtils {
    private static int i = 0;

    private static ObjectMapper DEFAULT_MAPPER;
    private static ObjectMapper FORMAT_MAPPER = new ObjectMapper();

    static {
        DEFAULT_MAPPER = new ObjectMapper();
        DEFAULT_MAPPER.setLocale(Locale.CHINA);
        DEFAULT_MAPPER.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        // 在遇到未知属性的时候不抛出异常
        DEFAULT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        FORMAT_MAPPER.configure(SerializationFeature.INDENT_OUTPUT, Boolean.TRUE);
        FORMAT_MAPPER.setLocale(Locale.CHINA);
        FORMAT_MAPPER.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        // 在遇到未知属性的时候不抛出异常
        FORMAT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    /**
     * 调用get方法生成json字符串 <br>
     * 2015年1月27日:下午12:26:55<br>
     * <br>
     *
     * @param obj
     * @return
     */
    public static String toJson(
            Object obj) {
        try {
            return DEFAULT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 拿到格式化之后的json <br>
     * <strong>仅用于测试</strong>
     * 2015年11月26日:下午7:16:51<br>
     * <br>
     *
     * @param obj
     * @return
     */
    public static String toFormatedJson(
            Object obj) {
        try {
            return FORMAT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 转换json为clazz. <br>
     * <strong>依赖get和set方法</strong> <br>
     * 2015年1月27日:下午12:26:18<br>
     * <br>
     *
     * @param jsonText
     * @param clazz
     * @return
     */
    public static <T> T fromJson(
            String jsonText,
            Class<T> clazz) throws IOException {
        if (jsonText == null) {
            return null;
        }
        return DEFAULT_MAPPER.readValue(jsonText, clazz);
    }

    /**
     * 转换json为clazz. <br>
     * <strong>依赖get和set方法</strong> <br>
     * 2015年1月27日:下午12:26:18<br>
     * <br>
     *
     * @param jsonText
     * @param clazz
     * @return
     */
    public static <T> T fromJsonIgnore(
            String jsonText,
            Class<T> clazz) throws IOException {
        if (jsonText == null) {
            return null;
        }
        DEFAULT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return DEFAULT_MAPPER.readValue(jsonText, clazz);
    }

    /**
     * 转换为集合类型的对象集合
     * <strong>依赖get和set方法</strong> <br>
     * 2015年3月10日:上午11:19:14<br>
     * <br>
     * <strong>example:</strong>
     * <p>
     * <pre>
     * JacksonUtils.fromJson(jsonText, new TypeReference&lt;List&lt;FeedImage&gt;&gt;() {
     * });
     * </pre>
     *
     * @param jsonText
     * @param valueTypeRef org.codehaus.jackson.type.TypeReference
     */
    public static <T> T fromJson(String jsonText, TypeReference<T> valueTypeRef) throws IOException {
        if (jsonText == null) {
            return null;
        }
        return DEFAULT_MAPPER.readValue(jsonText, valueTypeRef);
    }

    /**
     * 从json字符串中读取出指定的节点 <br>
     * 2015年1月27日:下午12:26:04<br>
     * <br>
     *
     * @param json
     * @param key
     * @return
     * @throws JsonProcessingException
     * @throws IOException
     */
    public static JsonNode getValueFromJson(
            String json,
            String key) throws JsonProcessingException, IOException {
        JsonNode node = DEFAULT_MAPPER.readTree(json);
        return node.get(key);
    }

    /**
     * 把json生成jsonNode <br>
     * 2015年12月15日:下午7:34:38<br>
     * <br>
     *
     * @param json
     * @return
     * @throws JsonProcessingException
     * @throws IOException
     */
    public static JsonNode getJsonNode(
            String json) throws JsonProcessingException, IOException {
        return DEFAULT_MAPPER.readTree(json);
    }

    /**
     * 从json字符串中读取数组节点所包含的JsonNode List<br>
     * 2015年1月28日:下午18:26:04<br>
     * <br>
     *
     * @param json
     * @param key
     * @return
     * @throws JsonProcessingException
     * @throws IOException
     */
    public static List<JsonNode> getListFromJson(
            String json,
            String key) throws JsonProcessingException, IOException {
        List<JsonNode> jsonNodes = null;
        JsonNode node = DEFAULT_MAPPER.readTree(json);
        JsonNode arrayNode = node.get(key);
        if (arrayNode.isArray()) {
            jsonNodes = new ArrayList<JsonNode>();
            for (JsonNode jsonNode : arrayNode) {
                jsonNodes.add(jsonNode);
            }
        }
        return jsonNodes;
    }

    /**
     * 处理node为null的问题 <br>
     * 2015年12月9日:下午12:44:17<br>
     * <br>
     *
     * @param node
     * @return
     */
    public static String getStringValueFromNode(
            JsonNode node) {
        if (node != null) {
            return node.asText();
        }
        return null;
    }

    public static boolean jsonEqueals(String json1, String json2) {
        try {
            JsonNode jsonNode1 = getJsonNode(json1);
            JsonNode jsonNode2 = getJsonNode(json2);
            return jsonNode1.equals(jsonNode2);
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean jsonEqueals(String json, Object o) {
        try {
            JsonNode jsonNode1 = getJsonNode(toJson(o));
            JsonNode jsonNode2 = getJsonNode(json);
            return jsonNode1.equals(jsonNode2);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 复杂嵌套Json获取Object数据
     */
    public static Object getObjectByJson(String jsonStr, String argsPath, TypeEnum argsType) throws Exception {
        if (argsPath == null || argsPath.equals("") || argsType == null) {
            return null;
        }

        Object obj = null;
        Map maps = FORMAT_MAPPER.readValue(jsonStr, Map.class);
        //多层获取
        if (argsPath.indexOf(".") >= 0) {
            //类型自适应
            obj = getObject(maps, argsPath, argsType);
        } else { //第一层获取
            if (argsType == TypeEnum.string) {
                obj = maps.get(argsPath).toString();
            } else if (argsType == TypeEnum.map) {
                obj = (Map) maps.get(argsPath);
            } else if (argsType == TypeEnum.arrayList) {
                obj = (List) maps.get(argsPath);
            } else if (argsType == TypeEnum.arrayMap) {
                obj = (List<Map>) maps.get(argsPath);
            }
        }
        i = 0;
        return obj;
    }

    //递归获取object
    private static Object getObject(Object m, String key, TypeEnum type) throws Exception {
        if (m == null) {
            return null;
        }
        Object o = null;
        Map mp = null;
        List ls = null;
        //对象层级递归遍历解析
        if (m instanceof Map || m instanceof LinkedHashMap) {
            mp = (LinkedHashMap) m;
            for (Iterator ite = mp.entrySet().iterator(); ite.hasNext(); ) {
                Map.Entry e = (Map.Entry) ite.next();

                if (i < key.split("\\.").length && e.getKey().equals(key.split("\\.")[i])) {
                    i++;
                    if (e.getValue() instanceof String) {
                        if (i == key.split("\\.").length) {
                            o = e.getValue();
                            return o;
                        }
                    } else if (e.getValue() instanceof LinkedHashMap) {
                        if (i == key.split("\\.").length) {
                            if (type == TypeEnum.map) {
                                o = (LinkedHashMap) e.getValue();
                                return o;
                            }
                        } else {
                            o = getObject((LinkedHashMap) e.getValue(), key, type);
                        }
                        return o;
                    } else if (e.getValue() instanceof ArrayList) {
                        if (i == key.split("\\.").length) {
                            if (type == TypeEnum.arrayList) {
                                o = (ArrayList) e.getValue();
                                return o;
                            }
                            if (type == TypeEnum.arrayMap) {
                                o = (ArrayList<Map>) e.getValue();
                                return o;
                            }
                        } else {
                            o = getObject((ArrayList) e.getValue(), key, type);
                        }
                        return o;
                    }
                }
            }
        }
        //数组层级递归遍历解析
        if (m instanceof List || m instanceof ArrayList) {
            ls = (ArrayList) m;
            for (int i = 0; i < ls.size(); i++) {
                if (ls.get(i) instanceof LinkedHashMap) {
                    if (i == key.split("\\.").length) {
                        if (type == TypeEnum.map) {
                            o = (LinkedHashMap) ls.get(i);
                            return o;
                        }
                    } else {
                        o = getObject((LinkedHashMap) ls.get(i), key, type);
                    }
                    return o;
                } else if (ls.get(i) instanceof ArrayList) {
                    if (i == key.split("\\.").length) {
                        if (type == TypeEnum.arrayList) {
                            o = (ArrayList) ls.get(i);
                            return o;
                        }
                        if (type == TypeEnum.arrayMap) {
                            o = (ArrayList<Map>) ls.get(i);
                            return o;
                        }
                    } else {
                        o = getObject((ArrayList) ls.get(i), key, type);
                    }
                    return o;
                }
            }
        }
        return o;
    }

    public final static boolean isJSONValid(String jsonInString ) {
        try {
            DEFAULT_MAPPER.readTree(jsonInString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /*
     * Json数据解析返回数据类型枚举
     */
    public enum TypeEnum {
        /**
         * 单纯的键值对，通过key获取valus
         */
        string,
        /**
         * 通过key获取到Map对象
         */
        map,
        /**
         * 通过key获取到ArrayList数组
         */
        arrayList,
        /**
         * 通过key获取到ArrayMap数组对象
         */
        arrayMap;
    }
}
