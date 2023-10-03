package com.thoughtworks.ecombackend.services;


import com.thoughtworks.ecombackend.dto.ProductDto;
import com.thoughtworks.ecombackend.models.Product;
import com.thoughtworks.ecombackend.repositories.ProductRepository;
import com.thoughtworks.ecombackend.utils.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> findAll() {
        final List<Product> products = productRepository.findAll(Sort.by("id"));
        return products.stream()
                .map((product) -> mapToDTO(product, new ProductDto()))
                .collect(Collectors.toList());
    }

    public ProductDto get(final String id) {
        return productRepository.findById(id)
                .map(product -> mapToDTO(product, new ProductDto()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final ProductDto productDTO) {
        final Product product = new Product();
        mapToEntity(productDTO, product);
        return productRepository.save(product).getId();
    }

    public void update(final String id, final ProductDto productDTO) {
        final Product product = productRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(productDTO, product);
        productRepository.save(product);
    }

    public void delete(final String id) {
        productRepository.deleteById(id);
    }

    private ProductDto mapToDTO(final Product product, final ProductDto productDTO) {
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategory(product.getCategory());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setManufacturer(product.getManufacturer());
        productDTO.setAvailableItems(product.getAvailableItems());
        productDTO.setImageUrl(product.getImageUrl());
        return productDTO;
    }

    private Product mapToEntity(final ProductDto productDTO, final Product product) {
        product.setName(productDTO.getName());
        product.setCategory(productDTO.getCategory());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setManufacturer(productDTO.getManufacturer());
        product.setAvailableItems(productDTO.getAvailableItems());
        product.setImageUrl(productDTO.getImageUrl());
        return product;
    }

}
