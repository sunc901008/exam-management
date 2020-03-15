package com.exam.domain.entity.question;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.exam.domain.dto.question.PaperAnswerParam;
import com.exam.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 试卷答案实体类
 *
 * @author sunc
 * @date 2020/3/14 11:44
 */
@Getter
@Setter
@TableName("sys_paper_answer")
public class PaperAnswer extends BaseEntity {

    @TableField("student_number")
    private String studentNumber;
    /**
     * 试卷id
     */
    @TableField("paper_id")
    private Long paperId;

    /**
     * 试题序号
     */
    private Integer index;

    /**
     * 答案
     */
    private String answer;

    /**
     * 得分
     */
    private Integer score;

    private PaperAnswer() {
    }

    private PaperAnswer(String studentNumber, Long paperId, Integer index, String answer) {
        this.studentNumber = studentNumber;
        this.paperId = paperId;
        this.index = index;
        this.answer = answer;
    }

    public static List<PaperAnswer> build(PaperAnswerParam param) {
        String studentNumber = param.getStudentNumber();
        Long paperId = param.getPaperId();
        return param.getAnswers().stream()
                .map(a -> new PaperAnswer(studentNumber, paperId, a.getIndex(), a.getAnswer()))
                .sorted(Comparator.comparing(PaperAnswer::getIndex))
                .collect(Collectors.toList());
    }

    public static PaperAnswer copy(PaperAnswer answer) {
        PaperAnswer entity = new PaperAnswer();
        entity.setId(answer.getId());
        entity.setVersion(answer.getVersion());
        return entity;
    }
}
