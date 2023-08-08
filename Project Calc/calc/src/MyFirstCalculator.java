import java.util.Scanner;

public class MyFirstCalculator {
    public static void main(String[] args) throws Exception {
        // Создаем сканер чтобы считывать то, что введет пользователь
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите пример:");
        String equation = scanner.nextLine();

        // Разделяем пробелом выражение на числа и математический знак
        String[] components = equation.split(" ");

        if (components.length != 3) throw new Exception("Должно быть два числа и арифметический знак");

        // Проверяем, есть ли в выражении римские цифры
        boolean isRoman = isRoman(components[0]) && isRoman(components[2]);
        boolean isArabic = isArabic(components[0]) && isArabic(components[2]);

        // Вычисляем выражение на основе введенных данных
        if (isRoman) {
            // Конвертируем римские числа в арабские
            int operand1 = romanToArabic(components[0]);
            int operand2 = romanToArabic(components[2]);

            if (operand1 > 10 || operand1 < 1 || operand2 > 10 || operand2 < 1) {
                throw new Exception("Введенные числа или число должны быть от 1 до 10");
            } else {
                int result = calculate(operand1, operand2, components[1]);

                if (result == 0) {
                    throw new Exception("В римских числах нет ноля");

                } else if (result < 0) {
                    throw new Exception("Римские числа не бывают отрицательными");

                } else {
                    // Полученный результат конвертируем назад в римские числа
                    String romanResult = arabicToRoman(result);
                    System.out.println("Ответ: " + romanResult);
                }
            }

        } else if(!isArabic) {

            throw new Exception("Вводите либо два римских числа и арфиметический знак одновременно, либо же два арабских целых числа и арифметический знак");

        }else {

            int operand1 = Integer.parseInt(components[0]);
            int operand2 = Integer.parseInt(components[2]);

            if (operand1 > 0 && operand1 < 11 || operand2 > 0 && operand2 < 11) {
                // Вычисляем
                int result = calculate(operand1, operand2, components[1]);
                System.out.println("Ответ: " + result);
            } else {
                throw new Exception("Введенные числа или число должны быть от 1 до 10");

            }
        }
        // Закрываем сканер
        scanner.close();
    }

    public static boolean isRoman(String input) {
        String romanNumerals = "IVXLCDM";

        // Проверяем, все ли введенные данные - римские цифры
        for (char c : input.toCharArray()) {
            if (!romanNumerals.contains(String.valueOf(c))) {
                return false;
            }
        }

        return true;
    }


    public static boolean isArabic(String input) {
        String arabicNumerals = "0123456789";

        // Проверяем, все ли введенные данные - арабские цифры
        for (char c : input.toCharArray()) {
            if (!arabicNumerals.contains(String.valueOf(c))) {
                return false;
            }
        }

        return true;
    }

    public static int romanToArabic(String input) {
        int result = 0;

        // Идем по римским цифрам справа налево
        for (int i = input.length() - 1; i >= 0; i--) {
            // Получаем значение текущей римской цифры
            int value = getValue(input.charAt(i));

            // Если предыдущая римская цифра имеет большее значение, вычитаем текущее значение
            if (i < input.length() - 1 && getValue(input.charAt(i + 1)) > value) {
                result -= value;
            } else {
                // В противном случае, прибавляем текущее значение
                result += value;
            }
        }

        return result;
    }

    public static int getValue(char numeral) {
        switch (numeral) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            default:
                return 0;
        }
    }

    public static String arabicToRoman(int number) {
        String[] romanNumerals = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] arabicValues = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        StringBuilder result = new StringBuilder();

        // Перебираем арабские значения и вычитаем соответствующую им римскую цифру
        for (int i = 0; i < arabicValues.length; i++) {
            while (number >= arabicValues[i]) {
                result.append(romanNumerals[i]);
                number -= arabicValues[i];
            }
        }

        return result.toString();
    }

    public static int calculate(int operand1, int operand2, String operator) throws Exception {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 == 0) {
                    throw new ArithmeticException("Нельзя делить на ноль");
                } else {
                    return operand1 / operand2;
                }
            default:
                throw new Exception("Неподдерживаемый арифметический знак : " + operator);
        }
    }
}