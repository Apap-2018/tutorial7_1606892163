package com.apap.tutorial7.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apap.tutorial7.model.CarModel;
import com.apap.tutorial7.model.DealerModel;
import com.apap.tutorial7.service.CarService;
import com.apap.tutorial7.service.DealerService;

/**
 * CarController
 * @author rico.putra
 * @version 2/10/18
 */
@RestController
@RequestMapping("/car")
public class CarController {
	@Autowired
	private CarService carService;
	
	@Autowired
	private DealerService dealerService;
	
	@PostMapping(value = "/add")
	private CarModel addCarSubmit(@RequestBody CarModel car) {
		return carService.addCar(car);
	}

	@GetMapping(value = "{carId}")
	private CarModel viewCar(@PathVariable("carId") long carId, Model model) {
		return carService.getDetailCarById(carId).get();
	}
	
	@GetMapping()
	private List<CarModel> viewAllCar(Model model) {
		return carService.getAllCar();
	}
	
	@DeleteMapping(value = "{carId}")
	private String deleteCar(@PathVariable("carId") long carId, Model model) {
		carService.deleteCarById(carId);
		return "car has been deleted";
	}
	
	@PutMapping(value = "{carId}")
	private String updateCar(	@PathVariable("carId") long carId,
								@RequestParam("brand") String brand,
								@RequestParam("type") String type,
								@RequestParam("price") Integer price,
								@RequestParam("amount") Integer amount,
								@RequestParam("dealerID") long dealerId,
								Model model) {
		CarModel car = (CarModel) carService.getDetailCarById(carId).get();
		
		car.setBrand(brand);
		car.setType(type);
		car.setPrice(price);
		car.setAmount(amount);
		car.setDealer(dealerService.getDealerDetailById(dealerId).get());
		carService.updateCar(carId, car);
		
		return "car update success";
	}
}
