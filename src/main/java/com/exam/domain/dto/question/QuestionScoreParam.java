package com.exam.domain.dto.question;

import cn.hutool.core.util.StrUtil;
import com.exam.domain.dto.BaseDTO;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import lombok.Getter;
import lombok.Setter;

/**
 * 自动生成试卷参数类
 *
 * @author sunc
 * @date 2020/3/14 13:18
 */

@Setter
@Getter
public class QuestionScoreParam {

    /**
     * 题目类型
     */
    private Integer type;
    /**
     * 题目数量
     */
    private Integer count;
    /**
     * 题目平均分值
     */
    private Integer score;

//            "0":"单选题",
//            "1":"多选题",
//            "2":"判断题",
//            "3":"填空题",
//            "4":"主观题"

}
