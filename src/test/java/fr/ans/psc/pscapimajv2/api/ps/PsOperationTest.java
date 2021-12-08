package fr.ans.psc.pscapimajv2.api.ps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.*;

import ch.qos.logback.classic.LoggerContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jupiter.tools.spring.test.mongo.annotation.ExpectedMongoDataSet;
import com.jupiter.tools.spring.test.mongo.annotation.MongoDataSet;
import com.jupiter.tools.spring.test.mongo.junit5.MongoDbExtension;
import fr.ans.psc.PscApiMajApplication;
import fr.ans.psc.delegate.PsApiDelegateImpl;
import fr.ans.psc.model.Ps;
import fr.ans.psc.model.PsRef;
import fr.ans.psc.repository.PsRefRepository;
import fr.ans.psc.repository.PsRepository;
import fr.ans.psc.utils.ApiUtils;
import fr.ans.psc.utils.MemoryAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

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
    private PsRepository psRepository;
    @Autowired
    private PsRefRepository psRefRepository;
    @Autowired
    private MongoTemplate mongoTemplateTest;
    @Autowired
    private MongoDatabaseFactory mongoDatabaseFactory;

    private MemoryAppender memoryAppender;
    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @BeforeEach
    public void setUp() {
        Logger logger = (Logger) LoggerFactory.getLogger(PsApiDelegateImpl.class);
        memoryAppender = new MemoryAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.INFO);
        logger.addAppender(memoryAppender);
        memoryAppender.start();
    }

    @Test
    @DisplayName(value = "should get Ps by id, nominal case")
    @MongoDataSet(value = "/dataset/ps_2_psref_entries.json", cleanBefore = true, cleanAfter = true)
    public void getPsById() throws Exception {

        Ps storedPs = psRepository.findByNationalId("800000000001");
        String psAsJsonString = objectWriter.writeValueAsString(storedPs);

        ResultActions firstPsRefRequest = mockMvc.perform(get("/api/v1/ps/800000000001")
                .header("Accept", "application/json"))
                .andExpect(status().is2xxSuccessful());

        firstPsRefRequest.andExpect(content().json(psAsJsonString));
        assertThat(memoryAppender.contains("Ps 800000000001 has been found", Level.INFO)).isTrue();

        ResultActions secondPsRefRequest = mockMvc.perform(get("/api/v1/ps/800000000011")
                .header("Accept", "application/json"))
                .andExpect(status().is2xxSuccessful());

        secondPsRefRequest.andExpect(content().json(psAsJsonString));
        assertThat(memoryAppender.contains("Ps 800000000001 has been found", Level.INFO)).isTrue();

    }

    @Test
    @DisplayName(value = "should not get Ps if deactivated")
    @MongoDataSet(value = "/dataset/deactivated_ps.json", cleanBefore = true, cleanAfter = true)
    public void getPsDeactivated() throws Exception {
        mockMvc.perform(get("/api/v1/ps/800000000002")
                .header("Accept", "application/json"))
                .andExpect(status().is(404));
        assertThat(memoryAppender.contains("Ps 800000000002 is deactivated", Level.WARN)).isTrue();
    }

    @Test
    @DisplayName(value = "should not get Ps if not exist")
    @MongoDataSet(value = "/dataset/ps_2_psref_entries.json", cleanBefore = true, cleanAfter = true)
    public void getNotExistingPs() throws Exception {
        mockMvc.perform(get("/api/v1/ps/800000000003")
                .header("Accept", "application/json"))
                .andExpect(status().is(404));
        assertThat(memoryAppender.contains("No Ps found with nationalIdRef 800000000003", Level.WARN)).isTrue();
    }

    @Test
    @DisplayName(value = "should create a brand new Ps")
    public void createNewPs() throws Exception {
        mockMvc.perform(post("/api/v1/ps").header("Accept", "application/json")
                .contentType("application/json").content("{\n" +
                        "\"idType\": \"8\",\n" +
                        "\"id\": \"00000000001\",\n" +
                        "\"nationalId\": \"800000000001\"\n" +
                        "}"))
                .andExpect(status().is(201));
        assertThat(memoryAppender.contains("Ps 800000000001 successfully stored or updated", Level.INFO)).isTrue();
        assertThat(memoryAppender.contains("PsRef 800000000001 has been reactivated", Level.INFO)).isFalse();
    }

    @Test
    @DisplayName(value = "should not create a Ps if already exists and still activated")
    @MongoDataSet(value = "/dataset/ps_2_psref_entries.json", cleanBefore = true, cleanAfter = true)
    public void createStillActivatedPsFailed() throws Exception {
        mockMvc.perform(post("/api/v1/ps").header("Accept", "application/json")
        .contentType("application/json").content("{\n" +
                        "\"idType\": \"8\",\n" +
                        "\"id\": \"00000000001\",\n" +
                        "\"nationalId\": \"800000000001\"\n" +
                        "}"))
                .andExpect(status().is(409));
        assertThat(memoryAppender.contains("Ps 800000000001 already exists and is activated, will not be updated", Level.WARN)).isTrue();
        assertThat(memoryAppender.contains("Ps 800000000001 successfully stored or updated", Level.INFO)).isFalse();
    }

    @Test
    @DisplayName(value = "should reactivate Ps if already exists")
    @MongoDataSet(value = "/dataset/deactivated_ps.json", cleanBefore = true, cleanAfter = true)
    public void reactivateExistingPs() throws Exception {
        mockMvc.perform(post("/api/v1/ps").header("Accept", "application/json")
                .contentType("application/json").content("{\n" +
                        "\"idType\": \"8\",\n" +
                        "\"id\": \"00000000002\",\n" +
                        "\"nationalId\": \"800000000002\"\n" +
                        "}"))
                .andExpect(status().is(201));
        assertThat(memoryAppender.contains("Ps 800000000002 successfully stored or updated", Level.INFO)).isTrue();
        assertThat(memoryAppender.contains("PsRef 800000000002 has been reactivated", Level.INFO)).isTrue();
    }

    @Test
    @DisplayName(value = "should not create Ps if malformed request body")
    public void createMalformedPsFailed() throws Exception {
        mockMvc.perform(post("/api/v1/ps").header("Accept", "application/json")
                .contentType("application/json").content("{\"toto\":\"titi\"}"))
                .andExpect(status().is(400));
    }


    @Test
    @DisplayName(value = "should delete Ps by Id")
    @MongoDataSet(value = "/dataset/ps_2_psref_entries.json", cleanBefore = true, cleanAfter = true)
    public void deletePsById() throws Exception {
        mockMvc.perform(delete("/api/v1/ps/800000000001")
                .header("Accept", "application/json"))
                .andExpect(status().is(204));

        assertThat(memoryAppender.contains("No Ps found with nationalId 800000000001, will not be deleted", Level.WARN)).isFalse();
        assertThat(memoryAppender.contains("Ps 800000000001 successfully deleted", Level.INFO)).isTrue();
        assertThat(memoryAppender.contains("Ps 800000000011 successfully deleted", Level.INFO)).isTrue();

        PsRef psRef1 = psRefRepository.findPsRefByNationalIdRef("800000000001");
        PsRef psRef2 = psRefRepository.findPsRefByNationalIdRef("800000000011");

        assertThat(ApiUtils.isPsRefActivated(psRef1)).isFalse();
        assertThat(ApiUtils.isPsRefActivated(psRef2)).isFalse();
    }

    @Test
    @DisplayName(value = "should not delete Ps if not exists")
    @MongoDataSet(value = "/dataset/ps_2_psref_entries.json", cleanBefore = true, cleanAfter = true)
    @ExpectedMongoDataSet(value = "/dataset/ps_2_psref_entries.json")
    public void deletePsFailed() throws Exception {
        mockMvc.perform(delete("/api/v1/ps/800000000003")
                .header("Accept", "application/json"))
                .andExpect(status().is(404));

        assertThat(memoryAppender.contains("No Ps found with nationalId 800000000003, will not be deleted", Level.WARN)).isTrue();
        assertThat(memoryAppender.contains("Ps 800000000003 successfully deleted", Level.INFO)).isFalse();
    }

    @Test
    @DisplayName(value = "should update Ps")
    @MongoDataSet(value = "/dataset/ps_2_psref_entries.json", cleanBefore = true, cleanAfter = true)
    public void updatePsById() throws Exception {
        mockMvc.perform(put("/api/v1/ps").header("Accept", "application/json")
                .contentType("application/json").content("{\n" +
                        "\"idType\": \"8\",\n" +
                        "\"id\": \"00000000001\",\n" +
                        "\"nationalId\": \"800000000001\"\n" +
                        "}"))
                .andExpect(status().is(200));

        assertThat(memoryAppender.contains("No Ps found with nationalId 800000000001, can not update it", Level.WARN)).isFalse();
        assertThat(memoryAppender.contains("Ps 800000000001 successfully updated", Level.INFO)).isTrue();
    }

    @Test
    @DisplayName(value = "should not update Ps if not exists")
    public void updateAbsentPsFailed() throws Exception {
        mockMvc.perform(put("/api/v1/ps").header("Accept", "application/json")
                .contentType("application/json").content("{\n" +
                "\"idType\": \"8\",\n" +
                "\"id\": \"00000000001\",\n" +
                "\"nationalId\": \"800000000001\"\n" +
                "}"))
                .andExpect(status().is(404));

        assertThat(memoryAppender.contains("No Ps found with nationalId 800000000001, can not update it", Level.WARN)).isTrue();
        assertThat(memoryAppender.contains("Ps 800000000001 successfully updated", Level.INFO)).isFalse();
    }

    @Test
    @DisplayName(value = "should not update Ps if deactivated")
    @MongoDataSet(value = "/dataset/deactivated_ps.json", cleanBefore = true, cleanAfter = true)
    public void updateDeactivatedPsFailed() throws Exception {
        mockMvc.perform(put("/api/v1/ps").header("Accept", "application/json")
                .contentType("application/json").content("{\n" +
                        "\"idType\": \"8\",\n" +
                        "\"id\": \"00000000002\",\n" +
                        "\"nationalId\": \"800000000002\"\n" +
                        "}"))
                .andExpect(status().is(404));

        assertThat(memoryAppender.contains("No Ps found with nationalId 800000000002, can not update it", Level.WARN)).isTrue();
        assertThat(memoryAppender.contains("Ps 800000000002 successfully updated", Level.INFO)).isFalse();
    }

    @Test
    @DisplayName(value = "should not update Ps if malformed request body")
    @MongoDataSet(value = "/dataset/ps_2_psref_entries.json", cleanBefore = true, cleanAfter = true)
    public void updateMalformedPsFailed() throws Exception {
        // Id not present
        mockMvc.perform(put("/api/v1/ps").header("Accept", "application/json")
                .contentType("application/json").content("{\n" +
                        "\"idType\": \"8\",\n" +
                        "\"id\": \"00000000001\",\n" +
                        "}"))
                .andExpect(status().is(400));

        // Id is blank
        mockMvc.perform(put("/api/v1/ps").header("Accept", "application/json")
                .contentType("application/json").content("{\n" +
                        "\"idType\": \"8\",\n" +
                        "\"id\": \"00000000001\",\n" +
                        "\"nationalId\": \"\"\n" +
                        "}"))
                .andExpect(status().is(400));
    }

    @Test
    @DisplayName(value = "should physically delete Ps")
    @MongoDataSet(value = "/dataset/3_ps_before_delete.json", cleanBefore = true, cleanAfter = true)
    @ExpectedMongoDataSet(value = "/dataset/1_ps_after_delete.json")
    public void physicalDeleteById() throws Exception {
        mockMvc.perform(delete("/api/v1/ps/force/800000000001")
                .header("Accept", "application/json"))
                .andExpect(status().is(204));

        assertThat(memoryAppender.contains("No Ps found with id 800000000001, could not delete it", Level.WARN)).isFalse();
        assertThat(memoryAppender.contains("Ps 800000000001 successfully deleted", Level.INFO)).isTrue();
        assertThat(memoryAppender.contains("PsRef 800000000001 pointing on Ps 800000000001 successfully removed", Level.INFO)).isTrue();
        assertThat(memoryAppender.contains("PsRef 800000000011 pointing on Ps 800000000001 successfully removed", Level.INFO)).isTrue();
        assertThat(memoryAppender.contains("Ps 800000000002 successfully deleted", Level.INFO)).isFalse();

        assertEquals(psRefRepository.count(), 2);
        assertEquals(psRepository.count(), 2);

        // physical delete of deactivated Ps
        mockMvc.perform(delete("/api/v1/ps/force/800000000002")
                .header("Accept", "application/json"))
                .andExpect(status().is(204));

        assertThat(memoryAppender.contains("Ps 800000000002 successfully deleted", Level.INFO)).isTrue();
        assertThat(memoryAppender.contains("PsRef 800000000002 pointing on Ps 800000000002 successfully removed", Level.INFO)).isTrue();

        assertEquals(psRefRepository.count(), 1);
        assertEquals(psRepository.count(), 1);
    }
}
