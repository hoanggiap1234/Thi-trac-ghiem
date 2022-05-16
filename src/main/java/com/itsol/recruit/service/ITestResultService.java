package com.itsol.recruit.service;

import com.itsol.recruit.dto.ResultStatisticDTO;
import com.itsol.recruit.dto.TestResultVM;
import com.itsol.recruit.entity.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ITestResultService {
    // gửi kết quả bài thi trắc nghiệm
    UUID postExam(TestResultVM answeredDTO);

    ResultStatisticDTO getResultTest(String userid);


}
