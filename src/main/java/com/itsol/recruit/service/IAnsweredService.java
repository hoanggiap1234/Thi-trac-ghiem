package com.itsol.recruit.service;

import com.itsol.recruit.dto.AnsweredDTO;
import com.itsol.recruit.entity.Answersed;
import com.itsol.recruit.entity.User;

import java.util.List;

public interface IAnsweredService {
    // gửi kết quả bài thi trắc nghiệm
    Answersed postExam(AnsweredDTO answeredDTO);
}
