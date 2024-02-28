package com.soya.core_spring_security.repository.local;

import com.soya.core_spring_security.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);
}
