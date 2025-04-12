import java.io.Serializable;
import java.util.HashSet;
import java.util.Comparator;

/** Clase Pajaro que hereda de Animal */
public class PajaroBean extends Animal implements Serializable, Comparable<Animal>, Comparator<Animal>
{
    private EspeciePajaro especie;
    private boolean alasCortadas;
    private static HashSet<PajaroBean> pajaros = new HashSet<>();
    
    //Getters y Setters
    public EspeciePajaro getEspecie (){
        return this.especie;
    }
    
    public void setEspecie (EspeciePajaro especie){
        this.especie = especie;
    }
    
    public boolean isAlasCortadas (){
        return this.alasCortadas;
    }
    
    public void setAlasCortadas (boolean alasCortadas){
        this.alasCortadas = alasCortadas;
    }
    
    public static HashSet<PajaroBean> getPajaros() {
        return pajaros;
    }

    public static void setPajaros(HashSet<PajaroBean> nuevosPajaros) {
        pajaros = nuevosPajaros;
    }
    
    //Constructor vacio y sin parametros
    public PajaroBean(){ 
    }
    
    //Pajaro callejero
    public PajaroBean (String nombre, EspeciePajaro especie, Genero genero, double peso){
        super(nombre, genero, 0, peso, 0);      //Edad e id cliente por default
        this.especie = especie;
        this.alasCortadas = false;              //Valor por defecto
    }
    
    //Pajaro con Dueño (Mascota)
    public PajaroBean (String nombre, EspeciePajaro especie, Genero genero, int edad, double peso, boolean alasCortadas, int idCliente){
        super(nombre, genero, edad, peso, idCliente);
        this.especie = especie;
        this.alasCortadas = alasCortadas;
    }
    
    //Metodos custom   
    public boolean registrarAnimal(Animal animal) throws Exception {
        boolean resultado = false;
        
        if (animal instanceof PajaroBean) {
            PajaroBean p = (PajaroBean)animal; 
            try {
                for(PajaroBean pajaro : pajaros){
                    if (p.equals(pajaro)){
                        throw new Exception("El pajaro ya se encuentra registrado."); 
                    }
                }
                pajaros.add(p); 
                resultado = true;
            } catch (Exception ex) { 
                System.out.println(ex.getMessage()); 
            }
        }
        
        return resultado;
    }

    public HashSet<Animal> listarAnimales() {
        return new HashSet<>(pajaros);
    }  
    
    public String formatoArchivo() {
        StringBuilder respuesta = new StringBuilder();
        
        respuesta.append(nombre);
        respuesta.append(",").append(especie);
        respuesta.append(",").append(genero);
        respuesta.append(",").append(edad);
        respuesta.append(",").append(peso);
        respuesta.append(",").append(isAlasCortadas());
        respuesta.append(",").append(idCliente);
    
        return respuesta.toString();
    }
    
    public String toString(){
        StringBuilder respuesta = new StringBuilder("PAJARO:");
        
        respuesta.append("\n Nombre: ").append(nombre);
        respuesta.append("\n Especie: ").append(especie);
        respuesta.append("\n Genero: ").append(genero);
        if (edad != 0) { respuesta.append("\n Edad: ").append(edad); }
        if (peso != 0.0) { respuesta.append("\n Peso: ").append(peso).append(" kg"); }
        if (isAlasCortadas()){
            respuesta.append("\n Tiene las alas cortadas: Si.");
        } else {
            respuesta.append("\n Tiene las alas cortadas: No.");
        }
        if (idCliente != 0) { respuesta.append("\n Pertenece al cliente ID: ").append(idCliente + "\n "); }
        
        return respuesta.toString();
    }
    
    public boolean equals(Object objeto) {
        PajaroBean p = (PajaroBean)objeto;
        if (nombre.equals(p.getNombre()) && (especie.equals(p.getEspecie()) && genero.equals(p.getGenero()) && idCliente == getIdCliente())){
            return true;
        }
        return false;
    }
    
    public int hashCode() {
        int result = 17;  // Valor inicial
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (especie != null ? especie.hashCode() : 0);
        result = 31 * result + (genero != null ? genero.hashCode() : 0);
        result = 31 * result + idCliente;
        return result;
    }
    
    public int compareTo(Animal animal) {
        if (animal instanceof PajaroBean) {
            PajaroBean otroPajaro = (PajaroBean) animal;
            int comparacionEspecie = this.especie.compareTo(otroPajaro.especie);
            if (comparacionEspecie != 0) {
                return comparacionEspecie;
            }
        }
        return super.compareTo(animal);
    }
}