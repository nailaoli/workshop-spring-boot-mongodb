package com.naila.workshopmongo.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naila.workshopmongo.domain.Post;
import com.naila.workshopmongo.repository.PostRepository;
import com.naila.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	public List<Post> findAll() {
		return postRepository.findAll();
	}
	
	public Post findById(String id) {
		Optional<Post> obj = postRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public List<Post> findByTitle(String text){
		return postRepository.findByTitleContainingIgnoreCase(text);
	}
	
	public List<Post> findByTitleComRegex(String text){
		return postRepository.findByTitleManual(text);
	}
	
	public List<Post> fullSearch(String text, LocalDate min, LocalDate max){
//		A data máxima precisa ser acrescentada de um dia, para que seja menor ou igual
//		a meia noite do próximo dia! Porque o Date considera a data como o instante
//		da meia noite daquele dia
		max.plusDays(1);
		return postRepository.fullSearch(text, min, max);
	}

}
