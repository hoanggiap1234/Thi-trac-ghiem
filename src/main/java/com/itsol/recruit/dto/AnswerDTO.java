package com.itsol.recruit.dto;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import java.util.UUID;

@Data
public class AnswerDTO {

    UUID id;

    String answer;

    Boolean corectAnswer;
}
