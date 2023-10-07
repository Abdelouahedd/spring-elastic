[//]: # (After test Elasticsearch 6.8.x I see that isn't compatible with spring 2.7.9 and I decide to test Elasticsearch 7.17.x)

# Elasticsearch 7.17.x

## How to launch Elasticsearch 7.17.x in a Docker container
    
### 1. Pull the image from Docker Hub
```
docker pull docker.elastic.co/elasticsearch/elasticsearch:7.17.14
```

### 2. Start a container
```
cd docker

docker-compose up -d
```

### 3. Check the status of the container
```
docker ps
```

### 4. Check the logs of the container
```
docker logs elasticsearch
```

### 5. Check the status of Elasticsearch
```
curl -X GET "localhost:9200/_cat/health?v&pretty"
```

### 6. Check the status of Elasticsearch cluster
```
curl -X GET "localhost:9200/_cat/nodes?v&pretty"
```

### 7. Check the status of Elasticsearch indices
```
curl -X GET "localhost:9200/_cat/indices?v&pretty"
```

### 8. Check the status of Elasticsearch shards
```
curl -X GET "localhost:9200/_cat/shards?v&pretty"
```

### 9. Check the status of Elasticsearch segments
```
curl -X GET "localhost:9200/_cat/segments?v&pretty"
```

### 10. Check the status of Elasticsearch thread pool
```
curl -X GET "localhost:9200/_cat/thread_pool?v&pretty"
```

### 11. Check the status of Elasticsearch pending tasks
```
curl -X GET "localhost:9200/_cat/pending_tasks?v&pretty"
```

### 12. Check the status of Elasticsearch recovery
```
curl -X GET "localhost:9200/_cat/recovery?v&pretty"
```

## swagger-ui

### 1. Open the swagger-ui
```
http://localhost:8080/swagger-ui.html
```
