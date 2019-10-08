<%-- 
    Document   : curso
    Created on : Mar 20, 2019, 9:17:32 AM
    Author     : Gisel
--%>

<%@page import="Classes.Bedelia.CursoBedelia"%>
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
    if(u!=null &&(u.isAdmin() || u.isNotas())){
%>
<% 
    CursoBedelia d=null;    
    if(request.getParameter("id")!=null){
        int id = Integer.valueOf(request.getParameter("id"));
        ManejadorBedelia mp = ManejadorBedelia.getInstance();
        d= mp.getCurso(id);
    }
%>
<p id="mensaje" style="color: #ffffff"><% if(sesion.getAttribute("Mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+sesion.getAttribute("Mensaje"));}%></p>
<%
    sesion.setAttribute("Mensaje",null);
%>
 <p align="left"><a href="javascript:history.go(-1)"><img src="images/atras.png" width="15%"/></a></p>   
<h1 align="center"><u><% if (d!=null){out.print("Editar Curso");}else{out.print("Agregar Curso");}%></u></h1>
<div id="enviando"  style="position: fixed; top:0; left:0; width:100%; height: 100%;background: url('images/loading-verde.gif') center center no-repeat; background-size: 20%; display: none"></div>
<form method="post" name="formulario" id="formulario"  action="Curso?id=<%if (d!=null){out.print(d.getId());}else{out.print("-1");} %>" >
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
            <td>A&ntilde;o Curricular: </td>
            <td><input type=number name="anioCurricular" required="required"  <% if(d!=null){out.print("value='"+ d.getAnioCurricular()+"'");} %> /> </td>
        </tr>
        <tr>
            <td>Jefatura: </td>
            <td><select name="jefatura" form="formulario" required="required">
                        <%
                        //HashMap<Integer,Arma> aa = mc.getArmas();
                        String s="";
                      // System.out.print(c.getArma());
                        if(d!=null && d.isJefatura()){
                            s="selected";
                        }
                        out.print("<option "+s+" value='JE'>JE</option>");
                        s="";
                        if(d!=null && !d.isJefatura()){
                            s="selected";
                        }
                        out.print("<option "+s+" value='JCC'>JCC</option>");

                        %>
                     </select> </td>
        </tr>
        <tr>
            <td>Activo: </td>
            <td><input type=checkbox name="activo" checked="" <% if(d!=null && d.isActivo()||d==null){out.print("checked=\"checked\"");} %> /> </td>
        </tr>
    </table>
    <p align='right'><input type="submit"  value="Aceptar" /></p>
</form>       
    <%
    if(request.getParameter("id")!=null){
    %>
        <ul id="tabs">
            <li><a href="#" title="Materias">Materias</a></li>
            <li><a href="#" title="Grupos">Grupos</a></li>
        </ul>
        <div id="content">
            <div id="Materias"><%@include file="materias-curso.jsp" %></div>
            <div id="Grupos"><%@include file="grupos-curso.jsp" %></div>

        </div>
    <%
    }
    }
    else{
         response.sendRedirect("");
    }

%>

<%@ include file="footer.jsp" %>