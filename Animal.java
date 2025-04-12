import java.io.Serializable;
import java.util.Comparator;

/** Clase Padre para los tipos de animales que pueden ser atendidos en la veterinaria. */
public abstract class Animal implements GestionAnimal, Comparable<Animal>, Comparator<Animal>
{
    protected String nombre;
    protected Genero genero;
    protected int edad;
    protected double peso;
    protected int idCliente;
    
    //Getters y Setters 
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
    
    public double getPeso (){
        return this.peso;
    }
    
    public void setPeso (double peso){
        this.peso = peso;
    }
    
    public int getIdCliente (){
        return this.idCliente;
    }
    
    public void setIdCliente (int idCliente){
        this.idCliente = idCliente;
    }
    
    //Constructor vacio y sin parametros
    public Animal(){ 
    }
    
    //Constructor custom
    public Animal (String nombre, Genero genero, int edad, double peso, int idCliente) {
        this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;
        this.peso = peso;
        this.idCliente = idCliente;
    }
    
    //Metodos custom
    public String formatoArchivo() {
        StringBuilder respuesta = new StringBuilder();
    
        respuesta.append(nombre);
        respuesta.append(",").append(genero);
        respuesta.append(",").append(edad);
        respuesta.append(",").append(peso);
        respuesta.append(",").append(idCliente);
        respuesta.append("\n");
    
        return respuesta.toString();
    }
    
    public int compareTo(Animal animal) {
        int resultado = this.nombre.compareTo(animal.getNombre());
        if (resultado != 0) {
            return resultado;
        }
    
        resultado = this.genero.compareTo(animal.getGenero());
        if (resultado != 0) {
            return resultado;
        }
    
        return Integer.compare(this.idCliente, animal.getIdCliente());
    }
    
    public int compare(Animal a1, Animal a2) {
        int comparacionEdad = Integer.compare(a1.edad, a2.edad);
        if (comparacionEdad != 0) {
            return comparacionEdad;
        }
        return a1.nombre.compareTo(a2.nombre);
    }
}