package com.springarena.service;

import com.springarena.model.Note;
import com.springarena.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> getAllNotes() {
        // TODO: Get all notes
        List<Note> notes = noteRepository.findAll();
        return notes;
    }

    @Override
    public Optional<Note> getNoteById(Long id) {
        // TODO: Get note by id
        return noteRepository.findById(id);
    }

    @Override
    public Note createNote(Note note) {
        // TODO: Create a note
        return noteRepository.save(note);
    }

    @Override
    public Optional<Note> updateNote(Long id, Note note) {
        // TODO: Update a note
        Optional<Note> foundNote = noteRepository.findById(id);

        foundNote.setId(note.getId());
        foundNote.setTitle(note.getTitle());
        foundNote.setContent(note.getContent());
        foundNote.set
        return Optional.empty();
    }

    @Override
    public boolean deleteNote(Long id) {
        // TODO: Delete a note and return true if existed, false otherwise
        return false;
    }
}
