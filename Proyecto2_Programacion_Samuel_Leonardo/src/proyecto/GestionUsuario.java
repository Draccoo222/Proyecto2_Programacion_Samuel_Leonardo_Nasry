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
    private Usuarios jugadorActual = null;
    
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
    
    public boolean verificarLogeo(){
        if(jugadorActual != null){
            return true;
        }
        return false;
    }
    
    public void cerrarSesion(){
        jugadorActual = null;
    }
   
    public static GestionUsuario getInstancia(){
        if (instancia == null) {
            instancia = new GestionUsuario();
        }
        return instancia;
    
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
                    return true;
               }
            }
         
        }
        return false;
    }
    
    public void eliminarUsuario(String nomU){
        Usuarios eU = buscarUsuario(nomU);
        Usuarios eU2 = jugadores[jugadores.length - 1];
        for (int i = 0; i < jugadores.length; i++) {
            if(jugadores[i] == eU && eU2!= null ){
                jugadores[i] = eU2;
                jugadores[jugadores.length - 1] = null;  
                break;
            }else if(jugadores[i] == eU && eU2 == null ){
                jugadores[i] = null;
                for (int j = 0; j < jugadores.length - 1; j++) {
                     jugadores[i] = jugadores[i + 1];
                }
                jugadores[jugadores.length - 1] = null;  
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
