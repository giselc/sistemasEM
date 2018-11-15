<%-- 
    Document   : cadete
    Created on : Apr 6, 2018, 8:46:18 AM
    Author     : Gisel
--%>

<%@page import="Manejadores.ManejadorPersonal"%>
<%@page import="Classes.Cadete"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/jquery-ui.js"></script>

    <script>
        function existeCadete(ciInput){
            xmlhttp=new XMLHttpRequest();
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                    var obj = jQuery.parseJSON( xmlhttp.responseText );
                    var existe = obj.Cadete;
                    if(existe.length>0){
                        if(existe.historial){
                            var r=confirm("Existe el cadete en el historial, si confirma, sus datos son cargados automaticamente.");
                            if (r==true){
                                window.location.href="Cadete?ci="+ciInput.value+"&crearDesdeHistorial=1";
                            }
                            else{
                                alert("No es posible crear el cadete si no es a través del historial.");
                                window.location.href="cadetes.jsp";
                            }
                        }
                        else{
                            alert("El cadete ya existe en el sistema.");
                            window.location.href="cadete.jsp?id="+ciInput.value;
                        }
                    }
                    else{
                        document.getElementById("rellenarOtrosDatos").style.display = '';
                    }
                };
            };
            xmlhttp.open("POST","Cadete?existe=1&ci="+ciInput.value);
            xmlhttp.send();
            return false;
        }
        function abrir_dialog(dialog) {
          $( dialog ).dialog({
              modal: true
          });
        };
        function cerrar_dialog(dialog) {
          $( dialog ).dialog('close');
        };
        $(document).ready(function() {
                $("#content div").hide();
                $("#tabs li:first").attr("id","current");
                $("#content div:first").fadeIn();
                $("#loader").fadeOut();
            $('#tabs a').click(function(e) {
                document.getElementById("mensaje").innerHTML="";
                e.preventDefault();
                $("#content div").hide();
                $("#tabs li").attr("id","");
                $(this).parent().attr("id","current");
                $('#' + $(this).attr('title')).fadeIn();
            });
        });
        function showPaseDirecto(b,admin){
            c = ((new String(b).valueOf() == new String("13").valueOf()) || admin);
            if(document.getElementById('lmga').checked && c){
                document.getElementById('pd').style.display = '';
            }
            else{
                document.getElementById('pd').style.display = 'none';
            }
        }
        function showNotaPaseDirecto(b,admin){
            c = ((new String(b).valueOf() == new String("13").valueOf()) || admin);
            if(document.getElementById('paseDirecto').checked && c){
                document.getElementById('notaPaseDirecto').style.display = '';
            }
            else{
                document.getElementById('notaPaseDirecto').style.display = 'none';
            }
        }
        
     </script>
     <%
        ManejadorPersonal mp = ManejadorPersonal.getInstance();
        Cadete c=null;
        int idTipoPersonal=1;
        if(request.getParameter("id")!=null){
           c = mp.getCadete(Integer.valueOf(request.getParameter("id")));
        }
     %>
    <div id='dialog2' style="display:none" title="Imprimir ficha personal">
       <form method="post" target="_blank" onsubmit="return listar(this)" name="formListar" action='Listar?tipo=personal&ci=<%=request.getParameter("id")%>'>
           <p>
               <b>Datos Basicos:</b>
               <input type="checkbox" name="datosBasicos" checked="checked"/>
           </p>
           <p>
               <b>Familiares:</b>
               <input type="checkbox" name="familiares" checked="checked"/>
           </p>
           <input type="submit" value="Imprimir"/>
       </form>
    </div>
           <table style="width: 100%">
               <tr>
                   <td>
                       <p align="left"><a href="cadetes.jsp"><img src="images/atras.png" width="20%"/></a></p>
                   </td>
                   <td>
                       <img src="images/imprimir.png" width="20%" onclick="abrir_dialog(dialog2)" />
                   </td>
               </tr>
           </table>
     
    <p id="mensaje" style="color: #990000"><% if(session.getAttribute("mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+session.getAttribute("mensaje"));}%></p>
    <%
        session.setAttribute("mensaje",null);
    %>
     <ul id="tabs">
        <li><a href="#" title="Datos-Personales">Datos Personales</a></li>
         <%--<li <% if (request.getParameter("ci")==null){ out.print("hidden='hidden'");} %>><a href="#" title="Familiares">Familiares</a></li>--%>
       <li <% if (request.getParameter("id")==null){ out.print("hidden='hidden'");} %>><a href="#" title="Documentos">Documentos</a></li>
    </ul>
    <div id="content">
         <div id="Datos-Personales"><%@include file="datosBasicos.jsp" %></div>
         <%--<div id="Familiares"><%@include file="familiares.jsp" %></div> --%>
          <div id="Documentos"><%@include file="documentos.jsp" %></div>
     </div>
<%@ include file="footer.jsp" %>

