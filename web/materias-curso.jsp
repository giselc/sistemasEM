<%-- 
    Document   : materias-curso
    Created on : Apr 29, 2019, 1:48:03 PM
    Author     : Gisel
--%>

<%@page import="java.util.HashMap"%>
<%@page import="Classes.Bedelia.CursoBedelia"%>
<%@page import="Classes.Bedelia.Materia"%>
<%@page import="Manejadores.ManejadorBedelia"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<table style="float: right">
    <tr>
        <td style="width: 40%"><h3 style="float: left; font-family: sans-serif">Materias</h3></td>
        <td style="width: 15%"><a href="vincularMateriasCurso.jsp?idCurso=<%= d.getId() %>" title="Vincular"><img width="30%" src='images/vincular.png' /></a> </td>
        <td style="width: 15%"><a href="materia.jsp?idCurso=<%= d.getId() %>" title="Agregar"><img width="30%" src='images/agregarLista.png' /></a> </td>
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
                HashMap<Integer,Materia> materiasCurso=d.getMaterias();
                String display="";
                out.print("<table style='width: 100%;' id='tablalistado'>"
                        + "<tr style='background-color:#ffcc66'>"
                            +"<td style='width: 5%' align='center'></td>"
                            +"<td style='width: 5%' align='center'><input type='checkbox' onclick='seleccionar_todo()' id='selTodo'></td>"
                            +"<td style='width: 5%' align='center'> ID </td>"
                            +"<td style='width: 15%' align='center'>C&oacute;digo</td>"
                            +"<td colspan=2 style='width: 20%' align='center'>Nombre</td>"
                            +"<td colspan=2 style='width: 10%' align='center'>Semestral</td>"
                            +"<td style='width: 10%' align='center'>Semestre</td>"
                            +"<td style='width: 10%' align='center'>Secundaria</td>"
                            +"<td style='width: 5%' align='center'>Coeficiente</td>"
                           +"<td style='width: 5%' align='center'>Ver</td>"
                        +"<td style='width: 5%' align='center'>Desvincular</td>");
                
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
                       +"<td style='width: 20%' align='center'>"+ m.getNombre() +"</td>"
                       +"<td style='width: 5%' align='center'>"+ m.isSemestral() +"</td>"
                       +"<td style='width: 10%' align='center'>"+ m.getSemestre() +"</td>"
                       +"<td style='width: 10%' align='center'>"+ m.isSecundaria() +"</td>"
                       +"<td style='width: 10%' align='center'>"+m.getCoeficiente()+"</td>"
                       +"<td style='width: 5%' align='center'><a href='materia.jsp?id="+m.getId()+"'><img src='images/ver.png' width='60%' /></a></td>");
              out.print("<td style='width: 5%' align='center'><a href='CursoBedelia?desvincular="+m.getId()+"&idCurso="+d.getId()+"'><img src='images/desvincular.png' width='60%' /></a></td>"
                    +"</tr>"); 
                       }
                
            out.print("</table>");
            %> 
                
            
            
