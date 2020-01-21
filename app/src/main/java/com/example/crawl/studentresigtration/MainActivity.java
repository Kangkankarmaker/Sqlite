package com.example.crawl.studentresigtration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crawl.studentresigtration.Db.DbHelper;
import com.example.crawl.studentresigtration.model.Student;

public class MainActivity extends AppCompatActivity {

    EditText name,email,phone,address;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.edittxt_name);
        email=findViewById(R.id.edittxt_email);
        phone=findViewById(R.id.edittxt_phone);
        address=findViewById(R.id.edittxt_address);
        b=findViewById(R.id.btn_show);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,StudentList.class);
                startActivity(intent);
            }
        });
    }

    private boolean validataUI(){

        if (name.getText().toString().length() == 0) {
            name.setError("Please enter student name");
            return false;
        }
        if (email.getText().toString().length() == 0) {
            email.setError("Please enter student email");
            return false;
        }
        if (phone.getText().toString().length() == 0) {
            email.setError("Please enter student phone");
            return false;
        }
        if (address.getText().toString().length() == 0) {
            address.setError("Please enter student address");
            return false;
        }
        return true;
    }
    public void Insert(View view){

        if (validataUI()){

            DbHelper dbHelper = new DbHelper(this);
            Student student = new Student();

            student.setStudentName(name.getText().toString());
            student.setStudentEmail(email.getText().toString());
            student.setStudentPhone(phone.getText().toString());
            student.setStudentAddress(address.getText().toString());

            if (dbHelper.addStudent(student)>0){

                Toast.makeText(this,"Successfully Inserted",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this,StudentList.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this,"Insertion failed.Please try again!",Toast.LENGTH_SHORT).show();
            }
        }
    }
}