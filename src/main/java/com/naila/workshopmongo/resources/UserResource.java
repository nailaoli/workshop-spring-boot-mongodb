package com.naila.workshopmongo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.naila.workshopmongo.domain.Post;
import com.naila.workshopmongo.domain.User;
import com.naila.workshopmongo.dto.UserDTO;
import com.naila.workshopmongo.services.UserService;

@RestController
@RequestMapping(value="/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> list = service.findAll();
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
//		"ok" retorna o código http 200
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {
		User user = service.findById(id);
		return ResponseEntity.ok().body(new UserDTO(user));
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody UserDTO userDto) {
		User user = service.fromDTO(userDto);
		user = service.insert(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{Id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).build();
//		"created" retorna o código http 201, que é o código de quando se cria um recurso
//		A uri do objeto criado irá no header
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
//		"no content" retorna o código http 204, que é quando não retorna nada
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody UserDTO userDto, @PathVariable String id) {
		User user = service.fromDTO(userDto);
		user.setId(id);
		user = service.update(user);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}/posts", method=RequestMethod.GET)
	public ResponseEntity<List<Post>> findPosts(@PathVariable String id) {
		User user = service.findById(id);
		return ResponseEntity.ok().body(user.getPosts());
//		O atributo lista de posts no User é lazy, porém como chamamos o .getPosts() no endpoint, essa lista será
//		carregada
	}
	
}
