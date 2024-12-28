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



}
