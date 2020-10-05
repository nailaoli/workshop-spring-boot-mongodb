package com.naila.workshopmongo.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.naila.workshopmongo.domain.Post;
import com.naila.workshopmongo.domain.User;
import com.naila.workshopmongo.dto.AuthorDTO;
import com.naila.workshopmongo.dto.CommentDTO;
import com.naila.workshopmongo.repository.PostRepository;
import com.naila.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public void run(String... args) throws Exception {
		userRepository.deleteAll();
		postRepository.deleteAll();
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Post post1 = new Post(null, LocalDate.parse("21/03/2018", formatter), "Partiu viagem", "Vou viajar para São Paulo. Abraços!", new AuthorDTO(maria));
		Post post2 = new Post(null, LocalDate.parse("23/03/2018", formatter), "Bom dia", "Acordei feliz hoje!", new AuthorDTO(maria));
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		post1.getComments().add(new CommentDTO("Boa viagem, mano!", LocalDateTime.parse("2018-03-21 10:15:30", formatter2), new AuthorDTO(alex)));
		post1.getComments().add(new CommentDTO("Aproveite", LocalDateTime.parse("2018-03-22 15:15:00", formatter2), new AuthorDTO(bob)));
		post2.getComments().add(new CommentDTO("Tenha um ótimo dia!", LocalDateTime.parse("2018-03-23 07:12:30", formatter2), new AuthorDTO(alex)));
		postRepository.saveAll(Arrays.asList(post1, post2));
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		userRepository.save(maria);
	}
}
