package model.dao;

import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public interface SellerDao {

    void insertSeller (Seller seller);
    void updateSeller (Seller seller);
    void deleteSellerByid (Integer id);
    Seller findSellerById (Integer id);
    List<Seller> findAllSellers ();
    List<Seller> findSellerByDepartment(Department department);
}
