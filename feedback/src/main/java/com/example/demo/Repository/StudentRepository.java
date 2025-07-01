package com.example.demo.Repository;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.*;

import com.example.demo.Model.Cources;
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
		   jdbc.update(query,student.getName(),student.getRoll_no(),student.getEmail(),student.getDep(),id);	   
	   }
	   
	   public void delete(int id) {
		   String query="delete from Student where student_id=?";
		   jdbc.update(query,id);
	   }
	   
	   public String addCourceForStudent(int student_id,List<Cources> courceList) {
		   String getCourseId ="select * from Cources where cource_name= ? ";
		   String insetCource="insert into Cources(cource_name) values(?)";
		   String insertStudentCource="insert into Student_Cource(student_id,cource_id) values(?,?)";
		   
		   try {
			   for(var cource:courceList) {
				   List<Integer> ids = jdbc.query(getCourseId,new Object[] {cource.getCource_name()},(rs,newRow)-> rs.getInt("cource_id"));
				   int cource_id;
				   if(ids.isEmpty()) {
					   
					   KeyHolder keyholdes = new GeneratedKeyHolder();
					   
					   jdbc.update(connection->{
		    			     PreparedStatement ps = connection.prepareStatement(insetCource,Statement.RETURN_GENERATED_KEYS);
		    			     ps.setString(1, cource.getCource_name());
		    			     return ps;
		    		     },keyholdes);
					   
					   cource_id=keyholdes.getKey().intValue();
					   
				   }else {
					   cource_id=ids.get(0);
				   }
				   jdbc.update(insertStudentCource,student_id,cource_id);  
			   }
			   return "Cources was already added in database";
		   }
		   catch(Exception e) {
			   System.out.println(e);
			   return "Cource already stored in database";
		   }
	   }
	   
	   public List<String> getAllCource() {
		   String query="select cource_name "
		   		+ " from Cources as c "
		   		+ " inner join Student_Cource as s"
		   		+ " on c.cource_id=s.cource_id ";
		   
		   return jdbc.query(query,(rs,rowNom)->rs.getString("cource_name"));
	   }
	   
	   public List<String> getCourceOfStudentById(int student_id){
	    	 String query="select cource_name "
	    	 		+ " from Cources as c "
	    	 		+ " join Student_Cource as s "
	    	 		+ " on c.cource_id=s.cource_id "
	    	 		+ " where s.student_id=?";
	    	 
	    	 return jdbc.query(query,new Object[] {student_id},(rs,rowNom)->rs.getString("cource_name"));
	     }
	   
	   public void deleteCources(int student_id,List<Cources> courceList) {
		        String query="delete s "
		        		+ " from Student_Cource  as s "
		        		+ " left join Cources as c "
		        		+ " on c.cource_id=s.cource_id "
		        		+ " where s.student_id=? and c.cource_name=?";
		        
		        for(var i:courceList) {
		        	jdbc.update(query,student_id,i.getCource_name());
		        }
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