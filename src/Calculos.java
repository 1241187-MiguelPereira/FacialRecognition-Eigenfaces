import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.Arrays;
import java.util.Comparator;

public class Calculos {
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
                desvio[i][j] = matrizA[i][j] - vetorColuna[i];
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






    public static double[][] MatrizdasCovarianciasTeorema(double[][] matrizDosDesvios) {
        int linhas = matrizDosDesvios.length;
        int colunas = matrizDosDesvios[0].length;

        // Criar a matriz transposta
        double[][] MatrizTransformada = criarTransposta(matrizDosDesvios);

        // Multiplicar a matriz transposta pela matriz original dos desvios
        double[][] matrizCovariancias = multiplicarMatrizes(MatrizTransformada, matrizDosDesvios);

        return matrizCovariancias;
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

    public static double[][] calcularPesos(double[][] A, double[][] eigenfaces, int k) {
        int numImagens = A[0].length; // Número de imagens
        double[][] pesos = new double[k][numImagens]; // Matriz para armazenar os pesos de todas as imagens

        for (int i = 0; i < numImagens; i++) { // Para cada imagem
            for (int p = 0; p < k; p++) { // Para cada eigenface
                double soma = 0;
                for (int j = 0; j < A.length; j++) { // Para cada pixel
                    soma += eigenfaces[j][p] * A[j][i];
                }
                pesos[p][i] = soma;
            }
        }

        return pesos;
    }

    public static double[][] reconstruirImagens(double[] media, double[][] eigenfaces, double[][] pesos, int k, int coluna) {
        // Se `coluna` for nulo, reconstruir todas as imagens
        if (coluna == -1) {
            int numImagens = pesos[0].length; // Número de imagens
            double[][] imagensReconstruidas = new double[media.length][numImagens]; // Matriz para armazenar todas as imagens reconstruídas

            for (int i = 0; i < numImagens; i++) {
                // Inicializar com a média
                for (int j = 0; j < media.length; j++) {
                    imagensReconstruidas[j][i] = media[j];
                }

                // Adicionar contribuição das eigenfaces ponderadas pelos pesos
                for (int p = 0; p < k; p++) {
                    for (int j = 0; j < media.length; j++) {
                        imagensReconstruidas[j][i] += eigenfaces[j][p] * pesos[p][i];
                    }
                }
            }
            return imagensReconstruidas;

        } else {
            // Reconstruir apenas uma imagem
            double[][] imagemReconstruida = new double[media.length][1]; // Matriz para armazenar uma única imagem reconstruída

            // Inicializar com a média
            for (int j = 0; j < media.length; j++) {
                imagemReconstruida[j][0] = media[j];
            }

            // Adicionar contribuição das eigenfaces ponderadas pelos pesos
            for (int p = 0; p < k; p++) {
                for (int j = 0; j < media.length; j++) {
                    imagemReconstruida[j][0] += eigenfaces[j][p] * pesos[p][coluna];
                }
            }
            return imagemReconstruida;
        }
    }

//--------FUNCIONALIDADE 3 EXCLUSIVAMENTE------------//
    public static double[] calcularDistanciaEuclidiana(double[] omegaNova, double[][] omegaBase) {
        int numVetores = omegaBase.length; // Número de vetores na base
        double[] distancias = new double[numVetores]; // Vetor para armazenar as distâncias

        for (int i = 0; i < numVetores; i++) {
            double soma = 0.0;
            for (int j = 0; j < omegaNova.length; j++) { // Percorre cada dimensão
                soma += Math.pow(omegaNova[j] - omegaBase[i][j], 2);
            }
            distancias[i] = Math.sqrt(soma); // Calcula a distância euclidiana
        }

        return distancias;
    }
    public static double[] CalculoDoDesvio(double[] vetorColuna, double[] vetorAlvo) {
        int tamanho = vetorColuna.length;
        double[] desvio = new double[tamanho];

        for (int i = 0; i < tamanho; i++) {
            desvio[i] = vetorAlvo[i] - vetorColuna[i];
        }

        return desvio;
    }
    public static double[] calcularPesosImagemAlvo(double[] imagemAlvo, double[][] eigenfaces, int k) {
        // Criamos uma array para armazenar os pesos da imagem alvo
        double[] pesos = new double[k];

        // Calculamos os pesos (projeção da imagem alvo nas k eigenfaces)
        for (int p = 0; p < k; p++) { // Para cada eigenface
            double soma = 0;
            for (int j = 0; j < imagemAlvo.length; j++) { // Para cada pixel da imagem alvo
                soma += eigenfaces[j][p] * imagemAlvo[j];
            }
            pesos[p] = soma;
        }

        return pesos;
    }

}
