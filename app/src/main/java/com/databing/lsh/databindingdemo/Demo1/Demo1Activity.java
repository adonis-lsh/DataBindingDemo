package com.databing.lsh.databindingdemo.Demo1;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.databing.lsh.databindingdemo.R;
import com.databing.lsh.databindingdemo.Student;
import com.databing.lsh.databindingdemo.databinding.ActivityDemo1Binding;

public class Demo1Activity extends AppCompatActivity {

    private ActivityDemo1Binding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_demo1);
        initData();
    }

    private void initData() {
        //模拟网络请求部分,假如请求到了一个学生实体
        Student student = new Student();
        student.setAge(25);
        student.setName("李宁");
        student.setHeaderImg("http://i2.muimg.com/567571/b528af67d68f7597.png");
        //对xml中写的变量进行赋值
        mBinding.setStudent(student);
    }
}
