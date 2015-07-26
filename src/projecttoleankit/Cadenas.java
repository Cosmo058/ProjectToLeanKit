/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecttoleankit;

public class Cadenas {
    public static String remvoverAnidados(String cadena){
        String cadenaSuperior = "";
        String ultimoNivel ="a";
        String penultimoNivel ="b";

        if (cadena.contains(":")){
            ultimoNivel = cadena.substring(cadena.lastIndexOf(':')+1);
            cadenaSuperior = cadena.substring(0,cadena.lastIndexOf(":"));
            if(cadenaSuperior.contains(":"))
                penultimoNivel = cadenaSuperior.substring(cadenaSuperior.lastIndexOf(':')+1);
            else
                penultimoNivel = cadenaSuperior;

            //System.out.println("LaneName: "+laneName+" LaneSup: "+laneSuperior+" ultiNiv: "+ultimoNivel+" penNiv:"+penultimoNivel);
        }

        if(ultimoNivel.equals(penultimoNivel))
            return cadenaSuperior;
        else
            return cadena;
    }
    
}
