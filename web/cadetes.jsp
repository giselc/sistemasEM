<%-- 
    Document   : cadetes
    Created on : Apr 6, 2018, 8:37:40 AM
    Author     : Gisel
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Classes.*"%>
<%@page import="Classes.ManejadorCadetes"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="Classes.ConexionDB"%>
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
                     e.preventDefault();
                     $("#content div").hide();
                     $("#tabs li").attr("id","");
                     $(this).parent().attr("id","current");
                     $('#' + $(this).attr('title')).fadeIn();
                 });
             })();
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
        function seleccionar_todo(formulario){ 
            if (document.getElementById("selTodo").checked){
                for (i=1;i<document.formulario.elements.length;i++) 
               if(document.formulario.elements[i].type == "checkbox")	
                  document.formulario.elements[i].checked=1 ;
            }
            else{
                for (i=1;i<document.formulario.elements.length;i++) 
               if(document.formulario.elements[i].type == "checkbox")	
                  document.formulario.elements[i].checked=0;
            }
        
        } 
        function listar(form) {//Funcion creada para no perder la sesion luego del submit
            form.submit();
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
    function aplicarFiltro(f,idDatos){
       // alert(serialize(f));
        xmlhttp=new XMLHttpRequest();
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                var obj = jQuery.parseJSON( xmlhttp.responseText );
                var listado = obj.listadoPostulantes;
                var color = "";
                var datos = "";
                var j;
                for (var i=0; i<listado.length;i++) {
                    if ((i%2)==0){
                        color=" #ccccff";
                    }
                    else{
                        color=" #ffff99";
                    }
                    j=i+1;
                    datos += "<tr style='background-color:"+color+"'>";
                    datos += "<td style='width: 5%' align='center'>"+j+"</td>";
                    datos +="<td style='width: 5%' align='center'><input type='checkbox' name='List[]' value='"+ listado[i].ci +"' /></td>";
                    if (listado[i].id != -1){
                        datos +="<td style='width: 10%' align='center'>"+ listado[i].id +"</td>";
                    }
                    else{
                        datos +="<td style='width: 10%' align='center'>COND.</td>";
                    }
                    datos +="<td style='width: 10%' align='center'>"+ listado[i].primerNombre +"</td>";
                    datos +="<td style='width: 10%' align='center'>"+ listado[i].segundoNombre +"</td>";
                    datos +="<td style='width: 10%' align='center'>"+ listado[i].primerApellido +"</td>";
                    datos +="<td style='width: 10%' align='center'>"+ listado[i].segundoApellido +"</td>";
                    datos +="<td style='width: 15%' align='center'>"+ listado[i].ci +"</td>";
                    datos +="<td style='width: 10%' align='center'><a href='cadete.jsp?ci="+ listado[i].ci +"'><img src='images/ver.png' width='25%' /></a></td>";
                    datos +="</tr>";

                }
                document.getElementById(idDatos).innerHTML = datos;
                document.getElementById("filtroTexto").innerHTML = obj.filtroMostrar;
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
%> 
     <ul id="tabs">
         <li><a href="#" title="Cuerpo-Cadetes"><b>Cuerpo de Cadetes</b></a></li>
     </ul>
    <div id="loader" style="z-index: 50;position: fixed; top:0; left:0; width:100%; height: 100%;background: url('images/loading-verde.gif') center center no-repeat; background-size: 10%"></div>


     <div id="content">
         <div id="Cuerpo-Cadetes">
             <%@include file="listarCadetes.jsp" %>
         </div>
     </div>    
<%@ include file="footer.jsp" %> 
