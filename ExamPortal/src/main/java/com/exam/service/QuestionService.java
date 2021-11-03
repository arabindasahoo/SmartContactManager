package com.exam.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
@Service
public interface QuestionService {
	
	public Question addQuestion(Question question);
	
	public Question updateQuestion(Question question);
	
	public Set<Question> getQustions();
	
	public Question getQuestion(Long qId);
	
	public Set<Question> getQuestionsOfQuiz(Quiz quiz);

	public void deleteQuestion(Long quesId);

	public Question get(Long qid);
	

}
