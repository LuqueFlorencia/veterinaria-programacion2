import javax.swing.*;
import java.util.HashSet;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

/** ABM para animales */
public class GestionAnimales
{
    //-----------------------------------------------------GUARDAR REGISTROS EN UN ARCHIVO--------------------------------------------------
    public void guardarAnimales(String tipoAnimal) {
        String archivo = "BD_" + tipoAnimal + ".txt";
        
        try{
            File file = new File(archivo);
            HashSet<Animal> registrosExistentes = new HashSet<>();
            
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                    String linea;
                    while ((linea = reader.readLine()) != null) {
                        String[] datos = linea.split(",");
                        if ("Perro".equals(tipoAnimal)) {
                            PerroBean perro = new PerroBean(datos[0], RazaPerro.valueOf(datos[1]), Genero.valueOf(datos[2]), 
                                                   Integer.parseInt(datos[3]), Double.parseDouble(datos[4]), 
                                                   Boolean.parseBoolean(datos[5]), Integer.parseInt(datos[6]));
                            registrosExistentes.add(perro);
                        }
                        
                        if ("Gato".equals(tipoAnimal)) {
                            GatoBean gato = new GatoBean(datos[0], RazaGato.valueOf(datos[1]), Genero.valueOf(datos[2]), 
                                                   Integer.parseInt(datos[3]), Double.parseDouble(datos[4]), 
                                                   Boolean.parseBoolean(datos[5]), Integer.parseInt(datos[6]));
                            registrosExistentes.add(gato);
                        }
                        
                        if ("Pajaro".equals(tipoAnimal)) {
                            PajaroBean pajaro = new PajaroBean(datos[0], EspeciePajaro.valueOf(datos[1]), Genero.valueOf(datos[2]), 
                                                   Integer.parseInt(datos[3]), Double.parseDouble(datos[4]), 
                                                   Boolean.parseBoolean(datos[5]), Integer.parseInt(datos[6]));
                            registrosExistentes.add(pajaro);
                        }
                    }
                }
            } else {
                file.createNewFile();
            }
        
            try (FileWriter writer = new FileWriter(archivo, true)) {
                switch (tipoAnimal) {
                    case "Perro":
                        for (PerroBean perro : PerroBean.getPerros()) {
                            if (!registrosExistentes.contains(perro)) {
                                writer.write(perro.formatoArchivo() + System.lineSeparator());
                            }
                        };
                        break;
                    case "Gato":
                        for (GatoBean gato : GatoBean.getGatos()) {
                            if (!registrosExistentes.contains(gato)) {
                                writer.write(gato.formatoArchivo() + System.lineSeparator());
                            }
                        };
                        break;
                    case "Pajaro":
                        for (PajaroBean pajaro : PajaroBean.getPajaros()) {
                            if (!registrosExistentes.contains(pajaro)) {
                                writer.write(pajaro.formatoArchivo() + System.lineSeparator());
                            }
                        };
                        break;
                    default:
                        throw new Exception ("Tipo de animal " + tipoAnimal + " invalido.");
                }
                writer.flush();
            }
        } catch (Exception ex) {
            System.err.println("Error al guardar los datos: " + ex.getMessage());
        }
    }
    
    
    //----------------------------------------------------ACTUALIZAR REGISTROS DESDE UN ARCHIVO--------------------------------------------
    public boolean actualizarArchivo(String tipoAnimal, HashSet<Animal> animales) {
        String archivoOriginal = "BD_" + tipoAnimal + ".txt";
        String archivoTemporal = "BD_" + tipoAnimal + "_temp.txt";
    
        try (FileWriter writer = new FileWriter(archivoTemporal, false)) {
            for (Animal animal : animales) {
                writer.write(animal.formatoArchivo() + System.lineSeparator());
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
        cargarAnimales("Perro");
        cargarAnimales("Gato");
        cargarAnimales("Pajaro");
    }
    
    public void cargarAnimales(String tipoAnimal) {
        String archivo = "BD_" + tipoAnimal + ".txt";
    
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            
            switch (tipoAnimal) {
                case "Perro":
                    HashSet<PerroBean> perrosCargados = new HashSet<>();
                    while ((linea = reader.readLine()) != null) {
                        String[] datos = linea.split(",");
                        PerroBean perro;
                        if (datos.length == 7){
                                perro = new PerroBean(
                                datos[0],                           //Nombre
                                RazaPerro.valueOf(datos[1]),        //Raza
                                Genero.valueOf(datos[2]),           //Genero
                                Integer.parseInt(datos[3]),         //Edad
                                Double.parseDouble(datos[4]),       //Peso
                                Boolean.parseBoolean(datos[5]),     //Castrado
                                Integer.parseInt(datos[6])          //IdCliente
                            );
                        } else {
                            System.out.println("Línea con datos insuficientes o mal formateada: " + linea);
                            continue;
                        }
                        perrosCargados.add(perro);
                    }
                    PerroBean.setPerros(perrosCargados);
                    break;
                    
                case "Gato":
                    HashSet<GatoBean> gatosCargados = new HashSet<>();
                    while ((linea = reader.readLine()) != null) {
                        String[] datos = linea.split(",");
                        GatoBean gato;
                        if (datos.length == 7){
                                gato = new GatoBean(
                                datos[0],                           //Nombre
                                RazaGato.valueOf(datos[1]),         //Raza
                                Genero.valueOf(datos[2]),           //Genero
                                Integer.parseInt(datos[3]),         //Edad
                                Double.parseDouble(datos[4]),       //Peso
                                Boolean.parseBoolean(datos[5]),     //Castrado
                                Integer.parseInt(datos[6])          //IdCliente
                            );
                        } else {
                            System.out.println("Línea con datos insuficientes o mal formateada: " + linea);
                            continue;
                        }
                        gatosCargados.add(gato);
                    }
                    GatoBean.setGatos(gatosCargados);
                    break;
                    
                case "Pajaro":
                    HashSet<PajaroBean> pajarosCargados = new HashSet<>();
                    while ((linea = reader.readLine()) != null) {
                        String[] datos = linea.split(",");
                        PajaroBean pajaro; 
                        if (datos.length == 7){
                                pajaro = new PajaroBean(
                                datos[0],                           //Nombre
                                EspeciePajaro.valueOf(datos[1]),    //Especie
                                Genero.valueOf(datos[2]),           //Genero
                                Integer.parseInt(datos[3]),         //Edad
                                Double.parseDouble(datos[4]),       //Peso
                                Boolean.parseBoolean(datos[5]),     //AlasCortadas
                                Integer.parseInt(datos[6])          //IdCliente
                            );
                        } else {
                            System.out.println("Línea con datos insuficientes o mal formateada: " + linea);
                            continue;
                        }
                        pajarosCargados.add(pajaro);
                    }
                    PajaroBean.setPajaros(pajarosCargados);
                    break;
                    
                default:
                    System.err.println("Tipo de animal no reconocido: " + tipoAnimal);
            }
        } catch (Exception ex) {
            System.err.println("Error al cargar los archivos: " + ex.getMessage());
        }
    }
    
    
    //-----------------------------------------------------------CREAR REGISTRO---------------------------------------------------------
    public boolean agregarAnimal(Animal animal) {
        boolean resultado = false;
        
        try {
            resultado = animal.registrarAnimal(animal);
            if (animal instanceof PerroBean) {
                guardarAnimales("Perro");
            } else if (animal instanceof GatoBean) {
                guardarAnimales("Gato");
            } else if (animal instanceof PajaroBean) {
                guardarAnimales("Pajaro");
            } else {
                JOptionPane.showMessageDialog(null, "Tipo de animal no soportado.");
            }
        } catch (Exception ex) { 
            JOptionPane.showMessageDialog(null, ex.getMessage()); 
        }
        
        return resultado;
    }

    
    //---------------------------------------------------------MODIFICAR REGISTRO--------------------------------------------------------
    public boolean actualizarAnimal(Animal animalModificado) throws Exception {
        HashSet<Animal> animales;
        boolean eliminado = false;
        boolean actualizado = false;
        
        if (animalModificado instanceof PerroBean) {
            animales = new PerroBean().listarAnimales();
            PerroBean perroModificado = (PerroBean)animalModificado;
            
            eliminado = animales.removeIf(animal -> animal instanceof PerroBean &&
                ((PerroBean)animal).getNombre().equals(perroModificado.getNombre()) && 
                ((PerroBean)animal).getRaza().equals(perroModificado.getRaza()) &&  
                ((PerroBean)animal).getGenero().equals(perroModificado.getGenero()));
            actualizado = actualizarArchivo("Perro", animales);
            
            if (eliminado && actualizado) {
                animales.add(perroModificado);
                actualizarArchivo("Perro", animales);
                return true;
            }
                    
        } else if (animalModificado instanceof GatoBean) {
            animales = new GatoBean().listarAnimales();
            GatoBean gatoModificado = (GatoBean)animalModificado;
            
            eliminado = animales.removeIf(animal -> animal instanceof GatoBean &&
                ((GatoBean)animal).getNombre().equals(gatoModificado.getNombre()) && 
                ((GatoBean)animal).getRaza().equals(gatoModificado.getRaza()) &&  
                ((GatoBean)animal).getGenero().equals(gatoModificado.getGenero()));
            actualizado = actualizarArchivo("Gato", animales);
                    
            if (eliminado && actualizado) {
                animales.add(gatoModificado);
                actualizarArchivo("Gato", animales);
                return true;
            }

        } else if (animalModificado instanceof PajaroBean) {
            animales = new PajaroBean().listarAnimales();
            PajaroBean pajaroModificado = (PajaroBean)animalModificado;
            
            eliminado = animales.removeIf(animal -> animal instanceof PajaroBean &&
                ((PajaroBean)animal).getNombre().equals(pajaroModificado.getNombre()) && 
                ((PajaroBean)animal).getEspecie().equals(pajaroModificado.getEspecie()) &&  
                ((PajaroBean)animal).getGenero().equals(pajaroModificado.getGenero()));
            actualizado = actualizarArchivo("Pajaro", animales);
                    
            if (eliminado && actualizado) {
                animales.add(pajaroModificado);
                actualizarArchivo("Pajaro", animales);
                return true;
            }
            
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar los datos.");
        }
        throw new Exception("ERROR: Animal invalido.");
    }
    
    
    //----------------------------------------------------------ELIMINAR REGISTRO--------------------------------------------------------
    public boolean eliminarAnimal(Animal animal) {
        HashSet<Animal> animales;
        
        if (animal instanceof PerroBean) {
            animales = new PerroBean().listarAnimales();
            return eliminarArchivo(animal, animales, "Perro");
        } else if (animal instanceof GatoBean) {
            animales = new GatoBean().listarAnimales();
            return eliminarArchivo(animal, animales, "Gato");
        } else if (animal instanceof PajaroBean) {
            animales = new PajaroBean().listarAnimales();
            return eliminarArchivo(animal, animales, "Pajaro");
        } else {
            JOptionPane.showMessageDialog(null, "Tipo de animal no soportado.");
            return false;
        }
    }

    public boolean eliminarArchivo(Animal animal, HashSet<Animal> animales, String tipoAnimal) {
        if (animales.contains(animal)) {
            animales.remove(animal);
            boolean actualizado = actualizarArchivo(tipoAnimal, animales);
            if (!actualizado) {
                JOptionPane.showMessageDialog(null, "Error al actualizar los datos.");
            }
            return actualizado;
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el " + tipoAnimal.toLowerCase() + " para eliminar.");
            return false;
        }
    }
    
    
    //-----------------------------------------------------------LISTAR REGISTROS--------------------------------------------------------
    public HashSet<Animal> listarAnimales(String tipoAnimal) throws Exception {
        HashSet<Animal> animales = null;
        switch(tipoAnimal){
            case "Perro": 
                animales = new PerroBean().listarAnimales();
                break;
            case "Gato":
                animales = new GatoBean().listarAnimales();
                break;
            case "Pajaro":
                animales = new PajaroBean().listarAnimales();
                break;
            default: 
                throw new Exception ("Tipo de animal invalido.");
        }
        return animales;
    }
}