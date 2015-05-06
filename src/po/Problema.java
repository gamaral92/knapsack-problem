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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gabrielamaral
 */
public class Problema {

    private int capacidade;
    private int tamanho;
    private int[] valor;
    private int[] peso;

    public void readFile(String nomeArquivo) {
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
        } catch (IOException | NumberFormatException ex) {
            System.out.println("Arquivo " + nomeArquivo + " não encontrado.");
        }
    }

    public int problema() {
        try {
            List<List> matriz = new ArrayList<>(tamanho + 1);
            for (int i = 0; i < tamanho + 1; i++) {
                List<Integer> linha = new ArrayList<>(capacidade + 1);
                for (int j = 0; j < capacidade + 1; j++) {
                    linha.add(0);
                }
                matriz.add(linha);
            }
            //int[][] K = new int[tamanho + 1][capacidade + 1];
            for (int i = 0; i <= tamanho; i++) {
                for (int w = 0; w <= capacidade; w++) {
                    if (i == 0 || w == 0) {
                        //K[i][w] = 0;
                        matriz.get(i).set(w, 0);
                    } else if (peso[i - 1] <= w) {
                        matriz.get(i).set(w, Math.max(valor[i - 1] + (int) matriz.get(i - 1).get(w - peso[i - 1]), (int) matriz.get(i - 1).get(w)));
                        //matriz.get(i).set(w, Math.max(valor[i - 1] + K[i - 1][w - peso[i - 1]], K[i - 1][w]));
                        //K[i][w] = Math.max(valor[i - 1] + K[i - 1][w - peso[i - 1]], K[i - 1][w]);
                    } else {
                        matriz.get(i).set(w, matriz.get(i - 1).get(w));
                        //K[i][w] = K[i - 1][w];
                    }
                }
            }
            return (int) matriz.get(tamanho).get(capacidade);
//            return K[tamanho][capacidade];
        } catch (OutOfMemoryError | Exception exception) {
            System.out.println(exception.getMessage());
            return -1;
        }
    }

}
