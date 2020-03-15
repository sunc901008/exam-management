package com.exam.controller.manage;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson.JSONArray;
import com.exam.base.CommonUtils;
import com.exam.base.ExcelFileUtils;
import com.exam.base.RoleCheck;
import com.exam.base.privilege.Role;
import com.exam.domain.PageParam;
import com.exam.domain.dto.manage.StudentParam;
import com.exam.domain.entity.manage.Student;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import com.exam.response.BaseResponse;
import com.exam.service.manage.StudentService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 学生相关信息控制类
 *
 * @author sunc
 * @date 2020/3/7 14:18
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    private static final Logger logger = Logger.getLogger(StudentController.class);

    @Resource
    private StudentService studentService;

    /**
     * 获取学生列表
     */
    @ResponseBody
    @GetMapping("/list")
    @RoleCheck({Role.MANAGER, Role.TEACHER})
    public BaseResponse list(HttpServletRequest request) {
        PageParam pageParam = new PageParam(request);
        StudentParam studentParam = StudentParam.select(request);
        return BaseResponse.success(studentService.list(pageParam, studentParam));
    }

    /**
     * 通过 excel 导入学生信息
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
        List<Student> students = Student.parseList(contents);
        studentService.inserts(students);
        return BaseResponse.success();
    }

    /**
     * 删除(批量)学生
     */
    @ResponseBody
    @DeleteMapping("/del")
    @RoleCheck(Role.MANAGER)
    public BaseResponse deletes(@RequestBody String data) {
        List<Long> stdIds = JSONArray.parseArray(data, Long.class);
        studentService.deletes(stdIds);
        return BaseResponse.success();
    }

    /**
     * 新增学生
     */
    @ResponseBody
    @PutMapping("/add")
    @RoleCheck(Role.MANAGER)
    public BaseResponse add(@RequestBody String data) throws LocalException {
        StudentParam studentParam = StudentParam.build(data);
        return BaseResponse.success(studentService.insert(studentParam));
    }

    /**
     * 修改学生信息
     */
    @ResponseBody
    @PostMapping("/update")
    @RoleCheck(Role.MANAGER)
    public BaseResponse update(@RequestBody String data) throws LocalException {
        StudentParam studentParam = StudentParam.build(data);
        return BaseResponse.success(studentService.update(studentParam));
    }

}
