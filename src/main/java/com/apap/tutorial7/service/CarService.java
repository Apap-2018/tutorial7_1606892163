package com.apap.tutorial7.service;

import java.util.List;
import java.util.Optional;

import com.apap.tutorial7.model.CarModel;

/**
 * CarService
 * @author rico.putra
 * @version 2/10/18
 */
public interface CarService {
	Optional<CarModel> getDetailCarById(Long id);
	
	CarModel addCar(CarModel car);
	
	void deleteCarById(Long id);
	
	void updateCar(Long carId, CarModel carNew);
	
	void deleteCar(CarModel car);
	
	List<CarModel> getAllCar();
}
