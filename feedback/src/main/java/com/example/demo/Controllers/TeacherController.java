package com.example.demo.Controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Model.Cources;
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
	public ResponseEntity<String> createTeacher(@RequestBody TeacherEntity teacher){
		teacherrepo.save(teacher);
		return ResponseEntity.ok("Your information was sucessfully registered");
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateTeacher(@PathVariable int id,@RequestBody TeacherEntity teacher){
		teacherrepo.update(id, teacher);
		return ResponseEntity.ok("Teacher detail was updated");
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteTeacher(@PathVariable int id){
		teacherrepo.delete(id);
		return ResponseEntity.ok("Teacher detail was deleted");
	}
    @PostMapping("/addCource/{id}")
    public ResponseEntity<String> addTeacherCources(@PathVariable int id, @RequestBody List<Cources> cource ){
    	String str=teacherrepo.addCourcesForTeacher(id, cource);
    	return ResponseEntity.ok(str);
    }
	
    @GetMapping("/getCource/{id}")
    public List<String> getCourcesOfTeacher(@PathVariable("id") int teacherId){
    	System.out.println(teacherId);
    	return teacherrepo.getAllCoucesById(teacherId);
    }
    
    @GetMapping("/getAllCource")
    public List<String> getAllCource(){
    	return teacherrepo.getAllCouces();
    }
    
    @DeleteMapping("/deleteCource/{id}")
     public ResponseEntity<String> deleteCourceById(@PathVariable("id") int teacher_id,@RequestBody List<Cources> c){
    	
    	teacherrepo.deleteCource(teacher_id, c);
    	return ResponseEntity.ok("Course was deleted");
    }
}
