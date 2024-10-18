package repository.custom.imp;

import entity.CustomerEntity;
import repository.custom.CustomerDao;
import util.CrudUtil;


import java.sql.SQLException;
import java.util.List;

public class CustomerDaoImp implements CustomerDao {
    @Override
    public boolean save(CustomerEntity entity) {
        System.out.println("Repositoey :"+entity);
        try {
            return CrudUtil.execute("INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)",
                    entity.getId(),
                    entity.getTitle(),
                    entity.getName(),
                    entity.getDob(),
                    entity.getSalary(),
                    entity.getAddress(),
                    entity.getCity(),
                    entity.getProvince(),
                    entity.getPostalCode()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean update(CustomerEntity entity, String s) {
        return false;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }

    @Override
    public List<CustomerEntity> findAll() {
        return List.of();
    }
}
