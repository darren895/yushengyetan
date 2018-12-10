package com.da.jubensha.controller;

import com.da.jubensha.domain.Evidence;
import com.da.jubensha.domain.Place;
import com.da.jubensha.domain.UserEvidence;
import com.da.jubensha.domain.UserRole;
import com.da.jubensha.repository.EvidenceRepository;
import com.da.jubensha.repository.PlaceRepository;
import com.da.jubensha.repository.StepRepository;
import com.da.jubensha.repository.UserEvidenceRepository;
import com.da.jubensha.repository.UserRoleRepository;
import com.da.jubensha.util.RoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class EvidenceController {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private EvidenceRepository evidenceRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserEvidenceRepository userEvidenceRepository;

    @Autowired
    private UserController userController;

    @PostMapping("findSearchPlace")
    public List<Place> findSearchPlace(HttpServletRequest request) {
        String sessonId = request.getSession().getId();
        Optional<UserRole> result = this.userRoleRepository.findById(sessonId);
        int roleId = 0;
        if(result.isPresent()) {
            UserRole userRole = result.get();
            roleId = userRole.getRoleId();
        }
        if(roleId == 0){
            return null;
        }
        int step = this.stepRepository.findAll().get(0).getStep();
        if (step == 2) {
            List<Place> places = placeRepository.findFirstStep();
            if(places != null){
                int finalRoleId = roleId;
                places = places.stream().filter(place -> !RoleUtil.ignore(finalRoleId, place.getId())).collect(Collectors.toList());
            }
            return places;
        } else if (step == 3) {
            List<Place> places = placeRepository.findSecondStep();
            if(places != null){
                int finalRoleId1 = roleId;
                places = places.stream().filter(place -> !RoleUtil.ignore(finalRoleId1, place.getId())).collect(Collectors.toList());
            }
            return places;
        }
        return null;
    }

    @PostMapping("findAllPlace")
    public List<Place> findAllPlace() {
        List<Place> places = placeRepository.findAll(Sort.by(Sort.Order.asc("id")));
        places = places.stream().filter(place -> place.getId() < 15).collect(Collectors.toList());
        return places;
    }

    @PostMapping("findEvidence/{placeId}")
    public List<Evidence> findEvidence(@PathVariable("placeId") Integer placeId) {
        List<Evidence> list = this.evidenceRepository.findEvidenceByPlace(placeId);
        return list;
    }

    @PostMapping("findMyEvidence/{placeId}")
    public List<UserEvidence> findMyEvidence(@PathVariable("placeId") Integer placeId, HttpServletRequest request){
        String sessonId = request.getSession().getId();
        Optional<UserRole> result = this.userRoleRepository.findById(sessonId);
        if(result.isPresent()){
            UserRole userRole = result.get();
            List<UserEvidence> userEvidences = this.userEvidenceRepository.findByPlaceAndRole(placeId, userRole.getRoleId());
            if (RoleUtil.sameMap.get(placeId) != null){
                List<UserEvidence> userEvidence2 = this.userEvidenceRepository.findByPlaceAndRole(RoleUtil.sameMap.get(placeId), userRole.getRoleId());
                if(userEvidences != null && userEvidence2 != null){
                    userEvidences.addAll(userEvidence2);
                }
            }
            if(userEvidences != null){
                Collections.sort(userEvidences, Comparator.comparing(o -> Integer.valueOf(o.getId())));
            }
            return userEvidences;
        }
        return null;
    }

    @PostMapping("deepEvi/{id}")
    public Map<String, Object> deepEvi(@PathVariable("id") Integer id, HttpServletRequest request){
        String sessonId = request.getSession().getId();
        Optional<UserRole> result = this.userRoleRepository.findById(sessonId);
        Map<String, Object> map = new HashMap<>();
        if (!result.isPresent()) {
            map.put("result", -1);
            return map;
        }
        UserRole userRole = result.get();
        int point = this.userController.getPoint(request);
        Optional<UserEvidence> userEviResult = this.userEvidenceRepository.findById(id);
        int step = this.stepRepository.findAll().get(0).getStep();
        if(userEviResult.isPresent()){
            UserEvidence oldEvi = userEviResult.get();
            if(!oldEvi.getChildId().equals(0)){
                Optional<Evidence> evidenceReuslt = this.evidenceRepository.findById(oldEvi.getChildId());
                if(evidenceReuslt.isPresent()){
                    Evidence evidenceInfo = evidenceReuslt.get();
                    if(step == 3 || !evidenceInfo.getCost().equals(0)){
                        if (evidenceInfo.getCost().equals(0) || point >0){
                            Evidence evidence = this.evidenceRepository.chooseEvi(oldEvi.getChildId());
                            if (evidence != null) {
                                UserEvidence userEvidence = new UserEvidence();
                                userEvidence.setEvidenceId(evidence.getId());
                                userEvidence.setPlaceId(evidence.getPlaceId());
                                userEvidence.setRoleId(userRole.getRoleId());
                                userEvidence.setUrl(evidence.getPicUrl());
                                userEvidence.setChildId(evidence.getChildId());
                                userEvidence.setStep(step);
                                this.userEvidenceRepository.save(userEvidence);
                                if (evidence.getCost() > 0) {
                                    if (userRole.getFirstPoint() > 0) {
                                        userRole = this.userRoleRepository.descFirst(userRole.getRoleId());
                                    } else {
                                        userRole = this.userRoleRepository.descSecond(userRole.getRoleId());
                                    }
                                }
                                oldEvi.setChildId(0);
                                this.userEvidenceRepository.save(oldEvi);
                                map.put("evidence", evidence);
                                map.put("result", 1);
                                map.put("point", this.userController.getPoint(request));
                                return map;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    @PostMapping("chooseEvi/{id}")
    public Map<String, Object> chooseEvi(@PathVariable("id") Integer id, HttpServletRequest request) {
        String sessonId = request.getSession().getId();
        Optional<UserRole> result = this.userRoleRepository.findById(sessonId);
        Map<String, Object> map = new HashMap<>();
        if (!result.isPresent()) {
            map.put("result", -1);
            return map;
        }
        int step = this.stepRepository.findAll().get(0).getStep();
        UserRole userRole = result.get();
        int point = this.userController.getPoint(request);
        Optional<Evidence> evidenceReuslt = this.evidenceRepository.findById(id);
        Evidence evidenceInfo = null;
        if(evidenceReuslt.isPresent()){
            evidenceInfo = evidenceReuslt.get();
        }
        if (evidenceInfo == null || (evidenceInfo.getCost() >0 && point <= 0)) {
            map.put("result", 0);
            map.put("message", "你的行动点已经为空");
            return map;
        }
        int count = this.userEvidenceRepository.countPlace(evidenceInfo.getPlaceId(), userRole.getRoleId(), step);
        if(count >=2){
            map.put("result", 0);
            map.put("message", "每个场所每轮只能搜两次");
            return map;
        }
        Evidence evidence = this.evidenceRepository.chooseEvi(id);
        if (evidence != null) {
            UserEvidence userEvidence = new UserEvidence();
            userEvidence.setEvidenceId(evidence.getId());
            userEvidence.setPlaceId(evidence.getPlaceId());
            userEvidence.setRoleId(userRole.getRoleId());
            userEvidence.setUrl(evidence.getPicUrl());
            userEvidence.setChildId(evidence.getChildId());
            userEvidence.setStep(step);
            this.userEvidenceRepository.save(userEvidence);
            if (evidence.getCost() > 0) {
                if (userRole.getFirstPoint() > 0) {
                    userRole = this.userRoleRepository.descFirst(userRole.getRoleId());
                } else {
                    userRole = this.userRoleRepository.descSecond(userRole.getRoleId());
                }
            }
            map.put("evidence", evidence);
            map.put("result", 1);
            map.put("point", this.userController.getPoint(request));
        } else {
            map.put("result", 0);
            map.put("message", "线索已经被搜走");
        }
        return map;
    }

    @PostMapping("changeEvi")
    public Map<String, Object> changeEvi(@RequestBody Map<String, Object> param, HttpServletRequest request){
        Integer userEviId = (Integer) param.get("id");
        Integer changeRoleId = (Integer) param.get("roleId");
        String sessonId = request.getSession().getId();
        Map<String, Object> resp = new HashMap<>();
        Optional<UserRole> result = this.userRoleRepository.findById(sessonId);
        if(result.isPresent()){
            UserEvidence userEvi = this.userEvidenceRepository.changeEvidence(userEviId, changeRoleId, result.get().getRoleId());
            if(userEvi != null){
                resp.put("result", 1);
                return resp;
            }
        }
        resp.put("result", 0);
        return resp;
    }

}
