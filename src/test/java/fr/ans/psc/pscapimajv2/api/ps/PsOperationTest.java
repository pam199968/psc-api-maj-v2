package fr.ans.psc.pscapimajv2.api.ps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.*;

import ch.qos.logback.classic.LoggerContext;
import com.jupiter.tools.spring.test.mongo.annotation.MongoDataSet;
import com.jupiter.tools.spring.test.mongo.junit5.MongoDbExtension;
import fr.ans.psc.PscApiMajApplication;
import fr.ans.psc.delegate.PsApiDelegateImpl;
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

    private MemoryAppender memoryAppender;

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

        String body = "{\n" +
                "      \"idType\": \"8\",\n" +
                "      \"id\": \"00000000001\",\n" +
                "      \"nationalId\": \"800000000001\",\n" +
                "      \"lastName\": \"DOE\",\n" +
                "      \"firstName\": \"JOHN''\",\n" +
                "      \"dateOfBirth\": \"17/12/1983\",\n" +
                "      \"birthAddressCode\": \"57463\",\n" +
                "      \"birthCountryCode\": \"99000\",\n" +
                "      \"birthAddress\": \"METZ\",\n" +
                "      \"genderCode\": \"F\",\n" +
                "      \"phone\": \"0682292033\",\n" +
                "      \"email\": \"zazou57@hotmail.fr\",\n" +
                "      \"salutationCode\": \"MME\",\n" +
                "      \"professions\": [\n" +
                "        {\n" +
                "          \"exProId\": \"50C\",\n" +
                "          \"code\": \"50\",\n" +
                "          \"categoryCode\": \"C\",\n" +
                "          \"salutationCode\": \"\",\n" +
                "          \"lastName\": \"LEY\",\n" +
                "          \"firstName\": \"CELINE\",\n" +
                "          \"expertises\": [\n" +
                "            {\n" +
                "              \"expertiseId\": \"SSM69\",\n" +
                "              \"typeCode\": \"S\",\n" +
                "              \"code\": \"SM69\"\n" +
                "            }\n" +
                "          ],\n" +
                "          \"workSituations\": [\n" +
                "            {\n" +
                "              \"situId\": \"SSA04\",\n" +
                "              \"modeCode\": \"S\",\n" +
                "              \"activitySectorCode\": \"SA04\",\n" +
                "              \"pharmacistTableSectionCode\": \"\",\n" +
                "              \"roleCode\": \"\",\n" +
                "              \"structures\": [\n" +
                "                {\n" +
                "                  \"siteSIRET\": null,\n" +
                "                  \"siteSIREN\": null,\n" +
                "                  \"siteFINESS\": null,\n" +
                "                  \"legalEstablishmentFINESS\": null,\n" +
                "                  \"structureTechnicalId\": null,\n" +
                "                  \"legalCommercialName\": null,\n" +
                "                  \"publicCommercialName\": null,\n" +
                "                  \"recipientAdditionalInfo\": null,\n" +
                "                  \"geoLocationAdditionalInfo\": null,\n" +
                "                  \"streetNumber\": null,\n" +
                "                  \"streetNumberRepetitionIndex\": null,\n" +
                "                  \"streetCategoryCode\": null,\n" +
                "                  \"streetLabel\": null,\n" +
                "                  \"distributionMention\": null,\n" +
                "                  \"cedexOffice\": null,\n" +
                "                  \"postalCode\": null,\n" +
                "                  \"communeCode\": null,\n" +
                "                  \"countryCode\": null,\n" +
                "                  \"phone\": null,\n" +
                "                  \"phone2\": null,\n" +
                "                  \"fax\": null,\n" +
                "                  \"email\": null,\n" +
                "                  \"departmentCode\": null,\n" +
                "                  \"oldStructureId\": null,\n" +
                "                  \"registrationAuthority\": null\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }";

        ResultActions returned = mockMvc.perform(get("/api/v1/ps/800000000001")
                .header("Accept", "application/json"))
                .andExpect(status().is2xxSuccessful());

        returned.andExpect(content().json(body));
        assertThat(memoryAppender.contains("Ps 800000000001 has been found", Level.INFO)).isTrue();

        ResultActions returned2 = mockMvc.perform(get("/api/v1/ps/800000000011")
                .header("Accept", "application/json"))
                .andExpect(status().is2xxSuccessful());

        returned.andExpect(content().json(body));
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
    @DisplayName(value = "should create a new Ps")
    public void createNewPs() {

    }

    @Test
    @DisplayName(value = "should not create Ps if already exists")
    public void createExistingPsFailed() {
    }

    @Test
    @DisplayName(value = "should not create Ps if malformed request body")
    public void createMalformedPsFailed() throws Exception {
        mockMvc.perform(post("/api/v1/ps").header("Accept", "application/json")
                .contentType("application/json").content("{\"toto\":\"titi\"}"))
                .andExpect(status().is4xxClientError()).andDo(print());
    }


    @Test
    @DisplayName(value = "should delete Ps by Id")
    public void deletePsById() {

    }

    @Test
    @DisplayName(value = "should not delete Ps if not exists")
    public void deletePsFailed() {

    }

    @Test
    @DisplayName(value = "should update Ps")
    public void updatePsById() {

    }

    @Test
    @DisplayName(value = "should not update Ps if not exists")
    public void updateAbsentPsFailed() {

    }

    @Test
    @DisplayName(value = "should not update Ps if malformed request body")
    public void updateMalformedPsFailed() {

    }

    @Test
    @DisplayName(value = "should create or reactivate Ps")
    public void forceCreatePs() {

    }

}
