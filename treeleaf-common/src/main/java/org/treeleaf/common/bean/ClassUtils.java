package org.treeleaf.common.bean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 类工具
 *
 * @author leaf
 */
@SuppressWarnings("rawtypes")
public class ClassUtils {

    /**
     * 当前类的泛型类型
     */
    public static Class getGeneric(Class classType) {
        Type type = (Type) classType.getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) type;
        Class classz = ((Class) pt.getActualTypeArguments()[0]);
        return classz;
    }

    /**
     * 当前类的泛型类型
     */
    public static Class[] getGenerics(Class classType) {
        Type type = (Type) classType.getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) type;
        Type[] types = pt.getActualTypeArguments();
        if (types[0] instanceof Class) {
            Class[] classz = new Class[types.length];
            for (int i = 0; i < classz.length; i++) {
                classz[i] = (Class) types[i];
            }
            return classz;
        } else {
            return null;
        }
    }

    /**
     * 得到指定对象的beanname(默认为类名首字母小写)
     *
     * @param obj
     */
    public static String getSimpleBeanName(Object obj) {
        String name;
        if (obj instanceof Class) {
            name = ((Class) obj).getSimpleName();
        } else {
            name = obj.getClass().getSimpleName();
        }
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }


}
