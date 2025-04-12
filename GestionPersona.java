import java.io.Serializable;
import java.util.List;
import java.util.HashSet;

/** Metodos para gestionar a las personas en la Veterinaria */
public interface GestionPersona extends Serializable
{  
    boolean registrarPersona(Persona persona) throws Exception;
    HashSet<Persona> listarPersonas();   
}
