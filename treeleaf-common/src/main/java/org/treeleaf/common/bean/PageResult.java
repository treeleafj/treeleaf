package org.treeleaf.common.bean;

import java.util.List;

/**
 * 分页结果
 *
 * @Author leaf
 * 2015/10/12 0012 22:30.
 */
public class PageResult<T> {

    private Pageable pageable;

    /**
     * 查出来的数据
     */
    private List<T> list;

    /**
     * 总条数
     */
    private long total;

    /**
     * 构造分页查询结果对象
     *
     * @param pageable 分页请求
     * @param list     查询的结果数据
     */
    public PageResult(Pageable pageable, List<T> list) {
        this(pageable, list, Long.valueOf(String.valueOf(list.size())));
    }

    /**
     * 构造分页查询结果对象
     *
     * @param pageable 分页请求
     * @param list     查询的结果数据
     * @param total    总条数
     */
    public PageResult(Pageable pageable, List<T> list, long total) {
        this.pageable = pageable;
        this.list = list;
        this.total = total;
    }

    /**
     * 构造分页查询结果对象
     *
     * @param pageNo   页码
     * @param pageSize 每页大小
     * @param list     查询的结果数据
     */
    public PageResult(int pageNo, int pageSize, List<T> list) {
        this(new Pageable(pageNo, pageSize), list);
    }

    /**
     * 构造分页查询结果对象
     *
     * @param pageNo   页码
     * @param pageSize 每页大小
     * @param list     查询的结果数据
     * @param total    总条数
     */
    public PageResult(int pageNo, int pageSize, List<T> list, long total) {
        this(new Pageable(pageNo, pageSize), list, total);
    }

    /**
     * 获取当前页码
     *
     * @return
     */
    public int getPageNo() {
        return this.pageable.getPageNo();
    }

    /**
     * 获取总分页数
     *
     * @return
     */
    public long getTotalPage() {
        long l = total / this.pageable.getPageSize();
        return total % this.pageable.getPageSize() == 0 ? l : l + 1;
    }

    /**
     * 获取每页大小
     *
     * @return
     */
    public int getPageSize() {
        return this.pageable.getPageSize();
    }

    /**
     * 获取起始偏移量(从0开始)
     *
     * @return
     */
    public int getOffset() {
        return this.pageable.getOffset();
    }

    /**
     * 获取数据
     *
     * @return
     */
    public List<T> getList() {
        return list;
    }

    public long getTotal() {
        return total;
    }

}
