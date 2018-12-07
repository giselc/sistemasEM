<%-- 
    Document   : sistemas
    Created on : Apr 4, 2018, 10:15:22 AM
    Author     : Gisel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<% if(u!=null && u.isAdmin()){%>
<style type="text/css">
                        
			.nav2 > li {
				float:left;
			}
			.nav2 li a {
                                
				background-color:rgba(158,158,158,0.8);
				color:#fff;
				text-decoration:none;
				padding:10px 5px;
				display:block;
			}
			
			.nav2 li a:hover{
				background-color:#D35400;
			}
			
			.nav2 li ul {
				display:none;
				position:absolute;
                                z-index: 5;
                                margin-left: -40px;
				width:15%;
                                 
			}
			
			.nav2 li:hover > ul {
				display:block;
			}
			
			.nav2 li ul li {
				position:relative;
                                
			}
			
			.nav2 li ul li ul {
				right:-15%;
				top:0px;
                                
			}
                        .nav2 li ul li ul li{
				width:220px;
			}
			
    </style> 

    <p id="mensaje" style="color: #ffffff"><% if(session.getAttribute("Mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+session.getAttribute("Mensaje"));}%></p>
    <%
        session.setAttribute("Mensaje",null);
    %>
    <ul class="nav2" style="width: 80%;padding-left: 10%;min-height: 400px;">
            <li  style="width: 25%">
                <a><p align="center" style="margin:0px"><img  class="boton" title="S1" src="images/button_personal.png"/></p></a>
                <ul>
                    <li  style="width: 90%">
                        <a href="cadetes.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Cadetes" src="images/button_cadetes.png"/></p></a>
                    </li>
                    <li style="width: 90%">
                        <a href="personal.jsp?tipo=2"><p align="center" style="margin:0px"><img  class="boton" title="PS" src="images/button_p-s.png"/></p></a>
                    </li>
                    <li style="width: 90%">
                        <a href="personal.jsp?tipo=3"><p align="center" style="margin:0px"><img  class="boton" title="Oficiales" src="images/button_oficiales.png"/></p></a>
                    </li>
                    <li style="width: 90%">
                        <a href="profesores.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Profesores" src="images/button_profesores.png"/></p></a>
                    </li>
                </ul>
            </li>
            <li style="width: 25%">
                <a><p align="center" style="margin:0px"><img  class="boton" title="Descuentos" src="images/button_descuentos.png"/></p></a>
                <ul>
                    <li  style="width: 90%">
                        <a href="descuentos?tipo=biblioteca.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Biblioteca" src="images/button_biblioteca.png"/></p></a>
                    </li>
                    <li style="width: 90%">
                        <a href="descuentos?tipo=impresiones.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Impresiones" src="images/button_impresiones.png"/></p></a>
                    </li>
                    <li style="width: 90%">
                        <a href="descuentos?tipo=farmacia.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Farmacia" src="images/button_farmacia.png"/></p></a>
                    </li>
                    <li style="width: 90%">
                        <a href="descuentos?tipo=enfermeria.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Enfermeria" src="images/button_enfermeria.png"/></p></a>
                    </li>
                    <li style="width: 90%">
                        <a href="descuentos?tipo=casinossoo.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Casino SS.OO." src="images/button_casino-ss-oo.png"/></p></a>
                    </li>
                    <li style="width: 90%">
                        <a href="descuentos?tipo=casinocc.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Casino C.C." src="images/button_casino-cc.png"/></p></a>
                    </li>
                </ul>
            </li>
            <li style="width: 25%">
                <a href="notas.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Notas" src="images/button_notas.png"/></p></a>
            </li>
            <li style="width: 25%">
                <a href="habilitacion.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Habilitacion" src="images/button_habilitacion.png"/></p></a>
            </li>
    </ul>
    <%
}
else{
    response.sendRedirect((String)sesion.getAttribute("inicio"));
}
    %>




<%@ include file="footer.jsp" %> 