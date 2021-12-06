package fr.ans.psc.pscapimajv2.api.ps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jupiter.tools.spring.test.mongo.annotation.MongoDataSet;
import com.jupiter.tools.spring.test.mongo.junit5.MongoDbExtension;
import fr.ans.psc.PscApiMajApplication;
import fr.ans.psc.model.Ps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
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
public class PsOperationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MongoTemplate mongoTemplateTest;
    @Autowired
    private MongoDatabaseFactory mongoDatabaseFactory;

    @BeforeEach
    public void setUp() {
    }

    @Test
    @MongoDataSet(value = "/dataset/8000000001.json", cleanBefore = true, cleanAfter = true)
    public void getPsById() throws Exception {

        mongoTemplateTest.findAll(Ps.class).forEach(ps -> System.out.println(ps.toString()));

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

}
