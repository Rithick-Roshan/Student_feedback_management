package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Model.Cources;
import com.example.demo.Model.StudentEntity;
import com.example.demo.Repository.Obeject;
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
	public ResponseEntity<Void> updateStudent(@PathVariable int id,@RequestBody StudentEntity student){
		  studentrepo.update(id,student);
		  return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable int id){
		studentrepo.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
	@PostMapping("/addCources/{id}")
	public ResponseEntity<String> addStudentCource(@PathVariable("id") int student_id,@RequestBody List<Cources> courcesList ){
		String str = studentrepo.addCourceForStudent(student_id, courcesList);
		return ResponseEntity.ok(str);
	}
	
	@GetMapping("/getAllStudentCources")
	public List<String> getAllStudentCources(){
		return studentrepo.getAllCource();
	}
	
    @GetMapping("/getCource/{id}")
    public List<String> getStudentCourceById(@PathVariable("id") int student_id){
    	  return studentrepo.getCourceOfStudentById(student_id);
    }
	
	@DeleteMapping("/deleteCource/{id}")
	public ResponseEntity<String> deleteCourceById(@PathVariable("id") int student_id,@RequestBody List<Cources> courceList){
		for(var i:courceList) {
			System.out.println(student_id+" "+i.getCource_name());
		}
		studentrepo.deleteCources(student_id, courceList);
		return ResponseEntity.ok("Cources was deleted");
	}
}
