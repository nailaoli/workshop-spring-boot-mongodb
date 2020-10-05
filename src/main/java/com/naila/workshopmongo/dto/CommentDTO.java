package com.naila.workshopmongo.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CommentDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String text;
	private LocalDateTime date;
	private AuthorDTO author;
	
	public CommentDTO() {}

	public CommentDTO(String text, LocalDateTime date, AuthorDTO author) {
		super();
		this.text = text;
		this.date = date;
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public AuthorDTO getAuthor() {
		return author;
	}

	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}
	
}
