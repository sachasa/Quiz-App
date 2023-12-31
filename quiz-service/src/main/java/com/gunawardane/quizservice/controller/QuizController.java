package com.gunawardane.quizservice.controller;

import com.gunawardane.quizservice.dto.QuizDto;
import com.gunawardane.quizservice.model.QuestionWrapper;
import com.gunawardane.quizservice.model.Response;
import com.gunawardane.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping
    public ResponseEntity<String> createQuiz
            (@RequestBody QuizDto quizDto){
        return quizService.createQuiz(
                quizDto.getCategory(),
                quizDto.getNumOfQuestions(),
                quizDto.getTitle());
    }

    @GetMapping("{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable String id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable String id, @RequestBody List<Response> responses){
        return quizService.submitQuiz(id, responses);
    }
}
