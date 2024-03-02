package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.form.SearchEmployee;
import com.example.demo.mybatis.entity.Employee;
import com.example.demo.mybatis.mapper.EmployeeMapper;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/example")
public class ExampleController {
    @Autowired
    ServletContext servletContext;
    
    @Autowired
    private EmployeeMapper employeeMapper;

    @RequestMapping("/helloworld")
    public void helloworld(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().append("<!DOCTYPE html><html><head><meta charset=\"utf-8\"><title>Hello World</title></head><body><h1>World</h1></body></html>");

    }
    
    @RequestMapping(path = "/test03RequestResponse", method = { RequestMethod.GET, RequestMethod.POST })
    public void test03RequestResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append("\r\nHTTP 请求头:\r\n");
//        System.out.println(Collections.list(request.getHeaderNames()));
        for (String header : Collections.list(request.getHeaderNames())) {
            sb.append(header + " = " + String.join(",", Collections.list(request.getHeaders(header))) + "\r\n");
        }
        sb.append("\r\nHTTP 请求参数:\r\n");
        for (Entry<String, String[]> param : request.getParameterMap().entrySet()) {
            sb.append(param.getKey() + " = " + String.join(",", param.getValue()) + "\r\n");
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        PrintWriter pw = response.getWriter();
        pw.append(sb.toString());
    }
    @RequestMapping(path = "/test04RequestResponse", method = { RequestMethod.GET, RequestMethod.POST })
    public void test04RequestResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append("\r\nHTTP 请求参数:\r\n");
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        sb.append("name=").append(name).append("\r\n");
        sb.append("age=").append(age).append("\r\n");

        String[] names = request.getParameterValues("name");
        String[] ages = request.getParameterValues("age");
        sb.append("names=").append(String.join(",", names)).append("\r\n");
        sb.append("ages=").append(String.join(",", ages)).append("\r\n");

        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        PrintWriter pw = response.getWriter();
        pw.append(sb.toString());
    }
    @RequestMapping(path = "/test05RequestParam", method = { RequestMethod.GET, RequestMethod.POST })
    public void test06RequestParam(@RequestParam(name = "name", defaultValue = "Tom") String name,
            @RequestParam(defaultValue = "28") int age, @RequestParam(defaultValue = "男") String sex,
            @RequestParam(name="hobbies",required = false) String[] hobbies, HttpServletResponse response) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append("\r\nHTTP 请求参数:\r\n");
        sb.append("name = " + name + "\r\n");
        sb.append("sex = " + sex + "\r\n");
        sb.append("age = " + age + "\r\n");
        if(hobbies!=null)
        	sb.append("hobbies = " + String.join(",", hobbies) + "\r\n");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        PrintWriter pw = response.getWriter();
        pw.append(sb.toString());
    }
    @GetMapping(path = "/test06PathAndMatrixVariable/{id}")
    public void test06PathAndMatrixVariable(@PathVariable String id, @MatrixVariable String name,
            @MatrixVariable String sex, @MatrixVariable int age, @MatrixVariable(required = false) String[] hobbies,
            HttpServletResponse response) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append("\r\n路径片段变量与矩阵变量:\r\n");
        sb.append("id = " + id + "\r\n");
        sb.append("name = " + name + "\r\n");
        sb.append("sex = " + sex + "\r\n");
        sb.append("age = " + age + "\r\n");
        if (hobbies != null)
            sb.append("hobbies = " + String.join(",", hobbies) + "\r\n");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        PrintWriter pw = response.getWriter();
        pw.append(sb.toString());
    }
    
    @PostMapping(value = "/test16Multipart")
    public void test16Multipart(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file,HttpServletResponse response) throws IOException {
        String fileName = new File(file.getOriginalFilename()).getName();
        String distFileName = new File(servletContext.getRealPath("/"), fileName).getCanonicalPath();
        Files.copy(file.getInputStream(), Paths.get(distFileName), StandardCopyOption.REPLACE_EXISTING);
        StringBuffer sb = new StringBuffer();
        sb.append("文件:" + distFileName + "\r\n");
        sb.append("大小:" + file.getSize() + "\r\n");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        PrintWriter pw = response.getWriter();
        pw.append(sb.toString());
    }
    
    @GetMapping(value = "/testJsonService01", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<Employee> testJsonService01(@RequestParam(defaultValue = "")String firstName,@RequestParam(defaultValue = "")String lastName) {
        return employeeMapper.findEmployeesByName(firstName, lastName);
    }

//    @PostMapping(value = "/testJsonService02",
//    		consumes = "application/json;charset=utf-8",
//    		produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public List<Employee> testJsonService02(@RequestBody SearchEmployee searchEmployee) {
//        return employeeMapper.findEmployeesByName("S", "S");
//    }

    @GetMapping(value = "/testJsonService02", 
            consumes = "application/json; charset=utf-8",
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<Employee> testJsonService02(RequestEntity<SearchEmployee> request) {
        SearchEmployee searchEmployee = request.getBody();
        return employeeMapper.findEmployeesByName(searchEmployee.getFirstName(), searchEmployee.getLastName());
    }


}

