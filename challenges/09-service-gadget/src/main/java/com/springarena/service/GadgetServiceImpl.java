package com.springarena.service;

import com.springarena.model.Gadget;
import com.springarena.repository.GadgetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GadgetServiceImpl implements GadgetService {

    private final GadgetRepository gadgetRepository;

    public GadgetServiceImpl(GadgetRepository gadgetRepository) {
        this.gadgetRepository = gadgetRepository;
    }

    @Override
    public List<Gadget> getAllGadgets() {
        // TODO: Get all gadgets
        return null;
    }

    @Override
    public Optional<Gadget> getGadgetById(Long id) {
        // TODO: Get gadget by id
        return Optional.empty();
    }

    @Override
    public Gadget createGadget(Gadget gadget) {
        // TODO: Create gadget
        return null;
    }

    @Override
    public Optional<Gadget> updateGadget(Long id, Gadget gadget) {
        // TODO: Update gadget
        return Optional.empty();
    }

    @Override
    public boolean deleteGadget(Long id) {
        // TODO: Delete gadget
        return false;
    }
}
