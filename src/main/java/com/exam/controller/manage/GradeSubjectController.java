package com.exam.controller.manage;

import cn.hutool.core.util.StrUtil;
import com.exam.base.RoleCheck;
import com.exam.base.privilege.Role;
import com.exam.domain.dto.manage.GradeSubjectParam;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import com.exam.response.BaseResponse;
import com.exam.service.manage.GradeSubjectService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 年级学科控制类, 如给某年级开设某课程
 *
 * @author sunc
 * @date 2020/3/8 14:19
 */
@RestController
@RequestMapping("/grade/subject")
public class GradeSubjectController {

    @Resource
    private GradeSubjectService gradeSubjectService;

    /**
     * 按照班级获取当前开设的科目/按照科目获取当前开设该科目的班级(根据参数不同)
     */
    @ResponseBody
    @GetMapping("/list")
    @RoleCheck(Role.MANAGER)
    public BaseResponse list(HttpServletRequest request) throws LocalException {
        String gradeId = request.getParameter("gradeId");
        String subjectId = request.getParameter("subjectId");
        Object object;
        if (StrUtil.isNotBlank(gradeId)) {
            object = gradeSubjectService.getSubjectByGrade(Long.parseLong(gradeId));
        } else if (StrUtil.isNotBlank(subjectId)) {
            object = gradeSubjectService.getClassBySubject(Long.parseLong(subjectId));
        } else {
            throw new LocalException(ExceptionCode.PARAM_ERROR, String.format("gradeId:%s subjectId:%s", gradeId, subjectId));
        }
        return BaseResponse.success(object);
    }

    /**
     * 给某些年级开设某些课程
     */
    @ResponseBody
    @PutMapping("/add")
    @RoleCheck(Role.MANAGER)
    public BaseResponse add(@RequestBody String data) throws LocalException {
        GradeSubjectParam param = GradeSubjectParam.build(data);
        gradeSubjectService.gradeSetSubject(param);
        return BaseResponse.success();
    }

    /**
     * 给某些年级取消某些课程
     */
    @ResponseBody
    @DeleteMapping("/del")
    @RoleCheck(Role.MANAGER)
    public BaseResponse del(@RequestBody String data) throws LocalException {
        GradeSubjectParam param = GradeSubjectParam.build(data);
        gradeSubjectService.gradeDelSubject(param);
        return BaseResponse.success();
    }

}
