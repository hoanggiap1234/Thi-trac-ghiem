package com.itsol.recruit.service.impl;

import com.itsol.recruit.dto.AnswerDTO;
import com.itsol.recruit.dto.QuestionDTO;
import com.itsol.recruit.entity.Answer;
import com.itsol.recruit.entity.Question;
import com.itsol.recruit.repository.IAnswerRepositoryJPA;
import com.itsol.recruit.repository.IQuestionRepositoryJPA;
import com.itsol.recruit.service.IQuestionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements IQuestionService {

    private final IQuestionRepositoryJPA iQuestionRepository;

    private final IAnswerRepositoryJPA iAnswerRepositoryJPA;

    public QuestionServiceImpl(IQuestionRepositoryJPA iQuestionRepository, IAnswerRepositoryJPA iAnswerRepositoryJPA) {
        this.iQuestionRepository = iQuestionRepository;
        this.iAnswerRepositoryJPA = iAnswerRepositoryJPA;
    }

    @Override
    public List<QuestionDTO> getAllQuestion() {
        List<Question> questions = iQuestionRepository.findAll();
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setTitle(questions.get(i).getTitle());
            questionDTO.setId(questions.get(i).getId());
            questionDTO.setDescription(questions.get(i).getDescription());
            List<Answer> answers = iAnswerRepositoryJPA.findByQuestionId(questions.get(i).getId());
            List<AnswerDTO> answerDTOs = new ArrayList<>();
            for (int j = 0; j < answers.size(); j++) {
                AnswerDTO answerDTO = new AnswerDTO();
                answerDTO.setId(answers.get(j).getId());
                answerDTO.setAnswer(answers.get(j).getAnswer());
                answerDTOs.add(answerDTO);
            }
            questionDTO.setAnswerDTOS(answerDTOs);
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }

    @Override
    public Question createQuestion(Question question) {
        return iQuestionRepository.save(question);
    }
}
