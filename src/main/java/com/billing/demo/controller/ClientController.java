package com.billing.demo.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.billing.demo .model.entity.Client;
import com.billing.demo.service.ClientService;
import com.billing.demo.service.UploadFileService;
import com.billing.demo.util.paginator.PageRender;

@Controller
@SessionAttributes("client")
@RequestMapping("/client")
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private UploadFileService uploadFileService;
		
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list (@RequestParam(name="page", defaultValue="0") int page, Model model) {
		Pageable pageRequest = new PageRequest(page, 5);
		Page<Client> clients = clientService.findAllPageable(pageRequest);
		PageRender<Client> pageRender = new PageRender<>("/client/list", clients); 
		model.addAttribute("title", "Clients' List");
		model.addAttribute("clients", clients);
		model.addAttribute("page", pageRender);
		return "client/list";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String create (Model model) {
		model.addAttribute("title", "Create Client");
		Client client = new Client();
		model.addAttribute("client", client);
		return "client/create";
	}

	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String save (@Valid Client client, BindingResult result, Model model, @RequestParam("file") MultipartFile photo, SessionStatus status, RedirectAttributes flash) {
		if(result.hasErrors()) {
			model.addAttribute("title", "Create Client");
			return "client/create";
		}
		if(!photo.isEmpty()) {
			if(client.getId() != null && client.getId() > 0 && client.getPhoto() != null) {
				uploadFileService.delete(client.getPhoto());
			}
			
			String uniqueFilename = null;	

			try {
				uniqueFilename = uploadFileService.copy(photo);
				flash.addAttribute("info", "Succesfully uploaded '" + uniqueFilename + "'");
				client.setPhoto(uniqueFilename);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		clientService.save(client);
		status.setComplete();
		flash.addFlashAttribute("success", "Client successfully saved.");
		return "redirect:/client/list";
	}
	
	@RequestMapping(value="/{id}/edit")
	public String edit(@PathVariable(value="id") Long id, Model model, RedirectAttributes flash) {
		Client client = null;
		if(id > 0) {
			client = clientService.findOne(id);
			if(client == null) {
				flash.addFlashAttribute("error", "ID doesn't exists.");
				return "redirect:/client/list";
			}
		} else {
			flash.addFlashAttribute("error", "ID doesn't exists.");
			return "redirect:/client/list";
		}
		model.addAttribute("client", client);
		model.addAttribute("titulo", "Edit Client");
		return "client/create";
	}
		
	
	
	@RequestMapping(value="/{id}/delete")
	public String delete(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		if(id > 0) {
			Client client  = clientService.findOne(id);
			clientService.delete(id);
			flash.addFlashAttribute("success", "Client successfully eliminated.");
			if(uploadFileService.delete(client.getPhoto())) {
				flash.addFlashAttribute("info", "Client's photo successfully eliminated.");
			}
		}
		return "redirect:/client/list";
	}

	@GetMapping(value="/ver/{id}")
	public String view(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Client client = clientService.findOne(id);
		if(client == null) {
			flash.addAttribute("error", "Client doesn't exists in the database");
			return "redirect:/client/list";
		}
		model.put("client", client);
		model.put("title", "Client details " + client.getName());
		return "view";
	}
	
	@GetMapping(value="/UPLOAD_FOLDER/{filename:.+}")
	public ResponseEntity<Resource> getPhoto(@PathVariable String filename){
		Resource resource = null;
		try {
			resource = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
	}
}