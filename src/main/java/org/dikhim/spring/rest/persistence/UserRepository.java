package org.dikhim.spring.rest.persistence;

import org.dikhim.spring.rest.model.User;
import org.graalvm.compiler.lir.LIRInstruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    void deleteById(long id);
}
