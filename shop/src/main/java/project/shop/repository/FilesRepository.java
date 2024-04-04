package project.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop.entity.FilesEntity;

public interface FilesRepository extends JpaRepository<FilesEntity, Long> {

}
