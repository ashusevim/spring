package com.springarena.service;

import com.springarena.model.Gadget;
import java.util.List;
import java.util.Optional;

public interface GadgetService {
    List<Gadget> getAllGadgets();
    Optional<Gadget> getGadgetById(Long id);
    Gadget createGadget(Gadget gadget);
    Optional<Gadget> updateGadget(Long id, Gadget gadget);
    boolean deleteGadget(Long id);
}
