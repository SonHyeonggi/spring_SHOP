package project.shop.repository;

import org.hibernate.sql.Insert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.shop.entity.CategoryEntity;
import project.shop.entity.ItemEntity;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
