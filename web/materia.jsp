<%-- 
    Document   : materia
    Created on : May 8, 2019, 9:54:36 AM
    Author     : Gisel
--%>

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
</script>
<% 
    if(u.isAdmin() || u.getPermisosPersonal().getId()==4){
%>
<% 
    Materia d=null;    
    if(request.getParameter("id")!=null){
        int id = Integer.valueOf(request.getParameter("id"));
        ManejadorBedelia mp = ManejadorBedelia.getInstance();
        d= mp.getMaterias().get(id);
    }
%>
<h1 align="center"><u><% if (d!=null){out.print("Editar Materia");}else{out.print("Agregar Materia");}%></u></h1>
<div id="enviando"  style="position: fixed; top:0; left:0; width:100%; height: 100%;background: url('images/loading-verde.gif') center center no-repeat; background-size: 20%; display: none"></div>
<%--<p align="left"><a href="personal.jsp?id=<%=request.getParameter("ci")%>"><img src="images/atras.png" width="15%"/></a></p>--%>
<form method="post" name="formulario" id="formulario"  action="Materia?id=<%if (d!=null){out.print(d.getId());}else{out.print("-1");}; if(request.getParameter("idCurso")!=null){out.print("&idCurso="+request.getParameter("idCurso"));} %>" >
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
            <td><input type=text name="nombre" required="required"  <% if(d!=null){out.print("value='"+ d.getNombre()+"'");} %> /> </td>
        </tr>
        <tr>
            <td>Semestral: </td>
            <td><input type=checkbox name="semestral" checked="" <% if(d!=null && d.isSemestral()||d==null){out.print("checked=\"checked\"");} %> /> </td>
        </tr>
        <tr>
            <td>Semestral: </td>
            <td><input type=number name="semestre" <% if(d!=null && d.isSemestral()){out.print("value='"+ d.getSemestre()+"'");} %> min="0" max="2" step="1"/> </td>
        </tr>
        <tr>
            <td>Secundaria: </td>
            <td><input type=checkbox name="secundaria" checked="" <% if(d!=null && d.isSecundaria()||d==null){out.print("checked=\"checked\"");} %> /> </td>
        </tr>
        <tr>
            <td>Coeficiente: </td>
            <td><input type=number name="coeficiente"  <% if(d!=null){out.print("value='"+ d.getCoeficiente()+"'");} %> /> </td>
        </tr>
        <tr>
            <td>Activo: </td>
            <td><input type=checkbox name="activo" checked="" <% if(d!=null && d.isActivo()||d==null){out.print("checked=\"checked\"");} %> /> </td>
        </tr>
    </table>
    <p align='right'><input type="submit"  value="Aceptar" /></p>
</form>       
    <%
    }
    
    else{
         response.sendRedirect("");
    }

%>

<%@ include file="footer.jsp" %>