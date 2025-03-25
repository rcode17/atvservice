package com.bancolombia.pocatv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bancolombia.pocatv.dto.AreaRequestDto;
import com.bancolombia.pocatv.model.AtvffUser;
import com.bancolombia.pocatv.service.AtvffUserService;

import java.util.List;

@RestController
@RequestMapping("/api/atvffuser")
public class AtvffUserController {

    @Autowired
    private AtvffUserService atvffUserService;

    @GetMapping
    public List<AtvffUser> getAllUsers() {
        return atvffUserService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtvffUser> getUserById(@PathVariable String id) {
        return atvffUserService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AtvffUser createUser(@RequestBody AtvffUser atvffUser) {
        return atvffUserService.save(atvffUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtvffUser> updateUser(@PathVariable String id, @RequestBody AtvffUser atvffUser) {
        return atvffUserService.findById(id)
                .map(existingUser -> {
                    atvffUser.setXuUser(existingUser.getXuUser());
                    return ResponseEntity.ok(atvffUserService.save(atvffUser));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        if (atvffUserService.findById(id).isPresent()) {
            atvffUserService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    

    @PostMapping("/login")
    public ResponseEntity<AtvffUser> login(@RequestBody AtvffUser user) {
    	AtvffUser loggedInUser = atvffUserService.findByXuuser(user.getXuUser(), user.getXuPass());
        return ResponseEntity.ok(loggedInUser);
    }
    
    
    @PutMapping("/{xuUser}/area")
    public ResponseEntity<AtvffUser> updateAreaUser(
            @PathVariable String xuUser,
            @RequestBody AreaRequestDto requestArea) {

    	AtvffUser updatedUsuario = atvffUserService.updateAreaOperativa(xuUser, requestArea.getArea());
        if (updatedUsuario != null) {
            return ResponseEntity.ok(updatedUsuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}