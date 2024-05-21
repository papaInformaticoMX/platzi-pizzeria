package com.platzi.pizza.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import com.platzi.pizza.persistence.repository.PizzaRepository;

@Service
public class PizzaService {
    @Autowired
    private PizzaRepository pizzaRepository;

    public List<PizzaEntity>getAll(){
        return pizzaRepository.findAll();

    }

    public PizzaEntity get(int idPizza){
        return this.pizzaRepository.findById(idPizza).orElse(null);
    }

    public PizzaEntity save(PizzaEntity pizzaEntity){
        return pizzaRepository.save(pizzaEntity);
    }

    public Boolean exists(Integer pizzaId){
        return pizzaRepository.existsById(pizzaId);
    }

    public void delete(Integer pizzaId){
            pizzaRepository.deleteById(pizzaId);
    }
    
}
