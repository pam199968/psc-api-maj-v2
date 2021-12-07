package fr.ans.psc.pscapimajv2.api.ps;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.jupiter.tools.spring.test.mongo.junit5.MongoDbExtension;
import fr.ans.psc.PscApiMajApplication;
import fr.ans.psc.delegate.StructureApiDelegateImpl;
import fr.ans.psc.utils.MemoryAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class, MongoDbExtension.class}) // pour restdocs
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataMongo
@ContextConfiguration(classes = PscApiMajApplication.class)
@DirtiesContext
@ActiveProfiles("test")
public class StructureOperationTest {

    @Autowired
    private MockMvc mockMvc;

    private MemoryAppender memoryAppender;

    @BeforeEach
    public void setUp() {
        Logger logger = (Logger) LoggerFactory.getLogger(StructureApiDelegateImpl.class);
        memoryAppender = new MemoryAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.INFO);
        logger.addAppender(memoryAppender);
        memoryAppender.start();
    }

    @Test
    @DisplayName(value = "should get a structure by its technical id")
    public void getStructureById() {

    }

    @Test
    @DisplayName(value = "should not get a structure if it doesn't exist")
    public void getStructureByIdFailed() {

    }

    @Test
    @DisplayName(value = "should create a new structure")
    public void createNewStructure() {

    }

    @Test
    @DisplayName(value = "should not create a structure if exists already")
    public void createExistingStructureFailed() {

    }

    @Test
    @DisplayName(value = "should not create a structure if malformed request body")
    public void createMalformedStructureFailed() {

    }

    @Test
    @DisplayName(value = "should update a structure")
    public void updateStructure() {

    }

    @Test
    @DisplayName(value = "should not update a structure if not exists")
    public void updateAbsentStructureFailed() {

    }

    @Test
    @DisplayName(value = "should not update a structure with malformed request body")
    public void updateMalformedStructureFailed() {

    }

    @Test
    @DisplayName(value = "should delete a structure")
    public void deleteStructureById() {

    }

    @Test
    @DisplayName(value = "should not delete a structure if not exists")
    public void deleteAbsentStructureFailed() {

    }
}
