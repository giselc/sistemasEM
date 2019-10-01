<%-- 
    Document   : materias
    Created on : May 4, 2019, 5:16:51 AM
    Author     : Gisel
--%>

<%@page import="Classes.Bedelia.Materia"%>
<%@page import="java.util.HashMap"%>
<%@page import="Classes.Bedelia.CursoBedelia"%>
<%@page import="Manejadores.ManejadorBedelia"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>   
<% 
    if(u.isAdmin() || (u.getPermisosPersonal()!=null && u.getPermisosPersonal().getId()==4)){
%>
    <script src="js/jquery-1.9.1.min.js"></script>
    <script src="js/jquery-ui.js"></script>
    <script>
             $(document).ready(function() {
                     $("#content div").hide();
                     $("#tabs li:first").attr("id","current");
                     $("#content div:first").fadeIn();
                     $("#loader").fadeOut();

                 $('#tabs a').click(function(e) {
                     e.preventDefault();
                     $("#content div").hide();
                     $("#tabs li").attr("id","");
                     $(this).parent().attr("id","current");
                     $('#' + $(this).attr('title')).fadeIn();
                 });
             });
    </script>
    <script>
        function abrir_dialog(dialog) {
          $( dialog ).dialog({
              modal: true
          });
        };
        function cerrar_dialog(dialog) {
          $( dialog ).dialog('close');
        };
        function listar(tipoPersonal) {//Funcion creada para no perder la sesion luego del submit
            var form=document.getElementById("formProfesoresListar");
            var x = document.getElementsByName("List[]");
            for (i=0;i<x.length;i++) {
                if(x[i].type == "checkbox"){
                   x[i].checked=1
                }
            }
            var filtro1=document.getElementById("filtroTexto").innerHTML;
            if(!(filtro1==(""))){
                form.action="Listar?tipoPersonal="+tipoPersonal+"&filtro="+filtro1;
            }
            form.submit();
            for (i=0;i<x.length;i++) {
                if(x[i].type == "checkbox"){
                   x[i].checked=0
                }
            }
            cerrar_dialog(dialog2);
            return false;
        };
        function serialize(form) {
                if (!form || form.nodeName !== "FORM") {
                        return;
                }
                var i, j, q = [];
                for (i = form.elements.length - 1; i >= 0; i = i - 1) {
                        if (form.elements[i].name === "") {
                                continue;
                        }
                        switch (form.elements[i].nodeName) {
                        case 'INPUT':
                                switch (form.elements[i].type) {
                                case 'text':
                                case 'hidden':
                                case 'password':
                                case 'button':
                                case 'reset':
                                case 'submit':
                                        q.push(form.elements[i].name + "=" + encodeURIComponent(form.elements[i].value));
                                        break;
                                case 'checkbox':
                                case 'radio':
                                        if (form.elements[i].checked) {
                                                q.push(form.elements[i].name + "=" + encodeURIComponent(form.elements[i].value));
                                        }						
                                        break;
                                case 'file':
                                        break;
                                }
                                break;			 
                        case 'TEXTAREA':
                                q.push(form.elements[i].name + "=" + encodeURIComponent(form.elements[i].value));
                                break;
                        case 'SELECT':
                                switch (form.elements[i].type) {
                                case 'select-one':
                                        q.push(form.elements[i].name + "=" + encodeURIComponent(form.elements[i].value));
                                        break;
                                case 'select-multiple':
                                        for (j = form.elements[i].options.length - 1; j >= 0; j = j - 1) {
                                                if (form.elements[i].options[j].selected) {
                                                        q.push(form.elements[i].name + "=" + encodeURIComponent(form.elements[i].options[j].value));
                                                }
                                        }
                                        break;
                                }
                                break;
                        case 'BUTTON':
                                switch (form.elements[i].type) {
                                case 'reset':
                                case 'submit':
                                case 'button':
                                        q.push(form.elements[i].name + "=" + encodeURIComponent(form.elements[i].value));
                                        break;
                                }
                                break;
                        }
                }
                return q.join("&");
        }
        
    </script>

    <p id="mensaje" style="color: #ffffff"><% if(sesion.getAttribute("Mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+sesion.getAttribute("Mensaje"));}%></p>
<%
    sesion.setAttribute("Mensaje",null);
%> 
<p align="left"><a href="javascript:history.go(-1)"><img src="images/atras.png" width="15%"/></a></p>
    <ul id="tabs">
        <li><a href="#"><b>MATERIAS</b></a></li>
    </ul>
    <div id="loader" style="z-index: 50;position: fixed; top:0; left:0; width:100%; height: 100%;background: url('images/loading-verde.gif') center center no-repeat; background-size: 10%"></div>
    <div id="content">
        <div>
            <div id='dialog2' style="display:none" title="Seleccione los campos a listar:">
                <form method="post" target="_blank"  id="formProfesoresListar" name="formProfesoresListar" action='Listar?tipoPersonal=4'>
                    
                </form>
            </div>
            <table style="float: right">
    <tr>
        <td style="width: 55%"><h3 style="float: left;">Materias</h3></td>
        <td style="width: 15%"><a href="materia.jsp" title="Agregar"><img width="30%" src='images/agregarLista.png' /></a> </td>
        <td style="width: 15%"><a onclick='abrir_dialog(dialog1)' title="Aplicar filtro"><img width="35%" src='images/filtro_1.png' /></a> </td>
        <td style="width: 15%"><img  width="30%" title="Imprimir" src="images/imprimir.png" onclick="listado(dialog2)"/></td>
    </tr>
    <tr>
        <td colspan="8">
            <p style="font-size: 70%" id="filtroTexto"></p>
        </td>   
    </tr>
</table>
<%--
<div id='dialog1' style="display:none" title="Filtro">
    <%@include file="filtroPersonal.jsp" %>
</div>
   --%>     
            <%
                ManejadorBedelia mb= ManejadorBedelia.getInstance();
                HashMap<Integer,Materia> materiasCurso=mb.getMaterias();
                String display="";
                out.print("<table style='width: 100%;' id='tablalistado'>"
                        + "<tr style='background-color:#ffcc66'>"
                            +"<td style='width: 5%' align='center'></td>"
                            +"<td style='width: 5%' align='center'><input type='checkbox' onclick='seleccionar_todo()' id='selTodo'></td>"
                            +"<td style='width: 5%' align='center'> ID </td>"
                            +"<td style='width: 15%' align='center'>C&oacute;digo</td>"
                            +"<td style='width: 15%' align='center'>Nombre</td>"
                            +"<td style='width: 10%' align='center'>Semestral</td>"
                            +"<td style='width: 10%' align='center'>Semestre</td>"
                            +"<td style='width: 10%' align='center'>Secundaria</td>"
                             +"<td style='width: 10%' align='center'>Espec&iacute;fica</td>"
                            +"<td style='width: 5%' align='center'>Coeficiente</td>"
                           +"<td style='width: 5%' align='center'>Ver</td>"
                        +"<td style='width: 5%' align='center'>Elim</td>");
                
                int i=0;
                String color;
                for (  Materia m : materiasCurso.values()){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;
                        
            out.print("<tr style='background-color:"+color+"'>"
                       +"<td style='width: 5%' align='center'>"+i+"</td>"
                       +"<td style='width: 5%"+display+"' align='center'><input type='checkbox' name='List[]' value='"+String.valueOf(m.getId())+"' form='formCadeteListar' /></td>"
                       +"<td style='width: 5%' align='center'>"+m.getId()+"</td>"
                       +"<td style='width: 15%' align='center'>"+ m.getCodigo() +"</td>"
                       +"<td style='width: 15%' align='center'>"+ m.getNombre() +"</td>");
            if(m.isSemestral()){
                      out.print("<td style='width: 5%' align='center'>SI</td>");
            }
            else{
                out.print("<td style='width: 5%' align='center'>NO</td>");
            }
                       out.print("<td style='width: 10%' align='center'>"+ m.getSemestre() +"</td>");
            if(m.isSecundaria()){
                      out.print("<td style='width: 5%' align='center'>SI</td>");
            }
            else{
                out.print("<td style='width: 5%' align='center'>NO</td>");
            }
            if(m.isEspecifica()){
                      out.print("<td style='width: 5%' align='center'>SI</td>");
            }
            else{
                out.print("<td style='width: 5%' align='center'>NO</td>");
            }
                       out.print("<td style='width: 10%' align='center'>"+m.getCoeficiente()+"</td>"
                       +"<td style='width: 5%' align='center'><a href='materia.jsp?id="+m.getId()+"'><img src='images/ver.png' width='60%' /></a></td>");
              out.print("<td style='width: 5%' align='center'><a href='Materia?elim="+m.getId()+"'><img src='images/eliminar.png' width='60%' /></a></td>"
                    +"</tr>"); 
                       }
                
            out.print("</table>");
            %> 
         </div>
     </div>    
<% 
    }
    else{
         response.sendRedirect("");
    }

%>
<%@ include file="footer.jsp" %> 

