package fr.ans.psc.pscapimajv2.api.ps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.bwaldvogel.mongo.MongoServer;
import fr.ans.psc.PscApiMajApplication;
import fr.ans.psc.api.PsApiDelegate;
import fr.ans.psc.model.Ps;
import fr.ans.psc.model.PsRef;
import fr.ans.psc.pscapimajv2.EnableMongoTestServer;
import fr.ans.psc.pscapimajv2.MongoTestServerConfiguration;
import fr.ans.psc.repository.PsRefRepository;
import fr.ans.psc.repository.PsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.util.List;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class}) // pour restdocs
@SpringBootTest(classes = PsOperationTest.TestConfiguration.class) // (properties = "file.encoding=UTF-8")
@AutoConfigureMockMvc
@ContextConfiguration(classes = {PscApiMajApplication.class, MongoTestServerConfiguration.class})
public class PsOperationTest {

    /**
     * The mock mvc.
     */
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PsApiDelegate psApiDelegate;

    @Autowired
    private PsRepository psRepository;
    @Autowired
    private PsRefRepository psRefRepository;
    @Autowired
    private MongoServer mongoServer;
    @Autowired
    private MongoTemplate mongoTemplateTest;
    @Autowired
    private MongoDatabaseFactory mongoDatabaseFactory;

    @BeforeEach
    public void setUp() throws IOException {
//        psRepository.deleteAll();
//        psRefRepository.deleteAll();
        mongoTemplateTest.dropCollection("ps");
        mongoTemplateTest.dropCollection("psref");

        ObjectMapper mapper = new ObjectMapper();
        List<Ps> psList = mapper.readValue(
                new File("/C:/WS/dev/ANS/prosanteconnect-platform-projects/psc-api-maj-v2/src/test/resources/PsDataSet.json"),
                new TypeReference<List<Ps>>() {
                });
        psList.forEach(ps -> mongoTemplateTest.save(ps, "ps"));
        List<PsRef> psRefList = mapper.readValue(
                new File("/C:/WS/dev/ANS/prosanteconnect-platform-projects/psc-api-maj-v2/src/test/resources/PsRefDataSet.json"),
                new TypeReference<List<PsRef>>() {
                });
        psRefList.forEach(psRef -> mongoTemplateTest.save(psRef, "psref"));
        mongoTemplateTest.findAll(Ps.class).forEach(ps -> {
            System.out.println("-------------------------------------STORED PS --------------- ");
            System.out.println(ps.toString());
        });
    }

    @Test
    public void getPsById() throws Exception {


        // cas nominal : Ps existe et retourné
        mockMvc.perform(get("/api/v1/ps/800000000001").header("Accept", "application/json"))
                .andExpect(status().is2xxSuccessful()).andDo(print());


        // id psRef ne pointe sur rien

        // psRef existe mais en pointe sur rien
    }

    @Test
    public void createNewPs() {

        // cas nominal : create OK

        // il existe déjà un Ps avec le même idNat

        // le body de la requête est mal formé
    }

    @Test
    public void deletePsById() {

        // cas nominal : delete OK

        // il n'y a pas de Ps avec le même idNat
    }

    @Test
    public void updatePsById() {

        // cas nominal : update OK

        // il n'existe pas de Ps avec le même idNat

        // le body de la requête est mal formé
    }

    @Configuration
    @EnableMongoTestServer
    @EnableMongoRepositories(basePackages = "fr.ans.psc.repository")
    protected static class TestConfiguration {
    }


}
