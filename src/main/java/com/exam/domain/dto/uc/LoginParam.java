package com.exam.domain.dto.uc;

import cn.hutool.core.util.StrUtil;
import com.exam.domain.dto.BaseDTO;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sunc
 * @date 2020/3/7 16:26
 */
@Getter
@Setter
public class LoginParam extends BaseDTO {
    private String role;
    private String identity;
    private String password;

    public static LoginParam build(String data) throws LocalException {
        LoginParam param = LoginParam.init(data, LoginParam.class);
        param.check();
        return param;
    }

    @Override
    protected void check() throws LocalException {
        if (StrUtil.isBlank(role) || StrUtil.isBlank(identity) || StrUtil.isBlank(password)) {
            throw new LocalException(ExceptionCode.PARAM_ERROR);
        }
    }

}
