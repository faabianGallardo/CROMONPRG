package com.cromon.dao;

import java.util.List;

import com.cromon.entity.Repeatedsheet;


public interface RepeatedsheetDAO {

	public void save(Repeatedsheet repetida);

	public Repeatedsheet getRepeatedsheet(int id);

	public List<Repeatedsheet> list();

	public void remove(Repeatedsheet repetida);

	public void update(Repeatedsheet repetida);
	
}
