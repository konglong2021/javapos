package com.pos.javapos.shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.javapos.authentication.repository.UserRepository;
import com.pos.javapos.shops.controller.ShopController;
import com.pos.javapos.shops.dto.ShopDto;
import com.pos.javapos.shops.dto.ShopRequestDto;
import com.pos.javapos.shops.dto.ShopResponseDto;
import com.pos.javapos.shops.service.ShopService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@WebMvcTest(value = ShopController.class,
excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ShopControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopService shopService;

    @MockBean
    private UserRepository userRepository;

    public ShopControllerTest(ShopService shopService) {
        this.shopService = shopService;
    }

    @Test
    @DisplayName("Should test add shop")
    public void testAddShop() throws Exception {
        //Arrange
        ShopRequestDto shopRequestDto = new ShopRequestDto();
        shopRequestDto.setName("Shop test");
        shopRequestDto.setShop_object(null);
        shopRequestDto.setEmail("nD6Xj@example.com");
        shopRequestDto.setContact("0123456789");
        shopRequestDto.setAddress("Address test");
        shopRequestDto.setOwner("1");

        ShopResponseDto shopResponseDto = new ShopResponseDto();
        when(shopService.addShop(shopRequestDto)).thenReturn(shopResponseDto);


        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/shop")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                //.header("Authorization", "Bearer " + "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0ZXN0MSIsInN1YiI6IjIiLCJleHAiOjE3MDg0MTYzNTMsImlhdCI6MTcwODMyOTk1M30.LsoDBtIuw4BdsMg12ffXawSO92HOpfPCt106IPnoW7yYDqAiHiEKj9JxgzsGxnA9Q11PlYhU09oV60aUWpWHoQqWAPGjpW98dtRdFh3JfL0CFQPknGVLozp5084LIdrqjkCato3IlXwOIr53Vu2ph6DBBUpm2TxtOQVRisNnB9bnfppbSrod1EyEzpjj85_IZaZ3zR91VeUzcNXLWUuo0BwlnC50-lijPIZK-m0Qwo5_olt1l9vZw6yShRQx3nUMw7HP5XfnQTGOmg5_yC9PFM35SqDxyosxYZT9K_hPC7MOYhEqxNJ57znWzw1QbnwgkBSo-a_2X47-z2BJiS3ljA")
                .content(new ObjectMapper().writeValueAsString(shopRequestDto));

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        Map<String, Object> dataMap = new ObjectMapper().readValue(responseBodyAsString, Map.class);

        // Extract the "data" field into another Map:
        Map<String, Object> shopData = (Map<String, Object>) dataMap.get("data");

        ShopRequestDto createdShop = new ShopRequestDto();
        createdShop.setName(shopData.get("name").toString());


        //Assert
        Assertions.assertEquals(shopRequestDto.getName(), createdShop.getName());
    }

    @Test
    public void testGetShopsByUserId() throws Exception {
        when(shopService.getShopsByUserId(1L)).thenReturn(List.of(new ShopDto()));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/shop/user/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();

    }

    @Test
    public void testShopServiceGetShopById() throws Exception {
        ShopDto shopDto = shopService.getShopById(1L);
        Assertions.assertNotNull(shopDto);
    }
}
