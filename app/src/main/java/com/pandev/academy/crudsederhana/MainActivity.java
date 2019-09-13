package com.pandev.academy.crudsederhana;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DataHelper myDb;
    EditText edtName, editSurename, edtMarks ,edtId;
    Button btnTambah, btnLihat, btnHapus, btnUbah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DataHelper(this);
        edtName = (EditText)findViewById(R.id.edt_name);
        editSurename = (EditText)findViewById(R.id.edt_surname);
        edtMarks = (EditText)findViewById(R.id.edt_marks);
        edtId = (EditText)findViewById(R.id.edt_id);
        btnTambah = (Button)findViewById(R.id.btn_tambah);
        btnLihat = (Button)findViewById(R.id.btn_lihat);
        btnHapus = (Button)findViewById(R.id.btn_hapus);
        btnUbah = (Button)findViewById(R.id.btn_ubah);

        AddData();
        UpdateData();
        DeleteData();
        ViewAll();
    }

    private void ViewAll() {
        btnLihat.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount()==0){
                            showMessage("Error", "Noting Found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            buffer.append("ID "+res.getString(0)+"\n");
                            buffer.append("NAME "+res.getString(1)+"\n");
                            buffer.append("SURENAME "+res.getString(2)+"\n");
                            buffer.append("MARKS "+res.getString(3)+"\n");
                        }

                        showMessage("Data", buffer.toString());
                    }


                }
        );
    }

    private void DeleteData() {
        btnHapus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(edtId.getText().toString());
                        if (deletedRows > 0)
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data Failed to Deleted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void UpdateData() {
        btnUbah.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(edtId.getText().toString(),
                                edtName.getText().toString(),
                                editSurename.getText().toString(),
                                edtMarks.getText().toString());
                        if (isUpdate == true)
                            Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data Failed to Updated", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void AddData() {
        btnTambah.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInsert = myDb.insertData(edtName.getText().toString(),
                                editSurename.getText().toString(),
                                edtMarks.getText().toString());
                        if (isInsert == true)
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();


    }
}
