package com.itsol.recruit.service.impl;

import com.itsol.recruit.dto.TestResultDTO;
import com.itsol.recruit.entity.Answer;
import com.itsol.recruit.entity.TestResult;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.IAnswerRepositoryJPA;
import com.itsol.recruit.repository.ITestResultRepositoryJPA;
import com.itsol.recruit.repository.UserRepositoryJPA;
import com.itsol.recruit.service.ITestResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TestResultService implements ITestResultService {

    private  final UserRepositoryJPA userRepositoryJPA;

    private  final IAnswerRepositoryJPA iAnswerRepositoryJPA;

    private final ITestResultRepositoryJPA iAnswesedRepositoryJPA;

    public TestResultService(UserRepositoryJPA userRepositoryJPA, IAnswerRepositoryJPA iAnswerRepositoryJPA, ITestResultRepositoryJPA iAnswesedRepositoryJPA) {
        this.userRepositoryJPA = userRepositoryJPA;
        this.iAnswerRepositoryJPA = iAnswerRepositoryJPA;
        this.iAnswesedRepositoryJPA = iAnswesedRepositoryJPA;
    }

    @Override
    public Boolean postExam(TestResultDTO testResultDTO) {

        try {
            // check email and birthday
            Optional<User> user = userRepositoryJPA.findByEmail(testResultDTO.getEmail());

            // check idAnswer tồn tại không.

            List<Answer> answers = testResultDTO.getAnswers();
            answers.stream().forEach(answer -> {
                if (!iAnswerRepositoryJPA.existsById(answer.getId())) {
                    log.error("id is not exits");
                    return;
                }
            });
            List<TestResult> testResults = new ArrayList<>();
            for (int i = 0; i < testResultDTO.getAnswers().size(); i++) {
                TestResult testResult = new TestResult();
                testResult.setAnswer(testResultDTO.getAnswers().get(i));
                if (!user.isPresent()) {
                    User newUser = new User();
                    newUser.setEmail(testResultDTO.getEmail());
                    newUser.setDelete(false);
                    newUser.setActive(false);
                    userRepositoryJPA.save(newUser);
                    testResult.setUser(newUser);
                } else {
                    testResult.setUser(user.get());
                }
                testResults.add(testResult);
            }
            iAnswesedRepositoryJPA.saveAll(testResults);
            log.info("post resultest success");
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }

    }
}
