import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/** Interfaz grafica para listar y filtrar los clientes de la veterinaria. Modificarlos/eliminarlos si se requiere. */
public class Listar_Eliminar_ClientesFrame extends JFrame
{
    private GestionPersonas gestionPersonas;
    private JTable tablaClientes = new JTable();
    private JPanel panelBotones = new JPanel();
    private JPanel panelBusqueda = new JPanel();   
    private JScrollPane panelScroll = new JScrollPane(tablaClientes);
    private JButton modificarButton = new JButton("Modificar registro");
    private JButton eliminarButton = new JButton("Eliminar registro");
    private JTextField buscarTextField = new JTextField(15);
    private JButton buscarButton = new JButton("Buscar");
    private JButton regresarButton = new JButton("Regresar");
    
    public Listar_Eliminar_ClientesFrame(GestionPersonas gestionPersonas) throws Exception {
        this.gestionPersonas = gestionPersonas;
        this.gestionPersonas.cargarDatosEnRAM();
        
        setTitle("LISTADO DE CLIENTES: ");
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
        
        cargarClientesEnTabla();
        
        modificarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modificarClienteSeleccionado();
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarClienteSeleccionado();
            }
        });
        
        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarClientes();
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regresar();
            }
        });
    }

    private void cargarClientesEnTabla() {
        try {
            HashSet<Persona> clientes = gestionPersonas.listarPersonas("Cliente");
            String[] columnas = {"Nro Cliente", "Apellido", "Nombre", "Genero", "Edad", "Fecha de Afiliacion"};
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0){
                public boolean isCellEditable(int row, int column) {
                    return false; // Celdas no editables
                }
            };
    
            for (Persona persona : clientes) {
                ClienteBean cliente = (ClienteBean)persona;
                Object[] filas = {
                    cliente.getNroCliente(),
                    cliente.getApellido(),
                    cliente.getNombre(),
                    cliente.getGenero(),
                    cliente.getEdad(),
                    cliente.getFechaAfiliacion()
                };
                modelo.addRow(filas);
            }
            tablaClientes.setModel(modelo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void modificarClienteSeleccionado() {
        try {
            int filaSeleccionada = tablaClientes.getSelectedRow();
            
            if (filaSeleccionada != -1) {
                int nroCliente = Integer.parseInt(tablaClientes.getValueAt(filaSeleccionada, 0).toString());
                String apellido = tablaClientes.getValueAt(filaSeleccionada, 1).toString();
                String nombre = tablaClientes.getValueAt(filaSeleccionada, 2).toString();
                Genero genero = Genero.valueOf(tablaClientes.getValueAt(filaSeleccionada, 3).toString());
                int edad = Integer.parseInt(tablaClientes.getValueAt(filaSeleccionada, 4).toString());
                String fechaAfiliacion = tablaClientes.getValueAt(filaSeleccionada, 5).toString();
        
                ClienteBean clienteAmodificar = new ClienteBean(nroCliente, apellido, nombre, genero, edad, fechaAfiliacion);
                this.dispose();
                new ModificarClienteFrame(clienteAmodificar, gestionPersonas, this).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente para modificar.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void eliminarClienteSeleccionado() {
        try {
            int filaSeleccionada = tablaClientes.getSelectedRow();
            
            if (filaSeleccionada != -1) {
                int nroCliente = Integer.parseInt(tablaClientes.getValueAt(filaSeleccionada, 0).toString());
                String apellido = tablaClientes.getValueAt(filaSeleccionada, 1).toString();
                String nombre = tablaClientes.getValueAt(filaSeleccionada, 2).toString();
                Genero genero = Genero.valueOf(tablaClientes.getValueAt(filaSeleccionada, 3).toString());
                int edad = Integer.parseInt(tablaClientes.getValueAt(filaSeleccionada, 4).toString());
                String fechaAfiliacion = tablaClientes.getValueAt(filaSeleccionada, 5).toString();
        
                ClienteBean clienteAeliminar = new ClienteBean(nroCliente, apellido, nombre, genero, edad, fechaAfiliacion);
                
                if (gestionPersonas.eliminarPersona(clienteAeliminar)) {
                    ((DefaultTableModel) tablaClientes.getModel()).removeRow(filaSeleccionada);
                    JOptionPane.showMessageDialog(this, "Cliente eliminado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el cliente.");
                }
                
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void buscarClientes() {
        String criterio = buscarTextField.getText().trim().toLowerCase();
        if (criterio.isEmpty()) {
            cargarClientesEnTabla();
            return;
        }
        
        try {
            HashSet<Persona> clientes = gestionPersonas.listarPersonas("Cliente");
    
            // Filtrar clientes según el nombre y/o apellido
            List<Persona> clientesList = new ArrayList<>(clientes);
            List<Persona> clientesFiltrados = clientesList.stream()
                .filter(cliente -> 
                    ((ClienteBean)cliente).getNombre().toLowerCase().contains(criterio) || 
                    ((ClienteBean)cliente).getApellido().toLowerCase().contains(criterio))
                .collect(Collectors.toList());
    
            actualizarTabla(clientesFiltrados);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void actualizarTabla(List<Persona> clientesFiltrados) {
        DefaultTableModel modelo = (DefaultTableModel) tablaClientes.getModel();
        modelo.setRowCount(0);
        
        for (Persona persona : clientesFiltrados) {
            ClienteBean cliente = (ClienteBean) persona;
            Object[] filas = {
                    cliente.getNroCliente(),
                    cliente.getApellido(),
                    cliente.getNombre(),
                    cliente.getGenero(),
                    cliente.getEdad(),
                    cliente.getFechaAfiliacion()
                };
            modelo.addRow(filas);
        }
            
        tablaClientes.setModel(modelo);
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