package com.bancolombia.pocatv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bancolombia.pocatv.dto.UsuarioProductoDTO;
import com.bancolombia.pocatv.dto.UsuarioRequestDTO;
import com.bancolombia.pocatv.dto.UsuarioResponseDTO;
import com.bancolombia.pocatv.model.AtvffPdo;
import com.bancolombia.pocatv.model.AtvffUser;
import com.bancolombia.pocatv.model.Xbknam;
import com.bancolombia.pocatv.service.AtvffUserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/atvffuser")
public class AtvffUserController {

    @Autowired
    private AtvffUserService atvffUserService;

    @GetMapping
    public ResponseEntity<Page<UsuarioResponseDTO>> getAllUsers(
            @RequestParam(required = false) String xuUser,
            Pageable pageable) {

        Page<AtvffUser> usersPage = atvffUserService.getAllUsers(xuUser, pageable);

        Page<UsuarioResponseDTO> dtoPage = usersPage.map(user -> new UsuarioResponseDTO(
                user.getXuUser(),
                user.getXuName(),
                user.getXuCarg(),
                user.getXuAcce(),
                user.getXuDom(),
                user.getXuUsrt(),
                user.getXuPass(),
                user.getXuArea()
        ));

        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtvffUser> getUserById(@PathVariable String id) {
        return atvffUserService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AtvffUser> createUser(@RequestBody UsuarioRequestDTO usuarioRequest) {
    	AtvffUser createdUser = atvffUserService.registrarUsuario(usuarioRequest);
    	return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtvffUser> updateUser(@PathVariable String id, @RequestBody UsuarioRequestDTO usuarioRequest) {
        return atvffUserService.findById(id)
                .map(existingUser -> {
                	usuarioRequest.setXuUser(existingUser.getXuUser());
                    return ResponseEntity.ok(atvffUserService.registrarUsuario(usuarioRequest));
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
    
    @GetMapping("/{userId}/areas")
    public ResponseEntity<Set<Xbknam>> getUserAreas(@PathVariable("userId") String userId) {
        Set<Xbknam> areas = atvffUserService.obtenerAreasUsuario(userId);
        return ResponseEntity.ok(areas);
    }
    
    
    @PutMapping("/{xuUser}/areas")
    public ResponseEntity<AtvffUser> AddAreasUser(
            @PathVariable String xuUser,
            @RequestBody List<BigDecimal> areaIds) {
        AtvffUser usuarioActualizado = atvffUserService.addAreasUser(xuUser, areaIds);
        return ResponseEntity.ok(usuarioActualizado);
    }
    
    @DeleteMapping("/{xuUser}/areas")
    public ResponseEntity<AtvffUser> eliminarAreasDeUsuario(
            @PathVariable String xuUser,
            @RequestBody List<BigDecimal> areaIds) {

        AtvffUser usuarioActualizado = atvffUserService.deleteAreasUser(xuUser, areaIds);
        return ResponseEntity.ok(usuarioActualizado);
    }
    
    
    @PostMapping("/{xuUser}/productos")
    public ResponseEntity<AtvffUser> addProductosUser(
            @PathVariable("xuUser") String xuUser,
            @RequestBody List<UsuarioProductoDTO> productosRequest) {

        AtvffUser updatedUser = atvffUserService.addProductoUser(xuUser, productosRequest);
        return ResponseEntity.ok(updatedUser);
    }
    
    
    @DeleteMapping("/{xuUser}/productos")
    public ResponseEntity<AtvffUser> deleteProductosUser(
            @PathVariable("xuUser") String xuUser,
            @RequestBody List<UsuarioProductoDTO> productosRequest) {

        AtvffUser updatedUser = atvffUserService.deleteProductoUser(xuUser, productosRequest);
        return ResponseEntity.ok(updatedUser);
    }
    
    
    @GetMapping("/{xuuser}/productos")
    public ResponseEntity<Set<AtvffPdo>> getProductosByUser(@PathVariable("xuuser") String xuUser) {
        Set<AtvffPdo> productos = atvffUserService.getProductosByUser(xuUser);
        return ResponseEntity.ok(productos);
    }
    
    
}