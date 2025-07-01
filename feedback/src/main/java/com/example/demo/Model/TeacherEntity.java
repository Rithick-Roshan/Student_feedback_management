package com.example.demo.Model;

public class TeacherEntity {
     private int teacher_id ;
     private String name;
     private String email;
     private String password;
     private String dep;

	 public String getName() {
		 return name;
	 }
	 public void setName(String name) {
		 this.name = name;
	 }
	 public String getEmail() {
		 return email;
	 }
	 public void setEmail(String email) {
		 this.email = email;
	 }
	 public String getDep() {
		 return dep;
	 }
	 public void setDep(String dep) {
		 this.dep = dep;
	 }
	 public int getTeacher_id() {
		return teacher_id;
	 }
	 public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	 }
	 public String getPassword() {
		return password;
	 }
	 public void setPassword(String password) {
		this.password = password;
	 }
}
