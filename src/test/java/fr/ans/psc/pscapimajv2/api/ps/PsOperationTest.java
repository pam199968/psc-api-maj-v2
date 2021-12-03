package fr.ans.psc.pscapimajv2.api.ps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import fr.ans.psc.PscApiMajApplication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class }) // pour restdocs
@SpringBootTest // (properties = "file.encoding=UTF-8")
@AutoConfigureMockMvc
@ContextConfiguration(classes = PscApiMajApplication.class)
public class PsOperationTest {

    /** The mock mvc. */
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("smoke test")
    @Disabled
    public void smokeTest() throws Exception {
        mockMvc.perform(get("/api/v1/ps/899700264593")
        .header("Accept", "application/json"))
                .andExpect(status().is2xxSuccessful()).andDo(print());
    }
}
