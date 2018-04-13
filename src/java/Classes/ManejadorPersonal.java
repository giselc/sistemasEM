/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Gisel
 */
public class ManejadorPersonal {
    private Connection connection;

    public ManejadorPersonal() {
        connection = ConexionDB.GetConnection();
    }
    
    public String getFiltroSQL(RecordCadetesFiltro rc){ 
        ManejadorCodigoBD mc =  new ManejadorCodigoBD(); 
        String filtro = "";
        String filtroMostrar = "";
        if (rc!=null){
            if(rc.lmga.equals("S")){
                filtro+= " and lmga = 1";
                filtroMostrar += "LMGA = SI - ";
            }
            else{
                if (rc.lmga.equals("N")){
                    filtro+= " and lmga = 0";
                    filtroMostrar += "LMGA = NO - ";
                }
            }
            if (rc.sexo.equals("M")){
                filtro+=" and sexo= 'M'";
                filtroMostrar += "SEXO = M - ";
            }
            else{
                if(rc.sexo.equals("F")){
                    filtro+=" and sexo= 'F'";
                    filtroMostrar += "SEXO = F - ";
                }
            }
            if(rc.repitiente.equals("S")){
                filtro+= " and repitiente = 1";
                filtroMostrar += "REPITIENTE = SI - ";
            }
            else{
                if (rc.repitiente.equals("N")){
                    filtro+= " and repitiente = 0";
                    filtroMostrar += "REPITIENTE = NO - ";
                }
            }
            if (rc.canthijos!=null){
                filtroMostrar += "HIJOS = ";
                int i=0;
                for (String s : rc.canthijos){
                    if(s.equals("4")){
                        filtroMostrar += "+ de 3,";
                    }
                    else{
                        filtroMostrar += s+",";
                    }
                    if (i==0){
                        filtro += " and (hijos= "+s;
                    }
                    else{
                        filtro += " or hijos= "+s;
                    }
                    i++;
                    if(i==rc.canthijos.length){
                         filtro += ")";
                    }
                    
                    
                }
                filtroMostrar= filtroMostrar.substring(0, filtroMostrar.lastIndexOf(","));
                filtroMostrar += " - ";
            }
        }
        Departamento d;
        if(rc.depNac!=null){
            filtroMostrar += "DEPARTAMENTO NACIMIENTO = ";
            int i= 0;
            for (String s : rc.depNac){
                if (i==0){
                    filtro += " and (departamentoNac= "+s;
                }
                else{
                    filtro += " or departamentoNac= "+s;
                }
                i++;
                if(i==rc.depNac.length){
                     filtro += ")";
                }
                d= mc.getDepartamento(Integer.valueOf(s));
                filtroMostrar += d.getDescripcion()+",";
            }
            filtroMostrar= filtroMostrar.substring(0, filtroMostrar.lastIndexOf(","));
            filtroMostrar += " - ";
        }
        if(rc.depDom!=null){
            filtroMostrar += "DEPARTAMENTO DOMICILIO = ";
            int i=0;
            for (String s : rc.depDom){
                if (i==0){
                    filtro += " and (departamento= "+s;
                }
                else{
                    filtro += " or departamento= "+s;
                }
                i++;
                if(i==rc.depDom.length){
                     filtro += ")";
                }
                d= mc.getDepartamento(Integer.valueOf(s));
                filtroMostrar += d.getDescripcion()+",";
            }
            filtroMostrar= filtroMostrar.substring(0, filtroMostrar.lastIndexOf(","));
            filtroMostrar += " - ";
        }
        Curso c;
        if(rc.cursos!=null){
            filtroMostrar += "CURSOS = ";
            int i=0;
            for (String s : rc.cursos){
                if (i==0){
                    filtro += " and (curso= "+s;
                }
                else{
                    filtro += " or curso= "+s;
                }
                i++;
                if(i==rc.cursos.length){
                     filtro += ")";
                }
                c= mc.getCurso(Integer.valueOf(s));
                filtroMostrar += c.getDescripcion()+",";
            }
            filtroMostrar= filtroMostrar.substring(0, filtroMostrar.lastIndexOf(","));
            filtroMostrar += " - ";
        }
        Arma a;
        if(rc.armas!=null){
            filtroMostrar += "ARMAS = ";
            int i=0;
            for (String s : rc.armas){
                if (i==0){
                    filtro += " and (arma= "+s;
                }
                else{
                    filtro += " or arma= "+s;
                }
                i++;
                if(i==rc.armas.length){
                     filtro += ")";
                }
                a= mc.getArma(Integer.valueOf(s));
                filtroMostrar += a.getDescripcion()+",";
            }
            filtroMostrar= filtroMostrar.substring(0, filtroMostrar.lastIndexOf(","));
            filtroMostrar += " - ";
        }
        if (filtroMostrar.contains(" - ")){
            rc.filtroMostrar = filtroMostrar.substring(0, filtroMostrar.lastIndexOf(" - "));
        }
        else{
            rc.filtroMostrar = "";
        }
        return filtro;
    }
    
    public ArrayList<Personal> getPersonalListar(RecordCadetesFiltro rf, int usuario, TipoPersonal tp){
        ArrayList<Personal> al= new ArrayList<>();
        ManejadorCodigoBD mc = new ManejadorCodigoBD();
        try {
            Statement s= connection.createStatement();
            Usuario u = mc.getUsuario(usuario);
            String sql = "";
            String filtro = getFiltroSQL(rf);
            if(u.isAdmin() || u.getPermisosPersonal().getId()==1){
                sql="SELECT * FROM personal left-join grado on personal.idgrado = grado.codigo where tipoPersonal = "+tp.getId()+ " order by grado.codigo asc";
            }
            else{
                return null;
            }
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return al;
    }
}
