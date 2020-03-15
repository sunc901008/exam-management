package com.exam.domain.dto.uc;

import cn.hutool.core.util.StrUtil;
import com.exam.base.policy.PasswordPolicy;
import com.exam.domain.dto.BaseDTO;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import lombok.Getter;
import lombok.Setter;

/**
 * 个人中心, 目前只涉及修改密码(如还有其他信息, 在此类中添加)
 *
 * @author sunc
 * @date 2020/3/8 12:36
 */

@Getter
@Setter
public class ProfileParam extends BaseDTO {
    private String password;

    public static ProfileParam build(String data) throws LocalException {
        ProfileParam param = ProfileParam.init(data, ProfileParam.class);
        param.check();
        return param;
    }

    @Override
    protected void check() throws LocalException {
        if (StrUtil.isBlank(password)) {
            throw new LocalException(ExceptionCode.PARAM_ERROR);
        }
        this.password = PasswordPolicy.encrypt(password);
    }

}
