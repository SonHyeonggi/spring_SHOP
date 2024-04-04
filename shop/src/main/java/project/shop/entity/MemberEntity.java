package project.shop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import project.shop.dto.MemberDto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Table(name = "member_shop_t")
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "mem_shop_seq", sequenceName = "mem_shop_seq", allocationSize = 1, initialValue = 1)
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mem_shop_seq")
    @Column(name = "idx")
    private Long idx;

    @Setter
    @Column(name = "userid")
    private String userid;

    @Setter
    @Column(name = "pwd")
    private String pwd;

    @Setter
    @Column(name = "username")
    private String username;

    @Setter
    @Column(name = "gender")
    private String gender;

    @Setter
    @Column(name = "birthday")
    private String birthday;

    @Setter
    @Column(name = "phonenum")
    private String phonenum;

    @Setter
    @Column(name = "address")
    private String address;

    @Setter
    @Column(name = "email")
    private String email;

    @Setter
    @Column(name = "author")
    private String author; // [member, admin]

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Timestamp regdate;

    @Builder
    public MemberEntity(String userid, String pwd, String username, String gender, String birthday, String phonenum, String address, String email, String author, Timestamp regdate) {
        this.userid = userid;
        this.pwd = pwd;
        this.username = username;
        this.gender = gender;
        this.birthday = birthday;
        this.phonenum = phonenum;
        this.address = address;
        this.email = email;
        this.author = author;
        this.regdate = regdate;
    }

    public MemberDto toDto() {
        String addressnum = null;
        String address1 = null;
        String address2 = null;
        if (address != null) {
            String[] addressParts = address.split("/");
            addressnum = addressParts.length > 0 ? addressParts[0] : null;
            address1 = addressParts.length > 1 ? addressParts[1] : null;
            address2 = addressParts.length > 2 ? addressParts[2] : null;
        }
        return MemberDto.builder()
                .idx(idx)
                .userid(userid)
                .pwd(pwd)
                .username(username)
                .gender(gender)
                .birthday(birthday)
                .phonenum(phonenum)
                .addressnum(addressnum)
                .address1(address1)
                .address2(address2)
                .email(email.split("@")[0])
                .site(email.split("@")[1])
                .author(author)
                .regdate(regdate)
                .build();
    }


}


