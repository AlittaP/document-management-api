package com.example.docapi.controller;

import com.example.docapi.model.Document;
import com.example.docapi.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService service;

    public DocumentController(DocumentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Document> create(@Valid @RequestBody Document document) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(document));
    }

    @GetMapping
    public List<Document> list(@RequestParam(required = false) String title) {
        return service.getAll(title);
    }

    @GetMapping("/{id}")
    public Document get(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Document update(@PathVariable Long id, @Valid @RequestBody Document document) {
        return service.update(id, document);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}