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
<%@page import="Classes.ManejadorPersonal"%>
<div id='dialog1' style="display:none" title="Filtro">
    <%@include file="filtroCadetes.jsp" %>
</div>
    
<%
ManejadorPersonal mp = ManejadorPersonal.getInstance();
%>   
<form method="post" target="_blank" onsubmit="return listar(this)" name="formCadete" action='Listar'>
    
    <table style="float: right">
        <tr>
            <td style="width: 55%"><h3 style="float: left; font-family: sans-serif">Cadetes</h3></td>
            <td style="width: 15%"><a onclick='cadete.jsp?ci=0' title="Agregar"><img width="30%" src='images/agregarLista.png' /></a> </td>
            <td style="width: 15%"><a onclick='abrir_dialog(dialog1)' title="Aplicar filtro"><img width="35%" src='images/filtro_1.png' /></a> </td>
            <td style="width: 15%"><input type="image" width="30%" title="Imprimir"src="images/imprimir.png" alt="Submit Form" /></td>
            
        </tr>
        <tr>
            <td colspan="8">
                <p style="font-size: 70%" id="filtroTexto"></p>
            </td>   

        </tr>
    </table>
    
            <%
                LinkedList<Personal> hmp = mp.getCadetesListarApellido(false);
                out.print("<table style='width: 100%;' id='tablalistado'>"
                        + "<tr style='background-color:#ffcc66'>"
                            +"<td style='width: 5%' align='center'></td>"
                            +"<td style='width: 5%' align='center'><input type='checkbox' onclick='seleccionar_todo()' id='selTodo'></td>"
                            +"<td style='width: 5%' align='center'><img src='images/derecha.png' width='30%' onclick='ordenar(1,1)' /> Nro.</td>"
                            +"<td style='width: 10%' align='center'><img src='images/derecha.png' width='15%' onclick='ordenar(2,1)' />Grado</td>"
                            +"<td colspan=2 style='width: 20%' align='center'><img src='images/derecha.png' width='6%' onclick='ordenar(3,1)' />Nombres</td>"
                            +"<td colspan=2 style='width: 20%' align='center'><img src='images/derecha.png' width='6%' onclick='ordenar(4,1)' />Apellidos</td>"
                            +"<td style='width: 10%' align='center'>Cédula</td>"
                            +"<td style='width: 5%' align='center'>Curso</td>"
                            +"<td style='width: 10%' align='center'></td>"
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
                       +"<td style='width: 10%' align='center'><a href='cadete.jsp?ci="+c.getCi()+"'><img src='images/ver.png' width='25%' /></a></td>"
                       +"</tr>");
                }
                out.print("</table>");
            %> 
                
            
            
</form>
