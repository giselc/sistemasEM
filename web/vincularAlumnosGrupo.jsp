<%-- 
    Document   : vincularAlumnosGrupo
    Created on : 22/05/2019, 01:09:58
    Author     : Gisel
--%>

<%@page import="Classes.Personal"%>
<%@page import="Classes.Cadete"%>
<%@page import="java.util.LinkedList"%>
<%@page import="Classes.Bedelia.Grupo"%>
<%@page import="Classes.Bedelia.CursoBedelia"%>
<%@page import="Manejadores.ManejadorBedelia"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>   
<% 
    if(u.isAdmin() || u.isNotas()){
        ManejadorBedelia mb= ManejadorBedelia.getInstance();
        CursoBedelia c= mb.getCurso(Integer.valueOf(request.getParameter("idCurso")));
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
            function seleccionar_todo(){ 
                if (document.getElementById("selTodo").checked){
                    for (i=1;i<=document.formListaMateriasVincular.elements.length;i++) 
                   if(document.formListaMateriasVincular.elements[i].type == "checkbox")	
                      document.formListaMateriasVincular.elements[i].checked=1 ;
                }
                else{
                    for (i=1;i<=document.formListaMateriasVincular.elements.length;i++) 
                   if(document.formListaMateriasVincular.elements[i].type == "checkbox")	
                      document.formListaMateriasVincular.elements[i].checked=0;
                }
            } 
    </script>


    <p id="mensaje" style="color: #ffffff"><% if(sesion.getAttribute("Mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+sesion.getAttribute("Mensaje"));}%></p>
<%
    sesion.setAttribute("Mensaje",null);
    CursoBedelia cb= mb.getCurso(Integer.valueOf(request.getParameter("idCurso")));
%> 
<p align="left"><a href="javascript:history.go(-1)"><img src="images/atras.png" width="15%"/></a></p>
    <ul id="tabs">
        <li><a href="#"><b>ASOCIACI&Oacute;N DE ALUMNOS:</b></a></li>
    </ul>
    <div id="loader" style="z-index: 50;position: fixed; top:0; left:0; width:100%; height: 100%;background: url('images/loading-verde.gif') center center no-repeat; background-size: 10%"></div>
    <div id="content">
        <div>
            <table>
                <tr>
                    <td><h2 style="margin: 0px">CURSO <%= cb.getCodigo()+" - "+cb.getNombre() %></h2></td>
                </tr>
                <tr>
                    <td><h3 >Asociar alumnos al grupo <%= request.getParameter("nombreGrupo")+" - "+request.getParameter("anioGrupo") %></h3></td>
                </tr>
            </table>
<%--
<div id='dialog1' style="display:none" title="Filtro">
    <%@include file="filtroPersonal.jsp" %>
</div>
   --%>     
            <form method="post" name="formListaAlumnosGrupo"  action="VincularAlumnosGrupo?idCurso=<%= request.getParameter("idCurso") %>&nombreGrupo=<%= request.getParameter("nombreGrupo")  %>&anioGrupo=<%= request.getParameter("anioGrupo")  %>">

            <%
                Grupo g = c.getGrupo(Integer.valueOf(request.getParameter("anioGrupo")),request.getParameter("nombreGrupo"));
                String display="";
                out.print("<table style='width: 100%;' id='tablalistado'>"
                        + "<tr style='background-color:#ffcc66'>"
                            +"<td style='width: 5%' align='center'></td>"
                            +"<td style='width: 5%' align='center'><input type='checkbox' onclick='seleccionar_todo()' id='selTodo'></td>"
                            +"<td style='width: 5%' align='center'> Nro. </td>"
                            +"<td style='width: 10%' align='center'>CI</td>"
                            +"<td style='width: 10%' align='center'>Grado</td>"
                            +"<td style='width: 20%' colspan='2' align='center'>Nombres</td>"
                            +"<td style='width: 20%' colspan='2' align='center'>Apellidos</td>"
                            +"<td style='width: 10%' align='center'>Curso</td>"
                           );
                
                int i=0;
                String color;
                
                for (  Personal m : Manejadores.ManejadorPersonal.getInstance().getPersonalListarGrado(1, false)){
                    //quede aca
                    if(!g.getAlumnos().containsKey(m.getCi())){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;
                        
            out.print("<tr style='background-color:"+color+"'>"
                       +"<td style='width: 5%' align='center'>"+i+"</td>"
                       +"<td style='width: 5%"+display+"' align='center'><input type='checkbox' name='List[]' value='"+String.valueOf(m.getCi())+"' /></td>"
                       +"<td style='width: 5%' align='center'>"+m.getNroInterno()+"</td>"
                        +"<td style='width: 10%' align='center'>"+ m.getCi() +"</td>"
                       +"<td style='width: 10%' align='center'>"+ m.getGrado().getAbreviacion() +"</td>"
                       +"<td style='width: 10%' align='center'>"+ m.getPrimerNombre() +"</td>"
                       +"<td style='width: 10%' align='center'>"+ m.getSegundoNombre()+"</td>"
                       +"<td style='width: 10%' align='center'>"+ m.getPrimerApellido()+"</td>"
                       +"<td style='width: 10%' align='center'>"+ m.getSegundoApellido()+"</td>"
                       +"<td style='width: 10%' align='center'>"+ ((Cadete)m).getCurso().getAbreviacion() +"</td>"
                      
                    +"</tr>"); 
                       }
                }
            out.print("</table>");
            %> 
            <input type="submit" value="VINCULAR"/>
            </form>
         </div>
     </div>    
<% 
    }
    else{
        sesion.setAttribute("Mensaje","Lo sentimos, no tiene permisos para acceder a esta p&aacute;gina. Contacte al administrador.");
         response.sendRedirect("");
    }

%>
<%@ include file="footer.jsp" %> 
