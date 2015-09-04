package org.treeleaf.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author leaf
 * 2015/9/4 0004 13:35.
 */
public class ParamMap {

    private Map<String, String> map = new HashMap<>();

    public ParamMap(Map<String, String> map) {
        this.map = map;
    }

    public String get(String p) {
        return this.map.get(p);
    }

    public Set<String> names() {
        return map.keySet();
    }

    public Map asMap() {
        return new HashMap<>(this.map);
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
}
