package org.ae.demo.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ae.demo.model.ElasticResponseModel;
import org.ae.demo.service.ElasticResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/models")
@Slf4j
@AllArgsConstructor
public class ElasticResponseController {
    private final ElasticResponseService service;


    @PostMapping
    public ResponseEntity<?> save(@RequestBody String content) {
        service.save(content);
        return ResponseEntity.created(URI.create("")).build();
    }

    @PutMapping("/{uuid}")
    public void update(@PathVariable UUID uuid, @RequestBody String content) {
        service.updateByGuid(uuid, content);
    }

    @GetMapping("/{uuid}")
    public ElasticResponseModel findByGuid(@PathVariable UUID uuid) {
        return service.findByGuid(uuid);
    }

    @GetMapping("/external-guid/{uuid}")
    public List<ElasticResponseModel> findByExternalGuid(@PathVariable UUID uuid) {
        return service.findByExternalGuid(uuid);
    }

    @GetMapping("/thread-guid/{uuid}")
    public List<ElasticResponseModel> findByThreadGuid(@PathVariable UUID uuid) {
        return service.findByThreadGuid(uuid);
    }

    @GetMapping("/entry-guid/{uuid}")
    public List<ElasticResponseModel> findByEntryGuid(@PathVariable UUID uuid) {
        return service.findByEntryGuid(uuid);
    }

    @PutMapping("/archive/external-guid/{uuid}")
    public void archiveByExternalGuid(@PathVariable UUID uuid) {
        service.archiveByExternalGuid(uuid);
    }

    @PutMapping("/archive/thread-guid/{uuid}")
    public void archiveByThreadGuid(@PathVariable UUID uuid) {
        service.archiveByThreadGuid(uuid);
    }

    @PutMapping("/archive/entry-guid/{uuid}")
    public void archiveByEntryGuid(@PathVariable UUID uuid) {
        service.archiveByEntryGuid(uuid);
    }

    @DeleteMapping("/external-guid/{uuid}")
    public void deleteByExternalGuid(@PathVariable UUID uuid) {
        service.deleteByExternalGuid(uuid);
    }

    @DeleteMapping("/thread-guid/{uuid}")
    public void deleteByThreadGuid(@PathVariable UUID uuid) {
        service.deleteByThreadGuid(uuid);
    }

    @DeleteMapping("/entry-guid/{uuid}")
    public void deleteByEntryGuid(@PathVariable UUID uuid) {
        service.deleteByEntryGuid(uuid);
    }


    @GetMapping
    public List<ElasticResponseModel> searchByContent(@RequestParam("content") String content) {
        return service.searchByContent(content);
    }
}
