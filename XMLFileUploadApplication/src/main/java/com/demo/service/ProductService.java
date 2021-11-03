package com.demo.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entity.Product;
import com.demo.helper.XMLHelper;
import com.demo.repository.ProductRepo;


@Service
public class ProductService {
	
	@Autowired
	private ProductRepo productRepo;
	
	public void saveProduct(InputStream is)
	{
		List<Product> crateProductList = XMLHelper.crateProductList(is);
		productRepo.saveAll(crateProductList);
	}
	
	public List<Product> getAllProducts()
	{
		List<Product> findAll = productRepo.findAll();
		return findAll;
	}

}
