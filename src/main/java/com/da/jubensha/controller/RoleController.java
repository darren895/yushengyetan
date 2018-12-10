package com.da.jubensha.controller;

import com.da.jubensha.domain.Role;
import com.da.jubensha.domain.UserRole;
import com.da.jubensha.repository.RoleRepository;
import com.da.jubensha.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @PostMapping("roles")
    public List<Role> roles(){
        List<Role> roles = this.roleRepository.findAll(Sort.by(Sort.Order.asc("id")));
        return roles;
    }

    @PostMapping("otherRoles")
    public List<Role> otherRoles(HttpServletRequest request){
        String sessonId = request.getSession().getId();
        Optional<UserRole> result = this.userRoleRepository.findById(sessonId);
        if(result.isPresent()){
            List<Role> roles = this.roleRepository.findAll(Sort.by(Sort.Order.asc("id")));
            roles = roles.stream().filter(role -> role.getId() != result.get().getRoleId()).collect(Collectors.toList());
            return roles;
        }
        return null;
    }

//    @GetMapping("insert")
//    public String insert(@RequestParam String name){
//        Role role = new Role();
//        role.setName(name);
//        this.roleRepository.save(role);
//        return "ok";
//    }

}
