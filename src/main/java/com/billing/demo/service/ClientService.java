package com.billing.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.billing.demo.model.entity.Client;


public interface ClientService {

	public List<Client> findAll();
	
	public Page<Client> findAllPageable(Pageable pageable);
	
	public void  save (Client client);
	
	public Client findOne(Long id);
	
	public void delete(Long id); 
}
