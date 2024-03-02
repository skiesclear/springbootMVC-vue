package com.example.demo.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.mybatis.entity.Employee;

@Mapper
public interface EmployeeMapper {
//    public List<Employee> findEmployeesByName(
//    		@Param("firstName") String firstName,
//    		@Param("lastName") String lastName);
	public List<Employee> findEmployeesByName(
    		String firstName,
    		 String lastName);
    public int addEmployee(Employee employee);
    public Employee getEmployee(Integer id);
    public int chgEmployee(Employee employee);
    public int delEmployee(Integer id);
}