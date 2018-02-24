package com.billing.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.billing.demo.model.dao.ClientDao;
import com.billing.demo.model.entity.Client;

@Service
public class ClientServiceImpl implements ClientService{

	@Autowired
	private ClientDao clientDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Client> findAll() {
		return (List<Client>) clientDao.findAll();
	}

	@Override
	@Transactional
	public void save(Client client) {
		clientDao.save(client);
	}

	@Override
	@Transactional(readOnly = true)
	public Client findOne(Long id) {
		return clientDao.findOne(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clientDao.delete(id);
	}

	@Override
	public Page<Client> findAllPageable(Pageable pageable) {
		return clientDao.findAll(pageable);
	}


}
