<%-- 
    Document   : personal
    Created on : Nov 30, 2018, 7:45:49 PM
    Author     : Gisel
--%>

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
        function listar(tipoPersonal) {//Funcion creada para no perder la sesion luego del submit
            var form=document.getElementById("formCadeteListar");
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
        function existePersonal(ciInput,tipo){
            if(ciInput.value.length!=0 && ciInput.value.length==8){
                xmlhttp=new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                        var obj = jQuery.parseJSON( xmlhttp.responseText );
                        var existe = obj.Personal;
                        if(existe.length>0){
                                alert("El personal ya existe en el sistema.");     
                                ciInput.value="";
                        }
                    };
                };
                xmlhttp.open("POST","Personal?existe=1&ci="+ciInput.value+"&tipo="+tipo);
                xmlhttp.send();
                return false;
            }
            else{
                if(ciInput.value.length!=0 && ciInput.value.length!=8){
                    alert("Cédula incorrecta.");
                    ciInput.value="";
                    return false;
                }
            }
        }
    </script>

    <script>
        
        function ordenar(tipo,orden,tipoPersonal){
           // alert(serialize(f));
            xmlhttp=new XMLHttpRequest();
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                    var obj = jQuery.parseJSON( xmlhttp.responseText );
                    var listado = obj.listadoPersonal;
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
                                +"<td style='width: 5%;display:none' align='center'><input type='checkbox' onclick='seleccionar_todo()' id='selTodo'></td>"
                                +"<td style='width: 5%' align='center'>"+imgnro+"Nro.</td>"
                                +"<td style='width: 10%' align='center'>"+imggrado+"Grado</td>"
                                +"<td colspan=2 style='width: 20%' align='center'>"+imgnombre+"Nombres</td>"
                                +"<td colspan=2 style='width: 20%' align='center'>"+imgapellido+"Apellidos</td>"
                                +"<td style='width: 10%' align='center'>Cédula</td>"
                                +"<td style='width: 15%' align='center'>Arma</td>"
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
                           +"<td style='width: 5%;display:none' align='center'><input type='checkbox' name='List[]' value='"+String.valueOf(listado[i].ci)+"' form='formCadeteListar'/></td>"
                           +"<td style='width: 5%' align='center'>"+listado[i].Nro+"</td>"
                           +"<td style='width: 10%' align='center'>"+listado[i].grado+"</td>"
                           +"<td style='width: 10%' align='center'>"+listado[i].primerNombre+"</td>"
                           +"<td style='width: 10%' align='center'>"+listado[i].segundoNombre+"</td>"
                           +"<td style='width: 10%' align='center'>"+listado[i].primerApellido+"</td>"
                           +"<td style='width: 10%' align='center'>"+listado[i].segundoApellido+"</td>"
                           +"<td style='width: 10%' align='center'>"+listado[i].ci+"</td>"
                           +"<td style='width: 15%' align='center'>"+listado[i].arma+"</td>"
                           +"<td style='width: 5%' align='center'><a href='baja.jsp?id="+listado[i].ci+"'><img src='images/eliminar.png' width='60%' /></a></td>"
                           +"</tr>";

                    }
                    document.getElementById("tablalistado").innerHTML = datos;
                };
            };
            xmlhttp.open("POST","ListarPersonal?tipoListado="+tipo+"&orden="+orden+"&tipo="+tipoPersonal);
            xmlhttp.send();
            return false;
        }
        function aplicarFiltro(f,tipoPersonal){
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
                            +"<td style='width: 5%;display:none' align='center'><input type='checkbox' onclick='seleccionar_todo()' id='selTodo'></td>"
                            +"<td style='width: 5%' align='center'><img src='images/derecha.png' width='30%' onclick='ordenar(1,1)' /> Nro.</td>"
                            +"<td style='width: 10%' align='center'><img src='images/abajo.png' width='15%' onclick='ordenar(2,1)' />Grado</td>"
                            +"<td colspan=2 style='width: 20%' align='center'><img src='images/derecha.png' width='6%' onclick='ordenar(3,1)' />Nombres</td>"
                            +"<td colspan=2 style='width: 20%' align='center'><img src='images/derecha.png' width='6%' onclick='ordenar(4,1)' />Apellidos</td>"
                            +"<td style='width: 10%' align='center'>Cédula</td>"
                            +"<td style='width: 15%' align='center'>Arma</td>"
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
                       +"<td style='width: 5%;display:none' align='center'><input type='checkbox' name='List[]' value='"+listado[i].ci+"'  form='formCadeteListar'/></td>"
                       +"<td style='width: 5%' align='center'>"+listado[i].numero+"</td>"
                       +"<td style='width: 10%' align='center'>"+listado[i].grado+"</td>"
                       +"<td style='width: 10%' align='center'>"+listado[i].primerNombre+"</td>"
                       +"<td style='width: 10%' align='center'>"+listado[i].segundoNombre+"</td>"
                       +"<td style='width: 10%' align='center'>"+listado[i].primerApellido+"</td>"
                       +"<td style='width: 10%' align='center'>"+listado[i].segundoApellido+"</td>"
                       +"<td style='width: 10%' align='center'>"+listado[i].ci+"</td>"
                       +"<td style='width: 15%' align='center'>"+listado[i].arma+"</td>"
                       +"<td style='width: 5%' align='center'><a href='baja.jsp?id="+listado[i].ci+"'><img src='images/eliminar.png' width='60%' /></a></td>"
                       +"</tr>";
                }
                datos+="</table>";
                document.getElementById("tablalistado").innerHTML = datos;
                document.getElementById("filtroTexto").innerHTML = obj.filtroTexto;
            };
        };
        xmlhttp.open("POST","Filtro?tipoPersonal="+tipoPersonal+"&"+serialize(f));
        xmlhttp.send();
        f.reset();
        return false;
    }
    </script> 
    <%
            int tipo=Integer.valueOf(request.getParameter("tipo"));
            %>
    <p id="mensaje" style="color: #ffffff"><% if(sesion.getAttribute("Mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+sesion.getAttribute("Mensaje"));}%></p>
<%
    sesion.setAttribute("Mensaje",null);
    sesion.setAttribute("atras","personal.jsp");
%> 
    <ul id="tabs">
        <li><a href="#"><b><%
            switch(Integer.valueOf(request.getParameter("tipo"))){
                case 2:out.print("PERSONAL SUBALTERNO");break;
                case 3:out.print("OFICIALES");break;
                case 4:out.print("PROFESORES");break;
            }
    
        %></b></a></li>
    </ul>
    <div id="loader" style="z-index: 50;position: fixed; top:0; left:0; width:100%; height: 100%;background: url('images/loading-verde.gif') center center no-repeat; background-size: 10%"></div>
    <div id="content">
        <div>
            <div id='dialog2' style="display:none" title="Seleccione los campos a listar:">
                <form method="post" target="_blank"  id="formCadeteListar" name="formCadeteListar" action='Listar?tipoPersonal=<%= tipo %>'>
                    
                </form>
            </div>
            <table style="float: right">
                <tr>
                    <td style="width: 55%"><h3 style="float: left; font-family: sans-serif"><%
            switch(Integer.valueOf(request.getParameter("tipo"))){
                case 2:out.print("PERSONAL SUBALTERNO");break;
                case 3:out.print("OFICIALES");break;
                case 4:out.print("PROFESORES");break;
            }
    
        %></h3></td>
                    <td style="width: 15%"><a onclick='abrir_dialog(dialog1)' title="Aplicar filtro"><img width="35%" src='images/filtro_1.png' /></a> </td>
                    <td style="width: 15%"><img  width="30%" title="Imprimir" src="images/imprimir.png" onclick="listar(<%= tipo %>);"/></td>
                </tr>
                <tr>
                    <td colspan="8">
                        <p style="font-size: 70%" id="filtroTexto"></p>
                    </td>   
                </tr>
            </table>
                <form action="Personal?tipo=<%= tipo %>" method="post" id="formularioAlta">
                    <table>
                        <tr>
                            <td>C.I.: </td>
                            <td><input type=number name="ci" size="8" maxlength="8" onblur="existePersonal(this,<%= tipo %>);" required="required"/> </td>
                        </tr>
                        <tr>
                            <td>Grado: </td>
                            <td>
                                <select name="grado" form="formularioAlta" required="required">
                                    <%
                                    HashMap<Integer,Grado> ag1 = mc.getGrados();
                                    for(Grado dep: ag1.values() ){
                                        if(dep.getIdTipoPersonal().getId()== tipo){//cadetes
                                            out.print("<option value='"+String.valueOf(dep.getId()) +"'>"+ dep.getDescripcion() +"</option>");
                                        }
                                    }
                                    %>
                                 </select>
                            </td>
                            <td>Arma: </td>
                            <td>
                                <select name="arma" form="formularioAlta" required="required">
                                    <%
                                    HashMap<Integer,Arma> aa1 = mc.getArmas();
                                    out.print("<option value='-1'>------</option>");
                                    for(Arma dep: aa1.values() ){
                                        out.print("<option value='"+String.valueOf(dep.getId()) +"'>"+ dep.getDescripcion() +"</option>");
                                    }

                                    %>
                                 </select>
                            </td>
                        </tr>
                        <tr>
                            <td>Primer nombre: </td>
                            <td><input type="text" name="primerNombre" size="50" required="required"/></td>
                            <td>Segundo nombre: </td>
                            <td><input type="text" name="segundoNombre"  size="50"/></td>
                        </tr>
                        <tr>    
                            <td>Primer apellido: </td>
                            <td><input type="text" name="primerApellido"  size="50" required="required"/></td>
                            <td>Segundo apellido: </td>
                            <td><input type="text" name="segundoApellido"  size="50" required="required"/></td>
                        </tr>
                        <tr>
                            <td colspan="4">
                                <p align="right"> <input style="font-size: 18px" type="submit" value="Agregar" /> </p>
                            </td>
                        </tr>
                    </table>
                </form>
            <%@ include file="listarPersonal.jsp" %>
         </div>
     </div>    
<% 
    }
    else{
         response.sendRedirect("");
    }

%>
<%@ include file="footer.jsp" %> 

