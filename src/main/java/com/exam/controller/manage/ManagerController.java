package com.exam.controller.manage;

import com.alibaba.fastjson.JSONArray;
import com.exam.domain.PageParam;
import com.exam.domain.dto.manage.ManagerParam;
import com.exam.exception.LocalException;
import com.exam.response.BaseResponse;
import com.exam.service.manage.ManagerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 管理员相关信息控制类, admin 用户才能操作
 *
 * @author sunc
 * @date 2020/3/8 12:08
 */

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Resource
    private ManagerService managerService;

    /**
     * 获取管理员列表
     */
    @ResponseBody
    @GetMapping("/list")
    public BaseResponse list(HttpServletRequest request) {
        PageParam pageParam = new PageParam(request);
        ManagerParam managerParam = ManagerParam.select(request);
        return BaseResponse.success(managerService.list(pageParam, managerParam));
    }

    /**
     * 删除(批量)管理员
     */
    @ResponseBody
    @DeleteMapping("/del")
    public BaseResponse deletes(@RequestBody String data) {
        List<Long> mgrIds = JSONArray.parseArray(data, Long.class);
        managerService.deletes(mgrIds);
        return BaseResponse.success();
    }

    /**
     * 新增管理员
     */
    @ResponseBody
    @PutMapping("/add")
    public BaseResponse add(@RequestBody String data) throws LocalException {
        ManagerParam managerParam = ManagerParam.build(data);
        return BaseResponse.success(managerService.insert(managerParam));
    }

    /**
     * 修改管理员信息
     */
    @ResponseBody
    @PostMapping("/update")
    public BaseResponse update(@RequestBody String data) throws LocalException {
        ManagerParam managerParam = ManagerParam.build(data);
        return BaseResponse.success(managerService.update(managerParam));
    }

}
