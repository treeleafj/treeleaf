package org.treeleaf.web;

/**
 * 返回html页面的结果
 * <p/>
 * Created by yaoshuhong on 2015/4/29.
 */
public class Html implements Result {

    /**
     * 页面上的数据,采用el表达式之类访问的时候:${model.xxx}
     */
    private Object model;

    public Html() {
    }

    public Html(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Html{" +
                "model=" + model +
                '}';
    }
}
