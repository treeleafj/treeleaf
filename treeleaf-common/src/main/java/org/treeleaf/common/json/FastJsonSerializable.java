package org.treeleaf.common.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * @author yaoshuhong
 * @date 2016-10-17 17:57
 */
public class FastJsonSerializable implements JosnSerializable {

    {
        // 时间类型转换
        SerializeConfig.getGlobalInstance().put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 添加类型转换
     *
     * @param classz     要转换的类型
     * @param serializer 转换器
     */
    public static void addSerializeConfig(Class<?> classz, ObjectSerializer serializer) {
        SerializeConfig.getGlobalInstance().put(classz, serializer);
    }

    /**
     * 添加类型解析
     *
     * @param type         要解析的类型
     * @param deserializer 解析器
     */
    public static void addDeserializerConfig(Type type, ObjectDeserializer deserializer) {
        ParserConfig.getGlobalInstance().getGlobalInstance().putDeserializer(type, deserializer);
    }

    /**
     * 将一个对象转为json字符窜 对象最好能继承IJSONObject(可防止相互引用的循环递归)
     */
    public String toJson(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 将一个json字符窜转为对象
     */
    public <T> T toObj(String json, Class<T> classz) {
        return JSON.parseObject(json, classz, new Feature[0]);
    }

    /**
     * 将一个json字符窜转为对象数组
     */
    public <T> List<T> toArray(String json, Class<T> classz) {
        return JSON.parseArray(json, classz);
    }

    /**
     * 将一个json字符窜转为对象
     *
     * @param typeReference 返回类型,例如 new TypeReference<Map<String, User>>() {},或者 new TypeReference<List<String, User>>() {}
     */
    public <T> T toObj(String json, TypeReference<T> typeReference) {
        return JSON.parseObject(json, typeReference);
    }

    /**
     * 将一个json字符窜转为对象数组 [{header类型}, {body类型}],Type[] types = new Type[]
     * {Header.class, Body.class}; (Header) list.get(0);(Body) list.get(1);
     */
    public List<Object> toArray(String json, Type[] types) {
        return JSON.parseArray(json, types);
    }

}
