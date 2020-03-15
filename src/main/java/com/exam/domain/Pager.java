package com.exam.domain;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 分页
 *
 * @author sunc
 * @date 2020/3/7 15:39
 */
@Getter
@Setter
public class Pager<T> {
    /**
     * 数据列表
     */
    private List<T> datas;
    /**
     * 数据总数
     */
    private long total;
    /**
     * 当前页
     */
    private long currentPage;
    /**
     * 每页数据量
     */
    private long pageSize;
    /**
     * 总页数
     */
    private long totalPage;

    public Pager(PageParam pageParam) {
        this.total = 0;
        this.currentPage = pageParam.getNumber();
        this.pageSize = pageParam.getSize();
        this.totalPage = 0;
        this.datas = CollectionUtil.newArrayList();
    }

    public Pager(IPage page, List<T> datas) {
        this.total = page.getTotal();
        this.currentPage = page.getCurrent();
        this.pageSize = page.getSize();
        this.totalPage = page.getPages();
        this.datas = datas;
    }

    public Pager(PageParam pageParam, List<T> datas) {
        this.total = datas.size();
        this.currentPage = pageParam.getNumber();
        this.pageSize = pageParam.getSize();

        this.totalPage = this.total / this.pageSize + (total % pageSize > 0 ? 1 : 0);

        long start = (currentPage - 1) * pageSize;
        start = start > 0 ? start : 0;
        long end = Math.min(currentPage * pageSize, total);
        this.datas = datas.subList((int) start, (int) end);
    }

}
