import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Interfaz grafica para crear el pajaro Callejero */ 
public class CrearPajaroCallejeroFrame extends JFrame 
{
    private GestionAnimales gestionAnimales;
    private JComboBox<EspeciePajaro> especieComboBox = new JComboBox<>(EspeciePajaro.values());
    private JComboBox<Genero> generoComboBox = new JComboBox<>(Genero.values());
    private JTextField nombreField = new JTextField();
    private JTextField pesoField = new JTextField();
    private JButton crearPajaroButton = new JButton("Crear Pajaro Callejero");
    private JButton regresarButton = new JButton("Regresar");
    
    public CrearPajaroCallejeroFrame(GestionAnimales gestionAnimales) throws Exception {
        this.gestionAnimales = gestionAnimales;

        setTitle("Crear Pajaro Callejero");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2));
        setLocationRelativeTo(this);
        
        crearPajaroButton.setFont(new Font("Dialog", Font.BOLD, 14));
        regresarButton.setForeground(Color.RED);

        add(new JLabel("Selecciona la especie:"));
        add(especieComboBox);
        add(new JLabel("Selecciona el género:"));
        add(generoComboBox);
        add(new JLabel("Peso:"));
        add(pesoField);
        add(new JLabel("Nombre Provisorio:"));
        add(nombreField);
        add(new JLabel("")); add(new JLabel("")); add(new JLabel(""));  // Espacios vacíos
        add(crearPajaroButton);
        add(new JLabel(""));
        add(regresarButton);

        crearPajaroButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                crearPajaro();
            }
        });
        
        regresarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regresar();
            }
        });
    }

    private void crearPajaro() {
        try {
            EspeciePajaro especie = (EspeciePajaro) especieComboBox.getSelectedItem();
            Genero genero = (Genero) generoComboBox.getSelectedItem();
            double peso = Double.parseDouble(pesoField.getText());
            String nombre = nombreField.getText();
    
            PajaroBean pajaroCallejero = new PajaroBean(nombre, especie, genero, peso);
            boolean resultado = gestionAnimales.agregarAnimal(pajaroCallejero);
            
            if (resultado){
                JOptionPane.showMessageDialog(null, pajaroCallejero.toString());
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