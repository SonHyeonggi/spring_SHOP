package project.shop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.shop.dto.MemberDto;
import project.shop.entity.MemberEntity;

import java.util.List;


@Service
public interface MemberService {
    public void join_proc(MemberDto memberDto);
    public Page<MemberEntity> paging(Pageable pageable, String searchKeyword, String selectType);
    public MemberEntity view(Long memberIdx);
    public void delete(Long memberIdx);
    public MemberDto modify(Long memberIdx);
    public void modify_proc(MemberDto memberDto);
}
