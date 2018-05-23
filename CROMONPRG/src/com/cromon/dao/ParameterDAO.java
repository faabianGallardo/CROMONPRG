package com.cromon.dao;

import java.util.List;

import com.cromon.entity.Parameter;


public interface ParameterDAO {

	public void save(Parameter parametro);

	public Parameter getParameter(int id);

	public List<Parameter> list();

	public void remove(Parameter parametro);

	public void update(Parameter parametro);
	
}
