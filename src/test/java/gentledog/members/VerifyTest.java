package gentledog.members;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class VerifyTest {
    ApplicationModules modules = ApplicationModules.of(MembersApplicationTests.class);

    @Test
    void verify() {
        System.out.println(modules.toString());
        modules.verify();
    }
}
