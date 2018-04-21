/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service.impl;

import gr.aegean.eIdEuSmartClass.model.dao.GenderRepository;
import gr.aegean.eIdEuSmartClass.model.dao.RoleRepository;
import gr.aegean.eIdEuSmartClass.model.dao.UserRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.Gender;
import gr.aegean.eIdEuSmartClass.model.dmo.Role;
import gr.aegean.eIdEuSmartClass.model.dmo.User;
import gr.aegean.eIdEuSmartClass.model.service.UserService;
import gr.aegean.eIdEuSmartClass.utils.enums.RolesEnum;
import gr.aegean.eIdEuSmartClass.utils.pojo.BaseResponse;
import gr.aegean.eIdEuSmartClass.utils.wrappers.DateWrappers;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 *
 * @author nikos
 */
@Service
public class UserServiceImpl implements UserService {

    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private GenderRepository genderRepo;

    @Override
    @Transactional
    public BaseResponse saveUser(User user) {
        try {
            userRepo.save(user);
            return new BaseResponse(BaseResponse.SUCCESS);
        } catch (Error e) {
            log.info("ERROR Saving user " + user.toString());
            log.error("ERROR", e);
        }

        return new BaseResponse(BaseResponse.FAILED);
    }

    /*
     Saves or Updates the fields on the user
     */
    @Override
    @Transactional
    @Modifying 
    public BaseResponse saveOrUpdateUser(String eIDASid, String name, String surname, String gend, String dateOfBirth,
            String email, String mobile, String affiliation, String country, String adId, String userPrincipal, String engName, String engSurname) {
        try {
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthDay = DateWrappers.parseEidasDate(dateOfBirth);//LocalDate.parse(dateOfBirth, formatter);

            Optional<User> user = userRepo.findFirstByEIDASId(eIDASid);
            Optional<Role> role = roleRepo.findFirstByName(Role.UNREGISTERED);
            Optional<Gender> gender = genderRepo.findFirstByName(Gender.UNSPECIFIED);
            if (!user.isPresent()) {
                if (role.isPresent() && gender.isPresent()) {
                    user = Optional.of(new User(role.get(), eIDASid, name, surname, email, mobile, affiliation, 
                            country, gender.get(), birthDay,
                            Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()),engName,engSurname) );
                    userRepo.save(user.get());

                } else {
                    throw new NullPointerException("Role or Gender was not founde for unregeistered/undefined");
                }
            } else {
//                user.get().setName(name);
//                user.get().setSurname(surname);
                user.get().setLastLogin(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
//                user.get().setBirthday(birthDay);
                user.get().setAffiliation(affiliation);
                user.get().setCountry(country);
                user.get().setEmail(email);
                if (gender.isPresent()) {
                    user.get().setGender(gender.get());
                }
                if (!StringUtils.isEmpty(adId)) {
                    user.get().setAdId(adId);
                }
                user.get().setPrincipal(userPrincipal);
                userRepo.save(user.get());
            }
            return new BaseResponse(BaseResponse.SUCCESS);

        } catch (Error e) {
            log.info("ERROR::" + e);
        }
        return new BaseResponse(BaseResponse.FAILED);
    }

    @Override
    @Transactional
    @Modifying
    public BaseResponse updateLogin(String eIDasid) {
        if (userRepo.findFirstByEIDASId(eIDasid).isPresent()) {
            try {
//                Timestamp time = Timestamp.valueOf(LocalDateTime.now());
                userRepo.updateLastLoginByeIDASID(eIDasid, DateWrappers.getNowTimeStamp());
                return new BaseResponse(BaseResponse.SUCCESS);
            } catch (Error e) {
                log.error("ERROR", e);
            }
        }
        return new BaseResponse(BaseResponse.FAILED);
    }

    @Override
    @Transactional
    public Optional<User> findByEid(String eID) {
        return userRepo.findFirstByEIDASId(eID);
    }

    @Override
    @Transactional
    public List<User> findAllUIdentified() {
        return userRepo.findAll().stream().filter(user -> {
            return user.getRole().getName().equals(RolesEnum.UNIDENTIFIED.role());
        }).collect(Collectors.toList());

    }

    @Override
    @Transactional
    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

}
