package com.itsol.recruit.repository;

import com.itsol.recruit.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IAnswerRepositoryJPA extends JpaRepository<Answer, UUID> {
    List<Answer> findByQuestionId(UUID id);
}
