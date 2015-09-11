package org.jgrades.rest;

import org.jgrades.property.ApplicationPropertiesConfig;
import org.jgrades.rest.lic.LicMockConfig;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationPropertiesConfig.class, LicMockConfig.class, RestConfig.class})
@WebAppConfiguration
@RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class DateTimeMapperTest {
    private static final DateTime EXPECTED_DATE_TIME = DateTime.now();

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(this)
                .setHandlerExceptionResolvers(new RestConfig().restExceptionResolver())
                .build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    DateTime returnDateTime() {
        return EXPECTED_DATE_TIME;
    }

    @Test
    public void shouldDeserializeDateTimeCorrectly_fromWebServiceResponse() throws Exception {
        // when
        MvcResult mvcResult = mockMvc.perform(get("/test")).andReturn();
        String timestamp = mvcResult.getResponse().getContentAsString();

        // then
        DateTime dateTime = new DateTime(Long.parseLong(timestamp));
        assertThat(dateTime).isEqualTo(EXPECTED_DATE_TIME);
    }
}
