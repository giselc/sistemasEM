<%-- 
    Document   : bedelia
    Created on : Jan 15, 2019, 10:55:30 AM
    Author     : Gisel
--%>

<%@page import="Manejadores.ManejadorBedelia"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script src="js/jquery-1.9.1.min.js"></script>
    <script src="js/jquery-ui.js"></script>
<script>
    var intervalo= setInterval('hayNotificacionesNuevas()',5000);
    
    function hayNotificacionesNuevas(){
        xmlhttp=new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                        
                        var obj = jQuery.parseJSON( xmlhttp.responseText );
                        var notificaciones = obj.notificaciones;
                        var imgNotificaciones= document.getElementById("botonNotificaciones");
                        //alert(notificaciones[0].cantNotificaciones>0);
                        if(notificaciones[0].cantNotificaciones>0){
                            if(imgNotificaciones.src!="images/new_notificaciones.png"){
                                imgNotificaciones.src="images/new_notificaciones.png";}
                        }
                        else{
                            if(imgNotificaciones.src!="images/button_notificaciones.png"){
                                imgNotificaciones.src="images/button_notificaciones.png";}
                        }
                        
                    };
                };
                xmlhttp.open("POST","Notificaciones?hayNotificaciones=1");
                xmlhttp.send();
                return false;
    }
</script>
<%@ include file="header.jsp" %>
<% if(u!=null && (u.isAdmin()|| (u.getPermisosPersonal()!=null && u.getPermisosPersonal().getId()==4))){
    ManejadorBedelia mb= ManejadorBedelia.getInstance();
%>
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
			}
			
    </style> 

    <p id="mensaje" style="color: #ffffff"><% if(session.getAttribute("Mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+session.getAttribute("Mensaje"));}%></p>
    <%
        session.setAttribute("Mensaje",null);
    %>
    
    <p align="left"><a href="javascript:history.go(-1)"><img src="images/atras.png" width="15%"/></a></p>
    <ul class="nav2" style="width: 80%;padding-left: 10%;min-height: 400px;">
            <li  style="width: 30%">
                <a><p align="center" style="margin:0px"><img  class="boton" title="Listados" src="images/button_listados.png"/></p></a>
                <ul>
                    <li  style="width: 90%">
                        <a href="notas.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Notas" src="images/button_notas_1.png"/></p></a>
                    </li>
                    <li style="width: 90%">
                        <a href=""><p align="center" style="margin:0px"><img  class="boton" title="Promedios" src="images/button_promedios.png"/></p></a>
                        <ul>
                           <li  style="width: 500%">
                               <a href="promedios?tipo=1.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Mensual" src="images/button_mensual.png"/></p></a>
                            </li>
                            <li  style="width: 500%">
                                <a href="promedios?tipo=2.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Semestral" src="images/button_semestral.png"/></p></a>
                            </li>
                            <li  style="width: 500%">
                                <a href="promedios?tipo=3.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Anual" src="images/button_anual.png"/></p></a>
                            </li>
                        </ul>
                    </li>
                    <li style="width: 90%">
                        <a href="inasistencias.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Inasistencia" src="images/button_inasistencias.png"/></p></a>
                    </li>
                    <li style="width: 90%">
                        <a href="sanciones.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Sanciones" src="images/button_sanciones.png"/></p></a>
                    </li>
                     
                </ul>
            </li>
            <li style="width: 30%">
                <a><p align="center" style="margin:0px"><img  class="boton" title="Administración" src="images/button_administracion.png"/></p></a>
                <ul>
                    <li  style="width: 90%">
                        <a href="cursos.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Cursos" src="images/button_cursos.png"/></p></a>
                    </li>
                    <li style="width: 90%">
                        <a href="materias.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Materias" src="images/button_materias.png"/></p></a>
                    </li>
                    <%--li style="width: 90%">
                        <a href="grupos.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Grupos" src="images/button_grupos.png"/></p></a>
                    </li --%>
                    <li style="width: 90%">
                        <a href="profesores.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Profesores" src="images/button_profesores_1.png"/></p></a>
                    </li>
                    <li style="width: 90%">
                         <a href="libretas.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Libretas" src="images/button_libretas.png"/></p></a>
                    </li>
                     <li style="width: 90%">
                         <a href="examenes.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Exámenes" src="images/button_examenes.png"/></p></a>
                    </li>
                     <li style="width: 90%">
                         <a href="escolaridad.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Escolaridad" src="images/button_escolaridad.png"/></p></a>
                    </li>
                    <%--li style="width: 90%">
                        <a href="horarios.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Horarios" src="images/button_horarios.png"/></p></a>
                    </li>
                    <li style="width: 90%">
                        <a href="salones.jsp"><p align="center" style="margin:0px"><img  class="boton" title="Salones" src="images/button_salones.png"/></p></a>
                    </li--%>
                </ul>
            </li>
            <li style="width: 30%">
                <a href="notificaciones.jsp"><p align="center" style="margin:0px"><img  id="botonNotificaciones" class="boton" title="Notificaciones" <% if(mb.getNotificacionesNuevas().size()==0){out.print("src=\"images/button_notificaciones.png\"");}else{out.print("src=\"images/new_notificaciones.png\"");} %>/></p></a>
            </li>
    </ul>
    <%
}
else{
    response.sendRedirect((String)sesion.getAttribute("inicio"));
}
    %>




<%@ include file="footer.jsp" %> 
