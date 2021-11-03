package com.exam.controller;


import java.util.List;

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

import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import com.exam.service.QuizService;

@RestController
@CrossOrigin("*")
@RequestMapping("/quiz/")
public class QuizController {

	@Autowired
	private QuizService quizReService;
	
	
	//Add Quiz
	@PostMapping("/")
	public ResponseEntity<?> addQuiz(@RequestBody Quiz quiz)
	{
		return ResponseEntity.ok(quizReService.addQuiz(quiz));
	}
	
	//Update Quiz
	@PutMapping("/")
	public ResponseEntity<Quiz> updarteQuiz(@RequestBody Quiz quiz)
	{
		return ResponseEntity.ok(quizReService.updateQuiz(quiz));
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllQuiz()
	{
		return ResponseEntity.ok(quizReService.getQuizzes());
				
	}
	
	//Get Single Quiz
	
	@GetMapping("/{quizId}")
	public Quiz getQuiz(@PathVariable("quizId")Long quizId)
	{
		return quizReService.getQuiz(quizId);
	}
	
	//Get Quizzes Of inside a Category
	
	@GetMapping("/category/{catId}")
	public ResponseEntity<?> getQuizzesOfCategory(@PathVariable("catId")Long catId)
	{
		Category cat = new Category();
		cat.setId(catId);
		
		List<Quiz> quizzesInsideCategory = quizReService.getQuizzesInsideCategory(cat);
		return ResponseEntity.ok(quizzesInsideCategory);
		
	}
	//Delete A single Quiz
	@DeleteMapping("/{quizId}")
	public void deleteQuiz(@PathVariable("quizId")Long quizId)
	{
		quizReService.deleteQuiz(quizId);
	}
	
	
	//Get All Active Quizzes
	@GetMapping("/active")
	public ResponseEntity<?> getActiveQuizzes()
	{
		List<Quiz> activeQuizzes = quizReService.getActiveQuizzes(true);
		return ResponseEntity.ok(activeQuizzes);
	}
	
	//Get Active Quizzes Inside a Category
	
	@GetMapping("/category/active/{cId}")
	public ResponseEntity<?> getActiveQuizzesOfCategory(@PathVariable("cId") Long cId)
	{
		
		Category cat = new Category();
		cat.setId(cId);
		
		List<Quiz> activeQuizzes = quizReService.getActiveQuizzesOfCategory(cat, true);
		
		return ResponseEntity.ok(activeQuizzes);
	}
	
	
}
