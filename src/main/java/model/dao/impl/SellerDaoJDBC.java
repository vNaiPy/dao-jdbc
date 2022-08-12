package model.dao.impl;

import db.DB;
import db.DBException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC (Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertSeller(Seller seller) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                    "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, seller.getName());
            st.setString(2, seller.getEmail());
            st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            st.setDouble(4, seller.getBaseSalary());
            st.setInt(5, seller.getDepartment().getId());

            int rowsAffected = st.executeUpdate();

            if(rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    seller.setId(id);
                }
                DB.closeResultSet(rs);
            }
            else {
                throw new DBException("Erro ao inserir um novo 'Seller'!");
            }
        }
        catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void updateSeller(Seller seller) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE seller " +
                    "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?  " +
                    "WHERE Id = ?");
            st.setString(1, seller.getName());
            st.setString(2, seller.getEmail());
            st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            st.setDouble(4, seller.getBaseSalary());
            st.setInt(5, seller.getDepartment().getId());
            st.setInt(6, seller.getId());

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteSellerByid(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM seller WHERE seller.Id = ?");
            st.setInt(1, id);

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public Seller findSellerById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DpName " +
                            "FROM seller " +
                            "INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "WHERE seller.Id = ?");
            st.setInt(1, id);

            rs = st.executeQuery();
            if(rs.next()) {
                Department dep = instantiateDepartment(rs);
                Seller seller = instantiateSeller(rs, dep);
                return seller;
            }
            return null;
        }
        catch (SQLException e){
            throw new DBException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }

    @Override
    public List<Seller> findAllSellers() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT seller.*, department.Name as DpName " +
                    "FROM seller " +
                    "INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "ORDER BY Name");
            rs = st.executeQuery();

            List<Seller> sellerList = new ArrayList<>();
            Map<Integer, Department> departmentMap = new HashMap<>();

            while (rs.next()){
                Department dep = departmentMap.get(rs.getInt("DepartmentId"));

                if(dep == null) {
                    dep = instantiateDepartment(rs);
                    departmentMap.put(rs.getInt("DepartmentId"), dep);
                }

                Seller seller = instantiateSeller(rs, dep);
                sellerList.add(seller);
            }
            return sellerList;
        }
        catch (SQLException e){
            throw new DBException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findSellerByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT seller.*, department.Name as DpName " +
                    "FROM seller " +
                    "INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "WHERE DepartmentId = ? ORDER BY Name");
            st.setInt(1, department.getId());
            rs = st.executeQuery();

            List<Seller> sellerList = new ArrayList<>();
            Map<Integer, Department> departmentMap = new HashMap<>();

            while (rs.next()){
                Department dep = departmentMap.get(rs.getInt("DepartmentId"));

                if(dep == null) {
                    dep = instantiateDepartment(rs);
                    departmentMap.put(rs.getInt("DepartmentId"), dep);
                }

                Seller seller = instantiateSeller(rs, dep);
                sellerList.add(seller);
            }
            return sellerList;
        }
        catch (SQLException e){
            throw new DBException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getInt("Id"));
        seller.setName(rs.getString("Name"));
        seller.setEmail(rs.getString("Email"));
        seller.setBaseSalary(rs.getDouble("BaseSalary"));
        seller.setBirthDate(rs.getDate("BirthDate"));
        seller.setDepartment(dep);
        return seller;
    }


    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DpName"));
        return dep;
    }
}
