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
<% 
    if(u.isAdmin() || (u.getPermisosPersonal()!=null && u.getPermisosPersonal().getId()==1)){
%>

<script type="text/javascript">
        function PreviewImage() {
            var fileInput = document.getElementById('uploadImage');
            var imagePreview = document.getElementById("uploadPreview");
            var filePath = fileInput.value;
            var allowedExtensions = /(.jpg|.jpeg|.png|.gif)$/i;
            if(!allowedExtensions.exec(filePath)){
                 imagePreview.src ="images/sinVistaPrevia.png";
            }else{
                //Image preview
                if (fileInput.files && fileInput.files[0]) {
                    var oFReader = new FileReader();
                    oFReader.readAsDataURL(fileInput.files[0]);
                    oFReader.onload = function (oFREvent) {
                        imagePreview.src = oFREvent.target.result;
                    };
                }
            }            
        };
        function enviandoSubmit(form){
            document.getElementById("enviando").style.display = "block";
            form.submit();
            return true;

        }

</script>
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
 <p align="left"><a href="javascript:history.go(-1)"><img src="images/atras.png" width="15%"/></a></p>
<h1 align="center"><u><% if (d!=null){out.print("Editar Documento");}else{out.print("Agregar Documento");}%></u></h1>
<div id="enviando"  style="position: fixed; top:0; left:0; width:100%; height: 100%;background: url('images/loading-verde.gif') center center no-repeat; background-size: 20%; display: none"></div>

<form enctype="multipart/form-data" method="post" onsubmit="enviandoSubmit(this);" name="formulario" id="formulario"  action="Documento?id=<%if (d!=null){out.print(d.getId());}else{out.print("-1");} %>&ci=<%= request.getParameter("ci") %>&idTipoPersonal=<%= request.getParameter("idTipoPersonal") %>" >
    <table  width='70%' align='center' style="text-align: left">
        <tr>
            <td style="width: 50%">
                <img width="100%"id="uploadPreview" src="images/sinVistaPrevia.png"/>
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
                    <tr>
                        <td>
                            <b>Descripci&oacute;n del documento:</b>
                        </td>
                        <td style="width: 50%">
                            <textarea form="formulario" name="descripcion"></textarea>
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
        sesion.setAttribute("Mensaje","Lo sentimos, no tiene permisos para acceder a esta p&aacute;gina. Contacte al administrador.");
         response.sendRedirect("");
    }

%>

<%@ include file="footer.jsp" %>