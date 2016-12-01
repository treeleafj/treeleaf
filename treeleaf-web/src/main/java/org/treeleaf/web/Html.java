package org.treeleaf.web;

/**
 * 返回html页面的结果
 * <p>
 * Created by yaoshuhong on 2015/4/29.
 */
public class Html implements Result {

    private String path;

    /**
     * 页面上的数据,采用el表达式之类访问的时候:${model.xxx}
     */
    private Object model;

    private boolean isRoot = false;

    public Html() {
    }

    public Html(Object model) {
        this.model = model;
    }

    public Html(String path, Object model) {
        this.path = path;
        this.model = model;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public Html setRoot(boolean root) {
        isRoot = root;
        return this;
    }

    @Override
    public String toString() {
        return "Html{" +
                "path='" + path + '\'' +
                ", model=" + model +
                ", isRoot=" + isRoot +
                '}';
    }
}
