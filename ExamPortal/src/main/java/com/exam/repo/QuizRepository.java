package com.exam.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long>{

	public List<Quiz> findByCategory(Category cat);
	
	public List<Quiz> findByActive(boolean b);
	
	public List<Quiz> findByCategoryAndActive(Category cat,Boolean b);
	

}
