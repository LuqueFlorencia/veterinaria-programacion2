import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashSet;

/** Interfaz grafica para modificar un Perro */ 
public class ModificarPerroFrame extends JFrame
{
    private GestionAnimales gestionAnimales;
    private PerroBean perroModificado;
    private Listar_Eliminar_PerrosFrame listarPerrosFrame; 
    
    private JTextField nombreField;
    private JComboBox<RazaPerro> razaComboBox;
    private JComboBox<Genero> generoComboBox;
    private JTextField edadField; 
    private JTextField pesoField; 
    private JCheckBox castradoCheckBox;
    private JTextField idClienteField;
    private JButton actualizarButton = new JButton("Guardar Cambios");
    private JButton regresarButton = new JButton("Regresar");

    public ModificarPerroFrame(PerroBean perro, GestionAnimales gestionAnimales, Listar_Eliminar_PerrosFrame listarPerrosFrame) throws Exception {
        this.perroModificado = perro;
        this.gestionAnimales = gestionAnimales;
        this.listarPerrosFrame = listarPerrosFrame;
        
        setTitle("Modificar Perro");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(10, 2));
        setLocationRelativeTo(this);

        nombreField = new JTextField(perro.getNombre());
        razaComboBox = new JComboBox<>(RazaPerro.values());
        razaComboBox.setSelectedItem(perro.getRaza());
        generoComboBox = new JComboBox<>(Genero.values());
        generoComboBox.setSelectedItem(perro.getGenero());
        edadField = new JTextField(String.valueOf(perro.getEdad()));
        pesoField = new JTextField(String.valueOf(perro.getPeso()));
        castradoCheckBox = new JCheckBox("Castrado", perro.isCastrado());
        idClienteField = new JTextField(String.valueOf(perro.getIdCliente()));
        
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
            perroModificado.setNombre(nombreField.getText());
            perroModificado.setEdad(Integer.parseInt(edadField.getText()));
            perroModificado.setPeso(Double.parseDouble(pesoField.getText()));
            perroModificado.setCastrado(castradoCheckBox.isSelected());
            perroModificado.setIdCliente(Integer.parseInt(idClienteField.getText()));
    
            boolean actualizado = gestionAnimales.actualizarAnimal(perroModificado);
            
            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Perro actualizado exitosamente.");
                this.dispose();
                new Listar_Eliminar_PerrosFrame(gestionAnimales).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el perro.");
            }
        } catch (Exception e) {
            e.printStackTrace();
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