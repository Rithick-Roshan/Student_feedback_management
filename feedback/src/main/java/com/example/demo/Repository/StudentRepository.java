package com.example.demo.Repository;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.*;

import com.example.demo.Model.StudentEntity;

import java.sql.*;

@Repository
public class StudentRepository {
	   @Autowired
       private JdbcTemplate jdbc;
	   
	   public void save(StudentEntity student) {
		   String query ="insert into Student(name,roll_no,email,password,dep) values (?,?,?,?,?)";
		   jdbc.update(query,student.getName(),student.getRoll_no(),student.getEmail(),student.getPassword(),student.getDep());
	   }
	   
	   public List<StudentEntity> findAll(){
		   String query = "select * from Student";
		     return jdbc.query(query,new StudentRowMapper());
		
	   }
	   
	   public StudentEntity findById(int id) {
		   String query ="select * from Student where student_id=?";
		   return jdbc.queryForObject(query,new Object[] {id},new StudentRowMapper());
	   }
	   
	   public void update(int id,StudentEntity student) {
		   String query ="update Student set name=?,roll_no=?,email=?,dep=? where student_id=?";
		   System.out.println(student.getName()+student.getRoll_no()+student.getEmail()+student.getPassword());
		   jdbc.update(query,student.getName(),student.getRoll_no(),student.getEmail(),student.getDep(),student.getPassword());	   
	   }
	   
	   public void delete(int id) {
		   String query="delete from Student where student_id=?";
		   jdbc.update(query,id);
	   }
	   
	   
	   
       
}


class StudentRowMapper implements RowMapper<StudentEntity>{
	public StudentRowMapper() {}
	 @Override
	 public StudentEntity mapRow(ResultSet rs,int rowNum) throws SQLException{
		 StudentEntity student = new StudentEntity();
		 student.setName(rs.getString("name"));
		 student.setEmail(rs.getString("email"));
		 student.setId(rs.getInt("student_id"));
		 student.setRoll_no(rs.getString("roll_no"));
		 student.setPassword(rs.getString("password"));
		 student.setDep(rs.getString("dep"));
		 
		 return student;
	 }
}