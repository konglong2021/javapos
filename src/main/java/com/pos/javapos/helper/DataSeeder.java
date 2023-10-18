package com.pos.javapos.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pos.javapos.authentication.dto.SignupDto;
import com.pos.javapos.authentication.service.PermissionService;
import com.pos.javapos.authentication.service.RoleService;
import com.pos.javapos.authentication.service.UserService;
import com.pos.javapos.shops.dto.ShopDto;
import com.pos.javapos.shops.dto.ShopRequestDto;
import com.pos.javapos.shops.service.ShopService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Random;

@Component
public class DataSeeder implements CommandLineRunner {
    private final UserService userService;
    private final RoleService roleService;
    private final PermissionService permissionService;
    private final ShopService shopService;

    public DataSeeder(UserService userService, RoleService roleService, PermissionService permissionService, ShopService shopService) {
        this.userService = userService;
        this.roleService = roleService;
        this.permissionService = permissionService;
        this.shopService = shopService;
    }

    @Override
    public void run(String... args) throws Exception {
        createPermission();
        createRole();
        createUser();
        createShop();
    }

    private void createShop() throws JsonProcessingException {
        for (int i = 0;i<10;i++){
            ShopRequestDto shopRequestDto = new ShopRequestDto();
            shopRequestDto.setName("Shop "+i);
            shopRequestDto.setAddress("Phnom penh");
            shopRequestDto.setEmail("shop"+i+"@shop.com");
            shopRequestDto.setContact("0965066555");
            shopRequestDto.setOwner("1");
            shopService.addShop(shopRequestDto);
            shopService.assignShopToUser(1L,(long) i+1);
        }
    }

    private void createPermission() {
        Arrays.asList("user_access", "user_create", "user_update", "user_delete", "shop_access", "shop_create", "shop_update", "shop_delete").forEach(permissionService::addPermission);
    }

    protected void createRole() {
        Arrays.asList("Admin", "User", "Staff","Employee").forEach(roleService::addRole);
        Random random = new Random();
        for (int i = 0; i < 8; i++)
        {
            roleService.addPermissionToRole(random.nextLong(4) + 1, random.nextLong(8) +1 );
        }
    }

    private void createUser() throws JsonProcessingException {
        for (int i = 0; i < 10; i++) {
            SignupDto signupDto = new SignupDto();
            signupDto.setUsername("test" + i);
            signupDto.setPassword("password");
            userService.createUser(signupDto);
            Random random = new Random();
            userService.addRoleToUser((long) i, random.nextLong(4));
        }
    }

}
