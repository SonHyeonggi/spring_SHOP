package project.shop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category_shop_t")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "category_shop_seq", sequenceName = "category_shop_seq", allocationSize = 1, initialValue = 1)
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_shop_seq")
    @Column(name = "category_idx")
    private Long category_idx;

    @OneToMany(mappedBy = "categoryEntity", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemEntity> itemEntities = new ArrayList<>();

    @Setter
    @Column(name= "category_code")
    private String category_code;

    @Setter
    @Column(name = "item_type")
    private String item_type;

    @Builder
    public CategoryEntity(String item_type) {
        this.item_type = item_type;
    }
}
