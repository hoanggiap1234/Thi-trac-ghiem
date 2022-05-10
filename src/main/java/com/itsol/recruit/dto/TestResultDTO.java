package com.itsol.recruit.dto;

import lombok.Data;

@Data
public class TestResultDTO {
    String testResultId;
    String answerId;
    String email;
    String timeAccepted;
    String questionId;
    String title;
    String answer;
    Integer corectAnswer;
}
