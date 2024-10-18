package repository.custom.imp;

import entity.ItemEntity;
import repository.custom.ItemDao;

import java.util.List;

public class ItemDaoImp implements ItemDao {
    @Override
    public boolean save(ItemEntity entity) {
        return false;
    }

    @Override
    public boolean update(ItemEntity entity, String s) {
        return false;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }

    @Override
    public List<ItemEntity> findAll() {
        return List.of();
    }
}
