package com.fintech.tech_clone;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(SerenityJUnit5Extension.class)
class TechCloneApplicationTests {

	@Test
	void main_ShouldStartApplicationWithoutExceptions() {
		// Assert that the main method runs without throwing exceptions
		Assertions.assertDoesNotThrow(() -> TechCloneApplication.main(new String[]{}));
	}

}
