package application.dao;

import java.util.List;

import application.entity.Employee;
import application.entity.Entreprise;

public interface IDaoEmploye {
boolean createEmploye(Employee e);
boolean deleteEmploye(Employee e );
boolean updateEmploye(Employee e);
Employee findEmployeById(int matricule);
List<Employee > findAll();
}
