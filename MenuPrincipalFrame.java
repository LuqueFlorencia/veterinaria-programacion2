import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Interfaz gráfica para el manejo de opciones generales de la Veterinaria. */
public class MenuPrincipalFrame extends JFrame
{
    private GestionTurnos gestionTurnos = new GestionTurnos();
    private String accion;
    private JButton agregarTurnoButton = new JButton("Agregar Turno");
    private JButton listarTurnoButton = new JButton("Listar Turnos");
    private JButton agregarAnimalButton = new JButton("Agregar Animal");
    private JButton listarAnimalButton = new JButton("Listar Animales");
    private JButton agregarPersonaButton = new JButton("Agregar Persona");
    private JButton listarPersonaButton = new JButton("Listar Personas");
    private JButton salirButton = new JButton("Salir");

    public MenuPrincipalFrame() {        
        setTitle("MENU PRINCIPAL - VETERINARIA");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 2));
        setLocationRelativeTo(null);
        
        salirButton.setForeground(Color.RED);

        add(new JLabel("TURNOS:"));
        add(agregarTurnoButton);
        add(new JLabel(""));
        add(listarTurnoButton);
        add(new JLabel("")); add(new JLabel(""));
        add(new JLabel("ANIMALES:"));
        add(agregarAnimalButton);
        add(new JLabel(""));
        add(listarAnimalButton);
        add(new JLabel("")); add(new JLabel(""));
        add(new JLabel("PERSONAS:"));
        add(agregarPersonaButton);
        add(new JLabel(""));
        add(listarPersonaButton);
        add(new JLabel("")); add(new JLabel("")); add(new JLabel(""));    // Espacios vacíos
        add(salirButton);
       
        agregarTurnoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirFormularioTurno("Agregar");
            }
        });
        
        listarTurnoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirFormularioTurno("Listar");
            }
        });
        
        agregarAnimalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirTipoAnimal("Agregar");
            }
        });
        
        listarAnimalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirTipoAnimal("Listar");
            }
        });
        
        agregarPersonaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirTipoPersona("Agregar");
            }
        });
        
        listarPersonaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirTipoPersona("Listar");
            }
        });
        
        salirButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void abrirFormularioTurno(String accion) {
        try {
            switch (accion) {
                case "Agregar":
                    new CrearTurnoFrame(gestionTurnos).setVisible(true);
                    break;            
                case "Listar":
                    new Listar_Eliminar_TurnosFrame(gestionTurnos).setVisible(true);
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
    
    private void abrirTipoAnimal(String accion) {
        try {
            new TipoAnimalFrame(accion).setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void abrirTipoPersona(String accion) {
        try {
            new TipoPersonaFrame(accion).setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}