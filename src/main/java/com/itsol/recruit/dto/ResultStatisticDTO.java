package com.itsol.recruit.dto;

import com.itsol.recruit.entity.Answer;
import com.itsol.recruit.entity.Question;
import lombok.Data;

import java.util.List;

@Data
public class ResultStatisticDTO {

    Integer numberAnswerCorect;
    Integer totalAnswer;
    Integer totalAnswerBadCorect;
    List<YourQuestion> yourQuestions;


}
