public class main {
    public static void main(String[] args) {

        public static boolean isSymmetric ( double[][] matrix, int colunas, int linhas){

            // Verifica se a matriz é quadrada
            if (rows != cols) {
                return false; // Apenas matrizes quadradas podem ser simétricas
            }

            // Verifica a simetria: A[i][j] deve ser igual a A[j][i]
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (matrix[i][j] != matrix[j][i]) {
                        return false; // Não é simétrica
                    }
                }
            }

            return true; // É simétrica
        }

    }
}
