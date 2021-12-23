package com.example.cv_generator

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val fname : EditText = findViewById(R.id.names);
    private val name = fname.text.toString();
    private val fphone : EditText = findViewById(R.id.phones);
    private val phone_number = fphone.text.toString();
    private val femail : EditText = findViewById(R.id.emails);
    private val email = femail.text.toString();
    private val fskill : EditText = findViewById(R.id.skills);
    private val skill = fskill.text.toString();
    private val fexperience : EditText = findViewById(R.id.experiences);
    val experience = fexperience.text.toString();
    private val fprojects : EditText = findViewById(R.id.projects);
    private val project = fprojects.text.toString();
    private val fdescription : EditText = findViewById(R.id.descriptions);
    private val description = fdescription.text.toString();

    private val submit_button : Button = findViewById(R.id.submit)
    private val STORAGE_CODE = 1001;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submit_button.setOnClickListener{
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                {
                    val permission =  arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission, STORAGE_CODE);
                }else {
                    savePDF()
                }

            } else {
                savePDF()
            }
        }


    }

    private fun savePDF() {
        val doc = Document();
        val filename = "resume";
        val filepath = Environment.getExternalStorageDirectory().toString() + "/" + filename + ".pdf";

        try {
            PdfWriter.getInstance(doc, FileOutputStream(filepath))
            doc.open()
            doc.addAuthor(name);
            doc.add(Paragraph(name));
            doc.add(Paragraph(description))
            doc.add(Paragraph(email));
            doc.add(Paragraph(phone_number));
            doc.add(Paragraph(skill));
            doc.add(Paragraph(experience));
            doc.add(Paragraph(project));
            doc.close()
            Toast.makeText(this,"$filename.pdf\n is created at \n$filepath",Toast.LENGTH_LONG).show();


        }catch (e : Exception){
            Toast.makeText(this,""+e.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            STORAGE_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    savePDF();
                }else
                {
                    Toast.makeText(this,"Permission Denied!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}