package com.exam.controller.exam;

import com.exam.base.RoleCheck;
import com.exam.base.privilege.Role;
import com.exam.domain.dto.question.PaperAnswerParam;
import com.exam.domain.dto.question.AnswerScoreParam;
import com.exam.domain.dto.question.PaperScoreParam;
import com.exam.exception.LocalException;
import com.exam.response.BaseResponse;
import com.exam.service.question.PaperAnswerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 试卷批改操作类
 *
 * @author sunc
 * @date 2020/3/15 14:39
 */
@RestController
@RequestMapping("/check")
public class CheckController {

    @Resource
    private PaperAnswerService paperAnswerService;

    /**
     * 获取学生答卷详情
     */
    @ResponseBody
    @GetMapping("/paper/detail")
    @RoleCheck({Role.TEACHER, Role.MANAGER})
    public BaseResponse detail(HttpServletRequest request) throws LocalException {
        PaperAnswerParam param = PaperAnswerParam.select(request);
        return BaseResponse.success(paperAnswerService.selectDetail(param));
    }

    /**
     * 批改题目给分
     */
    @ResponseBody
    @PostMapping("/question/score")
    @RoleCheck({Role.TEACHER, Role.MANAGER})
    public BaseResponse questionScore(@RequestBody String data) throws LocalException {
        AnswerScoreParam param = AnswerScoreParam.build(data);
        paperAnswerService.score(param);
        return BaseResponse.success();
    }

    /**
     * 统计某学生的成绩
     */
    @ResponseBody
    @PostMapping("/paper/score")
    @RoleCheck({Role.TEACHER, Role.MANAGER})
    public BaseResponse paperScore(@RequestBody String data) throws LocalException {
        PaperScoreParam param = PaperScoreParam.build(data);
        return BaseResponse.success(paperAnswerService.countScore(param));
    }

}
