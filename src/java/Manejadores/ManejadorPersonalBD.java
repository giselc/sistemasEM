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
import Classes.Documento;
import Classes.Familiar;
import Classes.HistorialBajaCadete;
import Classes.Personal;
import Classes.RecordCadete;
import Classes.RecordCadetesFiltro;
import Classes.RecordPersonal;
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
import javax.servlet.http.Part;

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
            System.out.print("getPersonalListar:"+ex.getMessage());
        }
        return al;
    }
    private LinkedList<HistorialBajaCadete> obtenerHistorialBaja(int ci){
        LinkedList<HistorialBajaCadete> hbj= new LinkedList<>();
        try {
            Statement s1= connection.createStatement();
            String sql="SELECT * FROM sistemasem.cadetesBajas where ci="+ci+" order by fechabaja desc";
            ResultSet rs1=s1.executeQuery(sql);
            while(rs1.next()){
                hbj.add(new HistorialBajaCadete(rs1.getString("fechaBaja"), rs1.getString("causaBaja")));
            }
        } catch (SQLException ex) {
            System.out.print("obtenerHistorialBaja:"+ex.getMessage());
        }
        return hbj;
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
                        madre = new Familiar(rs.getString("MNombreComp"), rs.getString("MFechaNac"), mc.getDepartamento(rs.getInt("MDepartamentoNac")), rs.getString("MLocalidadNac"), mc.getEstadoCivil(rs.getInt("MEstadoCivil")), rs.getString("MDomicilio"), mc.getDepartamento(rs.getInt("MDepartamento")), rs.getString("MLocalidad"), rs.getString("MTelefono"), rs.getString("MProfesion"), rs.getString("MLugarTrabajo"));
                        padre = new Familiar(rs.getString("PNombreComp"), rs.getString("PFechaNac"), mc.getDepartamento(rs.getInt("PDepartamentoNac")), rs.getString("PLocalidadNac"), mc.getEstadoCivil(rs.getInt("PEstadoCivil")), rs.getString("PDomicilio"), mc.getDepartamento(rs.getInt("PDepartamento")), rs.getString("PLocalidad"), rs.getString("PTelefono"), rs.getString("PProfesion"), rs.getString("PLugarTrabajo"));
                        p.get(1).add(new Cadete(rs.getString("fotoPasaporte"),mc.getCurso(rs.getInt("idCurso")),mc.getCarrera(rs.getInt("idCarrera")), rs.getString("fechaNac"),rs.getString("sexo"),mc.getDepartamento(rs.getInt("idDepartamentoNac")),rs.getString("localidadNac"),rs.getString("cc"),rs.getInt("ccNro"),mc.getEstadoCivil(rs.getInt("idEstadoCivil")),rs.getString("domicilio"),mc.getDepartamento(rs.getInt("idDepartamentoDom")),rs.getString("localidadDom"),rs.getString("telefono"),rs.getString("email"),rs.getInt("derecha"),rs.getInt("hijos"),rs.getBoolean("repitiente"),rs.getBoolean("lmga"),rs.getBoolean("paseDirecto"),rs.getDouble("notaPaseDirecto"),madre,padre,rs.getInt("numero"),rs.getInt("ci"),mc.getGrado(rs.getInt("idGrado")),mc.getArma(rs.getInt("idArma")),rs.getString("primerNombre"),rs.getString("segundoNombre"),rs.getString("primerApellido"),rs.getString("segundoApellido"),md.getDocumentos(rs.getInt("ci")),rs.getString("observaciones"),rs.getBoolean("profesor"),rs.getString("fechaAltaSistema"),rs.getString("talleOperacional"),rs.getInt("talleBotas"),rs.getInt("talleQuepi"),this.obtenerHistorialBaja(rs.getInt("ci"))));
                        break;
                    case 2:
                        p.get(2).add(new Personal(rs.getInt("numero"),rs.getInt("ci"),mc.getGrado(rs.getInt("idGrado")),mc.getArma(rs.getInt("idArma")),rs.getString("primerNombre"),rs.getString("segundoNombre"),rs.getString("primerApellido"),rs.getString("segundoApellido"),md.getDocumentos(rs.getInt("ci")),rs.getString("observaciones"),rs.getBoolean("profesor"),rs.getString("fecha")));
                        break;
                    case 3:
                        p.get(3).add(new Personal(rs.getInt("numero"),rs.getInt("ci"),mc.getGrado(rs.getInt("idGrado")),mc.getArma(rs.getInt("idArma")),rs.getString("primerNombre"),rs.getString("segundoNombre"),rs.getString("primerApellido"),rs.getString("segundoApellido"),md.getDocumentos(rs.getInt("ci")),rs.getString("observaciones"),rs.getBoolean("profesor"),rs.getString("fecha")));
                        break;
                    case 4:
                        p.get(4).add(new Personal(rs.getInt("numero"),rs.getInt("ci"),mc.getGrado(rs.getInt("idGrado")),mc.getArma(rs.getInt("idArma")),rs.getString("primerNombre"),rs.getString("segundoNombre"),rs.getString("primerApellido"),rs.getString("segundoApellido"),md.getDocumentos(rs.getInt("ci")),rs.getString("observaciones"),rs.getBoolean("profesor"),rs.getString("fecha")));
                        break;
                }
            }
            
        } catch (Exception ex) {
            System.out.print("obtenerPersonalEM:"+ex.getMessage());
        }
        return p;
    }
    public Personal agregarPersonal(RecordPersonal rp,Part foto){
        int clave = -1;
        java.util.Calendar fecha1 = java.util.Calendar.getInstance();
        int mes = fecha1.get(java.util.Calendar.MONTH)+1;
        String fecha=  fecha1.get(java.util.Calendar.YEAR)+"-"+mes+"-"+fecha1.get(java.util.Calendar.DATE); //usada para log
        String sql = "INSERT INTO sistemasem.personal (ci,idGrado,idArma, PrimerNombre, PrimerApellido, SegundoNombre, SegundoApellido,  OBSERVACIONES, profesor,fechaAltaSistema) values (?,?,?,?,?,?,?,?,?,?)";
        int i=1;
        try {
            PreparedStatement statement= connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // sql a insertar en postulantes
            statement.setInt(i++,rp.ci);
            statement.setInt(i++,rp.idGrado);
            statement.setInt(i++,rp.idArma);
            statement.setString(i++,rp.primerNombre);
            statement.setString(i++,rp.primerApellido);
            statement.setString(i++,rp.segundoNombre);
            statement.setString(i++,rp.segundoApellido);
            statement.setString(i++,rp.observaciones);
            statement.setBoolean(i++,rp.profesor);
            statement.setString(i++, fecha);
            int row=statement.executeUpdate();
            if(row>0){
                ResultSet rs=statement.getGeneratedKeys(); //obtengo las ultimas llaves generadas
                if(rs.next()){ 
                    clave=rs.getInt(1);
                    if(clave!=-1){
                        ManejadorCodigos mc= ManejadorCodigos.getInstance();
                        if(rp.rc!=null){//es cadete
                            try{
                                RecordCadete rc= rp.rc;
                                sql = "INSERT INTO sistemasem.cadetes (fotoPasaporte,ci,numero,idCurso,fechaNac, sexo, idDepartamentoNac, localidadNac, cc,  ccNro, idEstadoCivil,domicilio,idDepartamentoDom,localidadDom,telefono,email,derecha,hijos,repitiente,lmga,paseDirecto,notaPaseDirecto,talleOperacional,talleQuepi,talleBotas) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                                i=1;
                                statement= connection.prepareStatement(sql);
                                String ext = ManejadorDocumentosBD.getFileName(foto).substring(ManejadorDocumentosBD.getFileName(foto).lastIndexOf("."));
                                statement.setString(i++,rp.ci+ext );
                                statement.setInt(i++,rp.ci);
                                statement.setInt(i++,clave);
                                statement.setInt(i++,rc.idcurso);
                                statement.setString(i++,rc.fechaNac);
                                statement.setString(i++,rc.sexo);
                                statement.setInt(i++,rc.iddepartamentoNac);
                                statement.setString(i++, rc.localidadNac);
                                statement.setString(i++, rc.cc);
                                statement.setInt(i++, rc.ccNro);
                                statement.setInt(i++,rc.idestadoCivil);
                                statement.setString(i++, rc.domicilio);
                                statement.setInt(i++,rc.iddepartamento);
                                statement.setString(i++, rc.localidad);
                                statement.setString(i++, rc.telefono);
                                statement.setString(i++, rc.email);
                                statement.setInt(i++, rc.derecha);
                                statement.setInt(i++, rc.hijos);
                                statement.setBoolean(i++, rc.repitiente);
                                statement.setBoolean(i++, rc.lmga);
                                statement.setBoolean(i++, rc.paseDirecto);
                                statement.setDouble(i++, rc.notaPaseDirecto);
                                statement.setString(i++, rc.talleOperacional);
                                statement.setInt(i++, rc.talleQuepi);
                                statement.setInt(i++, rc.talleBotas);
                                row=statement.executeUpdate();
                                if(row>0){
                                    ManejadorDocumentosBD.subirArchivo(foto, -1, rp.ci, true);
                                    return new Cadete(rp.ci+ext,mc.getCurso(rc.idcurso),mc.getCarrera(rc.idcarrera),rc.fechaNac,rc.sexo, mc.getDepartamento(rc.iddepartamentoNac),rc.localidadNac,rc.cc,rc.ccNro,mc.getEstadoCivil(rc.idestadoCivil),rc.domicilio,mc.getDepartamento(rc.iddepartamento), rc.localidad, rc.telefono,rc.email,rc.derecha, rc.hijos,rc.repitiente,rc.lmga,rc.paseDirecto,rc.notaPaseDirecto, null, null, clave, rp.ci, mc.getGrado(rp.idGrado),mc.getArma(rp.idArma),rp.primerNombre, rp.segundoNombre, rp.primerApellido,rp.segundoApellido, new HashMap<>(), rp.observaciones,rp.profesor,rp.fechaAltaSistema,rc.talleOperacional,rc.talleBotas,rc.talleQuepi,this.obtenerHistorialBaja(rp.ci));
                                }
                                else{
                                    sql = "DELETE FROM sistemasem.PERSONAL WHERE CI="+ rp.ci;
                                    Statement statement1= connection.createStatement();
                                    statement1.execute(sql);
                                    return null;
                                }
                            }
                            catch(SQLException ex){
                                System.out.println("agregarPersonal-1:"+ex.getMessage());
                                sql = "DELETE FROM sistemasem.PERSONAL WHERE CI="+ rp.ci;
                                Statement statement1= connection.createStatement();
                                statement1.execute(sql);
                                return null;
                            }
                        }
                        else{ //no es cadete
                            return new Personal(clave,rp.ci,mc.getGrado(rp.idGrado),mc.getArma(rp.idArma),rp.primerNombre,rp.segundoNombre,rp.primerApellido,rp.segundoApellido, null, rp.observaciones, rp.profesor,fecha);
                        }
                    }
                }
            }
            else{
                return null;
            }
            
        } catch (SQLException ex) {
            System.out.print(ex);
            return null;
        }
        return null;
    }
    boolean modificarPersonal(RecordPersonal rp, Part foto) {
        String sql = "UPDATE sistemasem.personal set idGrado=?,idArma=?, PrimerNombre=?, PrimerApellido=?, SegundoNombre=?, SegundoApellido=?,  OBSERVACIONES=?, profesor=? where ci=?";
        int i=1;
        try {
            PreparedStatement statement= connection.prepareStatement(sql); // sql a insertar en postulantes
            statement.setInt(i++,rp.idGrado);
            statement.setInt(i++,rp.idArma);
            statement.setString(i++,rp.primerNombre);
            statement.setString(i++,rp.primerApellido);
            statement.setString(i++,rp.segundoNombre);
            statement.setString(i++,rp.segundoApellido);
            statement.setString(i++,rp.observaciones);
            statement.setBoolean(i++,rp.profesor);
            statement.setInt(i++,rp.ci);
            int row=statement.executeUpdate();
            if(row>0){
                if(rp.rc!=null){//es cadete
                    try{
                        RecordCadete rc= rp.rc;
                        String fotoPas="";
                        boolean fotoPasSubida=false;
                        if(foto!=null){
                            if(ManejadorDocumentosBD.subirArchivo(foto, -1, rp.ci, true)){
                                fotoPas="fotopasaporte=?,";
                                fotoPasSubida=true;
                                
                            }
                        }
                        sql = "UPDATE sistemasem.cadetes set "+fotoPas+"idCurso=?,fechaNac=?, sexo=?, idDepartamentoNac=?, localidadNac=?, cc=?,  ccNro=?, idEstadoCivil=?,domicilio=?,idDepartamentoDom=?,localidadDom=?,telefono=?,email=?,derecha=?,hijos=?,repitiente=?,lmga=?,paseDirecto=?,notaPaseDirecto=?,talleOperacional=?,talleQuepi=?,talleBotas=? where ci=?";
                        i=1;
                        statement= connection.prepareStatement(sql);
                        if(fotoPasSubida){
                            statement.setString(i++, rp.ci+ManejadorDocumentosBD.getFileName(foto).substring(ManejadorDocumentosBD.getFileName(foto).lastIndexOf(".")));
                        }
                        statement.setInt(i++,rc.idcurso);
                        statement.setString(i++,rc.fechaNac);
                        statement.setString(i++,rc.sexo);
                        statement.setInt(i++,rc.iddepartamentoNac);
                        statement.setString(i++, rc.localidadNac);
                        statement.setString(i++, rc.cc);
                        statement.setInt(i++, rc.ccNro);
                        statement.setInt(i++,rc.idestadoCivil);
                        statement.setString(i++, rc.domicilio);
                        statement.setInt(i++,rc.iddepartamento);
                        statement.setString(i++, rc.localidad);
                        statement.setString(i++, rc.telefono);
                        statement.setString(i++, rc.email);
                        statement.setInt(i++, rc.derecha);
                        statement.setInt(i++, rc.hijos);
                        statement.setBoolean(i++, rc.repitiente);
                        statement.setBoolean(i++, rc.lmga);
                        statement.setBoolean(i++, rc.paseDirecto);
                        statement.setDouble(i++, rc.notaPaseDirecto);
                        statement.setString(i++, rc.talleOperacional);
                        statement.setInt(i++, rc.talleQuepi);
                        statement.setInt(i++, rc.talleBotas);
                        statement.setInt(i++,rp.ci);
                        row=statement.executeUpdate();
                        return row>0;
                    }
                    catch(SQLException ex){
                       System.out.print("modificarPersonal:"+ex.getMessage());
                       return false;
                    }
                }
                else{ //no es cadete
                    return true;
                }
                    
                
            }
            else{
                return false;
            }
            
        } catch (SQLException ex) {
            System.out.print(ex);
            return false;
        }
    }
    boolean bajaCadete(int ci, String causa) {
        java.util.Calendar fecha1 = java.util.Calendar.getInstance();
        int mes = fecha1.get(java.util.Calendar.MONTH)+1;
        String fecha=  fecha1.get(java.util.Calendar.YEAR)+"-"+mes+"-"+fecha1.get(java.util.Calendar.DATE); //usada para log
        Statement s;
        try {
            s = connection.createStatement();
            String sql="insert into sistemasem.cadetesBajas (select cadetes.*,`idGrado`,`idArma`,`primerNombre`,`segundoNombre`,`primerApellido`,`segundoApellido`,`observaciones`,`profesor`,`fechaAltaSistema`,'"+causa+"'as causaBaja,'"+fecha+"' as fechaBaja from sistemasem.personal left join sistemasem.cadetes on personal.ci=cadetes.ci and personal.numero=cadetes.numero where cadetes.ci="+ci+")";
            s.addBatch(sql);
            sql="DELETE FROM sistemasem.personal where ci="+ci;
            s.addBatch(sql);
            sql="DELETE FROM sistemasem.cadetes where ci="+ci;
            s.addBatch(sql);
            s.executeBatch();
            return true;
        } catch (SQLException ex) {
            System.out.print("bajaCadete:"+ex.getMessage());
        }
        return false;
    }
    public Cadete crearCadeteHistorial(int ci){
        try {
            Statement s= connection.createStatement();
            String sql="Insert into sistemasem.personal SELECT numero, ci, idGrado, idArma, primerNombre, segundoNombre,primerApellido, segundoApellido, observaciones, profesor, fechaAltaSistema FROM sistemasem.cadetesBajas where ci="+ci+" order by fechaBaja desc LIMIT 1";
            s.addBatch(sql);
            sql="Insert into sistemasem.cadetes SELECT ci,fotopasaporte,numero,idCurso,idCarrera,fechaNac,sexo,idDepartamentoNac,localidadNac,cc,ccNro,idEstadoCivil,domicilio,idDepartamentoDom,localidadDom,telefono,email,derecha,hijos,repitiente,lmga,pasedirecto,notapaseDirecto,"
                    + "talleoperacional,tallequepi,tallebotas,PNombreComp,PFechaNac,PDepartamentoNac,PLocalidadNac,PEstadoCivil,PDomicilio,PDepartamento,PLocalidad,PTelefono,PProfesion,PLugarTrabajo,"
                    + "MNombreComp,MFechaNac,MDepartamentoNac,MLocalidadNac,MEstadoCivil,MDomicilio,MDepartamento,MLocalidad,MTelefono,MProfesion,MLugarTrabajo FROM sistemasem.cadetesBajas where ci="+ci+" order by fechaBaja desc LIMIT 1";
            s.addBatch(sql);
            s.executeBatch();
            sql ="Select * from sistemasem.personal left join sistemasem.cadetes on personal.ci=cadetes.ci where personal.ci="+ci;
            Statement s1= connection.createStatement();
            ResultSet rs= s1.executeQuery(sql);
            if(rs.next()){
                ManejadorCodigos mc = ManejadorCodigos.getInstance();
                Familiar madre = new Familiar(rs.getString("MNombreComp"), rs.getString("MFechaNac"), mc.getDepartamento(rs.getInt("MDepartamentoNac")), rs.getString("MLocalidadNac"), mc.getEstadoCivil(rs.getInt("MEstadoCivil")),rs.getString("MDomicilio"),mc.getDepartamento(rs.getInt("MDepartamento")),rs.getString("MLocalidad"),rs.getString("Mtelefono"), rs.getString("MProfesion"), rs.getString("MLugarTrabajo"));
                Familiar padre = new Familiar(rs.getString("PNombreComp"), rs.getString("PFechaNac"), mc.getDepartamento(rs.getInt("PDepartamentoNac")), rs.getString("PLocalidadNac"), mc.getEstadoCivil(rs.getInt("PEstadoCivil")),rs.getString("PDomicilio"),mc.getDepartamento(rs.getInt("PDepartamento")),rs.getString("PLocalidad"),rs.getString("Ptelefono"), rs.getString("PProfesion"), rs.getString("PLugarTrabajo"));
                return new Cadete(rs.getString("fotopasaporte"),mc.getCurso(rs.getInt("idCurso")),mc.getCarrera(rs.getInt("idCarrera")),rs.getString("fechaNac"),rs.getString("sexo"), mc.getDepartamento(rs.getInt("iddepartamentoNac")),rs.getString("localidadNac"),rs.getString("cc"),rs.getInt("ccNro"),mc.getEstadoCivil(rs.getInt("idestadoCivil")),rs.getString("domicilio"),mc.getDepartamento(rs.getInt("iddepartamentodom")), rs.getString("localidaddom"), rs.getString("telefono"),rs.getString("email"),rs.getInt("derecha"), rs.getInt("hijos"),rs.getBoolean("repitiente"),rs.getBoolean("lmga"),rs.getBoolean("paseDirecto"),rs.getDouble("notaPaseDirecto"), madre,padre, rs.getInt("numero"), rs.getInt("ci"), mc.getGrado(rs.getInt("idGrado")),mc.getArma(rs.getInt("idArma")),rs.getString("primerNombre"), rs.getString("segundoNombre"), rs.getString("primerApellido"),rs.getString("segundoApellido"), new HashMap<Integer, Documento>(), rs.getString("observaciones"),rs.getBoolean("profesor"),rs.getString("fechaAltaSistema"),rs.getString("talleOperacional"),rs.getInt("talleBotas"),rs.getInt("talleQuepi"),this.obtenerHistorialBaja(ci));
            }
        } catch (Exception ex) {
            try{
                String sql="Delete from sistemasem.personal where ci="+ci;
                Statement s= connection.createStatement();
                s.addBatch(sql);
                sql= "Delete from sistemasem.cadetes where ci="+ci;
                s.addBatch(sql);
                s.executeBatch();
                return null;
            }
            catch(Exception ex1){
                
            }
            System.out.print("crearCadeteDesdeHistorial: "+ex.getMessage());
        }
        return null;
    }
    public Boolean existeCadeteDesdeHistorial(int ci){
        try {
            Statement s= connection.createStatement();
            String sql="SELECT * FROM sistemasem.cadetesBajas where ci="+ci+" order by fechaBaja desc";
            ResultSet rs=s.executeQuery(sql);
            return(rs.next());
        } catch (SQLException ex) {
            System.out.print("crearCadeteDesdeHistorial: "+ex.getMessage());
        }
        return false;
    }
    boolean actualizarGrados(String[] parameterValues) {
        //Los de tercer año que no estaban en la lista ya fueron eliminados
        String sql ;
        if(parameterValues!=null){
            String ciActualizar="";
            boolean primero=true;
            for(String s:parameterValues){
                
                if(primero){
                    ciActualizar="ci="+s;
                    primero=false;
                }
                else{
                    ciActualizar=" or ci="+s;
                }
            }
            sql = "UPDATE sistemasem.personal set idGrado=idGrado-1 where NOT("+ciActualizar+")";
        }
        else{
            sql = "UPDATE sistemasem.personal set idGrado=idGrado-1";
        
        }
        try {
            Statement statement= connection.createStatement();
            int row=statement.executeUpdate(sql);
            return(row>0);

        } catch (SQLException ex) {
            System.out.print(ex);
            return false;
        }
    }
}
