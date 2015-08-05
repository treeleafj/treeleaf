package org.treeleaf.common.http;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by yaoshuhong on 2015/6/29.
 */
public class HttpHeader implements Iterable<String> {

    private Map<String, String> header = new HashMap<>();

    public void addHeader(String name, String val) {
        this.header.put(name, val);
    }

    public String getHeader(String name) {
        return this.header.get(name);
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> head) {
        this.header = head;
    }

    @Override
    public Iterator<String> iterator() {
        return header.keySet().iterator();
    }

}
