package com.itsol.recruit.repository.repoext;

import com.itsol.recruit.dto.TestResultDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestResultRepository {
    List<TestResultDTO> getStatisticResultTest(String userId);
}
