<%-- 
    Document   : familiares
    Created on : Jul 25, 2018, 8:12:49 AM
    Author     : Gisel
--%>

<%@page import="Classes.EstadoCivil"%>
<%@page import="Classes.Departamento"%>
<%@page import="java.util.HashMap"%>
<%@page import="Manejadores.ManejadorCodigos"%>
<%@page import="Classes.Cadete"%>
<form method="post"  id="formulario1" <% if (request.getParameter("id")!=null){ out.print("action='Cadete?ci="+request.getParameter("id")+"&patronimico=1'");} %> enctype="multipart/form-data">
    <table>
        <tr>
            <td>
                <h3>Datos Patron&iacute;micos:</h3>
            </td>
        </tr>
        <tr>
            <td>
                <h4>Padre:</h4>
            </td>
        </tr>
        <tr>
            <td>Nombre Completo: </td>
            <td><input type=text name="PNombreComp" size="50" value="<%if( c!=null){out.print(c.getPadre().getNombreComp());} %>" /></td>
        </tr>
        <tr>
            <td>Fecha de Nacimiento: </td>
            <td><input type=date name="PFechaNac" size="8" value="<%=c.getPadre().getFechaNac()%>"/></td>
        </tr>
        <tr>
            <td>Departamento de Nacimiento: </td>
            <td>
                <select name="PDepartamentoNac" form="formulario1">
                    <%
                    for(Departamento dep1: ad1.values() ){
                        String s1="";
                        if(c!=null && c.getPadre().getDepartamentoNac()!=null){
                            if(c.getPadre().getDepartamentoNac().getCodigo()==dep1.getCodigo()){
                                s1="selected";
                            }
                        }
                        out.print("<option " + s1 +" value='"+String.valueOf(dep1.getCodigo()) +"'>"+ dep1.getDescripcion() +"</option>");
                    }
                    %>
                 </select>
            </td>
        </tr>
        <tr>
            <td>Localidad de Nacimiento: </td>
            <td><input type=text name="PLocalidadNac" size="50" value="<%if( c!=null){out.print(c.getPadre().getLocalidadNac());} %>"/></td>
        </tr>
        <tr>
            <td>Estado Civil: </td>
            <td>
                <select name="PEstadoCivil" form="formulario1">
                    <%
                    for(EstadoCivil ec: ae.values() ){
                        String s1="";
                        if(c!=null && c.getPadre().getEstadoCivil()!=null){
                            if(c.getPadre().getEstadoCivil().getCodigo()==ec.getCodigo()){
                                s1="selected";
                            }
                        }
                        out.print("<option " + s1 +" value='"+String.valueOf(ec.getCodigo()) +"'>"+ ec.getDescripcion() +"</option>");
                    }
                    %>
                 </select>
            </td>
        </tr>
        <tr>
            <td>Domicilio: </td>
            <td><input type=text name="PDomicilio" size="50" value="<%if( c!=null){out.print(c.getPadre().getDomicilio());} %>"/></td>
        </tr>
        <tr>
            <td>Departamento: </td>
            <td>
                <select name="PDepartamento" form="formulario1">
                    <%
                    ad1 = mc.getDepartamentos();
                    for(Departamento dep1: ad1.values() ){
                        String s1="";
                        if(c!=null && c.getPadre().getDepartamento()!=null){
                            if( c.getPadre().getDepartamento().getCodigo()==dep1.getCodigo()){
                                s1="selected";
                            }
                        }
                        out.print("<option " + s1 +" value='"+String.valueOf(dep1.getCodigo()) +"'>"+ dep1.getDescripcion() +"</option>");
                    }
                    %>
                 </select>
            </td>
        </tr>
        
        <tr>
            <td>Localidad: </td>
            <td><input type=text name="PLocalidad" size="50" value="<%if( c!=null){out.print(c.getPadre().getLocalidad());} %>"/></td>
        </tr>
        <tr>
            <td>Tel&eacute;fono: </td>
            <td><input type=text name="PTelefono" value="<%if( c!=null){out.print(c.getPadre().getTelefono());} %>"/></td>
        </tr>
        <tr>
            <td>Profesi&oacute;n: </td>
            <td><input type=text name="PProfesion" size="50" value="<%if( c!=null){out.print(c.getPadre().getProfesion());} %>"/></td>
        </tr>
        <tr>
            <td>Lugar de trabajo: </td>
            <td><input type=text name="PLugarTrabajo" size="50" value="<%if( c!=null){out.print(c.getPadre().getLugarTrabajo());} %>"/></td>
        </tr>
        <tr>
            <td>
                <h4>Madre:</h4>
            </td>
        </tr>
        <tr>
            <td>Nombre Completo: </td>
            <td><input type=text name="MNombreComp" size="50" value="<%if( c!=null){out.print(c.getMadre().getNombreComp());} %>"/></td>
        </tr>
        <tr>
            <td>Fecha de Nacimiento: </td>
            <td><input type=date name="MFechaNac" size="8" value="<%=c.getMadre().getFechaNac()%>"/></td>
        </tr>
        <tr>
            <td>Departamento de Nacimiento: </td>
            <td>
                <select name="MDepartamentoNac" form="formulario1">
                    <%
                    ad1 = mc.getDepartamentos();
                    for(Departamento dep1: ad1.values() ){
                        String s1="";
                        if(c!=null && c.getMadre().getDepartamentoNac()!=null){
                            if(c.getMadre().getDepartamentoNac().getCodigo()==dep1.getCodigo()){
                                s1="selected";
                            }
                        }
                        out.print("<option " + s1 +" value='"+String.valueOf(dep1.getCodigo()) +"'>"+ dep1.getDescripcion() +"</option>");
                    }
                    %>
                 </select>
            </td>
        </tr>
        <tr>
            <td>Localidad de Nacimiento: </td>
            <td><input type=text name="MLocalidadNac" size="50" value="<%if( c!=null){out.print(c.getMadre().getLocalidadNac());} %>"/></td>
        </tr>
        <tr>
            <td>Estado Civil: </td>
            <td>
                <select name="MEstadoCivil" form="formulario1">
                    <%
                    ae = mc.getEstadosCiviles();
                    for(EstadoCivil ec: ae.values() ){
                        String s1="";
                        if(c!=null && c.getMadre().getEstadoCivil()!=null){
                            if(c.getMadre().getEstadoCivil().getCodigo()==ec.getCodigo()){
                                s1="selected";
                            }
                        }
                        out.print("<option " + s1 +" value='"+String.valueOf(ec.getCodigo()) +"'>"+ ec.getDescripcion() +"</option>");
                    }
                    %>
                 </select>
            </td>
        </tr>
        <tr>
            <td>Domicilio: </td>
            <td><input type=text name="MDomicilio" size="50" value="<%if( c!=null){out.print(c.getMadre().getDomicilio());} %>"></td>
        </tr>
        <tr>
            <td>Departamento: </td>
            <td>
                <select name="MDepartamento" form="formulario1">
                    <%
                    ad1 = mc.getDepartamentos();
                    for(Departamento dep1: ad1.values() ){
                        String s1="";
                        if(c!=null && c.getMadre().getDepartamento()!=null){
                            if(c.getMadre().getDepartamento().getCodigo()==dep1.getCodigo()){
                                s1="selected";
                            }
                        }
                        out.print("<option " + s1 +" value='"+String.valueOf(dep1.getCodigo()) +"'>"+ dep1.getDescripcion() +"</option>");
                    }
                    %>
                 </select>
            </td>
        </tr>
        <tr>
            <td>Localidad: </td>
            <td><input type=text name="MLocalidad" size="50" value="<%if( c!=null){out.print(c.getMadre().getLocalidad());} %>"/></td>
        </tr>
        <tr>
            <td>Tel&eacute;fono: </td>
            <td><input type=text name="MTelefono" value="<%if( c!=null){out.print(c.getMadre().getTelefono());} %>" /></td>
        </tr>
        <tr>
            <td>Profesi&oacute;n: </td>
            <td><input type=text name="MProfesion" size="50" value="<%if( c!=null){out.print(c.getMadre().getProfesion());} %>"/></td>
        </tr>
        <tr>
            <td>Lugar de trabajo: </td>
            <td><input type=text name="MLugarTrabajo" size="50" value="<%if( c!=null){out.print(c.getMadre().getLugarTrabajo());} %>"/></td>
        </tr>
        <tr>
            <td><input name= "ci" hidden="hidden" type="text" value="<%= request.getParameter("ci") %>"/></td>
        </tr>
    </table>
        <p align="right"> <input style="font-size: 18px" type="submit" value='Modificar' > 
</form>
