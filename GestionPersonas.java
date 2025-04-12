import javax.swing.*;
import java.util.HashSet;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

/** ABM para Personas */
public class GestionPersonas
{
    //-----------------------------------------------------GUARDAR REGISTROS EN UN ARCHIVO--------------------------------------------------
    public void guardarPersonas(String tipoPersona) {
        String archivo = "BD_" + tipoPersona + ".txt";
        
        try{
            File file = new File(archivo);
            HashSet<Persona> registrosExistentes = new HashSet<>();
            
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                    String linea;
                    while ((linea = reader.readLine()) != null) {
                        String[] datos = linea.split(",");                  
                        if ("Cliente".equals(tipoPersona)) {  
                            ClienteBean cliente = new ClienteBean(Integer.parseInt(datos[0]), datos[1], datos[2], Genero.valueOf(datos[3]),  
                                                    Integer.parseInt(datos[4]), datos[5]);
                            registrosExistentes.add(cliente);
                        }
                        
                        if ("Veterinario".equals(tipoPersona)) {
                            VeterinarioBean vet = new VeterinarioBean(Integer.parseInt(datos[0]), datos[1], datos[2], Genero.valueOf(datos[3]), 
                                                    Integer.parseInt(datos[4]), Integer.parseInt(datos[5]));
                            registrosExistentes.add(vet);
                        }
                    }
                }
            } else {
                file.createNewFile();
            }
        
            try (FileWriter writer = new FileWriter(archivo, true)) {
                switch (tipoPersona) {
                    case "Cliente":
                        for (ClienteBean cliente : ClienteBean.getClientes()) {
                            if (!registrosExistentes.contains(cliente)) {
                                writer.write(cliente.formatoArchivo() + System.lineSeparator());
                            }
                        };
                        break;
                    case "Veterinario":
                        for (VeterinarioBean vet : VeterinarioBean.getVeterinarios()) {
                            if (!registrosExistentes.contains(vet)) {
                                writer.write(vet.formatoArchivo() + System.lineSeparator());
                            }
                        };
                        break;
                    default:
                        throw new Exception ("Tipo de persona " + tipoPersona + " invalido.");
                }
                writer.flush();
            }
        } catch (Exception ex) {
            System.err.println("Error al guardar los datos: " + ex.getMessage());
        }
    }
    
    
    //----------------------------------------------------ACTUALIZAR REGISTROS DESDE UN ARCHIVO--------------------------------------------
    public boolean actualizarArchivo(String tipoPersona, HashSet<Persona> personas) {
        String archivoOriginal = "BD_" + tipoPersona + ".txt";
        String archivoTemporal = "BD_" + tipoPersona + "_temp.txt";
    
        try (FileWriter writer = new FileWriter(archivoTemporal, false)) {
            for (Persona persona : personas) {
                writer.write(persona.formatoArchivo() + System.lineSeparator());
            }
        } catch (Exception ex) {
            System.err.println("Error al actualizar el archivo: " + ex.getMessage());
        }
    
        // Reemplaza el archivo original con el archivo temporal
        File archivo = new File(archivoOriginal);
        File archivoTemp = new File(archivoTemporal);
    
        if (archivo.delete()) {
            if (archivoTemp.renameTo(archivo)) {
                return true;
            } else {
                System.err.println("Error al renombrar el archivo temporal.");
                return false;
            }
        } else {
            System.err.println("No se pudo eliminar el archivo original.");
            return false;
        }
    }
      
    
    //------------------------------------------------------CARGAR REGISTROS DESDE UN ARCHIVO----------------------------------------------
    public void cargarDatosEnRAM() {
        cargarPersonas("Cliente");
        cargarPersonas("Veterinario");
    }
    
    public void cargarPersonas(String tipoPersona) {
        String archivo = "BD_" + tipoPersona + ".txt";
    
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            
            switch (tipoPersona) {
                case "Cliente":
                    HashSet<ClienteBean> clientesCargados = new HashSet<>();
                    while ((linea = reader.readLine()) != null) {
                        String[] datos = linea.split(",");
                        ClienteBean cliente;
                        if (datos.length == 6){
                                cliente = new ClienteBean(
                                Integer.parseInt(datos[0]),         //NroCliente
                                datos[1],                           //Apellido
                                datos[2],                           //Nombre
                                Genero.valueOf(datos[3]),           //Genero
                                Integer.parseInt(datos[4]),         //Edad
                                datos[5]                            //FechaAfiliacion
                            );
                        } else {
                            System.out.println("Línea con datos insuficientes o mal formateada: " + linea);
                            continue;
                        }
                        clientesCargados.add(cliente);
                    }
                    ClienteBean.setClientes(clientesCargados);
                    break;
                    
                case "Veterinario":
                    HashSet<VeterinarioBean> veterinariosCargados = new HashSet<>();
                    while ((linea = reader.readLine()) != null) {
                        String[] datos = linea.split(",");
                        VeterinarioBean vet;
                        if (datos.length == 6){
                                vet = new VeterinarioBean(
                                Integer.parseInt(datos[0]),         //Matricula
                                datos[1],                           //Apellido
                                datos[2],                           //Nombre
                                Genero.valueOf(datos[3]),           //Genero
                                Integer.parseInt(datos[4]),         //Edad
                                Integer.parseInt(datos[5])          //HorasTurno
                            );
                        } else {
                            System.out.println("Línea con datos insuficientes o mal formateada: " + linea);
                            continue;
                        }
                        veterinariosCargados.add(vet);
                    }
                    VeterinarioBean.setVeterinarios(veterinariosCargados);
                    break;
                    
                default:
                    System.err.println("Tipo de persona no reconocido: " + tipoPersona);
            }
        } catch (Exception ex) {
            System.err.println("Error al cargar los archivos: " + ex.getMessage());
        }
    }
    
    
    //-----------------------------------------------------------CREAR REGISTRO---------------------------------------------------------
    public boolean agregarPersona(Persona persona) {
        boolean resultado = false;
        
        try {
            resultado = persona.registrarPersona(persona);
            if (persona instanceof ClienteBean) {
                guardarPersonas("Cliente");
            } else if (persona instanceof VeterinarioBean) {
                guardarPersonas("Veterinario");
            } else {
                JOptionPane.showMessageDialog(null, "Tipo de animal no soportado.");
            }
        } catch (Exception ex) { 
            JOptionPane.showMessageDialog(null, ex.getMessage()); 
        }
        
        return resultado;
    }
    
    
    //---------------------------------------------------------MODIFICAR REGISTRO--------------------------------------------------------
    public boolean actualizarPersona(Persona personaModificado) throws Exception {
        HashSet<Persona> personas;
        boolean eliminado = false;
        boolean actualizado = false;
        
        if (personaModificado instanceof ClienteBean) {
            personas = new ClienteBean().listarPersonas();
            ClienteBean clienteModificado = (ClienteBean)personaModificado;
            
            eliminado = personas.removeIf(persona -> persona instanceof ClienteBean && 
                (((ClienteBean)persona).getNroCliente() == clienteModificado.getNroCliente()) &&
                ((ClienteBean)persona).getApellido().equals(clienteModificado.getApellido()) &&  ((ClienteBean)persona).getNombre().equals(clienteModificado.getNombre()));
            actualizado = actualizarArchivo("Cliente", personas);
            
            if (eliminado && actualizado) {
                personas.add(clienteModificado);
                actualizarArchivo("Cliente", personas);
                return true;
            }
                    
        } else if (personaModificado instanceof VeterinarioBean) {
            personas = new VeterinarioBean().listarPersonas();
            VeterinarioBean vetModificado = (VeterinarioBean)personaModificado;
            
            eliminado = personas.removeIf(persona -> persona instanceof VeterinarioBean && 
                (((VeterinarioBean)persona).getMatricula() == vetModificado.getMatricula()) &&
                ((VeterinarioBean)persona).getApellido().equals(vetModificado.getApellido()) &&  ((VeterinarioBean)persona).getNombre().equals(vetModificado.getNombre()));
            actualizado = actualizarArchivo("Veterinario", personas);
            
            if (eliminado && actualizado) {
                personas.add(vetModificado);
                actualizarArchivo("Veterinario", personas);
                return true;
            }

        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar los datos.");
        }
        throw new Exception("ERROR: Persona invalido.");
    }
    
    
    //----------------------------------------------------------ELIMINAR REGISTRO--------------------------------------------------------
    public boolean eliminarPersona(Persona persona) {
        HashSet<Persona> personas;
        
        if (persona instanceof ClienteBean) {
            personas = new ClienteBean().listarPersonas();
            return eliminarArchivo(persona, personas, "Cliente");
        } else if (persona instanceof VeterinarioBean) {
            personas = new VeterinarioBean().listarPersonas();
            return eliminarArchivo(persona, personas, "Veterinario");
        } else {
            JOptionPane.showMessageDialog(null, "Tipo de persona no soportado.");
            return false;
        }
    }

    public boolean eliminarArchivo(Persona persona, HashSet<Persona> personas, String tipoPersona) {
        if (personas.contains(persona)) {
            personas.remove(persona);
            boolean actualizado = actualizarArchivo(tipoPersona, personas);
            if (!actualizado) {
                JOptionPane.showMessageDialog(null, "Error al actualizar los datos.");
            }
            return actualizado;
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el " + tipoPersona.toLowerCase() + " para eliminar.");
            return false;
        }
    }
    
    
    //-----------------------------------------------------------LISTAR REGISTROS--------------------------------------------------------
    public HashSet<Persona> listarPersonas(String tipoPersona) throws Exception {
        HashSet<Persona> personas;
        switch(tipoPersona){
            case "Cliente": 
                personas = new ClienteBean().listarPersonas();
                break;
            case "Veterinario":
                personas = new VeterinarioBean().listarPersonas();
                break;
            default: 
                throw new Exception ("Tipo de persona invalido.");
        }
        return personas;
    }
}