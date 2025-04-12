import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/** Interfaz grafica para crear un nuevo Veterinario */ 
public class CrearVeterinarioFrame extends JFrame 
{
    private GestionPersonas gestionPersonas;
    private JTextField apellidoField = new JTextField();
    private JTextField nombreField = new JTextField();
    private JComboBox<Genero> generoComboBox = new JComboBox<>(Genero.values());
    private JTextField edadField = new JTextField();
    private JTextField matriculaField = new JTextField();
    private JTextField horasTurnoField = new JTextField();
    private JButton crearVeterinarioButton = new JButton("Crear Veterinario Nuevo");
    private JButton regresarButton = new JButton("Regresar");

    public CrearVeterinarioFrame(GestionPersonas gestionPersonas) throws Exception {
        this.gestionPersonas = gestionPersonas;

        setTitle("Crear Veterinario Nuevo");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(9, 2));
        setLocationRelativeTo(this);
        
        crearVeterinarioButton.setFont(new Font("Dialog", Font.BOLD, 14));
        regresarButton.setForeground(Color.RED);

        add(new JLabel("Apellido:"));
        add(apellidoField);
        add(new JLabel("Nombre:"));
        add(nombreField);
        add(new JLabel("Selecciona el género:"));
        add(generoComboBox);
        add(new JLabel("Edad:"));
        add(edadField);
        add(new JLabel("Matricula:"));
        add(matriculaField);
        add(new JLabel("Horas Turno:"));
        add(horasTurnoField);
        add(new JLabel("")); add(new JLabel("")); add(new JLabel(""));  // Espacios vacíos
        add(crearVeterinarioButton);
        add(new JLabel(""));
        add(regresarButton);

        crearVeterinarioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                crearVeterinarioNuevo();
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regresar();
            }
        });
    }
    
    private void crearVeterinarioNuevo() {
        try {
            String apellido = apellidoField.getText();
            String nombre = nombreField.getText();
            Genero genero = (Genero) generoComboBox.getSelectedItem();
            int edad = Integer.parseInt(edadField.getText());
            int matricula = Integer.parseInt(matriculaField.getText());
            int horasTurno = Integer.parseInt(horasTurnoField.getText());

            VeterinarioBean vetNuevo = new VeterinarioBean(matricula, apellido, nombre, genero, edad, horasTurno);
            boolean resultado = gestionPersonas.agregarPersona(vetNuevo);

            if (resultado){
                JOptionPane.showMessageDialog(null, vetNuevo.toString());
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error en la entrada de datos, por favor revisa los campos.");
        }
    }
    
    private void regresar() {
        try {
            new TipoPersonaFrame("Agregar").setVisible(true);
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}