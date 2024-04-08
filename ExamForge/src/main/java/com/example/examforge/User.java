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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
