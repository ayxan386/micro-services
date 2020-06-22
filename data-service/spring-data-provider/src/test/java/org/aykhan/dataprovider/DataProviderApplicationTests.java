package org.aykhan.dataprovider;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
class DataProviderApplicationTests {

  @Test
  void contextLoads() {
    String dummy = "Hello World";
    assertThat(dummy).isEqualTo("Hello World");
  }

}
