package com.cromon.dao;

import java.util.List;

import com.cromon.entity.New;


public interface NewDAO {
	
	public void save(New noticia);

	public New getNew(int id);

	public List<New> list();

	public void remove(New noticia);

	public void update(New noticia);

}
