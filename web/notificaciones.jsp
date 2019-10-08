<%-- 
    Document   : notificaciones
    Created on : May 4, 2019, 5:32:24 AM
    Author     : Gisel
--%>

<%@page import="Classes.Bedelia.Notificacion"%>
<%@page import="java.util.LinkedList"%>
<%@page import="Manejadores.ManejadorBedelia"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>   
<% 
    if(u!=null && (u.isAdmin() || u.isNotas())){
%>
    <script src="js/jquery-1.9.1.min.js"></script>
    <script src="js/jquery-ui.js"></script>
    <script>
            $(document).ready(function() {
                     $("#content div").hide();
                     $("#tabs li:first").attr("id","current");
                     $("#content div:first").fadeIn();
                     $("#loader").fadeOut();

                 $('#tabs a').click(function(e) {
                     e.preventDefault();
                     $("#content div").hide();
                     $("#tabs li").attr("id","");
                     $(this).parent().attr("id","current");
                     $('#' + $(this).attr('title')).fadeIn();
                 });
             });
            function marcarLeido(leido,id){
                xmlhttp=new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                        var obj = jQuery.parseJSON( xmlhttp.responseText );
                        var msj = obj.msj;
                        if(msj[0].mensaje=="ok"){
                            var tablaOrigen;var tablaDestino;
                            if(leido){
                                tablaOrigen= document.getElementById("nuevas");
                                tablaDestino= document.getElementById("leidas");
                            }
                            else{
                                tablaDestino = document.getElementById("nuevas");
                                tablaOrigen= document.getElementById("leidas");
                            }
                            var row;
                            var elimine=false;
                            var ultimo=false;
                            for(var j=1;j<tablaOrigen.rows.length;j++){ 
                                if(tablaOrigen.rows[j].cells[1].innerHTML==id){
                                    row=tablaOrigen.rows[j];
                                    ultimo=j==(tablaOrigen.rows.length-1);
                                    tablaOrigen.deleteRow(j);
                                    elimine=true;
                                };
                                if (elimine&&!ultimo){
                                   if(tablaOrigen.rows[j].style.backgroundColor=="rgb(204, 204, 255)"){
                                       tablaOrigen.rows[j].style.backgroundColor="#ffff99";
                                   }
                                   else{
                                       tablaOrigen.rows[j].style.backgroundColor="#ccccff";
                                   };
                                };
                            }
                            var agregue=false;
                            for(var j=1;j<tablaDestino.rows.length;j++){ 
                               if(tablaDestino.rows[j].cells[2].innerHTML<=row.cells[2].innerHTML){
                                    var rowNueva=tablaDestino.insertRow(j);
                                    rowNueva.title=row.title;
                                    for(var k=0;k<row.cells.length;k++){
                                        cellNueva=rowNueva.insertCell(k);
                                        if(k<9 || k==10){
                                            cellNueva.innerHTML=row.cells[k].innerHTML;
                                        }
                                        if(k==9){
                                            var src;
                                            if(leido){
                                                src="nuevo";
                                            }
                                            else{
                                                src="leido";
                                            };
                                            cellNueva.innerHTML="<a onclick='marcarLeido("+!leido+","+id+")'><img src='images/"+src+".png' width='60%' /></a></td>";
                                        }
                                        if(k==11){
                                            cellNueva.innerHTML="<a onclick='eliminar("+!leido+","+id+")'><img src='images/eliminar.png' width='60%' /></a></td>";
                                        }
                                    }
                                    agregue=true;
                                    break;
                               };
                            }
                            if(!agregue){
                                var rowNueva=tablaDestino.insertRow(-1);
                                rowNueva.title=row.title;
                                var cellNueva;
                                for(var k=0;k<row.cells.length;k++){
                                    cellNueva=rowNueva.insertCell(k);
                                    if(k<9 || k==10){
                                        cellNueva.innerHTML=row.cells[k].innerHTML;
                                    }
                                    if(k==9){
                                        var src;
                                        if(leido){
                                            src="nuevo";
                                        }
                                        else{
                                            src="leido";
                                        };
                                        cellNueva.innerHTML="<a onclick='marcarLeido("+!leido+","+id+")'><img src='images/"+src+".png' width='60%' /></a></td>";
                                    }
                                    if(k==11){
                                        cellNueva.innerHTML="<a onclick='eliminar("+!leido+","+id+")'><img src='images/eliminar.png' width='60%' /></a></td>";
                                    }
                                }
                            }
                            var color;
                            for(var j=1;j<tablaDestino.rows.length;j++){ 
                                if ((j%2)==0){
                                    color="#ffff99";
                                }
                                else{
                                    color="#ccccff";
                                };
                                 tablaDestino.rows[j].style.backgroundColor=color;
                                 tablaDestino.rows[j].align='center';
                            }
                        }
                        else{
                            document.getElementById("mensaje").innerHTML="<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+msj[0].mensaje;
                        }
                    }
                };
                xmlhttp.open("POST","Notificaciones?marcarLeido="+leido+"&id="+id);
                xmlhttp.send();
                return false;
             }
            //////////////////////////////////////
            function eliminar(leido,id){
                xmlhttp=new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                        var obj = jQuery.parseJSON( xmlhttp.responseText );
                        var msj = obj.msj;
                        if(msj[0].mensaje=="ok"){
                            var tablaOrigen;
                            if(leido){
                                tablaOrigen= document.getElementById("leidas");
                            }
                            else{
                                tablaOrigen= document.getElementById("nuevas");
                            }
                            //json eliminar
                            var elimine=false;
                            for(var j=0;j<tablaOrigen.rows.length;j++){ 
                                if(tablaOrigen.rows[j].cells[1].innerHTML==id){
                                    tablaOrigen.deleteRow(j);
                                    elimine=true;
                                };
                                if (elimine){
                                   if(tablaOrigen.rows[j].style.backgroundColor=="rgb(204, 204, 255)"){
                                       tablaOrigen.rows[j].style.backgroundColor="#ffff99";
                                   }
                                   else{
                                       tablaOrigen.rows[j].style.backgroundColor="#ccccff";
                                   };
                                };
                            }
                        }
                        else{
                            document.getElementById("mensaje").innerHTML="<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+msj[0].mensaje;
                        }
                    }
                };
                xmlhttp.open("POST","Notificaciones?eliminar="+leido+"&id="+id);
                xmlhttp.send();
                return false;
             }
    </script>
    
    <p id="mensaje" style="color: #ffffff"><% if(sesion.getAttribute("Mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+sesion.getAttribute("Mensaje"));}%></p>
<%
    sesion.setAttribute("Mensaje",null);
%>
<p align="left"><a href="javascript:history.go(-1)"><img src="images/atras.png" width="15%"/></a></p>
    <ul id="tabs">
        <li><a href="#"><b>NOTIFICACIONES</b></a></li>
    </ul>
    <div id="loader" style="z-index: 50;position: fixed; top:0; left:0; width:100%; height: 100%;background: url('images/loading-verde.gif') center center no-repeat; background-size: 10%"></div>
    <div id="content">
        <div>
                
            <%
            ManejadorBedelia mp = ManejadorBedelia.getInstance();
            %>   

            <h3>
                Notificaciones Nuevas:
            </h3>
            <%
                out.print("<table style='width: 100%;' id='nuevas'>"
                        + "<tr style='background-color:#ffcc66'>"
                            +"<td style='width: 5%' align='center'></td>"
                        +"<td style='width: 10%' align='center'>ID</td>"    
                        +"<td style='width: 10%' align='center'>Fecha</td>"
                            +"<td style='width: 10%' align='center'>Materia</td>"
                            +"<td style='width: 10%' align='center'>Curso</td>"
                            +"<td style='width: 10%' align='center'>Grupo</td>"
                            +"<td style='width: 10%' align='center'>Profesor</td>"
                +"<td style='width: 10%' align='center'>Alumno</td>"
                +"<td style='width: 10%' align='center'>Código</td>");
                out.print(  "<td style='width: 5%' align='center'>Marcar Leído</td>");
                out.print(  "<td style='width: 5%' align='center'>Imprimir</td>");
                out.print(  "<td style='width: 5%' align='center'>Elim</td>"   
                +"</tr>" );
                int i=0;
                String color;
                if(u.isAdmin()|| u.isNotas()){
                    for (  Notificacion p : mp.getNotificacionesNuevas()){
                            if ((i%2)==0){
                                color=" #ccccff";
                            }
                            else{
                                color=" #ffff99";
                            }
                            i++;

                           out.print("<tr style='background-color:"+color+"' title=\"");
                           if(p.getFalta()!=null && p.getFalta().idFalta!=-1){
                               out.print("TIPO: FALTA&#10;FECHA: "+p.getFecha()+"&#10;CANTIDAD DE HORAS: "+p.getFalta().cantHoras+"&#10;OBSERVACIONES: "+p.getFalta().observaciones);
                           }
                           else{
                               String minutos=""; 
                               if(p.getSancion().tipo==2){
                                   minutos="&#10;MINUTOS TARDES: "+p.getSancion().minutosTardes; 
                               }
                               out.print("TIPO: SANCION&#10;FECHA: "+p.getFecha()+minutos+"&#10;CAUSA: "+p.getSancion().causa);
                           }
                           out.print("\">"
                           +"<td style='width: 5%' align='center'>"+i+"</td>" );
                           out.print("<td style='width: 10%' align='center'>"+p.getId()+"</td>");
                               out.print("<td style='width: 10%' align='center'>"+p.getFecha()+"</td>");
                           
                            out.print("<td style='width: 10%' align='center'>"+p.getLibreta().getMateria().getNombre()+"</td>"
                           +"<td style='width: 10%' align='center'>"+p.getLibreta().getGrupo().getCusoBedelia().getNombre()+"</td>"
                           +"<td style='width: 10%' align='center'>"+p.getLibreta().getGrupo().getAnio()+" - "+p.getLibreta().getGrupo().getNombre()+"</td>"
                           +"<td style='width: 10%' align='center'>"+ p.getLibreta().getProfesor().obtenerNombreCompleto() +"</td>"
                           +"<td style='width: 10%' align='center'>"+ p.getCadete().getGrado().getAbreviacion()+" "+p.getCadete().getPrimerApellido()+" "+p.getCadete().getPrimerNombre() +"</td>");
                           if(p.getFalta()!=null && p.getFalta().idFalta!=-1){
                                out.print("<td style='width: 10%' align='center'>"+ p.getFalta().codigoMotivo);
                                if(p.isEliminado()){
                                    out.print(" - Eliminado") ;
                                }
                                out.print("</td>");
                           }
                           else{
                               String codigo="R";
                               if(p.getSancion().tipo==1){
                                   codigo="M";
                               }
                               out.print("<td style='width: 10%' align='center'>"+codigo);
                                if(p.isEliminado()){
                                    out.print(" - Eliminado") ;
                                }
                                out.print("</td>");
                           }
                            out.print("<td style='width: 5%' align='center'><a onclick='marcarLeido(true,"+p.getId()+")'><img src='images/leido.png' width='60%' /></a></td>"
                            +"<td style=\"width: 5%\" align='center'>");
                            if(p.getSancion()!=null && p.getSancion().idSancion!=-1){
                                out.print("<a target='_blank' href='Notificacion?imprimir=1&id="+p.getId()+"'><img  width=\"60%\" title=\"Imprimir parte\" src=\"images/imprimir.png\" /></a>");
                            }
                            out.print("</td>"
                                    + "<td style='width: 5%' align='center'><a onclick='eliminar(false,"+p.getId()+")'><img src='images/eliminar.png' width='60%' /></a></td>"
                           +"</tr>");
                        
                    }
                }
                out.print("</table>");
            %> 
            <h3>
                Notificaciones Le&iacute;das:
            </h3>
            <%
                out.print("<table style='width: 100%;' id='leidas'>"
                        + "<tr style='background-color:#ffcc66'>"
                            +"<td style='width: 5%' align='center'></td>"
                            +"<td style='width: 10%' align='center'>ID</td>"
                            +"<td style='width: 10%' align='center'>Fecha</td>"
                            +"<td style='width: 10%' align='center'>Materia</td>"
                            +"<td style='width: 10%' align='center'>Curso</td>"
                            +"<td style='width: 10%' align='center'>Grupo</td>"
                            +"<td style='width: 10%' align='center'>Profesor</td>"
                +"<td style='width: 10%' align='center'>Alumno</td>"
                +"<td style='width: 10%' align='center'>Código</td>");
                out.print(  "<td style='width: 5%' align='center'>Marcar Nuevo</td>");
                out.print(  "<td style='width: 5%' align='center'>Imprimir</td>");
                out.print(  "<td style='width: 5%' align='center'>Elim</td>"   
                +"</tr>" );
                i=0;
                if(u.isAdmin()|| u.isNotas()){
                    for (  Notificacion p : mp.getNotificacionesLeidas()){
                            if ((i%2)==0){
                                color=" #ccccff";
                            }
                            else{
                                color=" #ffff99";
                            }
                            i++;

                           out.print("<tr style='background-color:"+color+"' title=\"");
                           if(p.getFalta()!=null && p.getFalta().idFalta!=-1){
                               out.print("TIPO: FALTA&#10;FECHA: "+p.getFecha()+"&#10;CANTIDAD DE HORAS: "+p.getFecha()+"&#10;OBSERVACIONES: "+p.getFalta().observaciones);
                           }
                           else{
                               String minutos=""; 
                               if(p.getSancion().tipo==2){
                                   minutos="&#10;MINUTOS TARDES: "+p.getSancion().minutosTardes; 
                               }
                               out.print("TIPO: SANCION&#10;FECHA: "+p.getFecha()+minutos+"&#10;CAUSA: "+p.getSancion().causa);
                           }
                           out.print("\">"
                           +"<td style='width: 5%' align='center'>"+i+"</td>" );
                          out.print("<td style='width: 10%' align='center'>"+p.getId()+"</td>");
                            out.print("<td style='width: 10%' align='center'>"+p.getFecha()+"</td>");
                            out.print("<td style='width: 10%' align='center'>"+p.getLibreta().getMateria().getNombre()+"</td>"
                           +"<td style='width: 10%' align='center'>"+p.getLibreta().getGrupo().getCusoBedelia().getNombre()+"</td>"
                           +"<td style='width: 10%' align='center'>"+p.getLibreta().getGrupo().getAnio()+" - "+p.getLibreta().getGrupo().getNombre()+"</td>"
                           +"<td style='width: 10%' align='center'>"+ p.getLibreta().getProfesor().obtenerNombreCompleto() +"</td>"
                           +"<td style='width: 10%' align='center'>"+ p.getCadete().getGrado().getAbreviacion()+" "+p.getCadete().getPrimerApellido()+" "+p.getCadete().getPrimerNombre() +"</td>");
                           if(p.getFalta()!=null && p.getFalta().idFalta!=-1){
                                out.print("<td style='width: 10%' align='center'>"+ p.getFalta().codigoMotivo );
                                if(p.isEliminado()){
                                    out.print(" - Eliminado") ;
                                }
                                out.print("</td>");
                           }
                           else{
                               String codigo="R";
                               if(p.getSancion().tipo==1){
                                   codigo="M";
                               }
                               out.print("<td style='width: 10%' align='center'>"+codigo);
                                if(p.isEliminado()){
                                    out.print(" - Eliminado") ;
                                }
                                out.print("</td>");
                           }
                            out.print("<td style='width: 5%' align='center'><a onclick='marcarLeido(false,"+p.getId()+")'><img src='images/nuevo.png' width='60%' /></a></td>"
                                    + "<td style=\"width: 5%\" align='center'>");
                            if(p.getSancion()!=null && p.getSancion().idSancion!=-1){
                                out.print("<a target='_blank' href='Notificacion?imprimir=1&id="+p.getId()+"'><img  width=\"60%\" title=\"Imprimir parte\" src=\"images/imprimir.png\" /></a>");
                            }
                             out.print("</td>"
                                    + "<td style='width: 5%' align='center'><a onclick='eliminar(true,"+p.getId()+")'><img src='images/eliminar.png' width='60%' /></a></td>"
                           +"</tr>");
                        
                    }
                }
                out.print("</table>");
            %> 
         </div>
     </div>    
<% 
    }
    else{
         response.sendRedirect("");
    }

%>
<%@ include file="footer.jsp" %> 



