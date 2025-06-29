package com.example.demo.Repository;

import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.sql.*;
import com.example.demo.Model.*;

@Repository

public class TeacherRepository {
     @Autowired
     private JdbcTemplate jdbc;
     
     public void save(TeacherEntity teacher) {
    	 String query="insert into Teacher (name,email,password,dep) values (?,?,?,?)";
    	 jdbc.update(query,teacher.getName(),teacher.getEmail(),teacher.getPasswoprd(),teacher.getDep());
     }
     

     public List<TeacherEntity> findall(){
    	 String query="select * from Teacher";
    	 return jdbc.query(query, new TeacherRowMapping());
     }

     public TeacherEntity findById(int id) {
    	 String query="select * from teacher where teacher_id=?";
    	 return jdbc.queryForObject(query, new Object[] {id},new TeacherRowMapping());
     }
     

     public void update(int id,TeacherEntity teacher) {
    	 String query="update Teacher set name=?,email=?,password=?,dep=?";
    	 jdbc.update(query,teacher.getName(),teacher.getEmail(),teacher.getPasswoprd(),teacher.getDep());
     }
     

     public void delete(int id) {
    	 String query ="delete Teacher where student_id=?";
    	 jdbc.update(query);
     }
}



class TeacherRowMapping implements RowMapper<TeacherEntity>{
	public TeacherRowMapping() {}
	
	@Override
	public TeacherEntity mapRow(ResultSet rs, int rowNow) throws SQLException{
		TeacherEntity teacher = new TeacherEntity();
		teacher.setTeacher_id(rs.getInt("teacher_id"));
		teacher.setName(rs.getString("name"));
		teacher.setEmail(rs.getString("email"));
		teacher.setPasswoprd(rs.getString("password"));
		teacher.setDep(rs.getString(rs.getString("dep")));
		return teacher;
	}
}
