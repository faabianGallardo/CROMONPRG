package com.cromon.dao.impl;



import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cromon.dao.UserDAO;
import com.cromon.entity.User;
import com.cromon.util.HibernateUtil;


public class UserDAOImpl implements UserDAO {

	public void save(User usuario) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(usuario);
		t.commit();
	}

	public User getUsuario(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (User) session.load(User.class, id);
	}

	public void update(User usuario) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(usuario);
		t.commit();
	}

	public void remove(User usuario) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(usuario);
		t.commit();
	}

	public List<User> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from User").list();
		t.commit();
		return lista;
	}
	
	

	@Override
	public User getUserEmail(String email) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("from User where emailAddress=:code");
		query.setParameter("code",email);
		List lista = query.list();
		
		if(lista.isEmpty())
		{
			return null;
		}
		else
		{
		return (User) lista.get(0);
	
		}
	}

}
