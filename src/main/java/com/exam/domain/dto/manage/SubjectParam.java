package com.exam.domain.dto.manage;

import cn.hutool.core.util.StrUtil;
import com.exam.domain.dto.BaseDTO;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

/**
 * 科目信息参数类
 *
 * @author sunc
 * @date 2020/3/8 12:58
 */

@Setter
@Getter
public class SubjectParam extends BaseDTO {

    private Long id;
    private String name;

    public static SubjectParam select(HttpServletRequest request) {
        SubjectParam param = new SubjectParam();
        String name = request.getParameter("name");
        if (StrUtil.isNotBlank(name)) {
            param.name = name.trim();
        }
        return param;
    }

    public static SubjectParam build(String data) throws LocalException {
        SubjectParam param = SubjectParam.init(data, SubjectParam.class);
        param.check();
        return param;
    }

    /**
     * 参数校验
     */
    @Override
    protected void check() throws LocalException {
        if (StrUtil.isBlank(name)) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "name:" + name);
        }
    }

}
