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
import Classes.Bedelia.Notificacion;
import Classes.Bedelia.Promedio;
import Classes.Bedelia.RecordFalta;
import Classes.Bedelia.RecordSancion;
import Classes.Bedelia.Sancion;
import Classes.Bedelia.TemaTratado;
import Classes.Cadete;
import Classes.FaltaSancion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                sql1="SELECT * FROM sistemasem.`cursos-materias` where idCursoBedelia="+rs.getInt("id")+";";
                s1= connection.createStatement();
                rs1=s1.executeQuery(sql1);
                while (rs1.next()){ //materias
                    cb.getMaterias().put(rs1.getInt("idMateria"), materias.get(rs1.getInt("idMateria")));
                }
                sql1="SELECT * FROM sistemasem.`cursos-grupos` where idCursoBedelia="+rs.getInt("id")+" order by anio desc, nombre asc;";
                s1= connection.createStatement();
                rs1=s1.executeQuery(sql1);
                while (rs1.next()){ //grupos
                    sql2="SELECT * FROM sistemasem.`grupos-alumnos` where idCursoBedelia="+rs.getInt("id")+" and anio="+rs1.getInt("anio")+" and nombre='"+rs1.getString("nombre")+"' order by ciAlumno asc;";
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
                p.put(rs.getInt("id"),new Materia(rs.getInt("id"),rs.getString("nombre"),rs.getString("codigo"),rs.getBoolean("semestral"),rs.getInt("semestre"),rs.getBoolean("secundaria"),rs.getDouble("coeficiente"),rs.getBoolean("activo")));
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
                LinkedList<TemaTratado> temasTratados = obtenerTemasTratados(rs.getInt("id"));
                p.get(rs.getInt("ciProfesor")).put(rs.getInt("id"), new Libreta(rs.getInt("id"),materias.get(rs.getInt("idMateria")),cursos.get(rs.getInt("idCurso")).getGrupo(rs.getInt("anio"), rs.getString("nombreGrupo")),mp.getProfesor(ciProfesor),rs.getString("salon"),libretasIndividuales,temasTratados,obtenerMesesCerrados(rs.getInt("id"))));
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
            HashMap<Integer, LinkedList<Nota>> notasOrales;
            HashMap<Integer, LinkedList<Nota>> notasEscritos;
            HashMap<Integer, Falta> faltas;
            ManejadorPersonal mp = ManejadorPersonal.getInstance();
            while (rs.next()){
                sanciones= obtenerSancionesLibretaIndividual(rs.getInt("idLibreta"),rs.getInt("ciAlumno"));
                promedios = obtenerPromediosLibretaIndividual(rs.getInt("idLibreta"),rs.getInt("ciAlumno"));
                notasOrales = obtenerNotasLibretaIndividual(2,rs.getInt("idLibreta"),rs.getInt("ciAlumno"));
                notasEscritos = obtenerNotasLibretaIndividual(1,rs.getInt("idLibreta"),rs.getInt("ciAlumno"));
                faltas = obtenerFaltasLibretaIndividual(rs.getInt("idLibreta"),rs.getInt("ciAlumno"));
                HashMap<Integer,HashMap<Integer,LinkedList<FaltaSancion>>> grillaFaltasSancion= new HashMap<>();
                int mes,dia;
                for(Falta f:faltas.values()){
                    mes= Integer.valueOf(f.getFecha().split("-")[1]);
                    dia= Integer.valueOf(f.getFecha().split("-")[2]);
                    if(grillaFaltasSancion.get(mes)==null){
                        grillaFaltasSancion.put(mes, new HashMap<>());
                    }
                    if(grillaFaltasSancion.get(mes).get(dia)==null){
                        grillaFaltasSancion.get(mes).put(dia,new LinkedList<>());
                    }
                    grillaFaltasSancion.get(mes).get(dia).add(new FaltaSancion(f,null));
                }
                for(Sancion sancion:sanciones.values()){
                    mes= Integer.valueOf(sancion.getFecha().split("-")[1]);
                    dia= Integer.valueOf(sancion.getFecha().split("-")[2]);
                    if(grillaFaltasSancion.get(mes)==null){
                        grillaFaltasSancion.put(mes, new HashMap<>());
                    }
                    if(grillaFaltasSancion.get(mes).get(dia)==null){
                        grillaFaltasSancion.get(mes).put(dia,new LinkedList<>());
                    }
                    grillaFaltasSancion.get(mes).get(dia).add(new FaltaSancion(null,sancion));
                }
                p.put(rs.getInt("ciAlumno"),new LibretaIndividual(idLibreta,mp.getCadete(rs.getInt("ciAlumno")), faltas, grillaFaltasSancion, notasOrales,notasEscritos, promedios, sanciones,rs.getDouble("PromedioAnual"),rs.getDouble("NotaFinal"),rs.getBoolean("activo"),rs.getDouble("promedioPrimeraReunion"),rs.getDouble("promedioSegundaReunion")));
            }
            
        } catch (SQLException | NumberFormatException ex) {
            System.out.print("obtenerLibretasIndividuales-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return p;
    }
    private LinkedList<TemaTratado> obtenerTemasTratados(int idLibreta) {
        LinkedList<TemaTratado> l= new LinkedList<>();
        try {
            Statement s= connection.createStatement();
            String sql;
            sql="SELECT * FROM sistemasem.temasTratados where idLibreta="+idLibreta+" order by fecha desc;";
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                l.add(new TemaTratado(rs.getInt("id"), rs.getString("fecha"), rs.getString("texto")));
            }
            
        } catch (SQLException | NumberFormatException ex) {
            System.out.print("obtenerTemasTratados-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return l;
    }
    private HashMap<Integer, Sancion> obtenerSancionesLibretaIndividual(int idLibretaIndividual, int ciAlumno) {
        HashMap<Integer, Sancion> p= new HashMap<>();
        try {
            Statement s= connection.createStatement();
            String sql;
            sql="SELECT * FROM sistemasem.sanciones where idLibreta="+idLibretaIndividual + " and ciAlumno="+ciAlumno;
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                p.put(rs.getInt("id"),new Sancion(rs.getInt("id"),rs.getInt("tipo"), rs.getInt("minutosTarde"),rs.getString("causa"), rs.getString("fecha")));
            }
        } catch (Exception ex) {
            System.out.print("obtenerSancionesLibretaIndividual-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return p;
    }
    private HashMap<Integer, Boolean> obtenerMesesCerrados(int idLibreta) {
        HashMap<Integer, Boolean> p= new HashMap<>();
        try {
            Statement s= connection.createStatement();
            String sql;
            sql="SELECT * FROM sistemasem.mesesCerrados where  idLibreta="+idLibreta;
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                p.put(rs.getInt("mes"),true);
            }
        } catch (Exception ex) {
            System.out.print("obtenerMesesCerrados-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return p;
    }
    private HashMap<Integer, Promedio> obtenerPromediosLibretaIndividual(int idLibretaIndividual, int ciAlumno) {
        HashMap<Integer, Promedio> p= new HashMap<>();
        try {
            Statement s= connection.createStatement();
            String sql;
            sql="SELECT * FROM sistemasem.promedios where  idLibreta="+idLibretaIndividual + " and ciAlumno="+ciAlumno;
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                p.put(rs.getInt("id"),new Promedio(rs.getInt("id"),rs.getInt("tipoPromedio"), rs.getDouble("nota"),rs.getInt("mes"),rs.getString("juicio")));
            }
        } catch (Exception ex) {
            System.out.print("obtenerPromediosLibretaIndividual-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return p;
    }

    private HashMap<Integer, LinkedList<Nota>> obtenerNotasLibretaIndividual(int tipo,int idLibretaIndividual, int ciAlumno) {
        HashMap<Integer, LinkedList<Nota>> p= new HashMap<>();
        try {
            Statement s= connection.createStatement();
            String sql;
            sql="SELECT * FROM sistemasem.notas where idLibreta="+idLibretaIndividual + " and ciAlumno="+ciAlumno +" and tipo="+tipo;
            ResultSet rs=s.executeQuery(sql);
            int mesNota;
            while (rs.next()){
                mesNota= Integer.valueOf(rs.getString("fecha").split("-")[1]);
                if(!p.containsKey(mesNota)){
                    p.put(mesNota,new LinkedList<>());
                }
                p.get(mesNota).add(new Nota(rs.getInt("id"),rs.getString("fecha"),rs.getInt("tipo"), rs.getString("observacion"),rs.getDouble("nota")));
            }
        } catch (Exception ex) {
            System.out.print("obtenerNotasLibretaIndividual-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return p;
    }

    private HashMap<Integer, Falta> obtenerFaltasLibretaIndividual(int idLibretaIndividual, int ciAlumno) {
        HashMap<Integer, Falta> p= new HashMap<>();
        try {
            Statement s= connection.createStatement();
            String sql;
            sql="SELECT * FROM sistemasem.faltas where idLibreta="+idLibretaIndividual + " and ciAlumno="+ciAlumno;
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                p.put(rs.getInt("id"),new Falta(rs.getInt("id"),rs.getString("fecha"),rs.getInt("canthoras"), rs.getString("codigoMotivo"),rs.getString("observaciones")));
            }
        } catch (Exception ex) {
            System.out.print("obtenerFaltasLibretaIndividual-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return p;
    }

    public boolean agregarMateria(Materia m) {
        try {
            String sql= "insert into sistemasEM.materias (nombre,codigo,semestral, semestre,secundaria,coeficiente,activo) values(?,?,?,?,?,?,?)";
            PreparedStatement s= connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i=1;
            int clave;
            s.setString(i++, m.getNombre());
            s.setString(i++, m.getCodigo());
            s.setBoolean(i++, m.isSemestral());
            s.setInt(i++, m.getSemestre());
            s.setBoolean(i++, m.isSecundaria());
            s.setDouble(i++, m.getCoeficiente());
            s.setBoolean(i++, m.isActivo());
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
            String sql= "insert into sistemasEM.`cursos-materias` (idCursoBedelia,idMateria) values(?,?)";
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
            String sql="DELETE FROM sistemasem.`cursos-materias` where idCursoBedelia="+idCurso+" and idMateria="+idMateria;
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
            String sql="DELETE FROM sistemasem.`cursos-materias` where idMateria="+idMateria;
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
            String sql="DELETE FROM sistemasem.materias where id="+idMateria;
            s.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            System.out.print("eliminarMateria-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return false;
    }

    boolean modificarMateria(Materia m) {
        String sql = "UPDATE sistemasem.materias set nombre=?, semestral=?, secundaria=?, activo=?,semestre=? , coeficiente=? where id=?";
        int i=1;
        try {
            PreparedStatement statement= connection.prepareStatement(sql); // sql a insertar en postulantes
            statement.setString(i++,m.getNombre());
            statement.setBoolean(i++,m.isSemestral());
            statement.setBoolean(i++,m.isSecundaria());
            statement.setBoolean(i++,m.isActivo());
            statement.setInt(i++,m.getSemestre());
            statement.setDouble(i++,m.getCoeficiente());
            statement.setInt(i++,m.getId());
            int row=statement.executeUpdate();
            return(row>0);
            
        } catch (Exception ex) {
            System.out.print("modificarMateria-ManejadorBedeliaBD: "+ex);
            return false;
        }
    }

    public boolean asociarMateriasCurso(String[] idMaterias, String idCurso) {
        try {
            String sql= "insert into sistemasEM.`cursos-materias` (idCursoBedelia,idMateria) values(?,?)";
            PreparedStatement s= connection.prepareStatement(sql);
            int i;
            for(String m:idMaterias){
                i=1;
                s.setInt(i++, Integer.valueOf(idCurso));
                s.setInt(i++, Integer.valueOf(m));
                s.addBatch();
            }
            int[] row=s.executeBatch();
            for(int ret:row){
                if(ret<0){
                    return false;
                }
            }
            return true;
        } catch (Exception ex) {
            System.out.print("asociarMateriasCurso-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return false;
    }

    public boolean agregarGrupoCurso(int idCurso, int anio, String nombre) {
        try {
            String sql= "insert into sistemasEM.`cursos-grupos` (idCursoBedelia,anio,nombre) values(?,?,?)";
            PreparedStatement s= connection.prepareStatement(sql);
            int i=1;
            s.setInt(i++, idCurso);
            s.setInt(i++, anio);
            s.setString(i++, nombre);
            int row=s.executeUpdate();
            return row>0;
        } catch (Exception ex) {
            System.out.print("agregarGrupoCurso-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return false;
    }

    boolean asociarAlumnosGrupo(LinkedList<Cadete> alumnos, LinkedList<Libreta> listaL,Grupo g) {
        try {
            String sql= "insert into sistemasEM.`grupos-alumnos` (idCursoBedelia,anio,nombre,ciAlumno) values(?,?,?,?)";
            PreparedStatement s= connection.prepareStatement(sql);
            String sql1= "insert into sistemasEM.`libretasIndividuales` (idLibreta,ciAlumno,promedioAnual,notaFinal,activo) values(?,?,?,?,?)";
            String sql2= "UPDATE sistemasEM.`libretasIndividuales` set activo=? where idLibreta=? and ciAlumno=?";
            PreparedStatement s1= connection.prepareStatement(sql1);
            PreparedStatement s2= connection.prepareStatement(sql2);
            int i,j;
            for(Cadete alumno:alumnos){
                i=1;
                s.setInt(i++, g.getCusoBedelia().getId());
                s.setInt(i++, g.getAnio());
                s.setString(i++, g.getNombre());
                s.setInt(i++, alumno.getCi());
                s.addBatch();
                for(Libreta l:listaL){
                    j=1;
                    if(l.getLibretasIndividuales().containsKey(alumno.getCi())){
                        s2.setBoolean(j++, true);
                        s2.setInt(j++, l.getId());
                        s2.setInt(j++, alumno.getCi());
                        s2.addBatch();
                    }
                    else{
                        s1.setInt(j++, l.getId());
                        s1.setInt(j++, alumno.getCi());
                        s1.setInt(j++, 0);
                        s1.setInt(j++, 0);
                        s1.setBoolean(j++, true);
                        s1.addBatch();
                    }
                }
            }
            
            s.executeBatch();
            s1.executeBatch();
            s2.executeBatch();
        } catch (Exception ex) {
            System.out.print("asociarAlumnosGrupo-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return true;
    }

    boolean desasociarAlumnoGrupo(Integer ciAlumno, Grupo grupo, LinkedList<Libreta> listaL) {
        try{
            String sql= "DELETE FROM sistemasEM.`grupos-alumnos` where ciAlumno="+ciAlumno;
            Statement s= connection.createStatement();
            String sql1= "UPDATE sistemasEM.`libretasindividuales` SET `activo`=0 WHERE idLibreta=? and ciAlumno=?";
            PreparedStatement s1= connection.prepareStatement(sql1);
            int j;
            if(s.executeUpdate(sql)>0){
                for(Libreta l:listaL){
                    j=1;
                    s1.setInt(j++, l.getId());
                    s1.setInt(j++, ciAlumno);
                    s1.addBatch();
                }
                s1.executeBatch();
                return true;
            }
        } catch (Exception ex) {
            System.out.print("desasociarAlumnoGrupo-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return false;
    }

    boolean desasociarAlumnosGrupo(String[] listaAlumnos, Grupo grupo, LinkedList<Libreta> listL) {
        try{
            String sql= "DELETE FROM sistemasEM.`grupos-alumnos` where ciAlumno=?";
            PreparedStatement s= connection.prepareStatement(sql);
            String sql1= "UPDATE sistemasEM.`libretasindividuales` SET `activo`=0 WHERE idLibreta=? and ciAlumno=?";
            PreparedStatement s1= connection.prepareStatement(sql1);
            int j;
            for(String ci:listaAlumnos){
                s.setInt(1, Integer.valueOf(ci));
                s.addBatch();
                for(Libreta l:listL){
                    j=1;
                    s1.setInt(j++, l.getId());
                    s1.setInt(j++, Integer.valueOf(ci));
                    s1.addBatch();
                }
            }
            s.executeBatch();
            s1.executeBatch();
            return true;
        
        } catch (Exception ex) {
            System.out.print("desasociarAlumnoGrupo-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return false;
    }

    public int crearLibreta(int idCurso, int anioGrupo, String nombreGrupo, int idMateria, int ciProfesor,String salon, HashMap<Integer, Cadete> alumnos) {
        try {
            String sql="insert into sistemasEM.libretas(ciProfesor,idCurso,anio,nombreGrupo,idMateria,salon) values (?,?,?,?,?,?)";
            String sql1= "insert into sistemasEM.`libretasIndividuales` (idLibreta,ciAlumno,promedioAnual,notaFinal,activo) values(?,?,?,?,?)";
            PreparedStatement s= connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            PreparedStatement s1= connection.prepareStatement(sql1);
            int i,j;
            i=1;
            s.setInt(i++, ciProfesor);
            s.setInt(i++, idCurso);
            s.setInt(i++, anioGrupo);
            s.setString(i++, nombreGrupo);
            s.setInt(i++, idMateria);
            s.setString(i++, salon);
            int row=s.executeUpdate();
            if(row>0){
                ResultSet rs=s.getGeneratedKeys(); //obtengo las ultimas llaves generadas
                if(rs.next()){ 
                    int clave=rs.getInt(1);
                    if(clave!=-1){
                        for(Cadete alumno:alumnos.values()){
                            j=1;
                            s1.setInt(j++, clave);
                            s1.setInt(j++, alumno.getCi());
                            s1.setInt(j++, 0);
                            s1.setInt(j++, 0);
                            s1.setBoolean(j++, true);
                            s1.addBatch();
                        }
                        s1.executeBatch();
                    }
                    return clave;
                }
            }
        } catch (SQLException ex) {
            System.out.print("crearLibreta-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return -1;
    }

    public int agregarFalta(Libreta l, Cadete c, String fecha, String codigoFalta, int cantHoras, String observaciones) {
        try {
            String sql= "insert into sistemasEM.faltas (idLibreta,ciAlumno,fecha, codigoMotivo,cantHoras,observaciones) values(?,?,?,?,?,?)";
            PreparedStatement s= connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i=1;
            s.setInt(i++, l.getId());
            s.setInt(i++, c.getCi());
            s.setString(i++, fecha);
            s.setString(i++, codigoFalta);
            s.setInt(i++, cantHoras);
            s.setString(i++, observaciones);
            int row=s.executeUpdate();
            if(row>0){
                ResultSet rs=s.getGeneratedKeys(); //obtengo las ultimas llaves generadas
                if(rs.next()){ 
                    return(rs.getInt(1));
                }
            }
        } catch (Exception ex) {
            System.out.print("agregarFalta-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return -1;
    }

    public int agregarNotificacion(Libreta libreta, Cadete cadete, RecordFalta falta, RecordSancion sancion,String fecha,boolean eliminado) {
        try {
            String sql= "insert into sistemasEM.notificaciones (idLibreta,ciCadete,idFalta,cantHoras,codigoMotivo,observaciones, idSancion,tipo,minutosTardes,causa ,estado,fecha,eliminado) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement s= connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i=1;
            s.setInt(i++, libreta.getId());
            s.setInt(i++, cadete.getCi());
            if(falta!=null){
                s.setInt(i++, falta.idFalta);
                s.setInt(i++, falta.cantHoras);
                s.setString(i++, falta.codigoMotivo);
                s.setString(i++, falta.observaciones);
            }
            else{
                s.setInt(i++, -1);
                s.setInt(i++, -1);
                s.setString(i++, "");
                s.setString(i++, "");
            }
            if(sancion!=null){
                s.setInt(i++, sancion.idSancion);
                s.setInt(i++, sancion.tipo);
                s.setInt(i++, sancion.minutosTardes);
                s.setString(i++, sancion.causa);
            }
            else{
                s.setInt(i++, -1);
                s.setInt(i++, -1);
                s.setInt(i++,-1);
                s.setString(i++, "");
            }
            s.setInt(i++, 1);
            s.setString(i++, fecha);
            s.setBoolean(i++, eliminado);
            int row=s.executeUpdate();
            if(row>0){
                ResultSet rs=s.getGeneratedKeys(); //obtengo las ultimas llaves generadas
                if(rs.next()){ 
                    return(rs.getInt(1));
                }
            }
        } catch (Exception ex) {
            System.out.print("agregarNotificacion-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return -1;
    }

    LinkedList<Notificacion> obtenerNotificaciones(HashMap<Integer, HashMap<Integer,Libreta>> libretas, int estado) {
        LinkedList<Notificacion> p= new LinkedList<>();
        try {
            Statement s= connection.createStatement();
            String sql;
            ManejadorPersonal mp= ManejadorPersonal.getInstance();
            sql="SELECT * FROM sistemasem.notificaciones where estado="+estado +" order by fecha desc;";
            ResultSet rs=s.executeQuery(sql);
            Libreta l=null;
            Falta falta;
            Sancion sancion;
            while (rs.next()){
                for(HashMap<Integer,Libreta> lista:libretas.values()){
                    if(lista.containsKey(rs.getInt("idLibreta"))){
                        l= lista.get(rs.getInt("idLibreta"));
                    }
                }
                RecordFalta rf= new RecordFalta();
                rf.idFalta=rs.getInt("idFalta");
                rf.cantHoras=rs.getInt("cantHoras");
                rf.codigoMotivo=rs.getString("codigoMotivo");
                rf.observaciones=rs.getString("observaciones");
                RecordSancion rsancion= new RecordSancion();
                rsancion.causa=rs.getString("causa");
                rsancion.idSancion=rs.getInt("idSancion");
                rsancion.minutosTardes=rs.getInt("minutosTardes");
                rsancion.tipo=rs.getInt("tipo");
                p.add(new Notificacion(rs.getInt("id"), l, mp.getCadete(rs.getInt("ciCadete")), rf, rsancion, estado,rs.getString("fecha"),rs.getBoolean("eliminado")));
            }
            
        } catch (Exception ex) {
            System.out.print("obtenerNotificaciones-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return p;
    }

    boolean marcarLeidoNotificacion(int id, boolean aLeido) {
        String sql = "UPDATE sistemasem.notificaciones set estado=? where id=?";
        int i=1;
        try {
            PreparedStatement statement= connection.prepareStatement(sql); // sql a insertar en postulantes
            if(aLeido){
                statement.setInt(i++,2);
            }
            else{
                 statement.setInt(i++,1);
            }
            statement.setInt(i++,id);
            int row=statement.executeUpdate();
            return(row>0);
        } catch (SQLException ex) {
            System.out.print("marcarLeidoNotificacion-ManejadorBedeliaBD: "+ex);
            return false;
        }
    }

    boolean eliminarNotificacion(int id) {
        Statement s;
        try {
            s = connection.createStatement();
            String sql="DELETE FROM sistemasem.notificaciones where id="+id;
            return(s.executeUpdate(sql)>0);
        } catch (SQLException ex) {
            System.out.print("eliminarNotificacion-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return false;
    }

    boolean eliminarFalta(int idFalta) {
        Statement s;
        try {
            s = connection.createStatement();
            String sql="DELETE FROM sistemasem.faltas where id="+idFalta;
            return(s.executeUpdate(sql)>0);
        } catch (SQLException ex) {
            System.out.print("eliminarFalta-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return false;
    }

    public int agregarTemaTratado(String fecha, String texto,int idLibreta) {
        try {
            String sql= "insert into sistemasEM.temasTratados (fecha,texto,idLibreta) values(?,?,?)";
            PreparedStatement s= connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i=1;
            s.setString(i++, fecha);
            s.setString(i++, texto);
            s.setInt(i++,idLibreta);
            int row=s.executeUpdate();
            if(row>0){
                ResultSet rs=s.getGeneratedKeys(); //obtengo las ultimas llaves generadas
                if(rs.next()){ 
                    return(rs.getInt(1));
                }
            }
        } catch (Exception ex) {
            System.out.print("agregarTemaTratado-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return -1;
    }

    public boolean elimTemaTratado(Integer idTema) {
        Statement s;
        try {
            s = connection.createStatement();
            String sql="DELETE FROM sistemasem.temasTratados where id="+idTema;
            return(s.executeUpdate(sql)>0);
        } catch (SQLException ex) {
            System.out.print("elimTemaTratado-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return false;
    }

    public int agregarSancion(Libreta l, Cadete c, String fecha, int codigoSancion, Integer minutosTardes, String causa) {
        try {
            String sql= "insert into sistemasEM.sanciones (idLibreta,ciAlumno,fecha, tipo,minutosTarde,causa) values(?,?,?,?,?,?)";
            PreparedStatement s= connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i=1;
            s.setInt(i++, l.getId());
            s.setInt(i++, c.getCi());
            s.setString(i++, fecha);
            s.setInt(i++,codigoSancion );
            s.setInt(i++, minutosTardes);
            s.setString(i++, causa);
            int row=s.executeUpdate();
            if(row>0){
                ResultSet rs=s.getGeneratedKeys(); //obtengo las ultimas llaves generadas
                if(rs.next()){ 
                    return(rs.getInt(1));
                }
            }
        } catch (Exception ex) {
            System.out.print("agregarSancion-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return -1;
    }

    public boolean eliminarSancion(int id) {
        Statement s;
        try {
            s = connection.createStatement();
            String sql="DELETE FROM sistemasem.sanciones where id="+id;
            return(s.executeUpdate(sql)>0);
        } catch (SQLException ex) {
            System.out.print("eliminarSancion-ManejadorBedeliaBD:"+ex.getMessage());
        }
        return false;
    }

   
}
