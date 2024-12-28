import org.apache.commons.math3.linear.*;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.io.FileNotFoundException;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String escolha;

        do {
            // Exibir menu
            UI.mostrarMenu();

            // Ler a escolha do usuário
            System.out.println("Escolha uma opção:");
            escolha = sc.nextLine().trim().toUpperCase();

            // Processar escolha
            switch (escolha) {
                case "1":
                    funcionalidade1(); // Funcionalidade 1: Reconstrução de matriz
                    break;

                case "2":
                    funcionalidade2(); // Funcionalidade 2: Construção da matriz de imagens
                    break;

                case "3":

                    break;

                case "END":
                    System.out.println("Programa encerrado. Obrigado!");
                    break;

                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
                    break;
            }
        } while (!escolha.equals("END"));
    }




    public static void funcionalidade1() {
        String filePath = "exampleInputFunc1.csv";

        Scanner sc = new Scanner(System.in);


        // Ler a matriz do ficheiro
        double[][] matriz = Leitura.lerMatriz(filePath);
        if (matriz == null) {
            System.out.println("Erro: Matriz não pôde ser carregada. Verifique o arquivo e tente novamente.");

        }

        System.out.println("Digite o número de valores próprios a utilizar na reconstrução da matriz:");
        int k = sc.nextInt();
        sc.nextLine(); // Consumir quebra de linha pendente

        // Estabelecer o índice da matriz
        int colunas = matriz[0].length;
        int linhas = matriz.length;

        // Verificar se é simétrica; prosseguir somente se for simétrica
        while (!Validacoes.isSymmetric(matriz, colunas, linhas)) {
            System.out.println("A matriz inserida é inválida. Tente novamente.");
            matriz = Leitura.lerMatriz(filePath); // Solicitar novamente a matriz
        }

        // Atualizar o k
        k = Validacoes.verificarValoresProprios(colunas, k);

        // Criar RealMatrix
        RealMatrix matrizOriginal = new Array2DRowRealMatrix(matriz);

        // Estabelecer a matriz dos vetores
        double[][] matrizP = Calculos.MatrizColunaDosVetoresProprios(matrizOriginal, k);

        // Estabelecer a matriz dos valores
        double[][] matrizD = Calculos.getEigenvalueDiagonal(matrizOriginal, k);

        // Estabelecer a matriz coluna dos vetores transposta
        double[][] matrizTransposta = Calculos.criarTransposta(matrizP);

        // Estabelecer a matriz reconstruída
        double[][] reconstruida = Calculos.reconstruirMatriz(matrizP, matrizD, matrizTransposta);

        // Exibir resultados
        System.out.println("Matriz reconstruída:");
        UI.imprimirMatriz(reconstruida);

        System.out.println("Número de valores utilizados: " + k);
        System.out.println("Matriz dos vetores:");
        UI.imprimirMatriz(matrizP);
        System.out.println("Matriz dos valores:");
        UI.imprimirMatriz(matrizD);

        double ErroMedio = Calculos.calcularErroAbsolutoMedio(matriz, reconstruida, colunas, linhas);
        System.out.printf("Erro Absoluto Médio: %.2f\n", ErroMedio);

        // Exportar matriz reconstruída
        Escrita.exportarMatriz(reconstruida);


    }

    public static void funcionalidade2() {
        String pastaImagens = "baseImagens";
        double[][] matrizImagens = Leitura.construirMatrizDeImagens(pastaImagens);
        int colunas = matrizImagens[0].length;



        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o número de eigenfaces a usar:");
        int i = sc.nextInt();
        int x = Validacoes.verificarValoresProprios(colunas, i);

        if (matrizImagens != null) {
            System.out.println("Matriz de imagens construída com sucesso:");
            //imprimirMatriz(matrizImagens);
        } else {
            System.out.println("Erro ao construir a matriz de imagens. Verifique a pasta de imagens.");
        }
        //calcular o vetor com as medias de cada coluna
        double[] vetorMedias = Calculos.VetorLinhaMedias(matrizImagens);

        System.out.println("Vetor das médias:");
        UI.imprimirVetor(vetorMedias);
        //calcular o desvio de cada elemento à média
        double[][] matrizA = Calculos.CálculoDoDesvio(vetorMedias, matrizImagens);
        //calcular A^T.AT
        double[][] matrizCov = Calculos.MatrizdasCovarianciasTeorema(matrizA);
        //transformar numa matriz reconhecivel pela libraria
        RealMatrix matrizCovReal = new Array2DRowRealMatrix(matrizCov);
        // calcular os vetores próprios
        double[][] matrizVetores = Calculos.MatrizColunaDosVetoresProprios(matrizCovReal, x);
        System.out.println("Matriz dos vetores:");
        UI.imprimirMatriz(matrizVetores);
        double[][] matrizVetoresC = Calculos.calcularEigenfaces(matrizA, matrizVetores);
        double[][] normalizados = Calculos.normalizeEigenfaces(matrizVetoresC);
        UI.imprimirMatriz(normalizados);
        double[] mediaTransposta = Calculos.transporMatrizColuna(vetorMedias);
        double[] matrizpesos = Calculos.CalcularPesos(matrizA, mediaTransposta);
        double[] imagemReconstruida = reconstruirImagem(vetorMedias, normalizados, matrizpesos, x);
        double[] imagemajustada = Conversoes.reescalonarParaImagem(imagemReconstruida);
        System.out.println("Tamanho do vetor reconstruído: " + imagemajustada.length);
        double[][] matrizImagem = Conversoes.vetorParaMatriz(imagemajustada);
        Conversoes.matrizParaJPG(matrizImagem);
        Escrita.exportarMatriz(matrizImagem);


    }

    //Metodo para reconstruir a imagem utilizando k eigenfaces

    public static double[] reconstruirImagem(double[] vetorMedia, double[][] eigenfaces, double[] pesos, int k) {
        int tamanho = vetorMedia.length;
        double[] imagemReconstruida = new double[tamanho];

        // Inicializa a imagem reconstruída com o vetor média
        for (int i = 0; i < tamanho; i++) {
            imagemReconstruida[i] = vetorMedia[i];
        }

        // Adiciona a contribuição de cada eigenface ponderada pelos pesos
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < tamanho; j++) {
                imagemReconstruida[j] += pesos[i] * eigenfaces[j][i];
            }
        }

        return imagemReconstruida;
    }







}
