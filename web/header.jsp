<%--
    Document   : postulantes
    Created on : Mar 18, 2016, 11:57:12 AM
    Author     : Gisel
--%>

<%@page import="Manejadores.ManejadorCodigos"%>
<%@page import="Classes.Usuario"%>
<%@page import="Manejadores.ManejadorCodigoBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/sistemasEM.css" type="text/css"/>
        <link rel="stylesheet" href="css/jquery-ui.css" type="text/css"/>
        <link rel="stylesheet" href="css/tabs.css" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style type="text/css">
                        
			ul, ol {
				list-style:none;
                               
			}
			.nav > li {
				float:left;
			}
			.nav li a {
                                
				background-color:rgba(158,158,158,0.8);
				color:#fff;
				text-decoration:none;
				padding:10px 12px;
				display:block;
			}
			
			.nav li a:hover {
				background-color:#434343;
			}
			
			.nav li ul {
				display:none;
				position:absolute;
                                z-index: 5;
                                margin-left: -40px;
				min-width:260px;
			}
			
			.nav li:hover > ul {
				display:block;
			}
			
			.nav li ul li {
				position:relative;
			}
			
			.nav li ul li ul {
				right:-260px;
				top:0px;
			}
			
		</style>
        <title>Sistemas internos de la Escuela Militar</title>
    </head>
    <body style="background-color: #bfd070;">
        
        <table style="width: 100%;">
            <tr>
                <td class="fondo">
                    
                    <% 
                    HttpSession sesion= request.getSession();
                    if (sesion.getAttribute("usuarioID")!=null){
                    %>
                    <table style="width: 100%">
                        <tr>
                            <td style="width: 20%; vertical-align: top" >
                                <p style="color: #000000; margin: 0px"><%
                                    ManejadorCodigos mc = ManejadorCodigos.getInstance();
                                    Usuario u= (Classes.Usuario)sesion.getAttribute("usuario");
                                    out.print("Bienvenido ");out.print(u.getNombreMostrar());
                                    %></p>
                                
                                    <ul class="nav" style="padding: 0px;margin: 0px">
                                        <li><a <% out.print("href='"+sesion.getAttribute("inicio")+"'"); %>><table><tr><td align="center"><img src="images/home.png" width="80%" /></td><td>Inicio</td></tr></table> </a></li>
                                        <li><a > <table><tr><td align="center"><img src="images/menu.png" width="80%" /></td><td>Menu</td></tr></table> </a>
                                            <ul>
                                                        <li><a href="cambiarContrasena.jsp?id=<%= u.getId() %>">Cambiar contrase&ntilde;a </a></li>
                                                        
                                                        <%
                                                            if (u.isAdmin()){
                                                        %>
                                                             <li><a href="usuarios.jsp">Usuarios </a></li>
                                                             <li>
                                                                <form action="AgregarPersonal?tipo=1" method="POST" style="font-size: 100%">
                                                                    <a align="center"><input type="submit" value="Agregar Cadetes"/></a>
                                                                </form>
                                                            </li>
                                                            <li>
                                                                <form action="AgregarPersonal?tipo=2" method="POST" style="font-size: 100%">
                                                                    <a align="center"><input type="submit" value="Agregar Personal"/></a>
                                                                </form>
                                                            </li>
                                                            <li>
                                                                <form action="AgregarPersonal?tipo=4" method="POST" style="font-size: 100%">
                                                                    <a align="center"><input type="submit" value="Agregar Profesores"/></a>
                                                                </form>
                                                            </li>
                                                        <%
                                                            }
                                                            if (u.isAdmin()|| u.getPermisosPersonal().getId()==1){
                                                        %>
                                                            <li><a href="bajasYActualizacionGrados.jsp">Baja 3er a&ntilde;o y actualizaci&oacute;n de grados </a></li>
                                                        <%
                                                            }
                                                        %>
                                                        <li>
                                                            <form action="logout" method="POST" style="font-size: 100%">
                                                                <a align="center"><input type="submit" value="SALIR"/></a>
                                                            </form>
                                                        </li>
                                                </ul>
                                        </li>
                                    </ul>
                                
                            </td>
                            <td style="width: 60%; vertical-align: top">
                                <p align="center" ><img src="images/logo-escuelaMilitar.png" title="INICIO" <% out.print("onclick=location.href='"+sesion.getAttribute("inicio")+"'"); %> style="height: 15%; width: 100%"/></p>
                            </td>
                            
                            <td style="width: 20%">
                                
                            </td>
                        </tr>
                    </table>
                    <table style="width: 100%" >
                        <tr>
                            <td style="width: 5%">
                            </td>
                            <td>
                                </td>
                        </tr>
                        <tr>
                            <td style="width: 5%">
                            </td>
                            <td style="width: 90%;" class="conteinermenu">
                                <p align="center" style="margin-top: 0px"><img src="images/LetrasSistemasEM.png" style="width: 35%"/></p>