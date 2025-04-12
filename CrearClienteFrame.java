import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/** Interfaz grafica para crear un nuevo Cliente */ 
public class CrearClienteFrame extends JFrame 
{
    private GestionPersonas gestionPersonas;
    private JTextField apellidoField = new JTextField();
    private JTextField nombreField = new JTextField();
    private JComboBox<Genero> generoComboBox = new JComboBox<>(Genero.values());
    private JTextField edadField = new JTextField();
    private JTextField nroClienteField = new JTextField();
    private JTextField fechaAfiliacionField = new JTextField();
    private JButton crearClienteButton = new JButton("Crear Cliente Nuevo");
    private JButton regresarButton = new JButton("Regresar");
    
    public CrearClienteFrame(GestionPersonas gestionPersonas) throws Exception {
        this.gestionPersonas = gestionPersonas;

        setTitle("Crear Cliente Nuevo");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(9, 2));
        setLocationRelativeTo(this);
        
        crearClienteButton.setFont(new Font("Dialog", Font.BOLD, 14));
        regresarButton.setForeground(Color.RED);

        add(new JLabel("Apellido:"));
        add(apellidoField);
        add(new JLabel("Nombre:"));
        add(nombreField);
        add(new JLabel("Selecciona el género:"));
        add(generoComboBox);
        add(new JLabel("Edad:"));
        add(edadField);
        add(new JLabel("NroCliente:"));
        add(nroClienteField);
        add(new JLabel("Fecha de afiliacion:"));
        add(fechaAfiliacionField);
        add(new JLabel("")); add(new JLabel("")); add(new JLabel(""));  // Espacios vacíos
        add(crearClienteButton);
        add(new JLabel(""));
        add(regresarButton);

        crearClienteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                crearClienteNuevo();
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regresar();
            }
        });
    }
    
    private void crearClienteNuevo() {
        try {
            String apellido = apellidoField.getText();
            String nombre = nombreField.getText();
            Genero genero = (Genero) generoComboBox.getSelectedItem();
            int edad = Integer.parseInt(edadField.getText());
            int nroCliente = Integer.parseInt(nroClienteField.getText());
            String fechaAfiliacion = fechaAfiliacionField.getText();

            ClienteBean clienteNuevo = new ClienteBean(nroCliente, apellido, nombre, genero, edad, fechaAfiliacion);
            boolean resultado = gestionPersonas.agregarPersona(clienteNuevo);

            if (resultado){
                JOptionPane.showMessageDialog(null, clienteNuevo.toString());
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