package com.itsol.recruit.web.question;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.dto.QuestionDTO;
import com.itsol.recruit.entity.Question;
import com.itsol.recruit.service.IQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = Constants.Api.Path.PUBLIC)
@Slf4j
public class QuestionController {

    private final IQuestionService iQuestionService;

    public QuestionController(IQuestionService iQuestionService) {
        this.iQuestionService = iQuestionService;
    }

    // lay danh sach cau hoi cac dap an
    @GetMapping("/questions")
    public ResponseEntity<List<QuestionDTO>> getAllQuestions(){
        List<QuestionDTO> listData = iQuestionService.getAllQuestion();
        return ResponseEntity.ok().body(listData);
    }

    // them cau hoi va danh sach cac dap an
    @PostMapping("/questions")
    public ResponseEntity<?> createQuestion(@RequestBody Question question) {

        log.debug("REST request to save question : {}", question);

        if (question.getId() != null) {
            return ResponseEntity.badRequest().body("id question is not null, not insert to database");
        }

        Question result = iQuestionService.createQuestion(question);
        return ResponseEntity.ok().body(result);
    }

}
