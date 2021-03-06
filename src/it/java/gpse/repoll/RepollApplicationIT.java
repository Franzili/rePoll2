package gpse.repoll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RepollApplication.class)
@TestPropertySource(properties = { "repoll.productionMode=false" })
public class RepollApplicationIT {

    @Test
    public void contextLoads() {
        //Tests Application-Start.
    }
}
