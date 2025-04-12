import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Interfaz grafica para manejar los 2 tipos de gatos que se pueden crear: Callejero o Mascota */ 
public class CrearGatoFrame extends JFrame {
    private GestionAnimales gestionAnimales;

    public CrearGatoFrame(GestionAnimales gestionAnimales) throws Exception {
        this.gestionAnimales = gestionAnimales;

        setTitle("Crear Gato");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));
        setLocationRelativeTo(this);

        JButton callejeroButton = new JButton("Crear Gato Callejero");
        JButton mascotaButton = new JButton("Crear Gato con Dueño");
        JButton regresarButton = new JButton("Regresar");
        regresarButton.setForeground(Color.RED);

        add(callejeroButton);
        add(mascotaButton);
        add(regresarButton);

        callejeroButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new CrearGatoCallejeroFrame(gestionAnimales).setVisible(true);
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }  
            }
        });

        mascotaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new CrearGatoMascotaFrame(gestionAnimales).setVisible(true);
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
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                dispose();
            }
        });
    }
}