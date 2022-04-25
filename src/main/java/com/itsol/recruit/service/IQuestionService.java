package com.itsol.recruit.service;

import com.itsol.recruit.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface IQuestionService {

    Page<Question> getAllQuestion(Pageable pageable);

    Question createQuestion(Question question);
}
