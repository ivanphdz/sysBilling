package com.billing.demo.model.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.billing.demo.model.entity.Invoice;

public interface InvoiceDao extends CrudRepository<Invoice, Long>{

	@Query("select f from Invoice f join fetch f.client c join fetch f.lstItem l join fetch l.product where f.id=?1")
	public Invoice fetchByIdWithClientWithItemWithProduct(Long id);
}
