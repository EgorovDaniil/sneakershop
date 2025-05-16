package com.example.sneakershop;

import com.example.sneakershop.mapper.MapperConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(MapperConfig.class)  // Если вы явно создаете бин для маппера

class SneakerShopApplicationTests {

	@Test
	void contextLoads() {
	}

}
