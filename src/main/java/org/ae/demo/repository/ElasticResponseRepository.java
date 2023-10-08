package org.ae.demo.repository;

import org.ae.demo.model.ElasticResponseModel;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ElasticResponseRepository extends ElasticsearchRepository<ElasticResponseModel, UUID> {

    List<ElasticResponseModel> findByExternalGuid(UUID externalGuid);

    List<ElasticResponseModel> findByThreadGuid(UUID threadGuid);

    List<ElasticResponseModel> findByEntryGuid(UUID entryGuid);

    List<ElasticResponseModel> findByEntryGuidAndArchivedIsNotNull(UUID entryGuid);

    List<ElasticResponseModel> findByEntryGuidParent(UUID entryGuidParent);
}
