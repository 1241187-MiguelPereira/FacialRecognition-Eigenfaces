import org.apache.commons.math3.linear.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class main {
    public static void main(String[] args) {

        String filePath = "exampleInputFunc1.csv";

        double[][] matriz = lerMatriz(filePath);

        double[][] transposta = criarTransposta(matriz);

        if (matriz != null) {
            Boolean simetrica = isSymmetric(matriz, matriz.length, matriz[0].length);
            imprimirMatriz(matriz);
            System.out.println("simetrica = " + simetrica);

        }

        imprimirMatriz(transposta);
    }

    public static double[][] lerMatriz(String filePath) {
        double[][] matriz = null;
        //mudar o buffered para Reader
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // First pass: Count rows and columns
            int rows = 0;
            int cols = 0;
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    rows++;
                    if (cols == 0) {
                        cols = line.split(",").length; // Determine number of columns from first line
                    }
                }
            }

            // Initialize the matrix with the correct size
            matriz = new double[rows][cols];

            // Second pass: Fill the matrix
            try (BufferedReader br2 = new BufferedReader(new FileReader(filePath))) {
                int row = 0;
                while ((line = br2.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        String[] values = line.split(",");
                        for (int col = 0; col < cols; col++) {
                            matriz[row][col] = Double.parseDouble(values[col].trim());
                        }
                        row++;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return null; // Return null in case of an error
        }

        return matriz;
    }

    // Method to print a matrix
    //Acho que nao precisamos deste metodo
    public static void imprimirMatriz(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%8.2f ", matrix[i][j]); // Formata cada elemento com largura fixa de 8 e 2 casas decimais
            }
            System.out.println(); // Quebra de linha após cada linha da matriz
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
        if(k >= cols || k <= 0){
            k = cols;
        }
        return k;
    }
    public static double[][] getEigenvalueDiagonal(double[][] matrix, int k) {

        // Criar uma matriz RealMatrix a partir do array bidimensional
        RealMatrix realMatrix = new Array2DRowRealMatrix(matrix);

        // Calcular os valores próprios usando EigenDecomposition
        EigenDecomposition eigenDecomposition = new EigenDecomposition(realMatrix);
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
    public static double[][] MatrizColunaDosVetoresProprios(RealMatrix matriz, int k) {
        EigenDecomposition decomposition = new EigenDecomposition(matriz);
        double[] eigenvectors = decomposition.getRealEigenvalues();
        Double[] sortedEigenvectors = Arrays.stream(eigenvectors)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .toArray(Double[]::new);
        double[][] MatrizColuna = new double[k][1];
        for (int i = 0; i < k; i++) {
            MatrizColuna[i][0] = sortedEigenvectors[i];
        }return MatrizColuna;

    }

    // Método que cria a transposta de uma matriz
    public static double[][] criarTransposta(double[][] matriz) {
        //vale mais a pena tranferir como parametro, inves de fazer isto outra vez
        int linhas = matriz.length;
        int colunas = matriz[0].length;

        // Cria uma nova matriz com dimensões invertidas
        double[][] transposta = new double[colunas][linhas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                transposta[j][i] = matriz[i][j];
            }
        }
        return transposta;
    }



}
