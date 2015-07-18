package org.jgrades.lic.api.model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LicenceDateTimeAdapterTest {
    @Test
    public void shouldReturnCorrectFormatter() throws Exception {
        // given
        DateTime dateTime = new DateTime(2015, 7, 19, 5, 59, 59);
        String expectedString = "2015-07-19 05:59:59";

        // when
        DateTimeFormatter formatter = LicenceDateTimeAdapter.getLicDateTimeFormatter();
        String dateTimeString = dateTime.toString(formatter);

        // then
        assertThat(dateTimeString).isEqualTo(expectedString);
    }

    @Test
    public void shouldParseStringInCorrectFormat() throws Exception {
        // given
        String inputDateTimeString = "2015-07-19 05:59:59";
        DateTime expectedDateTime = new DateTime(2015, 7, 19, 5, 59, 59);

        // when
        DateTimeFormatter formatter = LicenceDateTimeAdapter.getLicDateTimeFormatter();
        DateTime dateTime = DateTime.parse(inputDateTimeString, formatter);

        // then
        assertThat(dateTime).isEqualTo(expectedDateTime);
    }
}
