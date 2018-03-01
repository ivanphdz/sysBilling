package com.billing.demo.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {
	
	public Resource load(String filenmae) throws MalformedURLException;
	
	public String copy(MultipartFile file) throws IOException;
	
	public boolean delete (String filename);
	
	public void deleteAll();
	
	public void init() throws IOException;
}
