<%-- 
    Document   : cadetes
    Created on : Apr 6, 2018, 8:37:40 AM
    Author     : Gisel
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Classes.*"%>
<%@page import="Manejadores.ManejadorPersonal"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="Classes.ConexionDB"%>
<%@ include file="header.jsp" %>   
<% 
    if(u.isAdmin() || u.getPermisosPersonal().getId()==1){
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
    </script>
    <script>
        function abrir_dialog(dialog) {
          $( dialog ).dialog({
              modal: true
          });
        };
        function cerrar_dialog(dialog) {
          $( dialog ).dialog('close');
        };
        function listado(dialog) {
            var x = document.getElementsByName("List[]");
            var j=0;
            for (i=0;i<x.length;i++) {
                if(x[i].type == "checkbox"){
                   if(x[i].checked==1){
                       j++;
                       break;
                    }
                }
            }
            if(j==0){
                abrir_dialog(dialog);
            }
            else{
                var form = document.getElementById("formCadeteListar");
                form.action="Listar?fichas=1"
                form.submit();
            }
        };
        function listar(form) {//Funcion creada para no perder la sesion luego del submit
            var x = document.getElementsByName("List[]");
            for (i=0;i<x.length;i++) {
                if(x[i].type == "checkbox"){
                   x[i].checked=1
                }
            }
            var filtro1=document.getElementById("filtroTexto").innerHTML;
            if(!(filtro1==(""))){
                form.action="Listar?filtro="+filtro1;
            }
            form.submit();
            for (i=0;i<x.length;i++) {
                if(x[i].type == "checkbox"){
                   x[i].checked=0
                }
            }
            cerrar_dialog(dialog2);
            return false;
        };
        function serialize(form) {
                if (!form || form.nodeName !== "FORM") {
                        return;
                }
                var i, j, q = [];
                for (i = form.elements.length - 1; i >= 0; i = i - 1) {
                        if (form.elements[i].name === "") {
                                continue;
                        }
                        switch (form.elements[i].nodeName) {
                        case 'INPUT':
                                switch (form.elements[i].type) {
                                case 'text':
                                case 'hidden':
                                case 'password':
                                case 'button':
                                case 'reset':
                                case 'submit':
                                        q.push(form.elements[i].name + "=" + encodeURIComponent(form.elements[i].value));
                                        break;
                                case 'checkbox':
                                case 'radio':
                                        if (form.elements[i].checked) {
                                                q.push(form.elements[i].name + "=" + encodeURIComponent(form.elements[i].value));
                                        }						
                                        break;
                                case 'file':
                                        break;
                                }
                                break;			 
                        case 'TEXTAREA':
                                q.push(form.elements[i].name + "=" + encodeURIComponent(form.elements[i].value));
                                break;
                        case 'SELECT':
                                switch (form.elements[i].type) {
                                case 'select-one':
                                        q.push(form.elements[i].name + "=" + encodeURIComponent(form.elements[i].value));
                                        break;
                                case 'select-multiple':
                                        for (j = form.elements[i].options.length - 1; j >= 0; j = j - 1) {
                                                if (form.elements[i].options[j].selected) {
                                                        q.push(form.elements[i].name + "=" + encodeURIComponent(form.elements[i].options[j].value));
                                                }
                                        }
                                        break;
                                }
                                break;
                        case 'BUTTON':
                                switch (form.elements[i].type) {
                                case 'reset':
                                case 'submit':
                                case 'button':
                                        q.push(form.elements[i].name + "=" + encodeURIComponent(form.elements[i].value));
                                        break;
                                }
                                break;
                        }
                }
                return q.join("&");
        }
    </script>

    <script>
        function seleccionar_todo(){ 
            var x = document.getElementsByName("List[]");
            if (document.getElementById("selTodo").checked){
                for (i=0;i<x.length;i++) 
                    if(x[i].type == "checkbox")	
                       x[i].checked=true ;
            }
            else{
                for (i=0;i<x.length;i++) 
                    if(x[i].type == "checkbox")	
                       x[i].checked=false;
            }

        }
        function ordenar(tipo,orden){
           // alert(serialize(f));
            xmlhttp=new XMLHttpRequest();
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                    var obj = jQuery.parseJSON( xmlhttp.responseText );
                    var listado = obj.listadoCadetes;
                    var color = "";
                    var datos = "";
                    var j;
                    var imgnro="<img src='images/derecha.png' width='30%' onclick='ordenar(1,1)' />";
                    var imggrado="<img src='images/derecha.png' width='15%' onclick='ordenar(2,1)' />";
                    var imgnombre="<img src='images/derecha.png' width='6%' onclick='ordenar(3,1)' />";
                    var imgapellido="<img src='images/derecha.png' width='6%' onclick='ordenar(4,1)' />";
                    if(tipo==1){
                        if(orden==0){
                            imgnro="<img src='images/abajo.png' width='30%' onclick='ordenar(1,1)' />";
                        }
                        else{
                            imgnro="<img src='images/arriba.png' width='30%' onclick='ordenar(1,0)' />";
                        }
                    }
                    else{
                        if(tipo==2){
                            if(orden==0){
                                imggrado="<img src='images/abajo.png' width='15%' onclick='ordenar(2,1)' />";
                            }
                            else{
                                imggrado="<img src='images/arriba.png' width='15%' onclick='ordenar(2,0)' />";
                            }
                        }
                        else{
                           if(tipo==3){
                                if(orden==0){
                                    imgnombre="<img src='images/abajo.png' width='6%' onclick='ordenar(3,1)' />";
                                }
                                else{
                                    imgnombre="<img src='images/arriba.png' width='6%' onclick='ordenar(3,0)' />";
                                }
                            }
                            else{
                              if(tipo==4){
                                    if(orden==0){
                                        imgapellido="<img src='images/abajo.png' width='6%' onclick='ordenar(4,1)' />";
                                    }
                                    else{
                                        imgapellido="<img src='images/arriba.png' width='6%' onclick='ordenar(4,0)' />";
                                    }
                                }  
                            }
                        }
                    }

                    datos +="<tr style='background-color:#ffcc66'>"
                                +"<td style='width: 5%' align='center'></td>"
                                +"<td style='width: 5%' align='center'><input type='checkbox' onclick='seleccionar_todo()' id='selTodo'></td>"
                                +"<td style='width: 5%' align='center'>"+imgnro+"Nro.</td>"
                                +"<td style='width: 10%' align='center'>"+imggrado+"Grado</td>"
                                +"<td colspan=2 style='width: 20%' align='center'>"+imgnombre+"Nombres</td>"
                                +"<td colspan=2 style='width: 20%' align='center'>"+imgapellido+"Apellidos</td>"
                                +"<td style='width: 10%' align='center'>Cédula</td>"
                                +"<td style='width: 15%' align='center'>Curso</td>"
                                +"<td style='width: 5%' align='center'>Ver</td>"
                                +"<td style='width: 5%' align='center'>Elim</td>"   
                    +"</tr>";
                    for (var i=0; i<listado.length;i++) {
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        j=i+1;
                        datos += "<tr style='background-color:"+color+"'>"
                           +"<td style='width: 5%' align='center'>"+(i+1)+"</td>"
                           +"<td style='width: 5%' align='center'><input type='checkbox' name='List[]' value='"+String.valueOf(listado[i].ci)+"' form='formCadeteListar'/></td>"
                           +"<td style='width: 5%' align='center'>"+listado[i].Nro+"</td>"
                           +"<td style='width: 10%' align='center'>"+listado[i].grado+"</td>"
                           +"<td style='width: 10%' align='center'>"+listado[i].primerNombre+"</td>"
                           +"<td style='width: 10%' align='center'>"+listado[i].segundoNombre+"</td>"
                           +"<td style='width: 10%' align='center'>"+listado[i].primerApellido+"</td>"
                           +"<td style='width: 10%' align='center'>"+listado[i].segundoApellido+"</td>"
                           +"<td style='width: 10%' align='center'>"+listado[i].ci+"</td>"
                           +"<td style='width: 15%' align='center'>"+listado[i].curso+"</td>"
                           +"<td style='width: 5%' align='center'><a href='cadete.jsp?id="+listado[i].ci+"'><img src='images/ver.png' width='60%' /></a></td>"
                            +"<td style='width: 5%' align='center'><a href='baja.jsp?id="+listado[i].ci+"'><img src='images/eliminar.png' width='60%' /></a></td>"
                           +"</tr>";

                    }
                    document.getElementById("tablalistado").innerHTML = datos;
                };
            };
            xmlhttp.open("POST","ListarCadetes?tipo="+tipo+"&orden="+orden);
            xmlhttp.send();
            return false;
        }
        function aplicarFiltro(f){
       //alert("Hola");
        xmlhttp=new XMLHttpRequest();
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                //alert("Hola");
                var obj = jQuery.parseJSON( xmlhttp.responseText );
                var listado = obj.listadoPersonal;
                var color = "";
                var j;
                var datos="<table style='width: 100%;' id='tablalistado'>"
                        + "<tr style='background-color:#ffcc66'>"
                            +"<td style='width: 5%' align='center'></td>"
                            +"<td style='width: 5%' align='center'><input type='checkbox' onclick='seleccionar_todo()' id='selTodo'></td>"
                            +"<td style='width: 5%' align='center'><img src='images/derecha.png' width='30%' onclick='ordenar(1,1)' /> Nro.</td>"
                            +"<td style='width: 10%' align='center'><img src='images/abajo.png' width='15%' onclick='ordenar(2,1)' />Grado</td>"
                            +"<td colspan=2 style='width: 20%' align='center'><img src='images/derecha.png' width='6%' onclick='ordenar(3,1)' />Nombres</td>"
                            +"<td colspan=2 style='width: 20%' align='center'><img src='images/derecha.png' width='6%' onclick='ordenar(4,1)' />Apellidos</td>"
                            +"<td style='width: 10%' align='center'>Cédula</td>"
                            +"<td style='width: 15%' align='center'>Curso</td>"
                            +"<td style='width: 5%' align='center'>Ver</td>"
                            +"<td style='width: 5%' align='center'>Elim</td>"   
                +"</tr>";
                for (var i=0; i<listado.length;i++) {
                    if ((i%2)==0){
                        color=" #ccccff";
                    }
                    else{
                        color=" #ffff99";
                    }
                    j=i+1;
                    datos+="<tr style='background-color:"+color+"'>"
                       +"<td style='width: 5%' align='center'>"+j+"</td>"
                       +"<td style='width: 5%' align='center'><input type='checkbox' name='List[]' value='"+listado[i].ci+"'  form='formCadeteListar'/></td>"
                       +"<td style='width: 5%' align='center'>"+listado[i].numero+"</td>"
                       +"<td style='width: 10%' align='center'>"+listado[i].grado+"</td>"
                       +"<td style='width: 10%' align='center'>"+listado[i].primerNombre+"</td>"
                       +"<td style='width: 10%' align='center'>"+listado[i].segundoNombre+"</td>"
                       +"<td style='width: 10%' align='center'>"+listado[i].primerApellido+"</td>"
                       +"<td style='width: 10%' align='center'>"+listado[i].segundoApellido+"</td>"
                       +"<td style='width: 10%' align='center'>"+listado[i].ci+"</td>"
                       +"<td style='width: 15%' align='center'>"+listado[i].curso+"</td>"
                       +"<td style='width: 5%' align='center'><a href='cadete.jsp?id="+listado[i].ci+"'><img src='images/ver.png' width='60%' /></a></td>"
                               +"<td style='width: 5%' align='center'><a href='baja.jsp?id="+listado[i].ci+"'><img src='images/eliminar.png' width='60%' /></a></td>"
                       +"</tr>";

                }
                datos+="</table>";
                document.getElementById("tablalistado").innerHTML = datos;
                document.getElementById("filtroTexto").innerHTML = obj.filtroTexto;
            };
        };
        xmlhttp.open("POST","Filtro?"+serialize(f));
        xmlhttp.send();
        f.reset();
        return false;
    }
    </script> 
    <p id="mensaje" style="color: #ffffff"><% if(sesion.getAttribute("Mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+sesion.getAttribute("Mensaje"));}%></p>
<%
    sesion.setAttribute("Mensaje",null);
    sesion.setAttribute("atras","cadetes.jsp");
%> 
    <ul id="tabs">
        <li><a href="#" title="Cuerpo-Cadetes"><b>Cuerpo de Cadetes</b></a></li>
    </ul>
    <div id="loader" style="z-index: 50;position: fixed; top:0; left:0; width:100%; height: 100%;background: url('images/loading-verde.gif') center center no-repeat; background-size: 10%"></div>
    <div id="content">
        <div id="Cuerpo-Cadetes">
            <div id='dialog2' style="display:none" title="Seleccione los campos a listar:">
                 <%@include file="camposListar.jsp" %>
            </div>
            <table style="float: right">
                <tr>
                    <td style="width: 55%"><h3 style="float: left; font-family: sans-serif">Cadetes</h3></td>
                    <td style="width: 15%"><a href="cadete.jsp" title="Agregar"><img width="30%" src='images/agregarLista.png' /></a> </td>
                    <td style="width: 15%"><a onclick='abrir_dialog(dialog1)' title="Aplicar filtro"><img width="35%" src='images/filtro_1.png' /></a> </td>
                    <td style="width: 15%"><img  width="30%" title="Imprimir" src="images/imprimir.png" onclick="listado(dialog2)"/></td>
                </tr>
                <tr>
                    <td colspan="8">
                        <p style="font-size: 70%" id="filtroTexto"></p>
                    </td>   
                </tr>
            </table>
            <%@include file="listarCadetes.jsp" %>
         </div>
     </div>    
<% 
    }
    else{
         response.sendRedirect("");
    }

%>
<%@ include file="footer.jsp" %> 
