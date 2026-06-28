package com.springarena.service;

import com.springarena.model.Note;
import java.util.List;
import java.util.Optional;

public interface NoteService {
    List<Note> getAllNotes();
    Optional<Note> getNoteById(Long id);
    Note createNote(Note note);
    Optional<Note> updateNote(Long id, Note note);
    boolean deleteNote(Long id);
}
