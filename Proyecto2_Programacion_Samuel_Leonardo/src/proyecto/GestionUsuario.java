/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

/**
 *
 * @author unwir
 */
public class GestionUsuario {
    private static GestionUsuario instancia;
    private  Usuarios jugadores[] = new Usuarios[5];
    private int cantidadUsuarios = 0;
    private Usuarios jugadorActual = null;
    private Usuarios jugador2 = null;
    
    public Boolean loginJugador(String nomU, String passW) {
        if (jugadorActual != buscarUsuario(nomU)) {
            for (int i = 0; i < jugadores.length; i++) {
                if (jugadores[i] == buscarUsuario(nomU)) {
                    if (nomU.equals(jugadores[i].getName()) && passW.equals(jugadores[i].getPass())) {
                        jugadorActual = jugadores[i];
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    public boolean verificarLaPassWord(String passW){
        if(jugadorActual.verificarContra(passW)){
           return true;
        }
        return  false;  
    }
    
    
    
    public boolean verificarCantidad(){
        return cantidadUsuarios >= 2;
    }
    
    public boolean verificarLogeo(){
        return jugadorActual != null;
    }
    
    public void cerrarSesion(){
        jugadorActual = null;
    }
    
    public boolean buscarJugador2(String nomU){
        return buscarUsuario(nomU) != null && buscarUsuario(nomU) != jugadorActual;
    }
    
    public void setJugador2(String nomU){
        jugador2 = buscarUsuario(nomU);
    }
   
    public static GestionUsuario getInstancia(){
        if (instancia == null) {
            instancia = new GestionUsuario();
        }
        return instancia;
    
    }
   
    public void cambiarPassword(String nomU, String password){
        Usuarios eU = buscarUsuario(nomU);
        if(eU!=null){
             eU.setPassW(password); 
             System.out.println("Se cambio contraseña");
        }else{
            System.out.println("No se cambio contraseña");
                 
        }
      
    }
    
    public String getJugadorActual(){
        return jugadorActual.getName();
    }
   
    private Usuarios buscarUsuario(String nomU){
        for (Usuarios u : jugadores){
            if(u != null && u.getName().equals(nomU)){
                return u;
            } 
        }
        return null;
    }
    
    
    public boolean agregarUsuario(String nomU, String passW){
        if(buscarUsuario(nomU) == null){
            for (int i = 0; i < jugadores.length; i++){
               if(jugadores[i] == null){
                    jugadores[i] = new Usuarios(nomU, passW);
                    cantidadUsuarios++;
                    return true;
               }
            }
         
        }
        return false;
    }
    
   public void eliminarUsuario(String nomU) {
        for (int i = 0; i < cantidadUsuarios; i++) {
            if (jugadores[i].getName().equals(nomU)) {
                // Desplazar todos los elementos una posición a la izquierda
                for (int j = i; j < cantidadUsuarios - 1; j++) {
                    jugadores[j] = jugadores[j + 1];
                }
                jugadores[cantidadUsuarios - 1] = null; // Limpia el último espacio
                cantidadUsuarios--;
                System.out.println("Usuario eliminado correctamente.");
                break;
            }
            
        }
        cerrarSesion();
    }

    @Override
    public String toString() {
       String usuarios = "";
       for(Usuarios j: jugadores){
           if(j != null){
           usuarios += j.getName() + ", ";
           }
       }
       if(usuarios.length() == 0){
           return "0";
       }
       return usuarios.substring(0, usuarios.length()-2);
    }
    
    
    
    
    
    
}
