package com.example.crawl.studentresigtration;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.crawl.studentresigtration.Adapter.StudentAdapter;
import com.example.crawl.studentresigtration.Db.DbHelper;
import com.example.crawl.studentresigtration.model.Student;

import java.util.ArrayList;

public class StudentList extends AppCompatActivity {

    ListView lv_student;
    ArrayList<Student> studentlist;
    StudentAdapter adapter;
    DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);


        dbHelper = new DbHelper(this);
        studentlist = new ArrayList<>();
        studentlist = dbHelper.getAllStudent();
        adapter = new StudentAdapter(studentlist, this);
        lv_student = findViewById(R.id.studentlist);
        lv_student.setAdapter(adapter);

        registerForContextMenu(lv_student);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.setHeaderTitle(studentlist.get(info.position).getStudentName());
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

            final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();


            switch (item.getItemId()){

                case R.id.delete_id:
                    AlertDialog.Builder altdial = new AlertDialog.Builder(this);

                    altdial.setMessage("Do you want to delete this student?").setCancelable(false)
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    if (dbHelper.deleteStudent(studentlist.get(info.position).getStudentId())>0){

                                        studentlist.remove(info.position);
                                        adapter.notifyDataSetChanged();
                                        Toast.makeText(getApplicationContext(),"Successfully deleted",Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"Failed to delete.Try again",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alter = altdial.create();
                    alter.setTitle(studentlist.get(info.position).getStudentName());
                    alter.show();
                    return false;

                case R.id.edit_id:
                    Intent intent= new Intent(this,StudentDetails.class);
                    intent.putExtra("EDIT_STUDENT",studentlist.get(info.position));
                    startActivity(intent);

                default:
                    return super.onContextItemSelected(item);
            }
        }
    }