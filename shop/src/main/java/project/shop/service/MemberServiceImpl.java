package project.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import project.shop.dto.MemberDto;
import project.shop.entity.MemberEntity;
import project.shop.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join_proc(MemberDto memberDto) {
        memberRepository.save(memberDto.toEntity());
    }

    @Override
    public Page<MemberEntity> paging(Pageable pageable, String searchKeyword, String selectType) {
        Page<MemberEntity> page = null;

        if(searchKeyword.equals("none"))
        {
            page = memberRepository.findAll(pageable);
        }
        else if(selectType.equals("userid"))
        {
            page = memberRepository.findByUseridContaining(searchKeyword, pageable);
        }
        else
        {
            page = memberRepository.findByUsernameContaining(searchKeyword, pageable);
        }

        return page;
    }

    @Override
    public MemberEntity view(Long memberIdx) {
        return memberRepository.findById(memberIdx).orElseThrow();
    }

    @Override
    public void delete(Long memberIdx) {
        memberRepository.deleteById(memberIdx);

    }

    @Override
    public MemberDto modify(Long memberIdx) {
        return memberRepository.findById(memberIdx).orElseThrow().toDto();
    }

    @Override
    public void modify_proc(MemberDto memberDto) {
        MemberEntity memberEntity = memberRepository.findById(memberDto.getIdx()).orElse(null);
        memberEntity.setUserid(memberDto.getUserid());
        memberEntity.setPwd(memberDto.getPwd());
        memberEntity.setUsername(memberDto.getUsername());
        memberEntity.setGender(memberDto.getGender());
        memberEntity.setBirthday(memberDto.getBirthday());
        memberEntity.setPhonenum(memberDto.getPhonenum());
        memberEntity.setAddress(memberDto.getAddressnum() + "/" + memberDto.getAddress1() + "/" + memberDto.getAddress2());
        memberEntity.setEmail(memberDto.getEmail() + "@" + memberDto.getSite());
        memberEntity.setAuthor(memberDto.getAuthor());
        memberRepository.save(memberEntity);

    }
}

