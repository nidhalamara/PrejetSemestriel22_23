package com.example.backend.Models.Users;

import com.example.backend.Configurations.UserHistory;
import com.example.backend.Operation.Operation;
import com.example.backend.Security.PasswordSecurity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

@Document(collection = "Users")
public class User {
    public UserHistory userHistory;
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
    public void addToHistory(Operation o){
        this.userHistory.save(o);

    }


    public String encryptPassword(){
        return passwordManipulator.encrypt(this.password,this.firstName+this.token);
    }
    public String decryptPassword(){
        return  passwordManipulator.decrypt(this.password,this.firstName+this.token);
    }

}
