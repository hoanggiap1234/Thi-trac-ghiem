package com.itsol.recruit.repository;

import com.itsol.recruit.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ITestResultRepositoryJPA extends JpaRepository<TestResult, UUID> {
}
