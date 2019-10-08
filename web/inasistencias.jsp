<%-- 
    Document   : inasistencias
    Created on : 26/06/2019, 12:28:03
    Author     : Gisel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<% 
    if(u!=null &&(u.isAdmin() || u.isNotas())){
%>
 <p align="left"><a href="javascript:history.go(-1)"><img src="images/atras.png" width="15%"/></a></p>   
<h1 align="center"><u>Imprimir faltas</u></h1>
<div id="enviando"  style="position: fixed; top:0; left:0; width:100%; height: 100%;background: url('images/loading-verde.gif') center center no-repeat; background-size: 20%; display: none"></div>
<form method="post" name="formulario" id="formulario"  action="HistorialFaltas?imprimirFaltas=1" >
    <table  width='70%' align='center' style="text-align: left">
        <tr>
            <td>Seleccione el d&iacute;a: </td>
            <td><input type="date"  <%
                       
                        java.util.Calendar fecha1 = java.util.Calendar.getInstance();
                        int mes = fecha1.get(java.util.Calendar.MONTH)+1;
                        
                        String cero="";
                        if(mes<10){
                            cero="0";
                        }
                        out.print("value=\""+  fecha1.get(java.util.Calendar.YEAR)+"-"+cero+mes+"-"+fecha1.get(java.util.Calendar.DATE)+"\"");
                       
                        %> id="fecha" name="fecha" required="required">
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