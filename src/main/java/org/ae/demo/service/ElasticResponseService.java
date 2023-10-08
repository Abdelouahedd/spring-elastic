package org.ae.demo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ae.demo.model.ElasticResponseModel;
import org.ae.demo.repository.ElasticResponseRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class ElasticResponseService {
    private static final String MODEL_INDEX = "elastic-response";
    private final ElasticResponseRepository repository;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    // - Saving a new document will create a new guid (UUID.createRandom())
    public void save(String content) {
        ElasticResponseModel elasticResponseModel = ElasticResponseModel.builder().guid(UUID.randomUUID()).entryGuid(UUID.randomUUID()).threadGuid(UUID.randomUUID()).content(content).created(ZonedDateTime.now()).build();
        repository.save(elasticResponseModel);
    }

    //- Finding document by specific guid will return that single unique document.
    public ElasticResponseModel findByGuid(UUID guid) {
        ElasticResponseModel elasticResponseModel = repository.findById(guid).orElseThrow(() -> new RuntimeException("No document found with guid: " + guid));
        getAnswersAndHistories(elasticResponseModel);
        return elasticResponseModel;
    }


    public void updateByGuid(UUID guid, String content) {
        ElasticResponseModel elasticResponseModelToUpdate = findByGuid(guid);
        ElasticResponseModel responseModel = ElasticResponseModel.builder()
                .guid(UUID.randomUUID())
                .entryGuid(elasticResponseModelToUpdate.getEntryGuid())
                .content(content)
                .created(ZonedDateTime.now())
                .archived(elasticResponseModelToUpdate.getArchived())
                .build();
        repository.save(responseModel);
    }

    public List<ElasticResponseModel> findByExternalGuid(UUID externalGuid) {
        return repository.findByExternalGuid(externalGuid)
                .stream()
                .peek(this::getAnswersAndHistories)
                .toList();
    }

    public List<ElasticResponseModel> findByThreadGuid(UUID threadGuid) {
        return repository.findByThreadGuid(threadGuid)
                .stream()
                .peek(this::getAnswersAndHistories)
                .toList();
    }

    public List<ElasticResponseModel> findByEntryGuid(UUID entryGuid) {
        return repository.findByEntryGuid(entryGuid)
                .stream()
                .peek(this::getAnswersAndHistories)
                .toList();
    }

    public void archiveByExternalGuid(UUID externalGuid) {
        List<ElasticResponseModel> modelsToArchive = repository.findByExternalGuid(externalGuid);
        // Set archived timestamp to now for each model and save them
        modelsToArchive.forEach(model -> {
            model.setArchived(ZonedDateTime.now());
            repository.save(model);
        });
    }

    public void archiveByThreadGuid(UUID threadGuid) {
        List<ElasticResponseModel> modelsToArchive = repository.findByThreadGuid(threadGuid);
        // Set archived timestamp to now for each model and save them
        modelsToArchive.forEach(model -> {
            model.setArchived(ZonedDateTime.now());
            repository.save(model);
        });
    }

    public void archiveByEntryGuid(UUID entryGuid) {
        List<ElasticResponseModel> modelsToArchive = repository.findByEntryGuid(entryGuid);
        // Set archived timestamp to now for each model and save them
        modelsToArchive.forEach(model -> {
            model.setArchived(ZonedDateTime.now());
            repository.save(model);
        });
    }

    // Implement archiving and deleting methods for threadGuid and entryGuid as well

    public void deleteByExternalGuid(UUID externalGuid) {
        repository.deleteAll(repository.findByExternalGuid(externalGuid));
    }

    public void deleteByThreadGuid(UUID threadGuid) {
        repository.deleteAll(repository.findByThreadGuid(threadGuid));
    }

    public void deleteByEntryGuid(UUID entryGuid) {
        repository.deleteAll(repository.findByEntryGuid(entryGuid));
    }

    public Iterator<SearchHit<ElasticResponseModel>> searchByContent(String searchTerm) {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("content", searchTerm);
        Query searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();

        return elasticsearchRestTemplate.search(searchQuery, ElasticResponseModel.class, IndexCoordinates.of(MODEL_INDEX)).stream().iterator();
    }

    private void getAnswersAndHistories(ElasticResponseModel elasticResponseModel) {
        //get history (get all archived versions of this document by entryGuid and archived is not null)
        List<ElasticResponseModel> history = repository.findByEntryGuidAndArchivedIsNotNull(elasticResponseModel.getEntryGuid());
        elasticResponseModel.setHistory(history);
        //get answers (get all documents by entryGuidParent or entryGuid if entryGuidParent is null get all documents by entryGuid)
        List<ElasticResponseModel> answers = new ArrayList<>();
        Optional.ofNullable(elasticResponseModel.getEntryGuidParent())
                .ifPresentOrElse(
                        entryGuidParent -> answers.addAll(repository.findByEntryGuidParent(entryGuidParent)),
                        () -> answers.addAll(repository.findByEntryGuid(elasticResponseModel.getEntryGuid()))
                );
        elasticResponseModel.setAnswers(answers);
    }
}
