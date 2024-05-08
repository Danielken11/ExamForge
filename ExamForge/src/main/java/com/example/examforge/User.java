package com.example.examforge;

public class User {

   public String email;
   public String login;
   public String password;
   public String status;

   public User(String email,String login,String password,String status){
       this.email = email;
       this.login = login;
       this.password = password;
       this.status = status;
   }
}
