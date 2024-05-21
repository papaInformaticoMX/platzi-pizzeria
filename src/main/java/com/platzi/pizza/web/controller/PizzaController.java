package com.platzi.pizza.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import com.platzi.pizza.service.PizzaService;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController{
    @Autowired
    private  PizzaService pizzaService;
    
    @GetMapping
    public ResponseEntity<List<PizzaEntity>> getAll(){
        return ResponseEntity.ok(pizzaService.getAll());
    }

    @GetMapping("/{idPizza}")
    public ResponseEntity<PizzaEntity> gEntity(@PathVariable int idPizza){
        return ResponseEntity.ok(this.pizzaService.get(idPizza));
    }

    @PostMapping
    public ResponseEntity<PizzaEntity> save(@RequestBody PizzaEntity pizzaEntity){
        if(pizzaEntity.getIdPizza() == null || !pizzaService.exists(pizzaEntity.getIdPizza())){
            return ResponseEntity.ok( pizzaService.save(pizzaEntity));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping
    public ResponseEntity<PizzaEntity> update(@RequestBody PizzaEntity pizzaEntity){
        if(  pizzaEntity.getIdPizza() != null && pizzaService.exists(pizzaEntity.getIdPizza())){
            return ResponseEntity.ok(pizzaService.save(pizzaEntity));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{idPizza}")
    public ResponseEntity<?> delete(@PathVariable int idPizza){
        try{
            if(pizzaService.exists(idPizza)){
                pizzaService.delete(idPizza);
                return ResponseEntity.ok().build();
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PizzaId no exists");

        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

}