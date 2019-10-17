                                </td>
                                <td style="width: 5%"></td>
                            </tr>
                        </table>
                        <%
                            }
                            else{
                                sesion.setAttribute("usuarioID", null);
                                sesion.setAttribute("login", "perdidaDeSesion");
                                response.sendRedirect("");
                            }
                        }
                        else{
                            sesion.setAttribute("login", "vacio");
                            response.sendRedirect("");
                        }
                    %>    
            </td>
            </tr>
        </table>
    </body>
</html>
