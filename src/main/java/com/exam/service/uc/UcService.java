package com.exam.service.uc;

import com.exam.base.RedisUtils;
import com.exam.base.policy.PasswordPolicy;
import com.exam.base.privilege.Role;
import com.exam.config.context.LocalContext;
import com.exam.domain.dto.uc.UserDTO;
import com.exam.domain.dto.uc.LoginParam;
import com.exam.domain.entity.manage.Manager;
import com.exam.domain.entity.manage.Student;
import com.exam.domain.entity.manage.Teacher;
import com.exam.domain.mapper.manage.ManagerMapper;
import com.exam.domain.mapper.manage.StudentMapper;
import com.exam.domain.mapper.manage.TeacherMapper;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author sunc
 * @date 2020/3/7 15:45
 */
@Service
public class UcService {

    @Resource
    private StudentMapper studentMapper;
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private ManagerMapper managerMapper;

    public UserDTO login(LoginParam loginParam) throws LocalException {
        String role = loginParam.getRole();
        String identity = loginParam.getIdentity();
        String pwd = loginParam.getPassword();
        if (Role.MANAGER.equals(role)) {
            Manager manager = managerMapper.selectByMgrNum(identity);
            if (manager == null || !PasswordPolicy.matches(pwd, manager.getPassword())) {
                // 信息不存在|密码错误|用户已被删除, 返回登录失败
                throw new LocalException(ExceptionCode.LOGIN_ERROR);
            }
            return UserDTO.buildManager(manager);
        }
        if (Role.TEACHER.equals(role)) {
            Teacher teacher = teacherMapper.selectByTchNum(identity);
            if (teacher == null || !PasswordPolicy.matches(pwd, teacher.getPassword()) || teacher.getDeleted()) {
                // 信息不存在|密码错误|用户已被删除, 返回登录失败
                throw new LocalException(ExceptionCode.LOGIN_ERROR);
            }
            return UserDTO.buildTeacher(teacher);
        }
        if (Role.STUDENT.equals(role)) {
            Student student = studentMapper.selectByStdNum(identity);
            if (student == null || !PasswordPolicy.matches(pwd, student.getPassword()) || student.getDeleted()) {
                // 信息不存在|密码错误|用户已被删除, 返回登录失败
                throw new LocalException(ExceptionCode.LOGIN_ERROR);
            }
            return UserDTO.buildStudent(student);
        }
        throw new LocalException(ExceptionCode.LOGIN_ERROR);
    }

    public void logout() {
        RedisUtils.delete(LocalContext.getAccessToken());
    }

    public boolean updatePwd(String password) throws LocalException {
        String role = LocalContext.getRole();
        Long userId = LocalContext.getUserId();
        if (Role.MANAGER.equals(role)) {
            Manager user = managerMapper.selectById(userId);
            Manager o = Manager.copy(user);
            o.setPassword(password);
            managerMapper.updateById(o);
        } else if (Role.TEACHER.equals(role)) {
            Teacher user = teacherMapper.selectById(userId);
            Teacher o = Teacher.copy(user);
            o.setPassword(password);
            teacherMapper.updateById(o);
        } else if (Role.STUDENT.equals(role)) {
            Student user = studentMapper.selectById(userId);
            Student o = Student.copy(user);
            o.setPassword(password);
            studentMapper.updateById(o);
        }
        throw new LocalException(ExceptionCode.INVALID_REQUEST_ERROR);
    }

}
