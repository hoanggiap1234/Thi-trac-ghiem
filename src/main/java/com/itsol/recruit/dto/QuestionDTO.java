package com.itsol.recruit.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class QuestionDTO {

    UUID id;

    @NotNull
    String title;

    @NotNull
    String description;

    @NotNull
    String expectedAnswer ;

    @NotNull
    String corectAnswer ;
}
