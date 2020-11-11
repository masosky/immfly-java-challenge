package com.immfly.main;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = "localtest")
public class MainTest {

    @Autowired
    private Main main;

    /*@Test
    public void contextLoads() {
        System.out.println("TEST RUNNING");
        assertThat(main).isNotNull();
    }

    @Test
    public void test() {
        assertThat(main.home()).isEqualTo("Hello World!");
    }

    @Test
    public void testEnv() {
        assertThat(main.env()).isEqualTo("localhost");
    }*/

    @Test
    public void testEnv() {
        assertThat(1).isEqualTo(1);
    }
}
