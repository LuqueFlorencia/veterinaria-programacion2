import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashSet;

/** Interfaz grafica para modificar un Gato */ 
public class ModificarGatoFrame extends JFrame
{
    private GestionAnimales gestionAnimales;
    private GatoBean gatoModificado;
    private Listar_Eliminar_GatosFrame listarGatosFrame; 
    
    private JTextField nombreField;
    private JComboBox<RazaGato> razaComboBox;
    private JComboBox<Genero> generoComboBox;
    private JTextField edadField; 
    private JTextField pesoField; 
    private JCheckBox castradoCheckBox;
    private JTextField idClienteField;
    private JButton actualizarButton = new JButton("Guardar Cambios");
    private JButton regresarButton = new JButton("Regresar");

    public ModificarGatoFrame(GatoBean gato, GestionAnimales gestionAnimales, Listar_Eliminar_GatosFrame listarGatosFrame) {
        this.gatoModificado = gato;
        this.gestionAnimales = gestionAnimales;
        this.listarGatosFrame = listarGatosFrame;
        
        setTitle("Modificar Gato");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(10, 2));
        setLocationRelativeTo(this);

        nombreField = new JTextField(gato.getNombre());
        razaComboBox = new JComboBox<>(RazaGato.values());
        razaComboBox.setSelectedItem(gato.getRaza());
        generoComboBox = new JComboBox<>(Genero.values());
        generoComboBox.setSelectedItem(gato.getGenero());
        edadField = new JTextField(String.valueOf(gato.getEdad()));
        pesoField = new JTextField(String.valueOf(gato.getPeso()));
        castradoCheckBox = new JCheckBox("Castrado", gato.isCastrado());
        idClienteField = new JTextField(String.valueOf(gato.getIdCliente()));
        
        actualizarButton.setFont(new Font("Dialog", Font.BOLD, 14));
        regresarButton.setForeground(Color.RED);

        add(new JLabel("Nombre:"));
        add(nombreField);
        add(new JLabel("Raza (no modificable):"));
        add(razaComboBox);
        add(new JLabel("Género (no modificable):"));
        add(generoComboBox);
        add(new JLabel("Edad:"));
        add(edadField);
        add(new JLabel("Peso:"));
        add(pesoField);
        add(new JLabel("¿Está castrado?"));
        add(castradoCheckBox);
        add(new JLabel("ID del Cliente:"));
        add(idClienteField);
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
            gatoModificado.setNombre(nombreField.getText());
            gatoModificado.setEdad(Integer.parseInt(edadField.getText()));
            gatoModificado.setPeso(Double.parseDouble(pesoField.getText()));
            gatoModificado.setCastrado(castradoCheckBox.isSelected());
            gatoModificado.setIdCliente(Integer.parseInt(idClienteField.getText()));
    
            boolean actualizado = gestionAnimales.actualizarAnimal(gatoModificado);
            
            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Gato actualizado exitosamente.");
                this.dispose();
                new Listar_Eliminar_GatosFrame(gestionAnimales).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el gato.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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