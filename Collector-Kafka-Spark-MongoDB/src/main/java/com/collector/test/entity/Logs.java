package com.collector.test.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "logs")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Logs {
    @Id
    String _id;
    String logMessage;
    public Logs(String s) {
        this.logMessage=s;
    }
}
