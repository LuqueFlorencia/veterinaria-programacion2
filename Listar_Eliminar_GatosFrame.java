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

/** Interfaz grafica para listar y filtrar los gatos que se encuentran en la veterinaria. Modificarlos/eliminarlos si se requiere. */
public class Listar_Eliminar_GatosFrame extends JFrame
{
    private GestionAnimales gestionAnimales;
    private JTable tablaGatos = new JTable();
    private JPanel panelBotones = new JPanel();
    private JPanel panelBusqueda = new JPanel(); 
    private JScrollPane panelScroll = new JScrollPane(tablaGatos);
    private JButton modificarButton = new JButton("Modificar registro");
    private JButton eliminarButton = new JButton("Eliminar registro");
    private JTextField buscarTextField = new JTextField(15);
    private JButton buscarButton = new JButton("Buscar");
    private JButton regresarButton = new JButton("Regresar");
    
    public Listar_Eliminar_GatosFrame(GestionAnimales gestionAnimales) throws Exception {
        this.gestionAnimales = gestionAnimales;
        this.gestionAnimales.cargarDatosEnRAM();
        
        setTitle("LISTADO DE GATOS: ");
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
        
        cargarGatosEnTabla();
        
        modificarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modificarGatoSeleccionado();
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarGatoSeleccionado();
            }
        });
        
        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarGatos();
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regresar();
            }
        });
    }

    private void cargarGatosEnTabla() {
        try {
            HashSet<Animal> gatos = gestionAnimales.listarAnimales("Gato");
            String[] columnas = {"Nombre", "Raza", "Genero", "Edad", "Peso", "Castrado", "ID_Client"};
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0){
                public boolean isCellEditable(int row, int column) {
                    return false; // Celdas no editables
                }
            };
    
            for (Animal animal : gatos) {
                GatoBean gato = (GatoBean)animal;
                Object[] filas = {
                    gato.getNombre(),
                    gato.getRaza(),
                    gato.getGenero(),
                    gato.getEdad(),
                    gato.getPeso(),
                    gato.isCastrado(),
                    gato.getIdCliente()
                };
                modelo.addRow(filas);
            }
            tablaGatos.setModel(modelo);
            
            // Personalización de la columna "Castrado"
            tablaGatos.getColumnModel().getColumn(5).setCellRenderer(new TableCellRenderer() {
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
    
    private void modificarGatoSeleccionado() {
        try {
            int filaSeleccionada = tablaGatos.getSelectedRow();
            
            if (filaSeleccionada != -1) {
                String nombre = tablaGatos.getValueAt(filaSeleccionada, 0).toString();
                RazaGato raza = (RazaGato) tablaGatos.getValueAt(filaSeleccionada, 1);
                Genero genero = Genero.valueOf(tablaGatos.getValueAt(filaSeleccionada, 2).toString());
                int edad = Integer.parseInt(tablaGatos.getValueAt(filaSeleccionada, 3).toString());
                double peso = Double.parseDouble(tablaGatos.getValueAt(filaSeleccionada, 4).toString());
                boolean castrado = Boolean.parseBoolean(tablaGatos.getValueAt(filaSeleccionada, 5).toString());
                int idCliente = Integer.parseInt(tablaGatos.getValueAt(filaSeleccionada, 6).toString());
        
                GatoBean gatoAmodificar = new GatoBean(nombre, raza, genero, edad, peso, castrado, idCliente);
                this.dispose();
                new ModificarGatoFrame(gatoAmodificar, gestionAnimales, this).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un gato para modificar.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void eliminarGatoSeleccionado() {
        try {
            int filaSeleccionada = tablaGatos.getSelectedRow();   
            
            if (filaSeleccionada != -1) {
                String nombre = tablaGatos.getValueAt(filaSeleccionada, 0).toString();
                RazaGato raza = (RazaGato) tablaGatos.getValueAt(filaSeleccionada, 1);
                Genero genero = (Genero) tablaGatos.getValueAt(filaSeleccionada, 2);
                int edad = (Integer) tablaGatos.getValueAt(filaSeleccionada, 3);
                double peso = (Double) tablaGatos.getValueAt(filaSeleccionada, 4);
                boolean castrado = Boolean.parseBoolean(tablaGatos.getValueAt(filaSeleccionada, 5).toString());
                int idCliente = (Integer) tablaGatos.getValueAt(filaSeleccionada, 6);
    
                GatoBean gatoAEliminar = new GatoBean(nombre, raza, genero, edad, peso, castrado, idCliente);
                
                if (gestionAnimales.eliminarAnimal(gatoAEliminar)) {
                    ((DefaultTableModel) tablaGatos.getModel()).removeRow(filaSeleccionada);
                    JOptionPane.showMessageDialog(this, "Gato eliminado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el gato.");
                }
                
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un gato para eliminar.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void buscarGatos() {
        String criterio = buscarTextField.getText().trim().toLowerCase();
        if (criterio.isEmpty()) {
            cargarGatosEnTabla();
            return;
        }
        
        try {
            HashSet<Animal> gatos = gestionAnimales.listarAnimales("Gato");
    
            // Filtrar gatos según el nombre
            List<Animal> gatosList = new ArrayList<>(gatos);
            List<Animal> gatosFiltrados = gatosList.stream()
                .filter(gato -> ((GatoBean)gato).getNombre().toLowerCase().contains(criterio))
                .collect(Collectors.toList());
    
            actualizarTabla(gatosFiltrados);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void actualizarTabla(List<Animal> gatosFiltrados) {
        DefaultTableModel modelo = (DefaultTableModel) tablaGatos.getModel();
        modelo.setRowCount(0);
        
        for (Animal animal : gatosFiltrados) {
            GatoBean gato = (GatoBean) animal;
            Object[] fila = {
                gato.getNombre(),
                gato.getRaza(),
                gato.getGenero(),
                gato.getEdad(),
                gato.getPeso(),
                gato.isCastrado(),
                gato.getIdCliente()
            };
            modelo.addRow(fila);
        }
            
        tablaGatos.setModel(modelo);
        // Personalización de la columna "Castrado"
        tablaGatos.getColumnModel().getColumn(5).setCellRenderer(new TableCellRenderer() {
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