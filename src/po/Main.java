/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package po;

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
        Problema problema = new Problema();
        if(problema.readFile("instancias/teste20.txt")){
        //if(problema.readFile("teste.txt")){
            //System.out.println(problema.problema());
            problema.knapsack();
            //problema.gerarArquivo();
        }
    }
    
}
