package com.ssafy.luckytrip.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ssafy.luckytrip.plan.model.CourseDto;

public class ArrayTypeHandler extends BaseTypeHandler<List<CourseDto>> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<CourseDto> parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, new Gson().toJson(parameter));
	}

	@Override
	public List<CourseDto> getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return convertToList(rs.getString(columnName));
	}

	@Override
	public List<CourseDto> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return convertToList(rs.getString(columnIndex));
	}

	@Override
	public List<CourseDto> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return convertToList(cs.getString(columnIndex));
	}
	
	private List<CourseDto> convertToList(String listDataAsString) {
		try {
			return new ObjectMapper().readValue(listDataAsString, new TypeReference<List<CourseDto>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

}
