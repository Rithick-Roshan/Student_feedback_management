package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Model.StudentEntity;
import com.example.demo.Repository.StudentRepository;
import java.util.*;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private StudentRepository studentrepo;
	
	@GetMapping("/all")
	public List<StudentEntity> getAllDetails() {
		return  studentrepo.findAll();
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<StudentEntity> getDetail(@PathVariable int id){
		StudentEntity student =  studentrepo.findById(id);
		return student!=null?ResponseEntity.ok(student):ResponseEntity.notFound().build();
	}
	
	@PostMapping("/create")
	public ResponseEntity<Void> createStudent(@RequestBody StudentEntity student){
		studentrepo.save(student);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateUser(@PathVariable int id,StudentEntity student){
		  studentrepo.update(id,student);
		  return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable int id){
		studentrepo.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
       
}
