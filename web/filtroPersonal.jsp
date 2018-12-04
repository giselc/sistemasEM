<%-- 
    Document   : filtroCadetes
    Created on : May 24, 2018, 8:54:24 AM
    Author     : Gisel
--%>

<%@page import="Classes.Arma"%>
<%@page import="Classes.Curso"%>
<%@page import="Classes.Grado"%>
<%@page import="java.util.HashMap"%>
<%@page import="Classes.Departamento"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<form method="post" onsubmit='return aplicarFiltro(this,<%= tipo %>);' id="formCadetesFiltro" action="">
    <table style="font-size: 70%">
        <%
        if(tipo==1){
        %>
        <tr>
            <td>
                Carrera:
            </td>
            <td>
                <select name="carrera" form="formCadetesFiltro"> 
                    <option value="TODOS" selected="selected">TODOS</option>
                    <option value="C">Cuerpo Comando</option>
                    <option value="A">Apoyo de S. y C.</option>
                </select>
            </td>
        </tr>
        <%
        }
        %>
        <tr>
            <td>
                Grado/s:
            </td>
            <td>
                <select name="grados" multiple="multiple" form="formCadetesFiltro">
                    <%
                        HashMap<Integer, Grado> ag = mc.getGrados();
                        int j = 0;
                        for (Grado g : ag.values()){
                            if(g.getIdTipoPersonal().getId()== tipo ){ //CADETES
                                if (j==0){
                                    j++;
                                }
                                else{
                                    out.print("<option value='"+String.valueOf(g.getId())+"'>"+g.getDescripcion()+"</option>");
                                }
                            }
                        }
                    %>
                </select>
            </td>
        </tr>
        <%
        if(tipo==1){
        %>
        <tr>
            <td>
                Curso/s:
            </td>
            <td>
                <select name="cursos" multiple="multiple" form="formCadetesFiltro">
                    <%
                        HashMap<Integer, Curso> ac = mc.getCursos();
                        j = 0;
                        for (Curso g : ac.values()){
                            if (j==0){
                                j++;
                            }
                            else{
                                out.print("<option value='"+String.valueOf(g.getId())+"'>"+g.getDescripcion()+"</option>");
                            }
                        }
                    %>
                </select>
            </td>
        </tr>
        <%
        }
        %>
        <tr>
            <td>
                Arma/s:
            </td>
            <td>
                <select name="armas" multiple="multiple" form="formCadetesFiltro">
                    <%
                        HashMap<Integer, Arma> aa = mc.getArmas();
                        j = 0;
                        for (Arma g : aa.values()){
                            if (j==0){
                                j++;
                            }
                            else{
                                out.print("<option value='"+String.valueOf(g.getId())+"'>"+g.getDescripcion()+"</option>");
                            }
                        }
                    %>
                </select>
            </td>
        </tr>
        <%
        if(tipo==1){
        %>
        <tr>
            <td>
                LMGA:
            </td>
            <td>
                <select name="lmga" form="formCadetesFiltro"> 
                    <option value="TODOS" selected="selected">TODOS</option>
                    <option value="S">SI</option>
                    <option value="N">NO</option>
                </select>
            </td>
        </tr>
        
        <tr>
            <td>
                Pase directo:
            </td>
            <td>
                <select name="pd" form="formCadetesFiltro"> 
                    <option value="TODOS" selected="selected">TODOS</option>
                    <option value="S">SI</option>
                    <option value="N">NO</option>
                </select>
            </td>
        </tr>      
        <tr>
            <td>
                Sexo:
            </td>
            <td>
                <select name="sexo" form="formCadetesFiltro">
                    <option value="TODOS" selected="selected">TODOS</option>
                    <option value="M">Masculino</option>
                    <option value="F">Femenino</option>
                </select>
            </td>
        </tr>        
        <tr>
            <td>
                Departamento Nacimiento:
            </td>
            <td>
                <select name="depNac" multiple="multiple" form="formCadetesFiltro">
                    <%
                        HashMap<Integer, Departamento>  ad= mc.getDepartamentos();
                        j = 0;
                        for (Departamento dep : ad.values()){
                            if (j==0){
                                j++;
                            }
                            else{
                                out.print("<option value='"+String.valueOf(dep.getCodigo())+"'>"+dep.getDescripcion()+"</option>");
                            }
                        }
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                Departamento Domicilio:
            </td>
            <td>
                <select name="depDom" multiple="multiple" form="formCadetesFiltro">
                    <%
                        j = 0;
                        for (Departamento dep : ad.values()){
                            if (j==0){
                                j++;
                            }
                            else{
                                out.print("<option value='"+String.valueOf(dep.getCodigo())+"'>"+dep.getDescripcion()+"</option>");
                            }
                        }
                    %>
                </select>
            </td>
        </tr>  
        <tr>
            <td>
                Repitiente:
            </td>
            <td>
                <select name="repitiente" form="formCadetesFiltro"> 
                    <option value="TODOS" selected="selected">TODOS</option>
                    <option value="S">SI</option>
                    <option value="N">NO</option>
                </select>
            </td>
        </tr>
        <tr id="hijos">
            <td>
                Cantidad de hijos:
            </td>
            <td>
                <select name="canthijos" multiple="multiple" form="formCadetesFiltro">
                    <option value="0">0</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">+ de 3</option>
                </select>
            </td>
        </tr>
        <%
        }
        %>
        
        <tr>
            <td colspan="2" style="text-align: right">
               <input type="submit" value="Aplicar" onclick='cerrar_dialog(dialog1)'/>
            </td>
        </tr>
    </table>
</form>
