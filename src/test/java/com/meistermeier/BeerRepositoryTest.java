package com.meistermeier;

import com.meistermeier.beer.BeerApplication;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BeerApplication.class)
@WebAppConfiguration
public class BeerRepositoryTest {

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    public void index() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(document("index-page", links(
                linkWithRel("breweries").description("The <<resources-brewery,Brewery resource>>"),
                linkWithRel("beers").description("The <<resources-beer,Beer resource>>"),
                linkWithRel("profile").description("The ALPS profile for the service")
                ),
                responseFields(fieldWithPath("_links").description("<<resources-index-links,Links>> to other resources"))
        ));
    }

    @Test
    public void beers() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/beers")).andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(document("beer-list", links(
                linkWithRel("self").description("Canonical link for this resource"),

                 linkWithRel("profile").description("The ALPS profile for the service")
                ),
                responseFields(
                        fieldWithPath("_embedded.beers").description("A list of <<resources-beer, Beer resources>>"),
                        fieldWithPath("_links").description("<<resources-index-links,Links>> to other resources")
                )

        ));
    }

}
