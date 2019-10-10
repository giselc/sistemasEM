<%-- 
    Document   : libreta
    Created on : 29/05/2019, 10:52:33
    Author     : Gisel
--%>

<%@page import="Classes.Bedelia.Nota"%>
<%@page import="Classes.FaltaSancion"%>
<%@page import="Classes.Bedelia.TemaTratado"%>
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
                document.getElementById("tablaformulario").deleteRow(3);
                document.getElementById("tablaformulario").deleteRow(3);
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
                                    var tipo="";
                                    var tipo1="";
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
                                        for(var k=0;k<alumnos[i].alumno[actualJ].faltasSancionesxDia.length;k++){
                                                var faltasSancionesxDia=alumnos[i].alumno[actualJ].faltasSancionesxDia[k];
                                                if(faltasSancionesxDia.esFalta){
                                                    output+="<b title='Fecha: "+faltasSancionesxDia.fecha+"&#10;C&oacute;digo: "+faltasSancionesxDia.codigo+"&#10;Cantidad de horas: "+faltasSancionesxDia.cantHoras+"&#10;Observaciones: "+faltasSancionesxDia.observaciones+"'    ><p id='"+faltasSancionesxDia.id+"' onMouseleave=\"eliminar(false,false,"+faltasSancionesxDia.id+",'"+faltasSancionesxDia.codigo+"','"+faltasSancionesxDia.idLibreta+"','"+faltasSancionesxDia.ciAlumno+"','"+faltasSancionesxDia.ciProfesor+"');\" onMouseEnter=\"eliminar(false,true,"+faltasSancionesxDia.id+",'"+faltasSancionesxDia.codigo+"','"+faltasSancionesxDia.idLibreta+"','"+faltasSancionesxDia.ciAlumno+"','"+faltasSancionesxDia.ciProfesor+"');\">"+faltasSancionesxDia.codigo+"</p></b>";
                                                    total+=faltasSancionesxDia.cantHoras;
                                                }
                                                else{
                                                    tipo="M";
                                                    tipo1="M";
                                                    if(faltasSancionesxDia.codigo=="R"){
                                                        tipo="R";
                                                        tipo1="R&#10;Minutos tardes: "+faltasSancionesxDia.minutosTardes;
                                                    }
                                                    output+="<b title='Fecha: "+faltasSancionesxDia.fecha+"&#10;C&oacute;digo: "+tipo1+"&#10;Causa: "+faltasSancionesxDia.observaciones+"'    ><p id='"+faltasSancionesxDia.id+"' onMouseleave=\"eliminar(false,false,"+faltasSancionesxDia.id+",'"+faltasSancionesxDia.codigo+"','"+faltasSancionesxDia.idLibreta+"','"+faltasSancionesxDia.ciAlumno+"','"+faltasSancionesxDia.ciProfesor+"');\" onMouseEnter=\"eliminar(false,true,"+faltasSancionesxDia.id+",'"+faltasSancionesxDia.codigo+"','"+faltasSancionesxDia.idLibreta+"','"+faltasSancionesxDia.ciAlumno+"','"+faltasSancionesxDia.ciProfesor+"');\">"+faltasSancionesxDia.codigo+"</p></b>";
                                                }
                                                
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
                            output+="<td id=TOTAL-"+alumnos[i].alumno[0].ci+">"+total+"</td>";
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
        function validarFormSancionar(form){
            for (var i = 0; i < form.elements.length; i++) { 
                if(form.elements[i].type=="select-one"){
                    if(form.elements[i].required && form.elements[i].value=="-1"){
                        alert("Debe seleccionar tipo de sanción.");
                        form.elements[i].focus();
                        return false;
                    }
                };
            }
            return true;
        }
        function eliminar(esFalta,over,id,codigo,idLibreta,ciAlumno,ciProfesor){
            if(over){
                if(esFalta){
                    document.getElementById(id).innerHTML="<image src='images/eliminar.png' width='50%' onclick='eliminarFalta("+id+","+idLibreta+","+ciAlumno+","+ciProfesor+")'/>";
                }
                else{
                    document.getElementById(id).innerHTML="<image src='images/eliminar.png' width='50%' onclick='eliminarSancion("+id+","+idLibreta+","+ciAlumno+","+ciProfesor+")'/>";
                }
            }
            else{
                document.getElementById(id).innerHTML=codigo;
            }
        }
        function eliminarNota(over,id,nota,idLibreta,ciAlumno,ciProfesor,tipo,mes){
            if(over){
                document.getElementById("NOTA-"+id).innerHTML="<image src='images/eliminar.png' width='20%' onclick='eliminarNotaServidor("+id+","+idLibreta+","+ciAlumno+","+ciProfesor+","+tipo+","+mes+",event)'/>";
            }
            else{
                document.getElementById("NOTA-"+id).innerHTML=nota;
            }
        }
        function eliminarNotaServidor(idNota,idLibreta,ciAlumno,ciProfesor,tipo,mes,e){
           xmlhttp=new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                        var obj = jQuery.parseJSON( xmlhttp.responseText );
                        var msj = obj.msj;
                        if(msj[0].mensaje=="ok"){
                            var pNota=document.getElementById("NOTA-"+idNota);
                            pNota.removeChild(pNota.firstChild);
                            document.getElementById("grillaPromedios").innerHTML ="";
                            document.getElementById("mesPromedio").value=-1;
                        }
                        else{
                            document.getElementById("mensaje").innerHTML="<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+msj[0].mensaje;
                        }
                    };
                };
                xmlhttp.open("POST","Notas?eliminar="+idNota+"&idLibreta="+idLibreta+"&ciAlumno="+ciAlumno+"&ciProfesor="+ciProfesor+"&tipo="+tipo+"&mes="+mes);
                xmlhttp.send();
                e.stopPropagation();
                return false; 
        }
        function eliminarFalta(idFalta,idLibreta,ciAlumno,ciProfesor){
           xmlhttp=new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                        var obj = jQuery.parseJSON( xmlhttp.responseText );
                        var msj = obj.msj;
                        if(msj[0].mensaje=="ok"){
                            var pFalta=document.getElementById(idFalta);
                            pFalta.removeChild(pFalta.firstChild);
                            //alert(msj[0].cantHorasFalta);
                            var totalFaltas = document.getElementById("TOTAL-"+ciAlumno).innerHTML;
                            totalFaltas = totalFaltas - msj[0].cantHorasFalta;
                            if(totalFaltas==0){
                                totalFaltas="";
                            }
                            document.getElementById("TOTAL-"+ciAlumno).innerHTML=totalFaltas;
                        }
                        else{
                            document.getElementById("mensaje").innerHTML="<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+msj[0].mensaje;
                        }
                    };
                };
                xmlhttp.open("POST","HistorialFaltas?eliminar="+idFalta+"&idLibreta="+idLibreta+"&ciAlumno="+ciAlumno+"&ciProfesor="+ciProfesor);
                xmlhttp.send();
                return false; 
        }
        function eliminarSancion(idSancion,idLibreta,ciAlumno,ciProfesor){
           xmlhttp=new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                        var obj = jQuery.parseJSON( xmlhttp.responseText );
                        var msj = obj.msj;
                        if(msj[0].mensaje=="ok"){
                            var pSancion=document.getElementById(idSancion);
                            pFalta.removeChild(pSancion.firstChild);
                            //alert(msj[0].cantHorasFalta);
                        }
                        else{
                            document.getElementById("mensaje").innerHTML="<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+msj[0].mensaje;
                        }
                    };
                };
                xmlhttp.open("POST","HistorialFaltas?eliminar="+idSancion+"&idLibreta="+idLibreta+"&sancion=1&ciAlumno="+ciAlumno+"&ciProfesor="+ciProfesor);
                xmlhttp.send();
                return false; 
        }
        function agregarTemaTratado(form,idLibreta){
            var fecha=form.elements[0].value;
            var texto = form.elements[1].value;
            xmlhttp=new XMLHttpRequest();
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                    var obj = jQuery.parseJSON( xmlhttp.responseText );
                    var msj = obj.msj;
                    var cellNueva;
                    if(msj[0].mensaje=="ok"){
                        var tablaTemasTratados= document.getElementById("tablaTemasTratados");
                        var agregue= false;
                        for(var j=1;j<tablaTemasTratados.rows.length;j++){ 
                           if(tablaTemasTratados.rows[j].cells[1].innerHTML<=fecha){
                                var rowNueva=tablaTemasTratados.insertRow(j);
                                rowNueva.id=msj[0].id;
                                rowNueva.style="text-align:center;";
                                var cellNueva=rowNueva.insertCell(0);
                                cellNueva.innerHtml="";
                                cellNueva=rowNueva.insertCell(1);
                                cellNueva.innerText=fecha;
                                cellNueva=rowNueva.insertCell(2);
                                cellNueva.innerText=texto;
                                cellNueva=rowNueva.insertCell(3);
                                cellNueva.innerHTML="<a onclick=eliminarTemaTratado("+msj[0].id+","+ idLibreta +")><img src='images/eliminar.png' width='15%' /></a>";
                                agregue=true;
                                break;
                            };
                        };
                        if(!agregue){
                            var rowNueva=tablaTemasTratados.insertRow(tablaTemasTratados.rows.length);
                            rowNueva.id=msj[0].id;
                            rowNueva.style="text-align:center;";
                            var cellNueva=rowNueva.insertCell(0);
                            cellNueva.innerHtml="";
                            cellNueva=rowNueva.insertCell(1);
                            cellNueva.innerText=fecha;
                            cellNueva=rowNueva.insertCell(2);
                            cellNueva.innerText=texto;
                            cellNueva=rowNueva.insertCell(3);
                            cellNueva.innerHTML="<a onclick=eliminarTemaTratado("+msj[0].id+","+ idLibreta +")><img src='images/eliminar.png' width='15%' /></a>";
                        };
                        var color;
                        for(var j=1;j<tablaTemasTratados.rows.length;j++){ 
                            if ((j%2)==0){
                                    color="#ffff99";
                            }
                            else{
                                    color="#ccccff";
                            };
                            tablaTemasTratados.rows[j].style.backgroundColor=color;
                            tablaTemasTratados.rows[j].cells[0].innerText=j;
                        };
                        form.reset();
                    }
                    else{
                        document.getElementById("mensaje").innerHTML="<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+msj[0].mensaje;
                    }
                }
            };
            xmlhttp.open("POST","Libreta?id="+idLibreta+"&agregartematratado=1&fecha="+fecha+"&texto="+texto);
            xmlhttp.send();
            return false;
        }
        function eliminarTemaTratado(idTema, idLibreta){
            xmlhttp=new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                        var obj = jQuery.parseJSON( xmlhttp.responseText );
                        var msj = obj.msj;
                        if(msj[0].mensaje=="ok"){
                            document.getElementById(idTema).remove();
                            var color;
                            var tablaTemasTratados= document.getElementById("tablaTemasTratados");
                            for(var j=1;j<tablaTemasTratados.rows.length;j++){ 
                                if ((j%2)==0){
                                    color="#ffff99";
                                }
                                else{
                                    color="#ccccff";
                                };
                                 tablaTemasTratados.rows[j].style.backgroundColor=color;
                                 tablaTemasTratados.rows[j].cells[0].innerText=j;
                            }
                        }
                        else{
                            document.getElementById("mensaje").innerHTML="<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+msj[0].mensaje;
                        }
                    }
                };
                xmlhttp.open("POST","Libreta?elimTemaTratado=1&id="+idLibreta+"&idTema="+idTema);
                xmlhttp.send();
                return false;
        }
        function cambiarEstadoASancionar(boton){
            var input= document.getElementById("S-"+boton.value);
            var div = document.getElementById("DIVS-"+boton.value);
            var cf = document.getElementById("CS-"+boton.value);
            var causa = document.getElementById("CAUSA-"+boton.value);
            var MINTARDES = document.getElementById("MINTARDES-"+boton.value);
            if(input.value==1){
                boton.style="color: red";
                div.style="display:block";
                input.value=2;
                cf.required=true;
                MINTARDES.required=true;
                causa.required=true;
            }
            else{
                boton.style="color: green";
                div.style="display:none";
                input.value=1;
                cf.required=false;
                MINTARDES.required=false;
                causa.required=false;
            }
            return false;
        }
        function mostrarMesAgregarNota(mostrar){
            var trMesAgregarNota = document.getElementById("trMesAgregarNota");
            if(mostrar){
                trMesAgregarNota.style="";
            }
            else{
                trMesAgregarNota.style="display:none";
            }
        }
        function verificarSancionR(select){
            var mintardestr = document.getElementById("MINTARDESTR-"+select.id.substr(3,select.id.length));
            if(select.value=="R"){
                mintardestr.style="";
            }
            else{
                mintardestr.style="display:none";
            }
        }    
        function agregarNota(ci,tr){
            document.getElementById("formAgregarNota").reset();
            var trform= document.getElementById("trFotoNombre");
            trform.deleteCell(0);
            trform.deleteCell(0);
            trform.insertCell(0);
            trform.cells[0].innerHTML=tr.cells[0].innerHTML;
            trform.insertCell(1);
            trform.cells[1].innerHTML=tr.cells[1].innerHTML;
            document.getElementById("ciAgregarNota").value=ci;
            var tablaAgregarNota= document.getElementById("tablaAgregarNota");
            tablaAgregarNota.style="display:block";
            document.getElementById("valorAgregarNota").focus();
            
        }
        function agregarNotaServidor(form,idLibreta,ciProfesor){
            var valor="";
            var ci="";
            var tipo="";
            var mes = "";
            var obs="";
            for (var i=0;i<form.elements.length;i++)
            {
                if(form.elements[i].type!="submit" && (form.elements[i].type!="radio" || (form.elements[i].type=="radio" && form.elements[i].checked) )){
                    if(form.elements[i].name=="valorAgregarNota" && form.elements[i].value==""){
                        alert("ERROR: Debe ingresar un valor a la nota.");
                        return false;
                    }
                    else{
                        if(form.elements[i].name=="valorAgregarNota"){
                            valor = form.elements[i].value;
                        }
                        if(form.elements[i].name=="ciAgregarNota"){
                            ci= form.elements[i].value;
                        }
                        if(form.elements[i].name=="tipoAgregarNota"){
                            tipo= form.elements[i].value;
                        }
                        if(form.elements[i].name=="mesAgregarNota"){
                            mes= form.elements[i].value;
                        }
                        if(form.elements[i].name=="obsAgregarNota"){
                            obs= form.elements[i].value;
                        }
                    }
                }
            }  
            xmlhttp=new XMLHttpRequest();
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                    var obj = jQuery.parseJSON( xmlhttp.responseText );
                    var msj = obj.msj;
                    if(msj[0].mensaje=="ok"){
                        var auxTipo="O"+"-"+mes;
                        var txtMes="Mes correspondiente:"+mes+"&#10;";
                        if(tipo==1){
                            auxTipo="E"+"-"+mes;
                        }
                        else{
                            if(tipo==3){
                                txtMes="PRIMER PARCIAL&#10;";
                                auxTipo="PP";
                            }
                            else{
                                 if(tipo==4){
                                    txtMes="SEGUNDO PARCIAL&#10;";
                                    auxTipo="SP";
                                }
                            }
                        }
                        var trAgregar=document.getElementById(ci+"-"+auxTipo);
                        valorAux=valor;
                        if(!valor.includes('.')){
                            valorAux+=".0";
                        }
                        trAgregar.innerHTML += "<b "+txtMes+"title='Fecha alta: "+msj[0].fecha+"&#10;Nota: "+valorAux+"&#10;Observaciones: "+obs+"'><p id='NOTA-"+msj[0].id+"' onMouseleave=\"eliminarNota(false,"+msj[0].id+",'"+valorAux+"','"+idLibreta+"','"+ci+"','"+ciProfesor+"',"+tipo+","+mes+");\" onMouseEnter=\"eliminarNota(true,"+msj[0].id+",'"+valor+"','"+idLibreta+"','"+ci+"','"+ciProfesor+"',"+tipo+","+mes+");\">"+valorAux+"</p></b>";
                        document.getElementById("grillaPromedios").innerHTML ="";
                        document.getElementById("mesPromedio").value=-1;
                    }
                    else{
                        document.getElementById("mensaje").innerHTML="<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+msj[0].mensaje;
                    }
                };
            };
            xmlhttp.open("POST","Notas?ciAlumno="+ci+"&tipo="+tipo+"&mes="+mes+"&valor="+valor+"&obs="+obs+"&idLibreta="+idLibreta+"&ciProfesor="+ciProfesor);
            xmlhttp.send();
            form.reset();
            var tablaAgregarNota= document.getElementById("tablaAgregarNota");
            tablaAgregarNota.style="display:none";
            return false; 
        }
        function cambiarGrillaPromedio(idLibreta,ciProfesor){
            var mesPromedio = document.getElementById("mesPromedio").value;
            xmlhttp=new XMLHttpRequest();
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                    var obj = jQuery.parseJSON( xmlhttp.responseText );
                    var msj = obj.msj;
                    var grillaPromedios = document.getElementById("grillaPromedios");
                    grillaPromedios.innerHTML = msj[0].html;
                };
            };
            xmlhttp.open("POST","Promedios?cambiarGrilla=1&idLibreta="+idLibreta+"&ciProfesor="+ciProfesor+"&mes="+mesPromedio);
            xmlhttp.send();
        }
        function cambiarProfesor(select){
            document.getElementById("editarProfesor").href="profesor.jsp?id="+select.value;
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
        d= mp.getLibretas().get(id);
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
    if(u.isAdmin() || u.isNotas()){
%>
        <form method="post"  name="formulario" id="formulario"  onsubmit="return verificarFormulario();" action="Libreta?id=<%if (d!=null){out.print(d.getId());}else{out.print("-1");}%>">
            <table  width='50%' align='center' style="text-align: left;" id="tablaformulario">
                 <tr>
                    <td>Profesor: </td>
                    <td>
                        <select name="profesor" form="formulario" required="required" id="profesor" onchange="cambiarProfesor(this);">
                            <option value="-1" disabled="disabled" selected="selected" ></option>
                            <%
                            Manejadores.ManejadorProfesores mprof = ManejadorProfesores.getInstance();
                            int ci=-1;
                            for(Profesor prof: mprof.getProfesores() ){
                                String s="";
                                if(d!=null && d.getProfesor()!=null && d.getProfesor().getCi()==prof.getCi()){
                                    s="selected";
                                    ci=d.getProfesor().getCi();
                                }
                                String grado="";
                                if(prof.getGrado()!=null){
                                    grado=prof.getGrado().getAbreviacion()+" ";
                                }
                                out.print("<option " + s +" value='"+String.valueOf(prof.getCi()) +"'>"+grado + prof.getPrimerApellido()+" "+prof.getPrimerNombre()+"</option>");
                            }
                            %>
                         </select>
                         <a target="_blank" id="editarProfesor" href="profesor.jsp?id=<%= ci %>">VER</a>
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
    if(u.isAdmin()|| u.isNotas() ||(u.isProfesor()&& u.getCiProfesor()==d.getProfesor().getCi())){
        if(u.isAdmin()||u.isNotas()){
            %>
            <form method="post"  name="formulario1" id="formulario1" action="Libreta?id=<%=d.getId()%>" style="padding-bottom: 5%">
            <table  width='30%' align='center' style="text-align: left;" >
                 <tr>
                    <td>Profesor: </td>
                    <td>
                        <select name="profesor" form="formulario1" required="required" onchange="cambiarProfesor(this);">
                            <%
                            int ci=-1;
                            Manejadores.ManejadorProfesores mprof = ManejadorProfesores.getInstance();
                            for(Profesor prof: mprof.getProfesores() ){
                                String s="";
                                if(d!=null && d.getProfesor()!=null && d.getProfesor().getCi()==prof.getCi()){
                                    s="selected";
                                    ci= d.getProfesor().getCi();
                                }
                                out.print("<option " + s +" value='"+String.valueOf(prof.getCi()) +"'>"+ prof.obtenerNombreCompleto() +"</option>");
                            }
                            %>
                         </select>
                         <a target="_blank" id="editarProfesor" href="profesor.jsp?id=<%= ci %>">VER</a>
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
                <tr>
                    <td colspan="2">
                        <p align='right'><input type="submit"  value="Modificar" /></p>
                    </td>
                </tr>
            </table>
            
            </form>
            
            
        <%
        }
        else{
          %>
            <table  width='30%' align='center' style="text-align: left" >
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
            
                <ul id="tabs">
                    <li><a href="#" title="PasarLista"><b>Pasar Lista</b></a></li>
                    <li><a href="#" title="sanciones"><b>Sancionar</b></a></li>
                    <li><a href="#" title="historiaDeInasistencias"><b>Historial FALTAS/SANCIONES</b></a></li>
                    <li><a href="#" title="temasTratados"><b>Temas Tratados</b></a></li>
                    <li><a href="#" title="notas"><b>Notas</b></a></li>
                    <li><a href="#" title="promedios"><b>Promedios</b></a></li>
                    
                </ul>
                <div id="content">
                    <div id="PasarLista">
                    <form method="post"  name="pasarLista" id="pasarLista" onsubmit="return validarFormPasarLista(this)" action="Libreta?id=<%=d.getId()%>&pasarLista=1">

                        <p align="right">
                            <button form="pasarLista" style="background-color: #ff6600; border-radius: 15px; color: #ffffff; font-size: large;">&nbsp;FINALIZAR&nbsp;</button>
                       </p>
                       <p>
                        Seleccionar fecha:
                       <input type="date"  <%
                       
                        java.util.Calendar fecha1 = java.util.Calendar.getInstance();
                        int mes = fecha1.get(java.util.Calendar.MONTH)+1;
                        int dia = fecha1.get(java.util.Calendar.DATE);
                        String cero="",cerod="";
                        if(mes<10){
                            cero="0";
                        }
                        if(dia<10){
                            cerod="0";
                        }
                        out.print("value=\""+  fecha1.get(java.util.Calendar.YEAR)+"-"+cero+mes+"-"+cerod+dia+"\"");
                       
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
                                <a href="Listar?List[]=<%=p.getAlumno().getCi()%>&fichas=1&tipoPersonal=1" target="_blank">   <p align="center"><label for="uploadImage" ><img src="<%=request.getContextPath()%>/Imagenes?foto=<%=p.getAlumno().getCi() %>" id="uploadPreview" style="width: 50px; height: 65px;" onclick=""/></label></p></a>
                                <%
                                }
                                else{
                                %>

                                <a href="Listar?List[]=<%=p.getAlumno().getCi()%>&fichas=1&tipoPersonal=1" target="_blank"> <p align="center"><label for="uploadImage" ><img src="images/silueta.jpg" id="uploadPreview" style="width: 50px; height: 65px;" onclick=""/></label></p></a>
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
                                                <input  name="OBS-<%=p.getAlumno().getCi()%>"  type="text" value="Observaciones" style="color:#999999" onClick="if(this.value=='Observaciones'){this.value='';this.style.color='#000000';}" onblur="if(this.value==''){this.value='Observaciones';this.style.color='#999999';}"/>
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
                </form>
      
                     </div>
                    <div id="sanciones">
                    <form method="post"  name="sancionar" id="sancionar" onsubmit="return validarFormSancionar(this)" action="Libreta?id=<%=d.getId()%>&sancionar=1">

                        <p align="right">
                            <button form="sancionar" style="background-color: #ff6600; border-radius: 15px; color: #ffffff; font-size: large;">&nbsp;FINALIZAR&nbsp;</button>
                       </p>
                       <p>
                        Seleccionar fecha:
                       <input type="date"  <%
                       
                       out.print("value=\""+  fecha1.get(java.util.Calendar.YEAR)+"-"+cero+mes+"-"+cerod+dia+"\"");
                       
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
                            i=0;
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
                                <a href="Listar?List[]=<%=p.getAlumno().getCi()%>&fichas=1&tipoPersonal=1" target="_blank">   <p align="center"><label for="uploadImage" ><img src="<%=request.getContextPath()%>/Imagenes?foto=<%=p.getAlumno().getCi() %>" id="uploadPreview" style="width: 50px; height: 65px;" onclick=""/></label></p></a>
                                <%
                                }
                                else{
                                %>

                                <a href="Listar?List[]=<%=p.getAlumno().getCi()%>&fichas=1&tipoPersonal=1" target="_blank"> <p align="center"><label for="uploadImage" ><img src="images/silueta.jpg" id="uploadPreview" style="width: 50px; height: 65px;" onclick=""/></label></p></a>
                                <%
                                }
                                out.print("</td>"
                                   +"<td style='width: 10%' align='center'>"+p.getAlumno().getGrado().getAbreviacion()+"</td>"
                                   +"<td style='width: 10%' align='center'>"+p.getAlumno().getPrimerApellido()+"</td>"
                                   +"<td style='width: 10%' align='center'>"+ p.getAlumno().getPrimerNombre() +"</td>"
                                    +"<td style='width: 5%' align='center'>");
                                if(p.isActivo()){
                                out.print("<button width=100% value=\""+p.getAlumno().getCi()+"\" onclick=\"return cambiarEstadoASancionar(this);\" style=\"color: green\">Sancionar</button><input name=\"S-"+p.getAlumno().getCi()+"\" id=\"S-"+p.getAlumno().getCi()+"\" type=\"number\" value=\"1\" hidden=\"hidden\"/>");
                                %>
                                <div style="display:none" id="DIVS-<%= p.getAlumno().getCi() %>">
                                    <table>
                                        <tr>
                                            <td>
                                                TIPO:
                                            </td>
                                            <td>
                                                <select name="CS-<%=p.getAlumno().getCi()%>" form="sancionar" id="CS-<%=p.getAlumno().getCi()%>"  onchange="verificarSancionR(this);">
                                                    <option value="-1" disabled="disabled" selected hidden>Seleccionar tipo de sanci&oacute;n:</option>
                                                    <option value="M">M - Amonestaci&oacute;n</option>
                                                    <option value="R">R - Llegada Tarde</option>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr style="display:none" id="MINTARDESTR-<%=p.getAlumno().getCi()%>" width="100%" >
                                            <td>
                                                Minutos tardes:
                                            </td>
                                            <td>
                                                <input id="MINTARDES-<%=p.getAlumno().getCi()%>" name="MINTARDES-<%=p.getAlumno().getCi()%>"  value="0" type="number" min="0" step="1"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                Causa:
                                            </td>
                                            <td>
                                                <input  id="CAUSA-<%=p.getAlumno().getCi()%>" name="CAUSA-<%=p.getAlumno().getCi()%>"  type="text" value="CAUSA" style="color:#999999" onClick="if(this.value=='CAUSA'){this.value='';this.style.color=#000000;}" onblur="if(this.value==''){this.value='CAUSA';this.style.color=#999999;}"/>
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
                    </form>
                    </div>
                    <div id="historiaDeInasistencias">
                            <select onchange="cambiarGrilla(this,<%=d.getId()%>);">
                                <% if (!d.getMateria().isSemestral()||(d.getMateria().isSemestral()&& d.getMateria().getSemestre()==1)){ %>
                                <option value="3" <% if(mes==3){out.print("selected");} %> > MARZO </option>
                                <option value="4" <% if(mes==4){out.print("selected");} %> > ABRIL </option>
                                <option value="5" <% if(mes==5){out.print("selected");} %> > MAYO </option>
                                <option value="6" <% if(mes==6){out.print("selected");} %> > JUNIO </option>
                                <%}
                                 if (!d.getMateria().isSemestral()||(d.getMateria().isSemestral()&& d.getMateria().getSemestre()==2)){%>
                                <option value="7" <% if(mes==7){out.print("selected");} %> > JULIO </option>
                                <option value="8" <% if(mes==8){out.print("selected");} %> > AGOSTO </option>
                                <option value="9" <% if(mes==9){out.print("selected");} %> > SETIEMBRE </option>
                                <option value="10" <% if(mes==10){out.print("selected");} %> > OCTUBRE </option>
                                <%}%>
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
                                    HashMap<Integer,LinkedList<FaltaSancion>> grilla;
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
                                        
                                        grilla=l.getGrillaFaltasSanciones().get(mes);
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
                                                    for(FaltaSancion f:grilla.get(j)){
                                                        if(f.getFalta()!=null){
                                                            out.print("<b title='Fecha: "+f.getFalta().getFecha()+"&#10;C&oacute;digo: "+f.getFalta().getCodigoMotivo()+"&#10;Cantidad de horas: "+f.getFalta().getCanthoras()+"&#10;Observaciones: "+f.getFalta().getObservaciones()+"'    ><p id='"+f.getFalta().getId()+"' onMouseleave=\"eliminar(true,false,"+f.getFalta().getId()+",'"+f.getFalta().getCodigoMotivo()+"','"+d.getId()+"','"+l.getAlumno().getCi()+"','"+d.getProfesor().getCi()+"');\" onMouseEnter=\"eliminar(true,true,"+f.getFalta().getId()+",'"+f.getFalta().getCodigoMotivo()+"','"+d.getId()+"','"+l.getAlumno().getCi()+"','"+d.getProfesor().getCi()+"');\">"+f.getFalta().getCodigoMotivo()+"</p></b>");
                                                            total+=f.getFalta().getCanthoras();
                                                        }
                                                        else{
                                                            String tipo="M";
                                                            String tipo1="M";
                                                            if(f.getSancion().getTipo()==2){
                                                                tipo="R&#10;Minutos tardes: "+f.getSancion().getMinutosTardes();
                                                                tipo1="R"; 
                                                            }
                                                            out.print("<b title='Fecha: "+f.getSancion().getFecha()+"&#10;C&oacute;digo: "+tipo+"&#10;Causa "+f.getSancion().getCausa()+"'    ><p id='"+f.getSancion().getId()+"' onMouseleave=\"eliminar(false,false,"+f.getSancion().getId()+",'"+tipo1+"','"+d.getId()+"','"+l.getAlumno().getCi()+"','"+d.getProfesor().getCi()+"');\" onMouseEnter=\"eliminar(false,true,"+f.getSancion().getId()+",'"+tipo1+"','"+d.getId()+"','"+l.getAlumno().getCi()+"','"+d.getProfesor().getCi()+"');\">"+tipo1+"</p></b>");
                                                        }
                                                    }
                                                    
                                                }
                                                
                                                out.print("</td>");
                                            }
                                           if(total!=0){
                                           out.print("<td id=TOTAL-"+l.getAlumno().getCi()+">"+total+"</td>");
                                           }
                                           else{
                                               out.print("<td id=TOTAL-"+l.getAlumno().getCi()+"></td>");
                                           }
                                        }
                                        
                    out.print("</tr>");
                                    
                                   }
                            
                                %>
                            </table>

                        </div>
                            <div id="temasTratados">
                        <form method="post"  name="formTemasTratados" id="formTemasTratados"  onsubmit="return agregarTemaTratado(this,<%= d.getId() %>)" action="" >
                            <table>
                                <tr>
                                    <td>Seleccione la fecha:</td>
                                    <td><input type="date"  <%
                        
                            out.print("value=\""+  fecha1.get(java.util.Calendar.YEAR)+"-"+cero+mes+"-"+cerod+dia+"\"");
                       
                        %> id="fecha" name="fecha"></td>
                                </tr>
                                <tr>
                                    <td>Temas:</td>
                                    <td>
                                        <textarea cols="50" rows="5" name="texto" id="texto"  form="formTemasTratados"></textarea>
                                    </td>
                                </tr>    
                                <tr>
                                    <td>
                                        
                                    </td>
                                    <td><input type="submit" value="Agregar"/>  </td>
                                </tr>  
                            </table>
                        </form>
                        <%
                                    
                out.print("<table style='width: 100%;' id='tablaTemasTratados'>"
                        + "<tr style='background-color:#ffcc66'>"
                            +"<td style='width: 5%' align='center'></td>"
                            +"<td style='width: 10%' align='center'>Fecha</td>"
                            +"<td style='width: 10%' align='center'>Texto</td>");
                out.print(  "<td style='width: 5%' align='center'>Elim</td>"  ); 
                out.print("</tr>" );
                i=0;
                for (  TemaTratado t : d.getTemasTratados()){
                    if ((i%2)==0){
                        color=" #ccccff";
                    }
                    else{
                        color=" #ffff99";
                    }
                    i++;

                   out.print("<tr style='background-color:"+color+"' id='"+t.getId()+"'>"
                   +"<td style='width: 5%' align='center'>"+i+"</td>"
                   +"<td style='width: 10%' align='center'>"+t.getFecha()+"</td>"
                   +"<td style='width: 10%' align='center'>"+t.getTexto()+"</td>");
                    out.print("<td style='width: 5%' align='center' ><a onclick=eliminarTemaTratado("+t.getId()+","+d.getId()+")><img src='images/eliminar.png' width='15%' /></a></td>"
                   +"</tr>");
                }
                out.print("</table>");
            %> 
                    </div>
                    <div id="notas">
                        <form id='formAgregarNota' method="post" onsubmit="return agregarNotaServidor(this,<%= d.getId() %>, <%= d.getProfesor().getCi() %>);"/>
                        <table id='tablaAgregarNota' style="display:none">
                            <tr style="display:none">
                                <td>
                                    CI:
                                </td>
                                <td>
                                    <input type="number" id='ciAgregarNota' name='ciAgregarNota'/>
                                </td>
                            </tr>
                            <tr id="trFotoNombre">
                                <td>
                                </td>
                                <td>
                                    
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    TIPO:
                                </td>
                                <td>
                                    <input type="radio" onclick="mostrarMesAgregarNota(true);" name="tipoAgregarNota" checked="checked" value="2">Oral<br>
                                    <input type="radio" onclick="mostrarMesAgregarNota(true);" name="tipoAgregarNota" value="1">Escrito<br>
                                    <%
                                    if(d.getMateria().isSecundaria()){
                                    %>
                                    <input type="radio" onclick="mostrarMesAgregarNota(false);" name="tipoAgregarNota" value="3">1er Parcial<br>
                                    <input type="radio" onclick="mostrarMesAgregarNota(false);" name="tipoAgregarNota" value="4">2do Parcial<br>
                                    <%
                                    }
                                    %>
                                </td>
                            </tr>
                            <tr id="trMesAgregarNota">
                                <td>
                                    MES:
                                </td>
                                <td>
                                    <select form="formAgregarNota" name='mesAgregarNota' />
                                    <%
                                    if (!d.getMateria().isSemestral()||(d.getMateria().isSemestral()&& d.getMateria().getSemestre()==1)){
                                        if(!d.getMesesCerrados().containsKey(3)){
                                        %>
                                        <option value="3" <% if(mes==3){out.print("selected");} %>>MARZO</option>
                                        <%
                                        }
                                        if(!d.getMesesCerrados().containsKey(4)){
                                        %>
                                        <option value="4" <% if(mes==4){out.print("selected");} %>>ABRIL</option>
                                        <%
                                        }
                                        if(!d.getMesesCerrados().containsKey(5)){
                                        %>
                                        <option value="5" <% if(mes==5){out.print("selected");} %>>MAYO</option>
                                        <%
                                        }
                                        if(!d.getMesesCerrados().containsKey(6)){
                                        %>
                                        <option value="6" <% if(mes==6){out.print("selected");} %>>JUNIO</option>
                                        <%
                                        }
                                    }
                                    if (!d.getMateria().isSemestral()||(d.getMateria().isSemestral()&& d.getMateria().getSemestre()==2)){
                                        if(!d.getMesesCerrados().containsKey(7)){
                                        %>
                                        <option value="7" <% if(mes==7){out.print("selected");} %>>JULIO</option>
                                        <%
                                        }
                                        if(!d.getMesesCerrados().containsKey(8)){
                                        %>
                                        <option value="8" <% if(mes==8){out.print("selected");} %>>AGOSTO</option>
                                        <%
                                        }
                                        if(!d.getMesesCerrados().containsKey(9)){
                                        %>
                                        <option value="9" <% if(mes==9){out.print("selected");} %>>SETIEMBRE</option>
                                        <%
                                        }
                                        if(!d.getMesesCerrados().containsKey(10)){
                                        %>
                                        <option value="10" <% if(mes==10){out.print("selected");} %>>OCTUBRE</option>
                                        <%
                                        }
                                    }
                                    %>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                   VALOR:
                                </td>
                                <td>
                                    <input id='valorAgregarNota' step="0.01" min="1" max="<% if(d.getMateria().isSecundaria()){out.print("12");}else{out.print("10");}; %>"type="number" name="valorAgregarNota"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                   OBS.:
                                </td>
                                <td>
                                    <input id='obsAgregarNota' type="text" name="obsAgregarNota"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" style="text-align: right">
                                    <input type="submit" value='Guardar'/>
                                </td>    
                            </tr>
                        </table> 
                        </form>
                        <table id="grillaNotas" style=" width: 100%; overflow-x: scroll;border-collapse: separate;border-spacing: 2px;text-align: center;vertical-align: central;">
                                
                                <tr style='background-color:#ffcc66;padding:0px;'>
                                    <td></td>
                                    <td>Alumnos:</td>
                                    <% if (!d.getMateria().isSemestral()||(d.getMateria().isSemestral()&& d.getMateria().getSemestre()==1)){ %>
                                    <td colspan="3" style="width: 10%">MARZO</td>
                                    <td colspan="3" style="width: 10%">ABRIL</td>
                                    <td colspan="3" style="width: 10%">MAYO</td>
                                    <td colspan="3" style="width: 10%">JUNIO</td>
                                    <%
                                        if(d.getMateria().isSecundaria()){
                                            %> 
                                            <td title="Primera evaluaci&oacute;n especial" style="width: 3%"> 1&deg;EE</td>
                                            <td style="width: 3%">1&deg;R</td>
                                            
                                               <%
                                        }
                                    }
                                    if (!d.getMateria().isSemestral()||(d.getMateria().isSemestral()&& d.getMateria().getSemestre()==2)){
                                    %>
                                    <td colspan="3" style="width: 10%">JULIO</td>
                                    <td colspan="3" style="width: 10%">AGOSTO</td>
                                    <td colspan="3" style="width: 10%">SETIEMBRE</td>
                                    <td colspan="3" style="width: 10%">OCTUBRE</td>
                                    <%
                                        if(d.getMateria().isSecundaria()){
                                            %> 
                                            <td title="Segunda evaluaci&oacute;n especial" style="width: 3%"> 2&deg;EE</td>
                                            <td style="width: 3%">2&deg;R</td>
                                               <%
                                        }
                                    }
                                    if(!d.getMateria().isSecundaria()){
                                        %>
                                             <td style="width: 3%">PA</td>  
                                        <%
                                    }
                                    %>
                                </tr>
                                <tr style='background-color:#ffcc66;padding:0px;'>
                                    <td></td>
                                    <td></td>
                                    <% if (!d.getMateria().isSemestral()||(d.getMateria().isSemestral()&& d.getMateria().getSemestre()==1)){ %>
                                    
                                    <td>O</td><td>E</td><td>P</td>
                                    <td>O</td><td>E</td><td>P</td>
                                    <td>O</td><td>E</td><td>P</td>
                                    <td>O</td><td>E</td><td>P</td>
                                    <%
                                         if(d.getMateria().isSecundaria()){
                                            %>
                                            <td>E</td>
                                            <td>P</td>
                                               <%
                                        }
                                    }
                                    if (!d.getMateria().isSemestral()||(d.getMateria().isSemestral()&& d.getMateria().getSemestre()==2)){
                                    %>
                                    
                                    <td>O</td><td>E</td><td>P</td>
                                    <td>O</td><td>E</td><td>P</td>
                                    <td>O</td><td>E</td><td>P</td>
                                    <td>O</td><td>E</td><td>P</td>
                                    <%
                                    if(d.getMateria().isSecundaria()){
                                            %>
                                            <td>E</td>
                                            <td>P</td>
                                               <%
                                        }
                                    }
                                    if(!d.getMateria().isSecundaria()){
                                        %>
                                        <td>P</td>     
                                        <%
                                    }
                                    %>
                                </tr>
                                    
                                    <%
                                    i=0;
                                    for(LibretaIndividual l: d.getLibretasIndividuales().values()){
                                        if ((i%2)==0){
                                            color=" #ccccff";
                                        }
                                        else{
                                            color=" #ffff99";
                                        }
                                        i++;
                    out.print("<tr style=\"background-color:"+color+"; cursor: crosshair \" onclick='agregarNota("+l.getAlumno().getCi()+",this)' title='Para agregar una nota a "+ l.getAlumno().getPrimerApellido()+ " " + l.getAlumno().getPrimerNombre() +" hacer click'>" );
                        out.print("<td>");
                            if(l.getAlumno().getFoto()!=""){
                                %>
                                <a href="Listar?List[]=<%=l.getAlumno().getCi()%>&fichas=1&tipoPersonal=1" target="_blank">   <p align="center"><label for="uploadImage" ><img src="<%=request.getContextPath()%>/Imagenes?foto=<%=l.getAlumno().getCi() %>" id="uploadPreview" style="width: 50px; height: 65px;" onclick=""/></label></p></a>
                                <%
                                }
                                else{
                                %>

                                <a href="Listar?List[]=<%=l.getAlumno().getCi()%>&fichas=1&tipoPersonal=1" target="_blank"> <p align="center"><label for="uploadImage" ><img src="images/silueta.jpg" id="uploadPreview" style="width: 50px; height: 65px;" onclick=""/></label></p></a>
                                <%
                                }
                            out.print("</td>");
                            out.print( "<td>"
                                + l.getAlumno().getPrimerApellido()+ " " + l.getAlumno().getPrimerNombre() 
                            +"</td>");
                            l.imprimirGrillaNotas(out);
                                   }
                            
                                %>
                            </table>
                    </div>
                    <div id="promedios">
                        <p align="right">
                            <%
                            if(d.getMateria().isSecundaria()){
                            %>
                            <button onclick="guardarPromedios();" style="background-color: #ff6600; border-radius: 15px; color: #ffffff; font-size: large;">&nbsp;GUARDAR CAMBIOS&nbsp;</button>
                            <%
                            }
                            %>
                            <button onclick="cerrarMesPromedios();" style="background-color: #cd0a0a; border-radius: 15px; color: #ffffff; font-size: large;">&nbsp;CERRAR MES&nbsp;</button>
                        </p>
                        <form id='formGuardarPromedios' method="post" action="Promedios?idLibreta=<%= d.getId() %>,&ciProfesor=<%= d.getProfesor().getCi() %>" >
                        <table>
                            <tr>
                                <td>
                                    MES:
                                </td>
                                <td>
                                    <select  id="mesPromedio" name='mesPromedio' onchange="cambiarGrillaPromedio(<%= d.getId() %>, <%= d.getProfesor().getCi() %>);"/>
                                        <option value="-1" disabled="disabled" selected hidden>Seleccionar promedio a cerrar:</option>
                                    <%
                                    
                                    if (!d.getMateria().isSemestral()||(d.getMateria().isSemestral()&& d.getMateria().getSemestre()==1)){
                                        if(!d.getMesesCerrados().containsKey(3)){
                                        %>
                                        <option value="3">MARZO</option>
                                        <%
                                        }
                                        if(!d.getMesesCerrados().containsKey(4)){
                                        %>
                                        <option value="4">ABRIL</option>
                                        <%
                                        }
                                        if(!d.getMesesCerrados().containsKey(5)){
                                        %>
                                        <option value="5" >MAYO</option>
                                        <%
                                        }
                                        if(!d.getMesesCerrados().containsKey(6)){
                                        %>
                                        <option value="6" >JUNIO</option>
                                        <%
                                        }
                                        if(d.getMateria().isSecundaria() && !d.isCerradaPrimeraReunion()){
                                            %>
                                            <option value="11" >PRIMERA REUNI&Oacute;N</option>
                                            <%
                                        }
                                        if(d.getMateria().isSemestral()&& d.getMateria().getSemestre()==1){
                                            %>
                                            <option value="12" >PROMEDIO ANUAL</option>
                                            <%
                                        }
                                    }
                                    if (!d.getMateria().isSemestral()||(d.getMateria().isSemestral()&& d.getMateria().getSemestre()==2)){
                                        if(!d.getMesesCerrados().containsKey(7)){
                                        %>
                                        <option value="7" >JULIO</option>
                                        <%
                                        }
                                        if(!d.getMesesCerrados().containsKey(8)){
                                        %>
                                        <option value="8" >AGOSTO</option>
                                        <%
                                        }
                                        if(!d.getMesesCerrados().containsKey(9)){
                                        %>
                                        <option value="9" >SETIEMBRE</option>
                                        <%
                                        }
                                        if(!d.getMesesCerrados().containsKey(10)){
                                        %>
                                        <option value="10" >OCTUBRE</option>
                                        <%
                                        }
                                        if(d.getMateria().isSecundaria()){
                                            %>
                                            <option value="13" >SEGUNDA REUNI&Oacute;N</option>
                                            <%
                                        }
                                        else{
                                            %>
                                            <option value="14" >PROMEDIO ANUAL</option>
                                            <%
                                        }
                                    }
                                    %>
                                </td>
                                
                            </tr>
                            
                        </table>
                        <table id="grillaPromedios" style="text-align: center;">
                            
                            
                        </table>
                        </form>
                    </div>
                    
                    
                </div> 
                     
        <%
    }
    else{
        sesion.setAttribute("Mensaje","Lo sentimos, no tiene permisos para acceder a esta p&aacute;gina. Contacte al administrador.");
     response.sendRedirect("");
    }
}

%>

<%@ include file="footer.jsp" %>