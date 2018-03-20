package com.billing.demo.model.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.billing.demo.model.entity.Client;

public interface ClientDao extends PagingAndSortingRepository<Client, Long>{

	@Query("select c from Client c left join fetch c.lstInvoice f where c.id=?1")
	public Client fetchByIdWithInvoice(Long id);
	
}
