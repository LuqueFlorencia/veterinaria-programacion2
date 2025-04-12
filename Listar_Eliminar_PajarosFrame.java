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

/** Interfaz grafica para listar y filtrar los pajaros que se encuentran en la veterinaria. Modificarlos/eliminarlos si se requiere. */
public class Listar_Eliminar_PajarosFrame extends JFrame
{
    private GestionAnimales gestionAnimales;
    private JTable tablaPajaros = new JTable();
    private JPanel panelBotones = new JPanel();
    private JPanel panelBusqueda = new JPanel(); 
    private JScrollPane panelScroll = new JScrollPane(tablaPajaros);
    private JButton modificarButton = new JButton("Modificar registro");
    private JButton eliminarButton = new JButton("Eliminar registro");
    private JTextField buscarTextField = new JTextField(15);
    private JButton buscarButton = new JButton("Buscar");
    private JButton regresarButton = new JButton("Regresar");
    
    public Listar_Eliminar_PajarosFrame(GestionAnimales gestionAnimales) throws Exception {
        this.gestionAnimales = gestionAnimales;
        this.gestionAnimales.cargarDatosEnRAM();
        
        setTitle("LISTADO DE PAJAROS: ");
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

        cargarPajarosEnTabla();
        
        modificarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modificarPajaroSeleccionado();
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarPajaroSeleccionado();
            }
        });
        
        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarPajaros();
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regresar();
            }
        });
    }

    private void cargarPajarosEnTabla() {
        try {
            HashSet<Animal> pajaros = gestionAnimales.listarAnimales("Pajaro");
            String[] columnas = {"Nombre", "Especie", "Genero", "Edad", "Peso", "Alas Cortadas", "ID_Client"};
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0){
                public boolean isCellEditable(int row, int column) {
                    return false; // Celdas no editables
                }
            };
    
            for (Animal animal : pajaros) {
                PajaroBean pajaro = (PajaroBean)animal;
                Object[] filas = {
                    pajaro.getNombre(),
                    pajaro.getEspecie(),
                    pajaro.getGenero(),
                    pajaro.getEdad(),
                    pajaro.getPeso(),
                    pajaro.isAlasCortadas(),
                    pajaro.getIdCliente()
                };
                modelo.addRow(filas);
            }
            tablaPajaros.setModel(modelo);
            
            // Personalización de la columna "Castrado"
            tablaPajaros.getColumnModel().getColumn(5).setCellRenderer(new TableCellRenderer() {
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
        } catch (Exception e) {
        e.printStackTrace();
        }
    }
    
    private void modificarPajaroSeleccionado() {
        try {
            int filaSeleccionada = tablaPajaros.getSelectedRow();
            
            if (filaSeleccionada != -1) {
                String nombre = tablaPajaros.getValueAt(filaSeleccionada, 0).toString();
                EspeciePajaro especie = (EspeciePajaro) tablaPajaros.getValueAt(filaSeleccionada, 1);
                Genero genero = Genero.valueOf(tablaPajaros.getValueAt(filaSeleccionada, 2).toString());
                int edad = Integer.parseInt(tablaPajaros.getValueAt(filaSeleccionada, 3).toString());
                double peso = Double.parseDouble(tablaPajaros.getValueAt(filaSeleccionada, 4).toString());
                boolean alasCortadas = Boolean.parseBoolean(tablaPajaros.getValueAt(filaSeleccionada, 5).toString());
                int idCliente = Integer.parseInt(tablaPajaros.getValueAt(filaSeleccionada, 6).toString());
        
                PajaroBean pajaroAmodificar = new PajaroBean(nombre, especie, genero, edad, peso, alasCortadas, idCliente);
                this.dispose();
                new ModificarPajaroFrame(pajaroAmodificar, gestionAnimales, this).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un pajaro para modificar.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void eliminarPajaroSeleccionado() {
        try {
            int filaSeleccionada = tablaPajaros.getSelectedRow();   
            
            if (filaSeleccionada != -1) {
                String nombre = tablaPajaros.getValueAt(filaSeleccionada, 0).toString();
                EspeciePajaro especie = (EspeciePajaro) tablaPajaros.getValueAt(filaSeleccionada, 1);
                Genero genero = (Genero) tablaPajaros.getValueAt(filaSeleccionada, 2);
                int edad = (Integer) tablaPajaros.getValueAt(filaSeleccionada, 3);
                double peso = (Double) tablaPajaros.getValueAt(filaSeleccionada, 4);
                boolean alasCortadas = Boolean.parseBoolean(tablaPajaros.getValueAt(filaSeleccionada, 5).toString());
                int idCliente = (Integer) tablaPajaros.getValueAt(filaSeleccionada, 6);
    
                PajaroBean pajaroAEliminar = new PajaroBean(nombre, especie, genero, edad, peso, alasCortadas, idCliente);
                
                if (gestionAnimales.eliminarAnimal(pajaroAEliminar)) {
                    ((DefaultTableModel) tablaPajaros.getModel()).removeRow(filaSeleccionada);
                    JOptionPane.showMessageDialog(this, "Pajaro eliminado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el pajaro.");
                }
                
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un pajaro para eliminar.");
            }  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void buscarPajaros() {
        String criterio = buscarTextField.getText().trim().toLowerCase();
        if (criterio.isEmpty()) {
            cargarPajarosEnTabla();
            return;
        }
        
        try {
            HashSet<Animal> pajaros = gestionAnimales.listarAnimales("Pajaro");
    
            // Filtrar pajaros según el nombre
            List<Animal> pajarosList = new ArrayList<>(pajaros);
            List<Animal> pajarosFiltrados = pajarosList.stream()
                .filter(pajaro -> ((PajaroBean)pajaro).getNombre().toLowerCase().contains(criterio))
                .collect(Collectors.toList());
    
            actualizarTabla(pajarosFiltrados);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void actualizarTabla(List<Animal> pajarosFiltrados) {
        DefaultTableModel modelo = (DefaultTableModel) tablaPajaros.getModel();
        modelo.setRowCount(0);
        
        for (Animal animal : pajarosFiltrados) {
            PajaroBean pajaro = (PajaroBean)animal;
            Object[] filas = {
                pajaro.getNombre(),
                pajaro.getEspecie(),
                pajaro.getGenero(),
                pajaro.getEdad(),
                pajaro.getPeso(),
                pajaro.isAlasCortadas(),
                pajaro.getIdCliente()
            };
            modelo.addRow(filas);
        }
            
        tablaPajaros.setModel(modelo);
        // Personalización de la columna "Alas Cortadas"
        tablaPajaros.getColumnModel().getColumn(5).setCellRenderer(new TableCellRenderer() {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}