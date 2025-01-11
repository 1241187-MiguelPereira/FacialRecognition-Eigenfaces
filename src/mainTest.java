import org.apache.commons.math3.linear.*;
import java.io.File;

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
        testCriarTransposta();
        testGetEigenvalueDiagonal();
        testMultiplicarMatrizPorVetor();
        testCalcularPesos();
        testReconstruirImagens();
        testCalcularDistanciaEuclidiana();
        testConverterMatrizParaVetor();
        testEscreverArraycomoImagem();
        testArrayparaMatriz();
        testVerificarValoresProprios();
        testIndicedoMenor();




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

    public static void testCriarTransposta() {
        System.out.println("Testando criarTransposta...");

        // Caso 1: Matriz quadrada
        double[][] matrizQuadrada = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0},
                {7.0, 8.0, 9.0}
        };
        double[][] esperadoQuadrada = {
                {1.0, 4.0, 7.0},
                {2.0, 5.0, 8.0},
                {3.0, 6.0, 9.0}
        };
        double[][] resultadoQuadrada = Calculos.criarTransposta(matrizQuadrada);
        boolean passouQuadrada = true;
        for (int i = 0; i < esperadoQuadrada.length; i++) {
            for (int j = 0; j < esperadoQuadrada[i].length; j++) {
                if (resultadoQuadrada[i][j] != esperadoQuadrada[i][j]) {
                    passouQuadrada = false;
                    break;
                }
            }
        }
        if (passouQuadrada) {
            System.out.println("criarTransposta passou no teste com matriz quadrada.");
        } else {
            System.out.println("criarTransposta falhou no teste com matriz quadrada.");
        }

        // Caso 2: Matriz retangular (mais linhas que colunas)
        double[][] matrizMaisLinhas = {
                {1.0, 2.0},
                {3.0, 4.0},
                {5.0, 6.0}
        };
        double[][] esperadoMaisLinhas = {
                {1.0, 3.0, 5.0},
                {2.0, 4.0, 6.0}
        };
        double[][] resultadoMaisLinhas = Calculos.criarTransposta(matrizMaisLinhas);
        boolean passouMaisLinhas = true;
        for (int i = 0; i < esperadoMaisLinhas.length; i++) {
            for (int j = 0; j < esperadoMaisLinhas[i].length; j++) {
                if (resultadoMaisLinhas[i][j] != esperadoMaisLinhas[i][j]) {
                    passouMaisLinhas = false;
                    break;
                }
            }
        }
        if (passouMaisLinhas) {
            System.out.println("criarTransposta passou no teste com matriz retangular (mais linhas).");
        } else {
            System.out.println("criarTransposta falhou no teste com matriz retangular (mais linhas).");
        }

        // Caso 3: Matriz vazia
        double[][] matrizVazia = new double[0][0];
        double[][] resultadoVazia = Calculos.criarTransposta(matrizVazia);
        boolean passouVazia = resultadoVazia.length == 0;
        if (passouVazia) {
            System.out.println("criarTransposta passou no teste com matriz vazia.");
        } else {
            System.out.println("criarTransposta falhou no teste com matriz vazia.");
        }
    }

    public static void testGetEigenvalueDiagonal() {
        System.out.println("Testando getEigenvalueDiagonal...");

        // Caso 1: Matriz 2x2 com valores próprios simples
        RealMatrix matriz2x2 = MatrixUtils.createRealMatrix(new double[][]{
                {2.0, 0.0},
                {0.0, 3.0}
        });
        double[][] esperado2x2 = {
                {3.0, 0.0},
                {0.0, 2.0}
        };
        double[][] resultado2x2 = Calculos.getEigenvalueDiagonal(matriz2x2, 2);
        boolean passou2x2 = true;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (resultado2x2[i][j] != esperado2x2[i][j]) {
                    passou2x2 = false;
                    break;
                }
            }
        }
        if (passou2x2) {
            System.out.println("getEigenvalueDiagonal passou no teste com matriz 2x2.");
        } else {
            System.out.println("getEigenvalueDiagonal falhou no teste com matriz 2x2.");
        }

        // Caso 2: Matriz 3x3 com valores próprios conhecidos
        RealMatrix matriz3x3 = MatrixUtils.createRealMatrix(new double[][]{
                {6.0, 2.0, 1.0},
                {2.0, 3.0, 1.0},
                {1.0, 1.0, 1.0}
        });
        double[][] esperado3x3 = {
                {7.0, 0.0, 0.0},
                {0.0, 2.0, 0.0},
                {0.0, 0.0, 1.0}
        };
        double[][] resultado3x3 = Calculos.getEigenvalueDiagonal(matriz3x3, 3);
        boolean passou3x3 = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (resultado3x3[i][j] != esperado3x3[i][j]) {
                    passou3x3 = false;
                    break;
                }
            }
        }
        if (passou3x3) {
            System.out.println("getEigenvalueDiagonal passou no teste com matriz 3x3.");
        } else {
            System.out.println("getEigenvalueDiagonal falhou no teste com matriz 3x3.");
        }

        // Caso 3: Matriz identidade 3x3
        RealMatrix matrizIdentidade = MatrixUtils.createRealIdentityMatrix(3);
        double[][] esperadoIdentidade = {
                {1.0, 0.0, 0.0},
                {0.0, 1.0, 0.0},
                {0.0, 0.0, 1.0}
        };
        double[][] resultadoIdentidade = Calculos.getEigenvalueDiagonal(matrizIdentidade, 3);
        boolean passouIdentidade = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (resultadoIdentidade[i][j] != esperadoIdentidade[i][j]) {
                    passouIdentidade = false;
                    break;
                }
            }
        }
        if (passouIdentidade) {
            System.out.println("getEigenvalueDiagonal passou no teste com matriz identidade.");
        } else {
            System.out.println("getEigenvalueDiagonal falhou no teste com matriz identidade.");
        }
    }

    public static void testMultiplicarMatrizPorVetor() {
        System.out.println("Testando multiplicarMatrizPorVetor...");

        // Caso 1: Matriz 2x2 e vetor de tamanho 2
        double[][] matriz2x2 = {
                {1.0, 2.0},
                {3.0, 4.0}
        };
        double[] vetor2 = {5.0, 6.0};
        double[] esperado2x2 = {17.0, 39.0};
        double[] resultado2x2 = Calculos.multiplicarMatrizPorVetor(matriz2x2, vetor2);
        boolean passou2x2 = true;
        for (int i = 0; i < esperado2x2.length; i++) {
            if (resultado2x2[i] != esperado2x2[i]) {
                passou2x2 = false;
                break;
            }
        }
        if (passou2x2) {
            System.out.println("multiplicarMatrizPorVetor passou no teste com matriz 2x2 e vetor de tamanho 2.");
        } else {
            System.out.println("multiplicarMatrizPorVetor falhou no teste com matriz 2x2 e vetor de tamanho 2.");
        }

        // Caso 2: Matriz 3x3 e vetor de tamanho 3
        double[][] matriz3x3 = {
                {1.0, 0.0, 2.0},
                {0.0, 1.0, 3.0},
                {4.0, 5.0, 6.0}
        };
        double[] vetor3 = {1.0, 2.0, 3.0};
        double[] esperado3x3 = {7.0, 11.0, 32.0};
        double[] resultado3x3 = Calculos.multiplicarMatrizPorVetor(matriz3x3, vetor3);
        boolean passou3x3 = true;
        for (int i = 0; i < esperado3x3.length; i++) {
            if (resultado3x3[i] != esperado3x3[i]) {
                passou3x3 = false;
                break;
            }
        }
        if (passou3x3) {
            System.out.println("multiplicarMatrizPorVetor passou no teste com matriz 3x3 e vetor de tamanho 3.");
        } else {
            System.out.println("multiplicarMatrizPorVetor falhou no teste com matriz 3x3 e vetor de tamanho 3.");
        }

        // Caso 3: Matriz 1x2 e vetor de tamanho 2
        double[][] matriz1x2 = {
                {7.0, 8.0}
        };
        double[] vetor1x2 = {9.0, 10.0};
        double[] esperado1x2 = {151.0};
        double[] resultado1x2 = Calculos.multiplicarMatrizPorVetor(matriz1x2, vetor1x2);
        boolean passou1x2 = true;
        for (int i = 0; i < esperado1x2.length; i++) {
            if (resultado1x2[i] != esperado1x2[i]) {
                passou1x2 = false;
                break;
            }
        }
        if (passou1x2) {
            System.out.println("multiplicarMatrizPorVetor passou no teste com matriz 1x2 e vetor de tamanho 2.");
        } else {
            System.out.println("multiplicarMatrizPorVetor falhou no teste com matriz 1x2 e vetor de tamanho 2.");
        }

        // Caso 4: Exceção para matriz 2x2 e vetor de tamanho 3
        double[][] matriz2x2Excecao = {
                {1.0, 2.0},
                {3.0, 4.0}
        };
        double[] vetorExcecao = {1.0, 2.0, 3.0};
        try {
            Calculos.multiplicarMatrizPorVetor(matriz2x2Excecao, vetorExcecao);
            System.out.println("multiplicarMatrizPorVetor falhou no teste de exceção.");
        } catch (IllegalArgumentException e) {
            System.out.println("multiplicarMatrizPorVetor passou no teste de exceção.");
        }
    }

    public static void testCalcularPesos() {
        System.out.println("Testando calcularPesos...");

        // Caso 1: Matriz simples 2x2 com k = 1
        double[][] A1 = {
                {1.0, 2.0},
                {3.0, 4.0}
        };
        double[][] eigenfaces1 = {
                {0.5},
                {0.5}
        };
        int k1 = 1;
        double[][] esperado1 = {
                {2.0, 3.0}
        };
        double[][] resultado1 = Calculos.calcularPesos(A1, eigenfaces1, k1);
        boolean passou1 = true;
        for (int i = 0; i < k1; i++) {
            for (int j = 0; j < A1[0].length; j++) {
                if (resultado1[i][j] != esperado1[i][j]) {
                    passou1 = false;
                    break;
                }
            }
        }
        if (passou1) {
            System.out.println("calcularPesos passou no teste com matriz simples 2x2 e k=1.");
        } else {
            System.out.println("calcularPesos falhou no teste com matriz simples 2x2 e k=1.");
        }

        // Caso 2: Matriz 3x3 com k = 2
        double[][] A2 = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0},
                {7.0, 8.0, 9.0}
        };
        double[][] eigenfaces2 = {
                {0.1, 0.2},
                {0.3, 0.4},
                {0.5, 0.6}
        };
        int k2 = 2;
        double[][] esperado2 = {
                {3.8, 4.6, 5.4},
                {6.2, 7.4, 8.6}
        };
        double[][] resultado2 = Calculos.calcularPesos(A2, eigenfaces2, k2);
        boolean passou2 = true;
        for (int i = 0; i < k2; i++) {
            for (int j = 0; j < A2[0].length; j++) {
                if (resultado2[i][j] != esperado2[i][j]) {
                    passou2 = false;
                    break;
                }
            }
        }
        if (passou2) {
            System.out.println("calcularPesos passou no teste com matriz 3x3 e k=2.");
        } else {
            System.out.println("calcularPesos falhou no teste com matriz 3x3 e k=2.");
        }

        // Caso 3: Matriz identidade com eigenfaces identidade
        double[][] A3 = {
                {1.0, 0.0, 0.0},
                {0.0, 1.0, 0.0},
                {0.0, 0.0, 1.0}
        };
        double[][] eigenfaces3 = {
                {1.0, 0.0},
                {0.0, 1.0},
                {0.0, 0.0}
        };
        int k3 = 2;
        double[][] esperado3 = {
                {1.0, 0.0, 0.0},
                {0.0, 1.0, 0.0}
        };
        double[][] resultado3 = Calculos.calcularPesos(A3, eigenfaces3, k3);
        boolean passou3 = true;
        for (int i = 0; i < k3; i++) {
            for (int j = 0; j < A3[0].length; j++) {
                if (resultado3[i][j] != esperado3[i][j]) {
                    passou3 = false;
                    break;
                }
            }
        }
        if (passou3) {
            System.out.println("calcularPesos passou no teste com matriz identidade e eigenfaces identidade.");
        } else {
            System.out.println("calcularPesos falhou no teste com matriz identidade e eigenfaces identidade.");
        }
    }

    public static void testReconstruirImagens() {
        System.out.println("Testando reconstruirImagens...");

        // Caso 1: Reconstruir uma única imagem
        double[] media1 = {1.0, 2.0};
        double[][] eigenfaces1 = {
                {0.5, 0.3},
                {0.4, 0.2}
        };
        double[][] pesos1 = {
                {2.0},
                {1.0}
        };
        int k1 = 2;
        int coluna1 = 0;
        double[][] esperado1 = {
                {2.1},
                {2.8}
        };
        double[][] resultado1 = Calculos.reconstruirImagens(media1, eigenfaces1, pesos1, k1, coluna1);
        boolean passou1 = true;
        for (int i = 0; i < media1.length; i++) {
            if (resultado1[i][0] != esperado1[i][0]) {
                passou1 = false;
                break;
            }
        }
        if (passou1) {
            System.out.println("reconstruirImagens passou no teste de reconstrução de uma única imagem.");
        } else {
            System.out.println("reconstruirImagens falhou no teste de reconstrução de uma única imagem.");
        }

        // Caso 2: Reconstruir todas as imagens
        double[] media2 = {1.0, 2.0, 3.0};
        double[][] eigenfaces2 = {
                {0.5, 0.1},
                {0.4, 0.2},
                {0.3, 0.3}
        };
        double[][] pesos2 = {
                {2.0, 3.0},
                {1.0, 4.0}
        };
        int k2 = 2;
        int coluna2 = -1;
        double[][] esperado2 = {
                {2.1, 2.8},
                {2.8, 4.2},
                {4.1, 6.2}
        };
        double[][] resultado2 = Calculos.reconstruirImagens(media2, eigenfaces2, pesos2, k2, coluna2);
        boolean passou2 = true;
        for (int i = 0; i < media2.length; i++) {
            for (int j = 0; j < pesos2[0].length; j++) {
                if (resultado2[i][j] != esperado2[i][j]) {
                    passou2 = false;
                    break;
                }
            }
        }
        if (passou2) {
            System.out.println("reconstruirImagens passou no teste de reconstrução de todas as imagens.");
        } else {
            System.out.println("reconstruirImagens falhou no teste de reconstrução de todas as imagens.");
        }
    }

    public static void testCalcularDistanciaEuclidiana() {
        System.out.println("Testando calcularDistanciaEuclidiana...");

        // Caso 1: Base simples com 2 vetores, vetor omegaNova também 2D
        double[] omegaNova1 = {1.0, 2.0};
        double[][] omegaBase1 = {
                {1.0, 3.0},
                {2.0, 4.0}
        };
        double[] esperado1 = {0.0, Math.sqrt(8.0)};
        double[] resultado1 = Calculos.calcularDistanciaEuclidiana(omegaNova1, omegaBase1);
        boolean passou1 = true;
        for (int i = 0; i < esperado1.length; i++) {
            if (Math.abs(resultado1[i] - esperado1[i]) > 1e-6) { // Comparação com tolerância
                passou1 = false;
                break;
            }
        }
        if (passou1) {
            System.out.println("calcularDistanciaEuclidiana passou no teste com base simples 2D.");
        } else {
            System.out.println("calcularDistanciaEuclidiana falhou no teste com base simples 2D.");
        }

        // Caso 2: Base 3D com 3 vetores, vetor omegaNova 3D
        double[] omegaNova2 = {2.0, 3.0, 4.0};
        double[][] omegaBase2 = {
                {1.0, 3.0, 5.0},
                {2.0, 4.0, 6.0},
                {3.0, 5.0, 7.0}
        };
        double[] esperado2 = {Math.sqrt(3.0), Math.sqrt(3.0), Math.sqrt(27.0)};
        double[] resultado2 = Calculos.calcularDistanciaEuclidiana(omegaNova2, omegaBase2);
        boolean passou2 = true;
        for (int i = 0; i < esperado2.length; i++) {
            if (Math.abs(resultado2[i] - esperado2[i]) > 1e-6) { // Comparação com tolerância
                passou2 = false;
                break;
            }
        }
        if (passou2) {
            System.out.println("calcularDistanciaEuclidiana passou no teste com base 3D.");
        } else {
            System.out.println("calcularDistanciaEuclidiana falhou no teste com base 3D.");
        }

        // Caso 3: OmegaNova idêntico a um vetor na base
        double[] omegaNova3 = {0.0, 0.0, 0.0};
        double[][] omegaBase3 = {
                {0.0, 1.0, 2.0},
                {0.0, 2.0, 3.0},
                {0.0, 3.0, 4.0}
        };
        double[] esperado3 = {0.0, Math.sqrt(14.0), Math.sqrt(29.0)};
        double[] resultado3 = Calculos.calcularDistanciaEuclidiana(omegaNova3, omegaBase3);
        boolean passou3 = true;
        for (int i = 0; i < esperado3.length; i++) {
            if (Math.abs(resultado3[i] - esperado3[i]) > 1e-6) { // Comparação com tolerância
                passou3 = false;
                break;
            }
        }
        if (passou3) {
            System.out.println("calcularDistanciaEuclidiana passou no teste com vetor omegaNova idêntico a um vetor na base.");
        } else {
            System.out.println("calcularDistanciaEuclidiana falhou no teste com vetor omegaNova idêntico a um vetor na base.");
        }
    }

    // CONVERSOES

    public static void testConverterMatrizParaVetor() {
        System.out.println("Testando converterMatrizParaVetor...");

        // Teste com uma matriz válida
        double[][] matrizValida = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        };

        double[] vetorEsperado = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0};
        double[] vetorResultado = Conversoes.converterMatrizParaVetor(matrizValida);

        if (vetorResultado != null && vetorResultado.length == vetorEsperado.length) {
            boolean passou = true;
            for (int i = 0; i < vetorEsperado.length; i++) {
                if (Math.abs(vetorResultado[i] - vetorEsperado[i]) > 0.001) {
                    passou = false;
                    break;
                }
            }

            if (passou) {
                System.out.println("converterMatrizParaVetor passou no teste com matriz válida.");
            } else {
                System.out.println("converterMatrizParaVetor falhou no teste com matriz válida.");
            }
        } else {
            System.out.println("converterMatrizParaVetor falhou no teste com matriz válida (vetor nulo ou tamanho incorreto).");
        }

        // Teste com matriz nula
        double[] resultadoMatrizNula = Conversoes.converterMatrizParaVetor(null);
        if (resultadoMatrizNula == null) {
            System.out.println("converterMatrizParaVetor passou no teste com matriz nula.");
        } else {
            System.out.println("converterMatrizParaVetor falhou no teste com matriz nula.");
        }

        // Teste com matriz vazia
        double[][] matrizVazia = new double[0][0];
        double[] resultadoMatrizVazia = Conversoes.converterMatrizParaVetor(matrizVazia);
        if (resultadoMatrizVazia == null) {
            System.out.println("converterMatrizParaVetor passou no teste com matriz vazia.");
        } else {
            System.out.println("converterMatrizParaVetor falhou no teste com matriz vazia.");
        }
    }

    public static void testEscreverArraycomoImagem() {
        System.out.println("Testando escreverArraycomoImagem...");

        try {
            // Teste com uma matriz válida
            double[][] matrizValida = {
                    {0, 128, 255},
                    {64, 192, 128},
                    {255, 0, 64}
            };

            String caminhoOutput = "testeImagemValida.jpg";
            Conversoes.escreverArraycomoImagem(matrizValida, caminhoOutput);

            File arquivo = new File(caminhoOutput);
            if (arquivo.exists() && arquivo.length() > 0) {
                System.out.println("escreverArraycomoImagem passou no teste com matriz válida.");
            } else {
                System.out.println("escreverArraycomoImagem falhou no teste com matriz válida (arquivo não criado ou vazio).");
            }

            // Limpar arquivo gerado
            if (arquivo.exists()) {
                arquivo.delete();
            }

            // Teste com valores fora do intervalo permitido (0-255)
            double[][] matrizInvalida = {
                    {0, 300, -10},
                    {64, 192, 128},
                    {255, 0, 64}
            };

            try {
                Conversoes.escreverArraycomoImagem(matrizInvalida, "testeImagemInvalida.jpg");
                System.out.println("escreverArraycomoImagem falhou no teste com matriz inválida (não lançou exceção).");
            } catch (IllegalArgumentException e) {
                System.out.println("escreverArraycomoImagem passou no teste com matriz inválida (exceção lançada corretamente).");
            }

        } catch (Exception e) {
            System.out.println("escreverArraycomoImagem falhou no teste devido a uma exceção inesperada: " + e.getMessage());
        }
    }
    public static void testArrayparaMatriz() {
        System.out.println("Testando arrayparaMatriz...");

        try {
            // Teste com array e matriz válidos
            int[][] arrayValido = {
                    {1}, {2}, {3},
                    {4}, {5}, {6},
                    {7}, {8}, {9}
            };

            double[][] matrizImagens = new double[9][1]; // Deve resultar em uma matriz 3x3
            double[][] matrizEsperada = {
                    {1.0, 2.0, 3.0},
                    {4.0, 5.0, 6.0},
                    {7.0, 8.0, 9.0}
            };

            double[][] matrizResultado = Conversoes.arrayparaMatriz(arrayValido, matrizImagens);

            boolean passou = true;
            for (int i = 0; i < matrizEsperada.length; i++) {
                for (int j = 0; j < matrizEsperada[0].length; j++) {
                    if (Math.abs(matrizResultado[i][j] - matrizEsperada[i][j]) > 0.001) {
                        passou = false;
                        break;
                    }
                }
            }

            if (passou) {
                System.out.println("arrayparaMatriz passou no teste com array e matriz válidos.");
            } else {
                System.out.println("arrayparaMatriz falhou no teste com array e matriz válidos.");
            }

            // Teste com array incompatível com a matriz
            int[][] arrayInvalido = {
                    {1}, {2}, {3},
                    {4}, {5}, {6}
            };

            try {
                Conversoes.arrayparaMatriz(arrayInvalido, matrizImagens);
                System.out.println("arrayparaMatriz falhou no teste com array inválido (não lançou exceção).");
            } catch (IllegalArgumentException e) {
                System.out.println("arrayparaMatriz passou no teste com array inválido (exceção lançada corretamente).");
            }

        } catch (Exception e) {
            System.out.println("arrayparaMatriz falhou no teste devido a uma exceção inesperada: " + e.getMessage());
        }
    }

    //VALIDACOES

    public static void testVerificarValoresProprios() {
        System.out.println("Testando verificarValoresProprios...");

        try {
            // Teste com k dentro do intervalo permitido
            int cols = 5, k1 = 3;
            int resultadoEsperado1 = 3;
            int resultado1 = Validacoes.verificarValoresProprios(cols, k1);
            if (resultado1 == resultadoEsperado1) {
                System.out.println("verificarValoresProprios passou no teste com k dentro do intervalo permitido.");
            } else {
                System.out.println("verificarValoresProprios falhou no teste com k dentro do intervalo permitido.");
            }

            // Teste com k maior que cols
            int k2 = 6;
            int resultadoEsperado2 = cols;
            int resultado2 = Validacoes.verificarValoresProprios(cols, k2);
            if (resultado2 == resultadoEsperado2) {
                System.out.println("verificarValoresProprios passou no teste com k maior que cols.");
            } else {
                System.out.println("verificarValoresProprios falhou no teste com k maior que cols.");
            }

            // Teste com k igual a 0
            int k3 = 0;
            int resultadoEsperado3 = cols;
            int resultado3 = Validacoes.verificarValoresProprios(cols, k3);
            if (resultado3 == resultadoEsperado3) {
                System.out.println("verificarValoresProprios passou no teste com k igual a 0.");
            } else {
                System.out.println("verificarValoresProprios falhou no teste com k igual a 0.");
            }

            // Teste com k negativo
            int k4 = -2;
            int resultadoEsperado4 = cols;
            int resultado4 = Validacoes.verificarValoresProprios(cols, k4);
            if (resultado4 == resultadoEsperado4) {
                System.out.println("verificarValoresProprios passou no teste com k negativo.");
            } else {
                System.out.println("verificarValoresProprios falhou no teste com k negativo.");
            }

        } catch (Exception e) {
            System.out.println("verificarValoresProprios falhou no teste devido a uma exceção inesperada: " + e.getMessage());
        }
    }

    public static void testIndicedoMenor() {
        System.out.println("Testando IndicedoMenor...");

        try {
            // Teste com valores distintos
            double[] distancias1 = {10.5, 8.3, 7.2, 9.0};
            int resultadoEsperado1 = 2; // O menor valor está no índice 2 (7.2)
            int resultado1 = Validacoes.IndicedoMenor(distancias1);

            if (resultado1 == resultadoEsperado1) {
                System.out.println("IndicedoMenor passou no teste com valores distintos.");
            } else {
                System.out.println("IndicedoMenor falhou no teste com valores distintos. Resultado: " + resultado1);
            }

            // Teste com valores iguais
            double[] distancias2 = {5.0, 3.0, 3.0, 4.5};
            int resultadoEsperado2 = -1; // Há dois menores valores iguais (índices 1 e 2)
            int resultado2 = Validacoes.IndicedoMenor(distancias2);

            if (resultado2 == resultadoEsperado2) {
                System.out.println("IndicedoMenor passou no teste com valores iguais.");
            } else {
                System.out.println("IndicedoMenor falhou no teste com valores iguais. Resultado: " + resultado2);
            }

            // Teste com um único valor no array
            double[] distancias3 = {42.0};
            int resultadoEsperado3 = 0; // Apenas um valor, então o índice é 0
            int resultado3 = Validacoes.IndicedoMenor(distancias3);

            if (resultado3 == resultadoEsperado3) {
                System.out.println("IndicedoMenor passou no teste com um único valor.");
            } else {
                System.out.println("IndicedoMenor falhou no teste com um único valor. Resultado: " + resultado3);
            }

            // Teste com valores todos iguais
            double[] distancias4 = {7.0, 7.0, 7.0};
            int resultadoEsperado4 = -1; // Todos os valores são iguais
            int resultado4 = Validacoes.IndicedoMenor(distancias4);

            if (resultado4 == resultadoEsperado4) {
                System.out.println("IndicedoMenor passou no teste com valores todos iguais.");
            } else {
                System.out.println("IndicedoMenor falhou no teste com valores todos iguais. Resultado: " + resultado4);
            }

        } catch (Exception e) {
            System.out.println("IndicedoMenor falhou no teste devido a uma exceção inesperada: " + e.getMessage());
        }
    }


}




