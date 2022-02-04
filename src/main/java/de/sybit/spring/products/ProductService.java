package de.sybit.spring.products;

import de.sybit.spring.exceptions.EntityAlreadyExistsException;
import de.sybit.spring.exceptions.EntityNotExistsException;
import de.sybit.spring.exceptions.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository repository) {
        this.productRepository = repository;
    }

    public Product add(@NonNull Product product) {
        LOG.debug("-> add() product={}", product);
        if (product.getId() != null)
            throw new EntityAlreadyExistsException("Product with ID: " + product.getId() + " already exists");

        Product result = this.productRepository.save(product);
        LOG.debug("<- add() result={}", result);
        return result;
    }

    public Product getById(@NonNull Long id) {
        LOG.debug("-> get() id={}", id);
        Product result = this.productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product with ID: " + id + " not found!"));
        LOG.debug("<- get() result={}", result);
        return result;
    }

    public List<Product> getList() {
        LOG.debug("-> getList()");
        List<Product> result = this.productRepository.findAll();
        LOG.debug("<- getList() result={}", result.size());
        return result;
    }

    public Product update(@NonNull Product product) {
        LOG.debug("-> update() product={}", product);
        if (product.getId() == null)
            throw new EntityNotExistsException("Product does not exists because the ID is null!");

        Product result = this.productRepository.saveAndFlush(product);
        LOG.debug("<- update() result={}", result);
        return result;
    }

    public void delete(@NonNull Product product) {
        LOG.debug("-> delete() product={}", product);
        this.productRepository.delete(product);
        LOG.debug("<- delete()");
    }
}
