package sec.millionaire.domain;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;
import sec.millionaire.controller.MillionaireController;

@Entity
public class UserDetails extends AbstractPersistable<Long> {

    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(MillionaireController.bAnswered){this.name = name;}       
        
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(MillionaireController.bAnswered){this.email = email;}        
    }

}
