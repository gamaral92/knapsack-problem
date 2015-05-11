/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cplex;

/**
 *
 * @author gabrielamaral
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //-Djava.library.path="/opt/ibm/ILOG/CPLEX_Studio1261/cplex/bin/x86-64_linux/"
        //System.setProperty("java.library.path", "C:\\Program Files\\IBM\\ILOG\\CPLEX_Studio1261\\cplex\\lib");
        Modelo modelo = new Modelo();
        if(modelo.readFile("inputs/teste20.txt")){
            modelo.modelo();
        }
    }

    

}
