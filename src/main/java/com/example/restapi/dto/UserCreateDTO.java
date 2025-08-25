package com.example.restapi.dto;

import com.example.restapi.model.User;

public class UserCreateDTO {
    private String name;
    private String email;


    public UserCreateDTO(){    }

    public UserCreateDTO(String name,String email){
        this.name=name;
        this.email=email;
    }
     
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }
}
