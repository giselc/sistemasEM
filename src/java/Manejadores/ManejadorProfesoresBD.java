/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.Cadete;
import Classes.ConexionDB;
import Classes.Familiar;
import Classes.Personal;
import Classes.Bedelia.Profesor;
import Classes.RecordProfesores;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class ManejadorProfesoresBD {
    private Connection connection;
    
    public ManejadorProfesoresBD() {
        connection = ConexionDB.GetConnection();
    }
    public boolean agregarProfesoresTXT() {
        
        File archivo = null;
        BufferedReader br = null;

        try {
             // Apertura del fichero y creacion de BufferedReader para poder
             // hacer una lectura comoda (disponer del metodo readLine())..
            archivo = new File ("C:\\profesores.txt");
            br = new BufferedReader(new InputStreamReader(new FileInputStream(archivo),"UTF-8"));

            // Lectura del fichero
            String linea;
            String sql="INSERT INTO sistemasem.profesores (ci,idGrado,idArma,primerNombre,segundoNombre,primerApellido,segundoApellido,telefono, correo, numeroCuenta, dependenciaFinanciera, observaciones,fechaIngreso,cantHoras,categoria, antiguedad,adminBedelia) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement s = connection.prepareStatement(sql);
            int i;
            String [] campos;
            while((linea=br.readLine())!=null){
               i=1;
               campos= linea.split(";");
               s.setInt(i++, Integer.valueOf(campos[0]));//ci
               s.setInt(i++, Integer.valueOf(campos[1]));//grado
               s.setInt(i++, 0); //arma
               s.setString(i++,campos[2]);//primer nombre
               s.setString(i++,"");//segundo nombre
               s.setString(i++,campos[3]);//primer apellido
               s.setString(i++,"");//segundo apellido
               s.setString(i++,"");//telefono
               s.setString(i++,"");//correo
               s.setString(i++,"");//numeroCuenta
               s.setString(i++,"");//dependenciaFinanciera
               s.setString(i++,"");//observaciones
               s.setString(i++,campos[4]);//fechaIngreso
               s.setInt(i++, Integer.valueOf(campos[6]));//cantHoras
               s.setInt(i++, Integer.valueOf(campos[7]));//categoria
               s.setInt(i++, Integer.valueOf(campos[5]));//antiguedad
               s.setBoolean(i++, true);//adminBedelia
               s.addBatch();
            }
            s.executeBatch();
            s.close();
            return true;
        }
        catch(SQLException | IOException | NumberFormatException e){
            System.out.print("AgregarProfesoresTXT: "+e.getMessage());
            return false;
        }
    }

    LinkedList<Profesor> obtenerProfesoresBD() {
       LinkedList<Profesor> p= new LinkedList<>();
        try {
            Statement s= connection.createStatement();
            String sql;
            ManejadorCodigos mc = ManejadorCodigos.getInstance();
            sql="SELECT * FROM sistemasem.profesores left join sistemasem.grado on profesores.idgrado = grado.codigo order by primerApellido asc;";
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                p.add(new Profesor(rs.getInt("ci"), mc.getGrado(rs.getInt("idGrado")), mc.getArma(rs.getInt("idArma")), rs.getString("primerNombre"), rs.getString("segundoNombre"), rs.getString("primerApellido"), rs.getString("segundoApellido"), rs.getString("observaciones"), rs.getString("fechaIngreso"), rs.getString("telefono"), rs.getString("correo"), rs.getString("numeroCuenta"), rs.getString("dependenciaFinanciera"), rs.getInt("cantHoras"), rs.getInt("categoria"), rs.getInt("antiguedad"), true,new HashMap<>()));
            }
            
        } catch (Exception ex) {
            System.out.print("obtenerProfesoresEM:"+ex.getMessage());
        }
        return p;
    }
    
    public Profesor agregarProfesor(RecordProfesores rp){
        String sql="INSERT INTO sistemasem.profesores (ci,idGrado,idArma,primerNombre,segundoNombre,primerApellido,segundoApellido,telefono, correo, numeroCuenta, dependenciaFinanciera, observaciones,fechaIngreso,cantHoras,categoria, antiguedad,adminBedelia) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int i=1;
        try {
            PreparedStatement s= connection.prepareStatement(sql); // sql a insertar en postulantes
            s.setInt(i++, rp.ci);//ci
            s.setInt(i++, rp.idGrado);//grado
            s.setInt(i++, rp.idArma); //arma
            s.setString(i++,rp.primerNombre);//primer nombre
            s.setString(i++,rp.segundoNombre);//segundo nombre
            s.setString(i++,rp.primerApellido);//primer apellido
            s.setString(i++,rp.segundoApellido);//segundo apellido
            s.setString(i++,rp.telefono);//telefono
            s.setString(i++,rp.correo);//correo
            s.setString(i++,rp.numeroCuenta);//numeroCuenta
            s.setString(i++,rp.dependenciaFinanciera);//dependenciaFinanciera
            s.setString(i++,rp.observaciones);//observaciones
            s.setString(i++,rp.fechaIngreso);//fechaIngreso
            s.setInt(i++, rp.cantHoras);//cantHoras
            s.setInt(i++, rp.categoria);//categoria
            s.setInt(i++, rp.antiguedad);//antiguedad
            s.setBoolean(i++, rp.adminBedelia);//adminBedelia
            int row=s.executeUpdate();
            if (row>0){
                ManejadorCodigos mc= ManejadorCodigos.getInstance();
                return new Profesor(rp.ci, mc.getGrado(rp.idGrado), mc.getArma(rp.idArma), rp.primerNombre, rp.segundoNombre, rp.primerApellido, rp.segundoApellido, rp.observaciones, rp.fechaIngreso, rp.telefono, rp.correo, rp.numeroCuenta, rp.dependenciaFinanciera, rp.cantHoras, rp.categoria, rp.antiguedad, rp.adminBedelia,new HashMap<>());
            }
        }
        catch(Exception e){
            System.out.print("AgregarPersonal: "+e.getMessage());
        }
        return null;
    }
    public boolean modificarProfesor(RecordProfesores rp){
        String sql="UPDATE sistemasem.profesores set idGrado=?,idArma=?,primerNombre=?,segundoNombre=?,primerApellido=?,segundoApellido=?,telefono=?, correo=?, numeroCuenta=?, dependenciaFinanciera=?, observaciones=?,fechaIngreso=?,cantHoras=?,categoria=?, antiguedad=?,adminBedelia=? where ci=?";
        int i=1;
        try {
            PreparedStatement s= connection.prepareStatement(sql); // sql a insertar en postulantes
            s.setInt(i++, rp.idGrado);//grado
            s.setInt(i++, rp.idArma); //arma
            s.setString(i++,rp.primerNombre);//primer nombre
            s.setString(i++,rp.segundoNombre);//segundo nombre
            s.setString(i++,rp.primerApellido);//primer apellido
            s.setString(i++,rp.segundoApellido);//segundo apellido
            s.setString(i++,rp.telefono);//telefono
            s.setString(i++,rp.correo);//correo
            s.setString(i++,rp.numeroCuenta);//numeroCuenta
            s.setString(i++,rp.dependenciaFinanciera);//dependenciaFinanciera
            s.setString(i++,rp.observaciones);//observaciones
            s.setString(i++,rp.fechaIngreso);//fechaIngreso
            s.setInt(i++, rp.cantHoras);//cantHoras
            s.setInt(i++, rp.categoria);//categoria
            s.setInt(i++, rp.antiguedad);//antiguedad
            s.setBoolean(i++, rp.adminBedelia);//adminBedelia
            s.setInt(i++, rp.ci);//ci
            int row=s.executeUpdate();
            return (row>0);
        }
        catch(Exception e){
            System.out.print("ModificarPersonal: "+e.getMessage());
        }
        return false;
    }
    public boolean eliminarProfesor(int ci){
        String sql="DELETE from sistemasem.profesores where ci="+ci;
        int i=1;
        try {
            Statement s= connection.createStatement(); // sql a insertar en postulantes
            return (s.executeUpdate(sql)>0);
        }
        catch(Exception e){
            System.out.print("EliminarProfesor: "+e.getMessage());
        }
        return false;
    }
}
    

