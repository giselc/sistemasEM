<%-- 
    Document   : vincularMateriasCurso
    Created on : 17/05/2019, 13:19:14
    Author     : Gisel
--%>

<%@page import="Classes.Bedelia.Materia"%>
<%@page import="java.util.HashMap"%>
<%@page import="Manejadores.ManejadorBedelia"%>
<%@page import="Classes.Bedelia.CursoBedelia"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
%> 
 <p align="left"><a href="javascript:history.go(-1)"><img src="images/atras.png" width="15%"/></a></p>
    <ul id="tabs">
        <li><a href="#"><b>ASOCIACI&Oacute;N DE MATERIAS A CURSO</b></a></li>
    </ul>
    <div id="loader" style="z-index: 50;position: fixed; top:0; left:0; width:100%; height: 100%;background: url('images/loading-verde.gif') center center no-repeat; background-size: 10%"></div>
    <div id="content">
        <div>
            
            <table style="float: right">
                <tr>
                    <td style="width: 55%"><h3 style="float: left;">Asociar materias al curso <%= c.getNombre() %></h3></td>
                </tr>
            </table>
<%--
<div id='dialog1' style="display:none" title="Filtro">
    <%@include file="filtroPersonal.jsp" %>
</div>
   --%>     
            <form method="post" target="_blank"  name="formListaMateriasVincular"  action="VincularMateriasCurso?idCurso=<%= request.getParameter("idCurso") %>">

            <%
                HashMap<Integer,Materia> materiasCurso=mb.getMaterias();
                String display="";
                out.print("<table style='width: 100%;' id='tablalistado'>"
                        + "<tr style='background-color:#ffcc66'>"
                            +"<td style='width: 5%' align='center'></td>"
                            +"<td style='width: 5%' align='center'><input type='checkbox' onclick='seleccionar_todo()' id='selTodo'></td>"
                            +"<td style='width: 5%' align='center'> ID </td>"
                            +"<td style='width: 15%' align='center'>C&oacute;digo</td>"
                            +"<td style='width: 20%' align='center'>Nombre</td>"
                            +"<td style='width: 10%' align='center'>Semestral</td>"
                            +"<td style='width: 10%' align='center'>Semestre</td>"
                            +"<td style='width: 10%' align='center'>Secundaria</td>"
                            +"<td style='width: 5%' align='center'>Coeficiente</td>");
                
                int i=0;
                String color;
                
                for (  Materia m : materiasCurso.values()){
                    if(!c.getMaterias().containsKey(m.getId())){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;
                        
            out.print("<tr style='background-color:"+color+"'>"
                       +"<td style='width: 5%' align='center'>"+i+"</td>"
                       +"<td style='width: 5%"+display+"' align='center'><input type='checkbox' name='List[]' value='"+String.valueOf(m.getId())+"' /></td>"
                       +"<td style='width: 5%' align='center'>"+m.getId()+"</td>"
                       +"<td style='width: 15%' align='center'>"+ m.getCodigo() +"</td>"
                       +"<td style='width: 20%' align='center'>"+ m.getNombre() +"</td>"
                       +"<td style='width: 5%' align='center'>"+ m.isSemestral() +"</td>"
                       +"<td style='width: 10%' align='center'>"+ m.getSemestre() +"</td>"
                       +"<td style='width: 10%' align='center'>"+ m.isSecundaria() +"</td>"
                       +"<td style='width: 10%' align='center'>"+m.getCoeficiente()+"</td>"
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
         response.sendRedirect("");
    }

%>
<%@ include file="footer.jsp" %> 