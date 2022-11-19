package com.example.backend.Models.Users;

import com.example.backend.Security.PasswordSecurity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
public class User {

    public String firstName;
    public String lastName;
    @Id
    String id;
    public String email;
    public String password;
    public String token;
    private PasswordSecurity passwordManipulator = new PasswordSecurity();
    public void setPassword(String p){
        this.password=encryptPassword();
    }
    public String getPassword(){
        return decryptPassword();
    }


    public String encryptPassword(){
        return passwordManipulator.encrypt(this.password,this.firstName+this.token);
    }
    public String decryptPassword(){
        return  passwordManipulator.decrypt(this.password,this.firstName+this.token);
    }

}
