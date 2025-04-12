import java.io.Serializable;
import java.util.HashSet;
import java.util.Comparator;

/** Clase Medicos Veterinarios de la Clinica Veterinaria */
public class VeterinarioBean extends Persona implements Serializable, Comparable<Persona>, Comparator<Persona>
{
    private int matricula;
    private int horasTurno;
    private static HashSet<VeterinarioBean> veterinarios = new HashSet<>();
    
    //Getters y Setters   
    public int getMatricula (){
        return this.matricula;
    }
    
    public void setMatricula (int matricula){
        this.matricula = matricula;
    }
    
    public int getHorasTurno (){
        return this.horasTurno;
    }
    
    public void setHorasTurno (int horasTurno){
        this.horasTurno = horasTurno;
    }
    
    public static HashSet<VeterinarioBean> getVeterinarios() {
        return veterinarios;
    }

    public static void setVeterinarios(HashSet<VeterinarioBean> nuevosVeterinarios) {
        veterinarios = nuevosVeterinarios;
    }
    
    //Constructor vacio y sin parametros
    public VeterinarioBean (){ 
    }
    
    //Veterinario nuevo
    public VeterinarioBean (int matricula, String apellido, String nombre, Genero genero, int edad, int horasTurno){    
        super(apellido, nombre, genero, edad);
        this.matricula = matricula;
        this.horasTurno = horasTurno;
    }
    
    //Metodos custom    
    public boolean registrarPersona(Persona persona) throws Exception {
        boolean resultado = false;
        
        if (persona instanceof VeterinarioBean) {
            VeterinarioBean v = (VeterinarioBean)persona; 
            try {
                for(VeterinarioBean vet : veterinarios){
                    if (v.equals(vet)){
                        throw new Exception("El veterinario con la matricula " + v.getMatricula() + " ya se encuentra registrado."); 
                    }
                }
                veterinarios.add(v); 
                resultado = true;
            } catch (Exception ex) { 
                System.out.println(ex.getMessage()); 
            }
        }
        
        return resultado;
    }

    public HashSet<Persona> listarPersonas() {
        return new HashSet<>(veterinarios);
    }
    
    public String formatoArchivo() {
        StringBuilder respuesta = new StringBuilder();
        
        respuesta.append(matricula);
        respuesta.append(",").append(apellido);
        respuesta.append(",").append(nombre);
        respuesta.append(",").append(genero);
        respuesta.append(",").append(edad);
        respuesta.append(",").append(horasTurno);
    
        return respuesta.toString();
    }
    
    public String toString(){
        StringBuilder respuesta = new StringBuilder("VETERINARIO:");
        
        if (matricula != 0) { respuesta.append("\n Matricula: ").append(matricula + "\n "); }
        if (!apellido.equals(null)) { respuesta.append("\n Apellido: ").append(apellido); }
        if (!nombre.equals(null)) { respuesta.append("\n Nombre: ").append(nombre); }
        respuesta.append("\n Genero: ").append(genero);
        if (edad != 0) { respuesta.append("\n Edad: ").append(edad); }
        respuesta.append("\n Horas de turno: ").append(horasTurno + "\n ");
        
        return respuesta.toString();
    }
    
    public boolean equals(Object objeto) {
        VeterinarioBean v = (VeterinarioBean)objeto;
        if (matricula == v.getMatricula()){
            return true;
        }
        return false;
    }
    
    public int hashCode() {
        int result = 17;  // Valor inicial
        result = 31 * result + matricula;
        return result;
    }
    
    public int compareTo(Persona persona) {
        VeterinarioBean vet = (VeterinarioBean)persona;
        
        int resultado = this.apellido.compareTo(vet.getApellido());
        if (resultado != 0) {
            return resultado;
        }
        
        return resultado = this.nombre.compareTo(vet.getNombre());
    }
}