package com.rahul.authservice.repositories;

import com.rahul.authservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    Optional<User> findById(Long aLong);


    Optional<User> findByEmail(String email);

    @Override
    User save(User user);
    /*
    save method works as both insert and update
    -> If the input parameter has id already present then it act like a update
    -> If the input parameter doesn't have id then it act like a insert
     */


}
