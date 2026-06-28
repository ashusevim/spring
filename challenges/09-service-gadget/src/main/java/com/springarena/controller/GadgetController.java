package com.springarena.controller;

import com.springarena.model.Gadget;
import com.springarena.service.GadgetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gadgets")
public class GadgetController {

    private final GadgetService gadgetService;

    public GadgetController(GadgetService gadgetService) {
        this.gadgetService = gadgetService;
    }

    @GetMapping
    public List<Gadget> getAllGadgets() {
        // TODO: Get all gadgets via gadgetService
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gadget> getGadgetById(@PathVariable Long id) {
        // TODO: Get gadget by id via gadgetService
        return null;
    }

    @PostMapping
    public ResponseEntity<Gadget> createGadget(@RequestBody Gadget gadget) {
        // TODO: Create gadget via gadgetService
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gadget> updateGadget(@PathVariable Long id, @RequestBody Gadget gadget) {
        // TODO: Update gadget via gadgetService
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGadget(@PathVariable Long id) {
        // TODO: Delete gadget via gadgetService
        return null;
    }
}
