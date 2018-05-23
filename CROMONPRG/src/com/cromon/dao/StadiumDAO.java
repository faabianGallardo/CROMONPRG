package com.cromon.dao;

import java.util.List;

import com.cromon.entity.Stadium;


public interface StadiumDAO {
	
	public void save(Stadium estadio);

	public Stadium getStadium(int id);

	public List<Stadium> list();

	public void remove(Stadium estadio);

	public void update(Stadium estadio);

}
