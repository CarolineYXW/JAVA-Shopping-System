package com.pudding.final_project;

import com.pudding.final_project.service.AbstractBaseService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ItemService extends AbstractBaseService<Item, Long, ItemRepository> {
    public ItemService(ItemRepository repository) {
        super(repository);
    }

    public List<Item> findByCategory(String category) {
        return repository.findByCategory(category);
    }
} 