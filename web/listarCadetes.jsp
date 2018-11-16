<%-- 
    Document   : listarCadetes
    Created on : Apr 6, 2018, 9:01:21 AM
    Author     : Gisel
--%>

<%@page import="java.util.LinkedList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Set"%>
<%@page import="Classes.Personal"%>
<%@page import="java.util.HashMap"%>
<%@page import="Classes.Cadete"%>
<%@page import="Manejadores.ManejadorPersonal"%>
<div id='dialog1' style="display:none" title="Filtro">
    <%@include file="filtroCadetes.jsp" %>
</div>
    
<%
ManejadorPersonal mp = ManejadorPersonal.getInstance();
%>   

    
            <%
                LinkedList<Personal> hmp = mp.getCadetesListarGrado(false);
                out.print("<table style='width: 100%;' id='tablalistado'>"
                        + "<tr style='background-color:#ffcc66'>"
                            +"<td style='width: 5%' align='center'></td>"
                            +"<td style='width: 5%' align='center'><input type='checkbox' onclick='seleccionar_todo()' id='selTodo'></td>"
                            +"<td style='width: 5%' align='center'><img src='images/derecha.png' width='30%' onclick='ordenar(1,1)' /> Nro.</td>"
                            +"<td style='width: 10%' align='center'><img src='images/abajo.png' width='15%' onclick='ordenar(2,1)' />Grado</td>"
                            +"<td colspan=2 style='width: 20%' align='center'><img src='images/derecha.png' width='6%' onclick='ordenar(3,1)' />Nombres</td>"
                            +"<td colspan=2 style='width: 20%' align='center'><img src='images/derecha.png' width='6%' onclick='ordenar(4,1)' />Apellidos</td>"
                            +"<td style='width: 10%' align='center'>Cédula</td>"
                            +"<td style='width: 15%' align='center'>Curso</td>"
                            +"<td style='width: 5%' align='center'>Ver</td>"
                            +"<td style='width: 5%' align='center'>Elim</td>"   
                +"</tr>" );
                int i=0;
                String color;
                for (  Personal p : hmp){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;
                        Cadete c= (Cadete) p;
                       out.print("<tr style='background-color:"+color+"'>"
                       +"<td style='width: 5%' align='center'>"+i+"</td>"
                       +"<td style='width: 5%' align='center'><input type='checkbox' name='List[]' value='"+String.valueOf(c.getCi())+"' /></td>"
                       +"<td style='width: 5%' align='center'>"+c.getNroInterno()+"</td>"
                       +"<td style='width: 10%' align='center'>"+c.getGrado().getAbreviacion()+"</td>"
                       +"<td style='width: 10%' align='center'>"+c.getPrimerNombre()+"</td>"
                       +"<td style='width: 10%' align='center'>"+c.getSegundoNombre()+"</td>"
                       +"<td style='width: 10%' align='center'>"+c.getPrimerApellido()+"</td>"
                       +"<td style='width: 10%' align='center'>"+c.getSegundoApellido()+"</td>"
                       +"<td style='width: 10%' align='center'>"+c.getCi()+"</td>"
                       +"<td style='width: 15%' align='center'>"+c.getCurso().getAbreviacion()+"</td>"
                       +"<td style='width: 5%' align='center'><a href='cadete.jsp?id="+c.getCi()+"'><img src='images/ver.png' width='60%' /></a></td>"
                               +"<td style='width: 5%' align='center'><a href='baja.jsp?id="+c.getCi()+"'><img src='images/eliminar.png' width='60%' /></a></td>"
                       +"</tr>");
                }
                out.print("</table>");
            %> 
                
            
            
