package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println("---");
//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        mealsTo = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExcess> newList = new ArrayList<>();

        if (meals == null || meals.isEmpty()) {
            return newList;
        }

        HashMap<LocalDate, Integer> dayCaloriesTotal = new HashMap<>();

        for (UserMeal meal : meals) {
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            dayCaloriesTotal.put(mealDate, (dayCaloriesTotal.getOrDefault(mealDate, 0) + meal.getCalories()));
        }

        for (UserMeal meal : meals) {
            LocalTime mealTime = meal.getDateTime().toLocalTime();
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            if (mealTime.isAfter(startTime) && mealTime.isBefore(endTime)) {
                newList.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), (dayCaloriesTotal.get(mealDate).intValue() > caloriesPerDay)));
            }
        }

        return newList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        //return filteredByStreams1(meals, startTime, endTime, caloriesPerDay);
        return filteredByStreams2(meals, startTime, endTime, caloriesPerDay);
    }

    private static boolean isBetween(UserMeal meal, LocalTime startTime, LocalTime endTime) {
        LocalTime mealTime = meal.getDateTime().toLocalTime();
        return mealTime.isAfter(startTime) && mealTime.isBefore(endTime);
    }

    public static List<UserMealWithExcess> filteredByStreams1(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExcess> list = meals.stream()
                .filter(meal -> isBetween(meal, startTime, endTime))
                .map(meal -> new UserMealWithExcess(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        caloriesPerDay < meals.stream()
                                .filter(m -> m.getDateTime().toLocalDate().equals(meal.getDateTime().toLocalDate()))
                                .collect(Collectors.summingInt(UserMeal::getCalories))
                ))
                .collect(Collectors.toList());

        return list;
    }

    public static List<UserMealWithExcess> filteredByStreams2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dailyCalories = meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        List<UserMealWithExcess> list = meals.stream()
                .filter(meal -> isBetween(meal, startTime, endTime))
                .map(meal -> new UserMealWithExcess(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        caloriesPerDay < dailyCalories.get(meal.getDateTime().toLocalDate())
                ))
                .collect(Collectors.toList());

        return list;
    }

}
