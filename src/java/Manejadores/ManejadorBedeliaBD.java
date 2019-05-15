/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.ConexionDB;
import Classes.Bedelia.CursoBedelia;
import Classes.Bedelia.Falta;
import Classes.Bedelia.Grupo;
import Classes.Bedelia.Libreta;
import Classes.Bedelia.LibretaIndividual;
import Classes.Bedelia.Materia;
import Classes.Bedelia.Nota;
import Classes.Bedelia.Promedio;
import Classes.Bedelia.Sancion;
import Classes.Cadete;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 *
 * @author Gisel
 */
public class ManejadorBedeliaBD {
    private Connection connection;

    public ManejadorBedeliaBD() {
        connection = ConexionDB.GetConnection();
    }
    
    public HashMap<Integer, CursoBedelia> obtenerCursos(HashMap <Integer, Materia> materias) {
        HashMap<Integer, CursoBedelia> p= new HashMap<>();
        try {
            Statement s1,s2;
            Statement s= connection.createStatement();
            String sql,sql1,sql2;
            sql="SELECT * FROM sistemasem.cursosBedelia order by nombre asc;";
            ResultSet rs=s.executeQuery(sql);
            ResultSet rs1,rs2;
            CursoBedelia cb;
            ManejadorPersonal mp = ManejadorPersonal.getInstance();
            while (rs.next()){ //cursos
                cb = new CursoBedelia(rs.getInt("id"),rs.getString("codigo"),rs.getString("Nombre"),rs.getInt("anioCurricular"),rs.getBoolean("jefatura"),rs.getBoolean("activo"));
                sql1="SELECT * FROM sistemasem.`cursos-materias` where idCursoBedelia="+rs.getInt("id")+";";
                s1= connection.createStatement();
                rs1=s1.executeQuery(sql1);
                while (rs1.next()){ //materias
                    cb.getMaterias().put(rs1.getInt("idMateria"), materias.get(rs1.getInt("idMateria")));
                }
                sql1="SELECT * FROM sistemasem.`cursos-grupos` where idCursoBedelia="+rs.getInt("id")+" order by idCursoBedelia asc, anio desc, nombre asc;";
                s1= connection.createStatement();
                rs1=s1.executeQuery(sql1);
                while (rs1.next()){ //grupos
                    sql2="SELECT * FROM sistemasem.`grupos-alumnos` where idCursoBedelia="+rs.getInt("id")+" and anio="+rs1.getInt("anio")+" and nombre="+rs1.getString("nombre")+" order by ciAlumno asc;";
                    s2= connection.createStatement();
                    rs2=s2.executeQuery(sql2);
                    HashMap<Integer,Cadete> alumnos = new HashMap<>();
                    while (rs2.next()){ //alumnos
                        alumnos.put(rs2.getInt("ciAlumno"), mp.getCadete(rs2.getInt("ciAlumno")));
                    }
                    cb.getGrupos().add(new Grupo(cb, rs1.getInt("anio"), rs1.getString("nombre"),alumnos));
                }
                p.put(rs.getInt("id"),cb);
            }
            
        } catch (Exception ex) {
            System.out.print("obtenerCursos-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return p;
    }

    public HashMap<Integer, Materia> obtenerMaterias() {
        HashMap<Integer, Materia> p= new HashMap<>();
        try {
            Statement s= connection.createStatement();
            String sql;
            ManejadorBedelia mc = ManejadorBedelia.getInstance();
            sql="SELECT * FROM sistemasem.materias order by nombre asc;";
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                p.put(rs.getInt("id"),new Materia(rs.getInt("id"),rs.getString("nombre"),rs.getString("codigo"),rs.getBoolean("semestral"),rs.getInt("semestr"),rs.getBoolean("secundaria"),rs.getDouble("coeficiente"),rs.getBoolean("activo")));
            }
            
        } catch (Exception ex) {
            System.out.print("obtenerMaterias-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return p;
    }
    public boolean agregarCurso(CursoBedelia cb) {
        try {
            String sql= "insert into sistemasEM.cursosBedelia (codigo, nombre,anioCurricular,jefatura,activo) values(?,?,?,?,?)";
            PreparedStatement s= connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i=1;
            int clave;
            s.setString(i++, cb.getCodigo());
            s.setString(i++, cb.getNombre());
            s.setInt(i++, cb.getAnioCurricular());
            s.setBoolean(i++, cb.isJefatura());
            s.setBoolean(i++, cb.isActivo());
            int row=s.executeUpdate();
            if(row>0){
                ResultSet rs=s.getGeneratedKeys(); //obtengo las ultimas llaves generadas
                if(rs.next()){ 
                    clave=rs.getInt(1);
                    if(clave!=-1){
                        cb.setId(clave);
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            System.out.print("agregarCurso-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return false;
    }

    boolean modificarCurso(CursoBedelia cb) {
        String sql = "UPDATE sistemasem.cursosBedelia set nombre=?,anioCurricular=?, jefatura=?, activo=? where id=?";
        int i=1;
        try {
            PreparedStatement statement= connection.prepareStatement(sql); // sql a insertar en postulantes
            statement.setString(i++,cb.getNombre());
            statement.setInt(i++,cb.getAnioCurricular());
            statement.setBoolean(i++,cb.isJefatura());
            statement.setBoolean(i++,cb.isActivo());
            statement.setInt(i++,cb.getId());
            int row=statement.executeUpdate();
            return(row>0);
            
        } catch (SQLException ex) {
            System.out.print("modificarCurso-ManejadorBedeliaBD: "+ex);
            return false;
        }
    }

    boolean eliminarCurso(CursoBedelia c) {
        Statement s,s1;
        try {
            s = connection.createStatement();
            String sql="DELETE FROM sistemasem.cursosBedelia where id="+c.getId();
            if(s.executeUpdate(sql)>0){
                sql="DELETE FROM sistemasem.`cursos-grupos` where idCursoBedelia="+c.getId();
                s1=connection.createStatement();
                s1.executeUpdate(sql);
                return true;
            }
            return false;
        } catch (SQLException ex) {
            System.out.print("eliminarCurso-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return false;
    }

    HashMap<Integer, HashMap<Integer,Libreta>> obtenerLibretas(HashMap<Integer, Materia> materias, HashMap<Integer, CursoBedelia> cursos) {
        HashMap<Integer, HashMap<Integer,Libreta>> p= new HashMap<>();
        try {
            Statement s= connection.createStatement();
            String sql;
            sql="SELECT * FROM sistemasem.libretas order by ciProfesor asc,id asc;";
            ResultSet rs=s.executeQuery(sql);
            int ciProfesor;
            ManejadorBedelia mb = ManejadorBedelia.getInstance();
            ManejadorProfesores mp = ManejadorProfesores.getInstance();
            while (rs.next()){
                ciProfesor=rs.getInt("ciProfesor");
                if(p.get(ciProfesor)==null){
                    p.put(ciProfesor, new HashMap<>());
                }
                HashMap<Integer,LibretaIndividual> libretasIndividuales = obtenerLibretasIndividuales(rs.getInt("id"));
                p.get(rs.getInt("ciProfesor")).put(rs.getInt("id"), new Libreta(mb.getMaterias().get(rs.getInt("idMateria")),mb.getCurso(rs.getInt("idCurso")).getGrupo(rs.getInt("anio"), rs.getString("nombre")),mp.getProfesor(ciProfesor),rs.getString("salon"),libretasIndividuales));
            }
            
        } catch (Exception ex) {
            System.out.print("obtenerLibretas-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return p;
    }

    private HashMap<Integer, LibretaIndividual> obtenerLibretasIndividuales(int idLibreta) {
        HashMap<Integer, LibretaIndividual> p= new HashMap<>();
        try {
            Statement s= connection.createStatement();
            String sql;
            sql="SELECT * FROM sistemasem.libretasIndividuales where idLibreta="+idLibreta;
            ResultSet rs=s.executeQuery(sql);
            HashMap<Integer,Sancion> sanciones;
            HashMap<Integer,Promedio> promedios;
            HashMap<Integer, Nota> notas;
            HashMap<Integer, Falta> faltas;
            ManejadorPersonal mp = ManejadorPersonal.getInstance();
            while (rs.next()){
                sanciones= obtenerSancionesLibretaIndividual(rs.getInt("id"));
                promedios = obtenerPromediosLibretaIndividual(rs.getInt("id"));
                notas = obtenerNotasLibretaIndividual(rs.getInt("id"));
                faltas = obtenerFaltasLibretaIndividual(rs.getInt("id"));
                p.put(rs.getInt("id"),new LibretaIndividual(idLibreta,mp.getCadete(rs.getInt("ciCadete")), faltas, notas, promedios, sanciones,rs.getDouble("PromedioAnual"),rs.getDouble("NotaFinal")));
            }
            
        } catch (Exception ex) {
            System.out.print("obtenerMaterias-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return p;
    }

    private HashMap<Integer, Sancion> obtenerSancionesLibretaIndividual(int idLibretaIndividual) {
        HashMap<Integer, Sancion> p= new HashMap<>();
        try {
            Statement s= connection.createStatement();
            String sql;
            sql="SELECT * FROM sistemasem.sanciones where idLibretaIndividual="+idLibretaIndividual;
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                p.put(rs.getInt("id"),new Sancion(rs.getInt("id"),rs.getInt("tipo"), rs.getInt("minutosTarde"),rs.getString("causa"), rs.getString("fecha"), rs.getInt("estado")));
            }
        } catch (Exception ex) {
            System.out.print("obtenerSancionesLibretaIndividual-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return p;
    }

    private HashMap<Integer, Promedio> obtenerPromediosLibretaIndividual(int idLibretaIndividual) {
        HashMap<Integer, Promedio> p= new HashMap<>();
        try {
            Statement s= connection.createStatement();
            String sql;
            sql="SELECT * FROM sistemasem.promedios where idLibretaIndividual="+idLibretaIndividual;
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                p.put(rs.getInt("id"),new Promedio(rs.getInt("id"),rs.getInt("tipo"), rs.getDouble("nota"),rs.getInt("mes")));
            }
        } catch (Exception ex) {
            System.out.print("obtenerPromediosLibretaIndividual-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return p;
    }

    private HashMap<Integer, Nota> obtenerNotasLibretaIndividual(int idLibretaIndividual) {
        HashMap<Integer, Nota> p= new HashMap<>();
        try {
            Statement s= connection.createStatement();
            String sql;
            sql="SELECT * FROM sistemasem.notas where idLibretaIndividual="+idLibretaIndividual;
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                p.put(rs.getInt("id"),new Nota(rs.getInt("id"),rs.getString("fecha"),rs.getInt("tipo"), rs.getString("observacion"),rs.getDouble("nota")));
            }
        } catch (Exception ex) {
            System.out.print("obtenerNotasLibretaIndividual-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return p;
    }

    private HashMap<Integer, Falta> obtenerFaltasLibretaIndividual(int idLibretaIndividual) {
        HashMap<Integer, Falta> p= new HashMap<>();
        try {
            Statement s= connection.createStatement();
            String sql;
            sql="SELECT * FROM sistemasem.faltas where idLibretaIndividual="+idLibretaIndividual;
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                p.put(rs.getInt("id"),new Falta(rs.getInt("id"),rs.getString("fecha"),rs.getInt("canthoras"), rs.getString("motivo"),rs.getInt("estado")));
            }
        } catch (Exception ex) {
            System.out.print("obtenerFaltasLibretaIndividual-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return p;
    }

    boolean agregarMateria(Materia m) {
        try {
            String sql= "insert into sistemasEM.materias (nombre,codigo,semestral, semestre,secuandaria,coeficiente) values(?,?,?,?,?,?,?)";
            PreparedStatement s= connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i=1;
            int clave;
            s.setString(i++, m.getNombre());
            s.setString(i++, m.getCodigo());
            s.setBoolean(i++, m.isSemestral());
            s.setInt(i++, m.getSemestre());
            s.setBoolean(i++, m.isSecundaria());
            s.setDouble(i++, m.getCoeficiente());
            int row=s.executeUpdate();
            if(row>0){
                ResultSet rs=s.getGeneratedKeys(); //obtengo las ultimas llaves generadas
                if(rs.next()){ 
                    clave=rs.getInt(1);
                    if(clave!=-1){
                        m.setId(clave);
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            System.out.print("agregarMateria-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return false;
    }

    boolean asociarMateriaCurso(int idMateria, int idCurso) {
        try {
            String sql= "insert into sistemasEM.cursos-materias (idCurso,idMateria) values(?,?)";
            PreparedStatement s= connection.prepareStatement(sql);
            int i=1;
            s.setInt(i++, idCurso);
            s.setInt(i++, idMateria);
            int row=s.executeUpdate();
            if(row>0){
                return true;
            }
        } catch (Exception ex) {
            System.out.print("asociarMateriaCurso-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return false;
    }

    boolean desasociarMateriaCurso(int idMateria, int idCurso) {
        Statement s;
        try {
            s = connection.createStatement();
            String sql="DELETE FROM sistemasem.cursos-materias where idCurso="+idCurso+" and idMateria="+idMateria;
            s.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            System.out.print("desasociarMateriaCurso-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return false;
    }

    //precondicion: idMateria no tiene libretas asociadas
    boolean eliminarMateriaDeCursos(int idMateria) {
        Statement s;
        try {
            s = connection.createStatement();
            String sql="DELETE FROM sistemasem.cursos-materias where idMateria="+idMateria;
            s.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            System.out.print("eliminarMateriaDeCursos-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return false;
    }
    //precondicion: idMateria no tiene libretas asociadas, y no pertenece a ningun curso
    boolean eliminarMateria(int idMateria) {
        Statement s;
        try {
            s = connection.createStatement();
            String sql="DELETE FROM sistemasem.materias where idMateria="+idMateria;
            s.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            System.out.print("eliminarMateria-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return false;
    }

    boolean modificarMateria(Materia m) {
       String sql = "UPDATE sistemasem.materias set nombre=?,semestral=?, semestre=?, secundaria=?, coeficiente=?, activo=? where id=?";
        int i=1;
        try {
            PreparedStatement statement= connection.prepareStatement(sql); // sql a insertar en postulantes
            statement.setString(i++,m.getNombre());
            statement.setBoolean(i++,m.isSemestral());
            statement.setInt(i++,m.getSemestre());
            statement.setBoolean(i++,m.isSecundaria());
            statement.setDouble(i++,m.getCoeficiente());
            statement.setBoolean(i++,m.isActivo());
            statement.setInt(i++,m.getId());
            int row=statement.executeUpdate();
            return(row>0);
            
        } catch (SQLException ex) {
            System.out.print("modificarMateria-ManejadorBedeliaBD: "+ex);
            return false;
        }
    }
    
}
