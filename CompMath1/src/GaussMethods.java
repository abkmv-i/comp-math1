import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;



class GaussMethods
{

    int size;
    private double[][] matrix;
    private double[][] workingMatrix;

    static void Show_Equation(GaussMethods gauss) {
        for (var i = 0; i < gauss.size; i++) {
            for (var j = 0; j < gauss.size; j++) {
                System.out.print(String.format("(%.2f)*X%d ", gauss.matrix[i][j], j + 1));
                if (j != gauss.size - 1) System.out.print("+ ");
            }
            System.out.print(String.format("= %.2f\n", gauss.matrix[i][gauss.size]));
        }
        System.out.println();
    }

    static void Working(GaussMethods g) {
        g.workingMatrix = new double[g.size][g.size + 1];
        for (var i = 0; i < g.size; i++) {
            if (g.size + 1 >= 0) System.arraycopy(g.matrix[i], 0, g.workingMatrix[i], 0, g.size + 1);
        }
        double[] solution = new double[g.size];
        var determinant = Determinant(g);

        System.out.println("Матрица\n");
        for (var i = 0; i < g.size; i++) {
            for (var j = 0; j < g.size; j++) {
                System.out.print(String.format("%.2f  ", g.matrix[i][j]));
            }
            System.out.println();
            System.out.println();
        }

        System.out.println("Рабочая матрица\n");
        for (var i = 0; i < g.size; i++) {
            for (var j = 0; j < g.size; j++) {
                System.out.print(String.format("%.2f  ", g.workingMatrix[i][j]));
                System.out.print("  ");
            }
            System.out.println();
            System.out.println();
        }

        System.out.println(String.format("Определитель равен =  %.2f", determinant));
        if (determinant != 0) {
            for (int i = g.size - 1; i >= 0; i--) {
                double sum = 0;
                for (int j = g.size - 1; j > i; j--) {
                    sum += solution[j] * g.workingMatrix[i][j];
                }
                solution[i] = (g.workingMatrix[i][g.size] - sum) / g.workingMatrix[i][i];
                System.out.println();
            }
            for (var i = 0; i < g.size; i++) {
                System.out.print(String.format("X%d = ", i + 1));
                System.out.print(String.format("%.2f", solution[i]));
                System.out.println();
            }
        } else System.out.println("Система не имеет единственное решение");
    }

    private static void Swap_Lines(GaussMethods gauss, int str1, int str2) {
        var temp = new double[gauss.size + 1]; //temporary str for swapping
        for (var j = 0; j < gauss.size + 1; j++) {
            temp[j] = gauss.workingMatrix[str1][j];
            gauss.workingMatrix[str1][j] = gauss.workingMatrix[str2][j];
            gauss.workingMatrix[str2][j] = temp[j];
        }
    }

    private static double Determinant(GaussMethods gauss) // find det using Gauss Method
    {
        double det = 1;
        for (var j = 0; j < gauss.size - 1; j++) {
            double per2 = gauss.workingMatrix[j][j];
            int maxInd = j;

            double per1;
            for (var i = j + 1; i < gauss.size; i++) {
                per1 = gauss.workingMatrix[i][j];
                if (Math.abs(per1) > Math.abs(per2)) maxInd = i;
            }
            if (j != maxInd) {
                Swap_Lines(gauss, j, maxInd); // когда в массиве находится ненулевой элемент, стоящий ниже нужной строки, меняем их с помощью метода Change
                det *= -1; // при замене строк знак определителя меняется на противоположный
            }
            per2 = gauss.workingMatrix[j][j];
            if (per2 != 0) {
                for (var x1 = j + 1; x1 < gauss.size; x1++) {
                    if (gauss.workingMatrix[x1][j] != 0) {
                        per1 = gauss.workingMatrix[x1][j];
                        for (var x2 = j; x2 < gauss.size + 1; x2++)
                            gauss.workingMatrix[x1][x2] = gauss.workingMatrix[j][x2] * per1 / per2 - gauss.workingMatrix[x1][x2];
                    }
                }
            } else {
                det = 0;
                break;
            }
        }
        for (var i = 0; i < gauss.size; i++) {
            det *= gauss.workingMatrix[i][i];
        }

        return det;
    }
    void InputMatrix(GaussMethods g)
    {
        Scanner in = new Scanner(System.in);
        g.matrix = new double[g.size][g.size + 1];
        String n;
        System.out.println("Входные значения");
        if (g.size < 1) {
            System.out.println("Неверное количество уравнений");
        } else {
            for (var i = 0; i < g.size; i++) {
                for (var j = 0; j < g.size+1; j++) {
                    System.out.printf("(%d;%d) = ", i + 1, j + 1);
                    n = in.nextLine().replace(".",",");
                    while(isNumeric(n)){
                        System.out.printf("(%d;%d) = ", i + 1, j + 1);
                        n = in.nextLine().replace(".",",");
                    }
                    /*while (!in.hasNextDouble()) {
                        System.out.printf("(%d;%d) = ", i + 1, j + 1);
                        in.next();
                    }*/
                    g.matrix[i][j] = Double.parseDouble(n.replace(',','.'));
                    //in.nextDouble();
                }
            }
        }
    }
    
    int ReadFromFile(GaussMethods g)
    {
        Scanner in = new Scanner(System.in);
        Scanner sc = null;
        System.out.println("Введите имя файла: ");
        String name=in.nextLine()+".txt";
        try {
            sc = new Scanner(new File(name));
        } catch (FileNotFoundException e) {
            System.out.println("Файл не существует!");
            return 0;
        }
        String [] splitted = null;
        int row = 0;
        float[][] matrixFromFile = new float[20][20];
        assert sc != null;
        while(sc.hasNext())
        {
            splitted = sc.nextLine().split(";");
            for(int column = 0; column < splitted.length; column++) {
                splitted[column] = splitted[column].replace(',', '.');
                if(!isNumeric(splitted[column])){
                    System.out.println("Некоректные данные в файле");
                    return 0;
                }
                matrixFromFile[row][column] = Float.parseFloat(splitted[column]);
            }
            row++;
        }

        assert splitted != null;
        g.size = splitted.length - 1;
        g.matrix = new double[g.size][g.size + 1];
        for (var i = 0; i < g.size; i++)
        {
            for (var j = 0; j < g.size + 1; j++)
            {
                g.matrix[i][j] = matrixFromFile[i][j];
            }
        }
        return 1;
    }

    void RandomMatrix(GaussMethods g)
    {
        g.matrix = new double[g.size][g.size + 1];
        var a = new Random();
        for (var i = 0; i < g.size; i++) {
            for (var j = 0; j < g.size + 1; j++) {
                double d = 10.0 + a.nextDouble() * 20.0;
                g.matrix[i][j] = d;
            }
        }
    }
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}


