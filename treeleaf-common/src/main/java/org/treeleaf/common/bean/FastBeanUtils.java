package org.treeleaf.common.bean;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 提供快速操作javabean的方法<br>
 * 在apache beanutils上进行了扩展,对一些beanutils上存在的bug进行修复,比如beanutils.populate在大并发下内存泄漏的问题
 *
 * @author leaf
 * @date 2014-3-20 下午8:59:54
 */
public class FastBeanUtils extends BeanUtils {

    /**
     * 缓存bean信息*
     */
    private static FastBeanCache fastBeanCache = new FastBeanCache();

    protected static FastBeanCache getFastBeanCache() {
        return fastBeanCache;
    }

    /**
     * 将Map的值装入指定的对象中,对象由 classz 类型决定
     *
     * @param classz     对象类型
     * @param properties map值
     * @return 返回装入后生成的对象
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     * @throws IllegalArgumentException
     */
    public static <T> T fastPopulate(Class<T> classz, Map<String, Object> properties) {
        try {
            Object obj = classz.newInstance();
            fastPopulate(obj, properties);
            return (T) obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将Map的值装入指定的对象中
     *
     * @param obj        对象
     * @param properties map值
     * @throws java.lang.reflect.InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static void fastPopulate(Object obj, Map<String, Object> properties) {
        Class<?> classz = obj.getClass();
        Map<String, PropertiesEntry> map = getFastBeanCache().getPropertiesEntryMap(classz);
        if (map.size() > 0) {
            try {
                for (Map.Entry<String, Object> entry : properties.entrySet()) {

                    PropertiesEntry pe = map.get(entry.getKey());
                    if (pe != null) {
                        if (pe.getSet() != null) {
                            if (entry.getValue() != null) {
                                Object v = ConvertUtils.convert(entry.getValue(), pe.getType());
                                pe.getSet().invoke(obj, v);
                            } else {
                                entry.setValue(null);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 对Field进行设置,如果入参value的类型跟Field的类型不匹配,会尝试进行转换
     *
     * @param field
     * @param obj
     * @param v
     */
    public static void setFieldValue(Field field, Object obj, Object v) {
        field.setAccessible(true);
        try {
            Object value = ConvertUtils.convert(v, field.getType());
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取Field的值
     *
     * @param field
     * @param obj
     * @return
     */
    public static Object getFieldValue(Field field, Object obj) {
        field.setAccessible(true);
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
