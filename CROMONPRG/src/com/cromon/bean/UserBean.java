package com.cromon.bean;


import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.PrimeFaces;

import com.cromon.dao.AuditDAO;
import com.cromon.dao.MissingsheetDAO;
import com.cromon.dao.RepeatedsheetDAO;
import com.cromon.dao.UserDAO;
import com.cromon.dao.impl.AuditDAOImpl;
import com.cromon.dao.impl.MissingsheetDAOImpl;
import com.cromon.dao.impl.NewDAOImpl;
import com.cromon.dao.impl.RepeatedsheetDAOImpl;
import com.cromon.dao.impl.StadiumDAOImpl;
import com.cromon.dao.impl.UserDAOImpl;
import com.cromon.entity.Audit;
import com.cromon.entity.Missingsheet;
import com.cromon.entity.New;
import com.cromon.entity.Repeatedsheet;
import com.cromon.entity.Stadium;
import com.cromon.entity.User;
import com.cromon.util.Export;
import com.cromon.util.JavaEmail;
import com.cromon.util.RandomPassword;

@ManagedBean
@SessionScoped
public class UserBean {


	private User usuario;
	private DataModel listaUsuarios;
	private DataModel listaFaltantes;
	private DataModel listaRepetidas;
	private DataModel listaNoticias;
	private DataModel listaEstadios;
	private int intentos;
	
	private String contrasenaAnterior;
	private String contrasenaNueva;
	private String contrasenaNuevaRep;
	private Missingsheet faltante;
	private Repeatedsheet repetida;
	private Stadium estadio;


	@PostConstruct
	public void init()
	{
		usuario = new User();
		faltante = new Missingsheet();
		repetida = new Repeatedsheet();
		intentos = 1;
		contrasenaAnterior="";
		contrasenaNueva="";
		contrasenaNuevaRep="";
		estadio = new Stadium();
	}


	public String agregarFaltante() {
		return "/usuario/laminaFaltante";
	}

	public String agregarRepetida() {
		return "/usuario/laminaRepetida";
	}

	public String agregarLaminaFaltante() {
		MissingsheetDAO missingDao = new MissingsheetDAOImpl();
		UserDAO dao = new UserDAOImpl();
		User user = dao.getUserEmail(usuario.getEmailAddress());
		faltante.setUserId(user.getId());
		faltante.setCountSheets(1);
		missingDao.save(faltante);

		Audit audi = new Audit();
		audi.setUserId(user.getId());
		audi.setOperation("New Sheet");
		audi.setTableName("Missingsheets");
		audi.setTableId(0);
		audi.setCreateDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		AuditDAO daoAudi = new AuditDAOImpl();
		daoAudi.save(audi);

		return "/usuario/inicioUser";
	}

	public String agregarLaminaRepetida() {
		RepeatedsheetDAO repeatedDao = new RepeatedsheetDAOImpl();
		UserDAO dao = new UserDAOImpl();
		User user = dao.getUserEmail(usuario.getEmailAddress());

		repetida.setUserId(user.getId());
		repeatedDao.save(repetida);	

		Audit audi = new Audit();
		audi.setUserId(user.getId());
		audi.setOperation("New Sheet");
		audi.setTableName("Repeatedsheets");
		audi.setTableId(0);
		audi.setCreateDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		AuditDAO daoAudi = new AuditDAOImpl();
		daoAudi.save(audi);

		return "/usuario/inicioUser";
	}

	public String prepararAdicionarUsuario() {
		return "/login/registro";
	}

	public String prepararModificarUsuario() {
		usuario = (User)(listaUsuarios.getRowData());
		return "/login/registro";
	}

	public String eliminarUsuario() {
		User usuarioTemp = (User) (listaUsuarios.getRowData());
		UserDAO dao = new UserDAOImpl();
		User user = dao.getUserEmail(usuario.getEmailAddress());
		usuarioTemp.setActive("I");
		dao.update(usuarioTemp);

		Audit audi = new Audit();
		audi.setUserId(user.getId());
		audi.setOperation("Delete");
		audi.setTableName("User");
		audi.setTableId(usuarioTemp.getId());
		audi.setCreateDate(new Timestamp(Calendar.getInstance().getTime().getTime()));

		AuditDAO daoAudi = new AuditDAOImpl();
		daoAudi.save(audi);

		return "/admin/inicioAdmin";

	}

	public String habilitarUsuario() {

		User usuarioTemp = (User) (listaUsuarios.getRowData());
		UserDAO dao = new UserDAOImpl();
		User user = dao.getUserEmail(usuario.getEmailAddress());
		usuarioTemp.setActive("A");
		usuarioTemp.setContador(0);
		dao.update(usuarioTemp);

		Audit audi = new Audit();
		audi.setUserId(user.getId());
		audi.setOperation("Enable");
		audi.setTableName("User");
		audi.setTableId(usuarioTemp.getId());
		audi.setCreateDate(new Timestamp(Calendar.getInstance().getTime().getTime()));

		AuditDAO daoAudi = new AuditDAOImpl();
		daoAudi.save(audi);

		return "/admin/inicioAdmin";

	}

	public String adicionarUsuario() {
		String pagina = "";
		UserDAO dao = new UserDAOImpl();
		String correo =usuario.getEmailAddress();
		User user = dao.getUserEmail(usuario.getEmailAddress());
		String remoteAddr = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();

		if(camposVacios() == false)
		{
			if(user == null) {

				RandomPassword r = new RandomPassword();
				Integer a = new Integer((int) ((Math.random()*10000)+1));
				String s = r.getStringMessageDigest(a.toString(), r.MD5);
				JavaEmail pass = new JavaEmail();
				pass.enviarPass(usuario.getEmailAddress(),usuario.getUserName(), a.toString());
				usuario.setPassword(s);
				usuario.setActive("A");
				usuario.setDateLastPassword(new Timestamp(Calendar.getInstance().getTime().getTime()));
				usuario.setUserType("Usuario");
				usuario.setIpAddress(remoteAddr);
				dao.save(usuario);

				Audit audi = new Audit();
				audi.setUserId(usuario.getId());
				audi.setOperation("New User");
				audi.setTableName("User");
				audi.setTableId(0);
				audi.setCreateDate(new Timestamp(Calendar.getInstance().getTime().getTime()));



				AuditDAO daoAudi = new AuditDAOImpl();
				daoAudi.save(audi);
				pagina = "/login/login";
			}
			else if(correo.equals(user.getEmailAddress())) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Correo existente","Lo sentimos pero esta dirección de correo ya se encuentra registrada.");
				PrimeFaces.current().dialog().showMessageDynamic(message);
				pagina = "/login/registro";
			} 

		}else
		{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Campos requeridos","Llene todos los campos por favor.");
			PrimeFaces.current().dialog().showMessageDynamic(message);
		}



		return pagina;
	}

	public boolean camposVacios() {
		boolean vacio = false;

		if(usuario.getEmailAddress().equals("") || usuario.getFullName().equals("") || usuario.getUserName().equals(""))
		{
			vacio = true; 
		}

		return vacio;
	}

	public String modificarUsuario() {

		UserDAO dao = new UserDAOImpl();
		dao.update(usuario);
		User user = dao.getUsuario(usuario.getId());

		Audit audi = new Audit();
		audi.setUserId(user.getId());
		audi.setOperation("Update");
		audi.setTableName("User");
		audi.setTableId(1);
		audi.setCreateDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		AuditDAO daoAudi = new AuditDAOImpl();
		daoAudi.save(audi);
		return "/admin/inicioAdmin";
	}

	public String actualizar()
	{
		String pagina = "";

		UserDAO dao = new UserDAOImpl();
		User user = dao.getUserEmail(usuario.getEmailAddress());

		if(user.getUserType().equals("Usuario")) {
			if(usuario.getUserName().equals("") || usuario.getFullName().equals("") || usuario.getPhoneNumber().equals("") || usuario.getEmailAddress().equals("") 
					|| contrasenaAnterior.equals("") || contrasenaNueva.equals("") || contrasenaNuevaRep.equals(""))
			{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Campos requeridos","Llene todos los campos por favor.");
				PrimeFaces.current().dialog().showMessageDynamic(message);
				pagina = "/usuario/inicioUser";
			}
			else
			{
				user.setUserName(usuario.getUserName());
				user.setFullName(usuario.getFullName());
				user.setPhoneNumber(usuario.getPhoneNumber());
				user.setEmailAddress(usuario.getEmailAddress());
				dao.update(user);


				Audit audi = new Audit();
				audi.setUserId(user.getId());
				audi.setOperation("UpdateData");
				audi.setTableName("User");
				audi.setTableId(user.getId());
				audi.setCreateDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
				AuditDAO daoAudi = new AuditDAOImpl();
				daoAudi.save(audi);

				String pass=RandomPassword.getStringMessageDigest(contrasenaAnterior, RandomPassword.MD5);
				RandomPassword r = new RandomPassword();

				if(user.getPassword().equals(pass))
				{
					if(contrasenaNueva.equals(contrasenaNuevaRep))
					{
						String s = r.getStringMessageDigest(contrasenaNueva, r.MD5);
						user.setPassword(s);
						user.setDateLastPassword(new Timestamp(Calendar.getInstance().getTime().getTime()));
						dao.update(user);

						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "","Actualización exitosa.");
						PrimeFaces.current().dialog().showMessageDynamic(message);
					}
					else
					{
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error","Las contraseñas no coinciden.");
						PrimeFaces.current().dialog().showMessageDynamic(message);
					}
				}
				else
				{
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Contraseña incorrecta","La contraseña anterior no coincide con la registrada.");
					PrimeFaces.current().dialog().showMessageDynamic(message);
				}
			}

			pagina = "/usuario/inicioUser";
		}
		else
		{
			if(usuario.getUserName().equals("") || usuario.getFullName().equals("") || usuario.getPhoneNumber().equals("") || usuario.getEmailAddress().equals("") 
					|| contrasenaAnterior.equals("") || contrasenaNueva.equals("") || contrasenaNuevaRep.equals(""))
			{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Campos requeridos","Llene todos los campos por favor.");
				PrimeFaces.current().dialog().showMessageDynamic(message);
				pagina = "/usuario/inicioUser";
			}
			else
			{
				user.setUserName(usuario.getUserName());
				user.setFullName(usuario.getFullName());
				user.setPhoneNumber(usuario.getPhoneNumber());
				user.setEmailAddress(usuario.getEmailAddress());
				dao.update(user);


				Audit audi = new Audit();
				audi.setUserId(user.getId());
				audi.setOperation("UpdateData");
				audi.setTableName("User");
				audi.setTableId(user.getId());
				audi.setCreateDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
				AuditDAO daoAudi = new AuditDAOImpl();
				daoAudi.save(audi);

				String pass=RandomPassword.getStringMessageDigest(contrasenaAnterior, RandomPassword.MD5);
				RandomPassword r = new RandomPassword();

				if(user.getPassword().equals(pass))
				{
					if(contrasenaNueva.equals(contrasenaNuevaRep))
					{
						String s = r.getStringMessageDigest(contrasenaNueva, r.MD5);
						user.setPassword(s);
						user.setDateLastPassword(new Timestamp(Calendar.getInstance().getTime().getTime()));
						dao.update(user);

						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "","Actualización exitosa.");
						PrimeFaces.current().dialog().showMessageDynamic(message);
					}
					else
					{
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error","Las contraseñas no coinciden.");
						PrimeFaces.current().dialog().showMessageDynamic(message);
					}
				}
				else
				{
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Contraseña incorrecta","La contraseña anterior no coincide con la registrada.");
					PrimeFaces.current().dialog().showMessageDynamic(message);
				}
			}

			pagina = "/admin/inicioAdmin";
		}
		return pagina;
	}


	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public DataModel getListarUsuarios() {
		List<User> lista = new UserDAOImpl().list();
		listaUsuarios = new ListDataModel(lista);
		return listaUsuarios;
	}

	public DataModel getListarFaltantes() {
		List<Missingsheet> lista = new MissingsheetDAOImpl().list();
		listaFaltantes = new ListDataModel(lista);
		return listaFaltantes;
	}

	public DataModel getListarRepetidas() {
		List<Repeatedsheet> lista = new RepeatedsheetDAOImpl().list();
		listaRepetidas = new ListDataModel(lista);
		return listaRepetidas;
	}
	
	public DataModel getListarNoticias() {
		List<New> lista = new NewDAOImpl().list();
		listaNoticias = new ListDataModel(lista);
		return listaNoticias;
	}
	
	public DataModel getListarEstadios() {
		List<Stadium> lista = new StadiumDAOImpl().list();
		listaEstadios = new ListDataModel(lista);
		return listaEstadios;
		
	}


	public String ingresar() {
		String pagina="";
		UserDAO dao = new UserDAOImpl();
		User user = dao.getUserEmail(usuario.getEmailAddress());

		if(user==null)
		{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario no existente.","Favor registrarse.");
			PrimeFaces.current().dialog().showMessageDynamic(message);
		}
		else
		{
			String pass=RandomPassword.getStringMessageDigest(usuario.getPassword(), RandomPassword.MD5);
			if(user.getActive().equals("A")) {

				if(user.getUserType().equals("ADMIN"))
				{
					if(pass.equals(user.getPassword())) {
						Audit audi = new Audit();
						audi.setUserId(user.getId());
						audi.setOperation("loginADMIN");
						audi.setTableName("User");
						audi.setTableId(0);
						audi.setCreateDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
						AuditDAO daoAudi = new AuditDAOImpl();
						String remoteAddr = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
						user.setIpAddress(remoteAddr);
						dao.update(user);
						daoAudi.save(audi);
						pagina = "/admin/inicioAdmin";
					}
					else
					{
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Contraseña incorrecta.","Favor verificar su contraseña.");
						PrimeFaces.current().dialog().showMessageDynamic(message);
					}
				}
				else 
				{
					if(user.getContador() != 3)
					{
						if(pass.equals(user.getPassword())) {
							Audit audi = new Audit();
							audi.setUserId(user.getId());
							audi.setOperation("loginUSER");
							audi.setTableName("User");
							audi.setTableId(0);
							audi.setCreateDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
							AuditDAO daoAudi = new AuditDAOImpl();
							String remoteAddr = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
							user.setIpAddress(remoteAddr);
							user.setContador(0);
							dao.update(user);
							daoAudi.save(audi);
							pagina = "/usuario/inicioUser"; 
						}
						else
						{
							int p = intentos++;
							FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Contraseña incorrecta.","Favor verificar su contraseña.");
							PrimeFaces.current().dialog().showMessageDynamic(message);
							user.setContador(p);
							dao.update(user);
						}
					}
					if(user.getContador()>=3)
					{
						user.setActive("I");
						dao.update(user);
						AuditDAO daoAudi = new AuditDAOImpl();
						Audit audi = new Audit();
						audi.setUserId(user.getId());
						audi.setOperation("Blocked");
						audi.setTableName("User");
						audi.setTableId(0);
						audi.setCreateDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
						daoAudi.save(audi);

					}
				}
			}
			else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Usuario inactivo.","Favor contactar con el administrador.\n\t\r"
						+ "cromonprg@gmail.com");
				PrimeFaces.current().dialog().showMessageDynamic(message);
			}

		}


		return pagina;
	}	

	public String regresar() {
		return "/login/login";
	}

	public String cerrarSesion() {

		UserDAO dao = new UserDAOImpl();
		User user = dao.getUserEmail(usuario.getEmailAddress());
		Audit audi = new Audit();
		audi.setUserId(user.getId());
		audi.setOperation("Logout");
		audi.setTableName("User");
		audi.setTableId(0);
		audi.setCreateDate(new Timestamp(Calendar.getInstance().getTime().getTime()));

		AuditDAO daoAudi = new AuditDAOImpl();
		daoAudi.save(audi);
		return "/login/login"; 
	}

	public String recuperarContrasena()
	{

		String pagina="";
		UserDAO dao = new UserDAOImpl();
		User user = dao.getUserEmail(usuario.getEmailAddress());
		RandomPassword r = new RandomPassword();
		JavaEmail email = new JavaEmail();
		if(user!=null) {
			Integer a = new Integer((int) ((Math.random()*10000)+1));
			String s = r.getStringMessageDigest(a.toString(), r.MD5);
			user.setPassword(s);
			email.enviarPass(usuario.getEmailAddress(),user.getUserName(), a.toString());
			dao.update(user);
			Audit audi = new Audit();
			audi.setUserId(user.getId());
			audi.setOperation("ForgetPass");
			audi.setTableName("User");
			audi.setTableId(0);
			audi.setCreateDate(new Timestamp(Calendar.getInstance().getTime().getTime()));

			AuditDAO daoAudi = new AuditDAOImpl();
			daoAudi.save(audi);
			pagina = "/login/login";
		}else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Correo inexistente","Favor primero registrate.");
			PrimeFaces.current().dialog().showMessageDynamic(message);
			pagina = "/login/recuperarContrasena";
		}
		return pagina; 

	}

	public String getContrasenaAnterior() {
		return contrasenaAnterior;
	}

	public void setContrasenaAnterior(String contrasenaAnterior) {
		this.contrasenaAnterior = contrasenaAnterior;
	}

	public String getcontrasenaNueva() {
		return contrasenaNueva;
	}

	public void setcontrasenaNueva(String contrasenaNueva) {
		this.contrasenaNueva = contrasenaNueva;
	}

	public String getContrasenaNuevaRep() {
		return contrasenaNuevaRep;
	}

	public void setContrasenaNuevaRep(String contrasenaNuevaRep) {
		this.contrasenaNuevaRep = contrasenaNuevaRep;
	}

	public String exportarUserPDF() 
	{
		List<User> lista = new UserDAOImpl().list();
		Export.GuardarUserPDF(lista, "User");
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exportado","La lista de usuarios ya se encuentra descargada.");
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return "entro";
	}

	public String exportarAuditPDF() 
	{
		List<Audit> lista = new AuditDAOImpl().list();
		Export.GuardarAuditPDF(lista, "Audit");
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Exportado","Su auditoría ya se encuentra descargada.");
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return "entro";
	}



	public Missingsheet getFaltante() {
		return faltante;
	}



	public void setFaltante(Missingsheet faltante) {
		this.faltante = faltante;
	}



	public Repeatedsheet getRepetida() {
		return repetida;
	}



	public void setRepetida(Repeatedsheet repetida) {
		this.repetida = repetida;
	}


	public Stadium getEstadio() {
		return estadio;
	}


	public void setEstadio(Stadium estadio) {
		this.estadio = estadio;
	}



}
