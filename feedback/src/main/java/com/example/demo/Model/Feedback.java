package com.example.demo.Model;

public class Feedback {
         private int feedback_id;
         private int student_id;
         private int techer_id;
         private int cource_id;
         private String feedback;
         private int rating;
		 public int getFeedback_id() {
			 return feedback_id;
		 }
		 public void setFeedback_id(int feedback_id) {
			 this.feedback_id = feedback_id;
		 }
		 public int getStudent_id() {
			 return student_id;
		 }
		 public void setStudent_id(int student_id) {
			 this.student_id = student_id;
		 }
		 public int getTecher_id() {
			 return techer_id;
		 }
		 public void setTecher_id(int techer_id) {
			 this.techer_id = techer_id;
		 }
		 public int getCource_id() {
			 return cource_id;
		 }
		 public void setCource_id(int cource_id) {
			 this.cource_id = cource_id;
		 }
		 public String getFeedback() {
			 return feedback;
		 }
		 public void setFeedback(String feedback) {
			 this.feedback = feedback;
		 }
		 public int getRating() {
			 return rating;
		 }
		 public void setRating(int rating) {
			 this.rating = rating;
		 }
}
