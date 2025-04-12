import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Comparator;

/** Clase Cliente de la Veterinaria */
public class ClienteBean extends Persona implements Serializable, Comparable<Persona>, Comparator<Persona>
{
    private int nroCliente;
    private LocalDate fechaAfiliacion;
    private static HashSet<ClienteBean> clientes = new HashSet<>();
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    //Getters y Setters   
    public int getNroCliente (){
        return this.nroCliente;
    }
    
    public void setNroCliente (int nroCliente){
        this.nroCliente = nroCliente;
    }
    
    public LocalDate getFechaAfiliacion (){
        return this.fechaAfiliacion;
    }
    
    public void setFechaAfiliacion(String fechaAfiliacion) {
        try {
            this.fechaAfiliacion = LocalDate.parse(fechaAfiliacion, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            System.err.println("Formato de fecha inválido: " + fechaAfiliacion);
            this.fechaAfiliacion = null;
        }
    }
    
    public static HashSet<ClienteBean> getClientes() {
        return clientes;
    }

    public static void setClientes(HashSet<ClienteBean> nuevosClientes) {
        clientes = nuevosClientes;
    }
    
    //Constructor vacio y sin parametros
    public ClienteBean (){ 
    }
    
    //Cliente nuevo
    public ClienteBean (int nroCliente, String apellido, String nombre, Genero genero, int edad, String fechaAfiliacion){    
        super(apellido, nombre, genero, edad);
        this.nroCliente = nroCliente;
        try {
            this.fechaAfiliacion = LocalDate.parse(fechaAfiliacion, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            System.err.println("Formato de fecha inválido: " + fechaAfiliacion);
            this.fechaAfiliacion = null;
        }
    }
    
    //Metodos custom
    public boolean registrarPersona(Persona persona) throws Exception {
        boolean resultado = false;
        
        if (persona instanceof ClienteBean) {
            ClienteBean c = (ClienteBean)persona; 
            try {
                for(ClienteBean cliente : clientes){
                    if (c.equals(cliente)){
                        throw new Exception("El cliente con el id " + c.getNroCliente() + " ya se encuentra registrado."); 
                    }
                }
                clientes.add(c); 
                resultado = true;
            } catch (Exception ex) { 
                System.out.println(ex.getMessage()); 
            }
        }
        
        return resultado;
    }

    public HashSet<Persona> listarPersonas() {
        return new HashSet<>(clientes);
    }
    
    public String formatoArchivo() {
        StringBuilder respuesta = new StringBuilder();
        
        respuesta.append(nroCliente);
        respuesta.append(",").append(apellido);
        respuesta.append(",").append(nombre);
        respuesta.append(",").append(genero);
        respuesta.append(",").append(edad);
        respuesta.append(",").append(fechaAfiliacion);
    
        return respuesta.toString();
    }
    
    public String toString(){
        StringBuilder respuesta = new StringBuilder("CLIENTE:");
        
        if (nroCliente != 0) { respuesta.append("\n Numero de cliente: ").append(nroCliente + "\n "); }
        if (!apellido.equals(null)) { respuesta.append("\n Apellido: ").append(apellido); }
        if (!nombre.equals(null)) { respuesta.append("\n Nombre: ").append(nombre); }
        respuesta.append("\n Genero: ").append(genero);
        if (edad != 0) { respuesta.append("\n Edad: ").append(edad); }
        respuesta.append("\n Fecha de afiliacion: ").append(fechaAfiliacion + "\n ");
        
        return respuesta.toString();
    }
    
    public boolean equals(Object objeto) {
        ClienteBean c = (ClienteBean)objeto;
        if (nroCliente == c.getNroCliente()){
            return true;
        }
        return false;
    }
    
    public int hashCode() {
        int result = 17;  // Valor inicial
        result = 31 * result + nroCliente;
        return result;
    }
    
    public int compareTo(Persona persona) {
        ClienteBean cliente = (ClienteBean)persona;
        
        int resultado = this.apellido.compareTo(cliente.getApellido());
        if (resultado != 0) {
            return resultado;
        }
        
        resultado = this.nombre.compareTo(cliente.getNombre());
        if (resultado != 0) {
            return resultado;
        } 
        
        return resultado = this.fechaAfiliacion.compareTo(cliente.getFechaAfiliacion());
    }
}