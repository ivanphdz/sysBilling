package com.billing.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.billing.demo.model.entity.Client;
import com.billing.demo.model.entity.Invoice;
import com.billing.demo.service.ClientService;

@Controller
@RequestMapping("/invoice")
@SessionAttributes("invoice")
public class InvoiceController {
	
	@Autowired
	private ClientService clientService;
	
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
}
