package com.ssafy.luckytrip.attraction.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.luckytrip.attraction.model.AttractionDto;
import com.ssafy.luckytrip.attraction.model.SidoGugunCodeDto;
import com.ssafy.luckytrip.attraction.model.mapper.AttractionMapper;

@Service
public class AttractionServiceImpl implements AttractionService {
	private AttractionMapper attractionMapper;

	public AttractionServiceImpl(AttractionMapper attractionMapper) {
		super();
		this.attractionMapper = attractionMapper;
	}
	
	@Override
	public List<SidoGugunCodeDto> getSidos() throws Exception {
		return attractionMapper.getSidos();
	}
	
	@Override
	public List<SidoGugunCodeDto> getGuguns(int sido) throws Exception {
		return attractionMapper.getGuguns(sido);
	}

	@Override
	public List<AttractionDto> getAttractionList(Map<String, String> map) throws Exception {
		return attractionMapper.getAttractionList(map);
	}

	@Override
	public AttractionDto getAttractionDetail(int contentId) throws Exception {
		return attractionMapper.getAttractionDetail(contentId);
	}
}
