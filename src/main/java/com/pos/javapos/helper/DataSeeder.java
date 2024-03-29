package com.pos.javapos.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javafaker.Faker;
import com.pos.javapos.authentication.dto.SignupDto;
import com.pos.javapos.authentication.service.PermissionService;
import com.pos.javapos.authentication.service.RoleService;
import com.pos.javapos.authentication.service.UserService;
import com.pos.javapos.products.dto.CategoryDto;
import com.pos.javapos.products.dto.ProductDto;
import com.pos.javapos.products.service.CategoryService;
import com.pos.javapos.products.service.ProductService;
import com.pos.javapos.shops.dto.BranchDto;
import com.pos.javapos.shops.dto.ShopRequestDto;
import com.pos.javapos.shops.service.BranchService;
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
    private final BranchService branchService;
    private final ProductService productService;
    private final CategoryService categoryService;

    public DataSeeder(UserService userService, RoleService roleService, PermissionService permissionService, ShopService shopService, BranchService branchService, ProductService productService, CategoryService categoryService) {
        this.userService = userService;
        this.roleService = roleService;
        this.permissionService = permissionService;
        this.shopService = shopService;
        this.branchService = branchService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
//        createPermission();
//        createRole();
//        createUser();
//        createShop();
//        createBranch();
//        createCategory();
//        createProduct();

    }

    private void createProduct() throws JsonProcessingException {
        Faker faker = new Faker();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            ProductDto product = new ProductDto();
            product.setType(faker.commerce().material());
            product.setImage(faker.internet().url());
            product.setProductName(faker.commerce().productName() + i);
            product.setDescription(faker.commerce().material());
            product.setPrice(random.nextDouble(1000));
            product = productService.create(product);
            productService.assignProductToCategory(random.nextLong(1,100), product.getId());
        }
    }

    private void createCategory() {
        Faker faker = new Faker();
        for (int i = 0; i< 100; i++){
            CategoryDto category = new CategoryDto();
            category.setName(faker.commerce().material());
            category.setTag(faker.artist().name());
            categoryService.create(category);
        }
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
//            shopService.assignShopToUser(1L,(long) i+1);
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

    private void createBranch() throws JsonProcessingException {
        for (int i = 0; i < 10; i++) {
            BranchDto branchDto = new BranchDto();
            branchDto.setName("Branch "+i);
            branchDto.setAddress("Phnom penh" + i);
            branchDto.setContact("0965066555");
            branchDto.setEmail("branch"+i+"@branch.com");
            branchDto.setLogo("logo");
            branchDto.setDescription("description");
            branchDto.setShop_id((long) i+1);
            branchService.addBranch(branchDto);
        }
    }


}
