package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public MealTo get(int id) {
        Meal meal = service.get(id, SecurityUtil.authUserId());
        return MealsUtil.createTo(meal, service.isExcess(meal, SecurityUtil.authUserCaloriesPerDay()));
    }

    public MealTo create(MealTo mealTo) {
        Meal meal = new Meal(mealTo.getDateTime(), mealTo.getDescription(), mealTo.getCalories(), SecurityUtil.authUserId());
        meal = service.create(meal, SecurityUtil.authUserId());
        return MealsUtil.createTo(meal, service.isExcess(meal, SecurityUtil.authUserCaloriesPerDay()));
    }

    public void delete(int id) {
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(MealTo mealTo, int id) {
        Meal meal = new Meal(mealTo.getId(), mealTo.getDateTime(), mealTo.getDescription(), mealTo.getCalories(), SecurityUtil.authUserId());
        assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }

}