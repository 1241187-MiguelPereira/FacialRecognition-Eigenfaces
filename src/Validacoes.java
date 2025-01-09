public class Validacoes {
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

    public static int IndicedoMenor(double[] distancias) {
        double menorValor = distancias[0];
        int menorIndice = 0;
        boolean menorIgual = false;

        for(int i = 1; i < distancias.length; i++) {
            if(distancias[i] <= menorValor) {
                if(distancias[i] == menorValor) {
                    menorIgual = true;
                } else {
                    menorIgual = false;
                    menorValor = distancias[i];
                    menorIndice = i;
                }
            }
        }

        if(menorIgual) {
            return -1;
        } else {
            return menorIndice;
        }
    }
    public static double findMin(double[] array) {
        double min = Double.MAX_VALUE;
        for (double val : array) {
            if (val < min) {
                min = val;
            }
        }
        return min;
    }
    public static double findMax(double[] array) {
        double max = Double.MIN_VALUE;
        for (double val : array) {
            if (val > max) {
                max = val;
            }
        }
        return max;
    }public static int[] compararMatrizes(double[][] matriz1, double[][] matriz2) {
        // Verificar se as matrizes têm dimensões compatíveis
        if (matriz1.length != matriz2.length || matriz1[0].length != matriz2[0].length) {
            throw new IllegalArgumentException("As matrizes devem ter as mesmas dimensões.");
        }

        int rows = matriz1.length; // Número de linhas
        int cols = matriz1[0].length; // Número de colunas

        // Criar um array para armazenar os índices das colunas diferentes
        int[] colunasDiferentes = new int[cols];
        int contador = 0; // Contador para o número de colunas diferentes encontradas

        // Comparar cada coluna
        for (int col = 0; col < cols; col++) {
            boolean diferente = false; // Flag para identificar se a coluna é diferente

            for (int row = 0; row < rows; row++) {
                if (matriz1[row][col] != matriz2[row][col]) {
                    diferente = true;
                    break; // Parar de verificar a coluna, pois já sabemos que é diferente
                }
            }

            // Se a coluna for diferente, armazenar o índice no array
            if (diferente) {
                colunasDiferentes[contador] = col;
                contador++;
            }
        }

        // Criar um novo array com o tamanho exato das colunas diferentes
        int[] resultado = new int[contador];
        for (int i = 0; i < contador; i++) {
            resultado[i] = colunasDiferentes[i];
        }

        return resultado; // Retornar os índices das colunas diferentes
    }
    // Método auxiliar para verificar se um índice está no array de índices
    public static boolean contemIndice(int[] indices, int coluna) {
        for (int indice : indices) {
            if (indice == coluna) {
                return true;
            }
        }
        return false;
    }

}
