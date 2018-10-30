package com.apap.tutorial7.repository;

import java.util.List;
import com.apap.tutorial7.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * CarDb
 * @author rico.putra
 * @version 2/10/18
 */
public interface CarDb extends JpaRepository<CarModel, Long> {
	CarModel findByType(String type);
	
	void delete(CarModel car);
}
