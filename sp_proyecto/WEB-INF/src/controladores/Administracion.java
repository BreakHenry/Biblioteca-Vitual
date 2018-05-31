package controladores;

import java.util.Date;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





import javax.servlet.http.HttpSession;

import model.manager.PersonasManager;
import model.manager.MenuManager;
import model.manager.AreasManager;
import model.manager.EditorialesManager;
import model.manager.RolesManager;
import model.manager.TiposManager;
import model.manager.AutoresManager;
import model.manager.TextosManager;
import model.domain.personas;

import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.apache.velocity.servlet.VelocityServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import utils.ClaseDESBase64;

	@Controller
	public class Administracion {
		
		@Autowired
		PersonasManager personasManager;
		
		@Autowired
		AreasManager areasManager;
		
		@Autowired
		MenuManager menuManager;
		
		@Autowired
		EditorialesManager editorialesManager;
		
		@Autowired
		TiposManager tiposManager;
		
		@Autowired
		AutoresManager autoresManager;
		
		@Autowired
		TextosManager textosManager;
		
		@Autowired
		RolesManager rolesManager;
		
		
		// AQUI SE CREAN LOS SERVLETS, DONDE SE PASAN AL PRINCIPIO EL NOMBRE DEL MAPEO...
		
		
		@RequestMapping({"index.html"})
		public String vista1(HttpServletRequest request, Model model)  throws IOException  {
			model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
			
			return "inicio";
		}
		
		@RequestMapping({"index2.html"})
		public String vista2(HttpServletRequest request, Model model)  throws IOException  {
			model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
			
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			//System.out.println("Llega login "+UserConectado);
			Map xuser=(Map) sesion.getAttribute("xusuario");
			//System.out.println(xuser.get("xfoto"));
			//System.out.println("Llega usuario "+xuser);
				if (xuser==null){
					model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
					model.addAttribute("xanimacion", "autentificarse");
					model.addAttribute("xenlace", "index.html");
					return "mensajesAutentificacion";
				}else{
					List<?> xmenus = this.menuManager.xmenus();
					List<?> xopciones = this.menuManager.xopciones();
					
					List<?> xroles = this.menuManager.listarRolusu(UserConectado);
					
					model.addAttribute("xmenus", xmenus );	
					model.addAttribute("xopciones", xopciones );
					model.addAttribute("xroles", xroles );
					model.addAttribute("usuario",xuser);
					return "inicio2";
				}
		}
		
		// INICIO USUARIOS 
		
		@RequestMapping({"usuarios.html"})
		public String vista1(Model model,HttpServletRequest request)  throws IOException  {
			model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
			
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
				if (xuser==null){
					model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
					model.addAttribute("xanimacion", "autentificarse");
					model.addAttribute("xenlace", "index.html");
					return "mensajesAutentificacion";
				}else{
					List<?> xmenus = this.menuManager.xmenus();
					List<?> xopciones = this.menuManager.xopciones();
					model.addAttribute("xmenus", xmenus );	
					model.addAttribute("xopciones", xopciones );
					model.addAttribute("usuario",xuser);
					
					String xper=request.getParameter("xpersona");
					String xper2;
					
					String xpersonal=request.getParameter("xtipo");
					//System.out.println("valor del personal "+xpersonal);
					String xpersonal2="2";
					
					if (xpersonal==null) {
						xpersonal="A";
						xpersonal2="P";
					} else {
						if (xpersonal.equals("A")) {
							xpersonal="A";
							xpersonal2="A";
						}
						if (xpersonal.equals("P")) {
							xpersonal="P";
							xpersonal2="P";
						}
						if (xpersonal.equals("T")) {
							xpersonal="A";
							xpersonal2="P";
						}
					}
					
					if (xper==null) {  
						xper="";  
					}
					
					Map<String, Object> Data = new HashMap();

					List<?> lista = this.personasManager.listar(1,"%"+xper.toUpperCase()+"%",xpersonal,xpersonal2);
					//Data.put("data", lista);
					
					model.addAttribute("xlista", lista);
					model.addAttribute("xper2", xper);
					
					return "GestionUsuarios";
				}
			
			
			

		}
		
		@RequestMapping({"usuariosA.html"})
		public String vistaA(Model model,HttpServletRequest request)  throws IOException  {
			model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
			
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
			
			String xper=request.getParameter("xpersona");
			String xper2;
			String xpersonal=request.getParameter("xtipo");
			//System.out.println("valor del personal "+xpersonal);
			String xpersonal2="2";
			
			if (xpersonal==null) {
				xpersonal="A";
				xpersonal2="P";
			} else {
				if (xpersonal.equals("A")) {
					xpersonal="A";
					xpersonal2="A";
				}
				if (xpersonal.equals("P")) {
					xpersonal="P";
					xpersonal2="P";
				}
				if (xpersonal.equals("T")) {
					xpersonal="A";
					xpersonal2="P";
				}
			}
			
			if (xper==null) {  
				xper="";  
			}
			
			Map<String, Object> Data = new HashMap();

			List<?> lista = this.personasManager.listarA(1,"%"+xper.toUpperCase()+"%",xpersonal,xpersonal2);
			//Data.put("data", lista);
			
			model.addAttribute("xlista", lista);
			model.addAttribute("xper2", xper);
			return "GestionUsuariosA";
		}
			}
		
		@RequestMapping({"usuariosB.html"})
		public String vistaB(Model model,HttpServletRequest request)  throws IOException  {
			model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
			
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
			
			String xper=request.getParameter("xpersona");
			String xper2;
			
			String xpersonal=request.getParameter("xtipo");
			//System.out.println("valor del personal "+xpersonal);
			String xpersonal2="2";
			
			if (xpersonal==null) {
				xpersonal="A";
				xpersonal2="P";
			} else {
				if (xpersonal.equals("A")) {
					xpersonal="A";
					xpersonal2="A";
				}
				if (xpersonal.equals("P")) {
					xpersonal="P";
					xpersonal2="P";
				}
				if (xpersonal.equals("T")) {
					xpersonal="A";
					xpersonal2="P";
				}
			}
			
			if (xper==null) {  
				xper="";  
			}
			
			Map<String, Object> Data = new HashMap();

			List<?> lista = this.personasManager.listarB(1,"%"+xper.toUpperCase()+"%",xpersonal,xpersonal2);
			//Data.put("data", lista);
			
			model.addAttribute("xlista", lista);
			model.addAttribute("xper2", xper);
			
			return "GestionUsuariosB";
		}
		
		}
	
		@RequestMapping({"addPer_1.html"})
		public String addPer_1(Model model)  throws IOException  {
			return "addPer";
		}
		
		@RequestMapping({"addPer_2.html"})
		public String addPer_2(Model model, HttpServletRequest request,@RequestParam("file") MultipartFile file,@RequestParam String xnombre,String xap, String xam,String xgenero,String xtipoper,String xci,String xfilas)  throws IOException  {
			System.out.println(" "+xnombre+" "+xgenero+" "+xtipoper+" "+xci+" "+xfilas);
			 
			ServletContext ctx = request.getSession().getServletContext(); 
	        //System.out.println(ctx.getRealPath("/")); 
	    	String fileName = null;
	    	
	    	for(int i=1; i<= Integer.parseInt(xfilas) ; i++){
				System.out.println(request.getParameter("xcant_"+i));
			}
	    	
	
		if(!xci.equals("")){
	    	if (!file.isEmpty()) {
	            try {
	                fileName = file.getOriginalFilename();
	                byte[] bytes = file.getBytes();
	                BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(new File(ctx.getRealPath("/")+"uploads/" + fileName)));
//	                File file = new File(ctx.getRealPath("/")+"uploads/" + item.getName());
	                buffStream.write(bytes);
	                buffStream.close();
	                // VALIDAR LA FOTO (.png .jpj)
	                
	                Map<String,Object> sw=this.personasManager.adicionarPer(fileName, xnombre, xap, xam, xgenero, xtipoper);
	                int codper=(Integer) sw.get("codper");
	                int sw2=this.personasManager.adicionarCi(xci,codper);
					System.out.println(sw2);
					System.out.println(codper);
					for(int i=1; i<= Integer.parseInt(xfilas) ; i++){
						String xtel=request.getParameter("xcant_"+i);
						if(!xtel.equals("0")&&!xtel.equals("")&&xtel!=null){
							 sw2=personasManager.adicionarTelef(codper, xtel);//INSERT DATOS DE TELEFONOS
						}else{
						//	sw=alumnosManager.adicionarTelef(zci,xtel);//INSERT DATOS DE TELEFONOS
						}
						
						}
//	                return "CORRECTO, EL ARCHIVO    "+ fileName + "   SE HA ENVIADO CON EXITO..";
	            } catch (Exception e) {
	                return "ERROR AL ENVIAR " + fileName + ": " + e.getMessage();
	            }
	        } else {
	        	Map<String,Object> sw2 = personasManager.adicionarPer2( xnombre, xap, xam, xgenero, xtipoper);
	        	int codper=(Integer) sw2.get("codper");
	        	int sw3=this.personasManager.adicionarCi(xci,codper);
	        	for(int i=1; i<= Integer.parseInt(xfilas) ; i++){
					String xtel=request.getParameter("xcant_"+i);
					if(!xtel.equals("0")&&!xtel.equals("")&&xtel!=null){
						 sw3=personasManager.adicionarTelef(codper, xtel);//INSERT DATOS DE TELEFONOS
					}else{
					//	sw=alumnosManager.adicionarTelef(zci,xtel);//INSERT DATOS DE TELEFONOS
					}
					
					}
	        	model.addAttribute("xtexto", "SE ADICIONO SATISFACTORIAMENTE");	
	        }
	    	//
		}else{
			
			if (!file.isEmpty()) {
	            try {
	                fileName = file.getOriginalFilename();
	                byte[] bytes = file.getBytes();
	                BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(new File(ctx.getRealPath("/")+"uploads/" + fileName)));
//	                File file = new File(ctx.getRealPath("/")+"uploads/" + item.getName());
	                buffStream.write(bytes);
	                buffStream.close();
	                // VALIDAR LA FOTO (.png .jpj)
	                
	                Map<String,Object> sw=this.personasManager.adicionarPer(fileName, xnombre, xap, xam, xgenero, xtipoper);
	                int codper=(Integer) sw.get("codper");
		        	//insertar telefonos  -- Realiza N INSERT en la tabla telefonos
					for(int i=1; i<= Integer.parseInt(xfilas) ; i++){
						String xtel=request.getParameter("xcant_"+i);
						if(!xtel.equals("0")&&!xtel.equals("")&&xtel!=null){
							int sw3=personasManager.adicionarTelef(codper, xtel);//INSERT DATOS DE TELEFONOS
						}else{
						//	sw=alumnosManager.adicionarTelef(zci,xtel);//INSERT DATOS DE TELEFONOS
						}
						
						}
	//                return "CORRECTO, EL ARCHIVO    "+ fileName + "   SE HA ENVIADO CON EXITO..";
	            } catch (Exception e) {
	                return "ERROR AL ENVIAR " + fileName + ": " + e.getMessage();
	            }
	        } else {
	        	Map<String,Object> sw2 = personasManager.adicionarPer2( xnombre, xap, xam, xgenero, xtipoper);
	        	int codper=(Integer) sw2.get("codper");
	        	//insertar telefonos  -- Realiza N INSERT en la tabla telefonos
				for(int i=1; i<= Integer.parseInt(xfilas) ; i++){
					String xtel=request.getParameter("xcant_"+i);
					if(!xtel.equals("0")&&!xtel.equals("")&&xtel!=null){
						int sw3=personasManager.adicionarTelef(codper, xtel);//INSERT DATOS DE TELEFONOS
					}else{
					//	sw=alumnosManager.adicionarTelef(zci,xtel);//INSERT DATOS DE TELEFONOS
					}
					
					}		
	        	model.addAttribute("xtexto", "SE ADICIONO SATISFACTORIAMENTE");	
	        }
	    	//
			
		}
	    
		model.addAttribute("xtexto", "SE ADICIONO SATISFACTORIAMENTE");
		return "mensajes";
		}
		
		
		@RequestMapping({"delPer.html"})
		public String delPer(Model model,@RequestParam String xcodper)  throws IOException  {
			
			int sw=this.personasManager.eliminarPer(xcodper);
			
			model.addAttribute("xtexto", "SE ELIMINO SATISFACTORIAMENTE");
			return "mensajes";
		}
		
		@RequestMapping({"habPer.html"})
		public String habPer(Model model,@RequestParam String xcodper)  throws IOException  {
			
			int sw=this.personasManager.habilitarPer(xcodper);
			
			model.addAttribute("xtexto", "SE HABILITO SATISFACTORIAMENTE");
			return "mensajes";
		}
		
		@RequestMapping({"modPer.html"})
		public String modPer(HttpServletRequest request, Model model,@RequestParam String xcodper)  throws IOException  {
			
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
			
			//System.out.println(xcodper);
			
			Map<String,Object> clave=this.personasManager.buscarModPer(xcodper);
			
			
			model.addAttribute("datos",clave);
			return "modPer";
		}
			}
		
		@RequestMapping({"modPer_2.html"})
		public String modPer_2(Model model,@RequestParam String xcodper,String xnombre,String xap, String xam, String xgenero, String xtipoper, String xci)  throws IOException  {
		System.out.println("xru="+xcodper+" xnombre="+xnombre+" xap="+xap+" xam="+xam+" xci="+xci+" tipoper="+xtipoper+" genero="+xgenero);
			
			
			if(!xci.equals("")){			
				try{
					int sw=this.personasManager.modificarPer(xcodper, xnombre, xap, xam, xgenero, xtipoper);
					int sw2=this.personasManager.modificarCi(xci,Integer.parseInt(xcodper));
				}catch (Exception e){
					model.addAttribute("xtexto", " ERROR AL MODIFICAR DATOS ..!");
					return "mensajes";
				}
				}else{			
					try{
						int sw=this.personasManager.modificarPer(xcodper, xnombre, xap, xam, xgenero, xtipoper);
					}catch (Exception e){
						model.addAttribute("xtexto", " ERROR AL MODIFICAR DATOS ..!");
						return "mensajes";
					}
					}
				model.addAttribute("xtexto", "SE MODIFICO SATISFACTORIAMENTE");
				return "mensajes";

		}
		
		@RequestMapping({"crearDatosAcceso.html"})
		public String crearDatos(HttpServletRequest request,Model model,@RequestParam String xcodper)  throws IOException  {
			
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
			
			Map<String,Object> clave=this.personasManager.buscarPer(xcodper);
			
			model.addAttribute("datos",clave);
			return "crearDatosAcceso";
		}
		}
		
		@RequestMapping({"crearDatosAcceso_2.html"})
		public String crearDatos_2(Model model,@RequestParam String xcodper,String xlogin,String xpasswd,String xpasswd2)  throws IOException  {
			System.out.println(xcodper+" "+xlogin+" "+xpasswd+" "+xpasswd2);
			try{
				if(xpasswd.equals(xpasswd2)){
					int sw=this.personasManager.crearDatosAcceso(xcodper,xlogin,xpasswd);
				}else{
					model.addAttribute("xtexto", " ERROR LAS CLAVES DEBEN SER LAS IGUALES ..!");
					return "mensajes";
				}
			}catch (Exception e){
				model.addAttribute("xtexto", " ERROR AL GUADAR DATOS ..!");
				return "mensajes";
			}
			model.addAttribute("xtexto", "SE ADICIONARON DATOS DE ACCESO SATISACTORIAMENTE");
			return "mensajes";
		}
		
		@RequestMapping({"modDatosAcceso.html"})
		public String modDatos(HttpServletRequest request,Model model,@RequestParam String xcodper)  throws IOException  {
			
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
			
			Map<String,Object> clave=this.personasManager.buscarPer(xcodper);
			
			model.addAttribute("datos",clave);
			return "modDatosAcceso";
		}
		}
		
		@RequestMapping({"modDatosAcceso_2.html"})
		public String modDatos_2(Model model,@RequestParam String xcodper,String xpasswd,String xpasswd2)  throws IOException  {
//			System.out.println("xru="+xru+" xnombre="+xnombre+" xap="+xap+" xam="+xam);

			try{
				if(xpasswd.equals(xpasswd2)){
					int sw=this.personasManager.modDatosAcceso(xcodper, xpasswd);
				}else{
					model.addAttribute("xtexto", " ERROR LAS CLAVES DEBEN SER IGUALES ..!");
					return "mensajes";
				}
			}catch (Exception e){
				model.addAttribute("xtexto", " ERROR AL MODIFICAR DATOS ..!");
				return "mensajes";
			}
			model.addAttribute("xtexto", "SE MODIFICO LA CLAVE SATISFACTORIAMENTE");

			return "mensajes";
		}
		
		@RequestMapping({"verPer.html"})
		public String verPer(HttpServletRequest request,Model model,@RequestParam String xcodper)  throws IOException  {
			
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
			
			Map<String,Object> per=this.personasManager.buscarPer(xcodper);
			
			model.addAttribute("persona",per);
			return "verPer";
		}
		}
		
		// FIN USUARIOS
		// INICIO AREAS
		
		@RequestMapping({"areas.html"})
		public String vista2(Model model,HttpServletRequest request)  throws IOException  {
			model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
			
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
			
			String xarea=request.getParameter("xarea");
			String xarea2;
			
			
			if (xarea==null) {  
				xarea="";  
			}
			
			Map<String, Object> Data = new HashMap();

			List<?> lista = this.areasManager.listar(1,"%"+xarea.toUpperCase()+"%");
			//Data.put("data", lista);
			
			model.addAttribute("xlista", lista);
			model.addAttribute("xarea2", xarea);
			return "GestionAreas";
		}
		}
		
		@RequestMapping({"areasA.html"})
		public String areasA(Model model,HttpServletRequest request)  throws IOException  {
			model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
			
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
			
			String xarea=request.getParameter("xarea");
			String xarea2;
			
			
			if (xarea==null) {  
				xarea="";  
			}
			
			Map<String, Object> Data = new HashMap();

			List<?> lista = this.areasManager.listarA(1,"%"+xarea.toUpperCase()+"%");
			//Data.put("data", lista);
			
			model.addAttribute("xlista", lista);
			model.addAttribute("xarea2", xarea);
			return "GestionAreasA";
		}
		}
		
		@RequestMapping({"areasB.html"})
		public String areasB(Model model,HttpServletRequest request)  throws IOException  {
			model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
			
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
			
			String xarea=request.getParameter("xarea");
			String xarea2;
			
			
			if (xarea==null) {  
				xarea="";  
			}
			
			Map<String, Object> Data = new HashMap();

			List<?> lista = this.areasManager.listarB(1,"%"+xarea.toUpperCase()+"%");
			//Data.put("data", lista);
			
			model.addAttribute("xlista", lista);
			model.addAttribute("xarea2", xarea);
			return "GestionAreasB";
		}
		}
		
		@RequestMapping({"addArea_1.html"})
		public String addArea_1(Model model)  throws IOException  {
			return "addPer";
		}
		
		@RequestMapping({"addArea_2.html"})
		public String addArea_2(Model model,@RequestParam String xnombre)  throws IOException  {
			System.out.println(xnombre);
			

			try{
				int sw=this.areasManager.adicionarArea(xnombre);
			}catch (Exception e){
				model.addAttribute("xtexto", " ERROR AL GUADAR DATOS ..!");
				return "mensajesAreas";
			}
			model.addAttribute("xtexto", "SE ADICIONO AREA SATISFACTORIAMENTE");
			return "mensajesAreas";
		}
		
		@RequestMapping({"modArea.html"})
		public String modArea(HttpServletRequest request,Model model,@RequestParam String xcoda)  throws IOException  {
			
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
			
			ClaseDESBase64 des = new ClaseDESBase64();
			
			xcoda=des.desencriptar(xcoda);
			
			Map<String,Object> clave=this.areasManager.buscarArea(xcoda);
			
			model.addAttribute("datos",clave);
			return "modArea";
		}
		}
		
		@RequestMapping({"modArea_2.html"})
		public String modArea_2(Model model,@RequestParam String xnombre,String xcoda)  throws IOException  {
//			System.out.println("xru="+xru+" xnombre="+xnombre+" xap="+xap+" xam="+xam);
			
			try{
				int sw=this.areasManager.modificarArea(xnombre, xcoda);
			}catch (Exception e){
				model.addAttribute("xtexto", " ERROR AL MODIFICAR DATOS ..!");
				return "mensajesAreas";
			}
			model.addAttribute("xtexto", "SE MODIFICO SATISFACTORIAMENTE");

			return "mensajesAreas";
		}
		
		@RequestMapping({"delArea.html"})
		public String delArea(Model model,@RequestParam String xcoda)  throws IOException  {
			
			int sw=this.areasManager.eliminarArea(xcoda);
			
			model.addAttribute("xtexto", "SE ELIMINO SATISFACTORIAMENTE");
			return "mensajesAreas";
		}
		
		@RequestMapping({"habArea.html"})
		public String habArea(Model model,@RequestParam String xcoda)  throws IOException  {
			
			int sw=this.areasManager.habilitarArea(xcoda);
			
			model.addAttribute("xtexto", "SE HABILITO SATISFACTORIAMENTE");
			return "mensajesAreas";
		}
		
		// FIN AREAS
		// INICIO EDITORIALES
		
		@RequestMapping({"editoriales.html"})
		public String vista3(Model model,HttpServletRequest request)  throws IOException  {
			model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
			
			String xeditorial=request.getParameter("xeditorial");
			String xeditorial2;
			
			
			if (xeditorial==null) {  
				xeditorial="";  
			}
			
			Map<String, Object> Data = new HashMap();

			List<?> lista = this.editorialesManager.listar(1,"%"+xeditorial.toUpperCase()+"%");
			//Data.put("data", lista);
			
			model.addAttribute("xlista", lista);
			model.addAttribute("xeditorial2", xeditorial);
			return "GestionEditoriales";
		}
		}
		
		@RequestMapping({"editorialesA.html"})
		public String editorialesA(Model model,HttpServletRequest request)  throws IOException  {
			model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
			
			String xeditorial=request.getParameter("xeditorial");
			String xeditorial2;
			
			
			if (xeditorial==null) {  
				xeditorial="";  
			}
			
			Map<String, Object> Data = new HashMap();

			List<?> lista = this.editorialesManager.listarA(1,"%"+xeditorial.toUpperCase()+"%");
			//Data.put("data", lista);
			
			model.addAttribute("xlista", lista);
			model.addAttribute("xeditorial2", xeditorial);
			return "GestionEditorialesA";
		}
		}
		
		@RequestMapping({"editorialesB.html"})
		public String editorialesB(Model model,HttpServletRequest request)  throws IOException  {
			model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
			
			String xeditorial=request.getParameter("xeditorial");
			String xeditorial2;
			
			
			if (xeditorial==null) {  
				xeditorial="";  
			}
			
			Map<String, Object> Data = new HashMap();

			List<?> lista = this.editorialesManager.listarB(1,"%"+xeditorial.toUpperCase()+"%");
			//Data.put("data", lista);
			
			model.addAttribute("xlista", lista);
			model.addAttribute("xeditorial2", xeditorial);
			return "GestionEditorialesB";
		}
		}
		
		@RequestMapping({"addEditorial_1.html"})
		public String addEditorial_1(Model model)  throws IOException  {
			return "addEditorial";
		}
		
		@RequestMapping({"addEditorial_2.html"})
		public String addEditorial_2(Model model,@RequestParam String xnombre)  throws IOException  {
			System.out.println(xnombre);
			

			try{
				int sw=this.editorialesManager.adicionarEditorial( xnombre);
			}catch (Exception e){
				model.addAttribute("xtexto", " ERROR AL GUADAR DATOS ..!");
				return "mensajesEditoriales";
			}
			model.addAttribute("xtexto", "SE ADICIONO EDITORIAL SATISFACTORIAMENTE");
			return "mensajesEditoriales";
		}
		
		@RequestMapping({"modEditorial.html"})
		public String modEditorial(HttpServletRequest request,Model model,@RequestParam String xcode)  throws IOException  {
			
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
			
			ClaseDESBase64 des = new ClaseDESBase64();
			
			xcode=des.desencriptar(xcode);
			
			Map<String,Object> clave=this.editorialesManager.buscarEditorial(xcode);
			
			model.addAttribute("datos",clave);
			return "modEditorial";
		}
		}
		
		@RequestMapping({"modEditorial_2.html"})
		public String modEditorial_2(Model model,@RequestParam String xnombre,String xcode)  throws IOException  {
//			System.out.println("xru="+xru+" xnombre="+xnombre+" xap="+xap+" xam="+xam);
			
			try{
				int sw=this.editorialesManager.modificarEditorial(xnombre, xcode);
			}catch (Exception e){
				model.addAttribute("xtexto", " ERROR AL MODIFICAR DATOS ..!");
				return "mensajesEditoriales";
			}
			model.addAttribute("xtexto", "SE MODIFICO SATISFACTORIAMENTE");

			return "mensajesEditoriales";
		}
		
		@RequestMapping({"delEditorial.html"})
		public String delEditorial(Model model,@RequestParam String xcode)  throws IOException  {
			
			int sw=this.editorialesManager.eliminarEditorial(xcode);
			
			model.addAttribute("xtexto", "SE ELIMINO SATISFACTORIAMENTE");
			return "mensajesEditoriales";
		}
		
		@RequestMapping({"habEditorial.html"})
		public String habEditorial(Model model,@RequestParam String xcode)  throws IOException  {
			
			int sw=this.editorialesManager.habilitarEditorial(xcode);
			
			model.addAttribute("xtexto", "SE HABILITO SATISFACTORIAMENTE");
			return "mensajesEditoriales";
		}
		
		// FIN EDITORIALES
		// INICIO TIPOS
		
		@RequestMapping({"tipos.html"})
		public String vista4(Model model,HttpServletRequest request)  throws IOException  {
			model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
			
			String xtipo=request.getParameter("xtipo");
			String xtipo2;
			
			
			if (xtipo==null) {  
				xtipo="";  
			}
			
			Map<String, Object> Data = new HashMap();

			List<?> lista = this.tiposManager.listar(1,"%"+xtipo.toUpperCase()+"%");
			//Data.put("data", lista);
			
			model.addAttribute("xlista", lista);
			model.addAttribute("xtipo2", xtipo);
			return "GestionTipos";
		}
		}
		
		
		@RequestMapping({"tiposA.html"})
		public String tiposA(Model model,HttpServletRequest request)  throws IOException  {
			model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
			
			String xtipo=request.getParameter("xtipo");
			String xtipo2;
			
			
			if (xtipo==null) {  
				xtipo="";  
			}
			
			Map<String, Object> Data = new HashMap();

			List<?> lista = this.tiposManager.listarA(1,"%"+xtipo.toUpperCase()+"%");
			//Data.put("data", lista);
			
			model.addAttribute("xlista", lista);
			model.addAttribute("xtipo2", xtipo);
			return "GestionTiposA";
		}
		}
		
		@RequestMapping({"tiposB.html"})
		public String tiposB(Model model,HttpServletRequest request)  throws IOException  {
			model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
			
			String xtipo=request.getParameter("xtipo");
			String xtipo2;
			
			
			if (xtipo==null) {  
				xtipo="";  
			}
			
			Map<String, Object> Data = new HashMap();

			List<?> lista = this.tiposManager.listarB(1,"%"+xtipo.toUpperCase()+"%");
			//Data.put("data", lista);
			
			model.addAttribute("xlista", lista);
			model.addAttribute("xtipo2", xtipo);
			return "GestionTiposB";
		}
		}
		@RequestMapping({"addTipo_1.html"})
		public String addTipo_1(Model model)  throws IOException  {
			return "addTipo";
		}
		
		@RequestMapping({"addTipo_2.html"})
		public String addTipo_2(Model model,@RequestParam String xnombre,String xsw)  throws IOException  {
			System.out.println(xnombre+" hola "+xsw+" hola ");
			
			try{
				if(xsw==null){
					xsw="0";
					int sw=this.tiposManager.adicionarTipo( xnombre, xsw);
				}else{
					xsw="1";
					int sw=this.tiposManager.adicionarTipo( xnombre, xsw);
					}	
			}catch (Exception e){
				model.addAttribute("xtexto", " ERROR AL GUADAR DATOS ..!");
				return "mensajesTipos";
			}
			model.addAttribute("xtexto", "SE ADICIONO TIPO SATISFACTORIAMENTE");
			return "mensajesTipos";
		}
		
		@RequestMapping({"modTipo.html"})
		public String modTipo(HttpServletRequest request,Model model,@RequestParam String xcodtipo)  throws IOException  {
			
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
			
			ClaseDESBase64 des = new ClaseDESBase64();
			
			xcodtipo=des.desencriptar(xcodtipo);
			
			Map<String,Object> clave=this.tiposManager.buscarTipo(xcodtipo);
			
			model.addAttribute("datos",clave);
			return "modTipo";
		}
		}
		
		@RequestMapping({"modTipo_2.html"})
		public String modTipo_2(Model model,@RequestParam String xnombre,String xcodtipo,String xsw)  throws IOException  {
//			System.out.println("xru="+xru+" xnombre="+xnombre+" xap="+xap+" xam="+xam);
			
			try{
				if(xsw==null){
					xsw="0";
					int sw=this.tiposManager.modificarTipo(xnombre, xcodtipo , xsw);
				}else{
					xsw="1";
					int sw=this.tiposManager.modificarTipo(xnombre, xcodtipo , xsw);
				}	
			}catch (Exception e){
				model.addAttribute("xtexto", " ERROR AL MODIFICAR DATOS ..!");
				return "mensajesEditoriales";
			}
			model.addAttribute("xtexto", "SE MODIFICO SATISFACTORIAMENTE");

			return "mensajesTipos";
		}
		
		@RequestMapping({"delTipo.html"})
		public String delTipo(Model model,@RequestParam String xcodtipo)  throws IOException  {
			
			int sw=this.tiposManager.eliminarTipo(xcodtipo);
			
			model.addAttribute("xtexto", "SE ELIMINO SATISFACTORIAMENTE");
			return "mensajesTipos";
		}
		
		@RequestMapping({"habTipo.html"})
		public String habTipo(Model model,@RequestParam String xcodtipo)  throws IOException  {
			
			int sw=this.tiposManager.habilitarTipo(xcodtipo);
			
			model.addAttribute("xtexto", "SE HABILITO SATISFACTORIAMENTE");
			return "mensajesTipos";
		}
		
		// FIN TIPOS
		// INICIO AUTORES
		
		@RequestMapping({"autores.html"})
		public String vista5(Model model,HttpServletRequest request)  throws IOException  {
			model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
			
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
			
			String xautor=request.getParameter("xautor");
			String xautor2;
			
			
			if (xautor==null) {  
				xautor="";  
			}
			
			Map<String, Object> Data = new HashMap();

			List<?> lista = this.autoresManager.listar(1,"%"+xautor.toUpperCase()+"%");
			//Data.put("data", lista);
			
			model.addAttribute("xlista", lista);
			model.addAttribute("xautor2", xautor);
			return "GestionAutores";
		}
		}
		
		
		@RequestMapping({"autoresA.html"})
		public String vistaA5(Model model,HttpServletRequest request)  throws IOException  {
			model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			

			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
			
			String xautor=request.getParameter("xautor");
			String xautor2;
			
			
			if (xautor==null) {  
				xautor="";  
			}
			
			Map<String, Object> Data = new HashMap();

			List<?> lista = this.autoresManager.listarA(1,"%"+xautor.toUpperCase()+"%");
			//Data.put("data", lista);
			
			model.addAttribute("xlista", lista);
			model.addAttribute("xautor2", xautor);
			return "GestionAutoresA";
		}
		}
		
		@RequestMapping({"autoresB.html"})
		public String vistaB5(Model model,HttpServletRequest request)  throws IOException  {
			model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
			
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
			
			String xautor=request.getParameter("xpersona");
			String xautor2;
			
			
			if (xautor==null) {  
				xautor="";  
			}
			
			Map<String, Object> Data = new HashMap();

			List<?> lista = this.autoresManager.listarB(1,"%"+xautor.toUpperCase()+"%");
			//Data.put("data", lista);
			
			model.addAttribute("xlista", lista);
			model.addAttribute("xautor2", xautor);
			return "GestionAutoresB";
		}
		}

		@RequestMapping({"addAutor_1.html"})
		public String addAutor_1(Model model)  throws IOException  {
			return "addAutor";
		}
		
		@RequestMapping({"addAutor_2.html"})
		public String addAutor_2(Model model,@RequestParam String xnombre,String xap, String xam,String xgenero)  throws IOException  {
			System.out.println(xnombre+" "+xgenero);
			
				try{
					int sw=this.autoresManager.adicionarAutor( xnombre, xap, xam, xgenero);
				}catch (Exception e){
					model.addAttribute("xtexto", " ERROR AL GUADAR DATOS ..!");
					return "mensajesAutores";
					}
				model.addAttribute("xtexto", "SE ADICIONO SATISFACTORIAMENTE");
				return "mensajesAutores";
		}
		
		@RequestMapping({"delAutor.html"})
		public String delAutor(Model model,@RequestParam String xcoda)  throws IOException  {
			
			int sw=this.autoresManager.eliminarAutor(xcoda);
			
			model.addAttribute("xtexto", "SE ELIMINO SATISFACTORIAMENTE");
			return "mensajesAutores";
		}
		
		@RequestMapping({"habAutor.html"})
		public String habAutor(Model model,@RequestParam String xcoda)  throws IOException  {
			
			int sw=this.autoresManager.habilitarAutor(xcoda);
			
			model.addAttribute("xtexto", "SE HABILITO SATISFACTORIAMENTE");
			return "mensajesAutores";
		}
		
		@RequestMapping({"modAutor.html"})
		public String modAutor(HttpServletRequest request,Model model,@RequestParam String xcoda)  throws IOException  {
			
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
				
			ClaseDESBase64 des = new ClaseDESBase64();
			
			xcoda=des.desencriptar(xcoda);
			Map<String,Object> autor=this.autoresManager.buscarAutor(xcoda);
			
			model.addAttribute("datos",autor);
			return "modAutor";
		}
		}
		
		@RequestMapping({"modAutor_2.html"})
		public String modAutor_2(Model model,@RequestParam String xnombre,String xap, String xam, String xgenero,String xcoda)  throws IOException  {
//			System.out.println("xru="+xru+" xnombre="+xnombre+" xap="+xap+" xam="+xam);
				try{
				
					int sw=this.autoresManager.modificarAutor(xcoda, xnombre, xap, xam, xgenero);
					
				}catch (Exception e){
					
					model.addAttribute("xtexto", " ERROR AL MODIFICAR DATOS ..!");
					return "mensajesAutores";
				}
				
				model.addAttribute("xtexto", "SE MODIFICO SATISFACTORIAMENTE");
				return "mensajesAutores";

		}
		
		@RequestMapping({"verAutor.html"})
		public String verAutor(HttpServletRequest request,Model model,@RequestParam String xcoda)  throws IOException  {
			
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
			Map<String,Object> autor=this.autoresManager.buscarAutor(xcoda);
			
			model.addAttribute("autor",autor);
			return "verAutor";
		}
		}
		
		// FIN AUTORES
		// INICIO TEXTOS 
		
		@RequestMapping({"textos.html"})
		public String vistaT(Model model,HttpServletRequest request)  throws IOException  {
			model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
			
			
		
			
			
			
			
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
				
				List<?> xeditoriales = this.textosManager.xeditoriales();
				List<?> xareas = this.textosManager.xareas();
				List<?> xtipos = this.textosManager.xtipos();
				List<?> xautores = this.textosManager.xautores();
				
			model.addAttribute("xeditoriales", xeditoriales );
			model.addAttribute("xareas", xareas );
			model.addAttribute("xtipos", xtipos );
			model.addAttribute("xautores", xautores );
			
			String xarea=request.getParameter("xarea");
			String xeditorial=request.getParameter("xeditorial");
			String xtipo=request.getParameter("xtipo");
			String xtexto=request.getParameter("xtexto");
			String xtexto2;
			
			if (xtexto==null) {  
				xtexto="";  
			}
			//System.out.println("valor editorial: "+xeditorial);
			if (xeditorial==null) {
				xeditorial="";
			}
			//System.out.println("valor area: "+xarea);
			if (xarea==null) {
				xarea="";
			}
			//System.out.println("valor tipo: "+xtipo);
			if (xtipo==null) {
				xtipo="";
			}
			
			Map<String, Object> Data = new HashMap();

			List<?> lista = this.textosManager.listarTextos(1,"%"+xtexto.toUpperCase()+"%","%"+xeditorial.toUpperCase()+"%","%"+xarea.toUpperCase()+"%","%"+xtipo.toUpperCase()+"%");
			//Data.put("data", lista);
			
			
			model.addAttribute("xlista", lista);
			model.addAttribute("xtexto2", xtexto);
			return "GestionTextos";
		}
		}
		
		@RequestMapping({"textosA.html"})
		public String vistaTa(Model model,HttpServletRequest request)  throws IOException  {
			model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
			
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
				
				List<?> xeditoriales = this.textosManager.xeditoriales();
				List<?> xareas = this.textosManager.xareas();
				List<?> xtipos = this.textosManager.xtipos();
				List<?> xautores = this.textosManager.xautores();
				
			model.addAttribute("xeditoriales", xeditoriales );
			model.addAttribute("xareas", xareas );
			model.addAttribute("xtipos", xtipos );
			model.addAttribute("xautores", xautores );			
			
			String xarea=request.getParameter("xarea");
			String xeditorial=request.getParameter("xeditorial");
			String xtipo=request.getParameter("xtipo");
			String xtexto=request.getParameter("xtexto");
			String xtexto2;
			
			if (xtexto==null) {  
				xtexto="";  
			}
			//System.out.println("valor editorial: "+xeditorial);
			if (xeditorial==null) {
				xeditorial="";
			}
			//System.out.println("valor area: "+xarea);
			if (xarea==null) {
				xarea="";
			}
			//System.out.println("valor tipo: "+xtipo);
			if (xtipo==null) {
				xtipo="";
			}
			
			Map<String, Object> Data = new HashMap();

			List<?> lista = this.textosManager.listarTextosA(1,"%"+xtexto.toUpperCase()+"%","%"+xeditorial.toUpperCase()+"%","%"+xarea.toUpperCase()+"%","%"+xtipo.toUpperCase()+"%");
			//Data.put("data", lista);
			
			
			model.addAttribute("xlista", lista);
			model.addAttribute("xtexto2", xtexto);
			return "GestionTextosA";
		}
		}
		
		@RequestMapping({"textosB.html"})
		public String vistaTb(Model model,HttpServletRequest request)  throws IOException  {
			model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
			
			
		
			
			
			
			
			HttpSession sesion=request.getSession(true);
			String UserConectado=(String) sesion.getAttribute("login");
			Map xuser=(Map) sesion.getAttribute("xusuario");
			model.addAttribute("usuario",xuser);
			
			if (xuser==null){
				model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
				model.addAttribute("xanimacion", "autentificarse");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}else{
				List<?> xmenus = this.menuManager.xmenus();
				List<?> xopciones = this.menuManager.xopciones();
				model.addAttribute("xmenus", xmenus );	
				model.addAttribute("xopciones", xopciones );
				model.addAttribute("usuario",xuser);
				
				List<?> xeditoriales = this.textosManager.xeditoriales();
				List<?> xareas = this.textosManager.xareas();
				List<?> xtipos = this.textosManager.xtipos();
				List<?> xautores = this.textosManager.xautores();
				
			model.addAttribute("xeditoriales", xeditoriales );
			model.addAttribute("xareas", xareas );
			model.addAttribute("xtipos", xtipos );
			model.addAttribute("xautores", xautores );
			
			String xarea=request.getParameter("xarea");
			String xeditorial=request.getParameter("xeditorial");
			String xtipo=request.getParameter("xtipo");
			String xtexto=request.getParameter("xtexto");
			String xtexto2;
			
			if (xtexto==null) {  
				xtexto="";  
			}
			//System.out.println("valor editorial: "+xeditorial);
			if (xeditorial==null) {
				xeditorial="";
			}
			//System.out.println("valor area: "+xarea);
			if (xarea==null) {
				xarea="";
			}
			//System.out.println("valor tipo: "+xtipo);
			if (xtipo==null) {
				xtipo="";
			}
			
			Map<String, Object> Data = new HashMap();

			List<?> lista = this.textosManager.listarTextosB(1,"%"+xtexto.toUpperCase()+"%","%"+xeditorial.toUpperCase()+"%","%"+xarea.toUpperCase()+"%","%"+xtipo.toUpperCase()+"%");
			//Data.put("data", lista);
			
			
			model.addAttribute("xlista", lista);
			model.addAttribute("xtexto2", xtexto);
			return "GestionTextosB";
		}
		}
				
				@RequestMapping({"delTexto.html"})
				public String delTexto(Model model,@RequestParam String xcodt)  throws IOException  {
					//System.out.println(xcodt);
					int sw=this.textosManager.eliminarTexto(xcodt);
					
					model.addAttribute("xtexto", "SE ELIMINO SATISFACTORIAMENTE");
					return "mensajesTextos";
				}
				
				@RequestMapping({"habTexto.html"})
				public String habTexto(Model model,@RequestParam String xcodt)  throws IOException  {
					
					int sw=this.textosManager.habilitarTexto(xcodt);
					
					model.addAttribute("xtexto", "SE HABILITO SATISFACTORIAMENTE");
					return "mensajesTextos";
				}			
				
				@RequestMapping({"addTexto_1.html"})
				public String addTexto_1(Model model)  throws IOException  {
					return "addTexto";
				}
				
				@RequestMapping({"addTexto_2.html"})
				public String addTexto_2(Model model,@RequestParam("file") MultipartFile file,HttpServletRequest request,@RequestParam String xtitulo,String xresumen,String xfecha,int xedicion,String xisbn,String xcoda,int xcodtipo,String xcode,int xcant_1)  throws IOException, ParseException  {
					System.out.println("hola "+xtitulo+" "+xresumen+" "+xisbn+" "+xedicion+" "+xfecha+" "+xcoda+" "+xcode+" "+xcodtipo);					
					
					DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
					
					ServletContext ctx = request.getSession().getServletContext(); 
			        //System.out.println(ctx.getRealPath("/")); 
			    	String fileName = null;
					
			    	if (!file.isEmpty()) {
			            try {
			                fileName = file.getOriginalFilename();
			                byte[] bytes = file.getBytes();
			                BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(new File(ctx.getRealPath("/")+"PDFs/" + fileName)));
//			                File file = new File(ctx.getRealPath("/")+"uploads/" + item.getName());
			                buffStream.write(bytes);
			                buffStream.close();
			                // VALIDAR LA FOTO (.png .jpj)
			                
			                Date fecha2 = fecha.parse(xfecha);
							Map<String,Object> sw=this.textosManager.adicionarTexto(xtitulo, xresumen, xisbn, xedicion, fecha2, xcoda, xcode);
			                int codt= (Integer) sw.get("codt");
			                System.out.println("codt"+codt);
			                int sw2=this.textosManager.adicionarTipo(codt, xcodtipo , fileName);
							System.out.println(sw2);
							int sw3=this.textosManager.adicionarAutor(xcant_1,codt);
							System.out.println(codt);
							
//			                return "CORRECTO, EL ARCHIVO    "+ fileName + "   SE HA ENVIADO CON EXITO..";
			            } catch (Exception e) {
			                return "ERROR AL ENVIAR " + fileName + ": " + e.getMessage();
			            }
			        } else {
			        		
			        	Date fecha2 = fecha.parse(xfecha);
						Map<String,Object> sw=this.textosManager.adicionarTexto(xtitulo, xresumen, xisbn, xedicion, fecha2, xcoda, xcode);
		                int codt= (Integer) sw.get("codt");
		                System.out.println("codt"+codt);
		                int sw2=this.textosManager.adicionarTipo2(codt, xcodtipo);
						System.out.println(sw2);
						int sw3=this.textosManager.adicionarAutor(xcant_1,codt);
						System.out.println(codt);
			        	
			        	model.addAttribute("xtexto", "SE ADICIONO SATISFACTORIAMENTE");	
			        }
					
					model.addAttribute("xtexto", "SE ADICIONO SATISFACTORIAMENTE");
					return "mensajesTextos";
					
					
					
						
				}
				
				
				@RequestMapping({"modTexto.html"})
				public String modT(Model model,HttpServletRequest request,@RequestParam String xcodt)  throws IOException  {
					
					HttpSession sesion=request.getSession(true);
					String UserConectado=(String) sesion.getAttribute("login");
					Map xuser=(Map) sesion.getAttribute("xusuario");
					model.addAttribute("usuario",xuser);
					
					if (xuser==null){
						model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
						model.addAttribute("xanimacion", "autentificarse");
						model.addAttribute("xenlace", "index.html");
						return "mensajesAutentificacion";
					}else{
						
					
					List<?> xeditoriales = this.textosManager.xeditoriales();
					List<?> xareas = this.textosManager.xareas();
					List<?> xtipos = this.textosManager.xtipos();
					
					model.addAttribute("xeditoriales", xeditoriales );
					model.addAttribute("xareas", xareas );
					model.addAttribute("xtipos", xtipos );
					
					ClaseDESBase64 des = new ClaseDESBase64();
					
					xcodt=des.desencriptar(xcodt);
					
					Map<String,Object> texto=this.textosManager.buscarTexto(xcodt);
					
					model.addAttribute("texto",texto);
					return "modtexto";
				}
				}
				
				@RequestMapping({"modTexto_2.html"})
				public String moDT_2(Model model,@RequestParam int xcodt,String xtitulo,String xresumen,String xfecha,int xedicion,String xisbn,int xcoda,int xcodtipo,int xcode)  throws IOException  {
//					System.out.println("xru="+xru+" xnombre="+xnombre+" xap="+xap+" xam="+xam);
					
					
					try{
						//System.out.println("break");
						int sw=this.textosManager.modificarTexto(xtitulo, xresumen, xisbn, xedicion, xcoda, xcode, xcodt);
						int sw2=this.textosManager.modificarTipo(xcodt, xcodtipo);
						
					}catch (Exception e){
						model.addAttribute("xtexto", " ERROR AL GUADAR DATOS ..!");
						return "mensajesTextos";
						}
					model.addAttribute("xtexto", "SE MODIFICO SATISFACTORIAMENTE");
					return "mensajesTextos";

				}
				
				
								
				// FIN TEXTOS
				//INICIO EJEMPLARES
				
				@RequestMapping({"ejemplares.html"})
				public String vistaE(Model model,HttpServletRequest request)  throws IOException  {
					model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
					
	
					HttpSession sesion=request.getSession(true);
					String UserConectado=(String) sesion.getAttribute("login");
					Map xuser=(Map) sesion.getAttribute("xusuario");
					model.addAttribute("usuario",xuser);
					
					if (xuser==null){
						model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
						model.addAttribute("xanimacion", "autentificarse");
						model.addAttribute("xenlace", "index.html");
						return "mensajesAutentificacion";
					}else{
						List<?> xmenus = this.menuManager.xmenus();
						List<?> xopciones = this.menuManager.xopciones();
						model.addAttribute("xmenus", xmenus );	
						model.addAttribute("xopciones", xopciones );
						model.addAttribute("usuario",xuser);
						
						List<?> xeditoriales = this.textosManager.xeditoriales();
						List<?> xareas = this.textosManager.xareas();
						List<?> xtipos = this.textosManager.xtipos();
						
					model.addAttribute("xeditoriales", xeditoriales );
					model.addAttribute("xareas", xareas );
					model.addAttribute("xtipos", xtipos );
					
					String xarea=request.getParameter("xarea");
					String xeditorial=request.getParameter("xeditorial");
					String xtipo=request.getParameter("xtipo");
					String xtexto=request.getParameter("xtexto");
					String xtexto2;
					
					if (xtexto==null) {  
						xtexto="";  
					}
					//System.out.println("valor editorial: "+xeditorial);
					if (xeditorial==null) {
						xeditorial="";
					}
					//System.out.println("valor area: "+xarea);
					if (xarea==null) {
						xarea="";
					}
					//System.out.println("valor tipo: "+xtipo);
					if (xtipo==null) {
						xtipo="";
					}
					
					Map<String, Object> Data = new HashMap();

					List<?> lista = this.textosManager.listarTextos(1,"%"+xtexto.toUpperCase()+"%","%"+xeditorial.toUpperCase()+"%","%"+xarea.toUpperCase()+"%","%"+xtipo.toUpperCase()+"%");
					//Data.put("data", lista);
					
					
					model.addAttribute("xlista", lista);
					model.addAttribute("xtexto2", xtexto);
					return "GestionEjemplares";
				}
				}
				
				@RequestMapping({"ejemplaresA.html"})
				public String vistaEA(Model model,HttpServletRequest request)  throws IOException  {
					model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
					
	
					HttpSession sesion=request.getSession(true);
					String UserConectado=(String) sesion.getAttribute("login");
					Map xuser=(Map) sesion.getAttribute("xusuario");
					model.addAttribute("usuario",xuser);
					
					if (xuser==null){
						model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
						model.addAttribute("xanimacion", "autentificarse");
						model.addAttribute("xenlace", "index.html");
						return "mensajesAutentificacion";
					}else{
						List<?> xmenus = this.menuManager.xmenus();
						List<?> xopciones = this.menuManager.xopciones();
						model.addAttribute("xmenus", xmenus );	
						model.addAttribute("xopciones", xopciones );
						model.addAttribute("usuario",xuser);
						
						List<?> xeditoriales = this.textosManager.xeditoriales();
						List<?> xareas = this.textosManager.xareas();
						List<?> xtipos = this.textosManager.xtipos();
						
					model.addAttribute("xeditoriales", xeditoriales );
					model.addAttribute("xareas", xareas );
					model.addAttribute("xtipos", xtipos );
					
					String xarea=request.getParameter("xarea");
					String xeditorial=request.getParameter("xeditorial");
					String xtipo=request.getParameter("xtipo");
					String xtexto=request.getParameter("xtexto");
					String xtexto2;
					
					if (xtexto==null) {  
						xtexto="";  
					}
					//System.out.println("valor editorial: "+xeditorial);
					if (xeditorial==null) {
						xeditorial="";
					}
					//System.out.println("valor area: "+xarea);
					if (xarea==null) {
						xarea="";
					}
					//System.out.println("valor tipo: "+xtipo);
					if (xtipo==null) {
						xtipo="";
					}
					
					Map<String, Object> Data = new HashMap();

					List<?> lista = this.textosManager.listarTextosA(1,"%"+xtexto.toUpperCase()+"%","%"+xeditorial.toUpperCase()+"%","%"+xarea.toUpperCase()+"%","%"+xtipo.toUpperCase()+"%");
					//Data.put("data", lista);
					
					
					model.addAttribute("xlista", lista);
					model.addAttribute("xtexto2", xtexto);
					return "GestionEjemplaresA";
				}
				}
				
				@RequestMapping({"ejemplaresB.html"})
				public String vistaEB(Model model,HttpServletRequest request)  throws IOException  {
					model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
					
	
					HttpSession sesion=request.getSession(true);
					String UserConectado=(String) sesion.getAttribute("login");
					Map xuser=(Map) sesion.getAttribute("xusuario");
					model.addAttribute("usuario",xuser);
					
					if (xuser==null){
						model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
						model.addAttribute("xanimacion", "autentificarse");
						model.addAttribute("xenlace", "index.html");
						return "mensajesAutentificacion";
					}else{
						List<?> xmenus = this.menuManager.xmenus();
						List<?> xopciones = this.menuManager.xopciones();
						model.addAttribute("xmenus", xmenus );	
						model.addAttribute("xopciones", xopciones );
						model.addAttribute("usuario",xuser);
						
						List<?> xeditoriales = this.textosManager.xeditoriales();
						List<?> xareas = this.textosManager.xareas();
						List<?> xtipos = this.textosManager.xtipos();
						
					model.addAttribute("xeditoriales", xeditoriales );
					model.addAttribute("xareas", xareas );
					model.addAttribute("xtipos", xtipos );
					
					String xarea=request.getParameter("xarea");
					String xeditorial=request.getParameter("xeditorial");
					String xtipo=request.getParameter("xtipo");
					String xtexto=request.getParameter("xtexto");
					String xtexto2;
					
					if (xtexto==null) {  
						xtexto="";  
					}
					//System.out.println("valor editorial: "+xeditorial);
					if (xeditorial==null) {
						xeditorial="";
					}
					//System.out.println("valor area: "+xarea);
					if (xarea==null) {
						xarea="";
					}
					//System.out.println("valor tipo: "+xtipo);
					if (xtipo==null) {
						xtipo="";
					}
					
					Map<String, Object> Data = new HashMap();

					List<?> lista = this.textosManager.listarTextosB(1,"%"+xtexto.toUpperCase()+"%","%"+xeditorial.toUpperCase()+"%","%"+xarea.toUpperCase()+"%","%"+xtipo.toUpperCase()+"%");
					//Data.put("data", lista);
					
					
					model.addAttribute("xlista", lista);
					model.addAttribute("xtexto2", xtexto);
					return "GestionEjemplaresB";
				}
				}
				
				@RequestMapping({"Dejemplares.html"})
				public String DE(Model model,HttpServletRequest req)  throws IOException  {
					String xcodt=req.getParameter("xcodt");
					
					List<?> listaD = this.textosManager.listarEjemplares(Integer.parseInt(xcodt));
					
					
					model.addAttribute("xlista", listaD);	
					model.addAttribute("codigoT",xcodt);			
					return "DetalleEjemplares";
				}
				
				@RequestMapping({"addEjemplar_2.html"})
				public String addEjemplar_2(HttpServletRequest request,Model model,@RequestParam int xcodinv,int xcodt)  throws IOException  {
					//System.out.println(xnombre+" "+xgenero);
					HttpSession sesion=request.getSession(true);
					String UserConectado=(String) sesion.getAttribute("login");
					String UserConectado2=(String) sesion.getAttribute("login");
					Map xuser=(Map) sesion.getAttribute("xusuario");
					
					System.out.println("llego usuario::"+UserConectado);
					
					try{
						int sw=this.textosManager.adicionarEjemplar( xcodinv,xcodt,UserConectado,UserConectado);
					}catch (Exception e){
						model.addAttribute("xtexto", " ERROR AL GUADAR DATOS ..!");
						return "mensajesEjemplares";
						}
					model.addAttribute("xtexto", "SE ADICIONO SATISFACTORIAMENTE");
					return "mensajesEjemplares";
				}
				
				@RequestMapping({"modEjemplar.html"})
				public String modE(HttpServletRequest request,Model model,@RequestParam String xcodinv)  throws IOException  {
					
					HttpSession sesion=request.getSession(true);
					String UserConectado=(String) sesion.getAttribute("login");
					Map xuser=(Map) sesion.getAttribute("xusuario");
					model.addAttribute("usuario",xuser);
					
					if (xuser==null){
						model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
						model.addAttribute("xanimacion", "autentificarse");
						model.addAttribute("xenlace", "index.html");
						return "mensajesAutentificacion";
					}else{
						List<?> xmenus = this.menuManager.xmenus();
						List<?> xopciones = this.menuManager.xopciones();
						model.addAttribute("xmenus", xmenus );	
						model.addAttribute("xopciones", xopciones );
						model.addAttribute("usuario",xuser);
					
					ClaseDESBase64 des = new ClaseDESBase64();
					
					xcodinv=des.desencriptar(xcodinv);
					
					Map<String,Object> texto=this.textosManager.buscarEjemplar(xcodinv);
					
					model.addAttribute("ejemplar",texto);
					return "modEjemplar";
				}
				}
				
				@RequestMapping({"modEjemplar_2.html"})
				public String modE_2(Model model,@RequestParam int xcodinv,int xnvo)  throws IOException  {
					System.out.println(xcodinv+" - "+xnvo);
					
					try{
						int sw=this.textosManager.modificarEjemplar(xnvo, xcodinv);
					}catch (Exception e){
						model.addAttribute("xtexto", " ERROR AL MODIFICAR DATOS ..!");
						return "mensajesEjemplares";
					}
					model.addAttribute("xtexto", "SE MODIFICO SATISFACTORIAMENTE");

					return "mensajesEjemplares";
				}
				
				@RequestMapping({"delEjemplar.html"})
				public String delE(Model model,@RequestParam String xcodinv)  throws IOException  {
					//System.out.println(xcodinv);
					int sw=this.textosManager.eliminarEjemplar(xcodinv);
					
					model.addAttribute("xtexto", "SE ELIMINO SATISFACTORIAMENTE");
					return "mensajesEjemplares";
				}
				
				@RequestMapping({"habEjemplar.html"})
				public String habE(Model model,@RequestParam String xcodinv)  throws IOException  {
					
					int sw=this.textosManager.habilitarEjemplar(xcodinv);
					
					model.addAttribute("xtexto", "SE HABILITO SATISFACTORIAMENTE");
					return "mensajesEjemplares";
				}
				
				//FIN EJEMPLARES
				//INICIO PRESTAMOS DE EJEMPLARES
				
				@RequestMapping({"prestamos.html"})
				public String vistap(Model model,HttpServletRequest request)  throws IOException, ParseException  {
					model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
					
	
					HttpSession sesion=request.getSession(true);
					String UserConectado=(String) sesion.getAttribute("login");
					Map xuser=(Map) sesion.getAttribute("xusuario");
					model.addAttribute("usuario",xuser);
					
					if (xuser==null){
						model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
						model.addAttribute("xanimacion", "autentificarse");
						model.addAttribute("xenlace", "index.html");
						return "mensajesAutentificacion";
					}else{
						List<?> xmenus = this.menuManager.xmenus();
						List<?> xopciones = this.menuManager.xopciones();
						model.addAttribute("xmenus", xmenus );	
						model.addAttribute("xopciones", xopciones );
						model.addAttribute("usuario",xuser);
						
					String xpersona=request.getParameter("xpersona");
					String xpersona2;
					
					if (xpersona==null) {  
						xpersona="";  
					}
					
					String xfini=request.getParameter("xfini");
					String xfini2;
					
					String xffin=request.getParameter("xffin");
					String xffin2;
					
					DateFormat fechaIni = new SimpleDateFormat("dd/MM/yyyy");
					DateFormat fechaFin = new SimpleDateFormat("dd/MM/yyyy");
					
					System.out.println("llego fini:: "+xfini);
					System.out.println("llego ffin:: "+xffin);
					
					if (xfini==null || xffin==null || xfini.equals("") || xffin.equals("")) {  
						
					xfini="";
					xffin="";
					
					List<?> xest = this.textosManager.xpersona();
					model.addAttribute("xestudiante", xest );
					
					List<?> xejem = this.textosManager.xejemplares();
					model.addAttribute("xej", xejem );
					
					Map<String, Object> Data = new HashMap();

					List<?> lista = this.textosManager.listarPrestamos("%"+xpersona.toUpperCase()+"%");
					//Data.put("data", lista);
					model.addAttribute("xlista", lista);
					model.addAttribute("xpersona2", xpersona);
					model.addAttribute("xfini2", xfini);
					model.addAttribute("xffin2", xffin);
					
					return "GestionPrestamos";
				}else{
					System.out.println("entro");
					Date fechaIni2 = fechaIni.parse(xfini);
					Date fechaFin2 = fechaFin.parse(xffin);
					
					System.out.println("fecha:: "+fechaIni2);
					
					Map<String, Object> Data = new HashMap();

					List<?> lista = this.textosManager.listarPrestamos2("%"+xpersona.toUpperCase()+"%",fechaIni2,fechaFin2);
					//Data.put("data", lista);
					model.addAttribute("xlista", lista);
					model.addAttribute("xpersona2", xpersona);
					model.addAttribute("xfini2", xfini);
					model.addAttribute("xffin2", xffin);
					
					return "GestionPrestamos";
					}
				}
				}
				
				@RequestMapping({"Dprestamo.html"})
				public String Dp(Model model,HttpServletRequest req)  throws IOException  {
					String xcodp=req.getParameter("xcodp");
					
					List<?> listaD = this.textosManager.listarDprestamos(Integer.parseInt(xcodp));
					
					
					model.addAttribute("xlista", listaD);	
					model.addAttribute("codigoP",xcodp);			
					return "DetallePrestamo";
				}
				
				@RequestMapping({"delPrestamo.html"})
				public String delP(Model model,@RequestParam String xcodp)  throws IOException  {
					//System.out.println(xcodinv);
					int sw=this.textosManager.eliminarPrestamo(xcodp);
					
					model.addAttribute("xtexto", "SE ELIMINO SATISFACTORIAMENTE");
					return "mensajesPrestamos";
				}
				
				@RequestMapping({"addPrestamo_1.html"})
				public String addP_1(Model model)  throws IOException  {
					return "addPer";
				}
				
				@RequestMapping({"addPrestamo_2.html"})
				public String addP_2(Model model,HttpServletRequest request,@RequestParam String xfecha,String xfini,String xffin,int xtipopres,String xci,int xcant_1)  throws IOException  {
					System.out.println(xfecha+" "+xfini+" "+xffin+" "+xtipopres+" "+xci+" "+xcant_1);
					
					HttpSession sesion=request.getSession(true);
					String UserConectado=(String) sesion.getAttribute("login");
					Map xuser=(Map) sesion.getAttribute("xusuario");
					
					System.out.println("llego usuario::"+UserConectado);
					
					DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
					DateFormat fechaIni = new SimpleDateFormat("dd/MM/yyyy");
					DateFormat fechaFin = new SimpleDateFormat("dd/MM/yyyy");

					try{
						Date fecha2 = fechaIni.parse(xfecha);
						Date fechaIni2 = fechaIni.parse(xfini);
						Date fechaFin2 = fechaFin.parse(xffin);
						Map<String,Object> sw=this.textosManager.adicionarPrestamo(fecha2, fechaIni2, fechaFin2, xtipopres, xci, UserConectado);
		                int xcodp=(Integer) sw.get("codp");
						System.out.println(xcodp+" "+xcant_1);
		                int sw2=this.textosManager.adicionarDPrestamo(xcodp,xcant_1);
					}catch (Exception e){
						model.addAttribute("xtexto", " ERROR AL GUADAR DATOS ..!");
						return "mensajesPrestamos";
					}
					model.addAttribute("xtexto", "SE ADICIONO PRESTAMO SATISFACTORIAMENTE");
					return "mensajesPrestamos";
				}
				
				
				
				
				
				
				
				
				
				//FIN PRESTAMOS DE EJEMPLARES
				
		// INICIO GESTION DEVOLUCIONES
		
				@RequestMapping({"devoluciones.html"})
				public String vistad(Model model,HttpServletRequest request)  throws IOException, ParseException  {
					model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
					
	
					HttpSession sesion=request.getSession(true);
					String UserConectado=(String) sesion.getAttribute("login");
					Map xuser=(Map) sesion.getAttribute("xusuario");
					model.addAttribute("usuario",xuser);
					
					if (xuser==null){
						model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
						model.addAttribute("xanimacion", "autentificarse");
						model.addAttribute("xenlace", "index.html");
						return "mensajesAutentificacion";
					}else{
						List<?> xmenus = this.menuManager.xmenus();
						List<?> xopciones = this.menuManager.xopciones();
						model.addAttribute("xmenus", xmenus );	
						model.addAttribute("xopciones", xopciones );
						model.addAttribute("usuario",xuser);
						
					String xpersona=request.getParameter("xpersona");
					String xpersona2;
					
					String xcodinv=request.getParameter("xcodinv");
					String xcodinv2;
					
					if (xpersona==null && xcodinv==null) {  
						xpersona="";  
						xcodinv="";
					}
					
					System.out.println("codinv:: "+xcodinv);
					
					String xfini=request.getParameter("xfini");
					String xfini2;
					
					String xffin=request.getParameter("xffin");
					String xffin2;
					
					DateFormat fechaIni = new SimpleDateFormat("dd/MM/yyyy");
					DateFormat fechaFin = new SimpleDateFormat("dd/MM/yyyy");
					
					System.out.println("llego fini:: "+xfini);
					System.out.println("llego ffin:: "+xffin);
					
					if (xfini==null || xffin==null || xfini.equals("") || xffin.equals("")) {  
						
					xfini="";
					xffin="";
					
					List<?> xest = this.textosManager.xpersona();
					model.addAttribute("xestudiante", xest );
					
					List<?> xejem = this.textosManager.xejemplares();
					model.addAttribute("xej", xejem );
					
					Map<String, Object> Data = new HashMap();

					List<?> lista = this.textosManager.listarPrestamosDevol("%"+xpersona.toUpperCase()+"%");
					//Data.put("data", lista);
					model.addAttribute("xlista", lista);
					model.addAttribute("xpersona2", xpersona);
					model.addAttribute("xcodinv2", xcodinv);
					model.addAttribute("xfini2", xfini);
					model.addAttribute("xffin2", xffin);
					
					
					return "GestionDevoluciones";
				}else{
					System.out.println("entro");
					Date fechaIni2 = fechaIni.parse(xfini);
					Date fechaFin2 = fechaFin.parse(xffin);
					
					System.out.println("fecha:: "+fechaIni2);
					
					Map<String, Object> Data = new HashMap();

					List<?> lista = this.textosManager.listarPrestamos2Devol("%"+xpersona.toUpperCase()+"%",fechaIni2,fechaFin2);
					//Data.put("data", lista);
					model.addAttribute("xlista", lista);
					model.addAttribute("xpersona2", xpersona);
					model.addAttribute("xfini2", xfini);
					model.addAttribute("xffin2", xffin);
					
					return "GestionDevoluciones";
					}
				}
				}
				
				@RequestMapping({"Dprestamo2.html"})
				public String Dpd(Model model,HttpServletRequest req)  throws IOException  {
					String xcodp=req.getParameter("xcodp");
					
					
					List<?> listaD = this.textosManager.listarDprestamos(Integer.parseInt(xcodp));
					List<?> listaM = this.textosManager.mpresta();
					
					model.addAttribute("xlistaMprestamo", listaM);
					model.addAttribute("xlista", listaD);	
					model.addAttribute("codigoP",xcodp);			
					return "DetalleDevolucion";
				}
				
				@RequestMapping({"saveDevol.html"})
				public String saveDevol(HttpServletRequest request, Model model,HttpServletResponse res)  throws IOException  {				
					String zcodp=request.getParameter("codp");

			    	System.out.println("llegooo::"+zcodp);
			    	
			    	textosManager.addDevol(Integer.parseInt(zcodp));
			    	
			    	//res.sendRedirect("index.html");
			    	
					return "GestionDevoluciones";
				}
				 	
				
		// FIN GESTION DEVOLUCIONES
		//INICIO GESTION ANULACION DE DEVOLUCIONES
				@RequestMapping({"anulaciones.html"})
				public String vistaAd(Model model,HttpServletRequest request)  throws IOException, ParseException  {
					model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
					
	
					HttpSession sesion=request.getSession(true);
					String UserConectado=(String) sesion.getAttribute("login");
					Map xuser=(Map) sesion.getAttribute("xusuario");
					model.addAttribute("usuario",xuser);
					
					if (xuser==null){
						model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
						model.addAttribute("xanimacion", "autentificarse");
						model.addAttribute("xenlace", "index.html");
						return "mensajesAutentificacion";
					}else{
						List<?> xmenus = this.menuManager.xmenus();
						List<?> xopciones = this.menuManager.xopciones();
						model.addAttribute("xmenus", xmenus );	
						model.addAttribute("xopciones", xopciones );
						model.addAttribute("usuario",xuser);
						
					String xpersona=request.getParameter("xpersona");
					String xpersona2;
					
					String xcodinv=request.getParameter("xcodinv");
					String xcodinv2;
					
					if (xpersona==null && xcodinv==null) {  
						xpersona="";  
						xcodinv="";
					}
					
					System.out.println("codinv:: "+xcodinv);
					
					String xfini=request.getParameter("xfini");
					String xfini2;
					
					String xffin=request.getParameter("xffin");
					String xffin2;
					
					DateFormat fechaIni = new SimpleDateFormat("dd/MM/yyyy");
					DateFormat fechaFin = new SimpleDateFormat("dd/MM/yyyy");
					
					System.out.println("llego fini:: "+xfini);
					System.out.println("llego ffin:: "+xffin);
					
					if (xfini==null || xffin==null || xfini.equals("") || xffin.equals("")) {  
						
					xfini="";
					xffin="";
					
					List<?> xest = this.textosManager.xpersona();
					model.addAttribute("xestudiante", xest );
					
					List<?> xejem = this.textosManager.xejemplares();
					model.addAttribute("xej", xejem );
					
					Map<String, Object> Data = new HashMap();

					List<?> lista = this.textosManager.listarAnulacionDevol("%"+xpersona.toUpperCase()+"%");
					//Data.put("data", lista);
					model.addAttribute("xlista", lista);
					model.addAttribute("xpersona2", xpersona);
					model.addAttribute("xcodinv2", xcodinv);
					model.addAttribute("xfini2", xfini);
					model.addAttribute("xffin2", xffin);
					
					
					return "GestionAnulaciones";
				}else{
					System.out.println("entro");
					Date fechaIni2 = fechaIni.parse(xfini);
					Date fechaFin2 = fechaFin.parse(xffin);
					
					System.out.println("fecha:: "+fechaIni2);
					
					Map<String, Object> Data = new HashMap();

					List<?> lista = this.textosManager.listarAnulacionDevol2("%"+xpersona.toUpperCase()+"%",fechaIni2,fechaFin2);
					//Data.put("data", lista);
					model.addAttribute("xlista", lista);
					model.addAttribute("xpersona2", xpersona);
					model.addAttribute("xfini2", xfini);
					model.addAttribute("xffin2", xffin);
					
					return "GestionAnulaciones";
					}
				}
				}
				
				@RequestMapping({"Adevol2.html"})
				public String DApd(Model model,HttpServletRequest req)  throws IOException  {
					String xcodd=req.getParameter("xcodd");
					System.out.println(xcodd);
					
					List<?> listaD = this.textosManager.listarDdevol(Integer.parseInt(xcodd));
					List<?> listaM = this.textosManager.mpresta();
					
					model.addAttribute("xlistaMprestamo", listaM);
					model.addAttribute("xlista", listaD);	
					model.addAttribute("codigoP",xcodd);			
					return "DetalleAnulacion";
				}
				
				/*@RequestMapping({"saveDevol.html"})
				public String saveADevol(HttpServletRequest request, Model model,HttpServletResponse res)  throws IOException  {				
					String zcodp=request.getParameter("codp");

			    	System.out.println("llegooo::"+zcodp);
			    	
			    	textosManager.addDevol(Integer.parseInt(zcodp));
			    	
			    	//res.sendRedirect("index.html");
			    	
					return "GestionDevoluciones";
				}
				*/
				
		//FIN GESTION ANULACION DE DEVOLUCIONES		
		// INICIO GESTION MENUS
				
				@RequestMapping({"GestionMenus.html"})
				public String vistaM(Model model,HttpServletRequest request)  throws IOException  {
					model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
					
					HttpSession sesion=request.getSession(true);
					String UserConectado=(String) sesion.getAttribute("login");
					Map xuser=(Map) sesion.getAttribute("xusuario");
					model.addAttribute("usuario",xuser);
					
					if (xuser==null){
						model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
						model.addAttribute("xanimacion", "autentificarse");
						model.addAttribute("xenlace", "index.html");
						return "mensajesAutentificacion";
					}else{
						List<?> xmenus = this.menuManager.xmenus();
						List<?> xopciones = this.menuManager.xopciones();
						model.addAttribute("xmenus", xmenus );	
						model.addAttribute("xopciones", xopciones );
						model.addAttribute("usuario",xuser);
					
					String xmenu=request.getParameter("xmenu");
					String xmenu2;
					
					
					if (xmenu==null) {  
						xmenu="";  
					}
					
					Map<String, Object> Data = new HashMap();

					List<?> lista = this.menuManager.listar(1,"%"+xmenu.toUpperCase()+"%");
					//Data.put("data", lista);
					
					model.addAttribute("xlista", lista);
					model.addAttribute("xmenu2", xmenu);
					return "GestionMenus";
				}
				}
				
				@RequestMapping({"GestionMenusA.html"})
				public String vistaMa(Model model,HttpServletRequest request)  throws IOException  {
					model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
					
					HttpSession sesion=request.getSession(true);
					String UserConectado=(String) sesion.getAttribute("login");
					Map xuser=(Map) sesion.getAttribute("xusuario");
					model.addAttribute("usuario",xuser);
					
					if (xuser==null){
						model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
						model.addAttribute("xanimacion", "autentificarse");
						model.addAttribute("xenlace", "index.html");
						return "mensajesAutentificacion";
					}else{
						List<?> xmenus = this.menuManager.xmenus();
						List<?> xopciones = this.menuManager.xopciones();
						model.addAttribute("xmenus", xmenus );	
						model.addAttribute("xopciones", xopciones );
						model.addAttribute("usuario",xuser);
					
					String xmenu=request.getParameter("xmenu");
					String xmenu2;
					
					
					if (xmenu==null) {  
						xmenu="";  
					}
					
					Map<String, Object> Data = new HashMap();

					List<?> lista = this.menuManager.listarA(1,"%"+xmenu.toUpperCase()+"%");
					//Data.put("data", lista);
					
					model.addAttribute("xlista", lista);
					model.addAttribute("xmenu2", xmenu);
					return "GestionMenusA";
				}
				}
				
				@RequestMapping({"GestionMenusB.html"})
				public String vistaMb(Model model,HttpServletRequest request)  throws IOException  {
					model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
					
					HttpSession sesion=request.getSession(true);
					String UserConectado=(String) sesion.getAttribute("login");
					Map xuser=(Map) sesion.getAttribute("xusuario");
					model.addAttribute("usuario",xuser);
					
					if (xuser==null){
						model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
						model.addAttribute("xanimacion", "autentificarse");
						model.addAttribute("xenlace", "index.html");
						return "mensajesAutentificacion";
					}else{
						List<?> xmenus = this.menuManager.xmenus();
						List<?> xopciones = this.menuManager.xopciones();
						model.addAttribute("xmenus", xmenus );	
						model.addAttribute("xopciones", xopciones );
						model.addAttribute("usuario",xuser);
					
					String xmenu=request.getParameter("xmenu");
					String xmenu2;
					
					
					if (xmenu==null) {  
						xmenu="";  
					}
					
					Map<String, Object> Data = new HashMap();

					List<?> lista = this.menuManager.listarB(1,"%"+xmenu.toUpperCase()+"%");
					//Data.put("data", lista);
					
					model.addAttribute("xlista", lista);
					model.addAttribute("xmenu2", xmenu);
					return "GestionMenusB";
				}
				}
				
				@RequestMapping({"addMenu_1.html"})
				public String addMenu_1(Model model)  throws IOException  {
					return "addPer";
				}
				
				@RequestMapping({"addMenu_2.html"})
				public String addMenu_2(Model model,@RequestParam String xnombre)  throws IOException  {
					System.out.println(xnombre);
					

					try{
						int sw=this.menuManager.adicionarMenu(xnombre);
					}catch (Exception e){
						model.addAttribute("xtexto", " ERROR AL GUADAR DATOS ..!");
						return "mensajesMenus";
					}
					model.addAttribute("xtexto", "SE ADICIONO MENU SATISFACTORIAMENTE");
					return "mensajesMenus";
				}
				
				@RequestMapping({"modMenu.html"})
				public String modMenu(HttpServletRequest request,Model model,@RequestParam String xcodm)  throws IOException  {
					
					HttpSession sesion=request.getSession(true);
					String UserConectado=(String) sesion.getAttribute("login");
					Map xuser=(Map) sesion.getAttribute("xusuario");
					model.addAttribute("usuario",xuser);
					
					if (xuser==null){
						model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
						model.addAttribute("xanimacion", "autentificarse");
						model.addAttribute("xenlace", "index.html");
						return "mensajesAutentificacion";
					}else{
						List<?> xmenus = this.menuManager.xmenus();
						List<?> xopciones = this.menuManager.xopciones();
						model.addAttribute("xmenus", xmenus );	
						model.addAttribute("xopciones", xopciones );
						model.addAttribute("usuario",xuser);
					
					ClaseDESBase64 des = new ClaseDESBase64();
					
					xcodm=des.desencriptar(xcodm);
					
					Map<String,Object> clave=this.menuManager.buscarMenu(xcodm);
					
					model.addAttribute("datos",clave);
					return "modMenu";
				}
				}
				
				@RequestMapping({"modMenu_2.html"})
				public String modMenu_2(Model model,@RequestParam String xnombre,String xcodm)  throws IOException  {
//					System.out.println("xru="+xru+" xnombre="+xnombre+" xap="+xap+" xam="+xam);
					
					try{
						int sw=this.menuManager.modificarMenu(xnombre, xcodm);
					}catch (Exception e){
						model.addAttribute("xtexto", " ERROR AL MODIFICAR DATOS ..!");
						return "mensajesMenus";
					}
					model.addAttribute("xtexto", "SE MODIFICO SATISFACTORIAMENTE");

					return "mensajesMenus";
				}
				
				@RequestMapping({"delMenu.html"})
				public String delMenu(Model model,@RequestParam String xcodm)  throws IOException  {
					
					int sw=this.menuManager.eliminarMenu(xcodm);
					
					model.addAttribute("xtexto", "SE ELIMINO SATISFACTORIAMENTE");
					return "mensajesMenus";
				}
				
				@RequestMapping({"habMenu.html"})
				public String habMenu(Model model,@RequestParam String xcodm)  throws IOException  {
					
					int sw=this.menuManager.habilitarMenu(xcodm);
					
					model.addAttribute("xtexto", "SE HABILITO SATISFACTORIAMENTE");
					return "mensajesMenus";
				}		
				
		// FIN GESTION MENUS		
				
		// INICIO GESTION ROLES
				
				@RequestMapping({"roles.html"})
				public String vistaR(Model model,HttpServletRequest request)  throws IOException  {
					model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
					
					HttpSession sesion=request.getSession(true);
					String UserConectado=(String) sesion.getAttribute("login");
					Map xuser=(Map) sesion.getAttribute("xusuario");
					model.addAttribute("usuario",xuser);
					
					if (xuser==null){
						model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
						model.addAttribute("xanimacion", "autentificarse");
						model.addAttribute("xenlace", "index.html");
						return "mensajesAutentificacion";
					}else{
						List<?> xmenus = this.menuManager.xmenus();
						List<?> xopciones = this.menuManager.xopciones();
						model.addAttribute("xmenus", xmenus );	
						model.addAttribute("xopciones", xopciones );
						model.addAttribute("usuario",xuser);
					
					String xrol=request.getParameter("xrol");
					String xrol2;
					
					
					if (xrol==null) {  
						xrol="";  
					}
					
					Map<String, Object> Data = new HashMap();

					List<?> lista = this.rolesManager.listar(1,"%"+xrol.toUpperCase()+"%");
					//Data.put("data", lista);
					
					model.addAttribute("xlista", lista);
					model.addAttribute("xrol2", xrol);
					return "GestionRoles";
				}
				}
				
				@RequestMapping({"rolesA.html"})
				public String vistaRa(Model model,HttpServletRequest request)  throws IOException  {
					model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
					
					HttpSession sesion=request.getSession(true);
					String UserConectado=(String) sesion.getAttribute("login");
					Map xuser=(Map) sesion.getAttribute("xusuario");
					model.addAttribute("usuario",xuser);
					
					if (xuser==null){
						model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
						model.addAttribute("xanimacion", "autentificarse");
						model.addAttribute("xenlace", "index.html");
						return "mensajesAutentificacion";
					}else{
						List<?> xmenus = this.menuManager.xmenus();
						List<?> xopciones = this.menuManager.xopciones();
						model.addAttribute("xmenus", xmenus );	
						model.addAttribute("xopciones", xopciones );
						model.addAttribute("usuario",xuser);
					
					String xrol=request.getParameter("xrol");
					String xrol2;
					
					
					if (xrol==null) {  
						xrol="";  
					}
					
					Map<String, Object> Data = new HashMap();

					List<?> lista = this.rolesManager.listarA(1,"%"+xrol.toUpperCase()+"%");
					//Data.put("data", lista);
					
					model.addAttribute("xlista", lista);
					model.addAttribute("xrol2", xrol);
					return "GestionRolesA";
				}
				}
				
				@RequestMapping({"rolesB.html"})
				public String vistaRb(Model model,HttpServletRequest request)  throws IOException  {
					model.addAttribute("xtexto", "Break! :) ,estamos Trabajando con SPRING ..!");
					
					HttpSession sesion=request.getSession(true);
					String UserConectado=(String) sesion.getAttribute("login");
					Map xuser=(Map) sesion.getAttribute("xusuario");
					model.addAttribute("usuario",xuser);
					
					if (xuser==null){
						model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
						model.addAttribute("xanimacion", "autentificarse");
						model.addAttribute("xenlace", "index.html");
						return "mensajesAutentificacion";
					}else{
						List<?> xmenus = this.menuManager.xmenus();
						List<?> xopciones = this.menuManager.xopciones();
						model.addAttribute("xmenus", xmenus );	
						model.addAttribute("xopciones", xopciones );
						model.addAttribute("usuario",xuser);
					
					String xrol=request.getParameter("xrol");
					String xrol2;
					
					
					if (xrol==null) {  
						xrol="";  
					}
					
					Map<String, Object> Data = new HashMap();

					List<?> lista = this.rolesManager.listarB(1,"%"+xrol.toUpperCase()+"%");
					//Data.put("data", lista);
					
					model.addAttribute("xlista", lista);
					model.addAttribute("xrol2", xrol);
					return "GestionRolesB";
				}
				}
				
				@RequestMapping({"addRol_1.html"})
				public String addRol_1(Model model)  throws IOException  {
					return "addRol";
				}
				
				@RequestMapping({"addRol_2.html"})
				public String addRol_2(Model model,@RequestParam String xnombre)  throws IOException  {
					System.out.println(xnombre);
					

					try{
						int sw=this.rolesManager.adicionarRol(xnombre);
					}catch (Exception e){
						model.addAttribute("xtexto", " ERROR AL GUADAR DATOS ..!");
						return "mensajesRoles";
					}
					model.addAttribute("xtexto", "SE ADICIONO ROL SATISFACTORIAMENTE");
					return "mensajesRoles";
				}
				
				@RequestMapping({"modRol.html"})
				public String modRol(HttpServletRequest request,Model model,@RequestParam String xcodr)  throws IOException  {
					
					HttpSession sesion=request.getSession(true);
					String UserConectado=(String) sesion.getAttribute("login");
					Map xuser=(Map) sesion.getAttribute("xusuario");
					model.addAttribute("usuario",xuser);
					
					if (xuser==null){
						model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
						model.addAttribute("xanimacion", "autentificarse");
						model.addAttribute("xenlace", "index.html");
						return "mensajesAutentificacion";
					}else{
						List<?> xmenus = this.menuManager.xmenus();
						List<?> xopciones = this.menuManager.xopciones();
						model.addAttribute("xmenus", xmenus );	
						model.addAttribute("xopciones", xopciones );
						model.addAttribute("usuario",xuser);
					
					ClaseDESBase64 des = new ClaseDESBase64();
					
					xcodr=des.desencriptar(xcodr);

					Map<String,Object> clave=this.rolesManager.buscarRol(xcodr);
					
					model.addAttribute("datos",clave);
					return "ModRol";
				}
				}
				
				@RequestMapping({"modRol_2.html"})
				public String modRol_2(Model model,@RequestParam String xnombre,String xcodr)  throws IOException  {
//					System.out.println("xru="+xru+" xnombre="+xnombre+" xap="+xap+" xam="+xam);
					
					try{
						int sw=this.rolesManager.modificarRol(xnombre, xcodr);
					}catch (Exception e){
						model.addAttribute("xtexto", " ERROR AL MODIFICAR DATOS ..!");
						return "mensajesRoles";
					}
					model.addAttribute("xtexto", "SE MODIFICO SATISFACTORIAMENTE");

					return "mensajesRoles";
				}
				
				@RequestMapping({"delRol.html"})
				public String delRol(Model model,@RequestParam String xcodr)  throws IOException  {
					
					int sw=this.rolesManager.eliminarRol(xcodr);
					
					model.addAttribute("xtexto", "SE ELIMINO SATISFACTORIAMENTE");
					return "mensajesRoles";
				}
				
				@RequestMapping({"habRol.html"})
				public String habRol(Model model,@RequestParam String xcodr)  throws IOException  {
					
					int sw=this.rolesManager.habilitarRol(xcodr);
					
					model.addAttribute("xtexto", "SE HABILITO SATISFACTORIAMENTE");
					return "mensajesRoles";
				}		
				
		// FIN GESTION ROLES		
				
		// INICIO ASIGNACION MENU PROC 
				
				@RequestMapping({"menus.html"})
				public String vistaMp(HttpServletRequest request,Model model)  throws IOException  {
					HttpSession sesion=request.getSession(true);
					String UserConectado=(String) sesion.getAttribute("login");
					Map xuser=(Map) sesion.getAttribute("xusuario");
					model.addAttribute("usuario",xuser);
					
					if (xuser==null){
						model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
						model.addAttribute("xanimacion", "autentificarse");
						model.addAttribute("xenlace", "index.html");
						return "mensajesAutentificacion";
					}else{
						List<?> xmenus = this.menuManager.xmenus();
						List<?> xopciones = this.menuManager.xopciones();
						model.addAttribute("xmenus", xmenus );	
						model.addAttribute("xopciones", xopciones );
						model.addAttribute("usuario",xuser);
					
					String xmenu=request.getParameter("xmenu");
					String xmenu2;
					
					
					if (xmenu==null) {  
						xmenu="";  
					}
					
					Map<String, Object> Data = new HashMap();

					List<?> lista = this.menuManager.listar(1,"%"+xmenu.toUpperCase()+"%");
					//Data.put("data", lista);
					
					model.addAttribute("xlista", lista);
					model.addAttribute("xmenu2", xmenu);
					return "GestionMepro";
				}
				}
				
				
				@RequestMapping({"procesos.html"})
				public String procesos(Model model,HttpServletRequest req)  throws IOException  {
					String xcodm=req.getParameter("xcodm");
					List<?> listaProc = this.menuManager.listarProcesos(1);
					List<?> listaMepro = this.menuManager.listarMepro(Integer.parseInt(xcodm));
					
					
					model.addAttribute("xlistaProc", listaProc);		
					model.addAttribute("xlistaMepro", listaMepro);
								
					return "procesos";
				}
				
				@RequestMapping({"saveMepro.html"})
				public String saveProceso(HttpServletRequest request, Model model,HttpServletResponse res)  throws IOException  {				
					String zcodp=request.getParameter("codp");
					String zcodm=request.getParameter("codm");

					System.out.println("llegooo::"+zcodm);
			    	System.out.println("llegooo::"+zcodp);
			    	
			    	menuManager.addMepro(Integer.parseInt(zcodp),Integer.parseInt(zcodm));
			    	
			    	//res.sendRedirect("index.html");
			    	
					return "GestionMepro";
				}
				
				@RequestMapping({"delMepro.html"})
				public String delProceso(HttpServletRequest request, Model model,HttpServletResponse res)  throws IOException  {				
					String zcodp=request.getParameter("codp");
					String zcodm=request.getParameter("codm");

					System.out.println("llegooo::"+zcodm);
			    	System.out.println("llegooo::"+zcodp);
			    	
			    	menuManager.delMepro(Integer.parseInt(zcodp),Integer.parseInt(zcodm));
			    	
			    	//res.sendRedirect("index.html");
			    	
					return "GestionMepro";
				}
		//FIN ASIGNACION MENU PROC
		// INICIO ASIGNACION MENU A ROL
				
				@RequestMapping({"GestionRoles.html"})
				public String vistaMR(HttpServletRequest request,Model model)  throws IOException  {
					HttpSession sesion=request.getSession(true);
					String UserConectado=(String) sesion.getAttribute("login");
					Map xuser=(Map) sesion.getAttribute("xusuario");
					model.addAttribute("usuario",xuser);
					
					if (xuser==null){
						model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
						model.addAttribute("xanimacion", "autentificarse");
						model.addAttribute("xenlace", "index.html");
						return "mensajesAutentificacion";
					}else{
						List<?> xmenus = this.menuManager.xmenus();
						List<?> xopciones = this.menuManager.xopciones();
						model.addAttribute("xmenus", xmenus );	
						model.addAttribute("xopciones", xopciones );
						model.addAttribute("usuario",xuser);
					
					String xrol=request.getParameter("xrol");
					String xrol2;
					
					
					if (xrol==null) {  
						xrol="";  
					}
					
					Map<String, Object> Data = new HashMap();

					List<?> lista = this.menuManager.listarR(1,"%"+xrol.toUpperCase()+"%");
					//Data.put("data", lista);
					
					model.addAttribute("xlista", lista);
					model.addAttribute("xrol2", xrol);
					return "GestionRolme";
				}
				}
				
				
				@RequestMapping({"menusAjax.html"})
				public String menusM(Model model,HttpServletRequest req)  throws IOException  {
					String xcodr=req.getParameter("xcodr");
					List<?> listaMenu = this.menuManager.listarM(1);
					List<?> listaRolme = this.menuManager.listarRolme(Integer.parseInt(xcodr));
					
					
					model.addAttribute("xlistaMenu", listaMenu);		
					model.addAttribute("xlistaRolme", listaRolme);
								
					return "menus";
				}
				
				@RequestMapping({"saveRolme.html"})
				public String saveMenu(HttpServletRequest request, Model model,HttpServletResponse res)  throws IOException  {				
					String zcodm=request.getParameter("codm");
					String zcodr=request.getParameter("codr");

					//System.out.println("llegooo::"+zcodm);
			    	//System.out.println("llegooo::"+zcodr);
			    	
			    	menuManager.addRolme(Integer.parseInt(zcodm),Integer.parseInt(zcodr));
			    	
			    	//res.sendRedirect("index.html");
			    	
					return "GestionRolme";
				}
				
				@RequestMapping({"delRolme.html"})
				public String delMenu(HttpServletRequest request, Model model,HttpServletResponse res)  throws IOException  {				
					String zcodm=request.getParameter("codm");
					String zcodr=request.getParameter("codr");

					System.out.println("llegooo codm::"+zcodm);
			    	System.out.println("llegooo codr::"+zcodr);
			    	
			    	menuManager.delRolme(Integer.parseInt(zcodm),Integer.parseInt(zcodr));
			    	
			    	//res.sendRedirect("index.html");
			    	
					return "GestionRolme";
				}
				
				
		// FIN ASIGNACION MENU A ROL		
		// INICIO ASIGNACION ROLES A USUARIO
				
// INICIO ASIGNACION MENU A ROL
				
				@RequestMapping({"AsigRol.html"})
				public String vistaAR(HttpServletRequest request,Model model)  throws IOException  {
					HttpSession sesion=request.getSession(true);
					String UserConectado=(String) sesion.getAttribute("login");
					Map xuser=(Map) sesion.getAttribute("xusuario");
					model.addAttribute("usuario",xuser);
					
					if (xuser==null){
						model.addAttribute("xtexto", "ERROR, PRIMERO DEBE AUTENTIFICARSE..");
						model.addAttribute("xanimacion", "autentificarse");
						model.addAttribute("xenlace", "index.html");
						return "mensajesAutentificacion";
					}else{
						List<?> xmenus = this.menuManager.xmenus();
						List<?> xopciones = this.menuManager.xopciones();
						model.addAttribute("xmenus", xmenus );	
						model.addAttribute("xopciones", xopciones );
						model.addAttribute("usuario",xuser);
					
					String xusuario=request.getParameter("xusuario");
					String xusuario2;
					
					
					if (xusuario==null) {  
						xusuario="";  
					}
					
					Map<String, Object> Data = new HashMap();

					List<?> lista = this.menuManager.listarU(1,"%"+xusuario.toUpperCase()+"%");
					//Data.put("data", lista);
					
					model.addAttribute("xlista", lista);
					model.addAttribute("xusuario2", xusuario);
					return "GestionRolusu";
				}
				}
				
				
				@RequestMapping({"rolesAjax.html"})
				public String menusRA(Model model,HttpServletRequest req)  throws IOException  {
					String xlog=req.getParameter("xlogin");
					List<?> listaRol = this.menuManager.listarR(1);
					List<?> listaRolusu = this.menuManager.listarRolusu(xlog);
					
					
					model.addAttribute("xlistaRol", listaRol);		
					model.addAttribute("xlistaRolusu", listaRolusu);
								
					return "roles";
				}
				
				@RequestMapping({"saveRolusu.html"})
				public String saveRM(HttpServletRequest request, Model model,HttpServletResponse res)  throws IOException  {				
					String zcodr=request.getParameter("codr");
					String zlogin=request.getParameter("login");

					System.out.println("llegooo::"+zcodr);
			    	System.out.println("llegooo::"+zlogin);
			    	
			    	menuManager.addRolusu(Integer.parseInt(zcodr),zlogin);
			    	
			    	//res.sendRedirect("index.html");
			    	
					return "GestionRolusu";
				}
				
				@RequestMapping({"delRolusu.html"})
				public String delR(HttpServletRequest request, Model model,HttpServletResponse res)  throws IOException  {				
					String zcodr=request.getParameter("codr");
					String zlogin=request.getParameter("login");

					System.out.println("llegooo codr::"+zcodr);
			    	System.out.println("llegooo login::"+zlogin);
			    	
			    	menuManager.delRolusu(Integer.parseInt(zcodr),zlogin);
			    	
			    	//res.sendRedirect("index.html");
			    	
					return "GestionRolusu";
				}
				
						
				
		// FIN ASIGNACION ROLES A USUARIO		
				
		@RequestMapping({"valida.html"})
		public String vistaV(HttpServletRequest request, Model model,@RequestParam String xlogin , String xclave)  throws IOException  {
			
			//String xlogin = request.getParameter("xlogin");
			//String xpass = request.getParameter("xpass");
			
			System.out.println(xlogin);
			System.out.println(xclave);
			
			try{
				Map<String,Object> user=this.personasManager.buscarUsuario(xlogin,xclave);
				HttpSession sesion=request.getSession(true);
				sesion.setAttribute("login", xlogin);
				//System.out.println("login para enviar "+xlogin);
				//System.out.println("passwd para enviar "+xpasswd);
				sesion.setAttribute("xusuario",user);
				//System.out.println("usuario para enviar "+user);
				return "redirect:index2.html";
			}catch (Exception e){
				model.addAttribute("xtexto", " USUARIO NO AUTORIZADO!!!");
				model.addAttribute("xanimacion", "noautorizado");
				model.addAttribute("xenlace", "index.html");
				return "mensajesAutentificacion";
			}
		
		}
		
		@RequestMapping({"MenuLateral.html"})
		public String menuL(HttpServletRequest request, Model model)  throws IOException  {
			String xcodr=request.getParameter("xcodr");
			//System.out.println(xcodr);
			System.out.println("eL CODR para menu:: "+xcodr);
			List<?> xmenus = this.menuManager.xmenusA(xcodr);
			List<?> xopciones = this.menuManager.xopciones();
			model.addAttribute("xmenus", xmenus );
			model.addAttribute("xopciones", xopciones );
			System.out.println("hola");
			return "Menu";
		}
		
		@RequestMapping({"Cerrar.html"})
		public String cerrar(HttpServletRequest request, Model model)  throws IOException  {
			HttpSession sesion=request.getSession(true);
			sesion.removeAttribute("login");
			sesion.removeAttribute("xusuario");
			/*
			model.addAttribute("xtexto", "SESION CERRADA...");
			model.addAttribute("xanimacion", "aceptacion");
			model.addAttribute("xenlace", "index.html");
			*/
			return "redirect:index.html";
		}

	}
	
	
