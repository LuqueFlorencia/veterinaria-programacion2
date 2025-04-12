import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/** Interfaz grafica para listar y filtrar los perros que se encuentran en la veterinaria. Modificarlos/eliminarlos si se requiere. */
public class Listar_Eliminar_PerrosFrame extends JFrame
{
    private GestionAnimales gestionAnimales;
    private JTable tablaPerros = new JTable();
    private JPanel panelBotones = new JPanel();
    private JPanel panelBusqueda = new JPanel();   
    private JScrollPane panelScroll = new JScrollPane(tablaPerros);
    private JButton modificarButton = new JButton("Modificar registro");
    private JButton eliminarButton = new JButton("Eliminar registro");
    private JTextField buscarTextField = new JTextField(15);
    private JButton buscarButton = new JButton("Buscar");
    private JButton regresarButton = new JButton("Regresar");
    
    public Listar_Eliminar_PerrosFrame(GestionAnimales gestionAnimales) throws Exception {
        this.gestionAnimales = gestionAnimales;
        this.gestionAnimales.cargarDatosEnRAM();
        
        setTitle("LISTADO DE PERROS ");
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
        
        panelBusqueda.add(new JLabel("Buscar por Nombre:"));
        panelBusqueda.add(buscarTextField);
        panelBusqueda.add(buscarButton);
        add(panelBusqueda, BorderLayout.NORTH);
        
        add(panelScroll, BorderLayout.CENTER);
        
        cargarPerrosEnTabla();
        
        modificarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modificarPerroSeleccionado();
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarPerroSeleccionado();
            }
        });
        
        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarPerros();
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regresar();
            }
        });
    }

    public void cargarPerrosEnTabla(){
        try{
            HashSet<Animal> perros = gestionAnimales.listarAnimales("Perro");
            String[] columnas = {"Nombre", "Raza", "Genero", "Edad", "Peso", "Castrado", "ID_Client"};
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
                public boolean isCellEditable(int row, int column) {
                    return false; // Celdas no editables
                }
            };
            
            for (Animal animal : perros) {
                PerroBean perro = (PerroBean)animal;
                Object[] filas = {
                    perro.getNombre(),
                    perro.getRaza(),
                    perro.getGenero(),
                    perro.getEdad(),
                    perro.getPeso(),
                    perro.isCastrado(),
                    perro.getIdCliente()
                };
                modelo.addRow(filas);
            }
            tablaPerros.setModel(modelo);
            
            // Personalización de la columna "Castrado"
            tablaPerros.getColumnModel().getColumn(5).setCellRenderer(new TableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    JLabel label = new JLabel();
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setFont(new Font("Dialog", Font.PLAIN, 12));

                    if (value instanceof Boolean) {
                        label.setText((Boolean) value ? "Sí" : "No");
                    } else {
                        label.setText("N/A");
                    }

                    return label;
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void modificarPerroSeleccionado() {
        try{
            int filaSeleccionada = tablaPerros.getSelectedRow();
            
            if (filaSeleccionada != -1) {
                String nombre = tablaPerros.getValueAt(filaSeleccionada, 0).toString();
                RazaPerro raza = (RazaPerro) tablaPerros.getValueAt(filaSeleccionada, 1);
                Genero genero = Genero.valueOf(tablaPerros.getValueAt(filaSeleccionada, 2).toString());
                int edad = Integer.parseInt(tablaPerros.getValueAt(filaSeleccionada, 3).toString());
                double peso = Double.parseDouble(tablaPerros.getValueAt(filaSeleccionada, 4).toString());
                boolean castrado = Boolean.parseBoolean(tablaPerros.getValueAt(filaSeleccionada, 5).toString());
                int idCliente = Integer.parseInt(tablaPerros.getValueAt(filaSeleccionada, 6).toString());
        
                PerroBean perroAmodificar = new PerroBean(nombre, raza, genero, edad, peso, castrado, idCliente);
                this.dispose();
                new ModificarPerroFrame(perroAmodificar, gestionAnimales, this).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un perro para modificar.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void eliminarPerroSeleccionado() {
        try{
            int filaSeleccionada = tablaPerros.getSelectedRow();   
            
            if (filaSeleccionada != -1) {
                String nombre = tablaPerros.getValueAt(filaSeleccionada, 0).toString();
                RazaPerro raza = (RazaPerro) tablaPerros.getValueAt(filaSeleccionada, 1);
                Genero genero = (Genero) tablaPerros.getValueAt(filaSeleccionada, 2);
                int edad = (Integer) tablaPerros.getValueAt(filaSeleccionada, 3);
                double peso = (Double) tablaPerros.getValueAt(filaSeleccionada, 4);
                boolean castrado = Boolean.parseBoolean(tablaPerros.getValueAt(filaSeleccionada, 5).toString());
                int idCliente = (Integer) tablaPerros.getValueAt(filaSeleccionada, 6);
    
                PerroBean perroAEliminar = new PerroBean(nombre, raza, genero, edad, peso, castrado, idCliente);
                
                if (gestionAnimales.eliminarAnimal(perroAEliminar)) {
                    ((DefaultTableModel) tablaPerros.getModel()).removeRow(filaSeleccionada);
                    JOptionPane.showMessageDialog(this, "Perro eliminado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el perro.");
                }
                
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un perro para eliminar.");
            } 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void buscarPerros() {
        String criterio = buscarTextField.getText().trim().toLowerCase();
        if (criterio.isEmpty()) {
            cargarPerrosEnTabla();
            return;
        }
        
        try {
            HashSet<Animal> perros = gestionAnimales.listarAnimales("Perro");
    
            // Filtrar perros según el nombre
            List<Animal> perrosList = new ArrayList<>(perros);
            List<Animal> perrosFiltrados = perrosList.stream()
                .filter(perro -> ((PerroBean)perro).getNombre().toLowerCase().contains(criterio))
                .collect(Collectors.toList());
    
            actualizarTabla(perrosFiltrados);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void actualizarTabla(List<Animal> perrosFiltrados) {
        DefaultTableModel modelo = (DefaultTableModel) tablaPerros.getModel();
        modelo.setRowCount(0);
        
        for (Animal animal : perrosFiltrados) {
            PerroBean perro = (PerroBean) animal;
            Object[] fila = {
                perro.getNombre(),
                perro.getRaza(),
                perro.getGenero(),
                perro.getEdad(),
                perro.getPeso(),
                perro.isCastrado(),
                perro.getIdCliente()
            };
            modelo.addRow(fila);
        }
            
        tablaPerros.setModel(modelo);
        // Personalización de la columna "Castrado"
        tablaPerros.getColumnModel().getColumn(5).setCellRenderer(new TableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel();
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setFont(new Font("Dialog", Font.PLAIN, 12));

                if (value instanceof Boolean) {
                    label.setText((Boolean) value ? "Sí" : "No");
                } else {
                    label.setText("N/A");
                }

                return label;
            }
        });
    }
    
    private void regresar() {
        try {
            new TipoAnimalFrame("Listar").setVisible(true);  
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}