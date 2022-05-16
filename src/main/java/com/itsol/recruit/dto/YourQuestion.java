package com.itsol.recruit.dto;

import com.itsol.recruit.entity.Question;
import lombok.Data;

import java.util.List;

@Data
public class  YourQuestion {
    Question question;
    List<AnswerDTO> systemAnswer;
    List<AnswerDTO> yourAnswer;
}
