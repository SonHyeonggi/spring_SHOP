package project.shop.entity;

import jakarta.persistence.*;
import lombok.*;
import project.shop.dto.FilesDto;

@Entity
@Table(name = "files_shop_t")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "files_shop_seq", sequenceName = "files_shop_seq", initialValue = 1, allocationSize = 1)
public class FilesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "files_shop_seq")
    @Column(name = "filesidx")
    private Long filesidx;

    @Setter
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "itemidx")
    private ItemEntity itemEntity;

    @Setter
    @Column(name = "filename1")
    private String filename1;

    @Setter
    @Column(name = "original1")
    private String original1;

    @Setter
    @Column(name = "filename2")
    private String filename2;

    @Setter
    @Column(name = "original2")
    private String original2;

    @Setter
    @Column(name = "filename3")
    private String filename3;

    @Setter
    @Column(name = "original3")
    private String original3;

    @Builder
    public FilesEntity (String original1, String original2, String original3) {
        this.original1 = original1;
        this.original2 = original2;
        this.original3 = original3;
    }

    public FilesDto filesDto() {
        return FilesDto.builder()
                .filename1(filename1)
                .filename2(filename2)
                .filename3(filename3)
                .build();
    }
}
