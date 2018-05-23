package com.cromon.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import com.cromon.entity.Audit;
import com.cromon.entity.User;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Export 
{
	public static void GuardarUserPDF(List lista, String table)
	{
		Document documento = new Document();
		try 
		{
			FileOutputStream ficheroPdf = new FileOutputStream("C:/tomcat/usuarios.pdf");
			PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);
			
			documento.open();
			
			Image foto = Image.getInstance("C:/cromon.png");
			foto.scaleToFit(100, 100);
			foto.setAlignment(Chunk.ALIGN_MIDDLE);
			documento.add(foto);
		
			Date date = new Date();
			documento.add(new Paragraph("Informe de la tabla " + table +" al día de " + date.toString()));
			
			documento.add(new Paragraph(" "));
			
			PdfPTable tabla = new PdfPTable(9);
			
			tabla.addCell("id");
			tabla.addCell("Usuario");
			tabla.addCell("Nombre");
			tabla.addCell("Email");
			tabla.addCell("Telefono");
			tabla.addCell("Ultimo cambio");
			tabla.addCell("estado");
			tabla.addCell("Tipo de usuario");
			tabla.addCell("IP usuario");
			
			
			for (int i = 0; i < lista.size(); i++)
			{
				User ac = (User) lista.get(i);
				tabla.addCell(""+ac.getId());
				tabla.addCell(""+ac.getUserName());
				tabla.addCell(""+ac.getFullName());
				tabla.addCell(""+ac.getEmailAddress());
				tabla.addCell(""+ac.getPhoneNumber());
				tabla.addCell(ac.getDateLastPassword().toString());
				tabla.addCell(""+ac.getActive());
				tabla.addCell(""+ac.getUserType());
				tabla.addCell(""+ac.getIpAddress());
			}
			
			documento.add(tabla);
			
			documento.close();
			
			File file = new File("C:/tomcat/usuarios.pdf");

		    FacesContext facesContext = FacesContext.getCurrentInstance();

		    HttpServletResponse response = 
		            (HttpServletResponse) facesContext.getExternalContext().getResponse();

		    response.reset();
		    response.setHeader("Content-Type", "application/octet-stream");
		    response.setHeader("Content-Disposition", "attachment;filename=usuarios.pdf");

		    OutputStream responseOutputStream = response.getOutputStream();

		    InputStream fileInputStream = new FileInputStream(file);

		    byte[] bytesBuffer = new byte[2048];
		    int bytesRead;
		    while ((bytesRead = fileInputStream.read(bytesBuffer)) > 0) 
		    {
		        responseOutputStream.write(bytesBuffer, 0, bytesRead);
		    }

		    responseOutputStream.flush();

		    fileInputStream.close();
		    responseOutputStream.close();

		    facesContext.responseComplete();
			
		} catch (FileNotFoundException e) {e.printStackTrace();} catch (Exception e) {e.printStackTrace();}

	}
	
	public static void GuardarAuditPDF(List lista, String table)
	{
		Document documento = new Document();
		try 
		{
			FileOutputStream ficheroPdf = new FileOutputStream("C:/tomcat/auditoria.pdf");
			PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);
			
			documento.open();
			
			Image foto = Image.getInstance("C:/cromon.png");
			foto.scaleToFit(100, 100);
			foto.setAlignment(Chunk.ALIGN_MIDDLE);
			documento.add(foto);
		
			Date date = new Date();
			documento.add(new Paragraph("Informe de la tabla " + table +" al día de " + date.toString()));
			
			documento.add(new Paragraph(" "));
			
			PdfPTable tabla = new PdfPTable(6);
			
			tabla.addCell("id");
			tabla.addCell("Usuario");
			tabla.addCell("Operacion");
			tabla.addCell("Tabla");
			tabla.addCell("Actor");
			tabla.addCell("Fecha");
			
			
			for (int i = 0; i < lista.size(); i++)
			{
				Audit ac = (Audit) lista.get(i);
				tabla.addCell(""+ac.getId());
				tabla.addCell(""+ac.getUserId());
				tabla.addCell(""+ac.getOperation());
				tabla.addCell(""+ac.getTableName());
				tabla.addCell(""+ac.getTableId());
				tabla.addCell(ac.getCreateDate().toString());
			}
			
			documento.add(tabla);
			
			documento.close();
			
			File file = new File("C:/tomcat/auditoria.pdf");

		    FacesContext facesContext = FacesContext.getCurrentInstance();

		    HttpServletResponse response = 
		            (HttpServletResponse) facesContext.getExternalContext().getResponse();

		    response.reset();
		    response.setHeader("Content-Type", "application/octet-stream");
		    response.setHeader("Content-Disposition", "attachment;filename=auditoria.pdf");

		    OutputStream responseOutputStream = response.getOutputStream();

		    InputStream fileInputStream = new FileInputStream(file);

		    byte[] bytesBuffer = new byte[2048];
		    int bytesRead;
		    while ((bytesRead = fileInputStream.read(bytesBuffer)) > 0) 
		    {
		        responseOutputStream.write(bytesBuffer, 0, bytesRead);
		    }

		    responseOutputStream.flush();

		    fileInputStream.close();
		    responseOutputStream.close();

		    facesContext.responseComplete();
			
		} catch (FileNotFoundException e) {e.printStackTrace();} catch (Exception e) {e.printStackTrace();}

	}

}
