package com.exam.domain.dto.manage;

import cn.hutool.core.util.StrUtil;
import com.exam.domain.dto.BaseDTO;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import lombok.Getter;
import lombok.Setter;

/**
 * 年级信息参数类
 *
 * @author sunc
 * @date 2020/3/8 12:58
 */

@Setter
@Getter
public class GradeParam extends BaseDTO {

    private Long id;
    private String name;

    public static GradeParam build(String data) throws LocalException {
        GradeParam param = GradeParam.init(data, GradeParam.class);
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
