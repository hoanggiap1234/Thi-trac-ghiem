package com.itsol.recruit.web.question;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.service.impl.TestResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Constants.Api.Path.PUBLIC)
@Slf4j
public class TestResultControler {

    private final TestResultService testResultService;

    public TestResultControler(TestResultService testResultService) {
        this.testResultService = testResultService;
    }

    @GetMapping("test-result/statistic")
    Object getStatisticResulTest(@RequestParam String userId){
        return testResultService.getResultTest(userId);
    }
}
