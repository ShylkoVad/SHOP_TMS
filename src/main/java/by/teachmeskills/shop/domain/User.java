package by.teachmeskills.shop.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @NotBlank(message = "Поле должно быть заполнено!")
//    @Pattern(regexp = "^[А-Я][Ёа-яё]+$", message = "Введен неверный формат Имени!")
    @Size(min = 2, message = "Имя не может быть менее 2 символов!")
    private String name;

    @NotBlank(message = "Поле должно быть заполнено!")
//    @Pattern(regexp = "^[А-Я][Ёа-яё]+$", message = "Введен неверный формат Фамилии!")
    @Size(min = 2, message = "Фамилия не может быть менее 2 символов!")
    private String surname;

    @NotBlank(message = "Поле должно быть заполнено!")
    @Past(message = "Введен неверный формат Даты рождения!")
    private LocalDate birthday;
    private double balance;

    @Email(message = "Введен неверный формат email!")
    @NotBlank(message = "Поле должно быть заполнено!")
    private String email;

    @NotBlank(message = "Поле должно быть заполнено!")
    @Pattern(regexp = "\\S+", message = "пароль не должен содержать пробелы")
//@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$", message = "Введен неверный пароль!")
    private String password;

    private String street;
    private String accommodation_number;
    private String flat_number;
    private String phone_number;
}
