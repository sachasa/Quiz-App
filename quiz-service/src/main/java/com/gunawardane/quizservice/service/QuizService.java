package com.gunawardane.quizservice.service;

import com.gunawardane.quizservice.dao.QuizDao;
import com.gunawardane.quizservice.feign.QuizInterface;
import com.gunawardane.quizservice.model.QuestionWrapper;
import com.gunawardane.quizservice.model.Quiz;
import com.gunawardane.quizservice.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String quizTitle) {

        List<Integer> questionIds = quizInterface.
                getQuestionsForQuiz(category, String.valueOf(numQ)).getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(quizTitle);
        quiz.setQuestionIds(questionIds);

        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(String id) {
        Quiz quiz = quizDao.findById(Integer.parseInt(id)).get();

        List<Integer> questionIds = quiz.getQuestionIds();

        return quizInterface.getQuestionsFromIds(questionIds);
    }


    public ResponseEntity<Integer> submitQuiz(String id, List<Response> responses) {

        return quizInterface.getScore(responses);
    }
}
