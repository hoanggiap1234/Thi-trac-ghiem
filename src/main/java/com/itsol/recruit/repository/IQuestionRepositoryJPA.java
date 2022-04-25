package com.itsol.recruit.repository;

import com.itsol.recruit.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IQuestionRepositoryJPA extends JpaRepository<Question, UUID> {

}
