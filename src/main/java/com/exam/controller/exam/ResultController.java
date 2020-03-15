package com.exam.controller.exam;

import cn.hutool.core.util.StrUtil;
import com.exam.base.RoleCheck;
import com.exam.base.privilege.Role;
import com.exam.domain.PageParam;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import com.exam.response.BaseResponse;
import com.exam.service.question.PaperAnswerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 试卷结果操作类
 *
 * @author sunc
 * @date 2020/3/15 14:39
 */
@RestController
@RequestMapping("/result")
public class ResultController {

    @Resource
    private PaperAnswerService paperAnswerService;

    /**
     * 获取学生答卷详情
     */
    @ResponseBody
    @GetMapping("/paper/detail")
    public BaseResponse detail(HttpServletRequest request) throws LocalException {
        String paperId = request.getParameter("paperId");
        if (StrUtil.isBlank(paperId)) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "paperId:" + paperId);
        }
        return BaseResponse.success(paperAnswerService.selectDetail(Long.parseLong(paperId.trim())));
    }

    /**
     * 获取学生成绩排名(按照班级或者年级)
     */
    @ResponseBody
    @GetMapping("/paper/sort")
    @RoleCheck({Role.TEACHER, Role.MANAGER})
    public BaseResponse sort(HttpServletRequest request) throws LocalException {
        PageParam pageParam = new PageParam(request);

        String paperIdStr = request.getParameter("paperId");
        if (StrUtil.isBlank(paperIdStr)) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "paperId:" + paperIdStr);
        }
        Long paperId = Long.parseLong(paperIdStr.trim());

        String gradeIdStr = request.getParameter("gradeId");
        if (StrUtil.isNotBlank(gradeIdStr)) {
            // 年级排名
            return BaseResponse.success(paperAnswerService.gradeSort(pageParam, paperId, Long.parseLong(gradeIdStr.trim())));
        }
        String classId = request.getParameter("classId");
        if (StrUtil.isNotBlank(classId)) {
            // 班级排名
            return BaseResponse.success(paperAnswerService.classSort(pageParam, paperId, Long.parseLong(classId.trim())));
        }
        throw new LocalException(ExceptionCode.PARAM_ERROR);
    }

}
