package com.billing.demo.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.billing.demo.model.entity.Client;

@Repository
public class ClientDaoImpl  {
	@PersistenceContext
	private EntityManager em;
	


}
