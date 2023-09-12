package by.teachmeskills.shop.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "images")
public class Image extends BaseEntity {
    @NotBlank(message = "Поле не может быть пустым!")
    @Column(name = "image_path")
    private String imagePath;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_Id", nullable = false)
    private Product product;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotBlank(message = "Поле не может быть пустым!")
    @Column(name = "primary_image")
    private int primaryImage;
}
