package com.example.demo.Repository;

import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
    	 jdbc.update(query,teacher.getName(),teacher.getEmail(),teacher.getPassword(),teacher.getDep());
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
    	 String query="update Teacher set name=?,email=?,password=?,dep=? where teacher_id="+id;
    	 jdbc.update(query,teacher.getName(),teacher.getEmail(),teacher.getPassword(),teacher.getDep());
     }
     

     public void delete(int id) {
    	 // if we directly try delete teacher sql trows an error . Because here teacher_id is foreign key reference so need delete child row first then delete the parent row
    	 String query0="delete from Teacher_Cource where teacher_id=?";
    	 String query1 ="delete  from Teacher where teacher_id=?";
    	 jdbc.update(query0,id);
    	 jdbc.update(query1,id);
     }
     
     public String addCourcesForTeacher(int teacherId,List<Cources> cource) {
    	 String query0="select * from Cources where cource_name=?";
    	 String query1="insert into Cources(cource_name) values(?)";
    	 String query2="insert into Teacher_Cource(teacher_id,cource_id) values(?,?)";
       try { 	 
    	 for(var i:cource) {
//    		 jdbc.update(query1,i.getCource_name());
//    		 jdbc.update(query2,id,i.getCource_id());
    		 
             List<Integer> ids = jdbc.query(query0,new Object[] {i.getCource_name()},(rs,rowNow)-> rs.getInt("cource_id"));
             int courceId;
             if(ids.isEmpty())
             {
    		 
    		     KeyHolder keyholdes = new GeneratedKeyHolder();
    		     jdbc.update(connection->{
    			     PreparedStatement ps = connection.prepareStatement(query1,Statement.RETURN_GENERATED_KEYS);
    			     ps.setString(1, i.getCource_name());
    			     return ps;
    		     },keyholdes);
    		     courceId=keyholdes.getKey().intValue();
             }
             else {
            	 courceId=ids.get(0);
             }
    		 
    		 jdbc.update(query2,teacherId,courceId);
    	 }
    	 return "Cources add sucessfully";
       }catch(Exception e) {
    	    System.out.println(e);
    	    return "Cources was already added in database";
       }
     }
     
     public List<String> getAllCoucesById(int teacher_id) {
    	 String query = "select cource_name "
    	 		+ " from Cources as c "
    	 		+ " inner join Teacher_Cource as t "
    	 		+ " on c.cource_id=t.cource_id "
    	 		+ " where t.teacher_id=? ";
    	 return jdbc.query(query,new Object[] {teacher_id},(rs,rowNom)->rs.getString("cource_name"));		 
    	 
     }
     
     public List<String> getAllCouces() {
    	 String query="select cource_name from Cources";
    	 return jdbc.query(query,(rs,rowNom)->rs.getString("cource_name"));
     }
     
     
     public void deleteCource(int teacher_id,List<Cources> courceList) {
    	 String query="delete t  "
    	 		+ " from Teacher_Cource as t "
    	 		+ " left join Cources  as c on t.cource_id=c.cource_id "
    	 		+ " where teacher_id=? and cource_name=?";
    	 for(var cource_name:courceList)
    	       jdbc.update(query,teacher_id,cource_name.getCource_name());
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
		teacher.setPassword(rs.getString("password"));
		teacher.setDep(rs.getString("dep"));
		return teacher;
	}
}
