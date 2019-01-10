package sec.project.controller;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;
    
    @Autowired
    private AuthenticationManagerBuilder auth;
    
    private EmbeddedDatabase SQLdatabase;
    
   
   
    @RequestMapping("/login")
    public String loginMapping() {
        return "login";
    }
    
    @RequestMapping("/logged")
    public String loggedMapping() {
        return "logged";
    }
    
    
    @PostConstruct
    public void init() {
        // test data to the application
        Signup signup = new Signup();
        signup.setName("Pigeon 1");
        signup.setAddress("Rob him");
        signup.setPassword("pass1");
        signup.setsRole("User");
        signupRepository.save(signup);
        
        Signup signup2 = new Signup();
        signup2.setName("UberCustomer");
        signup2.setAddress("At home");
        signup2.setPassword("I'm the boss");
        signup2.setsRole("Admin");
        signupRepository.save(signup2);


//=====================================================    Broken Authentication: this pass in is part of the 10K most used, forbid user to take it
        Signup signup3 = new Signup();
        signup3.setName("Customer");
        signup3.setAddress("In10K");
        signup3.setPassword("vacation");
        signup3.setsRole("User");
        signupRepository.save(signup3);
        
//        SQLdatabase = dataSource();
    }


    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address, @RequestParam String password) throws Exception {
        Signup signup = signupRepository.findByName(name);
        if (!name.equals("")){
            if (!address.equals("")){
                if (!password.equals("")){
                    if (signup == null) {
                        signupRepository.save(new Signup(name, address,password));
                        return "done";
                    }
                }
            }
        }
        
        return "redirect:/";
        
    }
    
    
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String getForm() {
        return "redirect:/";
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getHome() {
        return "/form";
    }
    
    @RequestMapping(value = "/logged", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("signups", signupRepository.findAll());
        return "logged";
    }
    
    
    
    
//    @Bean
//    public EmbeddedDatabase dataSource() {
//
//            // no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
//            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//            EmbeddedDatabase db = builder
//                    .setType(EmbeddedDatabaseType.HSQL) //.H2 or .DERBY
//                    .addScript("create-db.sql")
//                    .addScript("insert-data.sql")
//                    .build();
//            return db;
//	}

}
