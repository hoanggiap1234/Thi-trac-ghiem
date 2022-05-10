package com.itsol.recruit.web.question;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.dto.TestResultVM;
import com.itsol.recruit.entity.Answer;
import com.itsol.recruit.entity.TestResult;
import com.itsol.recruit.service.IAnswerService;
import com.itsol.recruit.service.ITestResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = Constants.Api.Path.PUBLIC)
@Slf4j
public class AnswerController {

    private final IAnswerService iAnswerService;

    private final ITestResultService itestExcutedService;

    public AnswerController(IAnswerService iAnswerService, ITestResultService itestExcutedService) {
        this.iAnswerService = iAnswerService;
        this.itestExcutedService = itestExcutedService;
    }

    @PostMapping("/answer")
    public ResponseEntity<?> createQuestion(@Valid @RequestBody Answer answer) {

        log.debug("REST request to save answer : {}", answer);

        if (answer.getId() != null) {
            return ResponseEntity.badRequest().body("id is not null, not insert to database !");
        }

        Answer result = iAnswerService.createAnswerForEachQuestion(answer);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/test")
    public ResponseEntity<?> testExcuted(@Valid @RequestBody TestResultVM examp) {

        log.debug("REST request to save examp : {}", examp);

        if (examp.getEmail() == null) {
            return ResponseEntity.badRequest().body("email is null or empty");
        }

        UUID userId = itestExcutedService.postExam(examp);
        return ResponseEntity.ok().body(userId);
    }
}
