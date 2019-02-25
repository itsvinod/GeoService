package com.geodetails.application.controller;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.geodetails.application.model.ResponseResultModel;
import com.geodetails.application.service.GeoRepesponseService;

@RestController
public class GeoRepesponseController {

	Logger logger = LoggerFactory.getLogger(GeoRepesponseController.class);
	
	@Autowired
	GeoRepesponseService geoRepesponseService;
	
	@Value("${geoapp.errormsg}")
	String errorMsg;
	
	//for testing the timeout exception
	@Value("${wait.millis}")
	Long waitTime;
	
	
	/*
	 *  Dummy service for Geo Details 
	*/
	@GetMapping(path = "/geodetails")
	public ResponseResultModel getGeoDetails() {
		return geoRepesponseService.getGeoDetails();
	}

	
	@GetMapping(path = "/geocode2")
	public String getCityDetails(@RequestParam  String ip) {
		logger.info("geocode2 ip= {}",ip);
		RestTemplate rt =new RestTemplate();
		final String uri = "http://localhost:8080/geodetails";
		ResponseResultModel rsmd=rt.getForObject(uri, ResponseResultModel.class);
		String addrs[]=rsmd.getResults()[0].getFormattedAddressLines();
		return addrs[0]+","+addrs[1]+","+addrs[2];
	}
	
	
	@GetMapping(path = "/geocode")
	public Callable<String> getCityDetails2(@RequestParam  String ip) throws Exception {
	    return new Callable<String>() {
	        @Override
	        public String call() throws Exception{
	        	logger.info(" ip= {}",ip);
	    		RestTemplate rt =new RestTemplate();
	    		//for testing timeout excpetion
	    		if (!"192.168.1.2".equals(ip)) {
	    			Thread.sleep(waitTime);
	    		}
	    		final String uri = "http://localhost:8080/geodetails";
	    		ResponseResultModel rsmd=rt.getForObject(uri, ResponseResultModel.class);
	    		String addrs[]=rsmd.getResults()[0].getFormattedAddressLines();
	    		return addrs[0]+","+addrs[1]+","+addrs[2];
	        	//return "City , New Delhi";
	        }
	    };
	}
	
	
	//ToDo - move to GeoExceptionHandler			
	@ExceptionHandler
	@ResponseBody
	public String handleException(Exception ex) {
		return errorMsg;
	}
	
}