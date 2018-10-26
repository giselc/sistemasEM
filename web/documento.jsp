<%-- 
    Document   : documento
    Created on : Mar 20, 2017, 10:16:43 PM
    Author     : Gisel
--%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Classes.Tipo"%>
<%@page import="Manejadores.ManejadorCodigos"%>
<%@page import="java.util.Base64"%>
<%@page import="java.sql.Blob"%>
<%@page import="Manejadores.ManejadorPersonal"%>
<%@page import="Classes.Documento"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<script type="text/javascript">
        function PreviewImage() {
            var oFReader = new FileReader();
            oFReader.readAsDataURL(document.getElementById("uploadImage").files[0]);

            oFReader.onload = function (oFREvent) {
                document.getElementById("uploadPreview").src = oFREvent.target.result;
            };
        };
        function enviandoSubmit(form){
            document.getElementById("enviando").style.display = "block";
            form.submit();
            return true;

        }

</script>
<% 
    if(u.isAdmin() || u.getPermisosPersonal().getId()==1){

%>
<% 
    Documento d=null;    
    if(request.getParameter("id")!=null){
        int id = Integer.valueOf(request.getParameter("id"));
        int ci = Integer.valueOf(request.getParameter("ci"));
        int idTipoPersonal = Integer.valueOf(request.getParameter("idTipoPersonal"));
        ManejadorPersonal mp = ManejadorPersonal.getInstance();
        d= mp.getDocumento(ci, idTipoPersonal, id);
    }
%>
<h1 align="center"><u><% if (d!=null){out.print("Editar Documento");}else{out.print("Agregar Documento");}%></u></h1>
<div id="enviando"  style="position: fixed; top:0; left:0; width:100%; height: 100%;background: url('images/loading-verde.gif') center center no-repeat; background-size: 20%; display: none"></div>
<%--<p align="left"><a href="personal.jsp?id=<%=request.getParameter("ci")%>"><img src="images/atras.png" width="15%"/></a></p>--%>
<form enctype="multipart/form-data" method="post" onsubmit="enviandoSubmit(this);" name="formulario" id="formulario"  action="Documento?id=<%if (d!=null){out.print(d.getId());}else{out.print("-1");} %>&ci=<%= request.getParameter("ci") %>&idTipoPersonal=<%= request.getParameter("idTipoPersonal") %>" >
    <table  width='70%' align='center' style="text-align: left">
        <tr>
            <td style="width: 50%">
                <img width="100%"id="uploadPreview" src="<%if(d!=null && !d.getNombre().equals("")){out.print("Documentos/"+request.getParameter("ci")+"-"+d.getId()+d.getNombre().substring(d.getNombre().indexOf(".")));}else{out.print("images/sinVistaPrevia.png");} %>"/>
            </td>
            <td>
                <table>
                    <tr>
                        <td>
                            <b>Seleccione el archivo a subir:</b>
                        </td>
                        <td style="width: 50%">
                            <input type="file" accept="application/msword, application/vnd.ms-excel, application/vnd.ms-powerpoint,application/pdf, image/*" name="documento" id="uploadImage" required="required" onchange="PreviewImage();"/> 
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>Tipo Documento:</b></p>
                        </td>
                        <td>
                            <select name="tipoDocumento" form="formulario">
                                <%
                                HashMap<Integer,Tipo> ag= mc.getTipoDocumentos();
                                for(Tipo g: ag.values() ){
                                    String s="";
                                    if(d!=null && d.getTipo().getId()==g.getId()){
                                        s="selected";
                                    }
                                    out.print("<option " + s +" value='"+String.valueOf(g.getId()) +"'>"+ g.getDescripcion() +"</option>");
                                }
                                %>
                            </select>
                        </td>
                    </tr>
                </table>
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