package fr.ans.psc.pscapimajv2.api.ps;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jupiter.tools.spring.test.mongo.annotation.MongoDataSet;
import com.jupiter.tools.spring.test.mongo.junit5.MongoDbExtension;
import fr.ans.psc.PscApiMajApplication;
import fr.ans.psc.delegate.StructureApiDelegateImpl;
import fr.ans.psc.model.Structure;
import fr.ans.psc.repository.StructureRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @Autowired
    private StructureRepository structureRepository;

    private MemoryAppender memoryAppender;
    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

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
    @MongoDataSet(value = "/dataset/structure.json", cleanBefore = true, cleanAfter = true)
    public void getStructureById() throws Exception {
        Structure structure = structureRepository.findByStructureTechnicalId("1");
        String structureAsJsonString = objectWriter.writeValueAsString(structure);

        mockMvc.perform(get("/api/v1/structure/1").header("Accept", "application/json"))
                .andExpect(status().is(200)).andExpect(content().json(structureAsJsonString));

        assertThat(memoryAppender.contains("Structure 1 has been found", Level.INFO)).isTrue();
    }

    @Test
    @DisplayName(value = "should not get a structure if it doesn't exist")
    @MongoDataSet(value = "/dataset/structure.json", cleanBefore = true, cleanAfter = true)
    public void getStructureByIdFailed() throws Exception {
        mockMvc.perform(get("/api/v1/structure/3").header("Accept", "application/json"))
                .andExpect(status().is(404));

        assertThat(memoryAppender.contains("Structure 3 not found", Level.WARN)).isTrue();
    }

    @Test
    @DisplayName(value = "should create a new structure")
    public void createNewStructure() throws Exception {
        mockMvc.perform(post("/api/v1/structure").header("Accept", "application/json")
                .contentType("application/json").content("{\"_id\":\"61b0debad9b1af764debd6fa\",\"siteSIRET\":\"125 137 196 15574\"," +
                        "\"siteSIREN\":\"125 137 196\",\"siteFINESS\":null,\"legalEstablishmentFINESS\":null,\"structureTechnicalId\":\"1\","+
                        "\"legalCommercialName\":\"Structure One\",\"publicCommercialName\":\"Structure One\",\"recipientAdditionalInfo\":\"info +\","+
                        "\"geoLocationAdditionalInfo\":\"geoloc info +\",\"streetNumber\":\"1\",\"streetNumberRepetitionIndex\":\"bis\","+
                        "\"streetCategoryCode\":\"rue\",\"streetLabel\":\"Zorro\",\"distributionMention\":\"c/o Bernardo\",\"cedexOffice\":\"75117\","+
                        "\"postalCode\":\"75017\",\"communeCode\":\"75\",\"countryCode\":\"FR\",\"phone\":\"0123456789\",\"phone2\":\"0623456789\","+
                        "\"fax\":\"0198765432\",\"email\":\"structure@one.fr\",\"departmentCode\":\"99\",\"oldStructureId\":\"101\",\"registrationAuthority\":\"CIA\"}"))
                .andExpect(status().is(201));

        assertEquals(structureRepository.count(), 1);
        assertEquals(structureRepository.findByStructureTechnicalId("1").getLegalCommercialName(),"Structure One");
        assertThat(memoryAppender.contains("New structure created with structure technical id 1", Level.INFO)).isTrue();
    }

    @Test
    @DisplayName(value = "should not create a structure if exists already")
    @MongoDataSet(value = "/dataset/structure.json", cleanBefore = true, cleanAfter = true)
    public void createExistingStructureFailed() throws Exception {
        mockMvc.perform(post("/api/v1/structure").header("Accept", "application/json")
                .contentType("application/json").content("{\"siteSIRET\":\"125 137 196 15574\",\"siteSIREN\":\"125 137 196\",\"siteFINESS\":null,"+
                        "\"legalEstablishmentFINESS\":null,\"structureTechnicalId\":\"1\",\"legalCommercialName\":\"Structure One\","+
                        "\"publicCommercialName\":\"Structure One\",\"recipientAdditionalInfo\":\"info +\",\"geoLocationAdditionalInfo\":\"geoloc info +\"," +
                        "\"streetNumber\":\"1\",\"streetNumberRepetitionIndex\":\"bis\",\"streetCategoryCode\":\"rue\",\"streetLabel\":\"Zorro\"," +
                        "\"distributionMention\":\"c/o Bernardo\",\"cedexOffice\":\"75117\",\"postalCode\":\"75017\",\"communeCode\":\"75\"," +
                        "\"countryCode\":\"FR\",\"phone\":\"0123456789\",\"phone2\":\"0623456789\",\"fax\":\"0198765432\","+
                        "\"email\":\"structure@one.fr\",\"departmentCode\":\"99\",\"oldStructureId\":\"101\",\"registrationAuthority\":\"CIA\"}"))
                .andExpect(status().is(409));

        assertEquals(structureRepository.count(), 2);
        assertThat(memoryAppender.contains("New structure created with structure technical id 1", Level.INFO)).isFalse();
        assertThat(memoryAppender.contains("Structure 1 exists already", Level.WARN)).isTrue();
    }

    @Test
    @DisplayName(value = "should not create a structure if malformed request body")
    public void createMalformedStructureFailed() throws Exception {
        mockMvc.perform(post("/api/v1/structure").header("Accept", "application/json")
                .contentType("application/json").content("{\"structureTechnicalId\":\"\",\"legalCommercialName\":\"Structure One\"}"))
                .andExpect(status().is(400));

        mockMvc.perform(post("/api/v1/structure").header("Accept", "application/json")
                .contentType("application/json").content("{\"legalCommercialName\":\"Structure One\"}"))
                .andExpect(status().is(400));
    }

    @Test
    @DisplayName(value = "should update a structure")
    @MongoDataSet(value = "/dataset/structure.json", cleanBefore = true, cleanAfter = true)
    public void updateStructure() throws Exception {
        Structure structure = structureRepository.findByStructureTechnicalId("1");
        assertEquals(structure.getLegalCommercialName(), "Structure One");

        mockMvc.perform(put("/api/v1/structure").header("Accept", "application/json")
                .contentType("application/json").content("{\"structureTechnicalId\":\"1\",\"legalCommercialName\":\"Structure updated\"}"))
                .andExpect(status().is(200));

        assertThat(memoryAppender.contains("Structure 1 successfully updated", Level.INFO)).isTrue();
        structure = structureRepository.findByStructureTechnicalId("1");
        assertEquals(structure.getLegalCommercialName(), "Structure updated");
    }

    @Test
    @DisplayName(value = "should not update a structure if not exists")
    @MongoDataSet(value = "/dataset/structure.json", cleanBefore = true, cleanAfter = true)
    public void updateAbsentStructureFailed() throws Exception {
        mockMvc.perform(put("/api/v1/structure").header("Accept", "application/json")
                .contentType("application/json").content("{\"structureTechnicalId\":\"1000\",\"legalCommercialName\":\"Structure One\"}"))
                .andExpect(status().is(404));

        assertThat(memoryAppender.contains("Structure 1000 successfully updated", Level.INFO)).isFalse();
        assertThat(memoryAppender.contains("Structure 1000 not found", Level.WARN)).isTrue();
    }

    @Test
    @DisplayName(value = "should delete a structure")
    @MongoDataSet(value = "/dataset/structure.json", cleanBefore = true, cleanAfter = true)
    public void deleteStructureById() throws Exception {
        mockMvc.perform(delete("/api/v1/structure/1").header("Accept", "application/json"))
                .andExpect(status().is(204));

        assertThat(memoryAppender.contains("Structure 1 successfully removed", Level.INFO)).isTrue();
        assertEquals(structureRepository.count(), 1);
    }

    @Test
    @DisplayName(value = "should not delete a structure if not exists")
    @MongoDataSet(value = "/dataset/structure.json", cleanBefore = true, cleanAfter = true)
    public void deleteAbsentStructureFailed() throws Exception {
        mockMvc.perform(delete("/api/v1/structure/3").header("Accept", "application/json"))
                .andExpect(status().is(404));

        assertThat(memoryAppender.contains("Structure 3 successfully removed", Level.INFO)).isFalse();
        assertThat(memoryAppender.contains("Structure 3 not found", Level.WARN)).isTrue();
        assertEquals(structureRepository.count(), 2);
    }
}
