package com.exam.controller.manage;

import com.alibaba.fastjson.JSONArray;
import com.exam.base.RoleCheck;
import com.exam.base.privilege.Role;
import com.exam.domain.PageParam;
import com.exam.domain.dto.manage.GradeParam;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import com.exam.exception.msg.DependentMsg;
import com.exam.response.BaseResponse;
import com.exam.service.manage.GradeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 年级相关信息控制类
 *
 * @author sunc
 * @date 2020/3/8 12:08
 */

@RestController
@RequestMapping("/grade")
public class GradeController {

    @Resource
    private GradeService gradeService;

    /**
     * 获取年级列表
     */
    @ResponseBody
    @GetMapping("/list")
    @RoleCheck(Role.MANAGER)
    public BaseResponse list(HttpServletRequest request) {
        PageParam pageParam = new PageParam(request);
        return BaseResponse.success(gradeService.list(pageParam));
    }

    /**
     * 删除(批量)班年级
     */
    @ResponseBody
    @DeleteMapping("/del")
    @RoleCheck(Role.MANAGER)
    public BaseResponse deletes(@RequestBody String data) throws LocalException {
        List<Long> clsIds = JSONArray.parseArray(data, Long.class);
        JSONArray names = gradeService.deletes(clsIds);
        if (!names.isEmpty()) {
            DependentMsg msgParam = new DependentMsg();
            msgParam.setNames(names);
            throw new LocalException(ExceptionCode.DEPENDENCY_ERROR, "Some Dependent.", msgParam.toJson());
        }
        return BaseResponse.success();
    }

    /**
     * 新增年级
     */
    @ResponseBody
    @PutMapping("/add")
    @RoleCheck(Role.MANAGER)
    public BaseResponse add(@RequestBody String data) throws LocalException {
        GradeParam gradeParam = GradeParam.build(data);
        return BaseResponse.success(gradeService.insert(gradeParam));
    }

}
