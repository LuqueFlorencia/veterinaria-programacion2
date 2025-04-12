import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashSet;

/** Interfaz grafica para modificar un Veterinario */ 
public class ModificarClienteFrame extends JFrame
{
    private GestionPersonas gestionPersonas;
    private ClienteBean clienteModificado;
    private Listar_Eliminar_ClientesFrame listarClientesFrame; 
    
    private JTextField apellidoField;
    private JTextField nombreField;
    private JComboBox<Genero> generoComboBox;
    private JTextField edadField; 
    private JTextField nroClienteField; 
    private JTextField fechaAfiliacionField;
    private JButton actualizarButton = new JButton("Guardar Cambios");
    private JButton regresarButton = new JButton("Regresar");

    public ModificarClienteFrame(ClienteBean cliente, GestionPersonas gestionPersonas, Listar_Eliminar_ClientesFrame listarClientesFrame) {
        this.clienteModificado = cliente;
        this.gestionPersonas = gestionPersonas;
        this.listarClientesFrame = listarClientesFrame;
        
        setTitle("Modificar Cliente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(9, 2));
        setLocationRelativeTo(this);
        
        apellidoField = new JTextField(cliente.getNombre());
        nombreField = new JTextField(cliente.getNombre());
        generoComboBox = new JComboBox<>(Genero.values());
        generoComboBox.setSelectedItem(cliente.getGenero());
        nroClienteField = new JTextField(String.valueOf(cliente.getNroCliente()));
        fechaAfiliacionField = new JTextField(String.valueOf(cliente.getFechaAfiliacion()));
        
        actualizarButton.setFont(new Font("Dialog", Font.BOLD, 14));
        regresarButton.setForeground(Color.RED);

        add(new JLabel("Apellido:"));
        add(apellidoField);
        add(new JLabel("Nombre:"));
        add(nombreField);
        add(new JLabel("Género (no modificable):"));
        add(generoComboBox);
        add(new JLabel("Edad:"));
        add(edadField);
        add(new JLabel("Nro Cliente (no modificable):"));
        add(nroClienteField);
        add(new JLabel("Fecha de Afiliacion:"));
        add(fechaAfiliacionField);
        add(new JLabel("")); add(new JLabel("")); add(new JLabel(""));  // Espacios vacíos
        add(actualizarButton);
        add(new JLabel(""));
        add(regresarButton);
             
        actualizarButton.addActionListener(e -> guardarCambios());
        regresarButton.addActionListener(e -> regresar());

        setVisible(true);
    }
    
    private void guardarCambios() {
        try {
            clienteModificado.setApellido(apellidoField.getText());
            clienteModificado.setNombre(nombreField.getText());
            clienteModificado.setEdad(Integer.parseInt(edadField.getText()));
            clienteModificado.setFechaAfiliacion(fechaAfiliacionField.getText());
    
            boolean actualizado = gestionPersonas.actualizarPersona(clienteModificado);
            
            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Cliente actualizado exitosamente.");
                this.dispose();
                new Listar_Eliminar_ClientesFrame(gestionPersonas).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el cliente.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void regresar() {
        try {
            new TipoPersonaFrame("Listar").setVisible(true);
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}