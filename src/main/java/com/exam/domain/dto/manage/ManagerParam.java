package com.exam.domain.dto.manage;

import cn.hutool.core.util.StrUtil;
import com.exam.domain.dto.BaseDTO;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

/**
 * 学生信息参数类
 *
 * @author sunc
 * @date 2020/3/7 13:45
 */
@Setter
@Getter
public class ManagerParam extends BaseDTO {

    private Long id;
    private String managerNumber;
    private String name;
    private String password;

    public static ManagerParam select(HttpServletRequest request) {
        ManagerParam param = new ManagerParam();
        String name = request.getParameter("name");
        if (StrUtil.isNotBlank(name)) {
            param.name = name.trim();
        }
        return param;
    }

    public static ManagerParam build(String data) throws LocalException {
        ManagerParam param = ManagerParam.init(data, ManagerParam.class);
        param.check();
        return param;
    }

    /**
     * 参数校验
     */
    @Override
    protected void check() throws LocalException {
        if (StrUtil.isBlank(managerNumber)) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "managerNumber:" + managerNumber);
        }
        if (StrUtil.isBlank(name)) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "name:" + name);
        }
        if (StrUtil.isBlank(password)) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "password:" + password);
        }
    }

}
