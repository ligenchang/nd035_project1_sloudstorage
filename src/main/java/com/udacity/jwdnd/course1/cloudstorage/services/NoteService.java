package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;
    private UserMapper userMapper;

    public NoteService(NoteMapper noteMapper, UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    public void addNote(Note note){
        if (note.getNoteId() == null){
            noteMapper.insert(note);
        }else{
            noteMapper.updateNote(note);
        }

    }

    public void deleteNote(Integer noteId){
        noteMapper.deleteNote(noteId);
    }

    public List<Note> getNotes(String userName){
        return noteMapper.GetUserNotes(userName);
    }
}
