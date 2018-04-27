/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gisel
 */
public class ManejadorPersonal {
    private Connection connection;

    public ManejadorPersonal() {
        connection = ConexionDB.GetConnection();
    }
    public boolean AgregarCadetesTXT(){
        File archivo = null;
      FileReader fr = null;
      BufferedReader br = null;

      try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         archivo = new File ("C:\\Consulta1.txt");
         
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         // Lectura del fichero
         String linea;
         int i=0;
         String [] campos;
         ManejadorCodigos mc= ManejadorCodigos.getInstance();
         while((linea=br.readLine())!=null){
            campos= linea.split(";");
            
         }
      }
      catch(Exception e){
         e.printStackTrace();
      }
      return true;
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
    
    public boolean agregarPersonal(Personal p){
        java.util.Calendar fecha1 = java.util.Calendar.getInstance();
        int mes = fecha1.get(java.util.Calendar.MONTH)+1;
        String fecha=  fecha1.get(java.util.Calendar.YEAR)+"-"+mes+"-"+fecha1.get(java.util.Calendar.DATE); //usada para log
        String sql = "INSERT INTO sistemasem.personal (numero,ci,idGrado,idArma, PrimerNombre, PrimerApellido, SegundoNombre, SegundoApellido,  OBSERVACIONES, profesor) values (?,?,?,?,?,?,?,?,?,?)";
        int i=1;
        try {
            PreparedStatement statement= connection.prepareStatement(sql); // sql a insertar en postulantes
            statement.setInt(i++,p.getNroInterno());
            statement.setInt(i++,p.getCi());
            if(p.getGrado()!=null){
                statement.setInt(i++,p.getGrado().getId());
            }
            else{
                statement.setInt(i++,-1);
            }
            if(p.getArma()!=null){
                statement.setInt(i++,p.getArma().getId());
            }
            else{
                statement.setInt(i++,-1);
            }
            
            statement.setString(i++,p.getPrimerNombre());
            statement.setString(i++,p.getPrimerApellido());
            statement.setString(i++,p.getSegundoNombre());
            statement.setString(i++,p.getSegundoApellido());
            statement.setString(i++,p.getObservaciones());
            statement.setBoolean(i++,p.isProfesor());
            statement.setString(i++, fecha);
            int row=statement.executeUpdate();
            if(row>0){
                if(p instanceof Cadete){
                    sql = "INSERT INTO sistemasem.cadetes (ci,numero,idCurso,fechaNac, sexo, idDepartamentoNac, localidadNac, cc,  ccNro, idEstadoCivil,domicilio,idDepartamentoDom,localidadDom,telefono,email,derecha,hijos,repitiente,lmga,paseDirecto,notaPaseDirecto) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    i=1;
                    statement= connection.prepareStatement(sql);
                    statement.setInt(i++,p.getCi());
                    statement.setInt(i++,p.getNroInterno());
                    Cadete c= (Cadete)p;
                    statement.setInt(i++,c.getCurso().getId());
                    statement.setDate(i++,c.getFechaNac());
                    statement.setString(i++,c.getSexo());
                    statement.setInt(i++,c.getDepartamentoNac().getCodigo());
                    statement.setString(i++, c.getLocalidadNac());
                    statement.setString(i++, c.getCc());
                    statement.setInt(i++, c.getCcNro());
                    statement.setInt(i++,c.getEstadoCivil().getCodigo());
                    statement.setString(i++, c.getDomicilio());
                    statement.setInt(i++,c.getDepartamento().getCodigo());
                    statement.setString(i++, c.getLocalidad());
                    statement.setString(i++, c.getTelefono());
                    statement.setString(i++, c.getEmail());
                    statement.setInt(i++, c.getDerecha());
                    statement.setInt(i++, c.getHijos());
                    statement.setBoolean(i++, c.isRepitiente());
                    statement.setBoolean(i++, c.isLmga());
                    statement.setBoolean(i++, c.isPaseDirecto());
                    statement.setDouble(i++, c.getNotaPaseDirecto());
                    row=statement.executeUpdate();
                    if(row>0){
                        return true;
                    }
                    else{
                        sql = "DELETE FROM sistemasem.PERSONAL WHERE CI="+ p.getCi();
                        Statement statement1= connection.createStatement();
                        statement1.execute(sql);
                        return false;
                    }
                }
                return true;
            }
            else{
                return false;
            }
            
        } catch (Exception ex) {
            System.out.print(ex);
            return false;
        }
    }
    
}
