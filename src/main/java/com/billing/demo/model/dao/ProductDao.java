package com.billing.demo.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.billing.demo.model.entity.Product;

public interface ProductDao extends CrudRepository<Product, Long> {
	
	@Query("select p from Product p where p.name like %?1%")
	public List<Product> findByNombre(String term);
	
	public List<Product> findBynameLikeIgnoreCase(String term);

}
