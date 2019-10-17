<%@ include file="header.jsp" %>
<script>
    function validarContrasena(f,admin){
        if (f.elements["contraNue"].value != f.elements["contraNue2"].value){
            alert("ERROR! No coinciden las contraseñas.");
            return false;
        }
        else{
            if (!admin && f.elements["contraAnt"].value == f.elements["contraNue"].value){
                alert("ERROR! Introduzca una contraseña diferente a la anterior.");
                return false;       
            }
        }
        return f.submit();
    }
</script>
<p id="mensaje" style="color: #ffffff"><% if(sesion.getAttribute("Mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+sesion.getAttribute("Mensaje"));}%></p>
<%
    sesion.setAttribute("Mensaje",null);
%>
    <table  width='70%' style="font-size: 130%">
        <tr>
            <td>
                <img src="images/pass.png" />
            </td>
            <td>
                <form method="post" action="Usuario?pass=<%= request.getParameter("id") %>" onsubmit="return validarContrasena(this,<%= u.isAdmin() %>);">
                    <table>
                        <% 
                            if(!u.isAdmin()){

                        %>
                        <tr>
                            <td>
                                <p><b>Contrase&ntilde;a anterior:</b></p>
                            </td>
                            <td>
                                <p align="center"><input name="contraAnt" type="password" /></p>
                            </td>
                        </tr>
                        <% 
                            }

                        %>
                        <tr>
                            <td>
                                <p><b>Nueva contrase&ntilde;a:</b></p>
                            </td>
                            <td>
                                <p align="center"><input name="contraNue" type="password" /></p>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p><b>Repita nueva contrase&ntilde;a:</b></p>
                            </td>
                            <td>
                                <p align="center"><input name="contraNue2" type="password" /></p>
                            </td>
                        </tr>
                        <%
                            if(u.isAdmin()){
                        %>
                        <tr>
                            <td>
                                <p><b>Usuario debe cambiar la contrase&ntilde;a:</b></p>
                            </td>
                            <td>
                                <input  checked="checked" type=checkbox name="cambiarContra" />
                            </td>
                        </tr>
                        <%
                        }
                        %>
                    </table>
                    <p align="right"><input type="submit" value="CAMBIAR"/> </p>
                    
                </form>
            </td>
        </tr>
    </table>
                                
<%@ include file="footer.jsp" %> 
