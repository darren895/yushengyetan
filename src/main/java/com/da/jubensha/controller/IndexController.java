package com.da.jubensha.controller;

import com.da.jubensha.domain.Role;
import com.da.jubensha.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("")
    public String index(Model model){
        List<Role> roles = roleRepository.findAll(Sort.by(Sort.Order.asc("id")));
        model.addAttribute("roles", roles);
        return "index";
    }

}
