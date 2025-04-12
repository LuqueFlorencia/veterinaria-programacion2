import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Comparator;

/** Clase Turno que almacena los datos de los turnos de la veterinaria. */
public class TurnoBean implements Serializable, Comparable<TurnoBean>, Comparator<TurnoBean>
{
    private String nombreAnimal;
    private int nroCliente;
    private int matriculaVeterinario;
    private PracticaMedica practica;
    private LocalDate fechaTurno;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static HashSet<TurnoBean> turnos = new HashSet<>();
    
    //Getters y Setters   
    public String getNombreAnimal(){
        return this.nombreAnimal;
    }
    
    public void setNombreAnimal(String nombreAnimal){
        this.nombreAnimal = nombreAnimal;
    }
    
    public int getNroCliente(){
        return this.nroCliente;
    }
    
    public void setNroCliente(int nroCliente){
        this.nroCliente = nroCliente;
    }
    
    public int getMatriculaVeterinario(){
        return this.matriculaVeterinario;
    }
    
    public void setMatriculaVeterinario(int matriculaVeterinario){
        this.matriculaVeterinario = matriculaVeterinario;
    }

    public PracticaMedica getPractica (){
        return this.practica;
    }
    
    public void setPractica (PracticaMedica practica){
        this.practica = practica;
    }
    
    public LocalDate getFechaTurno (){
        return this.fechaTurno;
    }
    
    public void setFechaTurno(String fechaTurno) {
        try {
            this.fechaTurno = LocalDate.parse(fechaTurno, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            System.err.println("Formato de fecha inválido: " + fechaTurno);
            this.fechaTurno = null;
        }
    }
    
    public static HashSet<TurnoBean> getTurnos() {
        return turnos;
    }

    public static void setTurnos(HashSet<TurnoBean> nuevosTurnos) {
        turnos = nuevosTurnos;
    }
    
    //Constructor vacio y sin parametros
    public TurnoBean(){
    }
    
    //Constructor custom
    public TurnoBean(String nombreAnimal, int nroCliente, int matriculaVeterinario, PracticaMedica practica, String fechaTurno){
        this.nombreAnimal = nombreAnimal;
        this.nroCliente = nroCliente;
        this.matriculaVeterinario = matriculaVeterinario;
        this.practica = practica;
        try {
            this.fechaTurno = LocalDate.parse(fechaTurno, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            System.err.println("Formato de fecha inválido: " + fechaTurno);
            this.fechaTurno = null;
        }
    }
    
    //Metodos custom
    public boolean registrarTurno(TurnoBean turnoNuevo) throws Exception {
        boolean resultado = false;
        
        try {
            for(TurnoBean turno : turnos){
                if (turnoNuevo.equals(turno)){
                    throw new Exception("El turno ya se encuentra registrado."); 
                }
            }
            turnos.add(turnoNuevo);
            resultado = true;
        } catch (Exception ex) { 
            System.out.println(ex.getMessage()); 
        }
        
        return resultado;
    }
    
    public HashSet<TurnoBean> listarTurnos() {
        return new HashSet<>(turnos);
    }
    
    public String formatoArchivo() {
        StringBuilder respuesta = new StringBuilder();
        
        respuesta.append(nombreAnimal);
        respuesta.append(",").append(nroCliente);
        respuesta.append(",").append(matriculaVeterinario);
        respuesta.append(",").append(practica);
        respuesta.append(",").append(fechaTurno);
    
        return respuesta.toString();
    }
    
    public String toString(){
        StringBuilder respuesta = new StringBuilder("TURNO:");
        
        respuesta.append("\n Nombre Animal: ").append(nombreAnimal);
        respuesta.append("\n Nro Cliente/Dueño: ").append(nroCliente);
        respuesta.append("\n Veterinario: ").append(matriculaVeterinario);
        respuesta.append("\n Practica Medica: ").append(practica);
        respuesta.append("\n Fecha de afiliacion: ").append(fechaTurno + "\n ");
        
        return respuesta.toString();
    }
    
    public boolean equals(Object objeto) {
        TurnoBean t = (TurnoBean)objeto;
        if (nombreAnimal.equals(t.getNombreAnimal()) && nroCliente == t.getNroCliente() && 
            matriculaVeterinario == t.getMatriculaVeterinario() && practica.equals(t.getPractica()) && fechaTurno.equals(t.getFechaTurno())){
            return true;
        }
        return false;
    }
    
    public int hashCode() {
        int result = 17;  // Valor inicial
        result = 31 * result + (nombreAnimal.hashCode());
        result = 31 * result + nroCliente;
        result = 31 * result + matriculaVeterinario;
        result = 31 * result + (practica.hashCode());
        result = 31 * result + (fechaTurno.hashCode());
        return result;
    }
    
    public int compareTo(TurnoBean turno) {    
        int resultado = this.nombreAnimal.compareTo(turno.getNombreAnimal());
        if (resultado != 0) {
            return resultado;
        }
        
        resultado = this.fechaTurno.compareTo(turno.getFechaTurno());
        if (resultado != 0) {
            return resultado;
        } 
        
        return this.practica.compareTo(turno.getPractica());
    }
    
    public int compare(TurnoBean t1, TurnoBean t2) {
        int matriculaComparison = Integer.compare(t1.matriculaVeterinario, t2.matriculaVeterinario);
        if (matriculaComparison != 0) {
            return matriculaComparison;
        }
        return t1.fechaTurno.compareTo(t2.fechaTurno);
    }
}