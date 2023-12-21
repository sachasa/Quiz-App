package com.gunawardane.questionservice.service;

import com.gunawardane.questionservice.dao.QuestionDao;
import com.gunawardane.questionservice.model.Question;
import com.gunawardane.questionservice.model.QuestionWrapper;
import com.gunawardane.questionservice.model.Response;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    public List<Question> getAllQuestions() {
        return questionDao.findAll();
    }

    public List<Question> getQuestionsByCategory(String category) {
        return questionDao.findByCategory(category);
    }

     public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDao.save(question);
            return ResponseEntity.ok("Success");
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    public Optional<Question> getQuestion(String id) {
        return questionDao.findById(Integer.parseInt(id));
    }

    public Question updateQuestion(String id, Question udatedQuestion) {
        Question existQuestion = questionDao.findById(Integer.parseInt(id))
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));

        existQuestion.setQuestionTitle(udatedQuestion.getQuestionTitle());
        existQuestion.setCategory(udatedQuestion.getCategory());
        existQuestion.setOption1(udatedQuestion.getOption1());
        existQuestion.setOption2(udatedQuestion.getOption2());
        existQuestion.setOption3(udatedQuestion.getOption3());
        existQuestion.setOption4(udatedQuestion.getOption4());
        existQuestion.setDifficultyLevel(udatedQuestion.getDifficultyLevel());

        return questionDao.save(existQuestion);
    }

    public void deleteQuestion(String id) {
        questionDao.deleteById(Integer.parseInt(id));
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, String numOfQuestions) {
        List<Integer> questions_ids = questionDao.findRandomQuestionsByCategory(category, Integer.parseInt(numOfQuestions));

        return new ResponseEntity<>(questions_ids, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromIds(List<Integer> questionIds) {
        List<QuestionWrapper> wrappedQuestions = new ArrayList<>();

        try {
            wrappedQuestions = getQuestionsAndWrap(questionIds);
            return new ResponseEntity<>(wrappedQuestions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

    }

    private List<QuestionWrapper> getQuestionsAndWrap(List<Integer> questionIds){
        List<QuestionWrapper> wrappedQuestions = new ArrayList<>();

        for(Integer id: questionIds){
            Question question = questionDao.findById(id).get();
            QuestionWrapper questionWrapper = wrapQuestion(question);

            wrappedQuestions.add(questionWrapper);
        }
        return wrappedQuestions;
    }

    private QuestionWrapper wrapQuestion(Question question){
        QuestionWrapper questionWrapper = new QuestionWrapper();
        questionWrapper.setId(question.getId());
        questionWrapper.setQuestionTitle(question.getQuestionTitle());
        questionWrapper.setOption1(question.getOption1());
        questionWrapper.setOption2(question.getOption2());
        questionWrapper.setOption3(question.getOption3());
        questionWrapper.setOption4(question.getOption4());

        return questionWrapper;
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int result = 0;

        for(Response response: responses){
            Question question = questionDao.findById(response.getId()).get();
            if(response.getResponse().equals(question.getRightAnswer())){
                result++;
            }
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
