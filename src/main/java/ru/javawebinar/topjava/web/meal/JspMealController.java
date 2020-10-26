package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
public class JspMealController extends MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping({"/meals", "/all-meals"})
    public String getAll(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @GetMapping("/meals/delete")
    public String delete(HttpServletRequest request, Model model) {
        int id = getId(request);
        delete(id);
        // Spring seems to be aware of the contextPath, no need to bother
        return "redirect:/meals";
    }

    @GetMapping({"/meals/create", "/meals/update"})
    public String createUpdate(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
