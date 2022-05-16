package com.itsol.recruit.dto;

import com.itsol.recruit.entity.Answer;
import com.itsol.recruit.entity.Question;
import lombok.Data;

import java.util.UUID;

@Data
public class AnswerQuestionDTO {
     UUID yourAnswerID;
     String yourAnserOption;
     Boolean isCorrect;
     QuestionDTO yourQuestion;
}
