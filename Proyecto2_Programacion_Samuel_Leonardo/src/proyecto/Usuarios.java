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
    private int partidasHeroe;
    private int partidasVillano;
  
    
    
    public Usuarios(String nomU, String passW){
        this.nomU = nomU;
        this.passW = passW;
        puntaje = 0;
        partidasHeroe = 0;
        partidasVillano = 0;
    }
    
    
    public boolean verificarContra(String passsW){
        return this.passW.equals(passsW);
    }

    public int getPuntaje() {
        return puntaje;
    }
    
    
    
    public void sumarPuntaje(){
        puntaje+= 3;
    }
    
    public void sumPartidasHeroe(){
        partidasHeroe+= 1;
    }
     
    public void sumPartidasVillano(){
        partidasVillano+= 1;
    }

    public int getPartidasHeroe() {
        return partidasHeroe;
    }

    public int getPartidasVillano() {
        return partidasVillano;
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
