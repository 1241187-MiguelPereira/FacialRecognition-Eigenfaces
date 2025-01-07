import org.apache.commons.math3.linear.*;

public class mainTest {
    public static void main(String[] args) {
        testIsSymmetric();
        testCalcularErroAbsolutoMedio();
        testMultiplicarMatrizes();
        testImprimirMatriz();
        testVetorLinhaMedias();
        testCalculoDoDesvio();
        testMatrizDasCovarianciasTeorema();
        testMatrizColunaDosVetoresProprios();
        testCalcularEigenfaces();
        testNormalizeEigenfaces();
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

        if (Validacoes.isSymmetric(symmetricMatrix, 3, 3)) {
            System.out.println("isSymmetric passou no teste com matriz simétrica.");
        } else {
            System.out.println("isSymmetric falhou no teste com matriz simétrica.");
        }

        if (!Validacoes.isSymmetric(nonSymmetricMatrix, 3, 3)) {
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
        double result = Calculos.calcularErroAbsolutoMedio(original, reconstruida, 2, 2);

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

        double[][] result = Calculos.multiplicarMatrizes(A, B);

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

    public static void testImprimirMatriz() {

        // Criando uma matriz de teste
        double[][] matrizTeste = {
                {1.1, 2.2, 3.3},
                {4.4, 5.5, 6.6},
                {7.7, 8.8, 9.9}
        };

        // Chamando o método de impressão da matriz
        System.out.println("Imprimindo a matriz de teste:");
        UI.imprimirMatriz(matrizTeste);
    }

    public static void testVetorLinhaMedias() {
        System.out.println("Testando VetorLinhaMedias...");

        double[][] matrizImagens = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0},
                {7.0, 8.0, 9.0}
        };
        double[] expected = {4.0, 5.0, 6.0};
        double[] result = Calculos.VetorLinhaMedias(matrizImagens);

        if (java.util.Arrays.equals(result, expected)) {
            System.out.println("VetorLinhaMedias passou no teste.");
        } else {
            System.out.println("VetorLinhaMedias falhou no teste.");
        }
    }

    public static void testCalculoDoDesvio() {
        System.out.println("Testando CálculoDoDesvio...");

        double[] vetorMedias = {4.0, 5.0, 6.0};
        double[][] matrizImagens = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0},
                {7.0, 8.0, 9.0}
        };
        double[][] expected = {
                {-3.0, -3.0, -3.0},
                {0.0, 0.0, 0.0},
                {3.0, 3.0, 3.0}
        };
        double[][] result = Calculos.CálculoDoDesvio(vetorMedias, matrizImagens);

        if (java.util.Arrays.deepEquals(result, expected)) {
            System.out.println("CálculoDoDesvio passou no teste.");
        } else {
            System.out.println("CálculoDoDesvio falhou no teste.");
        }
    }

    public static void testMatrizDasCovarianciasTeorema() {
        System.out.println("Testando MatrizdasCovarianciasTeorema...");

        double[][] matrizA = {
                {-3.0, -3.0, -3.0},
                {0.0, 0.0, 0.0},
                {3.0, 3.0, 3.0}
        };
        double[][] expected = {
                {18.0, 18.0, 18.0},
                {18.0, 18.0, 18.0},
                {18.0, 18.0, 18.0}
        };
        double[][] result = Calculos.MatrizdasCovarianciasTeorema(matrizA);

        if (java.util.Arrays.deepEquals(result, expected)) {
            System.out.println("MatrizdasCovarianciasTeorema passou no teste.");
        } else {
            System.out.println("MatrizdasCovarianciasTeorema falhou no teste.");
        }
    }

    public static void testMatrizColunaDosVetoresProprios() {
        System.out.println("Testando MatrizColunaDosVetoresProprios...");

        RealMatrix matrizCovReal = new Array2DRowRealMatrix(new double[][]{
                {18.0, 18.0, 18.0},
                {18.0, 18.0, 18.0},
                {18.0, 18.0, 18.0}
        });
        int x = 2;

        double[][] result = Calculos.MatrizColunaDosVetoresProprios(matrizCovReal, x);

        if (result != null && result[0].length == x) {
            System.out.println("MatrizColunaDosVetoresProprios passou no teste.");
        } else {
            System.out.println("MatrizColunaDosVetoresProprios falhou no teste.");
        }
    }

    public static void testCalcularEigenfaces() {
        System.out.println("Testando calcularEigenfaces...");

        double[][] matrizA = {
                {-3.0, -3.0, -3.0},
                {0.0, 0.0, 0.0},
                {3.0, 3.0, 3.0}
        };
        double[][] matrizVetores = {
                {1.0, 0.0},
                {0.0, 1.0},
                {0.0, 0.0}
        };

        double[][] result = Calculos.calcularEigenfaces(matrizA, matrizVetores);

        if (result != null && result[0].length == matrizVetores[0].length) {
            System.out.println("calcularEigenfaces passou no teste.");
        } else {
            System.out.println("calcularEigenfaces falhou no teste.");
        }
    }

    public static void testNormalizeEigenfaces() {
        System.out.println("Testando normalizeEigenfaces...");

        double[][] matrizVetores = {
                {3.0, 4.0},
                {0.0, 0.0},
                {6.0, 8.0}
        };
        double[][] expected = {
                {0.4472, 0.4472},
                {0.0, 0.0},
                {0.8944, 0.8944}
        };

        double[][] result = Calculos.normalizeEigenfaces(matrizVetores);

        // Definir uma tolerância numérica para comparação
        double tolerance = 1e-4; // Menor precisão devido à comparação dos valores de ponto flutuante

        boolean passed = true;

        // Comparar as duas matrizes com tolerância
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[i].length; j++) {
                if (Math.abs(result[i][j] - expected[i][j]) > tolerance) {
                    passed = false;
                    System.out.println("Falha na comparação de [" + i + "][" + j + "]. Esperado: "
                            + expected[i][j] + ", mas obtido: " + result[i][j]);
                }
            }
        }

        if (passed) {
            System.out.println("normalizeEigenfaces passou no teste.");
        } else {
            System.out.println("normalizeEigenfaces falhou no teste.");
        }
    }



    }




