package com.da.jubensha.controller;

import com.da.jubensha.domain.Memory;
import com.da.jubensha.domain.UserRole;
import com.da.jubensha.repository.MemoryRepository;
import com.da.jubensha.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
public class MemoryContoller {

    @Autowired
    private MemoryRepository memoryRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @PostMapping("findMemory")
    public List<Memory> findMemory(HttpServletRequest request){
        String sessonId = request.getSession().getId();
        Optional<UserRole> userRole = userRoleRepository.findById(sessonId);
        if(userRole.isPresent()) {
            int roleId = userRole.get().getRoleId();
            if(roleId != 4){
                return null;
            }
            List<Memory> memories = this.memoryRepository.findByState(true);
            return memories;
        }
        return null;
    }
}
