import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashSet;

/** Interfaz grafica para modificar un Pajaro */ 
public class ModificarPajaroFrame extends JFrame
{
    private GestionAnimales gestionAnimales;
    private PajaroBean pajaroModificado;
    private Listar_Eliminar_PajarosFrame listarPajarosFrame; 
    
    private JTextField nombreField;
    private JComboBox<EspeciePajaro> especieComboBox;
    private JComboBox<Genero> generoComboBox;
    private JTextField edadField; 
    private JTextField pesoField; 
    private JCheckBox alasCortadasCheckBox;
    private JTextField idClienteField;
    private JButton actualizarButton = new JButton("Guardar Cambios");
    private JButton regresarButton = new JButton("Regresar");

    public ModificarPajaroFrame(PajaroBean pajaro, GestionAnimales gestionAnimales, Listar_Eliminar_PajarosFrame listarPajarosFrame) {
        this.pajaroModificado = pajaro;
        this.gestionAnimales = gestionAnimales;
        this.listarPajarosFrame = listarPajarosFrame;
        
        setTitle("Modificar Pajaro");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(10, 2));
        setLocationRelativeTo(this);

        nombreField = new JTextField(pajaro.getNombre());
        especieComboBox = new JComboBox<>(EspeciePajaro.values());
        especieComboBox.setSelectedItem(pajaro.getEspecie());
        generoComboBox = new JComboBox<>(Genero.values());
        generoComboBox.setSelectedItem(pajaro.getGenero());
        edadField = new JTextField(String.valueOf(pajaro.getEdad()));
        pesoField = new JTextField(String.valueOf(pajaro.getPeso()));
        alasCortadasCheckBox = new JCheckBox("Alas Cortadas", pajaro.isAlasCortadas());
        idClienteField = new JTextField(String.valueOf(pajaro.getIdCliente()));
        
        actualizarButton.setFont(new Font("Dialog", Font.BOLD, 14));
        regresarButton.setForeground(Color.RED);

        add(new JLabel("Nombre:"));
        add(nombreField);
        add(new JLabel("Especie (no modificable):"));
        add(especieComboBox);
        add(new JLabel("Género (no modificable):"));
        add(generoComboBox);
        add(new JLabel("Edad:"));
        add(edadField);
        add(new JLabel("Peso:"));
        add(pesoField);
        add(new JLabel("¿Tiene las alas cortadas?"));
        add(alasCortadasCheckBox);
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
            pajaroModificado.setNombre(nombreField.getText());
            pajaroModificado.setEdad(Integer.parseInt(edadField.getText()));
            pajaroModificado.setPeso(Double.parseDouble(pesoField.getText()));
            pajaroModificado.setAlasCortadas(alasCortadasCheckBox.isSelected());
            pajaroModificado.setIdCliente(Integer.parseInt(idClienteField.getText()));
    
            boolean actualizado = gestionAnimales.actualizarAnimal(pajaroModificado);
            
            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Pajaro actualizado exitosamente.");
                this.dispose();
                new Listar_Eliminar_PajarosFrame(gestionAnimales).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el pajaro.");
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