package org.jgrades.lic.app.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jgrades.lic.api.model.LicenceProperty;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.StrictAssertions.failBecauseExceptionWasNotThrown;

public class PropertiesTextAreaParserTest {
    private PropertiesTextAreaParser parser = new PropertiesTextAreaParser();

    @Test
    public void shouldReturnEmptyList_whenNull() throws Exception {
        // when
        List<LicenceProperty> properties = parser.getProperties(null);

        // then
        assertThat(properties).isNotNull();
        assertThat(properties).isEmpty();
    }

    @Test
    public void shouldReturnEmptyList_whenEmptyString() throws Exception {
        // when
        List<LicenceProperty> properties = parser.getProperties(StringUtils.EMPTY);

        // then
        assertThat(properties).isNotNull();
        assertThat(properties).isEmpty();
    }

    @Test
    public void shouldReturnOneProperty() throws Exception {
        // given
        String content = "name=value";

        // when
        List<LicenceProperty> properties = parser.getProperties(content);

        // then
        assertThat(properties).hasSize(1);
        assertThat(properties.get(0)).isEqualTo(buildProperty("name", "value"));
    }

    @Test
    public void shouldReturnOneProperty_whenValueIsEmpty() throws Exception {
        // given
        String content = "name=";

        // when
        List<LicenceProperty> properties = parser.getProperties(content);

        // then
        assertThat(properties).hasSize(1);
        assertThat(properties.get(0)).isEqualTo(buildProperty("name", StringUtils.EMPTY));
    }

    @Test
    public void shouldReturnOneProperty_whenValueHasEqualsSign() throws Exception {
        // given
        String content = "name==v=al=u=e=";

        // when
        List<LicenceProperty> properties = parser.getProperties(content);

        // then
        assertThat(properties).hasSize(1);
        assertThat(properties.get(0)).isEqualTo(buildProperty("name", "=v=al=u=e="));
    }

    @Test
    public void shouldReturnOneProperty_whenNameHasSpaceCharacters() throws Exception {
        // given
        String content = "   name   =value";

        // when
        List<LicenceProperty> properties = parser.getProperties(content);

        // then
        assertThat(properties).hasSize(1);
        assertThat(properties.get(0)).isEqualTo(buildProperty("   name   ", "value"));
    }

    @Test
    public void shouldReturnEmptyNameAndValue() throws Exception {
        // given
        String content = "=";

        // when
        List<LicenceProperty> properties = parser.getProperties(content);

        // then
        assertThat(properties).hasSize(1);
        assertThat(properties.get(0)).isEqualTo(buildProperty(StringUtils.EMPTY, StringUtils.EMPTY));
    }

    @Test
    public void shouldIgnoreEmptyLines() throws Exception {
        // given
        String content = "\nname=value\n\n";

        // when
        List<LicenceProperty> properties = parser.getProperties(content);

        // then
        assertThat(properties).hasSize(1);
        assertThat(properties.get(0)).isEqualTo(buildProperty("name", "value"));
    }

    @Test
    public void shouldReturnTwoProperties() throws Exception {
        // given
        String content = "name1=value1\n" +
                "name2=value2";

        // when
        List<LicenceProperty> properties = parser.getProperties(content);

        // then
        assertThat(properties).hasSize(2);
        assertThat(properties.get(0)).isEqualTo(buildProperty("name1", "value1"));
        assertThat(properties.get(1)).isEqualTo(buildProperty("name2", "value2"));
    }

    @Test
    public void shouldReturnFiveProperties() throws Exception {
        // given
        String content = "\n" +
                "key=value\n" +
                "\n" +
                "key2==value 2\n" +
                " key3 = val =\n" +
                "= key4==value4:\n" +
                "\n\n\n\n\n" +
                "= key4==value4:";

        List<LicenceProperty> expectedProperties =
                Lists.newArrayList(
                        buildProperty("key", "value"),
                        buildProperty("key2", "=value 2"),
                        buildProperty(" key3 ", " val ="),
                        buildProperty(StringUtils.EMPTY, " key4==value4:"),
                        buildProperty(StringUtils.EMPTY, " key4==value4:")
                );

        // when
        List<LicenceProperty> properties = parser.getProperties(content);

        // then
        assertThat(properties).isEqualTo(expectedProperties);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailed_whenThereIsNoEqualsSignInLine() throws Exception {
        // given
        String content = "TEST";

        // when
        parser.getProperties(content);

        // then
        failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }

    private LicenceProperty buildProperty(String name, String value) {
        LicenceProperty property = new LicenceProperty();
        property.setName(name);
        property.setValue(value);
        return property;
    }
}
