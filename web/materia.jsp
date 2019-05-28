<%-- 
    Document   : materia
    Created on : May 8, 2019, 9:54:36 AM
    Author     : Gisel
--%>

<%@page import="java.util.HashMap"%>
<%@page import="Classes.Bedelia.CursoBedelia"%>
<%@page import="Classes.Bedelia.Materia"%>
<%@page import="Manejadores.ManejadorBedelia"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/jquery-ui.js"></script>
<script>
    $(document).ready(function() {
                $("#content div").hide();
                $("#tabs li:first").attr("id","current");
                $("#content div:first").fadeIn();
                $("#loader").fadeOut();
            $('#tabs a').click(function(e) {
               // document.getElementById("mensaje").innerHTML="";
                e.preventDefault();
                $("#content div").hide();
                $("#tabs li").attr("id","");
                $(this).parent().attr("id","current");
                $('#' + $(this).attr('title')).fadeIn();
            });
        });
        function nombreMateria(){
            alert(document.getElementById("nombre").value);
            return true;
        }
</script>
<% 
    if(u.isAdmin() || u.getPermisosPersonal().getId()==4){
%>
<% 
    Materia d=null;    
    ManejadorBedelia mp = ManejadorBedelia.getInstance();

    if(request.getParameter("id")!=null){
        int id = Integer.valueOf(request.getParameter("id"));
        d= mp.getMaterias().get(id);
    }
%>
<p id="mensaje" style="color: #ffffff"><% if(sesion.getAttribute("Mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+sesion.getAttribute("Mensaje"));}%></p>
<%
    sesion.setAttribute("Mensaje",null);
%>
<p align="left"><a href="javascript:history.go(-1)"><img src="images/atras.png" width="15%"/></a></p>
<h1 align="center" style="font-family: arial"><u><% if (d!=null){out.print("Editar Materia");}else{out.print("Agregar Materia");}%></u></h1>
<div id="enviando"  style="position: fixed; top:0; left:0; width:100%; height: 100%;background: url('images/loading-verde.gif') center center no-repeat; background-size: 20%; display: none"></div>
<form method="post"  name="formulario" id="formulario"  action="Materia?id=<%if (d!=null){out.print(d.getId());}else{out.print("-1");}; if(request.getParameter("idCurso")!=null){out.print("&idCurso="+request.getParameter("idCurso"));} %>"  onsubmit="return nombreMateria();">
    <table  width='70%' align='center' style="text-align: left">
        <%if(d!=null){%>
        <tr>
            <td>ID: </td>
            <td><input type=number name="id" readonly="readonly"  <% out.print("value='"+ d.getId() +"'"); %> /> </td>
        </tr>
        <%}%>
        <tr>
            <td>C&oacute;digo: </td>
            <td><input type=text name="codigo" required="required"  <% if(d!=null){out.print("value='"+ d.getCodigo()+"' readonly='readonly'");} %> /> </td>
        </tr>
        <tr>
            <td>Nombre: </td>
            <td><input type=text name="nombre" id="nombre" required="required"  <% if(d!=null){out.print("value='"+ d.getNombre()+"'");} %> /> </td>
        </tr>
        <tr>
            <td>Semestral: </td>
            <td><input type=checkbox name="semestral" <% if(d!=null && d.isSemestral()||d==null){out.print("checked=\"checked\"");} %> /> </td>
        </tr>
        <tr>
            <td>Semestre: </td>
            <td><input type=number name="semestre" <% if(d!=null && d.isSemestral()){out.print("value='"+ d.getSemestre()+"'");}else{out.print("value='0'");}; %> min="0" max="2" step="1" required="required"/> </td>
        </tr>
        <tr>
            <td>Secundaria: </td>
            <td><input type=checkbox name="secundaria" <% if(d!=null && d.isSecundaria()||d==null){out.print("checked=\"checked\"");}%> /> </td>
        </tr>
        <tr>
            <td>Coeficiente: </td>
            <td><input type=number name="coeficiente"  <% if(d!=null){out.print("value='"+ d.getCoeficiente()+"'");} %> required="required"/> </td>
        </tr>
        <tr>
            <td>Activo: </td>
            <td><input type=checkbox name="activo" <% if(d!=null && d.isActivo()||d==null){out.print("checked=\"checked\"");} %> /> </td>
        </tr>
    </table>
    <p align='right'><input type="submit"  value="Aceptar" /></p>
</form>       
        <h3 align="center" style="font-family: arial">CURSOS ASOCIADOS</h3>
    <%
            if(d!=null){
               String display="";
               out.print("<table style='width: 100%;' id='tablalistado'>"
                        + "<tr style='background-color:#ffcc66'>"
                            +"<td style='width: 5%' align='center'></td>"
                            +"<td style='width: 10%' align='center'>id</td>"
                            +"<td style='width: 10%' align='center'>Codigo</td>"
                            +"<td style='width: 10%' align='center'>Nombre</td>"
                            +"<td style='width: 10%' align='center'>Anio Curricular</td>"
                            +"<td style='width: 10%' align='center'>Jefatura</td>");
                out.print(  "<td style='width: 5%' align='center'>Ver</td></tr>" );
                int i=0;
                String color;
                for (  CursoBedelia p : mp.getCursosMateria(d.getId()).values()){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;
                        
                       out.print("<tr style='background-color:"+color+"'>"
                       +"<td style='width: 5%' align='center'>"+i+"</td>"
                       +"<td style='width: 10%' align='center'>"+p.getId()+"</td>"
                       +"<td style='width: 10%' align='center'>"+p.getCodigo()+"</td>"
                       +"<td style='width: 10%' align='center'>"+p.getNombre()+"</td>"
                       +"<td style='width: 10%' align='center'>"+p.getAnioCurricular()+"</td>"
                       +"<td style='width: 10%' align='center'>");
                       if(p.isJefatura()){
                            out.print("JE");
                       }else{
                            out.print("JCC");
                       }
                       out.print("</td>");
                        out.print("<td style='width: 5%' align='center'><a target='_blank' href='curso.jsp?id="+p.getId()+"'><img src='images/ver.png' width='60%' /></a></td></tr>");
                }
                out.print("</table>");
            }
    }
    
    else{
         response.sendRedirect("");
    }

%>

<%@ include file="footer.jsp" %>