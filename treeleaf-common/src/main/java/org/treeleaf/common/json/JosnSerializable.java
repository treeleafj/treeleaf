package org.treeleaf.common.json;

import com.alibaba.fastjson.TypeReference;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author yaoshuhong
 * @date 2016-10-17 17:58
 */
public interface JosnSerializable {

    String toJson(Object obj);

    <T> T toObj(String json, Class<T> classz);

    <T> List<T> toArray(String json, Class<T> classz);

    <T> T toObj(String json, TypeReference<T> typeReference);

    List<Object> toArray(String json, Type[] types);

}
