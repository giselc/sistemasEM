<%-- 
    Document   : camposListar
    Created on : Nov 29, 2018, 9:14:26 AM
    Author     : Gisel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<form method="post" target="_blank" onsubmit="return listar(this)" id="formCadeteListar" name="formCadeteListar" action='Listar?tipoPersonal=<%=tipo %>'>
    <table style="font-size: 70%">
        <tr>
            <td>
                Carrera:
            </td>
            <td>
                <input name="carrera" type="checkbox" />
            </td>
        </tr>
        <tr>
            <td>
                N&uacute;mero:
            </td>
            <td>
                <input name="numero" type="checkbox"/>
            </td>
        </tr>
        <tr>
            <td>
                C.I.:
            </td>
            <td>
                <input name="ci" type="checkbox" checked="checked"/>
            </td>
        </tr>
        <tr>
            <td>
                Grado:
            </td>
            <td>
                <input name="grado" type="checkbox" checked="checked"/>
            </td>
        </tr>
        <tr>
            <td>
                Primer nombre:
            </td>
            <td>
                <input name="primerNombre" type="checkbox" checked="checked"/>
            </td>
        </tr>
        <tr>
            <td>
                Segundo nombre:
            </td>
            <td>
                <input name="segundoNombre" type="checkbox"/>
            </td>
        </tr>
        <tr>
            <td>
                Primer apellido:
            </td>
            <td>
                <input name="primerApellido" type="checkbox" checked="checked"/>
            </td>
        </tr>
        <tr>
            <td>
                Segundo apellido:
            </td>
            <td>
                <input name="segundoApellido" type="checkbox"/>
            </td>
        </tr>
        <tr>
            <td>
                Curso:
            </td>
            <td>
                <input name="curso" type="checkbox" />
            </td>
        </tr>
        <tr>
            <td>
                Arma:
            </td>
            <td>
                <input name="arma" type="checkbox" />
            </td>
        </tr>
        <tr>
            <td>
                LMGA:
            </td>
            <td>
                <input name="lmga" type="checkbox" />
            </td>
        </tr>
        <tr>
            <td>
                Pase directo:
            </td>
            <td>
                <input name="pd" type="checkbox" />
            </td>
        </tr>      
        <tr>
            <td>
                Sexo:
            </td>
            <td>
               <input name="sexo" type="checkbox" />
            </td>
        </tr>        
        <tr>
            <td>
                Departamento Nacimiento:
            </td>
            <td>
                <input name="dptoNac" type="checkbox" />
            </td>
        </tr>
        <tr>
            <td>
                Localidad Nacimiento:
            </td>
            <td>
                <input name="localidadNac" type="checkbox" />
            </td>
        </tr>  
        <tr>
            <td>
                Departamento Domicilio:
            </td>
            <td>
                <input name="dptoDom" type="checkbox" />
            </td>
        </tr>  
        <tr>
            <td>
                Localidad Domicilio:
            </td>
            <td>
                <input name="localidadDom" type="checkbox" />
            </td>
        </tr>  
        <tr>
            <td>
                Repitiente:
            </td>
            <td>
                <input name="repitiente" type="checkbox" />
            </td>
        </tr>
        <tr>
            <td>
                Cantidad de hijos:
            </td>
            <td>
                <input name="cantHijos" type="checkbox" />
            </td>
        </tr>
        <tr>
            <td>
                Talle Operacional:
            </td>
            <td>
                <input name="talleOperacional" type="checkbox" />
            </td>
        </tr>
        <tr>
            <td>
                Talle Botas:
            </td>
            <td>
                <input name="talleBotas" type="checkbox" />
            </td>
        </tr>
        <tr>
            <td>
                Talle Quepi
            </td>
            <td>
                <input name="talleQuepi" type="checkbox" />
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: right">
               <input type="submit" value="Aplicar"/>
            </td>
        </tr>
    </table>
</form>

