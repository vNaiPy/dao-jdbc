package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
        Department obj = new Department(1, "Books");

        Seller seller = new Seller(
                22,
                "Handrei",
                "handrei@gmail.com", new Date(),
                2200.59,
                new Department(1, "Tecnology"));

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println(seller);
    }
}
