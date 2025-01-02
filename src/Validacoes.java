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

    public static int IndiceDoMenor(double[] vetorDistancias) {
        double valor = vetorDistancias[0];
        int indice = 0;
        for (int i = 1; i < vetorDistancias.length; i++) {
            if (vetorDistancias[i] < valor) {
                indice = i;
            }
        }
        return indice;

    }
}
