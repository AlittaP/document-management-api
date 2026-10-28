package com.example.docapi.service;

import com.example.docapi.exception.DocumentNotFoundException;
import com.example.docapi.model.Document;
import com.example.docapi.model.DocumentStatus;
import com.example.docapi.repository.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    @Mock
    private DocumentRepository repository;

    @InjectMocks
    private DocumentService service;

    @Test
    void getById_returnsDocument_whenFound() {
        Document doc = new Document("Report", "PDF", "desc", DocumentStatus.DRAFT);
        when(repository.findById(1L)).thenReturn(Optional.of(doc));

        Document result = service.getById(1L);

        assertEquals("Report", result.getTitle());
    }

    @Test
    void getById_throws_whenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(DocumentNotFoundException.class, () -> service.getById(99L));
    }

    @Test
    void create_savesDocument() {
        Document doc = new Document("Report", "PDF", "desc", DocumentStatus.DRAFT);
        when(repository.save(doc)).thenReturn(doc);

        Document result = service.create(doc);

        assertEquals("Report", result.getTitle());
        verify(repository).save(doc);
    }

    @Test
    void delete_throws_whenNotFound() {
        when(repository.existsById(5L)).thenReturn(false);

        assertThrows(DocumentNotFoundException.class, () -> service.delete(5L));
        verify(repository, never()).deleteById(any());
    }

    @Test
    void delete_removesDocument_whenFound() {
        when(repository.existsById(5L)).thenReturn(true);

        service.delete(5L);

        verify(repository).deleteById(5L);
    }
}