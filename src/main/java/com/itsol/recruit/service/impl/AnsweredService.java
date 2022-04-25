package com.itsol.recruit.service.impl;

import com.itsol.recruit.dto.AnsweredDTO;
import com.itsol.recruit.entity.Answer;
import com.itsol.recruit.entity.Answersed;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.IAnswerRepositoryJPA;
import com.itsol.recruit.repository.IAnswesedRepositoryJPA;
import com.itsol.recruit.repository.UserRepositoryJPA;
import com.itsol.recruit.service.IAnsweredService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AnsweredService implements IAnsweredService {

    private  final UserRepositoryJPA userRepositoryJPA;

    private  final IAnswerRepositoryJPA iAnswerRepositoryJPA;

    private final IAnswesedRepositoryJPA iAnswesedRepositoryJPA;

    public AnsweredService(UserRepositoryJPA userRepositoryJPA, IAnswerRepositoryJPA iAnswerRepositoryJPA, IAnswesedRepositoryJPA iAnswesedRepositoryJPA) {
        this.userRepositoryJPA = userRepositoryJPA;
        this.iAnswerRepositoryJPA = iAnswerRepositoryJPA;
        this.iAnswesedRepositoryJPA = iAnswesedRepositoryJPA;
    }

    @Override
    public Answersed postExam(AnsweredDTO answeredDTO) {

        // check email and birthday
        Optional<User> user = userRepositoryJPA.findByEmail(answeredDTO.getEmail());

        // check idAnswer tồn tại không.

        List<Answer> answers = answeredDTO.getAnswers();
        answers.stream().forEach(answer -> {
            if (!iAnswerRepositoryJPA.existsById(answer.getId())) {
                log.error("id is not exits");
                return;
            }
        });
        Answersed answersed = new Answersed();
        answersed.setAnswers(answeredDTO.getAnswers());
        if (!user.isPresent()) {
            User newUser = new User();
            newUser.setEmail(answeredDTO.getEmail());
            newUser.setDelete(false);
            newUser.setActive(false);
            userRepositoryJPA.save(newUser);
            answersed.setUser(newUser);
        } else {
            answersed.setUser(user.get());
        }

        return iAnswesedRepositoryJPA.save(answersed);
    }
}
