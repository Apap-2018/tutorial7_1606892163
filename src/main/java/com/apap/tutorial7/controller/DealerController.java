package com.apap.tutorial7.controller;

import java.util.Collections;
import java.util.List;
import com.apap.tutorial7.model.*;
import com.apap.tutorial7.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * DealerController
 * @author rico.putra
 * @version 30/10/18
 */
@RestController
@RequestMapping("/dealer")
public class DealerController {
	@Autowired
	private DealerService dealerService;
	
	@PostMapping(value = "/add")
	private DealerModel addDealerSubmit(@RequestBody DealerModel dealer) {
		return dealerService.addDealer(dealer);
	}
	
	@GetMapping(value = "{dealerId}")
	private DealerModel viewDealer(@PathVariable("dealerId") long dealerId, Model model) {
		return dealerService.getDealerDetailById(dealerId).get();
	}
	
	@DeleteMapping(value = "/delete")
	private String deleteDealer(@RequestParam("dealerId") long id, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(id).get();
		dealerService.deleteById(id);
		return "Success";
	}
	
	@PutMapping(value = "{id}")
	private String updateDealerSubmit(	@PathVariable(value = "id") long id,
										@RequestParam("alamat") String alamat,
										@RequestParam("telp") String telp) {
		DealerModel dealer = (DealerModel) dealerService.getDealerDetailById(id).get();
		
		if (dealer.equals(null)) {
			return "Couldn't find your dealer";
		}
		
		dealer.setAlamat(alamat);
		dealer.setNoTelp(telp);
		dealerService.updateById(id, dealer);
		return "update success";
		
	}
	
	@GetMapping()
	private List<DealerModel> viewAllDealer(Model model) {
		return dealerService.getListDealer();
	}
}
