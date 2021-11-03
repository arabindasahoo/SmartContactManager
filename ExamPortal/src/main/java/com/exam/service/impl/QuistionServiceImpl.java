package com.exam.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.repo.QuestionRepository;
import com.exam.service.QuestionService;

@Service
public class QuistionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepository questionRepo;
	
	@Override
	public Question addQuestion(Question question) {
		return questionRepo.save(question);
	}

	@Override
	public Question updateQuestion(Question question) {

		return questionRepo.save(question);
	}

	@Override
	public Set<Question> getQustions() {
		return new HashSet<Question>(questionRepo.findAll());
	}

	@Override
	public Question getQuestion(Long qId) {
		return questionRepo.findById(qId).get();
	}

	@Override
	public Set<Question> getQuestionsOfQuiz(Quiz quiz) {
		return questionRepo.findByQuiz(quiz);
	}

	@Override
	public void deleteQuestion(Long quesId) {
		
		Question question = new Question();
		question.setQid(quesId);
		questionRepo.delete(question);
	}

	@Override
	public Question get(Long qid) {
		return questionRepo.getById(qid);
	}

}
