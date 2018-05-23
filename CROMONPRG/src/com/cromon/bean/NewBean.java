package com.cromon.bean;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import com.cromon.dao.AuditDAO;
import com.cromon.dao.NewDAO;
import com.cromon.dao.UserDAO;
import com.cromon.dao.impl.AuditDAOImpl;
import com.cromon.dao.impl.NewDAOImpl;
import com.cromon.dao.impl.UserDAOImpl;
import com.cromon.entity.Audit;
import com.cromon.entity.New;
import com.cromon.entity.User;


@ManagedBean
@SessionScoped
public class NewBean {
	
	private New noticia;
	private User usuario;
	private DataModel listaNoticias;
	
	@PostConstruct
	public void init() {
		
		noticia = new New();
		usuario = new User();
	}
	
	public String nuevaNoticia() {
		UserDAO daoUser = new UserDAOImpl();
		User user = daoUser.getUsuario(usuario.getId());
		NewDAO dao = new NewDAOImpl();
		noticia.setDateNews(new Timestamp(Calendar.getInstance().getTime().getTime()));
		noticia.setIdUser(user.getId());
		noticia.setLargeDescription(noticia.getLargeDescription());
		noticia.setShortDescription(noticia.getShortDescription());
		noticia.setState("A");
		dao.save(noticia);
		
		Audit audi = new Audit();
		audi.setUserId(user.getId());
		audi.setOperation("AddNew");
		audi.setTableName("News");
		audi.setTableId(0);
		audi.setCreateDate(new Timestamp(Calendar.getInstance().getTime().getTime()));

		AuditDAO daoAudi = new AuditDAOImpl();
		daoAudi.save(audi);
		
		return "inicioAdmin";
	}
	
	public String regresar() {
		return "inicioAdmin";
	}
	
	public DataModel getListarNoticias() {
		List<New> lista = new NewDAOImpl().list();
		listaNoticias = new ListDataModel(lista);
		return listaNoticias;
	}

	public New getNoticia() {
		return noticia;
	}

	public void setNoticia(New noticia) {
		this.noticia = noticia;
	}

	
}
