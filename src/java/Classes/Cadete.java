/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Gisel
 */
public class Cadete extends Personal{
    private String foto;
    private Curso curso;
    private Carrera carrera;
    private String fechaNac;
    private String sexo;
    private Departamento departamentoNac;
    private String localidadNac;
    private String cc;
    private int ccNro;
    private EstadoCivil estadoCivil;
    private String domicilio;
    private Departamento departamento;
    private String localidad;
    private String telefono;
    private String email;
    private int derecha;
    private int hijos;
    private boolean repitiente;
    private boolean lmga;
    private boolean paseDirecto;
    private double notaPaseDirecto;
    private String talleOperacional;
    private int talleBotas;
    private int talleQuepi;
    private String observaciones;
    private Familiar madre;
    private Familiar padre;
    private final LinkedList<HistorialBajaCadete> historialBaja; 

    public Cadete(String foto,Curso curso, Carrera carrera, String fechaNac, String sexo, Departamento departamentoNac, String localidadNac, String cc, int ccNro, EstadoCivil estadoCivil, String domicilio, Departamento departamento, String localidad, String telefono, String email, int derecha, int hijos, boolean repitiente, boolean lmga, boolean paseDirecto, double notaPaseDirecto, Familiar madre,Familiar padre, int nroInterno, int ci, Grado grado, Arma arma, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, HashMap<Integer,Documento> documentos, String observaciones,String fechaAltaSistema,String talleOperacional,int talleBotas,int talleQuepi,LinkedList<HistorialBajaCadete> historialBaja) {
        super(nroInterno, ci, grado, arma, primerNombre, segundoNombre, primerApellido, segundoApellido, documentos, fechaAltaSistema);
        this.curso = curso;
        this.carrera = carrera;
        this.fechaNac = fechaNac;
        this.sexo = sexo;
        this.departamentoNac = departamentoNac;
        this.localidadNac = localidadNac;
        this.cc = cc;
        this.ccNro = ccNro;
        this.estadoCivil = estadoCivil;
        this.domicilio = domicilio;
        this.departamento = departamento;
        this.localidad = localidad;
        this.telefono = telefono;
        this.email = email;
        this.derecha = derecha;
        this.hijos = hijos;
        this.repitiente = repitiente;
        this.lmga = lmga;
        this.paseDirecto = paseDirecto;
        this.notaPaseDirecto = notaPaseDirecto;
        this.madre = madre;
        this.padre = padre;
        this.foto=foto;
        this.talleBotas=talleBotas;
        this.talleOperacional=talleOperacional;
        this.talleQuepi=talleQuepi;
        this.historialBaja = historialBaja;
    }
 public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
  public String getObservaciones() {
        return observaciones;
    }
    public String getTalleOperacional() {
        return talleOperacional;
    }

    public void setTalleOperacional(String talleOperacional) {
        this.talleOperacional = talleOperacional;
    }

    public int getTalleBotas() {
        return talleBotas;
    }

    public void setTalleBotas(int talleBotas) {
        this.talleBotas = talleBotas;
    }

    public int getTalleQuepi() {
        return talleQuepi;
    }

    public void setTalleQuepi(int talleQuepi) {
        this.talleQuepi = talleQuepi;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    
    public Familiar getMadre() {
        return madre;
    }

    public Familiar getPadre() {
        return padre;
    }

    public LinkedList<HistorialBajaCadete> getHistorialBaja() {
        return historialBaja;
    }

    

    

    public Curso getCurso() {
        return curso;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public String getSexo() {
        return sexo;
    }

    public Departamento getDepartamentoNac() {
        return departamentoNac;
    }

    public String getLocalidadNac() {
        return localidadNac;
    }

    public String getCc() {
        return cc;
    }

    public int getCcNro() {
        return ccNro;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public String getLocalidad() {
        return localidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public int getDerecha() {
        return derecha;
    }

    public int getHijos() {
        return hijos;
    }

    public boolean isRepitiente() {
        return repitiente;
    }

    public boolean isLmga() {
        return lmga;
    }

    public boolean isPaseDirecto() {
        return paseDirecto;
    }

    public double getNotaPaseDirecto() {
        return notaPaseDirecto;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setDepartamentoNac(Departamento departamentoNac) {
        this.departamentoNac = departamentoNac;
    }

    public void setLocalidadNac(String localidadNac) {
        this.localidadNac = localidadNac;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public void setCcNro(int ccNro) {
        this.ccNro = ccNro;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDerecha(int derecha) {
        this.derecha = derecha;
    }

    public void setHijos(int hijos) {
        this.hijos = hijos;
    }

    public void setRepitiente(boolean repitiente) {
        this.repitiente = repitiente;
    }

    public void setLmga(boolean lmga) {
        this.lmga = lmga;
    }

    public void setPaseDirecto(boolean paseDirecto) {
        this.paseDirecto = paseDirecto;
    }

    public void setNotaPaseDirecto(double notaPaseDirecto) {
        this.notaPaseDirecto = notaPaseDirecto;
    }

    public void setMadre(Familiar madre) {
        this.madre = madre;
    }

    public void setPadre(Familiar padre) {
        this.padre = padre;
    }

    

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }
    
    public void imprimirFicha(PrintWriter out,String Context){
        String impr="<table class=\"resultsTable\" style=\"margin-left: 0%; margin-right: 0%;word-wrap: break-word\">\n" +
"            <tr>\n" +
"                <td>\n" +
"                    <p align=\"left\">Escuela Militar</p>\n" +
"                </td>\n" +
"                <td>\n" +
"                     <p align=\"right\">Jefatura de Estudios</p>\n" +
"                </td>\n" +
"            </tr>\n" +
"            <tr>\n" +
"                <td colspan=\"2\">\n";
       
                if(foto!=""){
                    impr+="<p align=\"center\"><img src=\""+Context+"/Imagenes?foto="+super.getCi()+"\" style=\"width: 20%\" /></p>";
                    
                }
                else{
                   
                     impr+="<p align=\"center\"><img src=\"images/silueta.jpg\" id=\"uploadPreview\" style=\"width: 20%\" /></p>";
                            
                }

                    impr+="</td>\n" +
"            </tr>\n" +
"            <tr>\n" +
"                <td width=\"40%\" style=\"vertical-align: top;border: solid\">\n" +
"                    <table  style=\"table-layout: fixed; width: 100%\">\n" +
"                        <tr>\n" +
"                            <td>\n" +
"                            <h4 style=\"padding: 0px\">Datos Personales:</h4>\n" +
"                            </td>\n" +
"                        </tr>\n" +
"                        <tr>\n" +
"                            <td>N&uacute;mero: </td>\n" +
"                            <td>"+ super.getNroInterno() +"\n" +
"                                "+ carrera.getDescripcion() +"\n" +
"                            </td>\n" +
                            
"                        <tr>\n" +
"                            <td>Arma: </td>\n" +
"                            <td>"+ super.getArma().getDescripcion()+"</td>\n" +
"                        </tr>\n" +
                            
"                        <tr>\n" +
"                            <td>Curso: </td>\n" +
"                            <td>"+ curso.getDescripcion()+"</td>\n" +
"                        </tr>\n" +
                            
"                        <tr>\n" +
"                            <td>Grado: </td>\n" +
"                            <td>"+ super.getGrado().getDescripcion()+"</td>\n" +
"                        </tr>\n" +
"                        <tr>\n" +
"                            <td>Primer nombre: </td>\n" +
"                            <td>"+ super.getPrimerNombre() +"</td>\n" +
"                        </tr>\n" +
"                        <tr>\n" +
"                            <td>Segundo nombre: </td>\n" +
"                            <td>"+ super.getSegundoNombre()+"</td>\n" +
"                        </tr>\n" +
"                        <tr>\n" +
"                            <td>Primer apellido: </td>\n" +
"                            <td>"+ super.getPrimerApellido()+"</td>\n" +
"                        </tr>\n" +
                            
"                        <tr>\n" +
"                            <td>Segundo apellido: </td>\n" +
"                            <td>"+ super.getSegundoApellido() +"</td>\n" +
"                        </tr>\n" +
"                        </tr>\n" +
                            "<tr>\n" +
"                            <td>Derecha: </td>\n" +
"                            <td>"+ derecha +
"                            </td>\n" +
"                        </tr>"+
"                        <tr>\n" +
"                            <td>C.I.: </td>\n" +
"                            <td>"+ super.getCi() +"</td>\n" +
"                        </tr>\n" +
"                        <tr>\n" +
"                            <td>Sexo: </td>\n" +
"                            <td>"+ sexo +"</td>\n" +
"                        </tr>\n" +

"                        <tr>\n" +
"                            <td>Fecha de Nacimiento: </td>\n" +
"                            <td>"+ fechaNac+"</td>\n" +
"                        </tr>\n" +
"                        <tr>\n" +
"                            <td>Departamento de Nacimiento: </td>\n" +
"                            <td>"+ departamentoNac.getDescripcion() +"</td>\n" +
"                        </tr>\n" +
"                        <tr>\n" +
"                            <td>Localidad de Nacimiento: </td>\n" +
"                            <td>"+ localidadNac +"</td>\n" +
"                        </tr>\n" +
"\n" +
"                        <tr>\n" +
"                            <td>Credencial: </td>\n" +
"                            <td>Serie: "+ cc +"\n" +
"                                N&deg;: "+ ccNro +"</td>\n" +
"                        </tr>\n" +
"                        <tr>\n" +
"                            <td>Estado Civil: </td>\n" +
"                            <td>"+ estadoCivil.getDescripcion() +"</td>\n" +
"                        </tr>\n" +
"                        <tr>\n" +
"                            <td>Departamento: </td>\n" +
"                            <td>"+ departamento.getDescripcion() +"</td>\n" +
"                        </tr>\n" +
"                        <tr>\n" +
"                            <td>Localidad: </td>\n" +
"                            <td>"+ localidad +"</td>\n" +
"                        </tr>\n" +
"                        <tr>\n" +
"                            <td>Tel&eacute;fono: </td>\n" +
"                            <td>"+ telefono +"</td>\n" +
"                        </tr>\n" +
"                        <tr>\n" +
"                            <td>Correo Electr&oacute;nico: </td>\n" +
"                            <td>"+ email +"</td>\n" +
"                        </tr>\n" ;
impr+="                        <tr>\n" +
"                            <td>LMGA: </td>\n" +
"                            <td>";
                                if(lmga){
                                    impr+="SI";
                                }
                                else{
                                    impr+="NO";
                                } 
                                impr+="</td>\n" +
"                        </tr>\n" ;
                        if(lmga){
    impr+="                 <tr>\n" +
    "                            <td>PASE DIRECTO: </td>\n" +
    "                            <td>";
                                    if(paseDirecto){
                                        impr+="SI";
                                    }
                                    else{
                                        impr+="NO";
                                    } 
                            impr+="</td>\n" +
    "                       </tr>\n" ;
                            if(paseDirecto){
        impr+="                 <tr>\n" +
        "                           <td>Nota Pase Directo: </td>\n" +
        "                           <td>"+ notaPaseDirecto +"</td>\n" +
        "                       </tr>\n" ;
                            }
                        };
                        
impr+="                 <tr>\n" +
"                            <td>Cantidad de hijos: </td>\n" +
"                            <td>"; if(hijos==4){
                                        impr+="+ de 3";
                                    }
                                    else{
                                        impr+= hijos;
                                    }
impr+="                      </td>\n" +
"                       </tr>\n" +
"                       <tr>\n" +
"                            <td>REPITIENTE: </td>\n" +
"                            <td>";
                                if(repitiente){
                                    impr+="SI";
                                }
                                else{
                                    impr+="NO";
                                } 
                        impr+="</td>\n" +
"                       </tr>\n" +
                                
"                        <tr>\n" +
"                            <td>Talle operacional: </td>\n" +
"                            <td>"+ talleOperacional +"</td>\n" +
"                        </tr>\n" +
                                
"                        <tr>\n" +
"                            <td>Talle Quep&iacute: </td>\n" +
"                            <td>"+ talleQuepi +"</td>\n" +
"                        </tr>\n" +
                                
"                        <tr>\n" +
"                            <td>Talle botas: </td>\n" +
"                            <td>"+ talleBotas +"</td>\n" +
"                        </tr>\n" +
"                </table>\n" +
"                </td>\n" +
"                <td width=\"40%\" style=\"vertical-align: top;border: solid\" >\n" +
"                    <table  style=\"width: 100%; table-layout: fixed\">\n" +
"                        <tr>\n" +
"                            <td>\n" +
"                                <h4 style=\"padding: 0px\">Datos Patron&iacute;micos:</h4>\n" +
"                            </td>\n" +
"                        </tr>\n" +
"                        <tr>\n" +
"                            <td>\n" +
"                                <b>Padre:</b>\n" +
"                            </td>\n" +
"                        </tr>\n" ;
                        for(int i=1;i<=2;i++){
                           Familiar f;
                           if(i==1){
                               f=padre;
impr+="                        <tr>\n" +
"                            <td>\n" +
"                                <b>Padre:</b>\n" +
"                            </td>\n" +
"                        </tr>\n" ;
                           }
                           else{
                               f=madre;
impr+="                        <tr>\n" +
"                            <td>\n" +
"                                <b>Madre:</b>\n" +
"                            </td>\n" +
"                        </tr>\n" ;
                           }
impr+="                            <td>Nombre Completo: </td>\n" +
"                            <td style=\"size: 50\">"+ f.getNombreComp() +"</td>\n" +
"                        </tr>\n" +
"                        <tr>\n" +
"                            <td>Fecha de Nacimiento: </td>\n" +
"                            <td>"+ f.getFechaNac()+"</td>\n" +
"                        </tr>\n" +
"                        <tr>\n" +
"                            <td>Departamento de Nacimiento: </td>\n" +
"                            <td>"+ f.getFechaNac() +"</td>\n" +
"                        </tr>\n" +
"                        <tr>\n" +
"                            <td>Localidad de Nacimiento: </td>\n" +
"                            <td>"+ f.getLocalidadNac() +"</td>\n" +
"                        </tr>\n" +
"                        <tr>\n" +
"                            <td>Estado Civil: </td>\n" +
"                            <td>"+ f.getEstadoCivil().getDescripcion() +"</td>\n" +
"                        </tr>\n" +
"                        <tr>\n" +
"                            <td>Domicilio: </td>\n" +
"                            <td>"+ f.getDomicilio() +"</td>\n" +
"                        </tr>\n" +
"                        <tr>\n" +
"                            <td>Departamento: </td>\n" +
"                            <td>"+ f.getDepartamento().getDescripcion() +"</td>\n" +
"                        </tr>\n" +
"\n" +
"                        <tr>\n" +
"                            <td>Localidad: </td>\n" +
"                            <td>"+ f.getLocalidad() +"</td>\n" +
"                        </tr>\n" +
"                        <tr>\n" +
"                            <td>Tel&eacute;fono: </td>\n" +
"                            <td>"+ f.getTelefono() +"</td>\n" +
"                        </tr>\n" +
"                        <tr>\n" +
"                            <td>Profesi&oacute;n: </td>\n" +
"                            <td>"+ f.getProfesion() +"</td>\n" +
"                        </tr>\n" +
"                        <tr>\n" +
"                            <td>Lugar de trabajo: </td>\n" +
"                            <td>"+ f.getLugarTrabajo() +"</td>\n" +
"                        </tr>\n" ;
                        }
impr+="                    </table>\n" +
"                        \n" +
"                </td>\n" +
"            </tr>\n" +
"            <tr> \n" +
"                <td colspan=\"2\">\n" +
"                    <table>\n" +
"                        <tr>\n" +
"                            <td>\n" +
"                                <b>Observaciones:</b>\n" +
"                            </td>\n" +
"                            <td>\n" +
"                                <textarea readonly rows=" ;
boolean sigo=true;
int ultIndex=0;
int cant=1;
while(sigo){
    ultIndex=observaciones.indexOf("\n", ultIndex);
    if(ultIndex!=-1){
        cant++;
        ultIndex++;
    }
    else{
        sigo=false;
    }
}
        impr+=cant+" style=\"resize: none\" cols=\"80\"  name=\"observaciones\">"+ observaciones +"</textarea>\n" +
"                            </td>\n" +
"                        </tr>\n" +
"                        \n" +
"                    </table>\n" +
"                    \n" +
"                </td>\n" +
"            </tr>\n" +
"        </table> <h1 style='page-break-after:always' > </h1>";
        if(historialBaja.isEmpty()){
 impr+="               <tr>"
         + "<td colspan=\"2\">\n" +
"                        <table>\n" +
"                            <tr>\n" +
"                            <h4>Historial de Baja</h4>\n" +
"                            </tr>\n" +
"\n" ;
                                for(HistorialBajaCadete hb:historialBaja){
impr+="                                    out.print(\"<tr>\"\n" +
"                                                + \"<td><b>Fecha Baja:</b> "+hb.getFechaBaja()+"</td>\"\n" +
"                                                + \"<td><b>Causa Baja:</b> "+hb.getCausa()+"</td>\"\n" +
"                                            + \"</tr>\");\n" ;
                                };
impr+="                        </table>\n" +
"                    </td>\n" +
"                </tr>";
                    
            }
       out.println(impr);
    }
    
}
