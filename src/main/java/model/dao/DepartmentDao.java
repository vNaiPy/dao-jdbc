package model.dao;

import model.entities.Department;

import java.util.List;

public interface DepartmentDao {

    void insertDepartment(Department department);

    void updateDepartment(Department department);

    void deleteDepartmentById (Integer id);

    Department findDepartmentById (Integer id);

    List<Department> findAllDepartments();
}
