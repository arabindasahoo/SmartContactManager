package com.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.entity.Product;
import com.demo.helper.XMLHelper;
import com.demo.service.ProductService;

@RestController
public class Productcontroller {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/product/upload")
	public ResponseEntity<?> saveProducts(@RequestParam("file") MultipartFile file) throws IOException
	{
		
		boolean checkXMLFileFormat = XMLHelper.checkXMLFileFormat(file);
		
		if(checkXMLFileFormat)
		{
			productService.saveProduct(file.getInputStream());
			return ResponseEntity.status(HttpStatus.CREATED).body("Products are saved successfully into data base");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not a Xml file..Upload a XML file");
		
	}
	
	
	@GetMapping("/findAll")
	public ResponseEntity<List<Product>> findAllProduct()
	{
		List<Product> allProducts = productService.getAllProducts();
		return ResponseEntity.ok(allProducts);
	}

}
