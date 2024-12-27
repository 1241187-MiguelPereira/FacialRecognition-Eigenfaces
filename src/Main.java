import org.apache.commons.math3.linear.*;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

import org.apache.commons.math3.linear.*;

import java.io.*;
import java.util.*;
import java.io.FileNotFoundException;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String escolha;

        do {
            // Exibir menu
            mostrarMenu();

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


    public static void mostrarMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1 - Reconstrução de Matriz");
        System.out.println("2 - Construção da Matriz de Imagens");
        System.out.println("3 - Exportação de Eigenfaces Normalizadas");
        System.out.println("END - Encerrar o programa");
    }

    public static void funcionalidade1() {
        String filePath = "exampleInputFunc1.csv";

        Scanner sc = new Scanner(System.in);


        // Ler a matriz do ficheiro
        double[][] matriz = lerMatriz(filePath);
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
        while (!isSymmetric(matriz, colunas, linhas)) {
            System.out.println("A matriz inserida é inválida. Tente novamente.");
            matriz = lerMatriz(filePath); // Solicitar novamente a matriz
        }

        // Atualizar o k
        k = verificarValoresProprios(colunas, k);

        // Criar RealMatrix
        RealMatrix matrizOriginal = new Array2DRowRealMatrix(matriz);

        // Estabelecer a matriz dos vetores
        double[][] matrizP = MatrizColunaDosVetoresProprios(matrizOriginal, k);

        // Estabelecer a matriz dos valores
        double[][] matrizD = getEigenvalueDiagonal(matrizOriginal, k);

        // Estabelecer a matriz coluna dos vetores transposta
        double[][] matrizTransposta = criarTransposta(matrizP);

        // Estabelecer a matriz reconstruída
        double[][] reconstruida = reconstruirMatriz(matrizP, matrizD, matrizTransposta);

        // Exibir resultados
        System.out.println("Matriz reconstruída:");
        imprimirMatriz(reconstruida);

        System.out.println("Número de valores utilizados: " + k);
        System.out.println("Matriz dos vetores:");
        imprimirMatriz(matrizP);
        System.out.println("Matriz dos valores:");
        imprimirMatriz(matrizD);

        double ErroMedio = calcularErroAbsolutoMedio(matriz, reconstruida, colunas, linhas);
        System.out.printf("Erro Absoluto Médio: %.2f\n", ErroMedio);

        // Exportar matriz reconstruída
        exportarMatriz(reconstruida);


    }

    public static void funcionalidade2() {
        String pastaImagens = "baseImagens";
        double[][] matrizImagens = construirMatrizDeImagens(pastaImagens);
        int colunas = matrizImagens[0].length;

        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o número de eigenfaces a usar:");
        int i = sc.nextInt();
        int x = verificarValoresProprios(colunas, i);

        if (matrizImagens != null) {
            System.out.println("Matriz de imagens construída com sucesso:");
            //imprimirMatriz(matrizImagens);
        } else {
            System.out.println("Erro ao construir a matriz de imagens. Verifique a pasta de imagens.");
        }
        //calcular o vetor com as medias de cada coluna
        double[] vetorMedias = VetorLinhaMedias(matrizImagens);

        System.out.println("Vetor das médias:");
        imprimirVetor(vetorMedias);
        //calcular o desvio de cada elemento à média
        double[][] matrizA = CálculoDoDesvio(vetorMedias, matrizImagens);
        //calcular A^T.AT
        double[][] matrizCov = MatrizdasCovarianciasTeorema(matrizA);
        //transformar numa matriz reconhecivel pela libraria
        RealMatrix matrizCovReal = new Array2DRowRealMatrix(matrizCov);
        // calcular os vetores próprios
        double[][] matrizVetores = MatrizColunaDosVetoresProprios(matrizCovReal, x);
        System.out.println("Matriz dos vetores:");
        imprimirMatriz(matrizVetores);
        double[][] matrizVetoresC = calcularEigenfaces(matrizA, matrizVetores);
        double[][] normalizados = normalizeEigenfaces(matrizVetoresC);
        imprimirMatriz(normalizados);
        double[] mediaTransposta = transporMatrizColuna(vetorMedias);
        double[] matrizpesos = CalcularPesos(matrizA, mediaTransposta);
        double[] imagemReconstruida = reconstruirImagem(vetorMedias, normalizados, matrizpesos, x);
        double[] imagemajustada = reescalonarParaImagem(imagemReconstruida);
        System.out.println("Tamanho do vetor reconstruído: " + imagemajustada.length);
        double[][] matrizImagem = vetorParaMatriz(imagemajustada);
        matrizParaJPG(matrizImagem);
        exportarMatriz(matrizImagem);


    }


    public static double[][] lerMatriz(String filePath) {
        try {
            // First pass to determine matrix dimensions
            Scanner scanner = new Scanner(new File(filePath));
            int rows = 0, cols = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    rows++;
                    if (cols == 0) cols = line.split(",").length; // Get column count from first valid line
                }
            }
            scanner.close();

            // Initialize the matrix
            double[][] matriz = new double[rows][cols];

            // Second pass to populate the matrix
            scanner = new Scanner(new File(filePath));
            int row = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] values = line.split(",");
                    for (int col = 0; col < cols; col++) {
                        matriz[row][col] = Double.parseDouble(values[col].trim());
                    }
                    row++;
                }
            }
            scanner.close();

            return matriz;
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
            return null;
        }
    }


    public static void imprimirMatriz(double[][] matrix) {
        for (double[] row : matrix) {
            for (double value : row) {
                System.out.printf("%8.2f ", value);
            }
            System.out.println();
        }
    }

    public static boolean isSymmetric(double[][] matrix, int rows, int cols) {
        if (rows != cols) {
            return false;
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != matrix[j][i]) {
                    return false;
                }
            }
        }

        return true;
    }

    public static int verificarValoresProprios(int cols, int k) {
        if (k > cols || k <= 0) {
            k = cols;
        }
        return k;
    }

    public static double[][] multiplicarMatrizes(double[][] A, double[][] B) {
        int linhasA = A.length;
        int colunasA = A[0].length;
        int colunasB = B[0].length;

        double[][] resultado = new double[linhasA][colunasB];

        for (int i = 0; i < linhasA; i++) {
            for (int j = 0; j < colunasB; j++) {
                for (int k = 0; k < colunasA; k++) {
                    resultado[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return resultado;
    }

    public static double[][] reconstruirMatriz(double[][] matrizP, double[][] matrizD, double[][] matrizTransposta) {
        double[][] matrizPD = multiplicarMatrizes(matrizP, matrizD);
        double[][] matrizReconstruida = multiplicarMatrizes(matrizPD, matrizTransposta);
        return matrizReconstruida;

    }

    public static double[][] MatrizColunaDosVetoresProprios(RealMatrix matrizOriginal, int k) {
        EigenDecomposition decomposition = new EigenDecomposition(matrizOriginal);
        double[] eigenvalues = decomposition.getRealEigenvalues(); // Valores próprios
        double[][] eigenvectors = decomposition.getV().getData();  // Vetores próprios (colunas da matriz P)

        // Criar um array de índices para associar os valores próprios aos vetores próprios
        int[] indices = new int[eigenvalues.length];
        for (int i = 0; i < eigenvalues.length; i++) {
            indices[i] = i;
        }

        // Ordenar os índices com base nos valores próprios (ordem decrescente)
        for (int i = 0; i < eigenvalues.length - 1; i++) {
            for (int j = i + 1; j < eigenvalues.length; j++) {
                if (eigenvalues[indices[i]] < eigenvalues[indices[j]]) {
                    int temp = indices[i];
                    indices[i] = indices[j];
                    indices[j] = temp;
                }
            }
        }

        // Construir a matriz coluna com os \(k\) maiores vetores próprios
        double[][] matrizColuna = new double[eigenvectors.length][k];
        for (int col = 0; col < k; col++) {
            int index = indices[col]; // Índice do vetor próprio correspondente
            for (int row = 0; row < eigenvectors.length; row++) {
                matrizColuna[row][col] = eigenvectors[row][index];
            }
        }

        return matrizColuna;
    }


    public static double[][] getEigenvalueDiagonal(RealMatrix matrizOriginal, int k) {
        // Calcular os valores próprios usando EigenDecomposition
        EigenDecomposition eigenDecomposition = new EigenDecomposition(matrizOriginal);
        double[] eigenvalues = eigenDecomposition.getRealEigenvalues();
        // Ordenar os valores próprios em ordem decrescente
        Double[] sortedEigenvalues = Arrays.stream(eigenvalues)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .toArray(Double[]::new);

        // Selecionar os k maiores valores próprios
        double[][] diagonalMatrix = new double[k][k];
        for (int i = 0; i < k; i++) {
            diagonalMatrix[i][i] = sortedEigenvalues[i];
        }
        return diagonalMatrix;
    }

    public static double[][] criarTransposta(double[][] matriz) {
        int linhas = matriz.length;
        int colunas = matriz[0].length;
        double[][] transposta = new double[colunas][linhas];

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                transposta[j][i] = matriz[i][j];
            }
        }

        return transposta;
    }

    public static double calcularErroAbsolutoMedio(double[][] matriz, double[][] matrizReconstruida, int colunas, int linhas) {
        double errorSum = 0.0;

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                errorSum += Math.abs(matriz[i][j] - matrizReconstruida[i][j]);
            }
        }

        return errorSum / (linhas * colunas);
    }


    public static void exportarMatriz(double[][] matriz) {
        // Nome do arquivo CSV
        String nomeArquivo = "matrizReconstruida.csv";

        // Escrever a matriz no arquivo CSV
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[i].length; j++) {
                    writer.append(String.format("%.2f", matriz[i][j]));// envia o valor com 2 casas decimais (34,23)
                    if (j < matriz[i].length - 1) {
                        writer.append(","); // Adiciona a vírgula entre os valores
                    }
                }
                writer.append("\n"); // Nova linha após cada linha da matriz
            }
            System.out.println("\nMatriz exportada com sucesso para o arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("\nErro ao escrever o arquivo CSV: " + e.getMessage());
        }
    }

    public static double[] VetorLinhaMedias(double[][] matrizA) {
        int linhas = matrizA.length; // Número de linhas
        int colunas = matrizA[0].length; // Número de colunas

        double[] vetorLinha = new double[linhas]; // Vetor para armazenar as médias de cada linha

        // Loop para calcular a soma de cada linha
        for (int i = 0; i < linhas; i++) {
            double soma = 0; // Resetar a soma para cada linha
            for (int j = 0; j < colunas; j++) {
                soma += matrizA[i][j]; // Somar os valores da linha atual
            }
            vetorLinha[i] = soma / colunas; // Calcular a média da linha
        }
        return vetorLinha;
    }

    public static double[][] CálculoDoDesvio(double[] vetorColuna, double[][] matrizA) {
        int linhas = matrizA.length;
        int colunas = matrizA[0].length;
        double[][] desvio = new double[linhas][colunas];
        for (int j = 0; j < colunas; j++) {
            for (int i = 0; i < linhas; i++) {
                desvio[i][j] = matrizA[i][j] - vetorColuna[j];
            }
        }
        return desvio;
    }

    public static double[][] normalizeEigenfaces(double[][] eigenvectors) {
        int rows = eigenvectors.length;      // Número de linhas (dimensão dos vetores)
        int cols = eigenvectors[0].length;   // Número de vetores próprios (colunas)

        double[][] normalizedEigenvectors = new double[rows][cols];

        // Iterar sobre cada vetor próprio (coluna)
        for (int j = 0; j < cols; j++) {
            double norm = 0.0;

            // 1. Calcular a norma L2 do vetor próprio (coluna j)
            for (int i = 0; i < rows; i++) {
                norm += eigenvectors[i][j] * eigenvectors[i][j]; // Soma dos quadrados dos elementos
            }
            norm = Math.sqrt(norm); // Raiz quadrada para obter a norma

            // 2. Normalizar o vetor próprio (dividir cada elemento pela norma)
            for (int i = 0; i < rows; i++) {
                normalizedEigenvectors[i][j] = eigenvectors[i][j] / norm;
            }
        }
        return normalizedEigenvectors;
    }

    public static double[][] MatrizdasCovariancias(double[][] matrizDosDesvios, double imagens) {
        int linhas = matrizDosDesvios.length;
        int colunas = matrizDosDesvios[0].length;

        // Criar a matriz transposta
        double[][] MatrizTransformada = criarTransposta(matrizDosDesvios);

        // Multiplicar a matriz transposta pela matriz original dos desvios
        double[][] matrizCovariancias = multiplicarMatrizes(matrizDosDesvios, MatrizTransformada);

        // Dividir cada elemento da matriz resultante pelo número de imagens
        for (int i = 0; i < matrizCovariancias.length; i++) {
            for (int j = 0; j < matrizCovariancias[i].length; j++) {
                matrizCovariancias[i][j] /= imagens;
            }
        }

        return matrizCovariancias;
    }

    //Metodo para criar um csv com eigenfaces normalizadas
    public static void exportarEigenfaces(double[][] matriz, int k, String nomeArquivo) {
        // Criar a matriz RealMatrix a partir da matriz original
        RealMatrix realMatrix = new Array2DRowRealMatrix(matriz);

        // Obter a matriz coluna dos vetores próprios (Eigenfaces)
        double[][] eigenfaces = Main.MatrizColunaDosVetoresProprios(realMatrix, k);

        // Normalizar as Eigenfaces
        double[][] eigenfacesNormalizadas = Main.normalizeEigenfaces(eigenfaces);

        // Exportar para um arquivo CSV
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            for (int i = 0; i < eigenfacesNormalizadas.length; i++) {
                for (int j = 0; j < eigenfacesNormalizadas[i].length; j++) {
                    writer.append(String.valueOf(eigenfacesNormalizadas[i][j]));
                    if (j < eigenfacesNormalizadas[i].length - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }
            System.out.println("Eigenfaces exportadas com sucesso para o arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao escrever o arquivo CSV: " + e.getMessage());
        }
    }

    //Metodo para Calcular vetor com a média das imagens presentes na matriz
    public static double[] calcularVetorMedia(double[][] matriz) {
        int linhas = matriz.length;    // Número de linhas (imagens)
        int colunas = matriz[0].length; // Número de colunas (pixels ou características)

        double[] vetorMedia = new double[colunas]; // Vetor para armazenar a média de cada coluna

        // Iterar sobre cada coluna para calcular a média
        for (int j = 0; j < colunas; j++) {
            double soma = 0.0;
            for (int i = 0; i < linhas; i++) {
                soma += matriz[i][j]; // Somar os valores da coluna
            }
            vetorMedia[j] = soma / linhas; // Calcular a média da coluna
        }

        return vetorMedia;
    }

//Conversão matriz de imagem para vetor

    public static double[] converterMatrizParaVetor(double[][] matriz) {
        if (matriz == null || matriz.length == 0 || matriz[0].length == 0) {
            System.out.println("Erro: Matriz inválida ou vazia.");
            return null;
        }

        int linhas = matriz.length;
        int colunas = matriz[0].length;

        // Vetor de tamanho total das entradas da matriz (linhas * colunas)
        double[] vetor = new double[linhas * colunas];
        int index = 0;

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                vetor[index++] = matriz[i][j];
            }
        }

        return vetor;
    }


    //Construção Matriz M

    public static double[][] construirMatrizDeImagens(String pasta) {
        File folder = new File(pasta);
        File[] arquivos = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhuma imagem encontrada na pasta: " + pasta);
            return null;
        }

        // Criar um array para armazenar os vetores de imagens
        int quantidadeDeImagens = arquivos.length;
        double[][] matrizM = null;
        int tamanhoDoVetor = -1;

        for (int i = 0; i < quantidadeDeImagens; i++) {
            File arquivo = arquivos[i];
            System.out.println("Lendo imagem: " + arquivo.getName());
            double[][] matriz = lerMatriz(arquivo.getAbsolutePath());

            if (matriz == null) {
                System.out.println("Erro ao ler a matriz do arquivo: " + arquivo.getName());
                return null; // Retorna null se algum erro ocorrer
            }

            double[] vetor = converterMatrizParaVetor(matriz);

            // Verificar consistência das dimensões
            if (tamanhoDoVetor == -1) {
                tamanhoDoVetor = vetor.length;
                matrizM = new double[tamanhoDoVetor][quantidadeDeImagens]; // Inicializa matrizM com as dimensões corretas
            } else if (vetor.length != tamanhoDoVetor) {
                System.out.println("Erro: Imagem " + arquivo.getName() + " tem dimensões diferentes.");
                return null; // Retorna null se as dimensões forem inconsistentes
            }

            // Adicionar o vetor à matriz M
            for (int j = 0; j < tamanhoDoVetor; j++) {
                matrizM[j][i] = vetor[j];
            }
        }

        return matrizM;
    }

    public static double[][] MatrizdasCovarianciasTeorema(double[][] matrizDosDesvios) {
        int linhas = matrizDosDesvios.length;
        int colunas = matrizDosDesvios[0].length;

        // Criar a matriz transposta
        double[][] MatrizTransformada = criarTransposta(matrizDosDesvios);

        // Multiplicar a matriz transposta pela matriz original dos desvios
        double[][] matrizCovariancias = multiplicarMatrizes(MatrizTransformada, matrizDosDesvios);

        return matrizCovariancias;
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

    public static double[] transporMatrizColuna(double[] matrizColuna) {
        int linhas = matrizColuna.length; // O número de elementos na coluna define o número de linhas
        double[] matrizLinha = new double[linhas]; // Cria um vetor para representar a linha (transposta)

        // Preenche a matriz linha com os valores da matriz coluna
        for (int i = 0; i < linhas; i++) {
            matrizLinha[i] = matrizColuna[i];
        }

        return matrizLinha;
    }

    public static double[] CalcularPesos(double[][] eigenfaces, double[] desvios) {
        // Número de eigenfaces (ou o número de componentes principais)
        int numEigenfaces = eigenfaces[0].length;

        // Vetor para armazenar os pesos
        double[] pesos = new double[numEigenfaces];

        // Calcular os pesos (produto escalar entre os desvios e cada eigenface)
        for (int i = 0; i < numEigenfaces; i++) {
            double peso = 0;
            for (int j = 0; j < desvios.length; j++) {
                peso += desvios[j] * eigenfaces[j][i];  // Produto escalar
            }
            pesos[i] = peso;
        }

        return pesos;


    }

    public static int[][] matrizDoubleParaInt(double[][] matriz) {
        int[][] intMatriz = new int[matriz.length][matriz[0].length];

        // Convert each element from double to int (truncating decimals)
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                intMatriz[i][j] = (int) matriz[i][j]; // Cast from double to int
            }
        }
        return intMatriz;
    }

    public static void matrizParaJPG(double[][] matriz) {
        // Define a sample 2D array of pixel intensities (0-255)

        int[][] intMatriz = matrizDoubleParaInt(matriz);

        // Create an instance of GreyscaleImageWriter
        GreyscaleImageWriter imageWriter = new GreyscaleImageWriter();

        // Define the output file path
        String outputPath = "ImagensReconstruidas/output_image.jpg";

        // Write the 2D array as a greyscale image
        try {
            imageWriter.writeArrayAsImage(intMatriz, outputPath);
            System.out.println("Image successfully written to " + outputPath);
        } catch (IOException e) {
            System.err.println("Failed to write the image: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid pixel values in the array: " + e.getMessage());
        }
    }

    public static double[][] vetorParaMatriz(double[] vetor) {
        // Calcula a largura e a altura assumindo que a matriz é quadrada
        int tamanho = vetor.length;
        int largura = (int) Math.sqrt(tamanho);

        // Verifica se é possível formar uma matriz quadrada
        if (largura * largura != tamanho) {
            throw new IllegalArgumentException("O vetor não representa uma matriz quadrada.");
        }

        int altura = largura; // Para uma matriz quadrada, altura = largura

        // Converte o vetor para uma matriz 2D
        double[][] matriz = new double[altura][largura];
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                matriz[i][j] = vetor[i * largura + j];
            }
        }
        return matriz;
    }

    public static void imprimirVetor(double[] vetor) {
        System.out.print("[");
        for (int i = 0; i < vetor.length; i++) {
            System.out.printf("%.2f", vetor[i]); // Formata com 2 casas decimais
            if (i < vetor.length - 1) {
                System.out.print(", "); // Adiciona vírgula entre os elementos
            }
        }
        System.out.println("]");
    }

    public static double[][] calcularEigenfaces(double[][] matrizDesvios, double[][] autovetoresATA) {
        int linhas = matrizDesvios.length; // Número de linhas em A
        int colunas = autovetoresATA[0].length; // Número de autovetores de A^T A

        // Matriz para armazenar as Eigenfaces (vetores próprios de AA^T)
        double[][] eigenfaces = new double[linhas][colunas];

        // Multiplica a matriz de desvios (A) pelos autovetores de A^T A (v_i) para obter u_i
        for (int j = 0; j < colunas; j++) { // Para cada autovetor v_i
            double[] colunaV = new double[autovetoresATA.length];
            for (int k = 0; k < colunaV.length; k++) {
                colunaV[k] = autovetoresATA[k][j]; // Extraindo a coluna j de autovetoresATA
            }

            // Calcula A * v_i para obter u_i
            double[] colunaU = multiplicarMatrizPorVetor(matrizDesvios, colunaV);


            // Insere u_i na matriz de Eigenfaces
            for (int i = 0; i < linhas; i++) {
                eigenfaces[i][j] = colunaU[i];
            }
        }

        return eigenfaces;
    }

    // Método auxiliar para multiplicar matriz por vetor
    public static double[] multiplicarMatrizPorVetor(double[][] matriz, double[] vetor) {
        int linhas = matriz.length;
        int colunas = matriz[0].length;

        if (vetor.length != colunas) {
            throw new IllegalArgumentException("O número de colunas da matriz deve ser igual ao tamanho do vetor.");
        }

        double[] resultado = new double[linhas];

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                resultado[i] += matriz[i][j] * vetor[j];
            }
        }

        return resultado;

    }
    public static double[] reescalonarParaImagem(double[] vetorReconstruido) {
        // Encontre os valores mínimo e máximo no vetor
        double min = Double.MAX_VALUE;
        double max = -Double.MAX_VALUE;

        for (double valor : vetorReconstruido) {
            if (valor < min) min = valor;
            if (valor > max) max = valor;
        }

        // Reescalonar os valores para o intervalo [0, 255]
        double[] vetorReescalonado = new double[vetorReconstruido.length];
        for (int i = 0; i < vetorReconstruido.length; i++) {
            // Fórmula para reescalonamento
            vetorReescalonado[i] = (vetorReconstruido[i] - min) / (max - min) * 255;
            vetorReescalonado[i] = Math.max(0, Math.min(255, vetorReescalonado[i]));  // Garantir que está entre 0 e 255
        }

        return vetorReescalonado;
    }

}
