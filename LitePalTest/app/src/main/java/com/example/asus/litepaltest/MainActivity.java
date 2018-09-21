package com.example.asus.litepaltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button createdata = (Button)findViewById(R.id.create_data);
        createdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
            }
        });
        Button addData = (Button)findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Book book = new Book();
                book.setName("The Da Vinci Code");
                book.setAnthor("Dan Brown");
                book.setPages(454);
                book.setPrice(16.96);
                book.setPress("Unknow");
                book.save();
            }
        });
        Button updatedata = (Button)findViewById(R.id.update_data);
        updatedata.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
              /* Book book = new Book();
                book.setName("The Lost Symbol");
                book.setAnthor("Dan brown");
                book.setPages(510);
                book.setPrice(19.95);
                book.setPress("Unknow");
                book.save();
                book.setPrice(10.99);
                book.save();*/
                Book book = new Book();
                book.setPrice(14.95);
                book.setPress("Anchor");
                book.updateAll("name = ? and anthor = ?","The Lost Symbol","Dan brown");

            }
        });
        Button deletedata = (Button)findViewById(R.id.delete_data);
        deletedata.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                DataSupport.deleteAll(Book.class,"price<?","15");
            }
        });
        Button querydata = (Button)findViewById(R.id.query_data);
        querydata.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                List<Book>  books = DataSupport.findAll(Book.class);
                for(Book book:books){
                    Log.d("MainActivity","book name is"+book.getName());
                    Log.d("MainActivity","book author is"+book.getAnthor());
                    Log.d("MainActivity","book pages is"+book.getPages());
                    Log.d("MainActivity","book price is"+book.getPrice());
                    Log.d("MainActivity","book press is"+book.getPress());
                }
            }
        });
    }
}
