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

        if (foundNote.isPresent()) {
            foundNote.get().setId(note.getId());
            foundNote.get().setTitle(note.getTitle());
            foundNote.get().setContent(note.getContent());
            foundNote.get().setAuthor(note.getAuthor());
            foundNote.get().setPinned(note.isPinned());
            return foundNote;
        } else {
            return Optional.empty();
        }

    }

    @Override
    public boolean deleteNote(Long id) {
        // TODO: Delete a note and return true if existed, false otherwise
        Optional<Note> note = noteRepository.findById(id);
        if (note.isPresent()) {
            noteRepository.delete(note.get());
            return true;
        }
        return false;
    }
}
