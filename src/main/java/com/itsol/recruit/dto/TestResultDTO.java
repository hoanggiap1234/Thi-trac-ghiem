package com.itsol.recruit.dto;

import com.itsol.recruit.entity.Answer;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Data
public class TestResultDTO {

    @NotEmpty
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    String email;

    @NotEmpty
    @NotNull
    Date birthDay;

    @NotNull
    List<Answer> answers;

}
