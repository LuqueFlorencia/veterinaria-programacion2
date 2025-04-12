import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Interfaz grafica para manejar los tipos de animales que pueden ser atendidos en la veterinaria */ 
public class TipoAnimalFrame extends JFrame {
    private GestionAnimales gestionAnimales;
    private String accion;
    private JButton perroButton = new JButton("Perro");
    private JButton gatoButton = new JButton("Gato");
    private JButton pajaroButton = new JButton("Pájaro");
    private JButton regresarButton = new JButton("Regresar al menu principal");
    private JButton salirButton = new JButton("Salir");

    public TipoAnimalFrame(String accion) throws Exception  {
        this.gestionAnimales = new GestionAnimales();
        this.accion = accion;

        setTitle("Seleccionar Tipo de Animal - " + accion);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));
        setLocationRelativeTo(this);
        
        regresarButton.setForeground(Color.RED);
        salirButton.setForeground(Color.RED);

        add(perroButton);
        add(gatoButton);
        add(pajaroButton);
        add(new JLabel(""));
        add(regresarButton);
        add(salirButton);
        
        perroButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirFormularioPerro();
            }
        });
        
        gatoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirFormularioGato();
            }
        });
        
        pajaroButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirFormularioPajaro();
            }
        });
        
        regresarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regresarAlMenu();
            }
        });
        
        salirButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    
    private void abrirFormularioPerro() {
        try {
            switch (accion) {
                case "Agregar":
                    new CrearPerroFrame(gestionAnimales).setVisible(true);
                    break;            
                case "Listar":
                    new Listar_Eliminar_PerrosFrame(gestionAnimales).setVisible(true);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Acción no válida");
                    break;
            }
            this.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void abrirFormularioGato() {
        try {
            switch (accion) {
            case "Agregar":
                new CrearGatoFrame(gestionAnimales).setVisible(true);
                break;
            case "Listar":
                new Listar_Eliminar_GatosFrame(gestionAnimales).setVisible(true);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Acción no válida");
                break;
            }
            this.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void abrirFormularioPajaro() {
        try {
            switch (accion) {
            case "Agregar":
                new CrearPajaroFrame(gestionAnimales).setVisible(true);
                break;
            case "Listar":
                new Listar_Eliminar_PajarosFrame(gestionAnimales).setVisible(true);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Acción no válida");
                break;
            }
            this.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void regresarAlMenu() {
        new MenuPrincipalFrame().setVisible(true);
        this.dispose();
    }
}