package com.exam.exceptions;

public class UserFoundException extends Exception{
	
	public UserFoundException() {
		super("User found with this username");
	}
	
	public UserFoundException(String msg)
	{
		super(msg);
	}
}
