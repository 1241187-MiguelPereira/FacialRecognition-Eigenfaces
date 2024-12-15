public class mainTest {
    public static void main(String[] args) {
        testIsSymmetric();
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
}
