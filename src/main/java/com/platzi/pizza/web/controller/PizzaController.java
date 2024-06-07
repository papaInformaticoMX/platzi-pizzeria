package com.platzi.pizza.web.controller;

import java.util.List;

import com.platzi.pizza.service.dto.UpdatePizzaPriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import com.platzi.pizza.service.PizzaService;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController{
    @Autowired
    private  PizzaService pizzaService;
    
    @GetMapping
    public ResponseEntity<Page<PizzaEntity>> getAll(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "8") int elements){
        return ResponseEntity.ok(pizzaService.getAll(page, elements));
    }

    @GetMapping("/{idPizza}")
    public ResponseEntity<PizzaEntity> gEntity(@PathVariable int idPizza){
        return ResponseEntity.ok(this.pizzaService.get(idPizza));
    }

    @GetMapping("/name/{pizzaName}")
    public ResponseEntity<PizzaEntity> gEntity(@PathVariable String pizzaName){
        return ResponseEntity.ok(this.pizzaService.getPizzaByName(pizzaName));
    }
    @GetMapping("/with/{ingredient}")
    public ResponseEntity<List<PizzaEntity>> getWith(@PathVariable String ingredient){
        return ResponseEntity.ok(this.pizzaService.getWith(ingredient));
    }

    @GetMapping("/without/{ingredient}")
    public ResponseEntity<List<PizzaEntity>> getWithout(@PathVariable String ingredient){
        return ResponseEntity.ok(this.pizzaService.getWithout(ingredient));
    }

    @GetMapping("/cheapest/{price}")
    public ResponseEntity<List<PizzaEntity>> getCheapest(@PathVariable double price){
        return ResponseEntity.ok(pizzaService.getCheapest(price));
    }

    @GetMapping("/available")
    public ResponseEntity<List<PizzaEntity>> gEntity(){
        return ResponseEntity.ok(this.pizzaService.getAvailable());
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

        @PutMapping("/price")
    public ResponseEntity<PizzaEntity> updatePrice(@RequestBody UpdatePizzaPriceDto dto){
        if( pizzaService.exists(dto.getPizzaId())){
            pizzaService.updatePrice(dto);
            return ResponseEntity.ok().build();
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