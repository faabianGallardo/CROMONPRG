package com.cromon.dao;

import java.util.List;

import com.cromon.entity.Audit;

public interface AuditDAO {
	
	public void save(Audit auditoria);
	
	public List<Audit> list();

}
