package com.in28minutes.rest.webservices.restfulwebservices;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restfulwebservices.todo.Todo;
import com.in28minutes.rest.webservices.restfulwebservices.todo.TodoHardcodedService;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class TodoResource {
	
	@Autowired
	private TodoHardcodedService todoService;
	
	//http://localhost:8080//users/test123/todos
	@GetMapping("/users/{userName}/todos")
	public List<Todo> getAllTodos(@PathVariable String userName) {
		
		return todoService.findAll();
	}
	
	@GetMapping("/users/{userName}/todos/{id}")
	public Todo getTodo(@PathVariable String userName, @PathVariable long id) {
		
		return todoService.findAll(id);
	}
	
	
	//http://localhost:8080//users/test123/todos/1
	@DeleteMapping("/users/{userName}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String userName, @PathVariable long id) {
	 
		Todo todo = todoService.deleteById(id);
		if(todo != null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();  
	}
	
	@PutMapping("/users/{userName}/todos/{id}")
	public ResponseEntity<Todo> updateTodo(
			@PathVariable String userName,
			@PathVariable long id,
			@RequestBody Todo todo) {
	 
		Todo todoUpdated = todoService.save(todo);
		return new ResponseEntity<Todo>(todo, HttpStatus.OK);  
	}
	
	@PostMapping("/users/{userName}/todos")
	public ResponseEntity<Object> updateTodo(
			@PathVariable String userName,
			@RequestBody Todo todo) {
	 
		Todo createdTodo = todoService.save(todo);
		//Location
		//Get current resource URL
		//append {id} to the url  /users/{userName}/todos/ 
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(createdTodo.getId())
		.toUri();
		
				
		return ResponseEntity.created(uri).build();  
	}
	
	
 
}
