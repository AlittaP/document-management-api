package com.example.docapi.service;

import com.example.docapi.exception.DocumentNotFoundException;
import com.example.docapi.model.Document;
import com.example.docapi.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository repository;

    public DocumentService(DocumentRepository repository) {
        this.repository = repository;
    }

    public Document create(Document document) {
        document.setId(null);
        return repository.save(document);
    }

    public List<Document> getAll(String titleSearch) {
        if (titleSearch == null || titleSearch.isBlank()) {
            return repository.findAll();
        }
        return repository.findByTitleContainingIgnoreCase(titleSearch);
    }

    public Document getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundException(id));
    }

    public Document update(Long id, Document updated) {
        Document existing = getById(id);
        existing.setTitle(updated.getTitle());
        existing.setFileType(updated.getFileType());
        existing.setDescription(updated.getDescription());
        existing.setStatus(updated.getStatus());
        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new DocumentNotFoundException(id);
        }
        repository.deleteById(id);
    }
}