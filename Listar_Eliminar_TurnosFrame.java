import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/** Interfaz grafica para listar y filtrar los turnos de la veterinaria. Y eliminarlos si se requiere. */
public class Listar_Eliminar_TurnosFrame extends JFrame
{
    private GestionTurnos gestionTurnos;
    private JTable tablaTurnos = new JTable();
    private JPanel panelBotones = new JPanel();
    private JPanel panelBusqueda = new JPanel(); 
    private JScrollPane panelScroll = new JScrollPane(tablaTurnos);
    private JButton eliminarButton = new JButton("Eliminar registro");
    private JComboBox<PracticaMedica> practicaComboBox = new JComboBox<>(PracticaMedica.values());
    private JButton buscarButton = new JButton("Buscar");
    private JButton regresarButton = new JButton("Regresar");
    
    public Listar_Eliminar_TurnosFrame(GestionTurnos gestionTurnos) throws Exception {
        this.gestionTurnos = gestionTurnos;
        this.gestionTurnos.cargarDatosEnRAM();
        
        setTitle("LISTADO DE TURNOS: ");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(this);

        practicaComboBox.setSelectedIndex(-1);
        eliminarButton.setFont(new Font("Dialog", Font.BOLD, 14));
        regresarButton.setForeground(Color.RED);
        panelBotones.add(eliminarButton);
        panelBotones.add(regresarButton);
        add(panelBotones, BorderLayout.SOUTH);
        
        panelBusqueda.add(new JLabel("Buscar por Practica:"));
        panelBusqueda.add(practicaComboBox);
        panelBusqueda.add(buscarButton);
        add(panelBusqueda, BorderLayout.NORTH);
        
        add(panelScroll, BorderLayout.CENTER);
        
        cargarTurnosEnTabla();
      
        eliminarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarTurnoSeleccionado();
            }
        });
        
        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarTurnosPorPracticaMedica();
            }
        });
        
        regresarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regresar();
            }
        });
    }

    private void cargarTurnosEnTabla() {
        try {
            HashSet<TurnoBean> turnos = gestionTurnos.listarTurnos();
            String[] columnas = {"Nombre Animal", "Nro Cliente", "Matricula Veterinario", "Practica Medica", "Fecha del Turno"};
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0){
                public boolean isCellEditable(int row, int column) {
                    return false; // Celdas no editables
                }
            };
    
            for (TurnoBean turno : turnos) {
                Object[] filas = {
                    turno.getNombreAnimal(),
                    turno.getNroCliente(),
                    turno.getMatriculaVeterinario(),
                    turno.getPractica(),
                    turno.getFechaTurno()
                };
                modelo.addRow(filas);
            }
            tablaTurnos.setModel(modelo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void eliminarTurnoSeleccionado() {
        try {
            int filaSeleccionada = tablaTurnos.getSelectedRow();
            
            if (filaSeleccionada != -1) {
                String nombreAnimal = tablaTurnos.getValueAt(filaSeleccionada, 0).toString();
                int nroCliente = Integer.parseInt(tablaTurnos.getValueAt(filaSeleccionada, 1).toString());
                int matriculaVet = Integer.parseInt(tablaTurnos.getValueAt(filaSeleccionada, 3).toString());
                PracticaMedica practica = PracticaMedica.valueOf(tablaTurnos.getValueAt(filaSeleccionada, 4).toString());
                String fechaTurno = tablaTurnos.getValueAt(filaSeleccionada, 5).toString();
        
                TurnoBean turnoAeliminar = new TurnoBean(nombreAnimal, nroCliente, matriculaVet, practica, fechaTurno);
                
                if (gestionTurnos.eliminarTurno(turnoAeliminar)) {
                    ((DefaultTableModel) tablaTurnos.getModel()).removeRow(filaSeleccionada);
                    JOptionPane.showMessageDialog(this, "Turno eliminado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el turno.");
                }
                
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un turno para eliminar.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void buscarTurnosPorPracticaMedica() {
        PracticaMedica practicaSeleccionada = (PracticaMedica) practicaComboBox.getSelectedItem();
    
        List<TurnoBean> turnosFiltrados = new ArrayList<>();
        for (TurnoBean turno : TurnoBean.getTurnos()) {
            if (turno.getPractica().equals(practicaSeleccionada)) {
                turnosFiltrados.add(turno);
            }
        }
    
        actualizarTabla(turnosFiltrados);
    }
    
    public void actualizarTabla(List<TurnoBean> turnosFiltrados) {
        DefaultTableModel modelo = (DefaultTableModel) tablaTurnos.getModel();
        modelo.setRowCount(0);      
    
        for (TurnoBean turno : turnosFiltrados) {
            Object[] filas = {
                turno.getNombreAnimal(),
                turno.getNroCliente(),
                turno.getMatriculaVeterinario(),
                turno.getPractica(),
                turno.getFechaTurno()
            };
            modelo.addRow(filas);
        }
    
        tablaTurnos.setModel(modelo);
    }

    private void regresar() {
        try {
            new MenuPrincipalFrame().setVisible(true);
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}