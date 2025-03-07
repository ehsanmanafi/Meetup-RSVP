package com.collector.test;

import com.collector.test.entity.Logs;
import com.collector.test.service.LogsService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KafkaListeners {

    @Autowired
    private SparkSession sparkSession;

    private final JavaSparkContext sparkContext;
    private final LogsService logsService;

    private static final int BATCH_SIZE = 10; // The number of messages needed for batch processing
    private final List<String> batchBuffer = new ArrayList<>(); // Temporary list to store messages

    public KafkaListeners(LogsService logsService) {
        this.logsService = logsService;
        this.sparkContext = new JavaSparkContext(sparkSession.sparkContext());
    }

    @KafkaListener(topics = "app-rsvp", groupId = "groupId")
    public void listener(ConsumerRecord<String, String> data) {
        synchronized (batchBuffer) {
            batchBuffer.add(data.value());

            if (batchBuffer.size() >= BATCH_SIZE) {
                processBatch(new ArrayList<>(batchBuffer)); // Batch processing
                batchBuffer.clear(); // Clear the buffer after processing
            }
        }
    }

    private void processBatch(List<String> messages) {
        System.out.println("Processing batch of " + messages.size() + " messages");

        // Convert input data into an RDD
        JavaRDD<String> rdd = sparkContext.parallelize(messages);

        // Processing (e.g., converting to uppercase)
        JavaRDD<String> processedRDD = rdd.map(String::toUpperCase);

        // Convert RDD to a list of DataDocument objects for storage in MongoDB
        List<Logs> processedDocs = processedRDD
                .map(Logs::new) // Convert each processed string into a DataDocument
                .collect(); // Convert to a Java list

        // Save to MongoDB
        logsService.SaveLogs(processedDocs);
    }
}
