/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

/**
 *
 * @author unwir
 */
public class Usuarios{
    private String passW; // Contraseña
    private String nomU;
    private int puntaje;

    public Usuarios(String nomU, String passW){
        this.nomU = nomU;
        this.passW = passW;
        puntaje = 0;
    }
    
    public boolean verificarContra(String passsW){
        return this.passW.equals(passsW);
    }
    
    public void sumarPuntaje(){
        puntaje+= 3;
    }
    
    
    public String getName(){
        return nomU;
    }
    public String getPass(){
        return passW;
    }

    public void setPassW(String passW) {
        this.passW = passW;
    }
   
    
 
}
