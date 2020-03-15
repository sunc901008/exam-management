package com.exam.controller.exam;

import com.exam.domain.PageParam;
import com.exam.domain.dto.question.PaperAnswerParam;
import com.exam.domain.dto.question.PaperParam;
import com.exam.exception.LocalException;
import com.exam.response.BaseResponse;
import com.exam.service.question.PaperAnswerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 试卷操作类
 *
 * @author sunc
 * @date 2020/3/15 14:39
 */
@RestController
@RequestMapping("/exam")
public class ExamController {

    @Resource
    private PaperAnswerService paperAnswerService;

    /**
     * 学生获取试卷列表
     */
    @ResponseBody
    @GetMapping("/paper/list")
    public BaseResponse list(HttpServletRequest request) {
        PageParam pageParam = new PageParam(request);
        PaperParam param = PaperParam.select(request);
        return BaseResponse.success(paperAnswerService.publishList(pageParam, param));
    }

    /**
     * 学生获取试卷详情
     */
    @ResponseBody
    @GetMapping("/paper/{id}")
    public BaseResponse detail(@PathVariable("id") Long id) throws LocalException {
        return BaseResponse.success(paperAnswerService.selectPublishDetail(id));
    }

    /**
     * 学生提交试卷
     */
    @ResponseBody
    @PutMapping("/paper/submit")
    public BaseResponse submit(@RequestBody String data) throws LocalException {
        PaperAnswerParam param = PaperAnswerParam.build(data);
        paperAnswerService.submit(param);
        return BaseResponse.success();
    }

}
