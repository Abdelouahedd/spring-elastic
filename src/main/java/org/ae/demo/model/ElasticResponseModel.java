package org.ae.demo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(indexName = "elastic-response")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ElasticResponseModel {
    @Id
    private UUID guid;
    private UUID externalGuid;
    private UUID threadGuid;
    private UUID entryGuid;
    private UUID entryGuidParent;
    private String content;
    @Field(type = FieldType.Date,format = org.springframework.data.elasticsearch.annotations.DateFormat.date_time)
    private ZonedDateTime created;
    @Field(type = FieldType.Date,format = org.springframework.data.elasticsearch.annotations.DateFormat.date_time)
    private ZonedDateTime archived;
    @Transient
    private List<ElasticResponseModel> answers = new ArrayList<>();
    @Transient
    private List<ElasticResponseModel> history = new ArrayList<>();
}
