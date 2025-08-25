package com.example.restapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// we using validation to define the rules for your data so that incoorect or incomplete 
/// inputs are automatically rejected before they even reach your bussiness 
@Entity
@Table(name="users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "name cannot be empty")    
    private String name;
    @Email(message = "email must be valid")
    private String email;
 
    public User(){}
    public User(Long id,String name,String email){
        this.id=id;
        this.name=name;
        this.email=email;
    }

    public Long getId(){
        return id;   
     }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }


    ///we use setter for hiding internal details of the class and contol how values are changed
    ///  we can use validation here or check befor updating
    public void setId(Long id){
        this.id=id;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setEmail(String email){
        this.email=email;
    }   
}