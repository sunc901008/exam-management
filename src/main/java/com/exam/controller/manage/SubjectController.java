package com.exam.controller.manage;

import com.alibaba.fastjson.JSONArray;
import com.exam.base.RoleCheck;
import com.exam.base.privilege.Role;
import com.exam.domain.PageParam;
import com.exam.domain.dto.manage.SubjectParam;
import com.exam.exception.LocalException;
import com.exam.response.BaseResponse;
import com.exam.service.manage.SubjectService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 学科相关信息控制类
 *
 * @author sunc
 * @date 2020/3/8 12:08
 */

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Resource
    private SubjectService subjectService;

    /**
     * 获取学科列表
     */
    @ResponseBody
    @GetMapping("/list")
    @RoleCheck(Role.MANAGER)
    public BaseResponse list(HttpServletRequest request) {
        PageParam pageParam = new PageParam(request);
        SubjectParam subjectParam = SubjectParam.select(request);
        return BaseResponse.success(subjectService.list(pageParam, subjectParam));
    }

    /**
     * 删除(批量)学科
     */
    @ResponseBody
    @DeleteMapping("/del")
    @RoleCheck(Role.MANAGER)
    public BaseResponse deletes(@RequestBody String data) {
        List<Long> subIds = JSONArray.parseArray(data, Long.class);
        subjectService.deletes(subIds);
        return BaseResponse.success();
    }

    /**
     * 新增学科
     */
    @ResponseBody
    @PutMapping("/add")
    @RoleCheck(Role.MANAGER)
    public BaseResponse add(@RequestBody String data) throws LocalException {
        SubjectParam subjectParam = SubjectParam.build(data);
        return BaseResponse.success(subjectService.insert(subjectParam));
    }

    /**
     * 修改学科信息
     */
    @ResponseBody
    @PostMapping("/update")
    @RoleCheck(Role.MANAGER)
    public BaseResponse update(@RequestBody String data) throws LocalException {
        SubjectParam subjectParam = SubjectParam.build(data);
        return BaseResponse.success(subjectService.update(subjectParam));
    }

}
