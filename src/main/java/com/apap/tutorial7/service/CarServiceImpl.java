package com.apap.tutorial7.service;

import java.util.List;
import java.util.Optional;

import com.apap.tutorial7.model.CarModel;
import com.apap.tutorial7.repository.CarDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CarServiceImpl
 * @author rico.putra
 * @version 2/10/18
 */
@Service
@Transactional
public class CarServiceImpl implements CarService {
 @Autowired
 private CarDb carDb;
 
 @Override
 public Optional<CarModel> getDetailCarById(Long id) {
	 return carDb.findById(id);
 }
 
 
 
 @Override
 public void addCar(CarModel car) {
	 carDb.save(car);
 }
 
 @Override
 public void deleteCarById(Long id) {
	 carDb.delete(this.getDetailCarById(id).get());
 }
 
 @Override
 public void updateCar(Long id, CarModel carNew) {
	 CarModel carOld = this.getDetailCarById(id).get();
	 carOld.setBrand(carNew.getBrand());
	 carOld.setType(carNew.getType());
	 carOld.setPrice(carNew.getPrice());
	 carOld.setAmount(carNew.getAmount());
 }
 
 @Override
 public void deleteCar(CarModel car) {
	 carDb.delete(car);
 }
}
