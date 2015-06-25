package org.treeleaf.common.bean;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * JavaBean信息操作
 *
 * @author leaf
 * @date 2014-3-20 下午10:00:44
 */
public abstract class BeanInfoUtils {

    /**
     * 判断一个方式是否private修饰的
     *
     * @param m
     * @return
     */
    public static boolean isPrivate(Method m) {
        return (m.getModifiers() & Modifier.PRIVATE) != 0;
    }

    /**
     * 判断一个方式是否protected修饰的
     *
     * @param m
     * @return
     */
    public static boolean isProtected(Method m) {
        return (m.getModifiers() & Modifier.PROTECTED) != 0;
    }

    /**
     * 判断一个方式是否public修饰的
     *
     * @param m
     * @return
     */
    public static boolean isPublic(Method m) {
        return (m.getModifiers() & Modifier.PUBLIC) != 0;
    }

    /**
     * 判断一个方式是否static修饰的
     *
     * @param m
     * @return
     */
    public static boolean isStatic(Method m) {
        return (m.getModifiers() & Modifier.STATIC) != 0;
    }

    /**
     * 判断一个方式是否abstract修饰的
     *
     * @param m
     * @return
     */
    public static boolean isAbstract(Method m) {
        return (m.getModifiers() & Modifier.ABSTRACT) != 0;
    }

    /**
     * 判断一个方式是否final修饰的
     *
     * @param m
     * @return
     */
    public static boolean isFinal(Method m) {
        return (m.getModifiers() & Modifier.FINAL) != 0;
    }

    /**
     * 判断一个方式是否synchronized修饰的
     *
     * @param m
     * @return
     */
    public static boolean isSynchronized(Method m) {
        return (m.getModifiers() & Modifier.SYNCHRONIZED) != 0;
    }

    /**
     * 判断一个方式是否set方法
     *
     * @param m
     * @return
     */
    public static boolean isSet(Method m) {
        return m.getName().startsWith("set") && isPublic(m) && !isStatic(m) && getParamLength(m) == 1;
    }

    /**
     * 判断一个方式是否get方法
     *
     * @param m
     * @return
     */
    public static boolean isGet(Method m) {
        if (isPublic(m) && !isStatic(m) && getParamLength(m) == 0 && !isReturnVoid(m)) {
            if (m.getReturnType() == boolean.class) {
                if (m.getName().startsWith("is")) {
                    return true;
                }
            }
            return m.getName().startsWith("get");
        }
        return false;
    }

    /**
     * 获得一个方式的入参个数
     *
     * @param m
     * @return
     */
    public static int getParamLength(Method m) {
        return m.getParameterTypes().length;
    }

    /**
     * 判断一个方法的返回值是否void
     *
     * @param m
     * @return
     */
    public static boolean isReturnVoid(Method m) {
        return (m.getReturnType() == void.class);
    }

    /**
     * 通过set方法得到对应的属性的名字
     *
     * @param m
     * @return
     */
    public static String getPropertiesNameBySet(Method m) {
        String name = m.getName().substring(3);
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    /**
     * 通过get方法得到对应的属性的名字
     *
     * @param m
     * @return
     */
    public static String getPropertiesNameByGet(Method m) {
        String name;
        if (m.getReturnType() == boolean.class && m.getName().startsWith("is")) {
            name = m.getName().substring(2);
        } else {
            name = m.getName().substring(3);
        }
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    /**
     * 通过get方法得到对应的属性的类型
     *
     * @param m
     * @return
     */
    public static Class<?> getPropertiesTypeByGet(Method m) {
        return m.getReturnType();
    }

    /**
     * 通过set方法得到对应的属性的类型
     *
     * @param m
     * @return
     */
    public static Class<?> getPropertiesTypeBySet(Method m) {
        return m.getParameterTypes()[0];
    }
}
