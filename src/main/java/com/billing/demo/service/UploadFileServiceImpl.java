package com.billing.demo.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements UploadFileService{

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final static String UPLOADS_FOLDER = "uploads";
	
	@Override
	public Resource load(String filename) throws MalformedURLException {	
		Path pathPhoto = this.gePath(filename);
		log.info("pathPhoto: " + pathPhoto);
		Resource resource = null;
		resource = new UrlResource(pathPhoto.toUri());
		if(!resource.exists() || !resource.isReadable()) {
			throw new RuntimeException("Error: no se puede cargar imagen");
		}
		return resource;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path pathPhoto = this.gePath(filename);		
		log.info("rootPath: " + pathPhoto);
		Files.copy(file.getInputStream(), pathPhoto);
		return filename;
	}

	@Override
	public boolean delete(String filename) {
		Path pathPhoto = this.gePath(filename);
		File file = pathPhoto.toFile();
		
		if(file.exists() && file.canRead()) {
			if(file.delete()) {
				return true; 
			}
		}
		return false;
	}
	
	public Path gePath(String filename) {
		return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath(); 
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
	}

	@Override
	public void init() throws IOException {
		Files.createDirectory(Paths.get(UPLOADS_FOLDER));
	}

}
