package fr.ans.psc.pscapimajv2.api.ps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.tools.spring.test.mongo.annotation.MongoDataSet;
import com.jupiter.tools.spring.test.mongo.junit5.MongoDbExtension;

import fr.ans.psc.PscApiMajApplication;
import fr.ans.psc.api.PsApiDelegate;
import fr.ans.psc.model.Ps;
import fr.ans.psc.model.PsRef;
import fr.ans.psc.repository.PsRefRepository;
import fr.ans.psc.repository.PsRepository;

@ExtendWith(MongoDbExtension.class)
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class }) // pour restdocs
@SpringBootTest
@AutoConfigureDataMongo
@AutoConfigureMockMvc
@ContextConfiguration(classes = { PscApiMajApplication.class })
@DirtiesContext
@ActiveProfiles("test")
public class PsOperationTest {

	/**
	 * The mock mvc.
	 */
	@Autowired
	private MockMvc mockMvc;

//    @Autowired
//    private PsApiDelegate psApiDelegate;
//
//    @Autowired
//    private PsRepository psRepository;
//    
//    @Autowired
//    private PsRefRepository psRefRepository;

	@Autowired
	private MongoTemplate mongoTemplateTest;

	@Autowired
	private MongoDatabaseFactory mongoDatabaseFactory;

	@BeforeEach
	public void setUp() throws IOException {

//        ObjectMapper mapper = new ObjectMapper();
//        URL datasetUrl = Thread.currentThread().getContextClassLoader().getResource("PsDataSet.json");
//        URL refDatasetUrl = Thread.currentThread().getContextClassLoader().getResource("PsRefDataSet.json");
//        List<Ps> psList = mapper.readValue(
//                new File(datasetUrl.getFile()),
//                new TypeReference<List<Ps>>() {
//                });
//        psList.forEach(ps -> mongoTemplateTest.save(ps, "ps"));
//        List<PsRef> psRefList = mapper.readValue(
//                new File(refDatasetUrl.getFile()),
//                new TypeReference<List<PsRef>>() {
//                });
//        psRefList.forEach(psRef -> mongoTemplateTest.save(psRef, "psref"));
//        mongoTemplateTest.findAll(Ps.class).forEach(ps -> {
//            System.out.println("-------------------------------------STORED PS --------------- ");
//            System.out.println(ps.toString());
//        });
	}

	@Test
	@MongoDataSet(value = "/dataset/8000000001.json", cleanBefore = true, cleanAfter = true)
	public void getPsById() throws Exception {

		mongoTemplateTest.findAll(Ps.class).forEach(ps -> {
			System.out.println(ps.toString());
		});

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
