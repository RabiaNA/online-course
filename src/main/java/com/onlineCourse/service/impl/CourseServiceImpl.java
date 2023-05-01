package com.onlineCourse.service.impl;

import com.onlineCourse.entities.Course;
import com.onlineCourse.entities.CourseEnrollment;
import com.onlineCourse.repository.CourseEnrollmentRepository;
import com.onlineCourse.repository.CourseRepository;
import com.onlineCourse.service.interfaces.CourseService;
import com.onlineCourse.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseEnrollmentRepository courseEnrollmentRepository;

    @Override
    public List<Course> getCourseList() {
        List<Course> courseList =  courseRepository.findAll();
        return stampImageId(courseList);
    }

    @Override
    public List<Course> search(String searchText) {
        List<Course> courseList =  courseRepository.findByCourseNameContainingIgnoreCase(searchText);
        return stampImageId(courseList);
    }

    private List<Course> stampImageId(List<Course> courseList) {
        return Utils.safe(courseList).stream().map(course -> {
            course.setImageId(String.valueOf(course.getId()%10));
            return course;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Course> findByIdIn(List<Integer> courseIdList) {
        return courseRepository.findByIdIn(courseIdList);
    }

    @Override
    public List<Course> getEnrolledCourseList(Integer userId){
        List<CourseEnrollment> courseEnrollmentList = courseEnrollmentRepository.getCourseEnrollmentByUserId(userId);
        List<Integer> courseIdList =  Utils.safe(courseEnrollmentList).stream().map(ce -> ce.getCourseId()).collect(Collectors.toList());
        List<Course> courseList = findByIdIn(courseIdList);
        return stampImageId(courseList);
    }
}