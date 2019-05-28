<%-- 
    Document   : profesor
    Created on : Dec 6, 2018, 1:33:59 PM
    Author     : Gisel
--%>

<%@page import="Classes.Arma"%>
<%@page import="java.util.HashMap"%>
<%@page import="Classes.Grado"%>
<%@page import="Classes.Bedelia.Profesor"%>
<%@page import="Manejadores.ManejadorProfesores"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<% 
    if(u.isAdmin() || u.getPermisosPersonal().getId()==4){
%>
<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/jquery-ui.js"></script>

    <script>
        function existeProfesor(ciInput){
           // alert(ciInput.value.length);
            if(ciInput.value.length==8){
                xmlhttp=new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                        var obj = jQuery.parseJSON( xmlhttp.responseText );
                        var existe = obj.Profesor;
                        if(existe.length>0){
                            alert("El profesor ya existe en el sistema.");
                            window.location.href="profesor.jsp?id="+ciInput.value;
                        }
                        else{
                            var form= document.getElementById("formularioAlta");
                            var ci =document.getElementById("ci").value;
                            form.reset;
                            document.getElementById("ci").value=ci;
                            document.getElementById("rellenarOtrosDatos").style.display = '';


                        }
                    };
                };
                xmlhttp.open("POST","Profesores?existe=1&ci="+ciInput.value);
                xmlhttp.send();
            }
            else{
                alert("Cédula incorrecta.");
                document.getElementById("rellenarOtrosDatos").style.display = 'none';
                //ciInput.focus();
            }
            return false;
        }
        function Validar(f){
            var r=confirm("¿Seguro que desea guardar los cambios?");
            if (r==true)
            {
                //alert(f.elements["ci"].value);
                if(f.elements["ci"].value.length == 8 ){
                    return true;
                }
                else{
                alert("Cédula incorrecta.");
                    return false;
            }
            }
            else{
                return false;
            }
        }

     </script>
     <%
        ManejadorProfesores mp = ManejadorProfesores.getInstance();
        Profesor c=null;
        if(request.getParameter("id")!=null){
           c = mp.getProfesor(Integer.valueOf(request.getParameter("id")));
        }
     %>
          
     
    <p id="mensaje" style="color: #990000"><% if(session.getAttribute("Mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+session.getAttribute("Mensaje"));}%></p>
    <%
        session.setAttribute("Mensaje",null);
    %>
    <p align="left"><a href="javascript:history.go(-1)"><img src="images/atras.png" width="15%"/></a></p>
     <ul id="tabs">
        <li><a href="#" title="Datos-Personales">Datos Personales</a></li>
    </ul>
    <div id="content">
        <div id="Datos-Personales">
            <form <% if (request.getParameter("id")==null){ out.print("action='Profesores'");} else{out.print("action='Profesores?id="+request.getParameter("id")+"'");} %> method="post" id="formularioAlta" onsubmit="return Validar(this);">
                <table>
                    <tr>
                        <td>C.I.: </td>
                        <td><input type=number id="ci" name="ci" size="8" maxlength="8"  required="required" <%if( c!=null){ out.print("value='"+Integer.valueOf(c.getCi())+"' readonly='readonly'");}else{out.print("onblur=\"existeProfesor(this);\"");} %>/> </td>
                    </tr>
                    <tr id="rellenarOtrosDatos" style="<%if( c==null){ out.print("display:none");} %>">
                        <td colspan="2">
                           <table>
                                <tr>
                                    <td>Fecha de ing.: </td>
                                    <td><input type=date name="fechaIng"/> </td>
                                </tr>
                                <tr>
                                    <td>Grado: </td>
                                    <td>
                                        <select name="grado" form="formularioAlta" required="required">
                                            <%
                                            HashMap<Integer,Grado> ag = mc.getGrados();
                                            String s="";
                                            if(c!=null && c.getArma()==null){
                                                s="selected";
                                            }
                                            out.print("<option "+s+" value='-1'>------</option>");
                                            for(Grado dep: ag.values() ){
                                                s="";
                                                if(c!=null && c.getGrado()!=null && c.getGrado().getId()==dep.getId()){
                                                    s="selected";
                                                }
                                                out.print("<option " + s +" value='"+String.valueOf(dep.getId()) +"'>"+ dep.getDescripcion() +"</option>");
                                                
                                            }
                                            %>
                                         </select>
                                    </td>
                                    <td>Arma: </td>
                                    <td>
                                        <select name="arma" form="formularioAlta" required="required">
                                            <%
                                            HashMap<Integer,Arma> aa = mc.getArmas();
                                            
                                          // System.out.print(c.getArma());
                                            if(c!=null && c.getArma()==null){
                                                s="selected";
                                            }
                                            out.print("<option "+s+" value='-1'>------</option>");
                                            for(Arma dep: aa.values() ){
                                                s="";
                                                if(c!=null && c.getArma()!=null && c.getArma().getId()==dep.getId()){
                                                    s="selected";
                                                }
                                                out.print("<option " + s +" value='"+String.valueOf(dep.getId()) +"'>"+ dep.getDescripcion() +"</option>");
                                            }

                                            %>
                                         </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Primer nombre: </td>
                                    <td><input type="text" name="primerNombre" value="<%if( c!=null){out.print(c.getPrimerNombre());} %>" size="50" required="required"/></td>
                                    <td>Segundo nombre: </td>
                                    <td><input type="text" name="segundoNombre" value="<%if( c!=null){out.print(c.getSegundoNombre());} %>" size="50"/></td>
                                </tr>
                                <tr>
                                    <td>Primer apellido: </td>
                                    <td><input type="text" name="primerApellido" value="<%if( c!=null){out.print(c.getPrimerApellido());} %>" size="50" required="required"/></td>
                                    <td>Segundo apellido: </td>
                                    <td><input type="text" name="segundoApellido" value="<%if( c!=null){out.print(c.getSegundoApellido());} %>" size="50"/></td>
                                </tr>
                                <tr>
                                    <td>Tel&eacute;fono: </td>
                                    <td><input type="text" id="telefono" name="telefono" value="<%if( c!=null){out.print(c.getTelefono());} %>" size="50"/></td>
                                    <td>correo: </td>
                                    <td><input type="text" id="correo" name="correo"  value="<%if( c!=null){out.print(c.getCorreo());} %>" size="50"/></td>
                                </tr>
                                 <tr>
                                    <td>Nro.Cuenta.: </td>
                                    <td><input type="text" id="nroCuenta" name="nroCuenta" value="<%if( c!=null){out.print(c.getNumeroCuenta());} %>" size="50"/></td>
                                    <td>Dep. Financiera: </td>
                                    <td><input type="text" id="depFinanciera" name="depFinanciera"  value="<%if( c!=null){out.print(c.getDependenciaFinanciera());} %>" size="50"/></td>
                                </tr>
                                 <tr>
                                    <td>Cantidad horas: </td>
                                    <td><input type="number" id="cantHoras" value="<%if( c!=null){ out.print(Integer.valueOf(c.getCantHoras()));} %>" name="cantHoras"/></td>
                                    <td>Categor&iacute;a: </td>
                                    <td><input type="number" id="categoria" value="<%if( c!=null){ out.print(Integer.valueOf(c.getCategoria()));} %>" name="categoria"/></td>
                                </tr>
                                <tr>
                                    <td>Antig&uuml;edad: </td>
                                    <td><input type="number" id="antiguedad" value="<%if( c!=null){ out.print(Integer.valueOf(c.getAntiguedad()));} %>" name="antiguedad"/></td>
                                    <td>Pertenece J.E.: </td>
                                    <td><input type="checkbox" id="bedelia" name="bedelia" <% if(c!=null && c.isAdminBedelia()){out.print("checked='checked'");}else{if(c==null){out.print("checked='checked'");}} %>/></td>
                                </tr>
                                <tr>
                                    <td>Observaciones: </td>
                                    <td>
                                        <textarea rows="4" cols="50" name="observaciones" form="formularioAlta"><% if(c!=null){out.print(c.getObservaciones());} %></textarea>
                                    </td>   
            </tr>
                                <tr>
                                    <td colspan="4">
                                        <p align="right"> <input style="font-size: 18px" type="submit" <% if (request.getParameter("ci")==null){ out.print("value='Guardar'");} else{out.print("value='Modificar'");} %> /> </p>
                                    </td>
                                </tr>
                            </table> 
                        </td>
                    </tr>
                </table>
                    
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