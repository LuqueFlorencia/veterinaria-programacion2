import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Interfaz grafica para crear el gato Callejero */ 
public class CrearGatoCallejeroFrame extends JFrame 
{
    private GestionAnimales gestionAnimales;
    private JComboBox<RazaGato> razaComboBox = new JComboBox<>(RazaGato.values());
    private JComboBox<Genero> generoComboBox = new JComboBox<>(Genero.values());
    private JTextField nombreField = new JTextField();
    private JTextField pesoField = new JTextField();
    private JButton crearGatoButton = new JButton("Crear Gato Callejero");
    private JButton regresarButton = new JButton("Regresar");

    public CrearGatoCallejeroFrame(GestionAnimales gestionAnimales) throws Exception {
        this.gestionAnimales = gestionAnimales;

        setTitle("Crear Gato Callejero");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2));
        setLocationRelativeTo(this);
        
        crearGatoButton.setFont(new Font("Dialog", Font.BOLD, 14));
        regresarButton.setForeground(Color.RED);

        add(new JLabel("Selecciona la raza:"));
        add(razaComboBox);
        add(new JLabel("Selecciona el género:"));
        add(generoComboBox);
        add(new JLabel("Peso:"));
        add(pesoField);
        add(new JLabel("Nombre Provisorio:"));
        add(nombreField);
        add(new JLabel("")); add(new JLabel("")); add(new JLabel(""));  // Espacios vacíos
        add(crearGatoButton);
        add(new JLabel(""));
        add(regresarButton);

        crearGatoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                crearGato();
            }
        });
        
        regresarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regresar();
            }
        });
    }

    private void crearGato() {
        try {
            RazaGato raza = (RazaGato) razaComboBox.getSelectedItem();
            Genero genero = (Genero) generoComboBox.getSelectedItem();
            double peso = Double.parseDouble(pesoField.getText());
            String nombre = nombreField.getText();
    
            GatoBean gatoCallejero = new GatoBean(nombre, raza, genero, peso);
            boolean resultado = gestionAnimales.agregarAnimal(gatoCallejero);
            
            if (resultado){
                JOptionPane.showMessageDialog(null, gatoCallejero.toString());
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