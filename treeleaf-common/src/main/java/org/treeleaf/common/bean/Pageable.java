package org.treeleaf.common.bean;

import org.treeleaf.common.safe.Assert;

/**
 * 分页请求对象
 *
 * @Author leaf
 * 2015/10/28 0028 23:04.
 */
public class Pageable {

    /**
     * 页码,从1开始
     */
    private int pageNo;

    /**
     * 每页大小,从1开始
     */
    private int pageSize;

    public Pageable() {
    }

    public Pageable(int pageNo, int pageSize) {

        Assert.numberLessOrEqual(pageNo, 0, "pageNo不能小于1");
        Assert.numberLessOrEqual(pageSize, 0, "pageSize不能小于1");

        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return (this.pageNo - 1) * this.pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
