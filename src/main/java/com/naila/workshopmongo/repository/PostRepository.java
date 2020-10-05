package com.naila.workshopmongo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.naila.workshopmongo.domain.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String>{
	
//	Ver sobre Query Methods na documentação do Spring Data: 
//	https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/
//	Apenas colocando o título seguindo as regras da especificação o Spring Data já
//	gera a query automaticamente!
	List<Post> findByTitleContainingIgnoreCase(String text);
	
}
