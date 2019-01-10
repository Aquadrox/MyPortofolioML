package sec.calculator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CalculatorController {
    
    @RequestMapping("/add")
    @ResponseBody
    public int add(@RequestParam int first
                      , @RequestParam int second) {
        int iSum = first + second;
        return iSum;
    }  

    
    @RequestMapping("/multiply")
    @ResponseBody
    public int multiply (@RequestParam int first
                      , @RequestParam int second) {
        int iProduct = first * second;
        return iProduct;
    }

}
