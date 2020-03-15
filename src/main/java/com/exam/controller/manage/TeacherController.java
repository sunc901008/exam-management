package com.exam.controller.manage;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson.JSONArray;
import com.exam.base.CommonUtils;
import com.exam.base.ExcelFileUtils;
import com.exam.base.RoleCheck;
import com.exam.base.privilege.Role;
import com.exam.domain.PageParam;
import com.exam.domain.dto.manage.TeacherParam;
import com.exam.domain.entity.manage.Teacher;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import com.exam.response.BaseResponse;
import com.exam.service.manage.TeacherService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 教师相关信息控制类
 *
 * @author sunc
 * @date 2020/3/7 14:18
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private static final Logger logger = Logger.getLogger(TeacherController.class);

    @Resource
    private TeacherService teacherService;

    /**
     * 获取教师列表
     */
    @ResponseBody
    @GetMapping("/list")
    @RoleCheck({Role.MANAGER, Role.TEACHER})
    public BaseResponse list(HttpServletRequest request) {
        PageParam pageParam = new PageParam(request);
        TeacherParam teacherParam = TeacherParam.select(request);
        return BaseResponse.success(teacherService.list(pageParam, teacherParam));
    }

    /**
     * 通过 excel 导入教师信息
     */
    @ResponseBody
    @PostMapping("/import")
    @RoleCheck(Role.MANAGER)
    public BaseResponse importExcel(@RequestParam("file") MultipartFile file) throws LocalException {
        File tmpFile = new File(CommonUtils.uuid());
        try {
            file.transferTo(tmpFile);
        } catch (IOException e) {
            logger.error(ExceptionUtil.stacktraceToString(e));
            throw new LocalException(ExceptionCode.FILE_UPLOAD_ERROR, e.getMessage());
        }
        // 读取 Excel 所有内容
        List<List<String>> contents = ExcelFileUtils.readExcel(tmpFile);
        List<Teacher> teachers = Teacher.parseList(contents);
        teacherService.inserts(teachers);
        return BaseResponse.success();
    }

    /**
     * 删除(批量)教师
     */
    @ResponseBody
    @DeleteMapping("/del")
    @RoleCheck(Role.MANAGER)
    public BaseResponse deletes(@RequestBody String data) {
        List<Long> tchIds = JSONArray.parseArray(data, Long.class);
        teacherService.deletes(tchIds);
        return BaseResponse.success();
    }

    /**
     * 新增教师
     */
    @ResponseBody
    @PutMapping("/add")
    @RoleCheck(Role.MANAGER)
    public BaseResponse add(@RequestBody String data) throws LocalException {
        TeacherParam teacherParam = TeacherParam.build(data);
        return BaseResponse.success(teacherService.insert(teacherParam));
    }

    /**
     * 修改教师信息
     */
    @ResponseBody
    @PostMapping("/update")
    @RoleCheck(Role.MANAGER)
    public BaseResponse update(@RequestBody String data) throws LocalException {
        TeacherParam teacherParam = TeacherParam.build(data);
        return BaseResponse.success(teacherService.update(teacherParam));
    }

}
