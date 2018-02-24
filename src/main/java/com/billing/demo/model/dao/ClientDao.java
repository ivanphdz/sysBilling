package com.billing.demo.model.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.billing.demo.model.entity.Client;

public interface ClientDao extends PagingAndSortingRepository<Client, Long>{

	
}
