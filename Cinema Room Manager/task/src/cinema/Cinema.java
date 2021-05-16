package cinema;

import java.util.Scanner;

public class Cinema {
    public static char[][] seats;
    public static int booked = 0;
    public static int currentIncome = 0;
    public static double percentage = 0.00;
    public static int numRows;
    public static int numSeats;
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        cinemaBooking();
    }

    public static char[][] seatMap(int rows, int cols) {

        char[][] arr = new char[rows][cols];

        for (int i = 0; i <arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = 'S';
            }
        }

        return arr;
    }

    public static void cinemaBooking() {
        System.out.println("Enter the number of rows:");
        numRows = scanner.nextInt();
        System.out.println("Enter the number of seats in each rows:");
        numSeats = scanner.nextInt();
        seats = seatMap(numRows, numSeats);
        boolean isExit = false;
        while (!isExit) {
            showMenu();
            int x = scanner.nextInt();
            switch (x) {
                case 1:
                    showSeats(seats);
                    break;
                case 2:
                    System.out.printf("Ticket price: $%d%n", buyTicket());
                    break;
                case 3:
                    showStatistics();
                    break;
                case 0:
                    isExit = true;
                    break;
                default:
                    System.out.println("Bad input");
                    break;

            }
        }
    }

    public static void showMenu() {
        System.out.println();
        String menu = String.format("%s%n%s%n%s%n%s%n",
                "1. Show the seats",
                "2. Buy a ticket",
                "3. Statistics",
                "0. Exit");
        System.out.println(menu);
    }

    public static int[] askInput() {
        int[] arr = new int[2];
        System.out.println("Enter a row number:");
        int row = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        int seat = scanner.nextInt();
        arr[0] = row;
        arr[1] = seat;
        return arr;
    }

    public static boolean isBooked(int a, int b) {
        System.out.println("a : " + a + " b : " + b);
        if (seats[a][b] == 'B') {
            return true;
        }

        return false;
    }

    public static boolean isInputWrong(int a, int b) {
        if (a < 1 || a > numRows || b < 1 || b > numSeats) {
            return true;
        }
        return false;
    }

    public static int buyTicket() {
        int[] coords = askInput();
        int row = coords[0];
        int seat = coords[1];

        boolean wrongInput = isInputWrong(row, seat);
        while (wrongInput) {
            System.out.println("Wrong input!");
            coords = askInput();
            row = coords[0];
            seat = coords[1];
            wrongInput = isInputWrong(row, seat);
        }

        boolean seatBooked = isBooked(row - 1, seat - 1);
        while (seatBooked) {
            System.out.println("That ticket has already been purchased!");
            coords = askInput();
            row = coords[0];
            seat = coords[1];
            wrongInput = isInputWrong(row, seat);
            while (wrongInput) {
                System.out.println("Wrong input!");
                coords = askInput();
                row = coords[0];
                seat = coords[1];
                wrongInput = isInputWrong(row, seat);
            }
            seatBooked = isBooked(row - 1, seat - 1);
        }

        int price = 10;
        int totalSeats = numRows * numSeats;
        if (totalSeats <= 60) {
            price = 10;
        } else {
            price = row <= (seats.length / 2) ? 10 : 8;
        }

        seats[row - 1][seat - 1] = 'B';
        booked++;
        currentIncome += price;
        return price;
    }

    public static void showStatistics() {
        int totalSeats = seats.length * seats[0].length;
        percentage = ((double) booked/totalSeats) * 100;
        String purchased = "Number of purchased tickets: " + booked;
        String percent = String.format("%s%.2f", "Percentage: ", percentage);
        String incomeSoFar = "Current Income: $" + currentIncome;
        String totalIncome = "Total income: $" + calcProfit();
        String states = String.format("%s%n%s%n%s%n%s%n",
                purchased,
                percent + "%",
                incomeSoFar,
                totalIncome);
        System.out.println(states);
    }

    public static void showSeats(char[][] arr) {
        System.out.println("Cinema:");
        for (int i = 0; i < arr[0].length; i++) {
            System.out.print(" " + (i + 1));
        }
        System.out.println();
        for (int j = 0; j < arr.length; j++) {
            String row = "" + (j + 1);
            for (int k = 0; k < arr[j].length; k++) {
                row += " " + arr[j][k];
            }
            System.out.printf("%s%n", row);
        }

    }

    public static int calcProfit() {
        int numRows = seats.length;
        int numSeats = seats[0].length;
        int total = 0;
        if (numRows * numSeats > 60) {
            int frontRow = numRows / 2;
            int backRow = numRows - frontRow;
            total = (frontRow * numSeats * 10) + (backRow * numSeats * 8);
        } else {
            total = numRows * numSeats * 10;
        }

        return total;
    }
}