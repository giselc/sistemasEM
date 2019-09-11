                                </td>
                                <td style="width: 5%"></td>
                            </tr>
                        </table>
                        <%
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
