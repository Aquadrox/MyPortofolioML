package sec.hellotemplates;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloTemplatesController {
    
    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("text", "Hello World!");
        return "index";
    }
    
    @RequestMapping("/video")
    public String video() {
        return "video";
    }
    
    @RequestMapping("/form")
    public String form() {
        return "form";
    }

}
