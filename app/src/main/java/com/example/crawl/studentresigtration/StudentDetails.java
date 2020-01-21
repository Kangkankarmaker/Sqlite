package com.example.crawl.studentresigtration;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crawl.studentresigtration.Db.DbHelper;
import com.example.crawl.studentresigtration.model.Student;

import java.util.IllegalFormatCodePointException;

public class StudentDetails extends AppCompatActivity {

    EditText etname, etaddress, etemail, etphone;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);


        etname = findViewById(R.id.edittxt_name1);

        etemail = findViewById(R.id.edittxt_email1);
        etphone = findViewById(R.id.edittxt_phone1);
        etaddress = findViewById(R.id.edittxt_address1);

        student = (Student) getIntent().getSerializableExtra("EDIT_STUDENT");

        if (student != null) {

            etname.setText(student.getStudentName());
            etemail.setText(student.getStudentEmail());
            etphone.setText(student.getStudentPhone());
            etaddress.setText(student.getStudentAddress());

        }
    }

    private boolean validataUI() {

        if (etname.getText().toString().length() == 0) {
            etname.setError("Please enter student name");
            return false;
        }
        if (etemail.getText().toString().length() == 0) {
            etemail.setError("Please enter student email");
            return false;
        }
        if (etphone.getText().toString().length() == 0) {
            etphone.setError("Please enter student phone");
            return false;
        }
        if (etaddress.getText().toString().length() == 0) {
            etaddress.setError("Please enter student address");
            return false;
        }
        return true;
    }
    public void Edit (View view){
        if (validataUI()){
            if (student!=null){
                final DbHelper dbHelper =new DbHelper(this);
                AlertDialog.Builder altdial =new AlertDialog.Builder(this);
                altdial.setMessage("do you want to update this student ?").setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                student.setStudentName(etname.getText().toString());
                                student.setStudentEmail(etemail.getText().toString());
                                student.setStudentAddress(etphone.getText().toString());
                                student.setStudentPhone(etaddress.getText().toString());

                                if (dbHelper.updateStudent(student)>0){
                                    Toast.makeText(getApplicationContext(),"Successfully updated",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(),StudentList.class));
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"Failed ,try again",Toast.LENGTH_LONG).show();

                                }

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alter = altdial.create();
                alter.show();

            }else {
                Toast.makeText(this,"Select Student first",Toast.LENGTH_SHORT).show();

            }
        }
    }
}
