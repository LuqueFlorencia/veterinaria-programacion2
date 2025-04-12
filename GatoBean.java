import java.io.Serializable;
import java.util.HashSet;
import java.util.Comparator;

/** Clase Gato que hereda de Animal */
public class GatoBean extends Animal implements Serializable, Comparable<Animal>, Comparator<Animal>
{
    private RazaGato raza;
    private boolean castrado;
    private static HashSet<GatoBean> gatos = new HashSet<>();
    
    //Getters y Setters
    public RazaGato getRaza (){
        return this.raza;
    }
    
    public void setRaza (RazaGato raza){
        this.raza = raza;
    }
    
    public boolean isCastrado (){
        return this.castrado;
    }
    
    public void setCastrado (boolean castrado){
        this.castrado = castrado;
    }
    
    public static HashSet<GatoBean> getGatos() {
        return gatos;
    }

    public static void setGatos(HashSet<GatoBean> nuevosGatos) {
        gatos = nuevosGatos;
    }
    
    //Constructor vacio y sin parametros
    public GatoBean(){ 
    }
    
    //Gato callejero
    public GatoBean (String nombre, RazaGato raza, Genero genero, double peso){
        super(nombre, genero, 0, peso, 0);      //Edad e id cliente por default
        this.raza = raza;
        this.castrado = false;                  //Valor por defecto
    }
    
    //Gato con Dueño (Mascota)
    public GatoBean (String nombre, RazaGato raza, Genero genero, int edad, double peso, boolean castrado, int idCliente){
        super(nombre, genero, edad, peso, idCliente);
        this.raza = raza;
        this.castrado = castrado;
    }
    
    //Metodos custom    
    public boolean registrarAnimal(Animal animal) throws Exception  {
        boolean resultado = false;
        
        if (animal instanceof GatoBean) {
            GatoBean g = (GatoBean)animal; 
            try {
                for(GatoBean gato : gatos){
                    if (g.equals(gato)){
                        throw new Exception("El gato ya se encuentra registrado."); 
                    }
                }
                gatos.add(g); 
                resultado = true;
            } catch (Exception ex) { 
                System.out.println(ex.getMessage()); 
            }
        }
        
        return resultado;
    }

    public HashSet<Animal> listarAnimales() {
        return new HashSet<>(gatos);
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
        StringBuilder respuesta = new StringBuilder("GATO:");
        
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
        GatoBean g = (GatoBean)objeto;
        if (nombre.equals(g.getNombre()) && (raza.equals(g.getRaza()) && genero.equals(g.getGenero()) && idCliente == getIdCliente())){
            return true;
        }
        return false;
    }
    
    public int hashCode() {
        int result = 17;  // Valor inicial
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (raza != null ? raza.hashCode() : 0);
        result = 31 * result + (genero != null ? genero.hashCode() : 0);
        result = 31 * result + idCliente;
        return result;
    }
    
    public int compareTo(Animal animal) {
        if (animal instanceof GatoBean) {
            GatoBean otroGato = (GatoBean) animal;
            int comparacionRaza = this.raza.compareTo(otroGato.raza);
            if (comparacionRaza != 0) {
                return comparacionRaza;
            }
        }
        return super.compareTo(animal);
    }
}