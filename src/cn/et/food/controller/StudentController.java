package cn.et.food.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.et.food.entity.Result;
import cn.et.food.entity.Student;
import cn.et.food.service.StudentService;
import cn.et.food.utils.PageTools;

@Controller
public class StudentController {
	@Autowired
	StudentService service;
	@ResponseBody
	@RequestMapping("/queryStudent")
	public PageTools queryStudent(String sname,Integer page,Integer rows){
		PageTools pt=service.queryStudent(sname,page,rows);
		return pt;
	}
	
	@ResponseBody
	@RequestMapping(value="/student/{sid}",method=RequestMethod.DELETE)
	public Result deleteStudent(@PathVariable String sid,Integer page,Integer rows){
		String[] sbs=sid.split(",");
		Result r=new Result();
		r.setCode(1);
		try {
			for(String s:sbs){
				service.deleteStudent(Integer.parseInt(s));
			}
			
		} catch (Exception e) {
			r.setCode(0);
			r.setMessage(e);
		}
		return r;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/student/{sid}",method=RequestMethod.PUT)
	public Result updateStudent(@PathVariable Integer sid,Student student,Integer page,Integer rows){
		student.setSid(sid);
		Result r=new Result();
		r.setCode(1);
		try {
			service.updateStudent(student);
		} catch (Exception e) {
			r.setCode(0);
			r.setMessage(e);
		}
		return r;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/student",method=RequestMethod.POST)
	public Result insertStudent(Student student,MultipartFile myImage){
		Result r=new Result();
		r.setCode(1);
		try {
			String fileName=myImage.getOriginalFilename();
			File destFile=new File("E:\\myfile\\"+fileName);
			myImage.transferTo(destFile);
			service.saveStudent(student);
		} catch (Exception e) {
			r.setCode(0);
			r.setMessage(e);
		}
		return r;
	}
	
	
}
