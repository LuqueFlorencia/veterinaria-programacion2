import java.io.Serializable;
import java.util.Comparator;

/** Clase Padre para los tipos de personas */
public abstract class Persona implements GestionPersona, Comparable<Persona>, Comparator<Persona>
{
    protected String apellido;
    protected String nombre;
    protected Genero genero;
    protected int edad;
    
    //Getters y Setters   
    public String getApellido (){
        return this.apellido;
    }
    
    public void setApellido (String apellido){
        this.apellido = apellido;
    }
    
    public String getNombre (){
        return this.nombre;
    }
    
    public void setNombre (String nombre){
        this.nombre = nombre;
    }
    
    public Genero getGenero (){
        return this.genero;
    }
    
    public void setGenero (Genero genero){
        this.genero = genero;
    }
    
    public int getEdad (){
        return this.edad;
    }
    
    public void setEdad (int edad){
        this.edad = edad;
    }
    
    //Constructor vacio y sin parametros
    public Persona() { 
    }
    
    //Constructor custom
    public Persona (String apellido, String nombre, Genero genero, int edad) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;
    }
    
    //Metodos custom
    public String formatoArchivo() {
        StringBuilder respuesta = new StringBuilder();
        
        respuesta.append(apellido);
        respuesta.append(",").append(nombre);
        respuesta.append(",").append(genero);
        respuesta.append(",").append(edad);
        respuesta.append("\n");
    
        return respuesta.toString();
    }
    
    public int compareTo(Persona persona) {      
        int resultado = this.apellido.compareTo(persona.getApellido());
        if (resultado != 0) {
            return resultado;
        }
        
        resultado = this.nombre.compareTo(persona.getNombre());
        if (resultado != 0) {
            return resultado;
        }
    
        return this.genero.compareTo(persona.getGenero());
    }
    
    public int compare(Persona p1, Persona p2) {
        int comparacionEdad = Integer.compare(p1.edad, p2.edad);
        if (comparacionEdad != 0) {
            return comparacionEdad;
        }
        return p1.apellido.compareTo(p2.apellido);
    }
}