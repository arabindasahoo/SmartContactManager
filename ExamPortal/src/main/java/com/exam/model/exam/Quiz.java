package com.exam.model.exam;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long qid;
	
	private String title;
	
	private String description;
	
	private String maxOfMarks;
	
	private String noOfQuestions;
	
	private boolean active = false;

	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "quiz")
	@JsonIgnore
	private Set<Question> questions = new HashSet<>();
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Category category;

	public Quiz(String title, String description, String maxOfMarks, String noOfQuestions, boolean active) {
		super();
		this.title = title;
		this.description = description;
		this.maxOfMarks = maxOfMarks;
		this.noOfQuestions = noOfQuestions;
		this.active = active;
	}

	public Quiz() {
		super();
	}

	public Long getQid() {
		return qid;
	}

	public void setQid(Long qid) {
		this.qid = qid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMaxOfMarks() {
		return maxOfMarks;
	}

	public void setMaxOfMarks(String maxOfMarks) {
		this.maxOfMarks = maxOfMarks;
	}

	public String getNoOfQuestions() {
		return noOfQuestions;
	}

	public void setNoOfQuestions(String noOfQuestions) {
		this.noOfQuestions = noOfQuestions;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
