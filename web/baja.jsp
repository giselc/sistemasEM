<%-- 
    Document   : baja
    Created on : Nov 12, 2018, 11:13:38 AM
    Author     : Gisel
--%>

<%@page import="Classes.Cadete"%>
<%@page import="Manejadores.ManejadorPersonal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<script>
    function confirmar(f){
        var s="¿Seguro que desea eliminar el cadete?");
        var r=confirm(s);
        if (r==true)
        {
            f.submit();
            return true;
        }
        else{
            return false;
        }
    }
</script>
<% 
    if(u.isAdmin() || (u.getPermisosPersonal()!=null && u.getPermisosPersonal().getId()==1)){
%>
<% 
        int ci = Integer.valueOf(request.getParameter("id"));
        ManejadorPersonal mp = ManejadorPersonal.getInstance();
        Cadete c =(Cadete) mp.getPersonal(ci, 1);
%>
<h1 align="center"><u>BAJA<% out.print(" "+c.getGrado().getAbreviacion()+" "+c.getPrimerNombre()+" "+c.getPrimerApellido()) ; %></u></h1>
<div id="enviando"  style="position: fixed; top:0; left:0; width:100%; height: 100%;background: url('images/loading-verde.gif') center center no-repeat; background-size: 20%; display: none"></div>
 <p align="left"><a href="javascript:history.go(-1)"><img src="images/atras.png" width="15%"/></a></p>
<form method="post" name="formulario" id="formulario" onsubmit="confirmar(this)" action="Cadete?baja=1&ci=<%= request.getParameter("id") %>">
    <table  width='70%' align='center' style="text-align: left">
        <tr>
            <td style="width: 50%">
                <b>Causa de baja</b>
            </td>
            <td>
                <textarea form="formulario" name="causa"></textarea>
            </td>            
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