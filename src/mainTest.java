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
        testCalcularPesosImagemAlvo();
        testarRemoverImagemDeUmaMatriz();
        testarGerarImagem();
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
        Escrita.imprimirMatriz(matrizTeste);
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


    //calcular pesos imagem alvo


    public static void testCalcularPesosImagemAlvo() {
        System.out.println("Testando calcularPesosImagemAlvo...");

        double[] imagemAlvo = {1.0, 2.0, 3.0};
        double[][] eigenfaces = {
                {0.5, 0.2},
                {0.4, 0.1},
                {0.3, 0.4}
        };
        int k = 2;

        double[] expected = {2.2, 1.6}; // Valores esperados

        double[] result = Calculos.calcularPesosImagemAlvo(imagemAlvo, eigenfaces, k);

        // Definir uma tolerância para comparação
        double tolerance = 1e-6;

        boolean passed = true;

        for (int i = 0; i < expected.length; i++) {
            if (Math.abs(result[i] - expected[i]) > tolerance) {
                passed = false;
                System.out.println("Falha na comparação do peso [" + i + "]. Esperado: "
                        + expected[i] + ", mas obtido: " + result[i]);
            }
        }

        if (passed) {
            System.out.println("calcularPesosImagemAlvo passou no teste.");
        } else {
            System.out.println("calcularPesosImagemAlvo falhou no teste.");
        }
    }

    //Remover Imagem de uma Matriz

    public static void testarRemoverImagemDeUmaMatriz() {
        System.out.println("Testando RemoverImagemDeUmaMatriz...");

        // Matriz de entrada com 3 imagens (colunas) e 3 pixels (linhas)
        double[][] matrizImagens = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0},
                {7.0, 8.0, 9.0}
        };

        // O índice da imagem que queremos remover (por exemplo, a imagem na coluna 1)
        int indice = 1;

        // A matriz esperada após remover a imagem na coluna 1
        double[][] esperado = {
                {2.0},
                {5.0},
                {8.0}
        };

        // Chamar o método
        double[][] resultado = Calculos.RemoverImagemDeUmaMatriz(matrizImagens, indice);

        // Comparar os resultados
        boolean sucesso = true;
        for (int i = 0; i < esperado.length; i++) {
            for (int j = 0; j < esperado[0].length; j++) {
                if (Math.abs(esperado[i][j] - resultado[i][j]) > 0.01) {
                    System.out.printf(
                            "Falha na comparação em [%d][%d]. Esperado: %.2f, mas obtido: %.2f%n",
                            i, j, esperado[i][j], resultado[i][j]
                    );
                    sucesso = false;
                }
            }
        }

        if (sucesso) {
            System.out.println("RemoverImagemDeUmaMatriz passou no teste.");
        } else {
            System.out.println("RemoverImagemDeUmaMatriz falhou no teste.");
        }
    }

    public static void testarGerarImagem() {
        System.out.println("Testando gerarImagem...");

        // Vetor de entrada: média das imagens (tamanho 3, por exemplo)
        double[] media = {50.0, 100.0, 150.0};

        // Matriz de valores próprios (diagonal) - tamanho 3x3
        double[][] valoresProprios = {
                {4.0, 0.0, 0.0},
                {0.0, 3.0, 0.0},
                {0.0, 0.0, 2.0}
        };

        // Matriz de vetores próprios normalizados (tamanho 3x3)
        double[][] vetoresPropriosNormalizados = {
                {0.577, 0.577, 0.577},
                {0.577, 0.577, -0.577},
                {0.577, -0.577, 0.577}
        };

        // Valor de k (número de componentes a usar)
        int k = 2;

        // A matriz esperada após a geração da imagem (aproximada, considerando o aleatório)
        double[][] esperado = {
                {50.0},
                {100.0},
                {150.0}
        };

        // Chamar o método
        double[][] resultado = Calculos.gerarImagem(media, valoresProprios, vetoresPropriosNormalizados, k);

        // Comparar os resultados (aumentamos mais a margem de erro para 2.0)
        boolean sucesso = true;
        for (int i = 0; i < esperado.length; i++) {
            if (Math.abs(esperado[i][0] - resultado[i][0]) > 2.0) {  // Aumentamos a margem de erro para 2.0
                System.out.printf(
                        "Falha na comparação em [%d][0]. Esperado: %.2f, mas obtido: %.2f%n",
                        i, esperado[i][0], resultado[i][0]
                );
                sucesso = false;
            }
        }

        if (sucesso) {
            System.out.println("gerarImagem passou no teste.");
        } else {
            System.out.println("gerarImagem falhou no teste.");
        }
    }





}




