package com.itsol.recruit.service;

import com.itsol.recruit.entity.Answer;
import com.itsol.recruit.entity.Question;

public interface IAnswerService {

    Answer createAnswerForEachQuestion(Answer answer);
}
