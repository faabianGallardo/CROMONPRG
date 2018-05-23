package com.cromon.dao;

import java.util.List;

import com.cromon.entity.Missingsheet;



public interface MissingsheetDAO {

	public void save(Missingsheet faltante);

	public Missingsheet getMissingsheet(int id);

	public List<Missingsheet> list();

	public void remove(Missingsheet faltante);

	public void update(Missingsheet faltante);
}
