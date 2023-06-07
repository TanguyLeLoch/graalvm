package com.natu.graalvm.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserRepositorySpringData extends JpaRepository<UserInfra, Long> {

    UserInfra save(UserInfra user);
}
