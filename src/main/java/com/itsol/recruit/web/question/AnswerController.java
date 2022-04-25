package com.itsol.recruit.web.question;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.dto.AnsweredDTO;
import com.itsol.recruit.entity.Answer;
import com.itsol.recruit.entity.Answersed;
import com.itsol.recruit.service.IAnswerService;
import com.itsol.recruit.service.IAnsweredService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = Constants.Api.Path.PUBLIC)
@Slf4j
public class AnswerController {

    private final IAnswerService iAnswerService;

    private final IAnsweredService itestExcutedService;

    public AnswerController(IAnswerService iAnswerService, IAnsweredService itestExcutedService) {
        this.iAnswerService = iAnswerService;
        this.itestExcutedService = itestExcutedService;
    }

    @PostMapping("/answer")
    public ResponseEntity<?> createQuestion(@Valid @RequestBody Answer answer) {

        log.debug("REST request to save answer : {}", answer);

        if (answer.getId() != null) {
            return null;
        }

        Answer result = iAnswerService.createAnswerForEachQuestion(answer);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/test")
    public ResponseEntity<?> testExcuted(@RequestBody AnsweredDTO examp) {

        log.debug("REST request to save examp : {}", examp);

        if (examp.getEmail() == null) {
            return null;
        }

        Answersed result = itestExcutedService.postExam(examp);
        return ResponseEntity.ok().body(result);
    }
}
