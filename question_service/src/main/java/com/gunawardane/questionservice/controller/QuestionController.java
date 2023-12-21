package com.gunawardane.questionservice.controller;

import com.gunawardane.questionservice.model.Question;
import com.gunawardane.questionservice.model.QuestionWrapper;
import com.gunawardane.questionservice.model.Response;
import com.gunawardane.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    // get all questions
    @Autowired
    QuestionService questionService;

    @Autowired
    Environment environment;

    @GetMapping("/all")
    public List<Question> getALlQuestions(){

        return questionService.getAllQuestions();
    }

    // get one question
    @GetMapping("{id}")
    public Optional<Question> getQuestion(@PathVariable String id){
        return questionService.getQuestion(id);
    }

    // get questions by category
    @GetMapping("category/{category}")
    public List<Question> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Question updateQuestion(@PathVariable String id, @RequestBody Question question){
        return questionService.updateQuestion(id, question);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteQuestion(@PathVariable String id){
        questionService.deleteQuestion(id);
        return "Question was deleted";
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam String numOfQuestions){
        return questionService.getQuestionsForQuiz(category, numOfQuestions);
    }

    @PostMapping("more")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromIds(@RequestBody List<Integer> questionIds){
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestionsFromIds(questionIds);
    }

    @PostMapping("score")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }
}
