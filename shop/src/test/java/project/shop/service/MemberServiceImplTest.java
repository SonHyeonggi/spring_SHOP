package project.shop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import project.shop.entity.MemberEntity;
import project.shop.repository.MemberRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceImplTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 테스트(@PageableDefault Pageable pageable) {



        Page<MemberEntity> all = memberRepository.findAll(pageable);
        for (MemberEntity entity : all) {
            System.out.println("entity = " + entity);
        }


    }

}