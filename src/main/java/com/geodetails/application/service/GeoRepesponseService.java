package com.geodetails.application.service;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geodetails.application.model.ResponseResultModel;

@Service
public class GeoRepesponseService {

	public ResponseResultModel getGeoDetails() {
		ObjectMapper objectMapper = new ObjectMapper();
		ResponseResultModel rsmdl = null;
		try {
			Resource resource = new ClassPathResource("SampleResponse.json");
			String srcJsonPath = resource.getURL().getPath();
			rsmdl = objectMapper.readValue(new File(srcJsonPath), ResponseResultModel.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rsmdl;
	}

	public String getCityDetails() {
		
		return "city11";
	}
}