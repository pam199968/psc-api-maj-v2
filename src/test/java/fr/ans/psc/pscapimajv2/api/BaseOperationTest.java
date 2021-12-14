package fr.ans.psc.pscapimajv2.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jupiter.tools.spring.test.mongo.junit5.MongoDbExtension;
import fr.ans.psc.PscApiMajApplication;
import fr.ans.psc.utils.MemoryAppender;
import org.junit.Rule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
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
public abstract class BaseOperationTest {

    @Rule
    protected JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    protected MemoryAppender memoryAppender;
    protected final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    @Autowired
    protected MockMvc mockMvc;
}
