import java.util.Scanner;

public class Main {
    private static void amountofVal(GaussMethods g) {
        System.out.print("Введите размерность матрицы: ");
        Scanner in = new Scanner(System.in);
        int number;
        do {

            while (!in.hasNextInt()) {
                System.out.print("Некорректно! Повторите ввод >>> ");
                in.next();
            }
            number = in.nextInt();
            if (number <= 1) {
                System.out.print("Некорректно! Повторите ввод >>> ");
            }
            g.size = number;
        } while (number <= 1);
    }

    public static void main(String[] args) {

        while (true) {
            System.out.print("\nВыберите тип ввода\n\n\t1 - Рандомно\n\t2 - С клавиатуры\n\t3 - С файла\n\tЛюбой другой символ == выход\n\n>>>  ");
            Scanner in = new Scanner(System.in);
            if(!in.hasNextInt()){
                break;
            }
            int choice = in.nextInt();
            GaussMethods gauss = new GaussMethods();

            if (choice == 1) {
                amountofVal(gauss);
                gauss.RandomMatrix(gauss);
                GaussMethods.Show_Equation(gauss);
                GaussMethods.Working(gauss);
                continue;
            }
            if (choice == 2) {
                amountofVal(gauss);
                gauss.InputMatrix(gauss);
                GaussMethods.Show_Equation(gauss);
                GaussMethods.Working(gauss);
                continue;
            }
            if (choice == 3) {
                if (gauss.ReadFromFile(gauss) == 1) {
                    GaussMethods.Show_Equation(gauss);
                    GaussMethods.Working(gauss);
                }

            } else break;
        }
    }
}
