/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package po;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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

    public void knapsack() {
        try {
            int[][] opt = new int[tamanho + 1][capacidade + 1];
            boolean[][] sol = new boolean[tamanho + 1][capacidade + 1];

            for (int n = 1; n <= tamanho; n++) {
                for (int w = 1; w <= capacidade; w++) {

                    // don't take item n
                    int option1 = opt[n - 1][w];

                    // take item n
                    int option2 = Integer.MIN_VALUE;
                    if (peso[n] <= w) {
                        option2 = valor[n] + opt[n - 1][w - peso[n]];
                    }

                    // select better of two options
                    opt[n][w] = Math.max(option1, option2);
                    sol[n][w] = (option2 > option1);
                }
            }

            // determine which items to take
            boolean[] take = new boolean[tamanho + 1];
            for (int n = tamanho, w = capacidade; n > 0; n--) {
                if (sol[n][w]) {
                    take[n] = true;
                    w = w - peso[n];
                } else {
                    take[n] = false;
                }
            }

            System.out.println("Capacidade = " + capacidade);
            System.out.println("Itens = " + tamanho);
            // print results
            System.out.println("item" + "\t" + "valor" + "\t" + "peso");
            int pesoAtual = 0;
            for (int n = 1; n <= tamanho; n++) {
                if (take[n]) {
                    pesoAtual += peso[n];
                    System.out.println(n + "\t" + valor[n] + "\t" + peso[n]);
                }
            }
            System.out.println("Maior valor = " + opt[tamanho][capacidade]);
            System.out.println("Maior peso = " + pesoAtual);
        } catch (OutOfMemoryError | Exception exception) {
            System.out.println(exception.toString());
        }
    }

}
