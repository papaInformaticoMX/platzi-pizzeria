package com.platzi.pizza.service;

import java.util.List;

import com.platzi.pizza.persistence.repository.PizzaPagSortRepository;
import com.platzi.pizza.service.dto.UpdatePizzaPriceDto;
import com.platzi.pizza.service.exception.EmailApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import com.platzi.pizza.persistence.repository.PizzaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PizzaService {
    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private PizzaPagSortRepository pizzaPagSortRepository;

    public Page<PizzaEntity> getAll(int page, int elements){
        Pageable pageRequest = PageRequest.of(page, elements, Sort.by("name"));
        return  pizzaPagSortRepository.findAll(pageRequest); //pizzaRepository.findAll();
    }

    public List<PizzaEntity> getAvailable(){
        return this.pizzaRepository.findAllByAvailableTrueOrderByPrice();
    }

    public PizzaEntity getPizzaByName(String name){
        this.pizzaPagSortRepository.
        //return this.pizzaRepository.findFirstByAvailableTrueAndNameIgnoreCase(name).orElse(null);
        return this.pizzaRepository.findFirstByAvailableTrueAndNameIgnoreCase(name).orElseThrow(()->new RuntimeException("La pizza no existe"));
    }

    public List<PizzaEntity> getWith(String ingredient){
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionContainingIgnoreCase(ingredient);
    }


    public List<PizzaEntity> getWithout(String ingredient){
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(ingredient);
    }


    public List<PizzaEntity> getCheapest(double price){
        return pizzaRepository.findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(price);
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

    private void sendEmail(){
        throw new EmailApiException();
    }

    @Transactional(noRollbackFor = EmailApiException.class, propagation = Propagation.REQUIRED)
    public void updatePrice(UpdatePizzaPriceDto dto){
        pizzaRepository.updatePrice(dto);
        sendEmail();
    }

    public void delete(Integer pizzaId){
            pizzaRepository.deleteById(pizzaId);
    }
    
}
