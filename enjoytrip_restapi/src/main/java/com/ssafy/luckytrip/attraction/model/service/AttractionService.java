package com.ssafy.luckytrip.attraction.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.luckytrip.attraction.model.AttractionDto;
import com.ssafy.luckytrip.attraction.model.SidoGugunCodeDto;

public interface AttractionService {
	
	public List<SidoGugunCodeDto> getSidos() throws Exception;
	
	public List<SidoGugunCodeDto> getGuguns(int sido) throws Exception;
	
	public List<AttractionDto> getAttractionList(Map<String, String> map) throws Exception;
	
	public AttractionDto getAttractionDetail(int contentId) throws Exception;
}
