package com.cromon.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cromon.dao.StadiumDAO;
import com.cromon.entity.Stadium;
import com.cromon.util.HibernateUtil;

public class StadiumDAOImpl implements StadiumDAO{

	@Override
	public void save(Stadium estadio) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(estadio);
		t.commit();
	}

	@Override
	public Stadium getStadium(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (Stadium) session.load(Stadium.class, id);
	}

	@Override
	public List<Stadium> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from Stadium").list();
		t.commit();
		return lista;
	}

	@Override
	public void remove(Stadium estadio) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(estadio);
		t.commit();
	}

	@Override
	public void update(Stadium estadio) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(estadio);
		t.commit();
	}


}
