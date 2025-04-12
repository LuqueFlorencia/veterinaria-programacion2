import java.io.Serializable;
import java.util.HashSet;
import java.util.Comparator;

/** Clase Perro que hereda de Animal */
public class PerroBean extends Animal implements Serializable, Comparable<Animal>, Comparator<Animal>
{
    private RazaPerro raza;
    private boolean castrado;
    private static HashSet<PerroBean> perros = new HashSet<>();
    
    //Getters y Setters 
    public RazaPerro getRaza (){
        return this.raza;
    }
    
    public void setRaza (RazaPerro raza){
        this.raza = raza;
    }
    
    public boolean isCastrado (){
        return this.castrado;
    }
    
    public void setCastrado (boolean castrado){
        this.castrado = castrado;
    }
    
    public static HashSet<PerroBean> getPerros() {
        return perros;
    }

    public static void setPerros(HashSet<PerroBean> nuevosPerros) {
        perros = nuevosPerros;
    }
    
    //Constructor vacio y sin parametros
    public PerroBean(){ 
    }
    
    //Perro callejero
    public PerroBean (String nombre, RazaPerro raza, Genero genero, double peso){
        super(nombre, genero, 0, peso, 0);  //Edad e id cliente por default
        this.raza = raza;
        this.castrado = false;              //Valor por defecto     
    }
    
    //Perro con dueño (Mascota)
    public PerroBean (String nombre, RazaPerro raza, Genero genero, int edad, double peso, boolean castrado, int idCliente){
        super(nombre, genero, edad, peso, idCliente);
        this.raza = raza;
        this.castrado = castrado;
    }
    
    //Metodos custom    
    public boolean registrarAnimal(Animal animal) throws Exception {
        boolean resultado = false;
        
        if (animal instanceof PerroBean) {
            PerroBean p = (PerroBean)animal; 
            try {
                for(PerroBean perro : perros){
                    if (p.equals(perro)){
                        throw new Exception("El perro ya se encuentra registrado."); 
                    }
                }
                perros.add(p); 
                resultado = true;
            } catch (Exception ex) { 
                System.out.println(ex.getMessage()); 
            }
        }
        
        return resultado;
    }
    
    public HashSet<Animal> listarAnimales() {
        return new HashSet<>(perros);
    }
    
    public String formatoArchivo() {
        StringBuilder respuesta = new StringBuilder();
        
        respuesta.append(nombre);
        respuesta.append(",").append(raza);
        respuesta.append(",").append(genero);
        respuesta.append(",").append(edad);
        respuesta.append(",").append(peso);
        respuesta.append(",").append(isCastrado());
        respuesta.append(",").append(idCliente);
    
        return respuesta.toString();
    }
    
    public String toString(){
        StringBuilder respuesta = new StringBuilder("PERRO:");
        
        respuesta.append("\n Nombre: ").append(nombre);
        respuesta.append("\n Raza: ").append(raza);
        respuesta.append("\n Genero: ").append(genero);
        if (edad != 0) { respuesta.append("\n Edad: ").append(edad); }
        if (peso != 0.0) { respuesta.append("\n Peso: ").append(peso).append(" kg"); }
        if (isCastrado()){
            respuesta.append("\n Castrado: Si.");
        } else {
            respuesta.append("\n Castrado: No.");
        }
        if (idCliente != 0) { respuesta.append("\n Pertenece al cliente ID: ").append(idCliente + "\n "); }
        
        return respuesta.toString();
    }
    
    public boolean equals(Object objeto) {
        PerroBean p = (PerroBean)objeto;
        if (nombre.equals(p.getNombre()) && (raza.equals(p.getRaza()) && genero.equals(p.getGenero()) && idCliente == p.getIdCliente())){
            return true;
        }
        return false;
    }
    
    public int hashCode() {
        int result = 17;  // Valor inicial
        result = 31 * result + (nombre.hashCode());
        result = 31 * result + (raza.hashCode());
        result = 31 * result + (genero.hashCode());
        result = 31 * result + idCliente;
        return result;
    }
    
    public int compareTo(Animal animal) {
        if (animal instanceof PerroBean) {
            PerroBean otroPerro = (PerroBean) animal;
            int comparacionRaza = this.raza.compareTo(otroPerro.raza);
            if (comparacionRaza != 0) {
                return comparacionRaza;
            }
        }
        return super.compareTo(animal);
    }
}