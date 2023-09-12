package by.teachmeskills.shop.repositories;

import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.exceptions.EntityOperationException;

public interface UserRepository extends BaseRepository<User> {
    User findById(int id);

    User findByEmailAndPassword(String email, String password);

    User findByEmail(String email);
}
