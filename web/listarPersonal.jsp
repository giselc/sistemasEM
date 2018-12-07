<%-- 
    Document   : listarPersonal
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
    <%@include file="filtroPersonal.jsp" %>
</div>
    
<%
ManejadorPersonal mp = ManejadorPersonal.getInstance();
%>   

    
            <%
                LinkedList<Personal> hmp = mp.getPersonalListarGrado(tipo,false);
                String display="";
                if(tipo!=1){
                    display=";display:none";
                }
                out.print("<table style='width: 100%;' id='tablalistado'>"
                        + "<tr style='background-color:#ffcc66'>"
                            +"<td style='width: 5%' align='center'></td>"
                            +"<td style='width: 5%"+display+"' align='center'><input type='checkbox' onclick='seleccionar_todo()' id='selTodo'></td>"
                            +"<td style='width: 5%' align='center'><img src='images/derecha.png' width='30%' onclick='ordenar(1,1)' /> Nro.</td>"
                            +"<td style='width: 10%' align='center'><img src='images/abajo.png' width='15%' onclick='ordenar(2,1)' />Grado</td>"
                            +"<td colspan=2 style='width: 20%' align='center'><img src='images/derecha.png' width='6%' onclick='ordenar(3,1)' />Nombres</td>"
                            +"<td colspan=2 style='width: 20%' align='center'><img src='images/derecha.png' width='6%' onclick='ordenar(4,1)' />Apellidos</td>"
                            +"<td style='width: 10%' align='center'>Cédula</td>");
                if(tipo==1){
                out.print(  "<td style='width: 15%' align='center'>Curso</td>"
                            +"<td style='width: 5%' align='center'>Ver</td>");
                
                }
                else{
                 out.print(  "<td style='width: 15%' align='center'>Arma</td>"); 
                }
                out.print(  "<td style='width: 5%' align='center'>Elim</td>"   
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
                        
                       out.print("<tr style='background-color:"+color+"'>"
                       +"<td style='width: 5%' align='center'>"+i+"</td>"
                       +"<td style='width: 5%"+display+"' align='center'><input type='checkbox' name='List[]' value='"+String.valueOf(p.getCi())+"' form='formCadeteListar' /></td>"
                       +"<td style='width: 5%' align='center'>"+p.getNroInterno()+"</td>"
                       +"<td style='width: 10%' align='center'>");
                       if(p.getGrado()!=null){
                           out.print(p.getGrado().getAbreviacion());
                       }
                       out.print("</td>"
                       +"<td style='width: 10%' align='center'>"+p.getPrimerNombre()+"</td>"
                       +"<td style='width: 10%' align='center'>"+p.getSegundoNombre()+"</td>"
                       +"<td style='width: 10%' align='center'>"+p.getPrimerApellido()+"</td>"
                       +"<td style='width: 10%' align='center'>"+p.getSegundoApellido()+"</td>"
                       +"<td style='width: 10%' align='center'>"+p.getCi()+"</td>");
                       if(tipo==1){
                        Cadete c= (Cadete) p;
                        out.print("<td style='width: 15%' align='center'>"+c.getCurso().getAbreviacion()+"</td>"
                        +"<td style='width: 5%' align='center'><a href='cadete.jsp?id="+c.getCi()+"'><img src='images/ver.png' width='60%' /></a></td>");
                       out.print("<td style='width: 5%' align='center'><a href='baja.jsp?id="+p.getCi()+"'><img src='images/eliminar.png' width='60%' /></a></td>"
                        +"</tr>"); 
                       }
                        else{
                        out.print("<td style='width: 15%' align='center'>");
                        if(p.getArma()!=null){
                            out.print(p.getArma().getDescripcion());
                        }
                       out.print("</td>");
                       out.print("<td style='width: 5%' align='center'><a href='Personal?elim=0&tipo="+tipo+"&ci="+p.getCi()+"'><img src='images/eliminar.png' width='60%' /></a></td>"
                        +"</tr>");
                        }
                        
                       
                }
                out.print("</table>");
            %> 
                
            
            
