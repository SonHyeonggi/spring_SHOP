package project.shop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.shop.entity.MemberEntity;
import project.shop.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        MemberEntity user = memberRepository.findByUserid(userid)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
        return new UserDetailsImpl(user.getUserid(), user.getUsername(), user.getPwd(), user.getAuthor());
    }

}
