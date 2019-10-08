<%@ include file="header.jsp" %>
<script>
    function validarContrasena(f){
        if (f.elements["contraNue"].value != f.elements["contraNue2"].value){
            document.getElementById("textocontrasena").innerHTML = "No coinciden las contraseñas."
            return false;
        }
        return f.submit();
    }
</script>
    <table  width='70%' style="font-size: 130%">
        <tr>
            <td>
                <img src="images/pass.png" />
            </td>
            <td>
                <form method="post" action="Usuario?pass=<%= request.getParameter("id") %>" onsubmit="return validarContrasena(this);">
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
                    </table>
                    <p align="right"><input type="submit" value="CAMBIAR"/> </p>
                    <h3 id="textocontrasena"><%
                        if (session.getAttribute("mensaje")!=null){
                            out.print(session.getAttribute("mensaje"));
                            session.setAttribute("mensaje", null);
                        }
                        
                    %></h3>
                </form>
            </td>
        </tr>
    </table>
                                
<%@ include file="footer.jsp" %> 
