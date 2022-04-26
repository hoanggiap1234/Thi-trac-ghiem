package com.itsol.recruit.service;

import com.itsol.recruit.dto.TestResultDTO;

public interface ITestResultService {
    // gửi kết quả bài thi trắc nghiệm
    Boolean postExam(TestResultDTO answeredDTO);
}
