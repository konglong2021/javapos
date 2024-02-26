package com.pos.javapos.products.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pos.javapos.authentication.dto.CurrentUserDto;
import com.pos.javapos.helper.CurrentUserInfo;
import com.pos.javapos.helper.CustomIdGenerator;
import com.pos.javapos.products.dto.ProductDto;
import com.pos.javapos.products.entity.Category;
import com.pos.javapos.products.entity.Product;
import com.pos.javapos.products.mapper.ProductMapper;
import com.pos.javapos.products.repository.CategoryRepository;
import com.pos.javapos.products.repository.ProductRepository;
import com.pos.javapos.products.service.ProductService;
import com.pos.javapos.shops.entity.Branch;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.pos.javapos.helper.Helper.PRODUCT_PREFIX;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CurrentUserInfo currentUserInfo;

    private ProductMapper productMapper;

    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    @Cacheable("products")
    public Page<ProductDto> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Product> products = productRepository.findAll(pageRequest);
        return new PageImpl<>(products.getContent().stream().map(product ->
                productMapper.fromProductToDto(product)).
                collect(Collectors.toList()),pageRequest,products.getTotalElements());
    }

    @Override
    public ProductDto show(Long id) {
        return productRepository.findById(id).map(product -> productMapper.fromProductToDto(product)).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public ProductDto create(ProductDto productDto){
        try {
            existedProduct(productDto);
            CurrentUserDto currentUser = currentUserInfo.getCurrentUser();
            String lastId = productRepository.findLastId();
            Product product = productMapper.fromDtoToProduct(productDto);

            CustomIdGenerator customIdGenerator = new CustomIdGenerator(lastId,PRODUCT_PREFIX);
            product.setSku(customIdGenerator.generateId());

            if(currentUser != null){
                product.setShops(currentUser.getShop());
                product.setBranches(currentUser.getBranch());
            }
            product = productRepository.save(product);
            return productMapper.fromProductToDto(product);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProductDto update(Long id,ProductDto productDto) throws JsonProcessingException {
        existedProduct(productDto);
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product = productMapper.fromDtoToProduct(productDto);
        product.setId(id);
        productRepository.save(product);
        return productMapper.fromProductToDto(product);
    }

    @Override
    public Boolean delete(Long id) {
        try {
            productRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    @Transactional
    public void assignProductToCategory(Long categoryId, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        product.addCategory(category);
    }

    @Override
    public Page<ProductDto> findProductsByCategory(Long categoryId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        CurrentUserDto currentUser = currentUserInfo.getCurrentUser();
        Branch branch = currentUser.getBranch();
        Page<Product> products = productRepository.findAllByCategoryIdAndBranches(categoryId,branch,pageRequest);

        return new PageImpl<>(products.getContent().stream().map(product ->
                        productMapper.fromProductToDto(product)).
                collect(Collectors.toList()),pageRequest,products.getTotalElements());
    }

    @Override
    public Page<ProductDto> findProductsByBranch(Long branchId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Product> products = productRepository.findAllByBranchId(branchId,pageRequest);
        return new PageImpl<>(products.getContent().stream().map(product ->
                productMapper.fromProductToDto(product)).
                collect(Collectors.toList()),pageRequest,products.getTotalElements());

    }

    private void existedProduct(ProductDto productDto){
        String existed = productRepository.existsByProductName(productDto.getProductName());
        if (existed !=null){
            throw new RuntimeException("Product already existed");
        }
    }


}
