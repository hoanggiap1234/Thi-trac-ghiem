package com.itsol.recruit.dto;

import com.itsol.recruit.entity.Answer;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
public class QuestionDTO {

    UUID id;

    String title;

    String description;

    List<AnswerDTO> answerDTOS;

}
