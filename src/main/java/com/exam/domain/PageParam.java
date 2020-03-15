package com.exam.domain;

import cn.hutool.core.util.StrUtil;
import com.exam.base.Constant;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sunc
 * @date 2020/3/7 16:04
 */

@Getter
public class PageParam {

    private Integer size = Constant.PAGE_SIZE;
    private Integer number = Constant.PAGE_NUMBER;

    public PageParam(HttpServletRequest request) {
        String pageSize = request.getParameter("pageSize");
        if (StrUtil.isNotBlank(pageSize)) {
            size = size(pageSize);
        }
        String pageNum = request.getParameter("pageNum");
        if (StrUtil.isNotBlank(pageNum)) {
            number = number(pageNum);
        }
    }

    private int size(String pageSize) {
        try {
            int size = Integer.parseInt(pageSize);
            if (size <= 0) {
                return Constant.PAGE_SIZE;
            }
            return size > Constant.MAX_PAGE_SIZE ? Constant.PAGE_SIZE : size;
        } catch (Exception ignored) {
            return Constant.PAGE_SIZE;
        }
    }

    private int number(String pageNum) {
        try {
            int number = Integer.parseInt(pageNum);
            return number <= 0 ? Constant.PAGE_NUMBER : number;
        } catch (Exception ignored) {
            return Constant.PAGE_SIZE;
        }
    }

}
