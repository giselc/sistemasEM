<%-- 
    Document   : cursos
    Created on : Mar 19, 2019, 9:39:46 AM
    Author     : Gisel
--%>

<%@page import="Classes.Bedelia.CursoBedelia"%>
<%@page import="Manejadores.ManejadorBedelia"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>   
<% 
    if(u.isAdmin() || u.getPermisosPersonal().getId()==4){
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
        function listar(tipoPersonal) {//Funcion creada para no perder la sesion luego del submit
            var form=document.getElementById("formProfesoresListar");
            var x = document.getElementsByName("List[]");
            for (i=0;i<x.length;i++) {
                if(x[i].type == "checkbox"){
                   x[i].checked=1
                }
            }
            var filtro1=document.getElementById("filtroTexto").innerHTML;
            if(!(filtro1==(""))){
                form.action="Listar?tipoPersonal="+tipoPersonal+"&filtro="+filtro1;
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

    <p id="mensaje" style="color: #ffffff"><% if(sesion.getAttribute("Mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+sesion.getAttribute("Mensaje"));}%></p>
<%
    sesion.setAttribute("Mensaje",null);
%>
<p align="left"><a href="javascript:history.go(-1)"><img src="images/atras.png" width="15%"/></a></p>
    <ul id="tabs">
        <li><a href="#"><b>CURSOS</b></a></li>
    </ul>
    <div id="loader" style="z-index: 50;position: fixed; top:0; left:0; width:100%; height: 100%;background: url('images/loading-verde.gif') center center no-repeat; background-size: 10%"></div>
    <div id="content">
        <div>
            <div id='dialog2' style="display:none" title="Seleccione los campos a listar:">
                <form method="post" target="_blank"  id="formProfesoresListar" name="formProfesoresListar" action='Listar?tipoPersonal=4'>
                    
                </form>
            </div>
            <table style="float: right">
                <tr>
                    <td style="width: 55%"><h3 style="float: left;">CURSOS</h3></td>
                    <td style="width: 15%"><a href="curso.jsp" title="Agregar"><img width="30%" src='images/agregarLista.png' /></a> </td>
                    <td style="width: 15%"><img  width="30%" title="Imprimir" src="images/imprimir.png" onclick="listar(4);"/></td>
                </tr>
                <tr>
                    <td colspan="8">
                        <p style="font-size: 70%" id="filtroTexto"></p>
                    </td>   
                </tr>
            </table>
                
            <%
            ManejadorBedelia mp = ManejadorBedelia.getInstance();
            %>   

    
            <%
                out.print("<table style='width: 100%;' id='tablalistado'>"
                        + "<tr style='background-color:#ffcc66'>"
                            +"<td style='width: 5%' align='center'></td>"
                            +"<td style='width: 5%; display:none' align='center'><input type='checkbox' onclick='seleccionar_todo()' id='selTodo'></td>"
                            +"<td style='width: 10%' align='center'>id</td>"
                            +"<td style='width: 10%' align='center'>Codigo</td>"
                            +"<td style='width: 10%' align='center'>Nombre</td>"
                            +"<td style='width: 10%' align='center'>Anio Curricular</td>"
                            +"<td style='width: 10%' align='center'>Jefatura</td>");
                out.print(  "<td style='width: 5%' align='center'>Ver</td>");
                out.print(  "<td style='width: 5%' align='center'>Elim</td>"   
                +"</tr>" );
                int i=0;
                String color;
                for (  CursoBedelia p : mp.getCursos().values()){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;
                        
                       out.print("<tr style='background-color:"+color+"'>"
                       +"<td style='width: 5%' align='center'>"+i+"</td>"
                       +"<td style='width: 5%;display:none' align='center'><input type='checkbox' name='List[]' value='"+String.valueOf(p.getId())+"' form='formCadeteListar' /></td>"
                       +"<td style='width: 10%' align='center'>"+p.getId()+"</td>"
                       +"<td style='width: 10%' align='center'>"+p.getCodigo()+"</td>"
                       +"<td style='width: 10%' align='center'>"+p.getNombre()+"</td>"
                       +"<td style='width: 10%' align='center'>"+p.getAnioCurricular()+"</td>"
                       +"<td style='width: 10%' align='center'>");
                       if(p.isJefatura()){
                            out.print("JE");
                       }else{
                            out.print("JCC");
                       }
                       out.print("</td>");
                        out.print("<td style='width: 5%' align='center'><a href='curso.jsp?id="+p.getId()+"'><img src='images/ver.png' width='60%' /></a></td>");
                        out.print("<td style='width: 5%' align='center'><a href='Curso?elim=1&id="+p.getId()+"'><img src='images/eliminar.png' width='60%' /></a></td>"
                       +"</tr>");
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

