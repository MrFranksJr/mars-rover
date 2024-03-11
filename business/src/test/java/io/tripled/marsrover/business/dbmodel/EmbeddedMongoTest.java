package io.tripled.marsrover.business.dbmodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableAutoConfiguration
//@ContextConfiguration(classes = MongoDbConfiguration.class)
public class EmbeddedMongoTest {
    @Autowired
    private MongoTemplate mongoTemplate;


}