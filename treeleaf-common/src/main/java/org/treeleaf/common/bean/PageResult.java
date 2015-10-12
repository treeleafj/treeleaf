package org.treeleaf.common.bean;

import java.util.List;

/**
 * 分页结果
 *
 * @Author leaf
 * 2015/10/12 0012 22:30.
 */
public class PageResult {

    /**
     * 每一页面的大小
     */
    private Integer size;

    /**
     * 开始行号,从0开始
     */
    private Long start;

    /**
     * 查出来的数据
     */
    private List list;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 构造分页查询结果对象
     *
     * @param start 开始行号
     * @param size  分页大小
     * @param list  查询的结果数据
     */
    public PageResult(Long start, int size, List list) {
        this.size = size;
        this.start = start;
        this.list = list;
    }

    /**
     * 构造分页查询结果对象
     *
     * @param start 开始行号
     * @param size  分页大小
     * @param list  查询的结果数据
     * @param total 总条数
     */
    public PageResult(Long start, int size, List list, long total) {
        this.size = size;
        this.start = start;
        this.list = list;
        this.total = total;
    }

    /**
     * 获取当前的页码
     *
     * @return
     */
    public Long getPageNum() {
        if (start != null) {
            return (start / 10) + 1;
        }
        return null;
    }

    /**
     * 获取总分页数
     *
     * @return
     */
    public Long getTotalPage() {
        if (total != null && size != null) {
            long l = total / size;
            return total % size == 0 ? l : l + 1;
        }
        return null;
    }

    public Integer getSize() {
        return size;
    }

    public Long getStart() {
        return start;
    }

    public List getList() {
        return list;
    }

    public Long getTotal() {
        return total;
    }

}
