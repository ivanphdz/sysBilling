package com.billing.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.billing.demo.model.dao.ClientDao;
import com.billing.demo.model.dao.InvoiceDao;
import com.billing.demo.model.dao.ProductDao;
import com.billing.demo.model.entity.Client;
import com.billing.demo.model.entity.Invoice;
import com.billing.demo.model.entity.Product;

@Service
public class ClientServiceImpl implements ClientService{

	@Autowired
	private ClientDao clientDao;

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private InvoiceDao invoiceDao;
	
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
		return clientDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clientDao.deleteById(id);
	}

	@Override
	public Page<Client> findAllPageable(Pageable pageable) {
		return clientDao.findAll(pageable);
	}

	@Override
	public List<Product> findByNombre(String term) {
		return productDao.findByNombre(term);
	}

	@Override
	@Transactional
	public void saveInvoice(Invoice invoice) {
		invoiceDao.save(invoice);
	}

	@Override
	@Transactional(readOnly=true)
	public Product findProductById(Long id) {
		return productDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public Invoice findInvoiceById(Long id) {
		return invoiceDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteInvoice(Long id) {
		invoiceDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Invoice fetchInvoiceByIdWithClientWithItemWithProduct(Long id) {
		return invoiceDao.fetchByIdWithClientWithItemWithProduct(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Client fetchByIdWithInvoices(Long id) {
		return clientDao.fetchByIdWithInvoice(id);
	}

	
}
