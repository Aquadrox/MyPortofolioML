package sec.millionaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sec.millionaire.domain.UserDetails;
import sec.millionaire.repository.UserDetailsRepository;

@Controller
public class MillionaireController {
    
    public static boolean bAnswered = false;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @RequestMapping("*")
    public String main() {
        return "redirect:/topics";
    }

    @RequestMapping("/incorrect")
    public String incorrect() {
        return "incorrect";
    }

    @RequestMapping("/finish")
    public String finish() {
        if (bAnswered){return "finish";}
        return "redirect:/cheater";
        
    }
    
    @RequestMapping("/cheater")
    public String cheater() {
        return "cheater";
    }

    @RequestMapping(value = "/details", method = RequestMethod.POST)
    public String postData(@ModelAttribute UserDetails details) {
        userDetailsRepository.save(details);
        return "thanks";
    }
}
