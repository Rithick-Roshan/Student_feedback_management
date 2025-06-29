package com.example.demo.Controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Model.TeacherEntity;
import com.example.demo.Repository.TeacherRepository;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
	
	@Autowired
	private TeacherRepository teacherrepo;
	
	@GetMapping("/all")
	public  List<TeacherEntity> getDetails(){
		return teacherrepo.findall();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TeacherEntity> getById(@PathVariable int id){
		TeacherEntity teacher = teacherrepo.findById(id);
		return teacher!=null?ResponseEntity.ok(teacher):ResponseEntity.notFound().build();
	}
	
	@PostMapping("/create")
	public ResponseEntity<String> createUser(@RequestBody TeacherEntity teacher){
		teacherrepo.save(teacher);
		return ResponseEntity.ok("Your information was sucessfully registered");
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateUser(@PathVariable int id,TeacherEntity teacher){
		teacherrepo.update(id, teacher);
		return ResponseEntity.ok("User detail was updated");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable int id){
		teacherrepo.delete(id);
		return ResponseEntity.ok("User detail was deleted");
	}

	

}
