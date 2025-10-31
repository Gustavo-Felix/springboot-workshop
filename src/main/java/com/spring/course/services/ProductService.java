package com.spring.course.services;

import com.spring.course.entities.Category;
import com.spring.course.entities.Product;
import com.spring.course.repositories.CategoryRepository;
import com.spring.course.repositories.ProductRepository;
import com.spring.course.services.exceptions.DatabaseException;
import com.spring.course.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Product insert(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        try {
            if (productRepository.existsById(id)) {
                productRepository.deleteById(id);
            } else {
                throw new ResourceNotFoundException(id);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Product update(Long id, Product newProduct) {
        try {
            Product entity = productRepository.getReferenceById(id);
            updateData(entity, newProduct);
            return productRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Product entity, Product newProduct) {
        entity.setName(newProduct.getName());
        entity.setDescription(newProduct.getDescription());
        entity.setPrice(newProduct.getPrice());
        entity.setImgUrl(newProduct.getImgUrl());

        entity.getCategories().clear();

        for (Category category : newProduct.getCategories()) {
            entity.getCategories().add(category);
        }
    }

}
