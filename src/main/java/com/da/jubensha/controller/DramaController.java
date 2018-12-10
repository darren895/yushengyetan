package com.da.jubensha.controller;

import com.da.jubensha.domain.Drama;
import com.da.jubensha.domain.UserRole;
import com.da.jubensha.repository.DramaRepository;
import com.da.jubensha.repository.StepRepository;
import com.da.jubensha.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
public class DramaController {

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private DramaRepository dramaRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @PostMapping("getDrama")
    public List<Drama> getDrama(HttpServletRequest request){
        String sessonId = request.getSession().getId();
        Optional<UserRole> userRole = userRoleRepository.findById(sessonId);
        if(userRole.isPresent()){
            int roleId = userRole.get().getRoleId();
            int step = stepRepository.findAll().get(0).getStep();
            List<Drama> dramas = this.dramaRepository.findWithStep(roleId, step);
            return dramas;
        }
        return null;
    }
}
