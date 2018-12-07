<%-- 
    Document   : documentos
    Created on : Jul 25, 2018, 8:13:03 AM
    Author     : Gisel
--%>

<%@page import="Manejadores.ManejadorPersonal"%>
<%@page import="java.util.HashMap"%>
<%@page import="Classes.Documento"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    if(request.getParameter("id")!=null){
        int ci=Integer.valueOf(request.getParameter("id"));
%>
<table style="float: right" >
        <tr>
            <td>
            <p id="mensaje" style="color: #990000"><% if(session.getAttribute("Mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+session.getAttribute("Mensaje"));}%></p>
            <%
                session.setAttribute("mensaje",null);
            %>
            </td>
        </tr>
        <tr>
            <td style="width: 55%"><h3 style="float: left; font-family: sans-serif">Documentos:</h3></td>
            <td style="width: 15%"><a href="documento.jsp?ci=<%=ci%>&idTipoPersonal=<%=idTipoPersonal%>" title="Agregar"><img width="30%" src='images/agregarLista.png' /></a> </td>
        </tr>
    </table>
            
               
    <table style="width: 80%;" align='center'>
            <%
               
                HashMap<Integer,Documento> a = mp.getPersonal(ci, idTipoPersonal).getDocumentos();
                out.print("<tr style='background-color:#ffcc66'>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Tipo Documento</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Archivo</h3></td>");
                            out.print("<td style='width: 30%' align='center'><h3 style='margin:2%;'>Descripcion</h3></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                       out.print("</tr>" );
                i=0;
                String color;
                for (Documento s1: a.values()){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;

                    out.print("<tr style='background-color:"+color+"'>");
                    out.print("<td style='width: 10%' align='center'>"+s1.getTipo().getDescripcion()+"</td>");
                    out.print("<td style='width: 10%' align='center'>");
                    if(!s1.getNombre().equals("")){
                        out.print("<a target='_blank' href='"+request.getContextPath()+"/Imagenes?ci="+ci+"&ext="+s1.getExtension()+"&idDoc="+s1.getId()+"'>"+s1.getNombre()+"</a>");
                    }
                    out.print("</td>");
                    out.print("<td style='width: 30%' align='center'><h3 style='margin:2%;'>"+s1.getDescripcion()+"</h3></td>");
                    out.print("<td style='width: 10%' align='center'><form method='post' onsubmit=\"return confirmar(this,'')\" action='Documento?elim="+s1.getId()+"&ci="+ci+"&idTipoPersonal="+idTipoPersonal+"'><input type='image' width='25%' title='Eliminar' src='images/eliminar.png' alt='Submit Form' /> </form></td>");
                    out.print("</tr>");
                }
            %> 
                
    </table>
    
<%
    }
%>