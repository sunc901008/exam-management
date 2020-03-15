package com.exam.controller.manage;

import com.alibaba.fastjson.JSONArray;
import com.exam.base.RoleCheck;
import com.exam.base.privilege.Role;
import com.exam.domain.PageParam;
import com.exam.domain.dto.manage.ClazzParam;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import com.exam.exception.msg.DependentMsg;
import com.exam.response.BaseResponse;
import com.exam.service.manage.ClazzService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 班级相关信息控制类
 *
 * @author sunc
 * @date 2020/3/8 12:08
 */

@RestController
@RequestMapping("/clazz")
public class ClazzController {

    @Resource
    private ClazzService clazzService;

    /**
     * 获取班级列表(按照年级返回列表)
     */
    @ResponseBody
    @GetMapping("/list")
    @RoleCheck(Role.MANAGER)
    public BaseResponse list(HttpServletRequest request) {
        PageParam pageParam = new PageParam(request);
        ClazzParam clazzParam = ClazzParam.select(request);
        return BaseResponse.success(clazzService.list(pageParam, clazzParam));
    }

    /**
     * 删除(批量)班级
     */
    @ResponseBody
    @DeleteMapping("/del")
    @RoleCheck(Role.MANAGER)
    public BaseResponse deletes(@RequestBody String data) throws LocalException {
        List<Long> clsIds = JSONArray.parseArray(data, Long.class);
        JSONArray names = clazzService.deletes(clsIds);
        if (!names.isEmpty()) {
            DependentMsg msgParam = new DependentMsg();
            msgParam.setNames(names);
            throw new LocalException(ExceptionCode.DEPENDENCY_ERROR, "Some Dependent.", msgParam.toJson());
        }
        return BaseResponse.success();
    }

    /**
     * 新增班级
     */
    @ResponseBody
    @PutMapping("/add")
    @RoleCheck(Role.MANAGER)
    public BaseResponse add(@RequestBody String data) throws LocalException {
        ClazzParam clazzParam = ClazzParam.build(data);
        return BaseResponse.success(clazzService.insert(clazzParam));
    }

}
