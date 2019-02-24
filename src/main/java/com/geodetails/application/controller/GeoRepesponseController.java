package com.geodetails.application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.geodetails.application.model.ResponseResultModel;
import com.geodetails.application.service.GeoRepesponseService;

@RestController
public class GeoRepesponseController {

	Logger logger = LoggerFactory.getLogger(GeoRepesponseController.class);
	
	@Autowired
	GeoRepesponseService geoRepesponseService;
	
	//@Autowired RestTemplate rt;

	@GetMapping(path = "/geodetails")
	public ResponseResultModel getGeoDetails() {
		return geoRepesponseService.getGeoDetails();
	}

	
	@GetMapping(path = "/citydetails")
	public String getCityDetails(@RequestParam  String ip) {
		logger.info(" ip= {}",ip);
		RestTemplate rt =new RestTemplate();
		final String uri = "http://localhost:8080/geodetails";
		ResponseResultModel rsmd=rt.getForObject(uri, ResponseResultModel.class);
		String addrs[]=rsmd.getResults()[0].getFormattedAddressLines();
		return addrs[0]+","+addrs[1]+","+addrs[2];
	}
}