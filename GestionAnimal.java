import java.io.Serializable;
import java.util.HashSet;

/** Metodos para gestionar a los animales en la Veterinaria */
public interface GestionAnimal extends Serializable
{
    boolean registrarAnimal(Animal animal) throws Exception;       
    HashSet<Animal> listarAnimales();     
}
