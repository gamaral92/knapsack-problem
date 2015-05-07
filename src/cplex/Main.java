/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cplex;

import ilog.concert.*;
import ilog.cplex.*;

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
        //System.setProperty("-Djava.library.path", "/opt/ibm/ILOG/CPLEX_Studio1261/cplex/bin/x86-64_linux/");
        model1();
    }

    private static void model1() {
        IloCplex cplex;
        try {
            cplex = new IloCplex();

            IloNumVar x = cplex.numVar(0, Double.MAX_VALUE, "x");
            IloNumVar y = cplex.numVar(0, Double.MAX_VALUE, "y");

            IloLinearNumExpr objective = cplex.linearNumExpr();

            objective.addTerm(0.12, x);
            objective.addTerm(0.15, y);

            cplex.addMinimize();

            cplex.addGe(cplex.sum(cplex.prod(60, x), cplex.prod(60, y)), 300);
            cplex.addGe(cplex.sum(cplex.prod(12, x), cplex.prod(6, y)), 36);
            cplex.addGe(cplex.sum(cplex.prod(10, x), cplex.prod(30, y)), 90);

            if (cplex.solve()) {
                System.out.println("obj = " + cplex.getObjValue());
                System.out.println("x   = " + cplex.getValue(x));
                System.out.println("y   = " + cplex.getValue(y));
            } else {
                System.out.println("Nao possui solução otima!");
            }
        } catch (IloException ex) {
            ex.printStackTrace();
        }

    }

}
