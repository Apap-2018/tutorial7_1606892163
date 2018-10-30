package com.apap.tutorial7.controller;

import com.apap.tutorial7.model.*;
import com.apap.tutorial7.service.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * CarController
 * @author rico.putra
 * @version 2/10/18
 */
@Controller
public class CarController {
	@Autowired
	private CarService carService;
	
	@Autowired
	private DealerService dealerService;
	
	@RequestMapping(value = "/car/add/{dealerId}")
	private String add(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		DealerModel dealer = new DealerModel();
		List<CarModel> listCar = new ArrayList<CarModel>();
		listCar.add(new CarModel());
		dealer.setListCar(listCar);
		
		model.addAttribute("listCar", listCar);
		model.addAttribute("dealer", dealer);
		model.addAttribute("dealerId", dealerId);
		model.addAttribute("pageTitle", "Add Car");
		
		return "addCar";
	}
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.POST, params={"addRow"})
	private String addRow(@PathVariable(value = "dealerId") Long dealerId, @ModelAttribute DealerModel dealer, Model model) {
		CarModel car = new CarModel();
		List<CarModel> listCar = null;
		
		if (dealer.getListCar() != null) {
			listCar = dealer.getListCar();
		}
		else {
			listCar = new ArrayList<CarModel>();
		}
		listCar.add(car);
		dealer.setListCar(listCar);
		model.addAttribute("listCar", listCar);
		model.addAttribute("dealer", dealer);
		model.addAttribute("dealerId", dealerId);
		model.addAttribute("pageTitle", "Add Car");
		
		return "addCar";
	}
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.POST, params= {"removeRow"})
	private String removeRow(@PathVariable(value = "dealerId") Long dealerId, @ModelAttribute DealerModel dealer, Model model, HttpServletRequest req) {
		Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
		dealer.getListCar().remove(rowId.intValue());
		
		model.addAttribute("dealer", dealer);
		model.addAttribute("dealerId", dealerId);
		
		return "addCar";
	}
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.POST, params= {"save"})
	private String addCarSave(@PathVariable(value = "dealerId") Long dealerId, @ModelAttribute DealerModel dealer, Model model) {
		DealerModel oldDealer = dealerService.getDealerDetailById(dealerId).get();
		
		for (CarModel car : dealer.getListCar()) {
			car.setDealer(oldDealer);
			carService.addCar(car);
		}
		model.addAttribute("pageTitle", "Add Car Succeed");
		return "add";
	}
	
	@RequestMapping(value = "/car/delete/{idCar}", method = RequestMethod.GET)
	private String deleteCar(@PathVariable(value = "idCar") Long carId, Model model) {
		carService.deleteCarById(carId);
		model.addAttribute("pageTitle", "Delete Car Succeed");
		return "delete";
	}
	
	@RequestMapping(value = "/car/update/{idCar}", method = RequestMethod.GET)
	private String updateCar(@PathVariable(value = "idCar") Long carId, Model model) {
		CarModel carOld = carService.getDetailCarById(carId).get();
		model.addAttribute("carOld", carOld);
		model.addAttribute("carNew", new CarModel());
		model.addAttribute("pageTitle", "Update Car");
		return "updateCar";
	}
	
	@RequestMapping(value = "/car/update/{idCar}", method = RequestMethod.POST)
	private String updateCar(@ModelAttribute CarModel carNew, @PathVariable(value = "idCar") Long id, Model model) {
		carService.updateCar(id, carNew);
		model.addAttribute("pageTitle", "Update Car Succeed");
		return "update";
	}
}
