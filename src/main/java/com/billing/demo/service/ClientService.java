package com.billing.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.billing.demo.model.entity.Client;
import com.billing.demo.model.entity.Invoice;
import com.billing.demo.model.entity.Product;


public interface ClientService {

	public List<Client> findAll();
	
	public Page<Client> findAllPageable(Pageable pageable);
	
	public void  save (Client client);
	
	public Client findOne(Long id);
	
	public void delete(Long id); 
	
	public List<Product> findByNombre(String term);
	
	public void saveInvoice(Invoice invoice);
	
	public Product findProductById(Long id);
	
	public Invoice findInvoiceById(Long id);
	
	public void deleteInvoice(Long id);
	
	public Invoice fetchInvoiceByIdWithClientWithItemWithProduct(Long id);
	
	public Client fetchByIdWithInvoices(Long id);
}
