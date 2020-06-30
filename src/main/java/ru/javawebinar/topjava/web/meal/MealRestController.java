package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public static int userId = 1;

    public List<MealTo> getAll() {
        return MealsUtil.getTos(service.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public MealTo get(int id) {
        Meal meal = service.get(id, userId);
        return MealsUtil.createTo(meal, service.isExcess(meal, MealsUtil.DEFAULT_CALORIES_PER_DAY));
    }

    public MealTo create(MealTo mealTo) {
        Meal meal = new Meal(mealTo.getDateTime(), mealTo.getDescription(), mealTo.getCalories(), userId);
        meal = service.create(meal, userId);
        return MealsUtil.createTo(meal, service.isExcess(meal, MealsUtil.DEFAULT_CALORIES_PER_DAY));
    }

    public void delete(int id) {
        service.delete(id, userId);
    }

    public void update(MealTo mealTo) {
        Meal meal = new Meal(mealTo.getId(), mealTo.getDateTime(), mealTo.getDescription(), mealTo.getCalories(), userId);
        service.update(meal, userId);
    }

}