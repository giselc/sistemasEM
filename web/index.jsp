<%-- 
    Document   : index
    Created on : Mar 18, 2016, 9:55:26 AM
    Author     : Gisel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <link rel="stylesheet" href="css/login.css" type="text/css"/>
        <title>Sistemas Internos E.M.</title>
        
<script src="js/JIC.js" type="text/javascript"></script>
<script src="js/demo.js" type="text/javascript"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <body>
        <% 
        HttpSession sesion = request.getSession();
        if(sesion.getAttribute("usuarioID")!=null){
                response.sendRedirect("sistemas.jsp");
        }
        else{
            %>
             <table  width="100%">
                <tr>
                	<div style="position:absolute; width:80%; z-index:0">
                            <p align="center" ><img src="images/chapaderecha6.png" width="100%" height="400px"/></p>
                        </div>
                <div style="position:absolute; z-index:-1">
                            <p align="center"><img src="images/photo1.jpg" /></p>
                        </div>
                    <td width="100%" class="login" style="padding:0px; " valign="top">
                        <table width="100%">
                        <td>
                            <div style="position:absolute; width:30%;padding-top:115px;padding-left: 45%">
                                <form method="POST" action="login" style="height:400px;" > 
                            <table border="0" width="80%" cellpadding="0px" cellspacing="0px" align="center">   
                                    <tr> 
                                    <td > <p align='center' style='margin:0px'><img src='images/usuarioChapa2.png' width='35%'/></p> </td> 
                                    </tr>
                                    <tr>	
                                        <td  align='center'><input style='width:80% ; font-size: 100%;color: #333333;background-color: #cccccc' type='Text' name='user' required='required'></td> 
                                    </tr> 
                                    <tr> 
                                        <td> <p align='center' style='margin:0px'><img src='images/contrasenaChapa.png' width='55%'/></p></td> 
                                    </tr>
                                    <tr>	
                                        <td align='center'><input align='middle' style='width:80%; font-size: 100%;color: #333333;background-color: #cccccc' type='password' name='password'></td>
                                </tr> 
                                <tr> 
                                    <td style="padding-top:5px;" colspan="2" align="center"><input name="Submit" style="font-size: 90%" type="Submit" value='Entrar' ></td> 
                                </tr> 
                                <tr>
                    <% 
                            Object loginStatus = sesion.getAttribute("login");
                                 if (loginStatus!=null && loginStatus.equals("incorrecto")){
                                     out.println("<td><h4 style='margin:0px;color: #ffff33;font-size: 100%'>USUARIO O CONTRASE&Ntilde;A INCORRECTO</h4></td>");
                                      %>
                                    <script>
                                        alert("Usuario o contraseña incorrecto")
                                    </script>
                                     <%
                                 }
                                 else if(loginStatus!=null && loginStatus.equals("vacio")){
                                     out.println("<td><h4 style='margin:0px;color: #ffff33;font-size: 100%'>DEBE INICAR SESI&Oacute;N</h4></td>");
                                     %>
                                    <script>
                                        alert("Debe iniciar sesión")
                                    </script>
                                     <%
                                 }

                    %>
                </tr>
                             </table>
                            </form>    
                            </div>
                        </td>
                        </table>
                    </td>
                </tr>
            </table>
        <%
        }
        %>
    </body>
</html>
