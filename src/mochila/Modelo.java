/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mochila;

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

    private int capacidade;
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
                capacidade = Integer.parseInt(linha);;
            }
            if (bufferedReader.ready()) {
                String linha = bufferedReader.readLine();
                tamanho = Integer.parseInt(linha);
                valor = new int[tamanho];
                peso = new int[tamanho];
            }
            int posicao = 0;
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

            IloNumVar[] itens = cplex.boolVarArray(tamanho);

            IloLinearNumExpr objective = cplex.linearNumExpr();

            for (int i = 0; i < tamanho; i++) {
                objective.addTerm(valor[i], itens[i]);
            }

            cplex.addMaximize(objective);

            IloLinearNumExpr expr = cplex.linearNumExpr();
            for (int i = 0; i < tamanho; i++) {
                expr.addTerm(peso[i], itens[i]);
                cplex.addLe(expr, capacidade);
            }

            if (cplex.solve()) {
                System.out.println("Status = " + cplex.getStatus());
                System.out.println("Value = " + cplex.getObjValue());
                System.out.println("Solução");
                for (int i = 0; i < tamanho; i++) {
                    if (cplex.getValue(itens[i]) == 1.0) {
                        System.out.print("item " + (i + 1) + "\t\t");
                        System.out.println(cplex.getValue(itens[i]) + "\t");
                    }
                }
            } else {
                System.out.println("Nao possui solução!");
            }
        } catch (IloException ex) {
            ex.printStackTrace();
        }
    }

}
