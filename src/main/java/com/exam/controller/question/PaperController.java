package com.exam.controller.question;

import com.alibaba.fastjson.JSONArray;
import com.exam.base.RoleCheck;
import com.exam.base.privilege.Role;
import com.exam.domain.PageParam;
import com.exam.domain.dto.question.AutoPaperParam;
import com.exam.domain.dto.question.PaperParam;
import com.exam.domain.dto.question.PaperReplaceParam;
import com.exam.domain.dto.question.QuestionParam;
import com.exam.exception.LocalException;
import com.exam.response.BaseResponse;
import com.exam.service.question.PaperService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 试卷相关信息控制类
 *
 * @author sunc
 * @date 2020/3/14 11:44
 */

@RestController
@RequestMapping("/paper")
public class PaperController {

    @Resource
    private PaperService paperService;

    /**
     * 试卷试题列表(管理员用户能看到所有试题，老师只能看到自己所在年级的试题库)
     */
    @ResponseBody
    @GetMapping("/list")
    @RoleCheck({Role.TEACHER, Role.MANAGER})
    public BaseResponse list(HttpServletRequest request) {
        PageParam pageParam = new PageParam(request);
        PaperParam param = PaperParam.select(request);
        return BaseResponse.success(paperService.list(pageParam, param));
    }

    /**
     * 试卷详情
     */
    @ResponseBody
    @GetMapping("/detail/{id}")
    @RoleCheck({Role.TEACHER, Role.MANAGER})
    public BaseResponse detail(@PathVariable("id") Long id) throws LocalException {
        return BaseResponse.success(paperService.selectDetail(id));
    }

    /**
     * 删除(批量)试卷
     */
    @ResponseBody
    @DeleteMapping("/del")
    @RoleCheck({Role.TEACHER, Role.MANAGER})
    public BaseResponse deletes(@RequestBody String data) {
        List<Long> qIds = JSONArray.parseArray(data, Long.class);
        paperService.deletes(qIds);
        return BaseResponse.success();
    }

    /**
     * 新增试卷
     */
    @ResponseBody
    @PutMapping("/add")
    @RoleCheck({Role.TEACHER, Role.MANAGER})
    public BaseResponse add(@RequestBody String data) throws LocalException {
        PaperParam param = PaperParam.build(data);
        paperService.add(param);
        return BaseResponse.success();
    }

    /**
     * 自动获取一份试卷
     */
    @ResponseBody
    @GetMapping("/auto")
    @RoleCheck({Role.TEACHER, Role.MANAGER})
    public BaseResponse auto(@RequestBody String data) throws LocalException {
        AutoPaperParam param = AutoPaperParam.build(data);
        return BaseResponse.success(paperService.auto(param));
    }

    /**
     * 替换某个试题
     */
    @ResponseBody
    @PutMapping("/replace")
    @RoleCheck({Role.TEACHER, Role.MANAGER})
    public BaseResponse replace(@RequestBody String data) throws LocalException {
        PaperReplaceParam param = PaperReplaceParam.build(data);
        return BaseResponse.success(paperService.replace(param));
    }

    /**
     * 发布试卷
     */
    @ResponseBody
    @PostMapping("/publish/{id}")
    @RoleCheck({Role.TEACHER, Role.MANAGER})
    public BaseResponse publish(@PathVariable("id") Long id) throws LocalException {
        return BaseResponse.success(paperService.publish(id));
    }

    /**
     * 修改试卷信息
     */
    @ResponseBody
    @PutMapping("/update")
    @RoleCheck({Role.TEACHER, Role.MANAGER})
    public BaseResponse update(@RequestBody String data) throws LocalException {
        PaperParam param = PaperParam.update(data);
        return BaseResponse.success(paperService.update(param));
    }

}
