package com.ssafy.luckytrip.attraction.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.luckytrip.attraction.model.AttractionDto;
import com.ssafy.luckytrip.attraction.model.SidoGugunCodeDto;

@Mapper
public interface AttractionMapper {
	
	public List<SidoGugunCodeDto> getSidos() throws SQLException;
	
	public List<SidoGugunCodeDto> getGuguns(int sido) throws SQLException;
	
	public List<AttractionDto> getAttractionList(Map<String, String> map) throws SQLException;
	
	public AttractionDto getAttractionDetail(int contentId) throws SQLException;
	
}
