import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/** Interfaz grafica para crear el perro con Dueño/Mascota */ 
public class CrearPerroMascotaFrame extends JFrame
{
    private GestionAnimales gestionAnimales;
    private ClienteBean cliente;
    private JTextField nombreField = new JTextField();
    private JComboBox<RazaPerro> razaComboBox = new JComboBox<>(RazaPerro.values());
    private JComboBox<Genero> generoComboBox = new JComboBox<>(Genero.values());
    private JTextField edadField = new JTextField();
    private JTextField pesoField = new JTextField();
    private JCheckBox castradoCheckBox = new JCheckBox();
    private JComboBox<Integer> idClienteComboBox = new JComboBox<>();
    private JButton crearPerroButton = new JButton("Crear Perro con Dueño");
    private JButton regresarButton = new JButton("Regresar");
    
    public CrearPerroMascotaFrame(GestionAnimales gestionAnimales) throws Exception {
        this.gestionAnimales = gestionAnimales;

        setTitle("Crear Perro con Dueño");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 2));
        setLocationRelativeTo(this);
        
        crearPerroButton.setFont(new Font("Dialog", Font.BOLD, 14));
        regresarButton.setForeground(Color.RED);
        
        for (ClienteBean cliente : ClienteBean.getClientes()) {
            idClienteComboBox.addItem(cliente.getNroCliente());
        }
        
        add(new JLabel("Nombre del Perro:"));
        add(nombreField);
        add(new JLabel("Selecciona la raza:"));
        add(razaComboBox);
        add(new JLabel("Selecciona el género:"));
        add(generoComboBox);
        add(new JLabel("Edad:"));
        add(edadField);
        add(new JLabel("Peso:"));
        add(pesoField);
        add(new JLabel("¿Está castrado?"));
        add(castradoCheckBox);
        add(new JLabel("ID del Cliente:"));
        add(idClienteComboBox);
        add(new JLabel("")); add(new JLabel("")); add(new JLabel(""));  // Espacios vacíos
        add(crearPerroButton);
        add(new JLabel(""));
        add(regresarButton);

        crearPerroButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                crearPerroMascota();
            }
        });
        
        regresarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regresar();
            }
        });
    }

    private void crearPerroMascota() {
        try {
            int idCliente = (int) idClienteComboBox.getSelectedItem();
            RazaPerro raza = (RazaPerro) razaComboBox.getSelectedItem();
            String nombre = nombreField.getText();
            Genero genero = (Genero) generoComboBox.getSelectedItem();
            int edad = Integer.parseInt(edadField.getText());
            double peso = Double.parseDouble(pesoField.getText());
            boolean castrado = castradoCheckBox.isSelected();

            PerroBean perroMascota = new PerroBean(nombre, raza, genero, edad, peso, castrado, idCliente);
            boolean resultado = gestionAnimales.agregarAnimal(perroMascota);
            
            if (resultado){
                JOptionPane.showMessageDialog(null, perroMascota.toString());
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error en la entrada de datos, por favor revisa los campos.");
        }
    }
    
    private void regresar() {
        try {
            new CrearPerroFrame(gestionAnimales).setVisible(true);
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}