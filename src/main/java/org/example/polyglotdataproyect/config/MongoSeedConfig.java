package org.example.polyglotdataproyect.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MongoSeedConfig {

    @Bean
    CommandLineRunner seedMongoDatabase(MongoTemplate mongoTemplate) {
        return args -> {

            // Seed a simple collection: exercises
            // Only insert if it doesn't already exist (Mongo equivalent of ON CONFLICT DO NOTHING)
            if (!mongoTemplate.collectionExists("exercises")) {
                mongoTemplate.createCollection("exercises");
            }

            long count = mongoTemplate.getCollection("exercises").countDocuments();

            if (count == 0) {
                Map<String, Object> doc = new HashMap<>();
                doc.put("name", "Push Ups");
                doc.put("type", "strength");
                doc.put("difficulty", "medium");
                doc.put("durationSec", 60);

                mongoTemplate.save(doc, "exercises");

                System.out.println("✅ Seeded MongoDB: inserted initial exercise.");
            } else {
                System.out.println("ℹ️ MongoDB already has exercises, skipping seed.");
            }
        };
    }
}
