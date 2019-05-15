<%-- 
    Document   : grupos-curso
    Created on : Apr 29, 2019, 1:48:18 PM
    Author     : Gisel
--%>

<%@page import="java.util.LinkedList"%>
<%@page import="Classes.Bedelia.Grupo"%>
<%@page import="java.util.HashMap"%>
<%@page import="Classes.Bedelia.CursoBedelia"%>
<%@page import="Classes.Bedelia.Materia"%>
<%@page import="Manejadores.ManejadorBedelia"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<table style="float: right">
    <tr>
        <td style="width: 55%"><h3 style="float: left; font-family: sans-serif">Grupos</h3></td>
        <td style="width: 15%"><a href="grupo.jsp?idCurso=<%= d.getId() %>" title="Agregar"><img width="30%" src='images/agregarLista.png' /></a> </td>
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
                
                LinkedList<Grupo> listaGrupos = d.getGrupos();
                display="";
                out.print("<table style='width: 100%;' id='tablalistado'>"
                        + "<tr style='background-color:#ffcc66'>"
                            +"<td style='width: 5%' align='center'></td>"
                            +"<td style='width: 5%' align='center'><input type='checkbox' onclick='seleccionar_todo()' id='selTodo'></td>"
                            +"<td style='width: 15%' align='center'>A&ntilde;o</td>"
                            +"<td colspan=2 style='width: 20%' align='center'>Nombre</td>"
                           +"<td style='width: 5%' align='center'>Ver</td>"
                        +"<td style='width: 5%' align='center'>Elim</td>");
                
                i=0;
                for (  Grupo m : listaGrupos){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;
                        
            out.print("<tr style='background-color:"+color+"'>"
                       +"<td style='width: 5%' align='center'>"+i+"</td>"
                       +"<td style='width: 5%"+display+"' align='center'><input type='checkbox' name='List[]' value='"+d.getId()+"/"+String.valueOf(m.getAnio())+"/"+m.getNombre()+"' form='formCadeteListar' /></td>"
                       +"<td style='width: 5%' align='center'>"+m.getAnio()+"</td>"
                       +"<td style='width: 15%' align='center'>"+ m.getNombre() +"</td>"
                       +"<td style='width: 5%' align='center'><a href='grupo.jsp?idCurso="+d.getId()+"&anioGrupo="+m.getAnio()+"&nombreGrupo="+m.getNombre()+"'><img src='images/ver.png' width='60%' /></a></td>");
              out.print("<td style='width: 5%' align='center'><a href='Grupo?elim=1idCurso="+d.getId()+"&anioGrupo="+m.getAnio()+"&nombreGrupo="+m.getNombre()+"'><img src='images/eliminar.png' width='60%' /></a></td>"
                    +"</tr>"); 
                       }
                
            out.print("</table>");
            %> 
                
            
            
