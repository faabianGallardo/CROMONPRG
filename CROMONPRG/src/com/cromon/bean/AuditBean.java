package com.cromon.bean;


import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import com.cromon.dao.AuditDAO;
import com.cromon.dao.impl.AuditDAOImpl;
import com.cromon.entity.Audit;

@ManagedBean
@SessionScoped
public class AuditBean {

	private Audit auditoria;
	private DataModel listaAuditoria;

	public String adicionarAuditoria(Audit auditoria) {
		AuditDAO dao = new AuditDAOImpl();
		dao.save(auditoria);
		return "inicioAdmin";
	}

	public DataModel getListarAuditoria() {
		List<Audit> lista = new AuditDAOImpl().list();
		listaAuditoria = new ListDataModel(lista);
		return listaAuditoria;
	}

}