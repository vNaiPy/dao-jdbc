package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("--------- Teste 1 ---------");
        Seller seller = sellerDao.findSellerById(3);
        System.out.println(seller);

        System.out.println("--------- Teste 2 ---------");
        Department department = new Department(2, null);
        List<Seller> sellerList = sellerDao.findSellerByDepartment(department);
        System.out.println(sellerList);

        for(Seller obj : sellerList){
            System.out.println(obj);
        }
    }
}
