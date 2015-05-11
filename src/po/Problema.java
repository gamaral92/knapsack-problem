/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package po;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabrielamaral
 */
public class Problema {

    private int capacidade;
    private int tamanho;
    private int[] valor;
    private int[] peso;

    public boolean readFile(String nomeArquivo) {
        try {
            FileReader fileReader = new FileReader(new File(nomeArquivo));
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            if (bufferedReader.ready()) {
                String linha = bufferedReader.readLine();
                capacidade = Integer.parseInt(linha);
            }
            if (bufferedReader.ready()) {
                String linha = bufferedReader.readLine();
                tamanho = Integer.parseInt(linha);
                valor = new int[tamanho + 1];
                peso = new int[tamanho + 1];
            }

            int posicao = 1;
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

    public void gerarArquivo() {
        try {
            FileWriter fileWriter = new FileWriter(new File("KP.dat"));
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("n=" + tamanho + ";");
            bufferedWriter.newLine();
            bufferedWriter.write("K=" + capacidade + ";");
            bufferedWriter.newLine();
            bufferedWriter.write("p=[");
            for (int i = 1; i < valor.length; i++) {
                if (valor.length - 2 >= i) {
                    bufferedWriter.write(valor[i] + ", ");
                } else {
                    bufferedWriter.write(valor[i] + "];");
                }
            }
            bufferedWriter.newLine();
            bufferedWriter.write("w=[");
            for (int i = 1; i < peso.length; i++) {
                if (peso.length - 2 >= i) {
                    bufferedWriter.write(peso[i] + ", ");
                } else {
                    bufferedWriter.write(peso[i] + "];");
                }
            }
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException ex) {
        }
    }

    public void knapsack() {
        try {
            int[][] matriz = new int[tamanho + 1][capacidade + 1];
            boolean[][] solucoes = new boolean[tamanho + 1][capacidade + 1];

            for (int n = 1; n <= tamanho; n++) {
                for (int w = 1; w <= capacidade; w++) {

                    int opcao = matriz[n - 1][w];

                    int opcao2 = Integer.MIN_VALUE;
                    if (peso[n] <= w) {
                        opcao2 = valor[n] + matriz[n - 1][w - peso[n]];
                    }

                    matriz[n][w] = Math.max(opcao, opcao2);
                    solucoes[n][w] = (opcao2 > opcao);
                }
            }

            boolean[] itens = new boolean[tamanho + 1];
            for (int n = tamanho, w = capacidade; n > 0; n--) {
                if (solucoes[n][w]) {
                    itens[n] = true;
                    w = w - peso[n];
                } else {
                    itens[n] = false;
                }
            }

            System.out.println("Capacidade = " + capacidade);
            System.out.println("Itens = " + tamanho);
            System.out.println("item" + "\t" + "valor" + "\t" + "peso");
            int pesoAtual = 0;
            for (int n = 1; n <= tamanho; n++) {
                if (itens[n]) {
                    pesoAtual += peso[n];
                    System.out.println(n + "\t" + valor[n] + "\t" + peso[n]);
                }
            }
            System.out.println("Maior valor = " + matriz[tamanho][capacidade]);
            System.out.println("Maior peso = " + pesoAtual);
        } catch (OutOfMemoryError | Exception exception) {
            System.out.println(exception.toString());
        }
    }

}
