package com.da.jubensha.controller;

import com.da.jubensha.domain.Role;
import com.da.jubensha.domain.UserRole;
import com.da.jubensha.repository.RoleRepository;
import com.da.jubensha.repository.StepRepository;
import com.da.jubensha.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private StepRepository stepRepository;
//    @Autowired
//    private DramaRepository dramaRepository;
//    @Autowired
//    private PlaceRepository placeRepository;
//    @Autowired
//    private MemoryRepository memoryRepository;
//    @Autowired
//    private EvidenceRepository evidenceRepository;

    @PostMapping("myrole")
    public Role myrole(HttpServletRequest request) {
        String sessonId = request.getSession().getId();
        Optional<UserRole> result = this.userRoleRepository.findById(sessonId);
        if (result.isPresent()) {
            UserRole userRole = result.get();
            Integer roleId = userRole.getRoleId();
            Optional<Role> roleResult = this.roleRepository.findById(roleId);
            if (roleResult.isPresent()) {
                return roleResult.get();
            }
        }
        return null;
    }

    @PostMapping("chooseRole")
    public Object chooseRole(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        Integer role = (Integer) param.get("role");
        if(role == null){
            return null;
        }
        String sessonId = request.getSession().getId();
        UserRole userRole = this.userRoleRepository.findByRoleId(role);
        if(userRole != null){
            this.userRoleRepository.deleteByRoleId(role);
        }
        this.userRoleRepository.deleteBySessionId(sessonId);
        if(userRole == null){
            userRole = new UserRole(sessonId, role, 5, 6);
        } else {
            userRole.setSessonId(sessonId);
        }
        userRole = this.userRoleRepository.save(userRole);
        return userRole;
    }


    @PostMapping("getPoint")
    public Integer getPoint(HttpServletRequest request){
        String sessonId = request.getSession().getId();
        Optional<UserRole> result = this.userRoleRepository.findById(sessonId);
        if(!result.isPresent()){
            return -1;
        }
        UserRole userRole = result.get();
        int step = this.stepRepository.findAll().get(0).getStep();
        if(step ==2){
            return userRole.getFirstPoint();
        } else if (step == 3){
            return userRole.getFirstPoint() + userRole.getSecondPoint();
        }
        return 0;
    }


//    @GetMapping("inputDrama")
//    public void inputDrama() {
//        File path = new File("/Users/zhuhuajun/Documents/211-212/羽生夜谈211/线索卡 12CMX8cm  250G 双面印刷");
//        OSSClient ossClient = new OSSClient("oss-cn-hangzhou.aliyuncs.com", "LTAIHkGEWE7MnoqA", "e1ZSkdnk1hli3kJGtz9ipbLFPmVpYA");
////        Map<Integer, Integer> indexCount = new HashMap<>();
//        Integer[] idex = {42};
//        List<Integer> indexs = Arrays.asList(idex);
//        Place place = new Place();
//        place.setId(13);
//        place.setName("深入线索");
//        place.setFirstPlace(false);
//        place.setSecondPlace(false);
////        place = this.placeRepository.save(place);
//        for (File dramaDir : path.listFiles()) {
//            if (!dramaDir.isDirectory()){
//                continue;
//            }
//            for (File file: dramaDir.listFiles()){
//                String name = file.getName();
//                if(!name.endsWith(".jpg")){
//                    continue;
//                }
//                int index = Integer.valueOf(name.replace(".jpg","").replace("卡背","").trim());
//                if (indexs.contains(index)){
////                    if(name.contains("卡背")){
////                        continue;
////                    }
//                    String ossKey = UUID.randomUUID().toString()+".jpg";
//                    ossClient.putObject("jb-drama", ossKey, file);
//                    String url = "https://jb-drama.oss-cn-hangzhou.aliyuncs.com/"+ossKey;
////                    Memory memory = new Memory();
////                    memory.setId(index);
////                    memory.setPicUrl(url);
////                    memory.setState(false);
////                    this.memoryRepository.save(memory);
//                    Optional<Evidence> find = this.evidenceRepository.findById(index);
//                    if(find.isPresent()){
//                        Evidence evidence = find.get();
//                        if(name.contains("卡背")){
//                            evidence.setBgUrl(url);
//                        }else {
//                            evidence.setPicUrl(url);
//                        }
//                        this.evidenceRepository.save(evidence);
//                    } else {
//                        Evidence evidence = new Evidence();
//                        evidence.setChildId(0);
//                        evidence.setPlaceId(place.getId());
//                        evidence.setCost(0);
//                        evidence.setState(true);
//                        evidence.setId(index);
//                        if(name.contains("卡背")){
//                            evidence.setBgUrl(url);
//                        }else {
//                            evidence.setPicUrl(url);
//                        }
//                        this.evidenceRepository.save(evidence);
//                    }
//                }
//            }
//        }
//        ossClient.shutdown();
//    }
//
//    public int switchStep(String name, String roleName){
//        name = name.replace(".jpg", "");
//        int page = Integer.valueOf(name);
//        if(roleName.equals("石川纪田")){
//            if(page <= 11){
//                return 0;
//            } else if (page <=14){
//                return 2;
//            } else {
//                return 3;
//            }
//        } else{
//            if(page <= 7){
//                return 0;
//            } else if (page <=9){
//                return 2;
//            } else {
//                return 3;
//            }
//        }
//    }

}
