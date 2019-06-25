<%-- 
    Document   : libreta
    Created on : 29/05/2019, 10:52:33
    Author     : Gisel
--%>

<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Classes.Bedelia.Falta"%>
<%@page import="java.util.LinkedList"%>
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
            var div = document.getElementById("DIV-"+boton.value);
            var cf = document.getElementById("CF-"+boton.value);
            var cantHoras = document.getElementById("CANTHORAS-"+boton.value);
            if(input.value==1){
                boton.innerText="Ausente";
                boton.style="color: red";
                div.style="display:block";
                input.value=2;
                cf.required=true;
                cantHoras.required=true;
            }
            else{
                boton.innerText="Presente";
                boton.style="color: green";
                div.style="display:none";
                input.value=1;
                cf.required=false;
                cantHoras.required=false;
            }
            return false;
        }
        function cambiarGrilla(select,idLibreta){
            var mes=select.value;
            xmlhttp=new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                        
                        var obj = jQuery.parseJSON( xmlhttp.responseText );
                        //alert(xmlhttp.responseText );
                        var alumnos = obj.alumnos;
                        var colspan=obj.cantDiasMes[0].cantDias + 1;
                        var output="<tr >"
                                    +"<td>"
                                        
                                    +"</td>"
                                    +"<td colspan='" + colspan + "' style='background-color:#ffcc66;padding:0px;'>"
                                           +"Días"
                                    +"</td>"
                                +"</tr>"
                                +"<tr style='background-color:#ffcc66;padding:0px;'>"
                                    +"<td>"
                                        +"Alumnos:"
                                    +"</td>";
                                    for (var j=1; j<=obj.cantDiasMes[0].cantDias;j++){
                                        output+="<td style=\"width: 3%\">"+j+"</td>";
                                    }
                                     output+="<td>"
                                        +"TOTAL:"
                                    +"</td>";
                                    output+="</tr>";     
                                    var color="";
                                    var total;
                        for (var i=0; i<alumnos.length;i++) {
                            total=0;
                            if ((i%2)==0){
                                color=" #ccccff";
                            }
                            else{
                                color=" #ffff99";
                            }
                            output+="<tr style=\"background-color:"+color+";\">"
                                    + "<td>"
                                    + alumnos[i].alumno[0].primerApellido+ " " + alumnos[i].alumno[0].primerNombre 
                                    +"</td>";
                            var actualJ=1;
                            for(var j=1;j<=obj.cantDiasMes[0].cantDias;j++ ){
                                if(alumnos[i].alumno[actualJ]!=null){
                                    if(alumnos[i].alumno[actualJ].dia==j){
                                        
                                        output+="<td>";
                                        for(var k=0;k<alumnos[i].alumno[actualJ].faltasxDia.length;k++){
                                                var faltasxDia=alumnos[i].alumno[actualJ].faltasxDia[k];
                                                output+="<b title='Fecha: "+faltasxDia.fecha+"&#10;C&oacute;digo: "+faltasxDia.faltaCodigo+"&#10;Cantidad de horas: "+faltasxDia.cantHoras+"&#10;Observaciones: "+faltasxDia.observaciones+"'>"+faltasxDia.faltaCodigo+"</b>";
                                                total+=faltasxDia.cantHoras;
                                        }
                                        output+="</td>";
                                        actualJ++;

                                    }
                                    else{
                                        output+="<td></td>";
                                    };
                                }
                                else{
                                    output+="<td></td>";
                                }
                            }
                            if(total==0){
                                total="";
                            }
                            output+="<td>"+total+"</td>";
                            output+="</tr>";
                            document.getElementById("grillaFaltas").innerHTML=output;
                        }
                        
                    };
                };
                xmlhttp.open("POST","ObtenerCamposParaLibreta?grilla="+mes+"&idLibreta="+idLibreta);
                xmlhttp.send();
                return false;
        }
        function validarFormPasarLista(form){
            for (var i = 0; i < form.elements.length; i++) { 
                if(form.elements[i].type=="select-one"){
                    if(form.elements[i].required && form.elements[i].value=="-1"){
                        alert("Debe seleccionar código de falta.");
                        form.elements[i].focus();
                        return false;
                    }
                };
            }
            return true;
        } 
        
</script>
<style>
    table tr td{
        padding: 0px;
    }
    p{
        margin: 0px;
    }
</style>

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
<h1 align="center"><u><% if (d!=null){out.print("Ver Libreta");}else{out.print("Agregar Libreta");}%></u></h1>
<div id="enviando"  style="position: fixed; top:0; left:0; width:100%; height: 100%;background: url('images/loading-verde.gif') center center no-repeat; background-size: 20%; display: none"></div>
<%
if(d==null){
    if(u.isAdmin() || u.getPermisosPersonal().getId()==4){
%>
        <form method="post"  name="formulario" id="formulario"  onsubmit="return verificarFormulario();" action="Libreta?id=<%if (d!=null){out.print(d.getId());}else{out.print("-1");}%>">
            <table  width='70%' align='center' style="text-align: left;" id="tablaformulario">
                 <tr>
                    <td>Profesor: </td>
                    <td>
                        <select name="profesor" form="formulario" required="required" id="profesor">
                            <option value="-1" disabled="disabled" selected="selected" ></option>
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
    if(u.isAdmin()||u.getPermisosPersonal().getId()==4||(u.isProfesor()&& u.getCiProfesor()==d.getProfesor().getCi())){
        if(u.isAdmin()||u.getPermisosPersonal().getId()==4){
            %>
            <form method="post"  name="formulario1" id="formulario1" action="Libreta?id=<%=d.getId()%>">
            <table  width='70%' align='center' style="text-align: left;" >
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
            
            <table  width='70%' align='center' style="text-align: left;">
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
            <table  width='70%' align='center' style="text-align: left" >
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
            
        <form method="post"  name="pasarLista" id="pasarLista" onsubmit="return validarFormPasarLista(this)" action="Libreta?id=<%=d.getId()%>&pasarLista=1">
                <ul id="tabs">
                    <li><a href="#" title="PasarLista"><b>Pasar Lista</b></a></li>
                    <li><a href="#" title="historiaDeInasistencias"><b>Historial de Inasistencias</b></a></li>
                    <li><a href="#" title="notas"><b>Notas</b></a></li>
                    <li><a href="#" title="promedios"><b>Promedios</b></a></li>
                    <li><a href="#" title="temasTratados"><b>Temas Tratados</b></a></li>
                    <li><a href="#" title="sanciones"><b>Sanciones</b></a></li>
                </ul>
                <div id="content">
                    <div id="PasarLista">
                        <p align="right">
                            <button form="pasarLista" style="background-color: #ff6600; border-radius: 15px; color: #ffffff; font-size: large;">&nbsp;FINALIZAR&nbsp;</button>
                       </p>
                       <p>
                        Seleccionar fecha:
                       <input type="date"  <%
                       
                        java.util.Calendar fecha1 = java.util.Calendar.getInstance();
                        int mes = fecha1.get(java.util.Calendar.MONTH)+1;
                        
                        String cero="";
                        if(mes<10){
                            cero="0";
                        }
                        out.print("value=\""+  fecha1.get(java.util.Calendar.YEAR)+"-"+cero+mes+"-"+fecha1.get(java.util.Calendar.DATE)+"\"");
                       
                        %> id="fecha" name="fecha"></p>
                        <%
                            out.print("<table style='width: 100%;' id='tablalistado' >"
                                    + "<tr style='background-color:#ffcc66;padding:0px'>"
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

                                   out.print("<tr style='background-color:"+color+";'>"
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
                                <div style="display:none" id="DIV-<%= p.getAlumno().getCi() %>">
                                    <table>
                                        <tr>
                                            <td>
                                                Causa:
                                            </td>
                                            <td>
                                                <select name="CF-<%=p.getAlumno().getCi()%>" form="pasarLista" id="CF-<%=p.getAlumno().getCi()%>" >
                                                    <option value="-1" disabled="disabled" selected hidden>Seleccionar causa:</option>
                                                    <option value="F0">F0 - Sin causa justificada</option>
                                                    <option value="F1">F1 - Guardia</option>
                                                    <option value="F2">F2 - Enfermer&iacute;a</option>
                                                    <option value="F3">F3 - Comisi&oacute;n</option>
                                                    <option value="F4">F4 - Internado en H.C.FF.AA.</option>
                                                    <option value="F5">F5 - Eximido</option>
                                                    <option value="F6">F6 - Consejero Acad&eacute;mico</option>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                Cantidad de horas:
                                            </td>
                                            <td>
                                                <input id="CANTHORAS-<%=p.getAlumno().getCi()%>" name="CANTHORAS-<%=p.getAlumno().getCi()%>"  type="number" min="0" step="1"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                Obs.:
                                            </td>
                                            <td>
                                                <input  name="OBS-<%=p.getAlumno().getCi()%>"  type="text" value="Observaciones" style="color:#999999" onClick="if(this.value=='Observaciones'){this.value='';this.color=black;}" onblur="if(this.value==''){this.value='Observaciones';this.color=grey;}"/>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
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
                        
                     </div>
                    <div id="historiaDeInasistencias">
                            <select onchange="cambiarGrilla(this,<%=d.getId()%>);">
                                <option value="1" <% if(mes==1){out.print("selected");} %> > ENERO </option>
                                <option value="2" <% if(mes==2){out.print("selected");} %> > FEBRERO </option>
                                <option value="3" <% if(mes==3){out.print("selected");} %> > MARZO </option>
                                <option value="4" <% if(mes==4){out.print("selected");} %> > ABRIL </option>
                                <option value="5" <% if(mes==5){out.print("selected");} %> > MAYO </option>
                                <option value="6" <% if(mes==6){out.print("selected");} %> > JUNIO </option>
                                <option value="7" <% if(mes==7){out.print("selected");} %> > JULIO </option>
                                <option value="8" <% if(mes==8){out.print("selected");} %> > AGOSTO </option>
                                <option value="9" <% if(mes==9){out.print("selected");} %> > SETIEMBRE </option>
                                <option value="10" <% if(mes==10){out.print("selected");} %> > OCTUBRE </option>
                                <option value="11" <% if(mes==11){out.print("selected");} %> > NOVIEMBRE </option>
                                <option value="12" <% if(mes==12){out.print("selected");} %> > DICIEMBRE </option>
                            </select>
                            <table id="grillaFaltas" style="border-collapse: separate;border-spacing: 2px;text-align: center;vertical-align: central;">
                                <%
                                Calendar cal = new GregorianCalendar(fecha1.get(java.util.Calendar.YEAR), mes-1, 1); 
                                // Get the number of days in that month 
                                int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
                                
                                %>
                                <tr >
                                    <td>
                                        
                                    </td>
                                    <td colspan="<%= days+1 %>" style='background-color:#ffcc66;padding:0px;'>
                                            D&iacute;as:
                                    </td>
                                </tr>
                                <tr style='background-color:#ffcc66;padding:0px;'>
                                    <td>
                                        Alumnos:
                                    </td>
                                    <%
                                    
                                    for (int j=1; j<=days;j++){
                                        out.print("<td style=\"width: 3%\">"+j+"</td>");
                                    }
                                    out.print("<td>TOTAL</td>");
                    out.print("</tr>"); 
                    i=0;
                                    HashMap<Integer,LinkedList<Falta>> grilla;
                                    for(LibretaIndividual l: d.getLibretasIndividuales().values()){
                                        if ((i%2)==0){
                                            color=" #ccccff";
                                        }
                                        else{
                                            color=" #ffff99";
                                        }
                                        i++;
                    out.print("<tr style=\"background-color:"+color+";\">"
                                                + "<td>"
                                                + l.getAlumno().getPrimerApellido()+ " " + l.getAlumno().getPrimerNombre() 
                                                +"</td>");
                                        
                                        grilla=l.getGrillaFaltas().get(mes);
                                        if(grilla==null){
                                            for (int j=1; j<=days+1;j++){
                                                out.print("<td>");
                                                
                                                out.print("</td>");
                                            } 
                                        }
                                        else{
                                            int total=0;
                                           for (int j=1; j<=days;j++){
                                                out.print("<td>");
                                                if(grilla.get(j)!=null){
                                                    for(Falta f:grilla.get(j)){
                                                        out.print("<b title='Fecha: "+f.getFecha()+"&#10;C&oacute;digo: "+f.getCodigoMotivo()+"&#10;Cantidad de horas: "+f.getCanthoras()+"&#10;Observaciones: "+f.getObservaciones()+"'>"+f.getCodigoMotivo()+"</b>");
                                                        total+=f.getCanthoras();
                                                    }
                                                    
                                                }
                                                
                                                out.print("</td>");
                                            }
                                           out.print("<td>"+total+"</td>");
                                        }
                                        
                    out.print("</tr>");
                                    
                                   }
                            
                                %>
                            </table>
                        </div>
                    
                    <div id="notas">
                        <h1>
                            SECCI&Oacute;N EN CONSTRUCCI&Oacute;N
                        </h1>
                    </div>
                    <div id="promedios">
                        <h1>
                            SECCI&Oacute;N EN CONSTRUCCI&Oacute;N
                        </h1>
                    </div>
                    <div id="temasTratados">
                        <h1>
                            SECCI&Oacute;N EN CONSTRUCCI&Oacute;N
                        </h1>
                    </div>
                    <div id="sanciones">
                        <h1>
                            SECCI&Oacute;N EN CONSTRUCCI&Oacute;N
                        </h1>
                    </div>
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