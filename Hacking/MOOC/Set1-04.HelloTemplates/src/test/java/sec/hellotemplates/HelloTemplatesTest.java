package sec.hellotemplates;

import fi.helsinki.cs.tmc.edutestutils.Points;
import org.fluentlenium.adapter.FluentTest;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Points("4")
@Controller
public class HelloTemplatesTest extends FluentTest {

    @LocalServerPort
    private Integer port;

    @RequestMapping("/")
    @ResponseBody
    public String indexShownAtVideo() {
        return "index";
    }

    @RequestMapping("/video")
    @ResponseBody
    public String videoShownAtVideo() {
        return "video";
    }
//
//    @Test
//    public void linkFromRootLeadsToVideo() {
//        goTo("http://localhost:" + port + "/");
//        find("a").click();
//        assertThat(pageSource()).contains("dQw4w9WgXcQ");
//    }
}
