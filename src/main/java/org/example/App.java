package org.example;

import java.util.*;

import java.util.*;

public class App {


    public static final int HOURS_OF_DAY = 24;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TimeAndPrice[] prices = new TimeAndPrice[0];
        String choice;

        do {
            String menu = """
                    Elpriser
                    ========
                    1. Inmatning
                    2. Min, Max och Medel
                    3. Sortera
                    4. Bästa Laddningstid (4h)
                    e. Avsluta
                    """;
            System.out.print(menu);
            choice = scanner.nextLine();
            //add code for choices 1 to 4
            switch (choice) {
                case "1" -> prices = readValues(scanner);
                case "2" -> printMinMaxAverage(prices);
                case "3" -> printSorted(prices);
                case "4" -> printBest4Hours(prices);
            }
            System.out.println(Arrays.toString(prices));
        }while(!choice.equalsIgnoreCase("e"));
    }

    private static void printBest4Hours(TimeAndPrice[] prices) {
        int lowest4HourIndex = 0;
        for (int i = 0; i < prices.length - 3; i++) {
            int current = prices[lowest4HourIndex].price()
                    + prices[lowest4HourIndex + 1].price()
                    + prices[lowest4HourIndex + 2].price()
                    + prices[lowest4HourIndex + 3].price();
            int next = prices[i].price()
                    + prices[i + 1].price()
                    + prices[i + 2].price()
                    + prices[i + 3].price();
            if (next < current) {
                lowest4HourIndex = i;

            }
        }

        String time = prices[lowest4HourIndex].timeInterval().substring(0,2);
        float avrgPrice = (prices[lowest4HourIndex].price()
                + prices[lowest4HourIndex + 1].price()
                + prices[lowest4HourIndex + 2].price()
                + prices[lowest4HourIndex + 3].price()) / 4.0f;

        System.out.printf("Påbörja laddning klockan " + time + "\n");
        System.out.printf("Medelpris 4h: %.1f öre/kWh\n", avrgPrice);
    }

    private static void printSorted(TimeAndPrice[] prices) {
        Arrays.sort(prices, Comparator.comparingInt(TimeAndPrice::price).reversed());//.thenComparingInt()
        for (var price : prices)
            System.out.print(price.timeInterval() + " " + price.price() + " öre\n");

    }

    private static void printMinMaxAverage(TimeAndPrice[] prices) {
        TimeAndPrice minPris = findLowestPrice(prices);
        TimeAndPrice maxPris = findHighestPrice(prices);
        float average = averagePrice(prices);

        System.out.printf("Lägsta pris: %s, %d öre/kWh\n", minPris.timeInterval(), minPris.price());
        System.out.printf("Högsta pris: %s, %d öre/kWh\n", maxPris.timeInterval(), maxPris.price());
        System.out.printf("Medelpris: %.2f öre/kWh\n", average);


    }

    private static float averagePrice(TimeAndPrice[] prices) {
        float sum = 0.0f;
        for (var price: prices) {
            sum += price.price();
        }
        return sum / prices.length;
    }

    private static TimeAndPrice findHighestPrice(TimeAndPrice[] prices) {
        return Arrays.stream(prices).max(Comparator.comparingInt(TimeAndPrice::price)).get();
    }

    private static TimeAndPrice findLowestPrice(TimeAndPrice[] prices) {
        var lowestSoFar = prices[0];
        for (var timeAndPrice: prices) {
            if (timeAndPrice.price() < lowestSoFar.price())
                lowestSoFar = timeAndPrice;
        }
        return lowestSoFar;
    }



    private static TimeAndPrice[] readValues(Scanner scanner) {
        var array = new TimeAndPrice[HOURS_OF_DAY];
        for (int i = 0; i < array.length; i++)
            array[i] = new TimeAndPrice(String.format("%02d-%02d", i, i + 1),
                    Integer.parseInt(scanner.nextLine()));
        return array;
    }
}

record TimeAndPrice(String timeInterval, int price) {
}