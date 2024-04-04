package project.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import project.shop.entity.CategoryEntity;
import project.shop.entity.FilesEntity;
import project.shop.entity.ItemEntity;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long itemidx;
    private String itemname;
    private String item_type;
    private String itemcountry;
    private String itemingred;
    private String itemprice;
    private String itemcontent;
    private MultipartFile original1;
    private MultipartFile original2;
    private MultipartFile original3;
    private String iteminven;
    private Timestamp regdate;

    public ItemEntity toEntity() {
        return ItemEntity.builder()
                .itemname(itemname)
                .itemcountry(itemcountry)
                .itemingred(itemingred)
                .itemprice(itemprice)
                .itemcontent(itemcontent)
                .iteminven(iteminven)
                .regdate(regdate)
                .build();
    }

    public FilesEntity tofilesEntity(ItemEntity itemEntity) {
        FilesEntity filesEntity = new FilesEntity();
        filesEntity.setOriginal1(original1.getOriginalFilename());
        filesEntity.setOriginal2(original2.getOriginalFilename());
        filesEntity.setOriginal3(original3.getOriginalFilename());
        filesEntity.setItemEntity(itemEntity);
        return filesEntity;
    }

    public CategoryEntity tocategoryEntity(ItemEntity itemEntity) {
        return CategoryEntity.builder()
                .item_type(item_type)
                .build();
    }
}
