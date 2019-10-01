<%-- 
    Document   : usuario
    Created on : Mar 3, 2017, 1:31:40 AM
    Author     : Gisel
--%>

<%@page import="Manejadores.ManejadorProfesores"%>
<%@page import="Classes.Bedelia.Profesor"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<script>
    function validarContrasena(f){
        if (f.elements["pass"].value != f.elements["pass1"].value){
            document.getElementById("textocontrasena").innerHTML = "No coinciden las contraseñas.";
            return false;
        }
        else{
            f.submit();
        }
    };
    function validarNoAdmin(f,u1Null){
        if(u1Null){
            if (f.value.toLowerCase()=="admin"){
                alert("El usuario no puede ser 'admin'.");
                f.value="";
            }
        }
    };
    function mostrarCiProfesor(esProfesor){
        if(esProfesor.checked){
            document.getElementById("ciProfesor").disabled = false;
            document.getElementById("ciProfesor").required = true;
        }
        else{
            document.getElementById("ciProfesor").disabled = true;
            document.getElementById("ciProfesor").required = false;
        }
    }
    
    function mostrarTipoPermisoDescuento(permisoDescuento){
        if(permisoDescuento.checked){
            document.getElementById("tipoPermisoDescuento").disabled = false;
        }
        else{
            document.getElementById("tipoPermisoDescuento").disabled = true;
        }
    }
    function mostrarTipoPermisoPersonal(permisoPersonal){
        if(permisoPersonal.checked){
            document.getElementById("tipoPermisoPersonal").disabled = false;
        }
        else{
            document.getElementById("tipoPermisoPersonal").disabled = true;
        }
    }
</script>
<style>
    .contenido{
        background-color: #888888;
    }
    .title{
        font-size: large;
        background-color: #555555;
    }
</style>
<% 
    if(u.isAdmin() || (u.getPermisosPersonal()!=null && u.getPermisosPersonal().getId()==4) ){
        Manejadores.ManejadorCodigoBD mcbd= new Manejadores.ManejadorCodigoBD();
        Profesor p=null;
        Usuario u1= null;
        if(request.getParameter("id")!=null){
            int id = Integer.valueOf(request.getParameter("id"));
            u1= mcbd.getUsuario(u, id);
        }
        else{
            if(request.getParameter("ciProfesor")!=null){
                int ciProfesor=Integer.valueOf(request.getParameter("ciProfesor"));
                ManejadorProfesores mp = ManejadorProfesores.getInstance();
                p= mp.getProfesor(ciProfesor);
            }
        }
%>
<p align="left"><a href="javascript:history.go(-1)"><img src="images/atras.png" width="15%"/></a></p>
<h1 align="center"><u><% if (u1!=null){out.print("Usuario: "+u1.getNombre());}else{out.print("Alta de usuario");}%></u></h1>
    <table  width='70%' style="font-size: 130%" >
        <tr>
            <td valign='top' width='40%'>
                <img src="images/usuario.png" />
            </td>
            <td width='10%'>
                
            </td>
            <td width='50%'>
                <form id="formUsuario" method="post" action="Usuario?id=<%if (u1!=null){out.print(u1.getId());}else{out.print("-1");} %>" onsubmit='return validarContrasena(this);'>
                <table style=" width: 100%; border-collapse: separate;border-spacing: 2px;text-align: center;vertical-align: central;">
                    <tr>
                        <td colspan="2" style="color: #cd0a0a">
                            <% if(session.getAttribute("mensaje")!=null){
                                out.print("<img src='images/icono-informacion.png' width='8%'/>"+session.getAttribute("mensaje"));
                                session.setAttribute("mensaje", null);
                            };%>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">
                            <b>Usuario:</b>
                        </td>
                        <td class="contenido" colspan="2">
                            <%
                            String nombreUsuarioProfesor= "";
                            if(p!=null){
                                nombreUsuarioProfesor= p.getPrimerNombre().substring(0, 1).concat(p.getPrimerApellido()).toLowerCase();
                                if(mcbd.existeUsuario(nombreUsuarioProfesor)){
                                    nombreUsuarioProfesor= p.getPrimerNombre().substring(0, 2).concat(p.getPrimerApellido()).toLowerCase();
                                    if(mcbd.existeUsuario(nombreUsuarioProfesor)){
                                        nombreUsuarioProfesor=""; 
                                    };
                                };
                            };
                            %>
                            <p align="center"><input type="text" onblur="validarNoAdmin(this,<%= u1==null %>);" value="<% if (u1!=null){out.print(u1.getNombre());}else{if(p!=null){out.print(nombreUsuarioProfesor);}%>" <% if (u1!=null){out.print("readonly='readonly'");}else{out.print("required='required'");}}%> name="usuario" /></p>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">
                            <b>Nombre para mostrar:</b>
                        </td>
                        <td class="contenido" colspan="2">
                            <p><input type="text" value="<% if (u1!=null){out.print(u1.getNombreMostrar());}else{if(p!=null){out.print(p.obtenerNombreCompleto());}}%>" name="nombreMostrar" required="required"/></p>
                        </td>
                    </tr>
                    <%
                    if(u.isAdmin()){
                    %>
                    
                    <tr>
                        <td class="title">
                            <b>Administrador:</b>
                        </td>
                        <td class="contenido" colspan="2">
                            <% if(u1!=null && u1.getNombre().equals("admin")){
                                out.print("No puede modificar este campo");
                            }
                            else{%>
                            <input  id="admin" type=checkbox name="admin" <% if(u1!=null && u1.isAdmin()){out.print("checked='checked'");} %>/>
                            <%
                            }
                            %>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">
                            <b>Permisos Sistema de Personal:</b>
                        </td>
                        <td class="contenido">
                            <input  id="permisoPersonal" type=checkbox name="permisoPersonal" <% if(u1!=null && u1.getPermisosPersonal()!=null){out.print("checked='checked'");} %> onchange="mostrarTipoPermisoPersonal(this);"/>
                        </td>
                        <td class="contenido">
                            <select form="formUsuario" id="tipoPermisoPersonal" name="tipoPermisoPersonal" <% if(u1==null || u1.getPermisosPersonal()==null){out.print("disabled");} %> >
                                <option value="-1" disabled="disabled" selected hidden>Seleccionar tipo de permiso:</option>
                                <option value="1" <% if(u1!=null&& u1.getPermisosPersonal()!=null && u1.getPermisosPersonal().getId()==1){out.print("selected");}%>>CADETES</option>
                                <option value="2" <% if(u1!=null&& u1.getPermisosPersonal()!=null && u1.getPermisosPersonal().getId()==2){out.print("selected");}%>>PS</option>
                                <option value="3" <% if(u1!=null&& u1.getPermisosPersonal()!=null && u1.getPermisosPersonal().getId()==3){out.print("selected");}%>>OFICIALES</option>
                                <option value="4" <% if(u1!=null&& u1.getPermisosPersonal()!=null && u1.getPermisosPersonal().getId()==4){out.print("selected");}%>>PROFESORES</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">
                            <b>Permisos Sistema de Descuentos:</b>
                        </td>
                        <td class="contenido">
                            <input  id="permisoDescuentos" type=checkbox name="permisoDescuentos" <% if(u1!=null && u1.getPermisosDescuento()!=null){out.print("checked='checked'");} %> onchange="mostrarTipoPermisoDescuento(this);"/>
                        </td>
                        <td class="contenido">
                            <select form="formUsuario" id="tipoPermisoDescuento" name="tipoPermisoDescuento"  <% if(u1==null || u1.getPermisosDescuento()==null){out.print("disabled");} %>>
                                <option value="-1" disabled="disabled" selected hidden>Seleccionar tipo de permiso:</option>
                                <option value="1" <% if(u1!=null&& u1.getPermisosPersonal()!=null && u1.getPermisosDescuento().getId()==1){out.print("selected");}%>>BIBLIOTECA</option>
                                <option value="2" <% if(u1!=null&& u1.getPermisosPersonal()!=null && u1.getPermisosDescuento().getId()==2){out.print("selected");}%>>IMPRESIONES</option>
                                <option value="3" <% if(u1!=null&& u1.getPermisosPersonal()!=null && u1.getPermisosDescuento().getId()==3){out.print("selected");}%>>FARMACIA</option>
                                <option value="4" <% if(u1!=null&& u1.getPermisosPersonal()!=null && u1.getPermisosDescuento().getId()==4){out.print("selected");}%>>ENFERMERIA</option>
                                <option value="5" <% if(u1!=null&& u1.getPermisosPersonal()!=null && u1.getPermisosDescuento().getId()==5){out.print("selected");}%>>CASINO SS.OO.</option>
                                <option value="6" <% if(u1!=null&& u1.getPermisosPersonal()!=null && u1.getPermisosDescuento().getId()==6){out.print("selected");}%>>CASINO CC.</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">
                            <b>Permisos Sistema de Notas (Bedel&iacute;a):</b>
                        </td>
                        <td class="contenido" colspan="2">
                            <input  id="permisoSistemaNotas" type=checkbox name="permisoSistemaNotas" <% if(u1!=null && u1.isNotas()){out.print("checked='checked'");} %> />
                        </td>
                    </tr>
                    <tr>
                        <td class="title">
                            <b>Permisos Sistema de Habilitaci&oacute;n:</b>
                        </td>
                        <td class="contenido" colspan="2">
                            <input  id="permisoSistemaHabilitacion" type=checkbox name="permisoSistemaHabilitacion" <% if(u1!=null && u1.isHabilitacion()){out.print("checked='checked'");} %> />
                        </td>
                    </tr>
                    <%
                    };
                    %>
                    <tr>
                        <td class="title">
                            <b>Profesor: / CI:</b>
                        </td>
                        <td class="contenido">
                            <input  id="esProfesor" type=checkbox name="esProfesor" <% if(u1!=null && u1.isProfesor() || p!=null){out.print("checked='checked'");} %> onchange="mostrarCiProfesor(this);"/>
                        </td>
                        <td class="contenido">
                            <input  id="ciProfesor" type=number name="ciProfesor" <% if(u1!=null && u1.isProfesor()){out.print("value='"+u1.getCiProfesor()+"' required" );}else{if(p!=null){out.print("value='"+p.getCi()+"' required ");}else{out.print("disabled");};} %>/>
                        </td>
                    </tr>
                    
                    <tr <% if (u1!=null){out.print("hidden='hidden'");};%>>
                        <td class="title">
                            <p><b>Contrase&ntilde;a:</b></p>
                        </td>
                        <td class="contenido" colspan="2">
                            <p><input type=password name="pass"/></p>
                        </td>
                    </tr>
                    <tr <% if (u1!=null){out.print("hidden='hidden'");};%>>
                        <td class="title">
                            <p><b>Repita la contrase&ntilde;a:</b></p>
                        </td>
                        <td class="contenido" colspan="2">
                            <p><input type=password name="pass1" /></p>

                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <h3 id="textocontrasena" style="color: red"></h3>
                        </td>
                    </tr>
                </table>
                        <p align='right'><input type="submit"  value="Aceptar" /></p>
                </form>
            </td>
        </tr>
    </table>  
<% 
    }
    else{
         response.sendRedirect("");
    }

%>
<%@ include file="footer.jsp" %>