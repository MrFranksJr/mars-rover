package io.tripled.marsrover.db;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableAutoConfiguration
@ContextConfiguration(classes = MongoDbConfiguration.class)
public class EmbeddedMongoTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @DisplayName("given object to save"
            + " when save object using MongoDB template"
            + " then object is saved")
    @Test
    public void test() {
        // given
        DBObject objectToSave = BasicDBObjectBuilder.start()
                .add("key", "value")
                .get();

        // when
        mongoTemplate.save(objectToSave, "collection");

        // then
        assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key")
                .containsOnly("value");
    }
}
