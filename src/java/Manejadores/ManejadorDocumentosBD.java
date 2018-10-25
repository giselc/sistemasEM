/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.ConexionDB;
import Classes.Documento;
import Classes.Tipo;
import Classes.TipoDocumento;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.servlet.http.Part;

/**
 *
 * @author Gisel
 */
public class ManejadorDocumentosBD {
    private final Connection connection;

    public ManejadorDocumentosBD() {
        connection = ConexionDB.GetConnection();
    }
    public HashMap<Integer,Documento> getDocumentos(int ci){ //sin la imagen
        HashMap<Integer,Documento> as= new HashMap<>();
        try {
            String sql= "Select * from documentos where idPersonal="+ci;
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            Documento documento;
            ManejadorCodigos mc = ManejadorCodigos.getInstance();
            while(rs.next()){
                documento=new Documento(rs.getInt("id"),(TipoDocumento)mc.getTipoDocumento(rs.getInt("idTipoDocumento")),rs.getString("nombre"));
                as.put(rs.getInt("id"),documento);
            }
        } catch (SQLException ex) {
           
        }
        
        return as;
    }
    public Documento getDocumento(int id){
        Documento p=null;
        try {
            String sql= "Select * from documentos where id="+id;
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            ManejadorCodigos mc = ManejadorCodigos.getInstance();
            if(rs.next()){
                p= new Documento(id,(TipoDocumento) mc.getTipoDocumento(rs.getInt("idTipoDocumento")), rs.getString("nombre"));
            }
        } catch (SQLException ex) {
        }
        return p;
    }
  
    public Documento crearDocumento(String path,Tipo tipoDocumento, int idPersonal, Part archivo){
        int clave = -1;
        boolean ret=false;
        String nombre=this.getFileName(archivo);
        try {
            String sql= "insert into documentos (idPersonal, idTipoDocumento, nombre) values("+idPersonal+","+tipoDocumento.getId()+",'"+nombre+"')";
            Statement s= connection.createStatement();
            int result = s.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
            if(result>0){
                ResultSet rs=s.getGeneratedKeys(); //obtengo las ultimas llaves generadas
                if(rs.next()){ 
                    clave=rs.getInt(1);
                    if(clave!=-1){
                        ret=this.subirArchivo(path,archivo, clave, idPersonal);
                        if(ret){
                            return new Documento(clave, tipoDocumento, nombre); 
                        }
                        else{
                            sql="delete * from documentos where id="+clave; //si se produce un error al cargar el archivo, debo eliiminarlo de la base para que no tengan accesibilidad
                            s.executeUpdate(sql);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            
        }
        return null;
    }
    public boolean eliminarDocumento(String path, Documento doc, int idPersonal){
        try {
            Statement s= connection.createStatement();
            String sql="delete from documentos where id="+doc.getId();
            int i= s.executeUpdate(sql);
            if (i>0){
                this.eliminarArchivo(path, idPersonal, doc); //si sale de la base de datos y el archivo no se borro de la compu, se retorna true igual debido a que ese archivo deja de ser accesible
                return true;
            }
        } catch (SQLException ex) {
        }
        return false;
    }
    
    
    
    //path getServletContext().getRealPath("/")
    private boolean eliminarArchivo(String path, int ciPersonal, Documento documento){
        if(!documento.getNombre().equals("")){
            String extension = documento.getNombre().substring(documento.getNombre().lastIndexOf("."));
            File f = new File(path+"/Documentos/"+ciPersonal+"-"+documento.getId()+extension);
            if(f.exists()){
                return f.delete();
            }
            return true;
        }
        return true;
    }
    private boolean subirArchivo(String path, Part archivo, int idDocumento, int ciPersonal){
        try{
            FileOutputStream output = null;
            String name = this.getFileName(archivo);
            if(name!=null && !name.equals("")){
                String extension = name.substring(name.lastIndexOf("."));
                InputStream input = archivo.getInputStream();
                try {
                    output = new FileOutputStream( path+"/Documentos/"+ciPersonal+"-"+idDocumento+extension);
                    int leido = 0;
                    leido = input.read();
                    while (leido != -1) {
                        output.write(leido);
                        leido = input.read();
                    }
                } catch (FileNotFoundException ex) {
                    
                } catch (IOException ex) {
                    
                } finally {
                    try {
                        output.flush();
                        output.close();
                        input.close();
                    } catch (IOException ex) {
                        
                    }
                }
            }
            return true;
        }
        catch(Exception e){
            
        }
        return false;
    }
    private String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
                if (cd.trim().startsWith("filename")) {
                        return cd.substring(cd.indexOf('=') + 1).trim()
                                        .replace("\"", "");
                }
        }
        return null;
    }
    
      /*public boolean modificarDocumento(String path, int idDoc, int idPersonal, Part archivo, Documento doc, String nombre){
        boolean ret=false;
        try {
            String sql= "update documentos set idTipoDocumento=?, nombre=? where id="+idDoc;
            PreparedStatement s= connection.prepareStatement(sql);
            s.setInt(1,doc.getId());
            s.setString(2,nombre);
            int result = s.executeUpdate();
            if(result>0){
               ret=this.modificarArchivo(path, archivo, doc, idPersonal);
            }
        } catch (Exception ex) {
        }
        return ret;
    }*/
    /*private boolean modificarArchivo(String path, Part archivo, Documento doc, int ciPersonal){
        String name = this.getFileName(archivo);
        boolean b=true;
        if(name!=null && !name.equals("") ){
            if(doc.getNombre().equals("")){
                b=this.subirArchivo(path, archivo, doc.getId(),ciPersonal);
            }
            else{
                String archivoViejo = ciPersonal+"-"+doc.getId()+doc.getNombre().substring(doc.getNombre().lastIndexOf("."));
                String archivoNuevo = ciPersonal+"-"+doc.getId()+name.substring(name.lastIndexOf("."));
                if(archivoNuevo.equals(archivoViejo)){
                    b=this.subirArchivo(path, archivo, doc.getId(),ciPersonal);
                }
                else{
                    b=this.eliminarArchivo(path, archivoViejo, ciPersonal,doc.getId());
                    b= b && this.subirArchivo(path, archivo, doc.getId(),ciPersonal);
                }
            }
        }
        return b;
    }*/
}
