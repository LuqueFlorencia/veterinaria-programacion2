import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Interfaz grafica para manejar los tipos de personas que pueden ser involucrarse en la veterinaria */ 
public class TipoPersonaFrame extends JFrame {
    private GestionPersonas gestionPersonas;
    private String accion;
    private JButton clienteButton = new JButton("Cliente");
    private JButton veterinarioButton = new JButton("Veterinario");
    private JButton regresarButton = new JButton("Regresar al menu principal");
    private JButton salirButton = new JButton("Salir");

    public TipoPersonaFrame(String accion) throws Exception {
        this.gestionPersonas = new GestionPersonas(); 
        this.accion = accion;

        setTitle("Seleccionar Tipo de Persona - " + accion);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));
        setLocationRelativeTo(this);
        
        regresarButton.setForeground(Color.RED);
        salirButton.setForeground(Color.RED);

        add(clienteButton);
        add(veterinarioButton);
        add(new JLabel(""));
        add(regresarButton);
        add(salirButton);
        
        clienteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirFormularioCliente();
            }
        });
        
        veterinarioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirFormularioVeterinario();
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

    private void abrirFormularioCliente() {
        try {
            switch (accion) {
                case "Agregar":
                    new CrearClienteFrame(gestionPersonas).setVisible(true);
                    break;            
                case "Listar":
                    new Listar_Eliminar_ClientesFrame(gestionPersonas).setVisible(true);
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

    private void abrirFormularioVeterinario() {
        try {
            switch (accion) {
                case "Agregar":
                    new CrearVeterinarioFrame(gestionPersonas).setVisible(true);
                    break;            
                case "Listar":
                    new Listar_Eliminar_VeterinariosFrame(gestionPersonas).setVisible(true);
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