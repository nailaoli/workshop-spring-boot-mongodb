package com.naila.workshopmongo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.naila.workshopmongo.domain.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String>{
	
//	Ver sobre Query Methods na documentação do Spring Data: 
//	https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/
//	Apenas colocando o título seguindo as regras da especificação o Spring Data já
//	gera a query automaticamente!
	List<Post> findByTitleContainingIgnoreCase(String text);
	
//	Query abaixo utilizando o padrão JSON do MongoDB. 
//	Referência: https://docs.mongodb.com/manual/reference/operator/query/regex/
//	?0 significa o primeiro parâmetro recebido pelo método
	@Query("{ 'title': { $regex: ?0, $options: 'i' } }")
	List<Post> findByTitleManual(String text);
	
//	Busca no título, ou corpo do post, ou comentários, entre datas
//	Referência: https://docs.mongodb.com/manual/reference/operator/query
	@Query("{ $and: [ { date: {$gte: ?1} }, { date: { $lte: ?2} } , { $or: [ { 'title': { $regex: ?0, $options: 'i' } }, { 'body': { $regex: ?0, $options: 'i' } }, { 'comments.text': { $regex: ?0, $options: 'i' } } ] } ] }")
	List<Post> fullSearch(String text, LocalDate minData, LocalDate maxDate);
}
