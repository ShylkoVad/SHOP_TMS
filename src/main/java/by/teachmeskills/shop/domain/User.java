package by.teachmeskills.shop.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "users")
@Entity
public class User extends BaseEntity {

    @NotNull
    @NotBlank(message = "Поле должно быть заполнено!")
    @Pattern(regexp = "^[А-Я][Ёа-яё]+$", message = "Введен неверный формат Имени!")
    @Size(min = 2, message = "Имя не может быть менее 2 символов!")
    @Column(name = "name")
    private String name;

    @NotNull
    @NotBlank(message = "Поле должно быть заполнено!")
    @Pattern(regexp = "^[А-Я][Ёа-яё]+$", message = "Введен неверный формат Фамилии!")
    @Size(min = 2, message = "Фамилия не может быть менее 2 символов!")
    @Column(name = "surname")
    private String surname;

    @NotNull (message = "Поле должно быть заполнено!")
    @Past(message = "Введен неверный формат Даты рождения!")
    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "balance")
    private double balance;

    @NotNull
    @Email(message = "Введен неверный формат email!")
    @NotBlank(message = "Поле должно быть заполнено!")
    @Column(name = "email")
    private String email;

    @NotNull
    @NotBlank(message = "Поле должно быть заполнено!")
    @Pattern(regexp = "\\S+", message = "Пароль не должен содержать пробелы!")
    @Column(name = "password")
    private String password;

    @Column(name = "street")
    private String street;

    @Column(name = "accommodation_number")
    private String accommodation_number;

    @Column(name = "flat_number")
    private String flat_number;

    @Column(name = "phone_number")
    private String phone_number;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Order> orders;
}
