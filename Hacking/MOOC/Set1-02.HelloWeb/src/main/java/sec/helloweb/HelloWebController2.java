package sec.helloweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWebController2 {
    
    @RequestMapping("/greet")
    @ResponseBody
    public String greet(@RequestParam String user) {
        return "Hi " + user + ", how are you?";
    }
    
    @RequestMapping("/path")
    @ResponseBody
    public String path() {
        return "Path";
    }

    @RequestMapping("/route")
    @ResponseBody
    public String route() {
        return "Route";
    }

    @RequestMapping("/trail")
    @ResponseBody
    public String trail() {
        return "Trail";
    }

}
