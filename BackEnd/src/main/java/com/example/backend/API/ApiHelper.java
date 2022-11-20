package com.example.backend.API;

import com.example.backend.Configurations.UsersMongoRepo;
import com.example.backend.Models.Users.User;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("/4200")
public class ApiHelper {
   private  UsersMongoRepo userRep ;
   @PostMapping("/signUp")
    public boolean createUser(@RequestBody User u){
       if(userRep.findUserByEmail(u.email)!=null){
           User newUser=new User();
           newUser.firstName=u.firstName;
           newUser.email=u.email;
           newUser.token=u.token;
           newUser.lastName=u.lastName;
           newUser.setPassword(u.password);
           userRep.save(newUser);
           return true;
       }
       return false;
   }
    @GetMapping("/checkUser")
    public boolean tryCreateUser(@RequestBody String e){
        return userRep.findUserByEmail(e) == null;
    }
    
}
