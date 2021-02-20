package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import org.springframework.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;



import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2021-02-19
 */
@Api(description="讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {
     @Autowired
    private EduTeacherService teacherService;

     @GetMapping("findAll")
    public R findAllTeacher(){
         List<EduTeacher> list = teacherService.list(null);
         int a=10/0;
         return R.ok().data("items",list);


     }
     @DeleteMapping("{id}")
     public R removeTeacher(@PathVariable String id){
         boolean flag = teacherService.removeById(id);
         return flag?R.ok():R.error();
     }
     @GetMapping("pageTeacher/{current}/{limit}")
     public R pageListTeacher(@PathVariable long current,@PathVariable long limit){
         Page<EduTeacher> pageTeacher=new Page(current,limit);
         teacherService.page(pageTeacher,null);
         long total = pageTeacher.getTotal();
         List<EduTeacher> records = pageTeacher.getRecords();
         return R.ok().data("total",total).data("rows",records);
     }
     @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,@PathVariable long limit, @RequestBody(required = false) TeacherQuery teacherQuery){

         Page<EduTeacher> pageTeacher = new Page<>(current,limit);
         QueryWrapper<EduTeacher> wrapper =new QueryWrapper<>();
         String name = teacherQuery.getName();
         Integer level = teacherQuery.getLevel();
         String begin = teacherQuery.getBegin();
         String end = teacherQuery.getEnd();
         if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
         }
         if(!StringUtils.isEmpty(level)){
             wrapper.eq("level",level);
         }if (!StringUtils.isEmpty(begin)){
             wrapper.ge("gmt_create",begin);
         }if (!StringUtils.isEmpty(end)){
             wrapper.le("gmt_create",end);
         }

         teacherService.page(pageTeacher,wrapper);
         long total = pageTeacher.getTotal();
         List<EduTeacher> records = pageTeacher.getRecords();
         return R.ok().data("total",total).data("rows",records);
     }
     @PostMapping("addTeacher")
    public  R addTeacher( @RequestBody EduTeacher eduTeacher){
         boolean save = teacherService.save(eduTeacher);
         if (save){
             return R.ok();
         }else {
             return R.error();
         }

     }
     @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
         EduTeacher eduTeacher = teacherService.getById(id);
         return R.ok().data("teacher",eduTeacher);

     }
     @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
         boolean flag= teacherService.updateById(eduTeacher);
         if (flag){
             return R.ok();
         }else {
             return R.error();
         }
     }
}

