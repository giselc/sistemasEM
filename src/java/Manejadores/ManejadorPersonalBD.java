/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.Arma;
import Classes.Cadete;
import Classes.ConexionDB;
import Classes.Curso;
import Classes.Departamento;
import Classes.Familiar;
import Classes.Personal;
import Classes.RecordCadetesFiltro;
import Classes.TipoPersonal;
import Classes.Usuario;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gisel
 */
public class ManejadorPersonalBD {
    private Connection connection;

    public ManejadorPersonalBD() {
        connection = ConexionDB.GetConnection();
    }
    private int ConvertirDepartamento(int deptoViejo){
        int dptoNuevo =0;
        switch(deptoViejo){
            case 1: dptoNuevo = 2; break; 
            case 2: dptoNuevo = 9; break;
            case 3: dptoNuevo = 14; break; 
            case 4: dptoNuevo = 19; break;
            case 5: dptoNuevo = 3; break; 
            case 6: dptoNuevo = 13; break;
            case 7: dptoNuevo = 1; break; 
            case 8: dptoNuevo = 15; break; 
            case 9: dptoNuevo = 11; break; 
            case 10: dptoNuevo = 12; break; 
            case 11: dptoNuevo = 17; break; 
            case 12: dptoNuevo = 4; break;     
            case 13: dptoNuevo = 16; break; 
            case 14: dptoNuevo = 6; break; 
            case 15: dptoNuevo = 7; break;   
            case 16: dptoNuevo = 8; break;     
            case 17: dptoNuevo = 5; break; 
            case 18: dptoNuevo = 18; break; 
            case 19: dptoNuevo = 10; break; 
        }
        return dptoNuevo;
    }
    private int convertirEstadoCivil(int estadoCivilViejo){
        int estadoCivilNuevo=0;
        switch(estadoCivilViejo){
            case 1: estadoCivilNuevo=1; break;
            case 2: estadoCivilNuevo=2; break;
            case 3: estadoCivilNuevo=5; break;
            case 5: estadoCivilNuevo=4; break;
            case 6: estadoCivilNuevo=3; break;
        }
        return estadoCivilNuevo;
    }
    private String convertirFecha(String fechaVieja){
        String [] campos= fechaVieja.split("/");
        return campos[2]+"-"+campos[1]+"-"+campos[0];
    }
    public boolean AgregarCadetesTXT(){
      File archivo = null;
      FileReader fr = null;
      BufferedReader br = null;

      try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         archivo = new File ("C:\\Consulta1.txt");
         
         br = new BufferedReader(new InputStreamReader(new FileInputStream(archivo),"UTF-8"));

         // Lectura del fichero
         String linea;
         String sql="INSERT INTO sistemasem.personal (numero,ci,idGrado,idArma,primerNombre,segundoNombre,primerApellido,segundoApellido,observaciones,profesor,fechaAltaSistema) values (?,?,?,?,?,?,?,?,?,?,?)";
         PreparedStatement s = connection.prepareStatement(sql);
         
         String sql1="INSERT INTO sistemasem.cadetes (ci,numero,idCurso,fechaNac,sexo,idDepartamentoNac,localidadNac,cc,ccNro,idEstadoCivil,domicilio,idDepartamentoDom,localidadDom,telefono,email,derecha,idCarrera) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
         PreparedStatement c = connection.prepareStatement(sql1);
         
         int i;
         String [] campos;
         ManejadorCodigos mc= ManejadorCodigos.getInstance();
         int curso = 0;
         while((linea=br.readLine())!=null){
            i=1;
            campos= linea.split(";");
            s.setInt(i++, Integer.valueOf(campos[0]));
            s.setInt(i++, Integer.valueOf(campos[14]));
            
            switch (Integer.valueOf(campos[10])){
                case 23: 
                    s.setInt(i++, 21); //grado
                    s.setInt(i++,6); //arma
                    curso=6;
                    break;
                case 24:
                    s.setInt(i++, 20);//grado
                    s.setInt(i++,6);//arma
                    curso=6;
                    break;
                default:
                    s.setInt(i++,Integer.valueOf(campos[10]));// grado
                    switch (Integer.valueOf(campos[2])){ //arma
                        case 6:
                            s.setInt(i++,0);
                            curso=7;
                            break;
                        case 7: //apoyo
                            s.setInt(i++,6);
                            if(Integer.valueOf(campos[10])==22){//aspirante
                                curso=7;
                            }
                            break;
                        default:
                            s.setInt(i++,Integer.valueOf(campos[2]));
                            curso=Integer.valueOf(campos[2]);
                            break;
                    }
                    break;
                        
            }
             s.setString(i++,campos[20]);//primer nombre
             s.setString(i++,campos[22]);//segundo nombre
             s.setString(i++,campos[23]);//primer apellido
             s.setString(i++,campos[21]);//segundo apellido
             s.setString(i++,"");//observaciones
             s.setBoolean(i++, false);//profesor
             
             java.util.Calendar fecha1 = java.util.Calendar.getInstance();
             int mes = fecha1.get(java.util.Calendar.MONTH)+1;
             String fecha=  fecha1.get(java.util.Calendar.YEAR)+"-"+mes+"-"+fecha1.get(java.util.Calendar.DATE); //usada para log
            
             s.setString(i++,fecha);//fecha Alta
             s.addBatch();
             i=1;
             
             //(ci,numero,idCurso,fechaNac,sexo,idDepartamentoNac,localidadNac,cc,ccNro,idEstadoCivil,domicilio,idDepartamentoDom,localidadDom,telefono,email,derecha,idCarrera)
            
             c.setInt(i++, Integer.valueOf(campos[14])); //ci
             c.setInt(i++, Integer.valueOf(campos[0])); //nro
             c.setInt(i++,curso); //curso
             c.setString(i++, this.convertirFecha(campos[7])); //fechaNAC
             c.setString(i++, campos[9]); //SEXO
             c.setInt(i++,this.ConvertirDepartamento(Integer.valueOf(campos[5]))); //idDeptoNac
             c.setString(i++, ""); //localidadNac
             c.setString(i++, campos[13]); //cc
             c.setInt(i++, Integer.valueOf(campos[11])); //ccNro
             c.setInt(i++, this.convertirEstadoCivil(Integer.valueOf(campos[11]))); //idEstadoCivil
             c.setString(i++, campos[19]); //domicilio
             c.setInt(i++,this.ConvertirDepartamento(Integer.valueOf(campos[16]))); //dptoDom
             c.setString(i++, campos[18]); //localidadDom
             c.setString(i++, campos[17]); //telefono
             c.setString(i++, ""); //email
             c.setInt(i++, Integer.valueOf(campos[4])); //derecha
             if(Integer.valueOf(campos[8])==7){
                c.setInt(i++,2);//apoyo
             }else{
                c.setInt(i++,1); //comando
             }
             
             
             c.addBatch();
         }
         s.executeBatch();
         c.executeBatch();
         s.close();
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
                sql="SELECT * FROM sistemasem.personal left-join sistemasem.grado on personal.idgrado = grado.codigo where tipoPersonal = "+tp.getId()+ " order by grado.codigo asc";
            }
            else{
                return null;
            }
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return al;
    }
    
    public HashMap<Integer,LinkedList<Personal>> obtenerPersonalEM(){
        HashMap<Integer,LinkedList<Personal>> p= new HashMap<>();
        for (int i=1;i<=4;i++){
            p.put(i, new LinkedList<>());
        }
        try {
            Statement s= connection.createStatement();
            String sql = "";
            ManejadorDocumentosBD md = new ManejadorDocumentosBD();
            ManejadorCodigos mc = ManejadorCodigos.getInstance();
            sql="SELECT * FROM sistemasem.personal left join sistemasem.grado on personal.idgrado = grado.codigo left join sistemasem.cadetes on personal.ci = cadetes.ci order by idTipoPersonal asc, grado.codigo asc, idCurso asc, idArma asc";
            ResultSet rs=s.executeQuery(sql);
            int i=0;
            Familiar madre=null;
            Familiar padre = null;
            while (rs.next()){
                switch (rs.getInt("idTipoPersonal")) {
                    case 1:
                        //System.out.print(i++);
                        madre = new Familiar(rs.getString("MNombreComp"), rs.getDate("MFechaNac"), mc.getDepartamento(rs.getInt("MDepartamentoNac")), rs.getString("MLocalidadNac"), mc.getEstadoCivil(rs.getInt("MEstadoCivil")), rs.getString("MDomicilio"), mc.getDepartamento(rs.getInt("MDepartamento")), rs.getString("MLocalidad"), rs.getString("MTelefono"), rs.getString("MProfesion"), rs.getString("MLugarTrabajo"));
                        padre = new Familiar(rs.getString("PNombreComp"), rs.getDate("PFechaNac"), mc.getDepartamento(rs.getInt("PDepartamentoNac")), rs.getString("PLocalidadNac"), mc.getEstadoCivil(rs.getInt("PEstadoCivil")), rs.getString("PDomicilio"), mc.getDepartamento(rs.getInt("PDepartamento")), rs.getString("PLocalidad"), rs.getString("PTelefono"), rs.getString("PProfesion"), rs.getString("PLugarTrabajo"));
                        p.get(1).add(new Cadete(rs.getString("fotoPasaporte"),mc.getCurso(rs.getInt("idCurso")),mc.getCarrera(rs.getInt("idCarrera")), rs.getDate("fechaNac"),rs.getString("sexo"),mc.getDepartamento(rs.getInt("idDepartamentoNac")),rs.getString("localidadNac"),rs.getString("cc"),rs.getInt("ccNro"),mc.getEstadoCivil(rs.getInt("idEstadoCivil")),rs.getString("domicilio"),mc.getDepartamento(rs.getInt("idDepartamentoDom")),rs.getString("localidadDom"),rs.getString("telefono"),rs.getString("email"),rs.getInt("derecha"),rs.getInt("hijos"),rs.getBoolean("repitiente"),rs.getBoolean("lmga"),rs.getBoolean("paseDirecto"),rs.getDouble("notaPaseDirecto"),madre,padre,rs.getInt("numero"),rs.getInt("ci"),mc.getGrado(rs.getInt("idGrado")),mc.getArma(rs.getInt("idArma")),rs.getString("primerNombre"),rs.getString("segundoNombre"),rs.getString("primerApellido"),rs.getString("segundoApellido"),md.getDocumentos(rs.getInt("ci")),rs.getString("observaciones"),rs.getBoolean("profesor")));
                        break;
                    case 2:
                        p.get(2).add(new Personal(rs.getInt("numero"),rs.getInt("ci"),mc.getGrado(rs.getInt("idGrado")),mc.getArma(rs.getInt("idArma")),rs.getString("primerNombre"),rs.getString("segundoNombre"),rs.getString("primerApellido"),rs.getString("segundoApellido"),md.getDocumentos(rs.getInt("ci")),rs.getString("observaciones"),rs.getBoolean("profesor")));
                        break;
                    case 3:
                        p.get(3).add(new Personal(rs.getInt("numero"),rs.getInt("ci"),mc.getGrado(rs.getInt("idGrado")),mc.getArma(rs.getInt("idArma")),rs.getString("primerNombre"),rs.getString("segundoNombre"),rs.getString("primerApellido"),rs.getString("segundoApellido"),md.getDocumentos(rs.getInt("ci")),rs.getString("observaciones"),rs.getBoolean("profesor")));
                        break;
                    case 4:
                        p.get(4).add(new Personal(rs.getInt("numero"),rs.getInt("ci"),mc.getGrado(rs.getInt("idGrado")),mc.getArma(rs.getInt("idArma")),rs.getString("primerNombre"),rs.getString("segundoNombre"),rs.getString("primerApellido"),rs.getString("segundoApellido"),md.getDocumentos(rs.getInt("ci")),rs.getString("observaciones"),rs.getBoolean("profesor")));
                        break;
                }
            }
            
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return p;
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