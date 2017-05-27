package com.android.wang.rxjavalearn.flatMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.android.wang.rxjavalearn.R;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by wang on 2016/11/23.
 */

public class FlatMap extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Course course1 = new Course();
        course1.setCourse("math");
        Course course2 = new Course();
        course2.setCourse("history");

        List<Course> courseList = new ArrayList<>();
        courseList.add(course1);
        courseList.add(course2);

        Student student = new Student();
        student.setName("one");
        student.setCourses(courseList);
        Student student1 = new Student();
        student1.setName("two");
        student1.setCourses(courseList);

        Student[] students = {student,student1};
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d("这是观察者的数据",s);
            }
        };
        Observable.from(students)
                .map(new Func1<Student, String>() {
                    @Override
                    public String call(Student student) {
                        Log.d("这是被观察的数据",student.getName());
                        return student.getName();
                    }
                }).subscribe(subscriber);


        //一对多的转化,Subscriber 中直接传入单个的 Course 对象

        /*
        flatMap() 的原理是这样的：
        1. 使用传入的事件对象创建一个 Observable 对象；
        2. 并不发送这个 Observable, 而是将它激活，于是它开始发送事件；
        3. 每一个创建出来的 Observable 发送的事件，都被汇入同一个 Observable ，而这个 Observable 负责将这些事件统一交给 Subscriber 的回调方法。
        这三个步骤，把事件拆成了两级，通过一组新创建的 Observable 将初始的对象『铺平』之后通过统一路径分发了下去。
         */
        Subscriber<Course> subscriber1 = new Subscriber<Course>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Course course) {
                Log.d("2222",course.getCourse());
            }
        };

        Observable.from(students)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(student.getCourses());
                    }
                })
                .subscribe(subscriber1);
    }
}
