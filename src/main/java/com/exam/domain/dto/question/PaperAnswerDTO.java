package com.exam.domain.dto.question;

import cn.hutool.core.collection.CollectionUtil;
import com.exam.domain.entity.question.Paper;
import com.exam.domain.entity.question.PaperAnswer;
import com.exam.domain.entity.question.PaperQuestion;
import lombok.Getter;

import java.util.List;

/**
 * 试卷答案实体类
 *
 * @author sunc
 * @date 2020/3/14 11:44
 */
@Getter
public class PaperAnswerDTO {

    private Long paperId;
    private String paperName;

    private List<QuestionAnswerDTO> answers;

    @Getter
    public class QuestionAnswerDTO {
        private Integer index;
        private String content;
        private String standardAnswer;
        private Integer standardScore;
        private String answer;
        private Integer score;

        QuestionAnswerDTO(PaperQuestion question, PaperAnswer answer) {
            this.index = question.getIndex();
            this.content = question.getContent();
            this.standardAnswer = question.getAnswer();
            this.standardScore = question.getScore();
            this.answer = answer.getAnswer();
            this.score = answer.getScore();
        }
    }

    public PaperAnswerDTO(Paper paper) {
        this.paperId = paper.getId();
        this.paperName = paper.getName();
    }

    public void addAnswer(PaperQuestion question, PaperAnswer answer) {
        if (answers == null) {
            answers = CollectionUtil.newArrayList();
        }
        answers.add(new QuestionAnswerDTO(question, answer));
    }

}
