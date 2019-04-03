/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.ConexionDB;
import Classes.Bedelia.CursoBedelia;
import Classes.Bedelia.Grupo;
import Classes.Bedelia.Libreta;
import Classes.Bedelia.Materia;
import Classes.Cadete;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;

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
                sql1="SELECT * FROM sistemasem.cursos-materias where idCursoBedelia="+rs.getInt("id")+";";
                s1= connection.createStatement();
                rs1=s1.executeQuery(sql1);
                while (rs1.next()){ //materias
                    cb.getMaterias().put(rs1.getInt("idMateria"), materias.get(rs1.getInt("idMateria")));
                }
                sql1="SELECT * FROM sistemasem.cursos-grupos where idCursoBedelia="+rs.getInt("id")+" order by idCursoBedelia asc, anio desc, nombre asc;";
                s1= connection.createStatement();
                rs1=s1.executeQuery(sql1);
                while (rs1.next()){ //grupos
                    sql2="SELECT * FROM sistemasem.grupos-alumnos where idCursoBedelia="+rs.getInt("id")+" and anio="+rs1.getInt("anio")+" and nombre="+rs1.getString("nombre")+" order by ciAlumno asc;";
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
                p.put(rs.getInt("id"),new Materia(rs.getInt("id"),rs.getString("nombre"),rs.getString("codigo"),rs.getBoolean("semestral"),rs.getInt("semestr"),rs.getBoolean("secundaria"),rs.getDouble("coeficiente")));
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    boolean eliminarCurso(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    HashMap<Integer, Libreta> obtenerLibretas(HashMap<Integer, Materia> materias, HashMap<Integer, CursoBedelia> cursos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
