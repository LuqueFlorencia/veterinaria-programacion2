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
public class ModificarVeterinarioFrame extends JFrame
{
    private GestionPersonas gestionPersonas;
    private VeterinarioBean vetModificado;
    private Listar_Eliminar_VeterinariosFrame listarVeterinariosFrame; 
    
    private JTextField apellidoField;
    private JTextField nombreField;
    private JComboBox<Genero> generoComboBox;
    private JTextField edadField; 
    private JTextField matriculaField; 
    private JTextField horasTurnoField;
    private JButton actualizarButton = new JButton("Guardar Cambios");
    private JButton regresarButton = new JButton("Regresar");

    public ModificarVeterinarioFrame(VeterinarioBean vet, GestionPersonas gestionPersonas, Listar_Eliminar_VeterinariosFrame listarVeterinariosFrame) {
        this.vetModificado = vet;
        this.gestionPersonas = gestionPersonas;
        this.listarVeterinariosFrame = listarVeterinariosFrame;
        
        setTitle("Modificar Veterinario");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(9, 2));
        setLocationRelativeTo(this);
        
        apellidoField = new JTextField(vet.getNombre());
        nombreField = new JTextField(vet.getNombre());
        generoComboBox = new JComboBox<>(Genero.values());
        generoComboBox.setSelectedItem(vet.getGenero());
        matriculaField = new JTextField(String.valueOf(vet.getMatricula()));
        horasTurnoField = new JTextField(String.valueOf(vet.getHorasTurno()));
        
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
        add(new JLabel("Matricula (no modificable):"));
        add(matriculaField);
        add(new JLabel("Horas Turno:"));
        add(horasTurnoField);
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
            vetModificado.setApellido(apellidoField.getText());
            vetModificado.setNombre(nombreField.getText());
            vetModificado.setEdad(Integer.parseInt(edadField.getText()));
            vetModificado.setHorasTurno(Integer.parseInt(horasTurnoField.getText()));
    
            boolean actualizado = gestionPersonas.actualizarPersona(vetModificado);
            
            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Veterinario actualizado exitosamente.");
                this.dispose();
                new Listar_Eliminar_VeterinariosFrame(gestionPersonas).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el veterinario.");
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