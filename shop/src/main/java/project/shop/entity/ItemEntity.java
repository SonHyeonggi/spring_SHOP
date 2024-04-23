package project.shop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import project.shop.dto.FilesDto;
import project.shop.dto.ItemDto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "item_shop_t")
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "item_shop_seq", sequenceName = "item_shop_seq", initialValue = 1, allocationSize = 1)
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_shop_seq")
    @Column(name = "itemidx")
    private Long itemidx;

    @Setter
    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "category_idx")
    private CategoryEntity categoryEntity;

    @Setter
    @Column(name = "itemname")
    private String itemname;

    @Setter
    @Column(name = "itemcountry")
    private String itemcountry;

    @Setter
    @Column(name = "itemingred")
    private String itemingred;

    @Setter
    @Column(name = "itemprice")
    private String itemprice;

    @Setter
    @Column(name = "itemcontent")
    private String itemcontent;

    @OneToMany(mappedBy="itemEntity" , fetch=FetchType.LAZY , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<FilesEntity> filesEntities = new ArrayList<>();

    /*@Setter
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
    private String original3;*/

    @Setter
    @Column(name = "iteminven")
    private String iteminven;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Timestamp regdate;

   @Builder
    public ItemEntity (String itemname, String itemcountry, String itemingred, String itemprice, String itemcontent, String filename1, String original1, String filename2, String original2, String filename3, String original3, String iteminven, Timestamp regdate) {
        this.itemname = itemname;
        this.itemcountry = itemcountry;
        this.itemingred = itemingred;
        this.itemprice = itemprice;
        this.itemcontent = itemcontent;
/*      this.filename1 = filename1;
        this.original1 = original1;
        this.filename2 = filename2;
        this.original2 = original2;
        this.filename3 = filename3;
        this.original3 = original3;*/
        this.iteminven = iteminven;
        this.regdate = regdate;
    }

    public ItemDto toDto() {
        return ItemDto.builder()
                .itemidx(itemidx)
                .itemname(itemname)
                .itemcountry(itemcountry)
                .itemingred(itemingred)
                .itemprice(itemprice)
                .itemcontent(itemcontent)
                .iteminven(iteminven)
                .regdate(regdate)
                .build();
    }

   /* public FilesDto filesDto() {
        return FilesDto.builder()
                .file1(filename1)
                .file2(filename2)
                .file3(filename3)
                .build();
    }*/
}
