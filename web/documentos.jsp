<%-- 
    Document   : documentos
    Created on : Jul 25, 2018, 8:13:03 AM
    Author     : Gisel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    if(request.getParameter("id")!=null){
        int ci=Integer.valueOf(request.getParameter("id"));
        ManejadorPersonal mp = new ManejadorPersonal();
%>
<table style="float: right" >
        <tr>
            <td>
            <p id="mensaje" style="color: #990000"><% if(session.getAttribute("mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+session.getAttribute("mensaje"));}%></p>
            <%
                session.setAttribute("mensaje",null);
            %>
            </td>
        </tr>
        <tr>
            <td style="width: 55%"><h3 style="float: left; font-family: sans-serif">Documentos:</h3></td>
            <td style="width: 15%"><a href="documento.jsp?ci=<%=ci%>" title="Agregar"><img width="30%" src='images/agregarLista.png' /></a> </td>
        </tr>
    </table>
            
               
    <table style="width: 80%;" align='center'>
            <%
               
                ArrayList<Documento> a = mp.getDocumentosListar(ci);
                mp.CerrarConexionManejador();
                out.print("<tr style='background-color:#ffcc66'>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Tipo Documento</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Archivo</h3></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                       out.print("</tr>" );
                int i=0;
                String color;
                for (Documento s: a){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;

                    out.print("<tr style='background-color:"+color+"'>");
                    out.print("<td style='width: 10%' align='center'>"+s.getTipo().getDescripcion()+"</td>");
                    out.print("<td style='width: 10%' align='center'>");
                    if(!s.getNombre().equals("")){
                        out.print("<a href='Documentos/"+request.getParameter("id")+"-"+s.getId()+s.getNombre().substring(s.getNombre().indexOf("."))+"'>"+s.getNombre()+"</a>");
                    }
                    out.print("</td>");
                    out.print("<td style='width: 10%' align='center'><a href='documento.jsp?id="+String.valueOf(s.getId())+"&ci="+request.getParameter("id")+"'><img title='Editar' src='images/ver.png' width='25%' /></a></td>");
                    out.print("<td style='width: 10%' align='center'><form method='post' onsubmit=\"return confirmar(this,'')\" action='Documento?elim="+s.getId()+"&ci="+request.getParameter("id")+"'><input type='image' width='25%' title='Eliminar' src='images/eliminar.png' alt='Submit Form' /> </form></td>");
                    out.print("</tr>");
                }
            %> 
                
    </table>
    
<%
    }
%>