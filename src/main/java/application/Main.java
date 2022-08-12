package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("--------- Teste findSellerById ---------");
        Seller seller = sellerDao.findSellerById(3);
        System.out.println(seller);

        System.out.println("--------- Teste findSellerByDepartment ---------");
        Department department = new Department(2, null);
        List<Seller> sellerList = sellerDao.findSellerByDepartment(department);
        System.out.println(sellerList);
        for(Seller obj : sellerList){
            System.out.println(obj);
        }

        System.out.println("--------- Teste 3 findAllSeller ---------");
        List<Seller> allSellers = sellerDao.findAllSellers();
        for(Seller obj : allSellers){
            System.out.println(obj);
        }

        System.out.println("--------- Teste 4 insertSeller ---------");
        Seller newSeller = new Seller(null, "Daniel", "daniel@gmail.com", new Date(), 2400.60, department);
        sellerDao.insertSeller(newSeller);
        System.out.println("Inserted! New id = " + newSeller.getId());
    }
}
