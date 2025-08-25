package com.example.restapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.example.restapi.dto.UserCreateDTO;
import com.example.restapi.dto.UserResponseDTO;
import com.example.restapi.model.User;
import com.example.restapi.repository.UserRepository;
import com.example.restapi.service.UserNotFoundException;
import com.example.restapi.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

 /* 
    //in memory data store
   // private List<User> users=new  ArrayList<>();
   
   @Autowired
   private UserRepository userRepository;
    // Constructor injection (best practice)
   public UserController(UserRepository userRepository){
    this.userRepository=userRepository;
   }
/**
     * Get all users or filter by name
     * Uses database-level filtering (NOT in-memory filtering)
     
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers(
          @RequestParam(required=false) Long id,
          @RequestParam(required=false) String name){
           
            List<User> users;
             if (name != null) {
            users = userRepository.findByNameIgnoreCase(name);
        } else {
            users = userRepository.findAll();
        }
 
              List<UserResponseDTO> responseDTOs=userRepository.findAll().stream()
              .map(user->new UserResponseDTO(user.getId(), user.getName(),user.getEmail()))
              .collect(Collectors.toList());
              return ResponseEntity.ok(responseDTOs);
          }

    /**
     * Get a specific user by ID
     * Returns 404 if user not found
     
          @GetMapping("/{id}")
          public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id){
              return userRepository.findById(id)
              .map((user->new UserResponseDTO(user.getId(),user.getName(),user.getEmail())))
              .map(ResponseEntity::ok)
              .orElse(ResponseEntity.notFound().build());
          }
    
          /**
     * Create a new user
     * Validates input using DTO constraints
     * Returns 201 Created with the new user data
     
    //create a new user
    @PostMapping
    public ResponseEntity<UserResponseDTO>createUser(
       @Valid @RequestBody UserCreateDTO createDTO){//validate user data
    User user= new User();
    user.setName(createDTO.getName()); // set the name 
    user.setEmail(createDTO.getEmail());  //set email

     User savedUser=userRepository.save(user);//sping data jpa saves it 

     UserResponseDTO responseDTO=new UserResponseDTO(
                              savedUser.getId(),//id is assined to the data 
                              savedUser.getName(),//name is assigned 
                              savedUser.getEmail());//email is assigned

     return ResponseEntity
                  .status(HttpStatus.CREATED)
                  .body(responseDTO);
    }
    /// we need to createa @pathvaribale and pass the id name 
    /// email basically fields or attributes of model or entity
    //// then we run the loop and get the matching id form the request 
    ///  we then create username and email using setter method
      
     /** "NEW CODE WITH DTO AND RESPONSEENTITY"
     * Update an existing user
     * CRITICAL: Uses UserCreateDTO which has NO ID field
     * This prevents the ObjectOptimisticLockingFailureException
     * Supports partial updates (only provided fields are updated)
     
    @PutMapping("/{id}")
      public ResponseEntity<UserResponseDTO> updateUser(
                                        @PathVariable Long id,
                                        @Valid @RequestBody UserCreateDTO updateDTO){
                  return userRepository.findById(id)
                        .map(existinguser->{
                          if(updateDTO.getName()!=null){
                            existinguser.setName(updateDTO.getName());
                          }
                          if(updateDTO.getEmail()!=null){
                            existinguser.setEmail(updateDTO.getEmail());
                          }
                         User updateUser=userRepository.save(existinguser);

                         return ResponseEntity.ok
                         (new UserResponseDTO(
                            updateUser.getId(),
                            updateUser.getEmail(),
                            updateUser.getName()
                         )
                         );
                        })
                      .orElse(ResponseEntity.notFound().build());
                    }
                      

      @DeleteMapping("/{id}")
      public ResponseEntity<Void> deleteUser(@PathVariable Long id){
           if(userRepository.existsById(id)){
            userRepository.deleteById(id);
              return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }  */

      private final UserService userService;

      public UserController(UserService userService){
        this.userService=userService;
      }
      //get /users by filter name

      @GetMapping
      public ResponseEntity<List<UserResponseDTO>> getAllUser(
        @RequestParam(required = false) String name) {    
      List<UserResponseDTO> users = userService.getAllUsers(name);
      return ResponseEntity.ok(users);
     }
    //Get a specific user by ID
     @GetMapping("/{id}")
     public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id){
      try{
        UserResponseDTO user=userService.getUserById(id);
        return ResponseEntity.ok(user);
      }catch(UserNotFoundException e){
        return ResponseEntity.notFound().build();
      }
     }

     //POST /users - Create a new user

     @PostMapping
     public ResponseEntity<UserResponseDTO> createUser(
      @Valid @RequestBody UserCreateDTO createDTO
     ){
      try{
        UserResponseDTO responseDTO=userService.createUser(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
      }catch(IllegalArgumentException e){
        return ResponseEntity.badRequest().build();
      }
     }

     //put operation
     @PutMapping("/{id}")
     public ResponseEntity <UserResponseDTO> updateUser(
      @PathVariable Long id,
      @Valid @RequestBody UserCreateDTO updateDTO
     ){
      try{
        UserResponseDTO responseDTO=userService.updateUser(id, updateDTO);
        return ResponseEntity.ok(responseDTO);
      }  catch (UserNotFoundException e){
          return ResponseEntity.notFound().build();
        }
        catch(IllegalArgumentException e){
          return ResponseEntity.badRequest().build();
        }
      
     }

     //deleteing user
     @DeleteMapping("/{id}")
     public ResponseEntity<Void> deleteUser(
      @PathVariable Long id
     ){
      try{
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
      }catch(UserNotFoundException e){
        return ResponseEntity.notFound().build();
      }
     }
    }
