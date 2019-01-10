package sec.notebook;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NotebookController {
    
    private List<String> list;
    private List<String> lAffiche;
    private int iToShow = 5;

    public NotebookController() {
        this.list = new ArrayList<>();
        this.list.add("Hello world!");
    }

    @RequestMapping("/")
    public String home(Model model, @RequestParam(required = false) String content) {
        try{
            if (content != null && !content.trim().isEmpty()) {
                this.list.add(content.trim());
            }

            if (list.size()>iToShow){    
                // source: https://stackoverflow.com/questions/14605999/getting-the-last-three-elements-from-a-list-arraylist
                lAffiche = list.subList(Math.max(list.size() - iToShow, 0), list.size());
                model.addAttribute("list", lAffiche);
                return "index";            
            }
            else{
                model.addAttribute("list", list);                    
            }
        }catch(Exception e){
            model.addAttribute("error", e.toString());
            return "index";
        }

        return "index";
        
    }

}
