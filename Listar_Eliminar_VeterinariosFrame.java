import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/** Interfaz grafica para listar y filtrar los veterinarios de la veterinaria. Modificarlos/eliminarlos si se requiere. */
public class Listar_Eliminar_VeterinariosFrame extends JFrame
{
    private GestionPersonas gestionPersonas;
    private JTable tablaVeterinarios = new JTable();
    private JPanel panelBotones = new JPanel();
    private JPanel panelBusqueda = new JPanel();  
    private JScrollPane panelScroll = new JScrollPane(tablaVeterinarios);
    private JButton modificarButton = new JButton("Modificar registro");
    private JButton eliminarButton = new JButton("Eliminar registro");
    private JTextField buscarTextField = new JTextField(15);
    private JButton buscarButton = new JButton("Buscar");
    private JButton regresarButton = new JButton("Regresar");
    
    public Listar_Eliminar_VeterinariosFrame(GestionPersonas gestionPersonas) throws Exception {
        this.gestionPersonas = gestionPersonas;
        this.gestionPersonas.cargarDatosEnRAM();
        
        setTitle("LISTADO DE VETERINARIOS: ");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(this);
        
        modificarButton.setFont(new Font("Dialog", Font.BOLD, 14));
        eliminarButton.setFont(new Font("Dialog", Font.BOLD, 14));
        regresarButton.setForeground(Color.RED);
        panelBotones.add(modificarButton);
        panelBotones.add(eliminarButton);
        panelBotones.add(regresarButton);
        add(panelBotones, BorderLayout.SOUTH);
        
        panelBusqueda.add(new JLabel("Buscar por Apellido o Nombre:"));
        panelBusqueda.add(buscarTextField);
        panelBusqueda.add(buscarButton);
        add(panelBusqueda, BorderLayout.NORTH);
        
        add(panelScroll, BorderLayout.CENTER);
        
        cargarVeterinariosEnTabla();
        
        modificarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modificarVeterinarioSeleccionado();
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarVeterinarioSeleccionado();
            }
        });
        
        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarVeterinarios();
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regresar();
            }
        });
    }

    private void cargarVeterinariosEnTabla() {
        try {
            HashSet<Persona> veterinarios = gestionPersonas.listarPersonas("Veterinario");
            String[] columnas = {"Matricula", "Apellido", "Nombre", "Genero", "Edad", "Horas de Turno"};
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0){
                public boolean isCellEditable(int row, int column) {
                    return false; // Celdas no editables
                }
            };
    
            for (Persona persona : veterinarios) {
                VeterinarioBean vet = (VeterinarioBean)persona;
                Object[] filas = {
                    vet.getMatricula(),
                    vet.getApellido(),
                    vet.getNombre(),
                    vet.getGenero(),
                    vet.getEdad(),                    
                    vet.getHorasTurno()
                };
                modelo.addRow(filas);
            }
            tablaVeterinarios.setModel(modelo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void modificarVeterinarioSeleccionado() {
        try {
            int filaSeleccionada = tablaVeterinarios.getSelectedRow();
            
            if (filaSeleccionada != -1) {
                int matricula = Integer.parseInt(tablaVeterinarios.getValueAt(filaSeleccionada, 0).toString());
                String apellido = tablaVeterinarios.getValueAt(filaSeleccionada, 1).toString();
                String nombre = tablaVeterinarios.getValueAt(filaSeleccionada, 2).toString();
                Genero genero = Genero.valueOf(tablaVeterinarios.getValueAt(filaSeleccionada, 3).toString());
                int edad = Integer.parseInt(tablaVeterinarios.getValueAt(filaSeleccionada, 4).toString());
                int horasTurno = Integer.parseInt(tablaVeterinarios.getValueAt(filaSeleccionada, 5).toString());
        
                VeterinarioBean vetAmodificar = new VeterinarioBean(matricula, apellido, nombre, genero, edad, horasTurno);
                this.dispose();
                new ModificarVeterinarioFrame(vetAmodificar, gestionPersonas, this).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un veterinario para modificar.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void eliminarVeterinarioSeleccionado() {
        try {
            int filaSeleccionada = tablaVeterinarios.getSelectedRow();
            
            if (filaSeleccionada != -1) {
                int matricula = Integer.parseInt(tablaVeterinarios.getValueAt(filaSeleccionada, 0).toString());
                String apellido = tablaVeterinarios.getValueAt(filaSeleccionada, 1).toString();
                String nombre = tablaVeterinarios.getValueAt(filaSeleccionada, 2).toString();
                Genero genero = Genero.valueOf(tablaVeterinarios.getValueAt(filaSeleccionada, 3).toString());
                int edad = Integer.parseInt(tablaVeterinarios.getValueAt(filaSeleccionada, 4).toString());
                int horasTurno = Integer.parseInt(tablaVeterinarios.getValueAt(filaSeleccionada, 5).toString());
    
                VeterinarioBean vetAeliminar = new VeterinarioBean(matricula, apellido, nombre, genero, edad, horasTurno);
                
                if (gestionPersonas.eliminarPersona(vetAeliminar)) {
                    ((DefaultTableModel) tablaVeterinarios.getModel()).removeRow(filaSeleccionada);
                    JOptionPane.showMessageDialog(this, "Veterinario eliminado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el veterinario.");
                }
                
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un veterinario para eliminar.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void buscarVeterinarios() {
        String criterio = buscarTextField.getText().trim().toLowerCase();
        if (criterio.isEmpty()) {
            cargarVeterinariosEnTabla();
            return;
        }
        
        try {
            HashSet<Persona> veterinarios = gestionPersonas.listarPersonas("Veterinario");
    
            // Filtrar veterinarios según el nombre y/o apellido
            List<Persona> veterinariosList = new ArrayList<>(veterinarios);
            List<Persona> veterinariosFiltrados = veterinariosList.stream()
                .filter(veterinario -> 
                    ((VeterinarioBean)veterinario).getNombre().toLowerCase().contains(criterio) || 
                    ((VeterinarioBean)veterinario).getApellido().toLowerCase().contains(criterio))
                .collect(Collectors.toList());
    
            actualizarTabla(veterinariosFiltrados);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void actualizarTabla(List<Persona> veterinariosFiltrados) {
        DefaultTableModel modelo = (DefaultTableModel) tablaVeterinarios.getModel();
        modelo.setRowCount(0);
        
        for (Persona persona : veterinariosFiltrados) {
            VeterinarioBean vet = (VeterinarioBean)persona;
                Object[] filas = {
                    vet.getMatricula(),
                    vet.getApellido(),
                    vet.getNombre(),
                    vet.getGenero(),
                    vet.getEdad(),                    
                    vet.getHorasTurno()
                };
                modelo.addRow(filas);
        }
            
        tablaVeterinarios.setModel(modelo);
    }
    
    private void regresar() {
        try {
            new TipoPersonaFrame("Listar").setVisible(true);
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}