package org.treeleaf.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * key-value的入参封装,主要为了解决spring mvc不支持map入参的问题
 *
 * @Author leaf
 * 2015/9/4 0004 13:35.
 */
public class Param {

    private Map<String, String> map = new HashMap<>();

    public Param(Map<String, String> map) {
        this.map = map;
    }

    public String get(String p) {
        return this.map.get(p);
    }

    public Set<String> names() {
        return map.keySet();
    }

    public Map<String, String> asMap() {
        return new HashMap<>(this.map);
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
}
