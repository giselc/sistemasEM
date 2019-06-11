<%-- 
    Document   : libreta
    Created on : 29/05/2019, 10:52:33
    Author     : Gisel
--%>

<%@page import="java.util.HashMap"%>
<%@page import="Classes.Bedelia.LibretaIndividual"%>
<%@page import="Classes.Bedelia.CursoBedelia"%>
<%@page import="Classes.Bedelia.Profesor"%>
<%@page import="Manejadores.ManejadorProfesores"%>
<%@page import="Classes.Bedelia.Libreta"%>
<%@page import="Manejadores.ManejadorBedelia"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/jquery-ui.js"></script>
<script>
    $(document).ready(function() {
                $("#content div").hide();
                $("#tabs li:first").attr("id","current");
                $("#content div:first").fadeIn();
                $("#loader").fadeOut();
            $('#tabs a').click(function(e) {
               // document.getElementById("mensaje").innerHTML="";
                e.preventDefault();
                $("#content div").hide();
                $("#tabs li").attr("id","");
                $(this).parent().attr("id","current");
                $('#' + $(this).attr('title')).fadeIn();
            });
        });
        function verificarFormulario(){
            if(document.getElementById("profesor")!=null && document.getElementById("profesor").value!=-1){
                if(document.getElementById("curso")!=null && document.getElementById("curso").value!=-1){
                    if(document.getElementById("grupo")!=null && document.getElementById("grupo").value!=-1){
                        if(document.getElementById("materia")!=null && document.getElementById("materia").value!=-1){
                            var sbstr=document.getElementById("grupo").value.substring(document.getElementById("grupo").value.indexOf("/")+1,document.getElementById("grupo").value.lenght);
                            var cantAlumnos = sbstr.substring(sbstr.indexOf("/")+1,sbstr.lenght);
                            if(cantAlumnos=="0"){
                                if(confirm("EL grupo no tiene alumnos asociados, desea continuar?")){
                                    return true;
                                }
                                else{
                                    return false;
                                };
                            }
                            return true;
                        }
                        else{
                            alert("ERROR! Debe seleccionar una materia.");
                            return false;

                        }
                    }
                    else{
                        
                        alert("ERROR! Debe seleccionar un grupo.");
                        return false;

                    }
                }
                else{
                    alert("ERROR! Debe seleccionar un curso.");
                    return false;

                }
            }
            else{
                alert("ERROR! Debe seleccionar un profesor.");
                return false;
                
            }
        }
        function mostrarCampos(){
            if(document.getElementById("grupo")==null){
                xmlhttp=new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                        
                        var obj = jQuery.parseJSON( xmlhttp.responseText );
                        var mat = obj.grupos;
                        var tabla= document.getElementById("tablaformulario");
                        var tblBody = tabla.getElementsByTagName("tbody");
                        if(mat.length>0){
                           // var textoCelda = "<select name=\"materia\" form=\"formulario\" required=\"required\" id=\"materia\">";
                            var hilera = document.createElement("tr");
                            var celda1=document.createElement("td");
                            celda1.appendChild(document.createTextNode("Grupo:"));
                            hilera.appendChild(celda1);
                            var celda = document.createElement("td");    
                            var sel = document.createElement('select');
                            sel.id="grupo";
                            sel.name="grupo";
                            var option;
                            option=document.createElement("option");
                            option.setAttribute("value",-1);
                            option.setAttribute("label","");
                            sel.appendChild(option);
                            sel.setAttribute("onchange","alerta(this.value)")
                            for (var i=0; i<mat.length;i++) {
                                    option=document.createElement("option");
                                    option.setAttribute("value",mat[i].anio+"/"+mat[i].nombre+"/"+mat[i].cantAlumnos);
                                    option.setAttribute("label",mat[i].anio+"/"+mat[i].nombre);
                                    sel.appendChild(option);
                            }
                            //textoCelda+="</select>";
                            celda.appendChild(sel);
                            hilera.appendChild(celda);
                            tblBody[0].appendChild(hilera);
                        }
                            //tabla.appendChild(tblBody[0]);
                        mat = obj.materias;
                        if(mat.length>0){
                           // var textoCelda = "<select name=\"materia\" form=\"formulario\" required=\"required\" id=\"materia\">";
                                hilera = document.createElement("tr");
                                celda1=document.createElement("td");
                                celda1.appendChild(document.createTextNode("Materia:"));
                                hilera.appendChild(celda1);
                                celda = document.createElement("td");    
                                sel = document.createElement('select');
                                sel.id="materia";
                                sel.name="materia";
                                var option;
                                option=document.createElement("option");
                                option.setAttribute("value",-1);
                                option.setAttribute("label","");
                                sel.appendChild(option);
                                for (var i=0; i<mat.length;i++) {
                                    option=document.createElement("option");
                                    option.setAttribute("value",mat[i].id);
                                    option.setAttribute("label",mat[i].codigo + " - "+mat[i].nombre);
                                    sel.appendChild(option);
                                }
                                //textoCelda+="</select>";

                                celda.appendChild(sel);
                                hilera.appendChild(celda);
                                tblBody[0].appendChild(hilera);
                                //tabla.appendChild(tblBody[0]);
                            
                        }
                    };
                };
                xmlhttp.open("POST","ObtenerCamposParaLibreta?materiasGrupoCurso=1&idCurso="+document.getElementById("curso").value);
                xmlhttp.send();
                return false;
            }
            else{
                document.getElementById("tablaformulario").deleteRow(2);
                document.getElementById("tablaformulario").deleteRow(2);
                mostrarCampos();
            }
        }
        function alerta(valueSelect){
            var sbstr=valueSelect.substring(valueSelect.indexOf("/")+1,valueSelect.lenght);
            var cantAlumnos = sbstr.substring(sbstr.indexOf("/")+1,sbstr.lenght);
            if(cantAlumnos=="0"){
                alert("Grupo sin alumnos asociados.");
            }
        }
        function cambiarEstadoPresenteAusente(boton){
            //alert("P-"+boton.value);
            var input= document.getElementById("P-"+boton.value);
            var CF = document.getElementById("CF-"+boton.value);
            var OBS = document.getElementById("OBS-"+boton.value);
            if(input.value==1){
                boton.innerText="Ausente";
                boton.style="color: red";
                CF.style="display:block";
                OBS.style="display:block";
                input.value=2;
            }
            else{
                boton.innerText="Presente";
                boton.style="color: green";
                CF.style="display:none";
                OBS.style="display:none";
                input.value=1;
            }
            return false;
        }
</script>
<% 
    
%>
<% 
    Libreta d=null;    
    ManejadorBedelia mp = ManejadorBedelia.getInstance();

    if(request.getParameter("id")!=null){
        int id = Integer.valueOf(request.getParameter("id"));
        d= mp.getLibreta(id);
    }
%>
<p id="mensaje" style="color: #ffffff"><% if(sesion.getAttribute("Mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+sesion.getAttribute("Mensaje"));}%></p>
<%
    sesion.setAttribute("Mensaje",null);
%>
<p align="left"><a href="javascript:history.go(-1)"><img src="images/atras.png" width="15%"/></a></p>
<h1 align="center" style="font-family: arial"><u><% if (d!=null){out.print("Ver Libreta");}else{out.print("Agregar Libreta");}%></u></h1>
<div id="enviando"  style="position: fixed; top:0; left:0; width:100%; height: 100%;background: url('images/loading-verde.gif') center center no-repeat; background-size: 20%; display: none"></div>
<%
if(d==null){
    if(u.isAdmin() || u.getPermisosPersonal().getId()==4){
%>
        <form method="post"  name="formulario" id="formulario"  onsubmit="return verificarFormulario();" action="Libreta?id=<%if (d!=null){out.print(d.getId());}else{out.print("-1");}%>">
            <table  width='70%' align='center' style="text-align: left; font-family: arial" id="tablaformulario">
                 <tr>
                    <td>Profesor: </td>
                    <td>
                        <select name="profesor" form="formulario" required="required" id="profesor">
                            <option value="-1"></option>
                            <%
                            Manejadores.ManejadorProfesores mprof = ManejadorProfesores.getInstance();
                            for(Profesor prof: mprof.getProfesores() ){
                                String s="";
                                if(d!=null && d.getProfesor()!=null && d.getProfesor().getCi()==prof.getCi()){
                                    s="selected";
                                }
                                String grado="";
                                if(prof.getGrado()!=null){
                                    grado=prof.getGrado().getAbreviacion()+" ";
                                }
                                out.print("<option " + s +" value='"+String.valueOf(prof.getCi()) +"'>"+grado + prof.getPrimerApellido()+" "+prof.getPrimerNombre()+"</option>");
                            }
                            %>
                         </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        Sal&oacute;n:
                    </td>
                    <td>
                        <input name="salon" value="<%if(d!=null){out.print(d.getSalon());}%>" type="text" />
                    </td>
                </tr>
                 <tr>
                    <td>Curso: </td>
                    <td>
                        <select name="curso" form="formulario" required="required" onchange="mostrarCampos();" id="curso">
                            <option value="-1"></option>
                            <%
                            for(CursoBedelia cb: mp.getCursos().values()){
                                out.print("<option value='"+cb.getId() +"'>"+ cb.getCodigo()+" - "+cb.getNombre()+"</option>");
                            }
                            %>
                         </select>
                    </td>
                </tr>
            </table>
            <p align='right'><input type="submit"  value="Aceptar" /></p>
        </form>    
                 <%
    }
}
else{
    if(u.isAdmin()||u.getPermisosPersonal().getId()==4||u.isProfesor()){
        if(u.isAdmin()||u.getPermisosPersonal().getId()==4){
            %>
            <form method="post"  name="formulario1" id="formulario1" action="Libreta?id=<%=d.getId()%>">
            <table  width='70%' align='center' style="text-align: left; font-family: arial" >
                 <tr>
                    <td>Profesor: </td>
                    <td>
                        <select name="profesor" form="formulario1" required="required">
                            <%
                            Manejadores.ManejadorProfesores mprof = ManejadorProfesores.getInstance();
                            for(Profesor prof: mprof.getProfesores() ){
                                String s="";
                                if(d!=null && d.getProfesor()!=null && d.getProfesor().getCi()==prof.getCi()){
                                    s="selected";
                                }
                                out.print("<option " + s +" value='"+String.valueOf(prof.getCi()) +"'>"+ prof.obtenerNombreCompleto() +"</option>");
                            }
                            %>
                         </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        Sal&oacute;n:
                    </td>
                    <td>
                        <input name="salon" value="<%= d.getSalon() %>" type="text" />
                    </td>
                </tr>
            </table>
            <p align='right'><input type="submit"  value="Modificar" /></p>
            </form>
            
            <table  width='70%' align='center' style="text-align: left; font-family: arial">
                <tr>
                    <td>
                        Curso:
                    </td>
                    <td>
                        <a target="_blank" href="curso.jsp?id=<%= d.getGrupo().getCusoBedelia().getId() %>" >  <%= d.getGrupo().getCusoBedelia().getNombre() %> </a>
                    </td>
                </tr>
                <tr>
                    <td>
                        Grupo:
                    </td>
                    <td>
                        <a target="_blank" href="grupo.jsp?idCurso=<%= d.getGrupo().getCusoBedelia().getId() %>&anioGrupo=<%= d.getGrupo().getAnio() %>&nombreGrupo=<%= d.getGrupo().getNombre() %>" >    <%= d.getGrupo().getNombre() %> </a>
                    </td>
                </tr>
                <tr>
                    <td>
                        Materia:
                    </td>
                    <td>
                        <a target="_blank" href="materia.jsp?id=<%= d.getMateria().getId() %>" > <%= d.getMateria().getNombre() %> </a>
                    </td>
                </tr>
            </table>
        <%
        }
        else{
          %>
            <table  width='70%' align='center' style="text-align: left; font-family: arial" >
                 <tr>
                    <td>Profesor: </td>
                    <td>
                        <%=d.getProfesor().obtenerNombreCompleto() %>
                    </td>
                </tr>
                <tr>
                    <td>
                        Sal&oacute;n:
                    </td>
                    <td>
                        <%=d.getSalon() %>
                    </td>
                </tr>
                <tr>
                    <td>
                        Curso:
                    </td>
                    <td>
                        <%= d.getGrupo().getCusoBedelia().getNombre() %>
                    </td>
                </tr>
                <tr>
                    <td>
                        Grupo:
                    </td>
                    <td>
                        <%= d.getGrupo().getNombre() %>
                    </td>
                </tr>
                <tr>
                    <td>
                        Materia:
                    </td>
                    <td>
                        <%= d.getMateria().getNombre() %>
                    </td>
                </tr>
            </table>
                <%
        }
        %>
            
            <form method="post"  name="pasarLista" id="pasarLista" action="Libreta?id=<%=d.getId()%>&pasarLista=1">
                <ul id="tabs">
                    <li><a href="#" title="PasarLista"><b>Pasar Lista</b></a></li>
                    <li><a href="#" title="historiaDeInasistencias"><b>Historial de Inasistencias</b></a></li>
                </ul>
                <div id="content">
                    <div id="PasarLista">
                       <input type="date"  <%
                       
                        java.util.Calendar fecha1 = java.util.Calendar.getInstance();
                        int mes = fecha1.get(java.util.Calendar.MONTH)+1;
                        String cero="";
                        if(mes<10){
                            cero="0";
                        }
                        out.print("value=\""+  fecha1.get(java.util.Calendar.YEAR)+"-"+cero+mes+"-"+fecha1.get(java.util.Calendar.DATE)+"\"");
                       
                       %> id="fecha" name="fecha">
                        <%
                            out.print("<table style='width: 100%;' id='tablalistado'>"
                                    + "<tr style='background-color:#ffcc66'>"
                                        +"<td style='width: 5%' align='center'></td>"

                                        +"<td style='width: 10%' align='center'>Foto</td>"
                                        +"<td style='width: 10%' align='center'>Grado</td>"
                                        +"<td style='width: 10%' align='center'>Primer Apellido</td>"
                                        +"<td style='width: 10%' align='center'>Primer Nombre</td>"
                                    +"<td style='width: 5%' align='center'></td>"
                            +"</tr>");
                            int i=0;
                            String color;
                            for (  LibretaIndividual p : d.getLibretasIndividuales().values()){
                                    if ((i%2)==0){
                                        color=" #ccccff";
                                    }
                                    else{
                                        color=" #ffff99";
                                    }
                                    i++;

                                   out.print("<tr style='background-color:"+color+"'>"
                                   +"<td style='width: 5%' align='center'>"+i+"</td>"

                                   +"<td style='width: 10%' align='center'>");

                                if(p.getAlumno().getFoto()!=""){
                                %>
                                <a href="Listar?List[]=<%=p.getAlumno().getCi()%>&fichas=1&tipoPersonal=1" target="_blank">   <p align="center"><label for="uploadImage" ><img src="<%=request.getContextPath()%>/Imagenes?foto=<%=p.getAlumno().getCi() %>" id="uploadPreview" style="width: 25%" onclick=""/></label></p></a>
                                <%
                                }
                                else{
                                %>

                                <a href="Listar?List[]=<%=p.getAlumno().getCi()%>&fichas=1&tipoPersonal=1" target="_blank"> <p align="center"><label for="uploadImage" ><img src="images/silueta.jpg" id="uploadPreview" style="width: 25%" onclick=""/></label></p></a>
                                <%
                                }
                                out.print("</td>"
                                   +"<td style='width: 10%' align='center'>"+p.getAlumno().getGrado().getAbreviacion()+"</td>"
                                   +"<td style='width: 10%' align='center'>"+p.getAlumno().getPrimerApellido()+"</td>"
                                   +"<td style='width: 10%' align='center'>"+ p.getAlumno().getPrimerNombre() +"</td>"
                                    +"<td style='width: 5%' align='center'>");
                                if(p.isActivo()){
                                out.print("<button width=100% value=\""+p.getAlumno().getCi()+"\" onclick=\"return cambiarEstadoPresenteAusente(this);\" style=\"color: green\">Presente</button><input name=\"P-"+p.getAlumno().getCi()+"\" id=\"P-"+p.getAlumno().getCi()+"\" type=\"number\" value=\"1\" hidden=\"hidden\"/>");
                                %>
                                    <select name="CF-<%=p.getAlumno().getCi()%>" form="pasarLista" style="display:none" id="CF-<%=p.getAlumno().getCi()%>" >
                                        <option value="-1">Seleccionar causa:</option>
                                        <option value="F0">F0 - Sin causa justificada</option>
                                        <option value="F1">F1 - Guardia</option>
                                        <option value="F2">F2 - Enfermer&iacute;a</option>
                                        <option value="F3">F3 - Comisi&oacute;n</option>
                                        <option value="F4">F4 - Internado en H.C.FF.AA.</option>
                                        <option value="F5">F5 - Eximido</option>
                                        <option value="F6">F6 - Consejero Acad&eacute;mico</option>
                                    </select>
                                    <input id="OBS-<%=p.getAlumno().getCi()%>" name="OBS-<%=p.getAlumno().getCi()%>" style="display: none" type="text" value="Observaciones" style="color:#999999" onClick="if(this.value=='Observaciones'){this.value=''}" onblur="if(this.value==''){this.value='Observaciones'}"/>
                                    <%
                                }
                                else{
                                    %>
                                    <h3>ALUMNO INACTIVO</h3>
                                    <%
                                }
                                    //out.print("<td style='width: 5%' align='center'><a href='libretaIndividual.jsp?idLibreta="+d.getId()+"&ciAlumno="+p.getAlumno().getCi()+"'><img src='images/ver.png' width='60%' /></a></td>"
                                out.print("</td><tr>");

                            }
                            out.print("</table>");
                        %> 
                        <input type="submit" value="ACEPTAR" />
                     </div>
                        <div id="historiaDeInasistencias"></div>    
                </div> 
                     
            </form>
        <%
    }
    else{
     response.sendRedirect("");
    }
}

%>

<%@ include file="footer.jsp" %>