package com.cromon.dao.impl;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cromon.dao.AuditDAO;
import com.cromon.entity.Audit;
import com.cromon.util.HibernateUtil;

public class AuditDAOImpl implements AuditDAO{

	@Override
	public void save(Audit auditoria) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(auditoria);
		t.commit();
		
	}

	@Override
	public List<Audit> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from Audit").list();
		t.commit();
		return lista;
	}

}
