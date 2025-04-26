package org.romanzhula.inventory_service.repositories;

import org.romanzhula.inventory_service.models.Inventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InventoryRepository extends CrudRepository<Inventory, String> {
}
