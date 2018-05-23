package com.cromon.dao;


import java.util.List;

import com.cromon.entity.User;

public interface UserDAO {

	public void save(User usuario);

	public User getUsuario(int id);

	public List<User> list();

	public void remove(User usuario);

	public void update(User usuario);
	
	public User getUserEmail(String email);
}
