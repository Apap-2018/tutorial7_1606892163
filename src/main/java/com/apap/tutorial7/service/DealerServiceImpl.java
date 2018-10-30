package com.apap.tutorial7.service;

import java.util.Optional;
import java.util.List;

import com.apap.tutorial7.model.DealerModel;
import com.apap.tutorial7.repository.DealerDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * DealerServiceImpl
 * @author rico.putra
 * @version 2/10/18
 */
@Service
@Transactional
public class DealerServiceImpl implements DealerService {
	@Autowired
	private DealerDb dealerDb;
	
	@Override
	public Optional<DealerModel> getDealerDetailById(Long id) {
		return dealerDb.findById(id);
	}
	
	@Override
	public DealerModel addDealer(DealerModel dealer) {
		dealerDb.save(dealer);
		return dealer;
	}
	
	@Override 
	public List<DealerModel> getListDealer() {
		return dealerDb.findAll();
	}
	
	@Override
	public void deleteById(Long id) {
		dealerDb.delete(this.getDealerDetailById(id).get());
	}
	
	@Override
	public void updateById(Long id, DealerModel dealerNew) {
		DealerModel dealerOld = this.getDealerDetailById(id).get();
		dealerOld.setAlamat(dealerNew.getAlamat());
		dealerOld.setNoTelp(dealerNew.getNoTelp());
	}
}
