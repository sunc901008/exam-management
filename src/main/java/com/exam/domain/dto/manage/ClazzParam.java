package com.exam.domain.dto.manage;

import cn.hutool.core.util.StrUtil;
import com.exam.domain.dto.BaseDTO;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

/**
 * 班级信息参数类
 *
 * @author sunc
 * @date 2020/3/8 12:58
 */

@Setter
@Getter
public class ClazzParam extends BaseDTO {

    private Long id;
    private String name;
    private Long gradeId;

    public static ClazzParam select(HttpServletRequest request) {
        ClazzParam param = new ClazzParam();
        String gradeId = request.getParameter("gradeId");
        if (StrUtil.isNotBlank(gradeId)) {
            param.gradeId = Long.parseLong(gradeId);
        }
        return param;
    }

    public static ClazzParam build(String data) throws LocalException {
        ClazzParam param = ClazzParam.init(data, ClazzParam.class);
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
        if (gradeId == null || gradeId <= 0) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "gradeId:" + gradeId);
        }
    }

}
