package edu.greenriver.it.booklendingspring.controllers;

import edu.greenriver.it.booklendingspring.util.AuthenticationInformation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Jason Engelbrecht
 * @version 1.0
 * Index controller
 */
@Controller
public class IndexController extends AuthenticationInformation {

    /**
     * Index page controller
     * @return view (index)
     */
    @RequestMapping({"/", "/index", "/index.html"})
    public String index() {
        return "index";
    }
}
