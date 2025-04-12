import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/** Interfaz grafica para crear un nuevo Turno */ 
public class CrearTurnoFrame extends JFrame 
{
    private GestionTurnos gestionTurnos;
    private PerroBean perro;
    private GatoBean gato;
    private PajaroBean pajaro;
    private ClienteBean cliente;
    private VeterinarioBean vet;
    
    String[] tiposDeAnimal = {"Perro", "Gato", "Pajaro"};
    private JComboBox<String> tipoAnimalComboBox = new JComboBox<>(tiposDeAnimal);
    private JComboBox<String> nombreAnimalComboBox = new JComboBox<>();
    private JComboBox<String> apellidoClienteComboBox = new JComboBox<>();
    private JComboBox<String> nombreClienteComboBox = new JComboBox<>();
    private JComboBox<Integer> nroClienteComboBox = new JComboBox<>();
    private JComboBox<String> apellidoVetComboBox = new JComboBox<>();
    private JComboBox<String> nombreVetComboBox = new JComboBox<>();
    private JComboBox<Integer> matriculaVetComboBox = new JComboBox<>();
    private JComboBox<PracticaMedica> practicaComboBox = new JComboBox<>(PracticaMedica.values());
    private JTextField fechaTurnoField = new JTextField();
    private JButton crearTurnoButton = new JButton("Crear Turno Nuevo");
    private JButton regresarButton = new JButton("Regresar");

    public CrearTurnoFrame(GestionTurnos gestionTurnos) throws Exception {
        this.gestionTurnos = gestionTurnos;

        setTitle("Crear Turno Nuevo");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(13, 2));
        setLocationRelativeTo(this);
        
        crearTurnoButton.setFont(new Font("Dialog", Font.BOLD, 14));
        regresarButton.setForeground(Color.RED);        

        for (ClienteBean cliente : ClienteBean.getClientes()) {
            apellidoClienteComboBox.addItem(cliente.getApellido());
        }
        
        for (VeterinarioBean vet : VeterinarioBean.getVeterinarios()) {
            apellidoVetComboBox.addItem(vet.getApellido());
        }
        
        add(new JLabel("Apellido del Dueño:"));
        add(apellidoClienteComboBox);
        add(new JLabel("Nombre del Dueño:"));
        add(nombreClienteComboBox);
        add(new JLabel("NroCliente:"));
        add(nroClienteComboBox);
        add(new JLabel("Apellido de Veterinario a atender:"));
        add(apellidoVetComboBox);
        add(new JLabel("Nombre de Veterinario a atender:"));
        add(nombreVetComboBox);
        add(new JLabel("Matricula:"));
        add(matriculaVetComboBox);
        add(new JLabel("Practica a realizar:"));
        add(practicaComboBox);
        add(new JLabel("Fecha del turno:"));
        add(fechaTurnoField);
        add(new JLabel("Tipo de Animal a ser atendido:"));
        add(tipoAnimalComboBox);
        add(new JLabel("Nombre Animal a ser atendido:"));
        add(nombreAnimalComboBox);
        add(new JLabel("")); add(new JLabel("")); add(new JLabel(""));  // Espacios vacíos
        add(crearTurnoButton);
        add(new JLabel(""));
        add(regresarButton);
               
        apellidoClienteComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarClienteComboBox();
            }
        });
        
        apellidoVetComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarVetComboBox();
            }
        });
        
        tipoAnimalComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarNombreAnimalComboBox();
            }
        });
        
        crearTurnoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                crearTurnoNuevo();
            }
        });
        
        regresarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regresar();
            }
        });
    }

    private void actualizarClienteComboBox() {
        nombreClienteComboBox.removeAllItems();
        nroClienteComboBox.removeAllItems();
        String apellidoSeleccionado = (String) apellidoClienteComboBox.getSelectedItem();

        if (apellidoSeleccionado != null) {
            for (ClienteBean cliente : ClienteBean.getClientes()) {
                if (cliente.getApellido().equals(apellidoSeleccionado)) {
                    nombreClienteComboBox.addItem(cliente.getNombre());
                    nroClienteComboBox.addItem(cliente.getNroCliente());
                }
            }
        }
    }
    
    private void actualizarVetComboBox() {
        nombreVetComboBox.removeAllItems();
        matriculaVetComboBox.removeAllItems();
        String apellidoSeleccionado = (String) apellidoVetComboBox.getSelectedItem();

        if (apellidoSeleccionado != null) {
            for (VeterinarioBean vet : VeterinarioBean.getVeterinarios()) {
                if (vet.getApellido().equals(apellidoSeleccionado)) {
                    nombreVetComboBox.addItem(vet.getNombre());
                    matriculaVetComboBox.addItem(vet.getMatricula());
                }
            }
        }
    }
    
    private void actualizarNombreAnimalComboBox() {
        nombreAnimalComboBox.removeAllItems();
        String tipoSeleccionado = (String) tipoAnimalComboBox.getSelectedItem();
        Integer nroClienteSeleccionado = (Integer) nroClienteComboBox.getSelectedItem();

        if (tipoSeleccionado != null && nroClienteSeleccionado != null) {
            switch (tipoSeleccionado) {
                case "Perro":
                    for (PerroBean perro : PerroBean.getPerros()) {
                        if (perro.getIdCliente() == nroClienteSeleccionado) {
                            nombreAnimalComboBox.addItem(perro.getNombre());
                        }           
                    }
                    break;
                case "Gato":
                    for (GatoBean gato : GatoBean.getGatos()) {
                        if (gato.getIdCliente() == nroClienteSeleccionado) {
                            nombreAnimalComboBox.addItem(gato.getNombre());
                        }
                    }
                    break;
                case "Pajaro":
                    for (PajaroBean pajaro : PajaroBean.getPajaros()) {
                        if (pajaro.getIdCliente() == nroClienteSeleccionado) {
                            nombreAnimalComboBox.addItem(pajaro.getNombre());
                        }
                    }
                    break;
                default:
                    System.err.println("Tipo de animal no reconocido: " + tipoSeleccionado);
                    break;
            }
        }
    }
    
    private void crearTurnoNuevo() {
        try {
            String nombreAnimal = (String) nombreAnimalComboBox.getSelectedItem();
            Integer nroCliente = (Integer) nroClienteComboBox.getSelectedItem();
            Integer matriculaVet = (Integer) matriculaVetComboBox.getSelectedItem();
            PracticaMedica practica = (PracticaMedica) practicaComboBox.getSelectedItem();
            String fechaTurno = fechaTurnoField.getText();

            if (nombreAnimal == null || nroCliente == null || matriculaVet == null || practica == null || fechaTurno.isEmpty()) {
                throw new IllegalArgumentException("Todos los campos deben estar completos.");
            }
            
            TurnoBean turnoNuevo = new TurnoBean(nombreAnimal, nroCliente, matriculaVet, practica, fechaTurno);
            boolean resultado = gestionTurnos.agregarTurno(turnoNuevo);

            if (resultado){
                JOptionPane.showMessageDialog(null, turnoNuevo.toString());
            } else {
                throw new NumberFormatException();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en la entrada de datos, por favor revisa los campos.");
        }
    }
    
    private void regresar() {
        try {
            new MenuPrincipalFrame().setVisible(true);
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}