package project.shop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.shop.entity.ItemEntity;
import project.shop.repository.ItemRepository;

@SpringBootTest
class ShopApplicationTests {

	@Autowired
	ItemRepository itemRepository;

	@Test
	void contextLoads() {
		ItemEntity itemEntity = itemRepository.findById(0L).orElse(null);
		itemEntity.getFilesEntities().get(0).getFilename3();
	}

}
