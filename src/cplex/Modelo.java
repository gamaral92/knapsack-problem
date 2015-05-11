/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cplex;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Gabriel
 */
public class Modelo {

    private int[] capacidade;
    private int tamanho;
    private int[] valor;
    private int[] peso;

    public boolean readFile(String nomeArquivo) {
        try {
            FileReader fileReader = new FileReader(new File(nomeArquivo));
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int tam = 0;
            if (bufferedReader.ready()) {
                String linha = bufferedReader.readLine();
                tam = Integer.parseInt(linha);
                capacidade = new int[tam];
            }
            int posicao = 0;
            while (bufferedReader.ready() && posicao < tam) {
                String linha = bufferedReader.readLine();
                capacidade[posicao++] = Integer.parseInt(linha);
            }
            if (bufferedReader.ready()) {
                String linha = bufferedReader.readLine();
                tamanho = Integer.parseInt(linha);
                valor = new int[tamanho];
                peso = new int[tamanho];
            }
            posicao = 0;
            while (bufferedReader.ready()) {
                String linha = bufferedReader.readLine();
                String[] parametros = linha.split("\t");
                valor[posicao] = Integer.parseInt(parametros[0]);
                peso[posicao++] = Integer.parseInt(parametros[1]);
            }
            bufferedReader.close();
            fileReader.close();
            return true;
        } catch (IOException | NumberFormatException ex) {
            System.out.println("Arquivo " + nomeArquivo + " não encontrado.");
            return false;
        }
    }

    public void modelo() {
        IloCplex cplex;
        try {
            cplex = new IloCplex();

            IloNumVar[][] itens = new IloNumVar[tamanho][];
            for (int i = 0; i < tamanho; i++) {
                itens[i] = cplex.boolVarArray(capacidade.length);
            }

            IloLinearNumExpr objective = cplex.linearNumExpr();

            for (int i = 0; i < tamanho; i++) {
                for (int j = 0; j < capacidade.length; j++) {
                    objective.addTerm(valor[i], itens[i][j]);
                }
            }

            cplex.addMaximize(objective);

            for (int i = 0; i < capacidade.length; i++) {
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int j = 0; j < tamanho; j++) {
                    expr.addTerm(peso[j], itens[j][i]);
                }
                cplex.addLe(expr, capacidade[i]);
            }

            for (int i = 0; i < tamanho; i++) {
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int j = 0; j < capacidade.length; j++) {
                    expr.addTerm(1, itens[i][j]);
                }
                cplex.addLe(expr, 1);
            }

            if (cplex.solve()) {
                System.out.println("Status = " + cplex.getStatus());
                System.out.println("Value = " + cplex.getObjValue());
                System.out.println("Solução");
                System.out.print("\t");
                for (int j = 0; j < capacidade.length; j++) {
                    System.out.print("\tc" + (j+1));
                }
                System.out.println("");
                for (int i = 0; i < tamanho; i++) {
                    System.out.print("item " + (i + 1) + "\t\t");
                    for (int j = 0; j < capacidade.length; j++) {
                        //System.out.print("x[" + i + "][" + j +  "] = " + cplex.getValue(itens[i][j]) + "\t");
                        System.out.print(cplex.getValue(itens[i][j]) + "\t");
                    }
                    System.out.println("");
                }
            } else {
                System.out.println("Nao possui solução!");
            }
        } catch (IloException ex) {
            ex.printStackTrace();
        }
    }

}
