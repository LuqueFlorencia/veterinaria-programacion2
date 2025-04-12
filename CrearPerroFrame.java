import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/** Interfaz grafica para manejar los 2 tipos de perros que se pueden crear: Callejero o Mascota */ 
public class CrearPerroFrame extends JFrame {
    private GestionAnimales gestionAnimales;
    private JButton callejeroButton = new JButton("Crear Perro Callejero");
    private JButton mascotaButton = new JButton("Crear Perro con Dueño");
    private JButton regresarButton = new JButton("Regresar");

    public CrearPerroFrame(GestionAnimales gestionAnimales) throws Exception {
        this.gestionAnimales = gestionAnimales;

        setTitle("Crear Perro");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));
        setLocationRelativeTo(this);
        
        regresarButton.setForeground(Color.RED);

        add(callejeroButton);
        add(mascotaButton);
        add(regresarButton);

        callejeroButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new CrearPerroCallejeroFrame(gestionAnimales).setVisible(true);
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        mascotaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new CrearPerroMascotaFrame(gestionAnimales).setVisible(true);
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new TipoAnimalFrame("Agregar").setVisible(true);
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}