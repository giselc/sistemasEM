<%-- 
    Document   : grilla
    Created on : 18/06/2019, 10:51:03
    Author     : Gisel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<table id="grillaFaltas" style="border-collapse: separate;border-spacing: 2px;text-align: center;vertical-align: central;">
    <%
    Calendar cal = new GregorianCalendar(fecha1.get(java.util.Calendar.YEAR), mes-1, 1); 
    // Get the number of days in that month 
    int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28

    %>
    <tr >
        <td>

        </td>
        <td colspan="<%= days %>" style='background-color:#ffcc66;padding:0px;'>
                D&iacute;as:
        </td>
    </tr>
    <tr style='background-color:#ffcc66;padding:0px;'>
        <td>
            Alumnos:
        </td>
        <%

        for (int j=1; j<=days;j++){
            out.print("<td style=\"width: 3%\">"+j+"</td>");
        }
out.print("</tr>"); 
i=0;
        HashMap<Integer,LinkedList<Falta>> grilla;
        for(LibretaIndividual l: d.getLibretasIndividuales().values()){
            if ((i%2)==0){
                color=" #ccccff";
            }
            else{
                color=" #ffff99";
            }
            i++;
out.print("<tr style=\"background-color:"+color+";\">"
                    + "<td>"
                    + l.getAlumno().getPrimerApellido()+ " " + l.getAlumno().getPrimerNombre() 
                    +"</td>");

            grilla=l.getGrillaFaltas().get(mes);
            if(grilla==null){
                for (int j=1; j<=days;j++){
                    out.print("<td>");

                    out.print("</td>");
                } 
            }
            else{
               for (int j=1; j<=days;j++){
                    out.print("<td>");
                    if(grilla.get(j)!=null){
                        for(Falta f:grilla.get(j)){
                            out.print("<b title='Fecha: "+f.getFecha()+"&#10;C&oacute;digo: "+f.getCodigoMotivo()+"&#10;Cantidad de horas: "+f.getCanthoras()+"&#10;Observaciones: "+f.getObservaciones()+"'>"+f.getCodigoMotivo()+"<image src='grilla.jsp' width='10%' id='"+ f.getId() +"' style='display:block;'/> </b>");
                        }

                    }

                    out.print("</td>");
                } 
            }

out.print("</tr>");

       }

    %>
</table>
