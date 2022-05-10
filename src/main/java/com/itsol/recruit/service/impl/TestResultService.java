package com.itsol.recruit.service.impl;

import com.itsol.recruit.dto.ResultStatisticDTO;
import com.itsol.recruit.dto.TestResultDTO;
import com.itsol.recruit.dto.TestResultVM;
import com.itsol.recruit.entity.Answer;
import com.itsol.recruit.entity.TestResult;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.IAnswerRepositoryJPA;
import com.itsol.recruit.repository.ITestResultRepositoryJPA;
import com.itsol.recruit.repository.UserRepositoryJPA;
import com.itsol.recruit.repository.repoext.TestResultRepository;
import com.itsol.recruit.service.ITestResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class TestResultService implements ITestResultService {

    private  final UserRepositoryJPA userRepositoryJPA;

    private  final IAnswerRepositoryJPA iAnswerRepositoryJPA;

    private final ITestResultRepositoryJPA iTestResultRepositoryJPA;

    @Autowired
    TestResultRepository testResulRepository;

    public TestResultService(UserRepositoryJPA userRepositoryJPA, IAnswerRepositoryJPA iAnswerRepositoryJPA, ITestResultRepositoryJPA iTestResultRepositoryJPA) {
        this.userRepositoryJPA = userRepositoryJPA;
        this.iAnswerRepositoryJPA = iAnswerRepositoryJPA;
        this.iTestResultRepositoryJPA = iTestResultRepositoryJPA;
    }

    @Override
    public UUID postExam(TestResultVM testResultDTO) {
        Date timeAccepted = new Date();
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
                testResult.setTime(timeAccepted.getTime());
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
           List<TestResult> results = iTestResultRepositoryJPA.saveAll(testResults);
//            log.info("post resultest success");
            return results.get(0).getUser().getId();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public ResultStatisticDTO getResultTest(String userId) {
        List<TestResultDTO> resultTests = testResulRepository.getStatisticResultTest(userId);
        ResultStatisticDTO resultStatistic = new ResultStatisticDTO();
        resultStatistic.setTotalAnswer(resultTests.size());
        // duyet mang ket qua
        Integer numberCorect = 0;
        Integer numberBadCorect = 0;
        List<Answer> answers = new ArrayList<>();

        for( int i =0 ;i < resultTests.size(); i++){
            answers.add(iAnswerRepositoryJPA.findById(UUID.fromString(resultTests.get(i).getAnswerId())).get());
            if(resultTests.get(i).getCorectAnswer() == 1){
                numberCorect++;
            }else{
                numberBadCorect++;
            }
        }
        resultStatistic.setNumberAnswerCorect(numberCorect);
        resultStatistic.setTotalAnswerBadCorect(numberBadCorect);
        resultStatistic.setAnswers(answers);
        return resultStatistic;
    }
}