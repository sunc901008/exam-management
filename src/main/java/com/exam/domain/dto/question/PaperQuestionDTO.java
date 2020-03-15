package com.exam.domain.dto.question;

import com.exam.domain.entity.question.PaperQuestion;
import com.exam.domain.entity.question.Question;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sunc
 * @date 2020/3/14 13:47
 */

@Setter
@Getter
public class PaperQuestionDTO {
    private Integer index;
    private String content;
    private Integer score;
    private String answer;

    public static PaperQuestionDTO build(PaperQuestion pq) {
        return build(pq, false);
    }

    public static PaperQuestionDTO build(PaperQuestion pq, boolean withAnswer) {
        PaperQuestionDTO entity = new PaperQuestionDTO();
        entity.index = pq.getIndex();
        entity.score = pq.getScore();
        entity.content = pq.getContent();
        if (withAnswer) {
            entity.answer = pq.getAnswer();
        }
        return entity;
    }

    public static PaperQuestionDTO build(int index, Question question, int score) {
        PaperQuestionDTO entity = new PaperQuestionDTO();
        entity.index = index;
        entity.score = score;
        entity.content = question.getContent();
        entity.answer = question.getAnswer();
        return entity;
    }

}
