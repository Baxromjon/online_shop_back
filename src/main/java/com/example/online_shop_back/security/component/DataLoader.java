package com.example.online_shop_back.security.component;

import com.example.online_shop_back.entity.Region;
import com.example.online_shop_back.entity.Role;
import com.example.online_shop_back.entity.User;
import com.example.online_shop_back.enums.RoleNameEnum;
import com.example.online_shop_back.repository.RegionRepository;
import com.example.online_shop_back.repository.RoleRepository;
import com.example.online_shop_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;


@Slf4j
@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value(value = "${spring.jpa.hibernate.ddl-auto}")
    private String initialMode;

    @Override
    public void run(String... strings) {
        if (roleRepository.findAll().size() == 0) {

            Role adminRole = new Role(RoleNameEnum.ROLE_ADMIN);
            Role userRole = new Role(RoleNameEnum.ROLE_USER);
            List<Role> roles = new ArrayList<>();
            roles.add(adminRole);
            roles.add(userRole);
            roleRepository.saveAll(roles);
        }
        if (userRepository.findAll().size() == 0) {
            User user = new User(
                    "Admin",
                    "Admin",
                    "+998990068005",
                    passwordEncoder.encode("root123"),
                    roleRepository.findByName(RoleNameEnum.ROLE_ADMIN)
            );
            userRepository.save(user);
        }


        if (regionRepository.findAll().size() == 0) {
            List<Region> regions = new ArrayList<>();
            Region andijon = new Region("Andijon");
            Region fargona = new Region("Farg`ona");
            Region namangan = new Region("Namangan");
            Region toshkent = new Region("Toshkent");
            Region toshkent_shaxri = new Region("Toshkent shahri");
            Region sirdaryo = new Region("Sirdaryo");
            Region samarqand = new Region("Samarqand");
            Region jizzax = new Region("Jizzax");
            Region qashqadaryo = new Region("Qashqadaryo");
            Region surxondaryo = new Region("Surxondaryo");
            Region buxoro = new Region("Buxoro");
            Region navoiy = new Region("Navoiy");
            Region xorazm = new Region("Xorazm");
            Region qoraqalpoq = new Region("Qoraqalpog`iston Respublikasi");
            regions.add(andijon);
            regions.add(fargona);
            regions.add(namangan);
            regions.add(toshkent);
            regions.add(toshkent_shaxri);
            regions.add(sirdaryo);
            regions.add(samarqand);
            regions.add(jizzax);
            regions.add(qashqadaryo);
            regions.add(surxondaryo);
            regions.add(buxoro);
            regions.add(navoiy);
            regions.add(xorazm);
            regions.add(qoraqalpoq);
            regionRepository.saveAll(regions);
        }


    }

//        if (roleRepository.findAll().size()==0){
//            List<Role> roles=new ArrayList<>();
//            Role admin=new Role();
//            admin.setName(RoleNameEnum.ROLE_ADMIN);
//            Role user=new Role();
//            user.setName(RoleNameEnum.ROLE_USER);
//            roles.add(admin);
//            roles.add(user);
//            roleRepository.saveAll(roles);
//        }
//        RoleNameEnum[] roleNameEnums = RoleNameEnum.values();
//        List<Role> roles = new ArrayList<>();
//        for (RoleNameEnum nameEnum : roleNameEnums) {
//            roles.add(new Role(nameEnum));
//            roleRepository.saveAll(roles);
//        }
//
//        int size = userRepository.findAll().size();
//
//        if (size == 0) {
//
//        }
//
//
//        if (regionRepository.findAll().size() == 0) {
//
//
//        }

}



