package com.vamshi.jobapply.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MongoConfig {
    private final String connectionUri;
    private final String databaseName;
}
