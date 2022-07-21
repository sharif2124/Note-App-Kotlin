package com.example.mynoteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {
    lateinit var noteTitleEdit: EditText
    lateinit var noteDescriptionEdit: EditText
    lateinit var addupdateButton: Button
    lateinit var viewModal: NoteViewModal
    var noteID = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)
        noteTitleEdit=findViewById(R.id.EditNoteTitle)
        noteDescriptionEdit=findViewById(R.id.EditNoteDescription)
        addupdateButton=findViewById(R.id.BtnAddUpdate)
        viewModal= ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(NoteViewModal::class.java)
        val noteType = intent.getStringExtra("noteType")
        if(noteType.equals("Edit")){
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDesc = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteID",-1)
            addupdateButton.setText("Update Note")
            noteTitleEdit.setText(noteTitle)
            noteDescriptionEdit.setText(noteDesc)
        }
        else{
            addupdateButton.setText("Save Note")
        }
        addupdateButton.setOnClickListener {
            val noteTitle = noteTitleEdit.text.toString()
            val noteDescription = noteDescriptionEdit.text.toString()
            if(noteType.equals("Edit")){
                if(noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentdate:String=sdf.format(Date())
                    val updateNote = Note(noteTitle,noteDescription,currentdate)
                    updateNote.id=noteID
                    viewModal.updateNote(updateNote)
                    Toast.makeText(this,"Note Updated..", Toast.LENGTH_LONG).show()
                }
            }
            else{
                if(noteTitle.isNotEmpty() && noteDescription.isNotEmpty() ){
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentdate:String=sdf.format(Date())
                    viewModal.addNote(Note(noteTitle,noteDescription,currentdate))
                    Toast.makeText(this,"Note Added..", Toast.LENGTH_LONG).show()
                }
            }
            startActivity(Intent(applicationContext,MainActivity::class.java))
            this.finish()

        }
    }
}