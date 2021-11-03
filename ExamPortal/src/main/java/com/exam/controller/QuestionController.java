package com.exam.controller; 

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;

import io.jsonwebtoken.lang.Collections;

@RestController
@CrossOrigin
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuizService quizService;
	
	
	//Add Question
	
	@PostMapping("/")
	public ResponseEntity<?> addQuestion(@RequestBody Question question){
		
		return ResponseEntity.ok(questionService.addQuestion(question));
		
	}
	
	//Update Question
	@PutMapping("/")
	public ResponseEntity<Question> updateQuestion(@RequestBody Question question)
	{
		return ResponseEntity.ok(questionService.updateQuestion(question));
	}
	
	//Get  Only Number of Questions of any Quiz
	
	@GetMapping("/quiz/{quizId}")
	public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("quizId")Long quizId)
	{
//		Quiz quiz = new Quiz();
//		quiz.setQid(quizId);
//		Set<Question> questionsOfQuiz = questionService.getQuestionsOfQuiz(quiz);
		
		
		
		Quiz quiz = quizService.getQuiz(quizId);
		Set<Question> questions = quiz.getQuestions();
		
		List list = (List) new ArrayList(questions); 
		
		if(list.size() > Integer.parseInt(quiz.getNoOfQuestions()))
		{
			list = list.subList(0, Integer.parseInt(quiz.getNoOfQuestions()+1));
		}
		java.util.Collections.shuffle(list);
		
		return ResponseEntity.ok(list);
	}
	
	//Get all Questions of a Quiz
	@GetMapping("/quiz/all/{quizId}")
	public ResponseEntity<?> getAllQuestionsOfQuiz(@PathVariable("quizId")Long quizId)
	{
		
				Quiz quiz = new Quiz();
			quiz.setQid(quizId); 
			Set<Question> questionsOfQuiz = questionService.getQuestionsOfQuiz(quiz);
		 
		return ResponseEntity.ok(questionsOfQuiz);
	}
	
	
	//Get Single Question
	@GetMapping("/{quesId}")
	public Question getQuestion(@PathVariable("quesId")Long quesId)
	{
		Question question = questionService.getQuestion(quesId);
		return question;
	}
	
	//Delete Question
	@DeleteMapping("/{quesId}")
	public void deleteQuestion(@PathVariable("quesId")Long quesId)
	{
		questionService.deleteQuestion(quesId);
	}
	
	@PostMapping("/eval-quiz")
	public ResponseEntity<?> evaluateQuiz(@RequestBody List<Question> questions)
	{

			double correctAnswers = 0;
			double questionAttempt = 0;
			double markSecured = 0;

		for (Question q : questions) {
			
			System.out.println(q.getGivenAnswer());
			
			Question question = questionService.get(q.getQid());
		
			if(question.getAnswer().equals(q.getGivenAnswer()))
			{
			
				correctAnswers++;
				
				double markSingle = Double.parseDouble(questions.get(0).getQuiz().getMaxOfMarks())/questions.size();
				markSecured += markSingle;
			}
			if(q.getGivenAnswer() != "")
			{
				questionAttempt++;
			}
		}
		
		System.out.println(questionAttempt);
		
		Map<String , Object> map = new HashMap<>();
		map.put("correctAnswers", correctAnswers);
		map.put("markSecured", markSecured);
		map.put("questionAttempt", questionAttempt);
		
		
		return ResponseEntity.ok(map);
	}
}
