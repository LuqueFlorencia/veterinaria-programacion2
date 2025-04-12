import javax.swing.*;
import java.util.HashSet;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

/** ABM para animales */
public class GestionTurnos
{
    //-----------------------------------------------------GUARDAR REGISTROS EN UN ARCHIVO--------------------------------------------------
    public void guardarTurnos() {
        String archivo = "BD_Turnos.txt";
        
        try{
            File file = new File(archivo);
            HashSet<TurnoBean> registrosExistentes = new HashSet<>();
            
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                    String linea;
                    while ((linea = reader.readLine()) != null) {
                        String[] datos = linea.split(",");
                        TurnoBean turno = new TurnoBean(datos[0], Integer.parseInt(datos[1]), Integer.parseInt(datos[2]), 
                                               PracticaMedica.valueOf(datos[3]),datos[4]);
                        registrosExistentes.add(turno);
                    }
                }
            } else {
                file.createNewFile();
            }
        
            try (FileWriter writer = new FileWriter(archivo, true)) {
                for (TurnoBean turno : TurnoBean.getTurnos()) {
                    if (!registrosExistentes.contains(turno)) {
                        writer.write(turno.formatoArchivo() + System.lineSeparator());
                    }
                };
                writer.flush();
            }
        } catch (Exception ex) {
            System.err.println("Error al guardar los datos: " + ex.getMessage());
        }
    }
    
    
    //----------------------------------------------------ACTUALIZAR REGISTROS DESDE UN ARCHIVO--------------------------------------------
    public boolean actualizarArchivo(HashSet<TurnoBean> turnos) {
        String archivoOriginal = "BD_Turnos.txt";
        String archivoTemporal = "BD_Turnos_temp.txt";
    
        try (FileWriter writer = new FileWriter(archivoTemporal, false)) {
            for (TurnoBean turno : turnos) {
                writer.write(turno.formatoArchivo() + System.lineSeparator());
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
        cargarTurnos();
    }
    
    public void cargarTurnos() {
        String archivo = "BD_Turnos.txt";
    
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            HashSet<TurnoBean> turnosCargados = new HashSet<>();
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                TurnoBean turno;
                if (datos.length == 5){
                        turno = new TurnoBean(
                        datos[0],                           //NombreAnimal
                        Integer.parseInt(datos[1]),         //NroCliente
                        Integer.parseInt(datos[2]),         //MatriculaVeterinario
                        PracticaMedica.valueOf(datos[3]),   //PracticaMedica
                        datos[4]                            //FechaTurno
                    );
                } else {
                    System.out.println("Línea con datos insuficientes o mal formateada: " + linea);
                    continue;
                }
                turnosCargados.add(turno);
            }
            TurnoBean.setTurnos(turnosCargados);

        } catch (Exception ex) {
            System.err.println("Error al cargar los archivos: " + ex.getMessage());
        }
    }
    
    
    //-----------------------------------------------------------CREAR REGISTRO---------------------------------------------------------
    public boolean agregarTurno(TurnoBean turno) {
        boolean resultado = false;
        
        try {
            resultado = turno.registrarTurno(turno);
            guardarTurnos();
        } catch (Exception ex) { 
            JOptionPane.showMessageDialog(null, ex.getMessage()); 
        }
        
        return resultado;
    }
    
    //----------------------------------------------------------ELIMINAR REGISTRO--------------------------------------------------------
    public boolean eliminarTurno(TurnoBean turno) {
        HashSet<TurnoBean> turnos;
        turnos = new TurnoBean().listarTurnos();
        return eliminarArchivo(turno, turnos);
        }

    public boolean eliminarArchivo(TurnoBean turno, HashSet<TurnoBean> turnos) {
        if (turnos.contains(turno)) {
            turnos.remove(turno);
            boolean actualizado = actualizarArchivo(turnos);
            if (!actualizado) {
                JOptionPane.showMessageDialog(null, "Error al actualizar los datos.");
            }
            return actualizado;
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el turno para eliminar.");
            return false;
        }
    }
    
    
    //-----------------------------------------------------------LISTAR REGISTROS--------------------------------------------------------
    public HashSet<TurnoBean> listarTurnos() throws Exception {
        HashSet<TurnoBean> turnos = new TurnoBean().listarTurnos();
        return turnos;
    }
}