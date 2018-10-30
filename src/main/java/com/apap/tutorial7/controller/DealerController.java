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
 * @version 2/10/18
 */
@Controller
public class DealerController {
	@Autowired
	private DealerService dealerService;
	
	@Autowired
	private CarService carService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("pageTitle", "Home");
		return "home";
	}
	
	/**
	 * Masuk ke halaman "Tambah Dealer"
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/dealer/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("dealer", new DealerModel());
		model.addAttribute("pageTitle", "Add Dealer");
		return "addDealer";
	}
	
	/**
	 * Dipanggil ketika tombol submit pada halaman "Tambah Dealer" ditekan,
	 * yaitu ketika berhasil menambahkan dealer baru
	 * @param dealer
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/dealer/add", method = RequestMethod.POST)
	private String addDealerSubmit(@ModelAttribute DealerModel dealer, Model model) {
		dealerService.addDealer(dealer);
		model.addAttribute("pageTitle", "Add Succeed");
		return "add";
	}
	
	/**
	 * Dipanggil ketika tombol "Cari" di home ditekan,
	 * untuk mengambil data Dealer yang dicari
	 * @param dealerId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/dealer/view", method = RequestMethod.GET)
	private String viewDealerById(@RequestParam(value = "dealerId") Long dealerId, Model model) {
		
		DealerModel dealer = null;
		List<CarModel> listCar = null;
		
		if (dealerService.getDealerDetailById(dealerId).isPresent()) {
			dealer = dealerService.getDealerDetailById(dealerId).get();
			listCar = dealer.getListCar();
			Collections.sort(listCar);
		}
		dealer.setListCar(listCar);
		model.addAttribute("dealer", dealer);
		model.addAttribute("listCar", listCar);
		model.addAttribute("pageTitle", "View Dealer");
		return "view-dealer";
	}
	
	/**
	 * Menampilkan seluruh dealer
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/dealer/view-all")
	private String viewAllDealer(Model model) {
		List<DealerModel> listDealer = null;
		String adaDealer = "Dealer Kosong";
		
		if (!dealerService.getListDealer().isEmpty()) {
			listDealer = dealerService.getListDealer();
			model.addAttribute("listDealer", listDealer);
			adaDealer = "View All Dealer";
		}
		model.addAttribute("pageTitle", adaDealer);
		return "view-allDealer";
	}
	
	/**
	 * Menghapus setiap mobil milik suatu dealer yang checkbox hapus-nya di-check
	 * lalu tombol "Hapus yang dipilih" ditekan.
	 * Ada pada halaman view-dealer.html
	 * @param dealer
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/car/delete", method = RequestMethod.POST)
	private String delete(@ModelAttribute DealerModel dealer, Model model) {
		for (CarModel car : dealer.getListCar()) {
			carService.deleteCar(car);
		}
		model.addAttribute("pageTitle", "Delete Car Succeed");
		return "delete";
	}
	
	/**
	 * Menghapus dealer yang dipilih utk dihapus pada halaman view-all
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/dealer/delete/{dealerId}")
	private String deleteDealerById(@PathVariable(value = "dealerId") Long id, Model model) {
		dealerService.deleteById(id);
		model.addAttribute("pageTitle", "Delete Dealer");
		return "delete";
	}
	
	/**
	 * Menghapus satu dealer ketika tombol "Hapus Dealer" yang ada pada halaman view-dealer.html ditekan
	 * -> ex": /dealer/delete?dealerId=4
	 * @param dealerId
	 * @return
	 */
	@RequestMapping(value = "/dealer/delete")
	private String deleteDealer(@RequestParam(value = "dealerId") Long dealerId, Model model) {
		dealerService.deleteById(dealerId);
		model.addAttribute("pageTitle", "Delete Dealer Succeed");
		return "delete";
	}
	
	@RequestMapping(value = "/dealer/update/{dealerId}")
	private String updateDealer(@PathVariable(value = "dealerId") Long id, Model model) {
		DealerModel dealerOld = dealerService.getDealerDetailById(id).get();
		model.addAttribute("dealerOld", dealerOld);
		model.addAttribute("dealerNew", new DealerModel());
		model.addAttribute("pageTitle", "Update Dealer");
		return "updateDealer";
	}
	
	@RequestMapping(value = "/dealer/update/{dealerId}", method = RequestMethod.POST)
	private String updateDealer(@ModelAttribute DealerModel dealerNew, @PathVariable(value = "dealerId") Long id, Model model) {
		dealerService.updateById(id, dealerNew);
		model.addAttribute("pageTitle", "Update Dealer Berhasil");
		return "update";
	}
}
