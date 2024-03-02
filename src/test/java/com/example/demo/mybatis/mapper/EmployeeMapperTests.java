package com.example.demo.mybatis.mapper;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import com.example.demo.mybatis.entity.Employee;

@SpringBootTest
class EmployeeMapperTests {
	@Autowired
	private EmployeeMapper employeeMapper;

//	@Test
//	public void testFindEmployeesByName() throws Exception {
//
//		List<Employee> employeeList = employeeMapper.findEmployeesByName("S","S");
//		Assert.notEmpty(employeeList, "获取 getEmployees 失败");
//		for (Employee e : employeeList) {
//			System.out.println(e);
//		}
//	}
    @Test
    public void testAddEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("Slina");
        employee.setLastName("John");
        employee.setEmail("JSlina");
        employee.setPhoneNumber("19912344321");
        employee.setHireDate(new Date(0));
        employee.setJob("AD_VP");
        employee.setSalary(987654.1);
        employee.setCommissionPct(0.25);
        employee.setManager(null);
        employee.setDepartment(100);
        Assert.isTrue(employeeMapper.addEmployee(employee) == 1, "addEmployee 失败");
        System.out.println(employee);
    }
//    @Test
//    public void testGetEmployee() throws Exception {
//        System.out.println(employeeMapper.getEmployee(208));
//    }
//    @Test
//    public void testChgEmployee() throws Exception {
//        Employee employee = employeeMapper.getEmployee(208);
//        System.out.println(employee);
//        employee.setFirstName("ZZZZZZ");
//        employee.setSalary(9999.0);
//        Assert.isTrue(employeeMapper.chgEmployee(employee) == 1, "chgEmployee 失败");
//    }
//	@Test
//	public void testDelEmployee() throws Exception {
//		Assert.isTrue(employeeMapper.delEmployee(208) == 1, "delEmployee fail");
//	}

}
