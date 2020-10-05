package com.naila.workshopmongo.resources;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.naila.workshopmongo.domain.Post;
import com.naila.workshopmongo.resources.util.URL;
import com.naila.workshopmongo.services.PostService;

@RestController
@RequestMapping(value="/posts")
public class PostResource {
	
	@Autowired
	private PostService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Post>> findAll() {
		List<Post> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Post> findById(@PathVariable String id) {
		Post post = service.findById(id);
		return ResponseEntity.ok().body(post);
	}
	
//	Modelo da URL: http://localhost:8080/posts/titlesearch?text=bom%20dia
//	O texto virá como parâmetro (@ResquestParam(value="text")), e não um PathVariable
//	value="text" é o nome do parâmetro que vem da requisição (URL). Caso não venha esse parâmetro,
//	o defaultValue é vazio.
	@RequestMapping(value="/titlesearch", method=RequestMethod.GET)
	public ResponseEntity<List<Post>> findByTitle(@RequestParam(value="text", defaultValue="") String text) {
		String decodedText = URL.decodeParam(text);
		List<Post> posts = service.findByTitle(decodedText);
		return ResponseEntity.ok().body(posts);
	}
	
//	Igual o método anterior, mas utilizando consulta ao BD com
//	@Query e regex manual
	@RequestMapping(value="/searchtitle", method=RequestMethod.GET)
	public ResponseEntity<List<Post>> searchTitle(@RequestParam(value="text", defaultValue="") String text) {
		String decodedText = URL.decodeParam(text);
		List<Post> posts = service.findByTitleComRegex(decodedText);
		return ResponseEntity.ok().body(posts);
	}
	
	@RequestMapping(value="/fullsearch", method=RequestMethod.GET)
	public ResponseEntity<List<Post>> fullSearch(
			@RequestParam(value="text", defaultValue="") String text,
			@RequestParam(value="minDate", defaultValue="") String minDateText,
			@RequestParam(value="maxDate", defaultValue="") String maxDateText) {
		String decodedText = URL.decodeParam(text);
		LocalDate minDate = URL.convertDate(minDateText, LocalDate.ofEpochDay(0L));
		LocalDate maxDate = URL.convertDate(maxDateText, LocalDate.now());
		List<Post> posts = service.fullSearch(decodedText, minDate, maxDate);
		return ResponseEntity.ok().body(posts);
	}
	
}
