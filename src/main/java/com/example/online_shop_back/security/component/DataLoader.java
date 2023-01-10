package com.example.online_shop_back.security.component;

import com.example.online_shop_back.entity.*;
import com.example.online_shop_back.enums.OrderStatus;
import com.example.online_shop_back.enums.RoleNameEnum;
import com.example.online_shop_back.repository.*;
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

    @Autowired
    OrderStatusRepository orderStatusRepository;

    @Autowired
    MonthRepository monthRepository;

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
        if (orderStatusRepository.findAll().size() == 0) {
            OrderStatusClass newOrder = new OrderStatusClass(OrderStatus.NEW);
            OrderStatusClass accepted = new OrderStatusClass(OrderStatus.ACCEPTED);
            OrderStatusClass inProgress = new OrderStatusClass(OrderStatus.IN_PROGRESS);
            OrderStatusClass completed = new OrderStatusClass(OrderStatus.COMPLETED);
            OrderStatusClass canceled = new OrderStatusClass(OrderStatus.CANCELED);
            List<OrderStatusClass> list = new ArrayList<>();
            list.add(newOrder);
            list.add(accepted);
            list.add(inProgress);
            list.add(completed);
            list.add(canceled);
            orderStatusRepository.saveAll(list);
//            OrderStatus[] orderStatuses=OrderStatus.values();
//            List<OrderStatus> list=new ArrayList<>();
//            for (OrderStatus orderStatus:orderStatuses){
//                list.add(orderStatus);
//                orderStatusRepository.saveAll(list);
//            }
        }
        if (monthRepository.findAll().size() == 0) {
            List<Month> monthList = new ArrayList<>();
            Month three = new Month(3);
            Month six = new Month(6);
            Month nine = new Month(9);
            Month twelve = new Month(12);
            monthList.add(three);
            monthList.add(six);
            monthList.add(nine);
            monthList.add(twelve);
            monthRepository.saveAll(monthList);
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

}



