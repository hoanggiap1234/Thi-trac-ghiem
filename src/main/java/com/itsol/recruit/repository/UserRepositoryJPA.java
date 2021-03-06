package com.itsol.recruit.repository;

import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.repoext.UserRepositoryExt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepositoryJPA extends JpaRepository<User, Long>, UserRepositoryExt {

    User findByUserName(String userName);

    Optional<User> findByEmail(String username);

    User findByEmailAndBirthDay(String email, Date birthDay);

}
