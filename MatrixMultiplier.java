/**
 * Done by Student: Ahmad Mohamed #21808164
 * CMPE 411
 */

import java.util.Random;

public class MatrixMultiplier {
    public static void main(String[] args) {
        // Set the matrix dimensions
        int rows = 100;
        int cols = 100;

        // Create two random matrices
        int[][] matrixA = generateRandomMatrix(rows, cols);
        int[][] matrixB = generateRandomMatrix(rows, cols);

        // Display the matrices (optional)
        System.out.println("Matrix A:");
        printMatrix(matrixA);

        System.out.println("Matrix B:");
        printMatrix(matrixB);

        // Result matrix
        int[][] resultMatrix = new int[rows][cols];

        // Number of threads
        int numThreads = 10;
        if (rows < 10)
            numThreads = 1;

        // Create and start threads
        Thread[] threads = new Thread[numThreads];
        int rowsPerThread = rows / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int startRow = i * rowsPerThread;
            int endRow = (i + 1) * rowsPerThread;

            threads[i] = new Thread(new MatrixMultiplierWorker(matrixA, matrixB, resultMatrix, startRow, endRow));
            threads[i].start();
        }

        // Wait for all threads to complete
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Display the result matrix
        System.out.println("Result Matrix:");
        printMatrix(resultMatrix);
    }

    // Helper method to generate a random matrix
    private static int[][] generateRandomMatrix(int rows, int cols) {
        int[][] matrix = new int[rows][cols];
        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(10); // You can adjust the range of random values
            }
        }

        return matrix;
    }

    // Helper method to print a matrix (optional)
    private static void printMatrix(int[][] matrix) {
        /*
         for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
         */
        /*
        for (int[] ints : matrix) {
            for (int j = 0; j < ints.length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
         */

        for (int[] ints : matrix) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

class MatrixMultiplierWorker implements Runnable {
    private final int[][] matrixA;
    private final int[][] matrixB;
    private final int[][] resultMatrix;
    private final int startRow;
    private final int endRow;

    public MatrixMultiplierWorker(int[][] matrixA, int[][] matrixB, int[][] resultMatrix, int startRow, int endRow) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.resultMatrix = resultMatrix;
        this.startRow = startRow;
        this.endRow = endRow;
    }

    @Override
    public void run() {
        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < matrixB[0].length; j++) {
                for (int k = 0; k < matrixB.length; k++) {
                    resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
    }
}
