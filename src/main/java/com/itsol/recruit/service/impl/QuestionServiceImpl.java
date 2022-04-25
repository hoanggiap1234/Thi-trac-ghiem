package com.itsol.recruit.service.impl;

import com.itsol.recruit.entity.Question;
import com.itsol.recruit.repository.IQuestionRepositoryJPA;
import com.itsol.recruit.service.IQuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements IQuestionService {

    private final IQuestionRepositoryJPA iQuestionRepository;

    public QuestionServiceImpl(IQuestionRepositoryJPA iQuestionRepository) {
        this.iQuestionRepository = iQuestionRepository;
    }

    @Override
    public Page<Question> getAllQuestion(Pageable pageable) {
        return iQuestionRepository.findAll(pageable);
    }

    @Override
    public Question createQuestion(Question question) {
        return iQuestionRepository.save(question);
    }
}
