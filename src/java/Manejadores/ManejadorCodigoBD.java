/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.Arma;
import Classes.Carrera;
import Classes.ConexionDB;
import Classes.Curso;
import Classes.Departamento;
import Classes.EstadoCivil;
import Classes.Grado;
import Classes.Tipo;
import Classes.TipoDescuento;
import Classes.TipoDocumento;
import Classes.TipoPersonal;
import Classes.Usuario;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Gisel
 */
public class ManejadorCodigoBD {
    private final Connection connection;

    public ManejadorCodigoBD() {
        connection = ConexionDB.GetConnection();
    }
    public static String fechaFormatoDDMMAAAA(Date fecha){
        String dia= String.valueOf(fecha.getDate());
        if (fecha.getDate()<10){
            dia="0"+dia;
        }
        String mes = String.valueOf(fecha.getMonth()+1);
        if(fecha.getMonth()+1<10){
            mes="0"+mes;
        }
        return dia+"-"+mes+"-"+(fecha.getYear()+1900);
    }
    
    public Departamento getDepartamento(int codigo){
        Departamento d= null;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from sistemasEM.departamentos where codigo="+ codigo;
            ResultSet rs=s.executeQuery(sql);
            if (rs.next()){
                d = new Departamento(codigo, rs.getString("descripcion"));
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
    public HashMap<Integer,Departamento> getDepartamentos(){
        HashMap<Integer,Departamento> al= new HashMap<>();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from sistemasEM.departamentos order by descripcion asc";
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                al.put(rs.getInt("codigo"),new Departamento(rs.getInt("codigo"), rs.getString("descripcion")));
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    
    public Curso getCurso(int codigo){
        Curso d= null;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from sistemasEM.cursos where codigo="+ codigo;
            ResultSet rs=s.executeQuery(sql);
            if (rs.next()){
                d = new Curso(codigo, rs.getString("descripcion"),rs.getString("abreviacion"));
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
    public HashMap<Integer,Curso> getCursos(){
       HashMap<Integer,Curso> al= new HashMap<>();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from sistemasEM.cursos order by descripcion asc";
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                al.put(rs.getInt("codigo"),new Curso(rs.getInt("codigo"), rs.getString("descripcion"),rs.getString("abreviacion")));
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public Grado getGrado(int codigo){
        Grado d= null;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from sistemasEM.grado where codigo="+ codigo;
            ResultSet rs=s.executeQuery(sql);
            if (rs.next()){
                d = new Grado(codigo, rs.getString("descripcion"),rs.getString("abreviacion"),this.getTipoPersonal(rs.getInt("idTipoPersonal")));
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
    public HashMap<Integer,Grado> getGrados(){
       HashMap<Integer,Grado> al= new HashMap<>();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from sistemasEM.Grado order by descripcion asc";
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                al.put(rs.getInt("codigo"),new Grado(rs.getInt("codigo"), rs.getString("descripcion"),rs.getString("abreviacion"),this.getTipoPersonal(rs.getInt("idTipoPersonal"))));
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public Arma getArma(int codigo){
        Arma d= null;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from sistemasEM.arma where codigo="+ codigo;
            ResultSet rs=s.executeQuery(sql);
            if (rs.next()){
                d = new Arma(codigo, rs.getString("descripcion"));
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
    public HashMap<Integer,Arma> getArmas(){
        HashMap<Integer,Arma> al= new HashMap<>();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from sistemasEM.arma order by descripcion asc";
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                al.put(rs.getInt("codigo"),new Arma(rs.getInt("codigo"), rs.getString("descripcion")));
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public EstadoCivil getEstadoCivil(int codigo){
        EstadoCivil d= null;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from sistemasEM.estadocivil where codigo="+ codigo;
            ResultSet rs=s.executeQuery(sql);
            if (rs.next()){
                d = new EstadoCivil(codigo, rs.getString("descripcion"));
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
    
    public HashMap<Integer,EstadoCivil> getEstadosCiviles(){
        HashMap<Integer,EstadoCivil> al= new HashMap<>();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from sistemasEM.estadocivil order by descripcion asc";
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                al.put(rs.getInt("codigo"),new EstadoCivil(rs.getInt("codigo"), rs.getString("descripcion")));
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public TipoDescuento getTipoDescuento(int codigo){
        TipoDescuento d= null;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from sistemasEM.tiposDescuentos where codigo="+ codigo;
            ResultSet rs=s.executeQuery(sql);
            if (rs.next()){
                d = new TipoDescuento(codigo, rs.getString("descripcion"));
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
    public ArrayList<Tipo> getTipoDescuentos(){
        ArrayList<Tipo> al= new ArrayList<>();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from sistemasEM.tiposDescuentos order by descripcion asc";
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                al.add(new TipoDescuento(rs.getInt("codigo"), rs.getString("descripcion")));
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public TipoDocumento getTipoDocumento(int codigo){
        TipoDocumento d= null;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from sistemasEM.tiposDocumento where codigo="+ codigo;
            ResultSet rs=s.executeQuery(sql);
            if (rs.next()){
                d = new TipoDocumento(codigo, rs.getString("descripcion"));
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
    public HashMap<Integer,Tipo> getTipoDocumentos(){
        HashMap<Integer,Tipo> al= new HashMap();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from sistemasEM.tiposDocumentos order by descripcion asc";
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                al.put(rs.getInt("codigo"),new TipoDocumento(rs.getInt("codigo"), rs.getString("descripcion")));
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public TipoPersonal getTipoPersonal(int codigo){
        TipoPersonal d= null;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from sistemasEM.tiposPersonal where codigo="+ codigo;
            ResultSet rs=s.executeQuery(sql);
            if (rs.next()){
                d = new TipoPersonal(codigo, rs.getString("descripcion"));
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
    public HashMap<Integer,Tipo> getTipoPersonal(){
        HashMap<Integer,Tipo> al= new HashMap();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from sistemasEM.tiposPersonal order by descripcion asc";
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                al.put(rs.getInt("codigo"),new TipoDocumento(rs.getInt("codigo"), rs.getString("descripcion")));
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public Usuario getUsuario(int id){
        Usuario d= null;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from sistemasEM.usuarios where id="+ id;
            ResultSet rs=s.executeQuery(sql);
            if (rs.next()){
                TipoDescuento td =  this.getTipoDescuento(rs.getInt("permisosDescuento"));
                TipoPersonal tp =  this.getTipoPersonal(rs.getInt("permisosPersonal"));
                d= new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("mostrar"),rs.getBoolean("admin"),tp,td,rs.getBoolean("notas"),rs.getBoolean("habilitacion"),rs.getBoolean("profesor"),rs.getInt("ciProfesor"));

            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
    public ArrayList<Usuario> getUsuarios(){
        ArrayList<Usuario> al= new ArrayList<>();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from sistemasEM.usuarios order by mostrar asc";
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                TipoDescuento td =  this.getTipoDescuento(rs.getInt("permisosDescuento"));
                TipoPersonal tp =  this.getTipoPersonal(rs.getInt("permisosPersonal"));
                al.add( new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("mostrar"),rs.getBoolean("admin"),tp,td,rs.getBoolean("notas"),rs.getBoolean("habilitacion"),rs.getBoolean("profesor"),rs.getInt("ciProfesor")));
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    
    
    //retorna el userID; si no existe retorna -1
    public int loginCorrecto(String user, String pass){
        int b=-1;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from sistemasEM.usuarios where nombre='"+ user+"' and contrasena=MD5('"+pass+"')";
            ResultSet rs=s.executeQuery(sql);
            
            if (rs.next()){
               b=rs.getInt("id");
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
  /*  public int cambiarContrasena(int userId, String passOld, String passNew){
        int b=-1;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from postulantes.usuarios where id=" + String.valueOf(userId) + " and contrasena=MD5('" + passOld + "')";
            ResultSet rs=s.executeQuery(sql);
            if (rs.next()){
                String sql1="UPDATE postulantes.usuarios set contrasena=MD5('" + passNew + "') where id=" + String.valueOf(userId);
                Statement r= connection.createStatement();
                int a = r.executeUpdate(sql1);
                if(a>0){
                    b= 0;
                }
            }
        } catch (Exception ex) {
            //out.print(ex.getMessage());
        }
        return b;
    }*/
    
    //funcion creada para asegurar que no existe otro usuario con usuario='usuario'
    public boolean existeUsuario(String usuario){
        try {
            Statement s= connection.createStatement();
            String sql="Select * from sistemasEM.usuarios where nombre='"+usuario+"'";
            ResultSet rs= s.executeQuery(sql);
            if (rs.next()){
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    //Retorna un Usuario con los parametros indicados persistiendolo en la base de datos si 'creador' es Admin.
    //retorna null si creador no es admin o se produjo algun error en la escritura de la base de datos.
    //PRECONDICIONES: - existeUsuario(usuario)==false
    public boolean crarUsuario(Usuario creador, String nombre, String nombreMostrar, String contrasena, boolean admin,boolean s1,boolean descuentos, int tipodescuento, boolean notas, boolean habilitacion){
        if (creador.isAdmin()){
            try {
                String sql= "insert into sistemasEM.usuarios (nombre, mostrar, contrasena,admin,s1,descuentos, tipodescuento,notas, habilitacion) values(?,?,MD5(?),?,?,?,?,?,?)";
                PreparedStatement s= connection.prepareStatement(sql);
                int i=1;
                s.setString(i++, nombre);
                s.setString(i++, nombreMostrar);
                s.setString(i++, contrasena);
                s.setBoolean(i++, admin);
                s.setBoolean(i++, s1);
                s.setBoolean(i++, descuentos);
                s.setInt(i++, tipodescuento);
                s.setBoolean(i++, notas);
                s.setBoolean(i++, habilitacion);
                int result = s.executeUpdate();
                if(result>0){
                    return true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    //retorna false si 'creador' no es admin o se produjo algun error en la escritura de la base de datos.
    //atributo usuario no es modificable.
    //atributo contrasena modificable con la funcion cambiarContrasena
    public boolean ModificarUsuario(Usuario creador, int id, String nombreMostrar, boolean admin, boolean s1, boolean descuentos, int tipoDescuento, boolean notas, boolean habilitacion){
        if (creador.isAdmin()){
            try {
                String sql= "Update sistemasEM.usuarios set mostrar=?, admin=?, s1=?, descuentos=?, tipoDescuento=?, notas=?, habilitacion=? where id="+id;
                PreparedStatement s= connection.prepareStatement(sql);
                int i=1;
                s.setString(i++, nombreMostrar);
                s.setBoolean(i++, admin);
                s.setBoolean(i++, s1);
                s.setBoolean(i++, descuentos);
                s.setInt(i++, tipoDescuento);
                s.setBoolean(i++, notas);
                s.setBoolean(i++, habilitacion);
                int result = s.executeUpdate();
                if(result>0){
                    return true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    
    //retorna false si 'creador' no es admin(o no es contrasena propia) o su contrasena anterior es incorrecta o se produjo algun error en la escritura de la base de datos.
    public boolean cambiarContrasena(Usuario creador, int id, String contrasenaNueva, String contrasenaAnterior){
        if(creador.isAdmin()){
            try {
                String sql= "Update sistemaEM.usuarios set contrasena=MD5('"+contrasenaNueva+"') where id="+id;
                Statement s= connection.createStatement();
                int result = s.executeUpdate(sql);
                if(result>0){
                    return true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            if(creador.getId()== id){
                try {
                    String sql= "Update sistemaEM.usuarios set contrasena=MD5('"+contrasenaNueva+"') where id="+id + " and contrasena=MD5('"+contrasenaAnterior+"')";
                    Statement s= connection.createStatement();
                    int result = s.executeUpdate(sql);
                    if(result>0){
                        return true;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }
    
    //Retorna la lista de Usuarios del sistema si el usuario 'creador' es admin
    public ArrayList<Usuario> getUsuarios(Usuario creador){
        ArrayList<Usuario> al= new ArrayList<>();
        if (creador.isAdmin()){
            try {
                String sql= "Select * from sistemasEM.usuarios";
                Statement s= connection.createStatement();
                ResultSet rs = s.executeQuery(sql);
                Usuario u;
                while(rs.next()){
                    TipoDescuento td =  this.getTipoDescuento(rs.getInt("permisosDescuento"));
                    TipoPersonal tp =  this.getTipoPersonal(rs.getInt("permisosPersonal"));
                    al.add( new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("mostrar"),rs.getBoolean("admin"),tp,td,rs.getBoolean("notas"),rs.getBoolean("habilitacion"),rs.getBoolean("profesor"),rs.getInt("ciProfesor")));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return al;
    }
    
    //retorna el usuario con id='id' si el usuario 'creador' es admin o es el mismo. 
    public Usuario getUsuario(Usuario creador, int id){
        Usuario u=null;
        if (creador.isAdmin() || creador.getId()==id){
            try {
                String sql= "Select * from postulantes.usuarios where id="+id;
                Statement s= connection.createStatement();
                ResultSet rs = s.executeQuery(sql);
                while(rs.next()){
                    TipoDescuento td =  this.getTipoDescuento(rs.getInt("permisosDescuento"));
                    TipoPersonal tp =  this.getTipoPersonal(rs.getInt("permisosPersonal"));
                    u= new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("mostrar"),rs.getBoolean("admin"),tp,td,rs.getBoolean("notas"),rs.getBoolean("habilitacion"),rs.getBoolean("profesor"),rs.getInt("ciProfesor"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return u;
    }
    
    public Usuario getUsuario(String usuario, String pass){
       
        Usuario u= null;
        try {
                String sql= "Select * from postulantes.usuarios where usuario='"+usuario+"' and contrasena=MD5('"+pass+"')";
                Statement s= connection.createStatement();
                ResultSet rs = s.executeQuery(sql);
                while(rs.next()){
                    TipoDescuento td =  this.getTipoDescuento(rs.getInt("permisosDescuento"));
                    TipoPersonal tp =  this.getTipoPersonal(rs.getInt("permisosPersonal"));
                    u= new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("mostrar"),rs.getBoolean("admin"),tp,td,rs.getBoolean("notas"),rs.getBoolean("habilitacion"),rs.getBoolean("profesor"),rs.getInt("ciProfesor"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return u;
    }
    public void imprimirUsuarios(PrintWriter out, Usuario creador){
        out.print("<h1 align='center'>USUARIOS DEL SISTEMA</h1>");
        out.print("<table style=\"width: 100%;\">");
        ArrayList<Usuario> au = this.getUsuarios(creador);
        out.print("<tr style='background-color:#ffcc66'>");
                out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Usuario</h3></td>");
                out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Nombre para mostrar</h3></td>");
                out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Admin</h3></td>");
                out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Personal</h3></td>");
                out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Descuentos</h3></td>");
                out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Tipo Descuento</h3></td>");
                out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Notas</h3></td>");
                out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Habilitacion</h3></td>");
        out.print("</tr></table>  <table style=\"width: 100%;\">" );
        int i=0;
        String color;
        for (Usuario u1: au){
            if ((i%2)==0){
                color=" #ccccff";
            }
            else{
                color=" #ffff99";
            }
            i++;

            out.print("<tr style='background-color:"+color+"'>");
                out.print("<td style='width: 20%' align='center'>"+u1.getNombre()+"</td>");
                out.print("<td style='width: 20%' align='center'>"+u1.getNombreMostrar()+"</td>");
                out.print("<td style='width: 10%' align='center'>"+u1.isAdmin()+"</td>");
                if(u1.getPermisosPersonal()!=null){
                    out.print("<td style='width: 10%' align='center'>"+u1.getPermisosPersonal().getDescripcion()+"</td>"); 
                }
                else{
                    out.print("<td style='width: 10%' align='center'></td>");
                }
                if(u1.getPermisosDescuento()!=null){
                    out.print("<td style='width: 10%' align='center'>"+u1.getPermisosDescuento().getDescripcion()+"</td>"); 
                }
                else{
                    out.print("<td style='width: 10%' align='center'></td>");
                }
                out.print("<td style='width: 10%' align='center'>"+u1.isNotas()+"</td>");
                out.print("<td style='width: 10%' align='center'>"+u1.isHabilitacion()+"</td>");
            out.print("</tr>");
        }
        out.print("</table>");
    }

    public HashMap<Integer, Carrera> getCarreras() {
        HashMap<Integer,Carrera> al= new HashMap<>();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from sistemasEM.carrera order by descripcion asc";
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                al.put(rs.getInt("codigo"),new Carrera(rs.getInt("codigo"), rs.getString("descripcion")));
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorCodigoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    
    
}
