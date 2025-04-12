/** APLICACION DE LA VETERINARIA */
public class VeterinariaApp
{
    public static void main(String[] args) {  
        GestionAnimales gestionAnimales = new GestionAnimales();
        gestionAnimales.cargarDatosEnRAM();
        GestionPersonas gestionPersonas = new GestionPersonas();
        gestionPersonas.cargarDatosEnRAM();
        GestionTurnos gestionTurnos = new GestionTurnos();
        gestionTurnos.cargarDatosEnRAM();
        
        MenuPrincipalFrame menu = new MenuPrincipalFrame();
        menu.setVisible(true);
    }
}
