package com.itsol.recruit.service.impl;

import com.itsol.recruit.entity.Answer;
import com.itsol.recruit.repository.IAnswerRepositoryJPA;
import com.itsol.recruit.repository.IQuestionRepositoryJPA;
import com.itsol.recruit.service.IAnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AnswerService implements IAnswerService {

    private final IQuestionRepositoryJPA iQuestionRepositoryJPA ;

    private final IAnswerRepositoryJPA iAnswerRepositoryJPA;

    public AnswerService(IQuestionRepositoryJPA iQuestionRepositoryJPA, IAnswerRepositoryJPA iAnswerRepositoryJPA) {
        this.iQuestionRepositoryJPA = iQuestionRepositoryJPA;
        this.iAnswerRepositoryJPA = iAnswerRepositoryJPA;
    }

    @Override
    public Answer createAnswerForEachQuestion(Answer answer) {

        Boolean isExitsId = iQuestionRepositoryJPA.existsById(answer.getQuestion().getId());
        if (!isExitsId) {
            log.error("questionId is not exits");
            return null;
        }
        return iAnswerRepositoryJPA.save(answer);
    }
}
