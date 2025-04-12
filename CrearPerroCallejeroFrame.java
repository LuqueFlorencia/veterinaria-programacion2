import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Interfaz grafica para crear el perro Callejero */ 
public class CrearPerroCallejeroFrame extends JFrame 
{
    private GestionAnimales gestionAnimales;
    private JComboBox<RazaPerro> razaComboBox = new JComboBox<>(RazaPerro.values());
    private JComboBox<Genero> generoComboBox = new JComboBox<>(Genero.values());
    private JTextField nombreField = new JTextField();
    private JTextField pesoField = new JTextField();
    private JButton crearPerroButton = new JButton("Crear Perro Callejero");
    private JButton regresarButton = new JButton("Regresar");

    public CrearPerroCallejeroFrame(GestionAnimales gestionAnimales) throws Exception {
        this.gestionAnimales = gestionAnimales;

        setTitle("Crear Perro Callejero");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2));
        setLocationRelativeTo(this);
        
        crearPerroButton.setFont(new Font("Dialog", Font.BOLD, 14));
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
        add(crearPerroButton);
        add(new JLabel(""));
        add(regresarButton);

        crearPerroButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                crearPerro();
            }
        });
        
        regresarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regresar();
            }
        });
    }

    private void crearPerro() {
        try {
            RazaPerro raza = (RazaPerro) razaComboBox.getSelectedItem();
            Genero genero = (Genero) generoComboBox.getSelectedItem();
            double peso = Double.parseDouble(pesoField.getText());
            String nombre = nombreField.getText();
    
            PerroBean perroCallejero = new PerroBean(nombre, raza, genero, peso);
            boolean resultado = gestionAnimales.agregarAnimal(perroCallejero);
            
            if (resultado){
                JOptionPane.showMessageDialog(null, perroCallejero.toString());
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