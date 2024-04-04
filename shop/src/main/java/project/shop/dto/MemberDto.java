package project.shop.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import project.shop.entity.MemberEntity;

import java.lang.reflect.Member;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private Long idx;
    private String userid;
    private String pwd;
    private String username;
    private String gender;
    private String birthday;
    private String phonenum;
    private String addressnum;
    private String address1;
    private String address2;
    private String email;
    private String site;
    private String author;
    private Timestamp regdate;

    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .userid(userid)
                .pwd(pwd)
                .username(username)
                .gender(gender)
                .birthday(birthday)
                .phonenum(phonenum)
                .address(addressnum + "/" + address1 + "/" + address2)
                .email(email + "@" + site)
                .author(author)
                .regdate(regdate)
                .build();
    }
}
