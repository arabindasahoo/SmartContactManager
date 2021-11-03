package com.exam.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import com.exam.repo.QuizRepository;
import com.exam.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizRepository quizRepo;

	@Override
	public Quiz addQuiz(Quiz quiz) {
		return quizRepo.save(quiz);
	}

	@Override
	public Quiz updateQuiz(Quiz quiz) {
		return quizRepo.save(quiz);
	}

	@Override
	public Set<Quiz> getQuizzes() {
		return new HashSet<Quiz>(quizRepo.findAll());
	}

	@Override
	public Quiz getQuiz(Long quizId) {
		return quizRepo.findById(quizId).get();
	}

	@Override
	public void deleteQuiz(Long quizId) {
		
		/*
		 * Quiz quiz = new Quiz();
		 * 
		 * quiz.setQid(quizId);
		 */
		  
		 
		quizRepo.deleteById(quizId);;
	}

	@Override
	public List<Quiz> getQuizzesInsideCategory(Category cat) {
		
		return quizRepo.findByCategory(cat);
	}

	@Override
	public List<Quiz> getActiveQuizzes(boolean b) {
		return quizRepo.findByActive(b);
	}

	@Override
	public List<Quiz> getActiveQuizzesOfCategory(Category cat, boolean b) {
		return quizRepo.findByCategoryAndActive(cat, true);
	}

}
