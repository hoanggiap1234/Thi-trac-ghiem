package com.itsol.recruit.service.impl;

import com.itsol.recruit.dto.*;
import com.itsol.recruit.entity.Answer;
import com.itsol.recruit.entity.Question;
import com.itsol.recruit.entity.TestResult;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.IAnswerRepositoryJPA;
import com.itsol.recruit.repository.IQuestionRepositoryJPA;
import com.itsol.recruit.repository.ITestResultRepositoryJPA;
import com.itsol.recruit.repository.UserRepositoryJPA;
import com.itsol.recruit.repository.repoext.TestResultRepository;
import com.itsol.recruit.service.ITestResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TestResultService implements ITestResultService {
    private  final UserRepositoryJPA userRepositoryJPA;
    private  final IAnswerRepositoryJPA iAnswerRepositoryJPA;
    private final ITestResultRepositoryJPA iTestResultRepositoryJPA;

    private  final IQuestionRepositoryJPA iQuestionRepositoryJPA;

    @Autowired
    TestResultRepository testResulRepository;

    public TestResultService(UserRepositoryJPA userRepositoryJPA, IAnswerRepositoryJPA iAnswerRepositoryJPA, ITestResultRepositoryJPA iTestResultRepositoryJPA, IQuestionRepositoryJPA iQuestionRepositoryJPA) {
        this.userRepositoryJPA = userRepositoryJPA;
        this.iAnswerRepositoryJPA = iAnswerRepositoryJPA;
        this.iTestResultRepositoryJPA = iTestResultRepositoryJPA;
        this.iQuestionRepositoryJPA = iQuestionRepositoryJPA;
    }

    @Override
    public UUID postExam(TestResultVM testResultDTO) {
        Date timeAccepted = new Date();
        try {

            List<TestResult> testResults = new ArrayList<>();
            for (int i = 0; i < testResultDTO.getAnswerIDs().size(); i++) {
                Optional<User> user = userRepositoryJPA.findByEmail(testResultDTO.getEmail());
                TestResult testResult = new TestResult();
                testResult.setTime(timeAccepted.getTime());
                Answer answer = iAnswerRepositoryJPA.findById(testResultDTO.getAnswerIDs().get(i)).get();
                testResult.setAnswer(answer);
                // check email
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
        Integer numberCorrect = 0;
        Integer numberBad = 0;
        ResultStatisticDTO resultStatistic = new ResultStatisticDTO();

        List<TestResultDTO> resultTests = testResulRepository.getStatisticResultTest(userId);

       Map<String, Long> numberYourAnswer =  resultTests.stream().collect(
                Collectors.groupingBy(TestResultDTO::getQuestionId, Collectors.counting())
        );
        resultStatistic.setTotalAnswer(numberYourAnswer.size());

        List<YourQuestion> yourQuestions = new ArrayList<>();
        for( int i = 0 ; i< resultTests.size(); i++){
            //1. Lấy ra phần trả lời của người làm: gồm câu trả lời tương ứng câu hỏi nào.
            UUID questionId = UUID.fromString(resultTests.get(i).getQuestionId());
            if(checkIDExits(yourQuestions, questionId) ==false){
                YourQuestion yourQuestion = new YourQuestion();

                // set Question
                yourQuestion.setQuestion(iQuestionRepositoryJPA.findById(questionId).get());

                // set systemAnswer
                List<Answer> answers = iAnswerRepositoryJPA.findByQuestionId(questionId);
                List<AnswerDTO> systemAnswer = new ArrayList<>();
                answers.forEach(answer -> {
                    AnswerDTO answerDTO = new AnswerDTO();
                    BeanUtils.copyProperties(answer,answerDTO);
                    systemAnswer.add(answerDTO);
                });
                yourQuestion.setSystemAnswer(systemAnswer);

                // set yourAnswer
                List<TestResultDTO> testResultDTOS =
                        resultTests.stream().filter( testResultDTO -> UUID.fromString(testResultDTO.getQuestionId()).equals(questionId))
                        .collect(Collectors.toList());

                List<AnswerDTO> yourAnswer = new ArrayList<>();
                testResultDTOS.forEach(testResultDTO -> {
                    Answer answer = iAnswerRepositoryJPA.findById(UUID.fromString(testResultDTO.getAnswerId())).get();
                    AnswerDTO answerDTO = new AnswerDTO();
                    BeanUtils.copyProperties(answer,answerDTO);
                    yourAnswer.add(answerDTO);
                });
                yourQuestion.setYourAnswer(yourAnswer);
               if(markResultTest(systemAnswer, yourAnswer)==true){
                   System.out.println("tra loi dung");
                   numberCorrect ++ ;
               }else {
                   System.out.println("tra lơi sai");
                   numberBad ++ ;
               }
                yourQuestions.add(yourQuestion);

            }
            resultStatistic.setYourQuestions(yourQuestions);
            resultStatistic.setNumberAnswerCorect(numberCorrect);
            resultStatistic.setTotalAnswerBadCorect(numberBad);
            //2.. Lấy ra danh sách các câu hỏi co đủ option của đề bài.

        }
        return resultStatistic;
    }

    public  Boolean checkIDExits(List<YourQuestion> objects, UUID id) {

        Boolean check = false;
        for( int i = 0; i < objects.size(); i++){
            if(objects.get(i).getQuestion().getId().equals(id)) {
                check = true;
                break;
            }
        }
        return  check;
    }

    public Boolean markResultTest(List<AnswerDTO> systemAnswers, List<AnswerDTO> yourAnswers) {
        List<Boolean> checks = new ArrayList<>();
        Boolean check = false;
        for (int i = 0; i < systemAnswers.size(); i++) {
            Answer systemAnswer = iAnswerRepositoryJPA.findById(systemAnswers.get(i).getId()).get();
            if (systemAnswer.getCorectAnswer() == true && findIDInObject(yourAnswers, systemAnswer.getId()) == true) {
                checks.add(true);
            } else if (systemAnswer.getCorectAnswer() == true && findIDInObject(yourAnswers, systemAnswer.getId()) == false) {
                checks.add(false);
            } else if (systemAnswer.getCorectAnswer() == false && findIDInObject(yourAnswers, systemAnswer.getId()) == false) {
                checks.add(true);
            } else if (systemAnswer.getCorectAnswer() == true && findIDInObject(yourAnswers, systemAnswer.getId()) == true) {
                checks.add(false);
            } else {
                checks.add(false);
            }
        }
//        }
        for (int i = 0; i < checks.size(); i++) {
            if (checks.get(i) == false) {
                check = false;
                break;
            } else {
                check = true;
            }
        }
        return check;
    }

    public Boolean findIDInObject(List<AnswerDTO> answerDTOs,UUID id){
        Boolean check = false;
       for( int i =0; i< answerDTOs.size(); i++){
           if(answerDTOs.get(i).getId().equals(id)){
               check = true;
               break;
           } else {
               check = false;
           }
       }
        return  check;
    }

}