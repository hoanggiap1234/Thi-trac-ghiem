package com.itsol.recruit.service;

import com.itsol.recruit.dto.QuestionDTO;
import com.itsol.recruit.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IQuestionService {

    List<QuestionDTO> getAllQuestion();

    Question createQuestion(Question question);
}
