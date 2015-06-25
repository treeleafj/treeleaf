package org.treeleaf.common.bean;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class FastBeanCache {


    /**
     * 缓存对象的method
     */
    private Map<Class<?>, Map<String, PropertiesEntry>> cache = new HashMap<Class<?>, Map<String, PropertiesEntry>>();


    /**
     * 获得指定类型的get/set方法集合
     *
     * @param classz
     * @return
     */
    public Map<String, PropertiesEntry> getPropertiesEntryMap(Class<?> classz) {
        Map<String, PropertiesEntry> methods = this.cache.get(classz);
        if (methods == null) {

            synchronized (classz) {

                methods = this.cache.get(classz);
                //保证线程同步性
                if (methods == null) {

                    methods = new HashMap<String, PropertiesEntry>();

                    Method[] ms = classz.getMethods();

                    for (Method m : ms) {
                        if (BeanInfoUtils.isGet(m)) {

                            String name = BeanInfoUtils.getPropertiesNameByGet(m);
                            PropertiesEntry entry = methods.get(name);

                            if (entry == null) {
                                Class<?> type = BeanInfoUtils.getPropertiesTypeByGet(m);
                                entry = new PropertiesEntry(name, type);
                                entry.setGet(m);
                                methods.put(name, entry);
                            }
                            entry.setGet(m);

                        } else if (BeanInfoUtils.isSet(m)) {

                            String name = BeanInfoUtils.getPropertiesNameBySet(m);
                            PropertiesEntry entry = methods.get(name);

                            if (entry == null) {
                                Class<?> type = BeanInfoUtils.getPropertiesTypeBySet(m);
                                entry = new PropertiesEntry(name, type);
                                entry.setSet(m);
                                methods.put(name, entry);
                            }
                            entry.setSet(m);
                        }
                    }

                    this.cache.put(classz, methods);
                }
            }
        }
        return methods;
    }


}
