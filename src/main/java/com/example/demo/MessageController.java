package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class MessageController {
    private final List<String> topics = new ArrayList<>();
    // curl localhost:8080/topics
    @GetMapping("topics")
    public ResponseEntity<List<String>> getTopics() {
        return ResponseEntity.ok(topics);
    }
    // curl -X POST localhost:8080/topics -d "data"
    @PostMapping("topics")
    public ResponseEntity<Void> addTopic(@RequestBody String text) {
        topics.add(text);
        return ResponseEntity.accepted().build();
    }
    // curl -X DELETE localhost:8080/topics/0
    @DeleteMapping("topics/{index}")
    public ResponseEntity<Void> deleteTopic(@PathVariable("index") Integer
                                                   index) {
        topics.remove((int) index);
        return ResponseEntity.noContent().build();
    }
    // curl -X DELETE localhost:8080/topics/delete
    @DeleteMapping("topics/delete")
    public ResponseEntity<Void> deleteAllTopics() {
        for (int i = topics.size() - 1; i >= 0; i--) {
            topics.remove(i);
        }

        return ResponseEntity.noContent().build();
    }
    // curl -X PUT localhost:8080/topics/0 -d "data"
    @PutMapping("topics/{index}")
    public ResponseEntity<Void> updateTopic(
            @PathVariable("index") Integer i,
            @RequestBody String topic) {
        topics.remove((int) i);
        topics.add(i, topic);
        return ResponseEntity.accepted().build();
    }
    // curl localhost:8080/topics/search/data
    @GetMapping("/topics/search/{text}")
    public ResponseEntity<Integer> searchTopic(@PathVariable("text") String text) {
        for (int i = 0; i < topics.size(); i++) {
            if (topics.get(i).contains(text)) {
                return ResponseEntity.ok(i);
            }
        }
        return ResponseEntity.ok(-1);
    }
    // curl localhost:8080/topics/count
    @GetMapping("/topics/count")
    public ResponseEntity<Integer> countTopics() {
        return ResponseEntity.ok(topics.size());
    }
    // curl -X POST localhost:8080/topics/0/create -d "123"
    @PostMapping("/topics/{index}/create")
    public ResponseEntity<Void> createMessage(
            @PathVariable("index") Integer i,
            @RequestBody String topic) {
        topics.add(i, topic);
        return ResponseEntity.accepted().build();
    }
    // curl localhost:8080/topics/0
    @GetMapping("topics/{index}")
    public ResponseEntity<List<String>> getMessages(@PathVariable("index") Integer i,
                                                    @RequestBody String topic) {
        return ResponseEntity.ok(Collections.singletonList(topics.get(i)));
    }
    // curl -X DELETE localhost:8080/topics/0/0
    @DeleteMapping("topics/{index}/{index2}")
    public ResponseEntity<Void> deleteTopic(@PathVariable("index") Integer
                                                    index, @PathVariable("index2") Integer
            index2) {
        Collections.singletonList(topics.get(index)).remove((int) index2);
        return ResponseEntity.noContent().build();
    }
    // curl -X DELETE localhost:8080/topics/search/data
    @DeleteMapping("/topics/search/{text}")
    public ResponseEntity<Void> searchAndDeleteTopics(@PathVariable("text") String text) {
        for (int i = 0; i < topics.size(); i++) {
            if (topics.get(i).contains(text)) {
                topics.remove(i);
                i--;
            }
        }
        return ResponseEntity.accepted().build();
    }
}