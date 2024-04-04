package project.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.shop.entity.CategoryEntity;
import project.shop.entity.ItemEntity;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    Optional<ItemEntity> findByItemname(String itemname);
    Page<ItemEntity> findByItemnameContaining(String itemname, Pageable pageable);
    /*Page<ItemEntity> findByItemtype1Containing(String itmetype1, Pageable pageable);*/
    Page<ItemEntity> findByItemcountryContaining(String itemcountry, Pageable pageable);
}
