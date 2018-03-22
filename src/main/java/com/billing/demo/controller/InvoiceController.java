package com.billing.demo.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.billing.demo.model.entity.Client;
import com.billing.demo.model.entity.Invoice;
import com.billing.demo.model.entity.Item;
import com.billing.demo.model.entity.Product;
import com.billing.demo.service.ClientService;

@Controller
@RequestMapping("/invoice")
@SessionAttributes("invoice")
public class InvoiceController {
	
	@Autowired
	private ClientService clientService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/form/{clientId}")
	public String create(@PathVariable(value="clientId") Long clientId,
			Map<String, Object> model, RedirectAttributes flash) {
		Client client = clientService.findOne(clientId);
		if(client == null) {
			flash.addFlashAttribute("error","Client doesn't exist");
			return "redirect:/list";
		}
		Invoice invoice = new Invoice();
		invoice.setClient(client);
		model.put("invoice", invoice);
		model.put("title", "Create invoice");
		return "invoice/form";
	}
	
	@GetMapping(value="/load-products/{term}", produces={"application/json"})
	public @ResponseBody List<Product> loadProducts(@PathVariable String term){
		return clientService.findByNombre(term);
	}
	
	@PostMapping("/form")
	public String save(@Valid Invoice invoice, BindingResult result, Model model,
			@RequestParam(name="item_id[]", required=false) Long[] itemId,
			@RequestParam(name="quantity[]", required=false) Integer[] quantity,
			RedirectAttributes flash,
			SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("title", "Create invoice");
			return "invoice/form";
		}
		if(itemId == null || itemId.length == 0) {
			model.addAttribute("title", "Create invoice");
			model.addAttribute("error", "Error: Invoice needs at least one line");
			return "invoice/form";
		}
		for(int i = 0; i < itemId.length; i++) {
			Product product = clientService.findProductById(itemId[i]);
			Item line = new Item();
			line.setProduct(product);
			line.setQuantity(quantity[i]);
			invoice.addItem(line);
			log.info("ID: " + itemId[i].toString() + ", quantity: " + quantity[i].toString());
		}
		clientService.saveInvoice(invoice);
		status.setComplete();
		flash.addFlashAttribute("success", "Invoice created correctly");
		return "redirect:/invoice/view/" + invoice.getClient().getId();
	} 
	
	@GetMapping(value="/view/{id}")
	public String view(@PathVariable(value="id") Long id, Model model,
			RedirectAttributes flash) {
		Invoice invoice =  clientService.fetchInvoiceByIdWithClientWithItemWithProduct(id); //clientService.findInvoiceById(id);
		if(invoice == null) {
			flash.addFlashAttribute("error", "Invoice doesn't exist");
			return "redirect:/client/list";
		}
		model.addAttribute("invoice", invoice);
		model.addAttribute("title", "Invoice: ".concat(invoice.getDescription()));
		return "invoice/view";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(value="id") Long id,
			RedirectAttributes flash) {
		Invoice invoice = clientService.findInvoiceById(id);
		if(invoice != null) {
			clientService.deleteInvoice(id);
			flash.addFlashAttribute("success", "Invoice created succesfully");
			return "redirect:/invoice/view/" + invoice.getClient().getId();
		}
		flash.addFlashAttribute("error", "Invoice doesn't exist");
		return "redirect:/client/list";
	}
}
