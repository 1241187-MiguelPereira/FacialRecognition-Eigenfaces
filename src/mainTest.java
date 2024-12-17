public class mainTest {
    public static void main(String[] args) {
        testIsSymmetric();
        testCalcularErroAbsolutoMedio();
        testMultiplicarMatrizes();
    }

    public static void testIsSymmetric() {
        System.out.println("Testando isSymmetric...");

        double[][] symmetricMatrix = {
                {1.0, 2.0, 3.0},
                {2.0, 4.0, 5.0},
                {3.0, 5.0, 6.0}
        };

        double[][] nonSymmetricMatrix = {
                {1.0, 0.0, 3.0},
                {2.0, 4.0, 5.0},
                {3.0, 5.0, 6.0}
        };

        if (Main.isSymmetric(symmetricMatrix, 3, 3)) {
            System.out.println("isSymmetric passou no teste com matriz simétrica.");
        } else {
            System.out.println("isSymmetric falhou no teste com matriz simétrica.");
        }

        if (!Main.isSymmetric(nonSymmetricMatrix, 3, 3)) {
            System.out.println("isSymmetric passou no teste com matriz não simétrica.");
        } else {
            System.out.println("isSymmetric falhou no teste com matriz não simétrica.");
        }
    }
    public static void testCalcularErroAbsolutoMedio() {
        System.out.println("Testando calcularErroAbsolutoMedio...");

        double[][] original = {
                {1.0, 2.0},
                {3.0, 4.0}
        };

        double[][] reconstruida = {
                {1.1, 1.9},
                {3.1, 3.9}
        };

        double expectedError = 0.1;
        double result = Main.calcularErroAbsolutoMedio(original, reconstruida, 2, 2);

        if (Math.abs(result - expectedError) < 0.01) {
            System.out.println("calcularErroAbsolutoMedio passou no teste.");
        } else {
            System.out.println("calcularErroAbsolutoMedio falhou no teste.");
        }
    }
    public static void testMultiplicarMatrizes() {
        System.out.println("Testando multiplicarMatrizes...");

        double[][] A = {
                {1, 2},
                {3, 4}
        };

        double[][] B = {
                {2, 0},
                {1, 2}
        };

        double[][] expected = {
                {4, 4},
                {10, 8}
        };

        double[][] result = Main.multiplicarMatrizes(A, B);

        if (arraysIguais(expected, result)) {
            System.out.println("multiplicarMatrizes passou no teste.");
        } else {
            System.out.println("multiplicarMatrizes falhou no teste.");
        }
    }

    public static boolean arraysIguais(double[][] a, double[][] b) {
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i].length != b[i].length) {
                return false;
            }
            for (int j = 0; j < a[i].length; j++) {
                if (a[i][j] != b[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
public static double[][] CálculoDoDesvio(double [] vetorColuna,double[][] matrizA){
        int linhas=matrizA.length;
        int colunas=matrizA[0].length;
        double[][] desvio = new double[linhas][colunas];
        for (int j = 0; j < colunas; j++) {
            for (int i = 0; i < linhas; i++) {
                desvio[i][j]=matrizA[i][j]-vetorColuna[j];
            }
        }return desvio;
}
}

