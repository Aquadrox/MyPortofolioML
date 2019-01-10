package sec.helloweb;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.concurrent.ThreadLocalRandom;


@Controller
public class HelloWebWithDatabaseController {
boolean fait = false;
    @Autowired
    private HelloMessageRepository helloMessageRepository;

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        if(!fait){Addmessage();}        
        String sMessage="Pas init";
        List<HelloMessage> message = helloMessageRepository.findAll();
        
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = ThreadLocalRandom.current().nextInt(0, message.size());
        
        sMessage = message.get(randomNum).getContent();            
        
        
        return sMessage;
        
    }
    
    public void Addmessage(){
        HelloMessage msg = new HelloMessage();
        HelloMessage msg2 = new HelloMessage();
        HelloMessage msg3 = new HelloMessage();
        HelloMessage msg4 = new HelloMessage();
        HelloMessage msg5 = new HelloMessage();
        msg.setContent("Hello");
        msg2.setContent("World");
        msg3.setContent("How");
        msg4.setContent("Are");
        msg5.setContent("You");
        helloMessageRepository.save(msg);
        helloMessageRepository.save(msg2);
        helloMessageRepository.save(msg3);
        helloMessageRepository.save(msg4);
        helloMessageRepository.save(msg5);
        fait = true;
    }
        

}
