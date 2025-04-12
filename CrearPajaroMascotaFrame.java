import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/** Interfaz grafica para crear el pajaro con Dueño/Mascota */ 
public class CrearPajaroMascotaFrame extends JFrame
{
    private GestionAnimales gestionAnimales;
    private ClienteBean cliente;
    private JTextField nombreField = new JTextField();
    private JComboBox<EspeciePajaro> especieComboBox = new JComboBox<>(EspeciePajaro.values());
    private JComboBox<Genero> generoComboBox = new JComboBox<>(Genero.values());
    private JTextField edadField = new JTextField();
    private JTextField pesoField = new JTextField();
    private JCheckBox alasCortadasCheckBox = new JCheckBox();
    private JComboBox<Integer> idClienteComboBox = new JComboBox<>();
    private JButton crearPajaroButton = new JButton("Crear Pajaro con Dueño");
    private JButton regresarButton = new JButton("Regresar");
    
    public CrearPajaroMascotaFrame(GestionAnimales gestionAnimales) throws Exception {
        this.gestionAnimales = gestionAnimales;

        setTitle("Crear Pajaro con Dueño");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 2));
        setLocationRelativeTo(this);
        
        crearPajaroButton.setFont(new Font("Dialog", Font.BOLD, 14));
        regresarButton.setForeground(Color.RED);   
        
        for (ClienteBean cliente : ClienteBean.getClientes()) {
            idClienteComboBox.addItem(cliente.getNroCliente());
        }     

        add(new JLabel("Nombre del Pajaro:"));
        add(nombreField);
        add(new JLabel("Selecciona la especie:"));
        add(especieComboBox);
        add(new JLabel("Selecciona el género:"));
        add(generoComboBox);
        add(new JLabel("Edad:"));
        add(edadField);
        add(new JLabel("Peso:"));
        add(pesoField);
        add(new JLabel("¿Tiene las alas cortadas?"));
        add(alasCortadasCheckBox);
        add(new JLabel("ID del Cliente:"));
        add(idClienteComboBox);
        add(new JLabel("")); add(new JLabel("")); add(new JLabel(""));  // Espacios vacíos
        add(crearPajaroButton);
        add(new JLabel(""));
        add(regresarButton);

        crearPajaroButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                crearPajaroMascota();
            }
        });
        
        regresarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regresar();
            }
        });
    }

    private void crearPajaroMascota() {
        try {
            int idCliente = (int) idClienteComboBox.getSelectedItem();
            EspeciePajaro especie = (EspeciePajaro) especieComboBox.getSelectedItem();
            String nombre = nombreField.getText();
            Genero genero = (Genero) generoComboBox.getSelectedItem();
            int edad = Integer.parseInt(edadField.getText());
            double peso = Double.parseDouble(pesoField.getText());
            boolean castrado = alasCortadasCheckBox.isSelected();

            PajaroBean pajaroMascota = new PajaroBean(nombre, especie, genero, edad, peso, castrado, idCliente);
            boolean resultado = gestionAnimales.agregarAnimal(pajaroMascota);
            
            if (resultado){
                JOptionPane.showMessageDialog(null, pajaroMascota.toString());
            } else {
                throw new NumberFormatException();
            } 
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error en la entrada de datos, por favor revisa los campos.");
        }
    }
    
    private void regresar() {
        try {
            new CrearPajaroFrame(gestionAnimales).setVisible(true);
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}