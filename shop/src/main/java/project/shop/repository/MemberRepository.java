package project.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.shop.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByUserid(String userid);
    Page<MemberEntity> findByUseridContaining(String searchKeyword, Pageable pageable);
    Page<MemberEntity> findByUsernameContaining(String searchKeyword, Pageable pageable);
}
