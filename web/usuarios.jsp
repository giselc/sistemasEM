<%-- 
    Document   : usuarios
    Created on : Mar 2, 2017, 9:15:15 PM
    Author     : Gisel
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Manejadores.ManejadorCodigoBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<% 
    if(u.isAdmin()){

%>

<script>
    function listar(form) {//Funcion creada para no perder la sesion luego del submit
        form.submit();
        return false;
    };
    function confirmar(f,usuario){
        if(usuario=="admin"){
            alert("ERROR! El usuario admin no puede ser eliminado");
            return false;
        }
        var s="¿Seguro que desea eliminar el usuario: ";
        var s1= s.concat(usuario,"?");
        var r=confirm(s1);
        if (r==true)
        {
            f.submit();
            return true;
        }
        else{
            return false;
        }
    };
</script>
<form method="post" target="_blank" onsubmit="return listar(this)" name="formListar" action='Usuario?listar=usuarios'>
    
    <table style="float: right">
        <tr>
            <td>
            <p id="mensaje" style="color: #990000"><% if(session.getAttribute("mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+session.getAttribute("mensaje"));}%></p>
            <%
                session.setAttribute("mensaje",null);
            %>
            </td>
        </tr>
        <tr>
            <td style="width: 55%"><h3 style="float: left; font-family: sans-serif">Usuarios del sistema:</h3></td>
            <td style="width: 15%"><p align="left"><a href="javascript:history.go(-1)"><img src="images/atras.png" width="15%"/></a></p></td>
            <td style="width: 15%"><a href="usuario.jsp" title="Agregar Usuario"><img width="30%" src='images/agregarLista.png' /></a> </td>
        </tr>
    </table>
</form>    
    <table style="width: 100%;" align='center'>
            <%
                ManejadorCodigoBD mcbd = new ManejadorCodigoBD();
                ArrayList<Usuario> au = mcbd.getUsuarios(u);
                out.print("<tr style='background-color:#ffcc66'>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Usuario</h3></td>");
                            out.print("<td style='width: 15%' align='center'><h3 style='margin:2%;'>Nombre para mostrar</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Admin</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Personal</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Descuento</h3></td>");
                            out.print("<td style='width: 5%' align='center'><h3 style='margin:2%;'>Notas</h3></td>");
                            out.print("<td style='width: 5%' align='center'><h3 style='margin:2%;'>Habilitaci&oacute;n</h3></td>");
                            out.print("<td style='width: 5%' align='center'><h3 style='margin:2%;'>Profesor</h3></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                       out.print("</tr>" );
                int i=0;
                String color;
                for (Usuario u1: au){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;

                    out.print("<tr style='background-color:"+color+"'>");
                    out.print("<td style='width: 10%' align='center'>"+u1.getNombre()+"</td>");
                    out.print("<td style='width: 15%' align='center'>"+u1.getNombreMostrar()+"</td>");
                    out.print("<td style='width: 10%' align='center'>");
                    if(u1.isAdmin()){
                       out.print("SI");
                    }
                            
                    out.print("</td>");
                    out.print("<td style='width: 10%' align='center'>");
                        if(u1.getPermisosPersonal()!=null){
                            switch( u1.getPermisosPersonal().getId()){
                                case 1: out.print("Cadetes");break;
                                case 2: out.print("P.S.");break;
                                case 3: out.print("Oficiales");break;
                            }
                        }
                    out.print("</td>");
                    out.print("<td style='width: 10%' align='center'>");
                        if(u1.getPermisosDescuento()!=null){
                            switch( u1.getPermisosDescuento().getId()){
                                case 1: out.print("Biblioteca");break;
                                case 2: out.print("Impresiones");break;
                                case 3: out.print("Farmacia");break;
                                case 4: out.print("Enfermer&iacute;a");break;
                                case 5: out.print("Casino SS.OO.");break;
                                case 6: out.print("Casino CC.");break;
                            }
                        }
                    out.print("</td>");
                    out.print("<td style='width: 5%' align='center'>");
                    if(u1.isNotas()){
                       out.print("SI");
                    }
                            
                    out.print("</td>");
                    out.print("<td style='width: 5%' align='center'>");
                    if(u1.isHabilitacion()){
                       out.print("SI");
                    }
                            
                    out.print("</td>");
                    out.print("<td style='width: 5%' align='center'>");
                    if(u1.isProfesor()){
                       out.print("SI");
                    }
                            
                    out.print("</td>");
                    out.print("<td style='width: 10%' align='center'><a href='usuario.jsp?id="+String.valueOf(u1.getId())+"'><img src='images/ver.png' width='25%' /></a></td>");
                    out.print("<td style='width: 10%' align='center'><form method='post' onsubmit=\"return confirmar(this,'"+u1.getNombre()+"')\" action='Usuario?elim="+u1.getId()+"'><input type='image' width='25%' title='Eliminar Usuario' src='images/eliminar.png' alt='Submit Form' /> </form></td>");
                    out.print("<td style='width: 10%' align='center'><a href='cambiarContrasena.jsp?id="+String.valueOf(u1.getId())+"'><img src='images/pass.png' width='25%' /></a></td>");
                    out.print("</tr>");
                }
            %> 
                
    </table>
        
<% 
    }
    else{
        sesion.setAttribute("Mensaje","Lo sentimos, no tiene permisos para acceder a esta p&aacute;gina. Contacte al administrador.");
         response.sendRedirect("");
    }

%>
<%@ include file="footer.jsp" %>