import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/** Interfaz grafica para crear el gato con Dueño/Mascota */ 
public class CrearGatoMascotaFrame extends JFrame
{
    private GestionAnimales gestionAnimales;
    private ClienteBean cliente;
    private JTextField nombreField = new JTextField();
    private JComboBox<RazaGato> razaComboBox = new JComboBox<>(RazaGato.values());
    private JComboBox<Genero> generoComboBox = new JComboBox<>(Genero.values());
    private JTextField edadField = new JTextField();
    private JTextField pesoField = new JTextField();
    private JCheckBox castradoCheckBox = new JCheckBox();
    private JComboBox<Integer> idClienteComboBox = new JComboBox<>();
    private JButton crearGatoButton = new JButton("Crear Gato con Dueño");
    private JButton regresarButton = new JButton("Regresar");

    
    public CrearGatoMascotaFrame(GestionAnimales gestionAnimales) throws Exception {
        this.gestionAnimales = gestionAnimales;

        setTitle("Crear Gato con Dueño");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 2));
        setLocationRelativeTo(this);
        
        crearGatoButton.setFont(new Font("Dialog", Font.BOLD, 14));
        regresarButton.setForeground(Color.RED);
        
        for (ClienteBean cliente : ClienteBean.getClientes()) {
            idClienteComboBox.addItem(cliente.getNroCliente());
        }

        add(new JLabel("Nombre del Gato:"));
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
        add(crearGatoButton);
        add(new JLabel(""));
        add(regresarButton);

        crearGatoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                crearGatoMascota();
            }
        });
        
        regresarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regresar();
            }
        });
    }

    private void crearGatoMascota() {
        try {
            int idCliente = (int) idClienteComboBox.getSelectedItem();
            RazaGato raza = (RazaGato) razaComboBox.getSelectedItem();
            String nombre = nombreField.getText();
            Genero genero = (Genero) generoComboBox.getSelectedItem();
            int edad = Integer.parseInt(edadField.getText());
            double peso = Double.parseDouble(pesoField.getText());
            boolean castrado = castradoCheckBox.isSelected();

            GatoBean gatoMascota = new GatoBean(nombre, raza, genero, edad, peso, castrado, idCliente);
            boolean resultado = gestionAnimales.agregarAnimal(gatoMascota);
            
            if (resultado){
                JOptionPane.showMessageDialog(null, gatoMascota.toString());
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error en la entrada de datos, por favor revisa los campos.");
        }
    }
    
    private void regresar() {
        try {
            new CrearGatoFrame(gestionAnimales).setVisible(true);
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}