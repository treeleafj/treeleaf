package org.treeleaf.common.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericUtils {

	private static Logger log = LoggerFactory.getLogger(GenericUtils.class);

	/**
	 * 当前类的泛型类型
	 * @param obj
	 * @return
	 */
	public static Class getGenericFirst(Object obj){
		Type type = (Type) obj.getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) type;
		Class classz = ((Class) pt.getActualTypeArguments()[0]);
		return classz;
	}

	/**
	 * 当前类的泛型类型
	 * @param obj
	 * @return
	 */
	public static Class getGeneric(Object obj, int index){
		Type type = (Type) obj.getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) type;
		log.info("pt:" + pt);
		Class classz = ((Class) pt.getActualTypeArguments()[index]);
		return classz;
	}
	
}
