<%-- 
    Document   : datosBasicos
    Created on : Jul 25, 2018, 8:12:36 AM
    Author     : Gisel
--%>

<%@page import="Classes.Curso"%>
<%@page import="Classes.Arma"%>
<%@page import="Classes.Grado"%>
<%@page import="Classes.EstadoCivil"%>
<%@page import="Classes.Departamento"%>
<%@page import="java.util.HashMap"%>
<%@page import="Manejadores.ManejadorCodigos"%>
<%@page import="Classes.Cadete"%>
<script>
    function PreviewImage() {
            var oFReader = new FileReader();
            oFReader.readAsDataURL(document.getElementById("uploadImage").files[0]);
            oFReader.onload = function (oFREvent) {
                document.getElementById("uploadPreview").src = oFREvent.target.result;
            };   
        };
        function canvasimage(input,canv) {
            var canvas= document.getElementById(canv);
            var ctx = canvas.getContext('2d');
            var img = new Image;
            var oFReader = new FileReader();
            oFReader.readAsDataURL(document.getElementById(input).files[0]);
            oFReader.onload = function (oFREvent) {
                img.src = oFREvent.target.result;
            };
            
            img.onload = function() {
                var MAX_WIDTH = 800;
                var MAX_HEIGHT = 600;
                var width = img.width;
                var height = img.height;
                if (width > height) {
                  if (width > MAX_WIDTH) {
                    height *= MAX_WIDTH / width;
                    width = MAX_WIDTH;
                  }
                } else {
                  if (height > MAX_HEIGHT) {
                    width *= MAX_HEIGHT / height;
                    height = MAX_HEIGHT;
                  }
                }
                canvas.width = width;
                canvas.height = height;
                ctx.drawImage(img, 0, 0, width, height);
            };

        }
</script>
<div id="loader" style="position: fixed; top:0; left:0; width:100%; height: 100%;background: url('images/loading-verde.gif') center center no-repeat; background-size: 10%"></div>
<form method="post"  name="formulario" id="formulario" onsubmit="<% if (request.getParameter("ci")==null){out.print("alert('Se habilitó la pestaña de Datos Patronímicos');");} %>;" <% if (request.getParameter("ci")==null){ out.print("action='Cadete'");} else{out.print("action='Cadete?ci"+request.getParameter("ci")+"'");} %> enctype="multipart/form-data">
    <table style="width: 100%; text-align: center">
    <tr>
            <td>
                <%
                if(c!=null){
                    %>
                    <p align="center"><label for="uploadImage" ><img src="<%=request.getContextPath()%>/Imagenes?foto=<%=c.getCi()%>" id="uploadPreview" style="width: 20%" onclick=""/></label></p>
                    <%
                }
                else{
                    %>
                    <p align="center"><label for="uploadImage" ><img src="images/silueta.jpg" id="uploadPreview" style="width: 20%" onclick=""/></label></p>
                <%}
                %>
                
            </td>
        </tr>
    </table>
    <table border="0">
        
        <tr>
            <p id="mensaje" style="color: #990000"><% if(session.getAttribute("Mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+session.getAttribute("Mensaje"));}%></p>
            <%
                session.setAttribute("Mensaje",null);
            %>
        </tr>
        <tr>
            <h3>Datos Personales:</h3>
        </tr>
        <tr>
            <td>Foto tipo pasaporte: </td>
            <td><input type="file" name="foto" id="uploadImage" onchange="PreviewImage(); canvasimage('uploadImage','canvas6');" accept="image/*"/></td>
            <td><canvas id="canvas6" hidden="hidden" > </canvas></td>
        </tr>
        <tr  <% 
                if(c==null){out.println("hidden='hidden'");} %>>
            <td>ID: </td>
            <td><input type="text" name="id" value="<%if(c!=null){out.print(c.getNroInterno());} %>" size="3" style="text-align: center" readonly/>
                <input type="text" name="carreraString" value="<%if(c!=null){out.print(c.getCarrera().getDescripcion());} %>" size="30" style="text-align: center" readonly/>
            </td>
            
        </tr>
        <tr>
            <td>C.I.: </td>
            <td><input type=number name="ci" size="8" maxlength="8" value="<%if( c!=null){ out.print(Integer.valueOf(c.getCi()));} %>" required="required"/></td>
        </tr>
        <tr>
            <td>Grado: </td>
            <td>
                <select name="grado" form="formulario" required="required">
                    <%
                    HashMap<Integer,Grado> ag = mc.getGrados();
                    for(Grado dep: ag.values() ){
                        if(dep.getIdTipoPersonal().getId()==1){//cadetes
                            String s="";
                            if(c!=null && c.getGrado().getId()==dep.getId()){
                                s="selected";
                            }
                            out.print("<option " + s +" value='"+String.valueOf(dep.getId()) +"'>"+ dep.getDescripcion() +"</option>");
                        }
                    }
                    %>
                 </select>
            </td>
        </tr>
        <tr>
            <td>Arma: </td>
            <td>
                <select name="arma" form="formulario" required="required">
                    <%
                    HashMap<Integer,Arma> aa = mc.getArmas();
                    String s="";
                    System.out.print(c.getArma());
                    if(c!=null && c.getArma()==null){
                        s="selected";
                    }
                    out.print("<option "+s+" value='-1'>------</option>");
                    for(Arma dep: aa.values() ){
                        s="";
                        if(c!=null && c.getArma()!=null && c.getArma().getId()==dep.getId()){
                            s="selected";
                        }
                        out.print("<option " + s +" value='"+String.valueOf(dep.getId()) +"'>"+ dep.getDescripcion() +"</option>");
                    }
                    
                    %>
                 </select>
            </td>
        </tr>
        <tr>
            <td>Curso: </td>
            <td>
                <select name="curso" form="formulario" required="required">
                    <%
                    HashMap<Integer,Curso> ac = mc.getCursos();
                    for(Curso dep: ac.values() ){
                        s="";
                        if(c!=null && c.getCurso().getId()==dep.getId()){
                            s="selected";
                        }
                        out.print("<option " + s +" value='"+String.valueOf(dep.getId()) +"'>"+ dep.getDescripcion() +"</option>");
                    }
                    %>
                 </select>
            </td>
        </tr>
        <tr>
            <td>Derecha: </td>
            <td><input type=number name="derecha" size="8" maxlength="8" value="<%if( c!=null){ out.print(Integer.valueOf(c.getDerecha()));} %>" required="required"/></td>
        </tr>
        <tr>
            <td>Primer nombre: </td>
            <td><input type="text" name="primerNombre" value="<%if( c!=null){out.print(c.getPrimerNombre());} %>" size="50" required="required"/></td>
        </tr>
        <tr>
            <td>Segundo nombre: </td>
            <td><input type="text" name="segundoNombre" value="<%if( c!=null){out.print(c.getSegundoNombre());} %>" size="50"/></td>
        </tr>
        <tr>
            <td>Primer apellido: </td>
            <td><input type="text" name="primerApellido" value="<%if( c!=null){out.print(c.getPrimerApellido());} %>" size="50" required="required"/></td>
        </tr>
        <tr>
            <td>Segundo apellido: </td>
            <td><input type="text" name="segundoApellido" value="<%if( c!=null){out.print(c.getSegundoApellido());} %>" size="50" required="required"/></td>
        </tr>
        <tr>
            <td>Sexo: </td>
            <td>M: <input type="radio" name="sexo[]" value="M" <% if ((c!=null && c.getSexo().equals("M"))||(c==null)){ out.print("checked='checked'");} %>/>
                F: <input type="radio" name="sexo[]" value="F" <% if ((c!=null && c.getSexo().equals("F"))){ out.print("checked='checked'");} %> /></td>
        </tr>
        <tr>
            <td>Fecha de Nacimiento: </td>
            <td><input type=date name="fechaNac" size="8" value="<%if( c!=null){out.print(c.getFechaNac());} %>"required="required"/></td>
        </tr>
        <tr>
            <td>Departamento de Nacimiento: </td>
            <td>
                <select name="departamentoNac" form="formulario" required="required">
                    <%
                    HashMap<Integer,Departamento> ad = mc.getDepartamentos();
                    for(Departamento dep: ad.values() ){
                        s="";
                        if(c!=null && c.getDepartamentoNac().getCodigo()==dep.getCodigo()){
                            s="selected";
                        }
                        out.print("<option " + s +" value='"+String.valueOf(dep.getCodigo()) +"'>"+ dep.getDescripcion() +"</option>");
                    }
                    %>
                 </select>
            </td>
        </tr>
        <tr>
            <td>Localidad de Nacimiento: </td>
            <td><input type=text name="localidadNac" value="<%if( c!=null){out.print(c.getLocalidadNac());} %>" size="50" required="required"/></td>
        </tr>
        
        <tr>
            <td>Credencial: </td>
            <td>Serie: <input type=text name="cc" size="8" value="<%if( c!=null){out.print(c.getCc());} %>"/>
                N&deg;: <input type=number name="CCNro" size="8" value="<%if( c!=null){out.print(c.getCcNro());} %>"/></td>
        </tr>
        <tr>
            <td>Estado Civil: </td>
            <td>
                <select name="estadoCivil" form="formulario" required="required">
                    <%
                    HashMap<Integer,EstadoCivil> ae = mc.getEstadosCiviles();
                    for(EstadoCivil ec: ae.values() ){
                        s="";
                        if(c!=null && c.getEstadoCivil().getCodigo()==ec.getCodigo()){
                            s="selected";
                        }
                        out.print("<option " + s +" value='"+String.valueOf(ec.getCodigo()) +"'>"+ ec.getDescripcion() +"</option>");
                    }
                    %>
                 </select>
            </td>
        </tr>
        <tr>
            <td>Domicilio: </td>
            <td><input type=text name="domicilio" value="<%if( c!=null){out.print(c.getDomicilio());} %>" size="50"/></td>
        </tr>
        <tr>
            <td>Departamento: </td>
            <td>
                <select name="departamento" form="formulario">
                    <%
                    HashMap<Integer,Departamento> ad1 = mc.getDepartamentos();
                    for(Departamento dep1: ad1.values() ){
                        s="";
                        if(c!=null && c.getDepartamento().getCodigo()==dep1.getCodigo()){
                            s="selected";
                        }
                        out.print("<option " + s +" value='"+String.valueOf(dep1.getCodigo()) +"'>"+ dep1.getDescripcion() +"</option>");
                    }
                    %>
                 </select>
            </td>
        </tr>
        <tr>
            <td>Localidad: </td>
            <td><input type=text name="localidad" value="<% if( c!=null){out.print(c.getLocalidad());} %>" size="50"/></td>
        </tr>
        <tr>
            <td>Tel&eacute;fono: </td>
            <td><input type=text name="telefono" size="8" value="<% if(c!=null){out.print(c.getTelefono());} %>" required="required"/></td>
        </tr>
        <tr>
            <td>Correo Electr&oacute;nico: </td>
            <td><input type=text name="email" size="50" value="<%if( c!=null){out.print(c.getEmail());} %>" required="required"/></td>
        </tr>
        <tr>
            <td>Repitiente: </td>
            <td><input  type=checkbox name="repitiente" <% if(c!=null && c.isRepitiente()){out.print("checked='checked'");} %>/></td>
        </tr>
        
        <% int i = Integer.parseInt(session.getAttribute("usuarioID").toString());%>
        <tr>
            <td>LMGA: </td>
            <td><input  id="lmga" onchange="showPaseDirecto(<% out.print(session.getAttribute("usuarioID").toString()); %> , <%out.print(u.isAdmin()); %>);" type=checkbox name="lmga" <% if(c!=null && c.isLmga()){out.print("checked='checked'");} %>/></td>
        </tr>
        <tr id="pd" <% if (((c!=null && !c.isLmga())||(c==null)) ) {out.print("style='display: none'");} %> >
            <td>Pase directo: </td>
            <td><input id="paseDirecto" onchange="showNotaPaseDirecto(<% out.print(session.getAttribute("usuarioID").toString()); %> , <%out.print(u.isAdmin()); %>);" type=checkbox name="paseDirecto" <% if(c!=null && c.isPaseDirecto()){out.print("checked='checked'");} %>/></td>
        </tr>
        <tr id="notaPaseDirecto" <% if (((c!=null && !c.isPaseDirecto())||(c==null)) ) {out.print("style='display: none'");} %> >
            <td>Nota: </td>
            <td><input type=number name="notaPaseDirecto" step="0.001" value="<% if( c!=null){out.print(c.getNotaPaseDirecto());}else{out.print("0");} %>"/></td>
        </tr>
        <%--<tr>
            <td>Presenta certificado de Buena Conducta: </td>
            <td><input type=checkbox name="buenaConducta" <% if(p!=null && p.isBuenaConducta()){out.print("checked='checked'");} %>/></td>
        </tr>--%>
        <tr id="hijos" >
            <td>Cantidad de hijos: </td>
            <td>
                0:
                <input type=radio name="hijos[]" value="0" <% if((c!=null && c.getHijos()==0) || (c==null) ){out.print("checked='checked'");} %>/>
                1:
                <input type=radio name="hijos[]" value="1" <% if(c!=null && c.getHijos()==1){ out.print("checked='checked'");} %>/> 
                2:
                <input type=radio name="hijos[]" value="2" <% if(c!=null && c.getHijos()==2){ out.print("checked='checked'");} %>/> 
                3:
                <input type=radio name="hijos[]" value="3" <% if(c!=null && c.getHijos()==3){ out.print("checked='checked'");} %>/> 
                + de 3:
                <input type=radio name="hijos[]" value="4" <% if(c!=null && c.getHijos()==4){ out.print("checked='checked'");} %>/> 
            
            </td>

        </tr>
        <tr>
            <td>Fecha de alta en el sistema: </td>
            <td><input type=date name="fechaAlta" size="8" value="<%if( c!=null){out.print(c.getFechaNac());} %>"required="required"/></td>
        </tr>
        <tr>
            <td>Observaciones: </td>
            <td>
                <textarea rows="4" cols="50" name="observaciones" form="formulario"><% if(c!=null){out.print(c.getObservaciones());} %></textarea>
            </td>
        </tr>
        
    </table>
    <p align="right"> <input style="font-size: 18px" type="submit" <% if (request.getParameter("ci")==null){ out.print("value='Guardar y Continuar'");} else{out.print("value='Modificar'");} %> /> </p>
                                             
</form>
