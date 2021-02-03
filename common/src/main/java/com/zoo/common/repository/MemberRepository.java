package com.zoo.common.repository;

import com.zoo.common.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Long countByUsernameOrPhone(String userName, String Phone);
    Member findByPhone(String phone);

}
