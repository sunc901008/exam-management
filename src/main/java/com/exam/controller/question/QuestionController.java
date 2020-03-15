package com.exam.controller.question;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.exam.base.Constant;
import com.exam.base.RoleCheck;
import com.exam.base.privilege.Role;
import com.exam.domain.PageParam;
import com.exam.domain.dto.question.QuestionParam;
import com.exam.exception.LocalException;
import com.exam.response.BaseResponse;
import com.exam.service.question.QuestionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 试题相关信息控制类
 *
 * @author sunc
 * @date 2020/3/14 11:44
 */

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Resource
    private QuestionService questionService;

    /**
     * 获取试题列表(管理员用户能看到所有试题，老师只能看到自己所在年级所教科目的试题库)
     */
    @ResponseBody
    @GetMapping("/list")
    @RoleCheck({Role.TEACHER, Role.MANAGER})
    public BaseResponse list(HttpServletRequest request) {
        PageParam pageParam = new PageParam(request);
        QuestionParam param = QuestionParam.select(request);
        return BaseResponse.success(questionService.list(pageParam, param));
    }

    /**
     * 获取试题详情(学生无法查看答案)
     */
    @ResponseBody
    @GetMapping("/detail/{id}")
    public BaseResponse detail(@PathVariable("id") Long id) throws LocalException {
        return BaseResponse.success(questionService.detail(id));
    }

    /**
     * 删除(批量)试题
     */
    @ResponseBody
    @DeleteMapping("/del")
    @RoleCheck({Role.TEACHER, Role.MANAGER})
    public BaseResponse deletes(@RequestBody String data) {
        List<Long> qIds = JSONArray.parseArray(data, Long.class);
        questionService.deletes(qIds);
        return BaseResponse.success();
    }

    /**
     * 新增试题
     */
    @ResponseBody
    @PutMapping("/add")
    @RoleCheck({Role.TEACHER, Role.MANAGER})
    public BaseResponse add(@RequestBody String data) throws LocalException {
        QuestionParam param = QuestionParam.build(data);
        return BaseResponse.success(questionService.insert(param));
    }

    /**
     * 修改试题
     */
    @ResponseBody
    @PutMapping("/update")
    @RoleCheck({Role.TEACHER, Role.MANAGER})
    public BaseResponse update(@RequestBody String data) throws LocalException {
        QuestionParam param = QuestionParam.update(data);
        return BaseResponse.success(questionService.update(param));
    }

    /**
     * 获取试题难度列表
     */
    @ResponseBody
    @GetMapping("/level")
    public BaseResponse level() {
        Constant.QuestionLevel[] values = Constant.QuestionLevel.values();
        JSONObject json = new JSONObject();
        for (int i = 0; i < values.length; i++) {
            json.put(String.valueOf(i), values[i].toString());
        }
        return BaseResponse.success(json);
    }

    /**
     * 获取试题类型列表
     */
    @ResponseBody
    @GetMapping("/type")
    public BaseResponse type() {
        Constant.QuestionType[] values = Constant.QuestionType.values();
        JSONObject json = new JSONObject();
        for (int i = 0; i < values.length; i++) {
            json.put(i + "", values[i].getName());
        }
        return BaseResponse.success(json);
    }

//    {
//        "data":{
//            "0":"单选题",
//            "1":"多选题",
//            "2":"判断题",
//            "3":"填空题",
//            "4":"主观题"
//    },
//        "errCode":0,
//            "exception":"",
//            "msgParams":{
//
//    },
//        "success":true
//    }

}
