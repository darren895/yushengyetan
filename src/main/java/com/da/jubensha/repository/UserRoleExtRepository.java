package com.da.jubensha.repository;

import com.da.jubensha.domain.UserRole;

public interface UserRoleExtRepository {

    UserRole descFirst(Integer roleId);

    UserRole descSecond(Integer roleId);

    long deleteBySessionId(String sessionId);

    long deleteByRoleId(Integer roleId);
}
