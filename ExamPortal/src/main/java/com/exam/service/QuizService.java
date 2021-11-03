package com.exam.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
@Service
public interface QuizService {
	
	public Quiz addQuiz(Quiz quiz);
	
	public Quiz updateQuiz(Quiz quez);
	
	public Set<Quiz> getQuizzes();
	
	public Quiz getQuiz(Long quizId);
	
	public void deleteQuiz(Long Quiz);

	public List<Quiz> getQuizzesInsideCategory(Category cat);
	
	public List<Quiz> getActiveQuizzes(boolean b);
	
	public List<Quiz> getActiveQuizzesOfCategory(Category cat,boolean b);
	
	

}
