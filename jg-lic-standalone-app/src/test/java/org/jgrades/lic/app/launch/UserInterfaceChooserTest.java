package org.jgrades.lic.app.launch;

import org.jgrades.lic.app.cli.ConsoleApplication;
import org.jgrades.lic.app.controller.JavafxApplication;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserInterfaceChooserTest {
    private UserInterfaceChooser chooser;

    @Before
    public void setUp() throws Exception {
        chooser = new UserInterfaceChooser();
    }

    @Test
    public void shouldRunGUI_whenNoArgs() throws Exception {
        // given
        String[] args = new String[]{};

        // when
        LicenceApplication application = chooser.choose(args);

        // then
        assertThat(application).isInstanceOf(JavafxApplication.class);
    }

    @Test
    public void shouldRunGUI_whenOneArgsExistsWithoutNoguiPhrase() throws Exception {
        // given
        String[] args = new String[]{"argument"};

        // when
        LicenceApplication application = chooser.choose(args);

        // then
        assertThat(application).isInstanceOf(JavafxApplication.class);
    }

    @Test
    public void shouldRunConsoleUI_whenOneArgsExistsWithNoguiPhrase() throws Exception {
        // given
        String[] args1 = new String[]{"nogui"};
        String[] args2 = new String[]{"-nogui"};
        String[] args3 = new String[]{"--nogui"};

        // when
        LicenceApplication application1 = chooser.choose(args1);
        LicenceApplication application2 = chooser.choose(args2);
        LicenceApplication application3 = chooser.choose(args3);

        // then
        assertThat(application1).isInstanceOf(ConsoleApplication.class);
        assertThat(application2).isInstanceOf(ConsoleApplication.class);
        assertThat(application3).isInstanceOf(ConsoleApplication.class);
    }

    @Test
    public void shouldRunGUI_whenMoreThenTwoDashesBeforeNogui() throws Exception {
        // given
        String[] args1 = new String[]{"---nogui"};
        String[] args2 = new String[]{"----nogui"};

        // when
        LicenceApplication application1 = chooser.choose(args1);
        LicenceApplication application2 = chooser.choose(args2);

        // then
        assertThat(application1).isInstanceOf(JavafxApplication.class);
        assertThat(application2).isInstanceOf(JavafxApplication.class);
    }

    @Test
    public void shouldRunGUI_whenNoguiPhraseIsSubSetOfArgument() throws Exception {
        // given
        String[] args1 = new String[]{"dnogui"};
        String[] args2 = new String[]{"noguix"};

        // when
        LicenceApplication application1 = chooser.choose(args1);
        LicenceApplication application2 = chooser.choose(args2);

        // then
        assertThat(application1).isInstanceOf(JavafxApplication.class);
        assertThat(application2).isInstanceOf(JavafxApplication.class);
    }

    @Test
    public void shouldRunConsoleUI_whenOneArgsExistsWithNoguiPhraseIgnoreCase() throws Exception {
        // given
        String[] args1 = new String[]{"NOGUI"};
        String[] args2 = new String[]{"-nOgUI"};
        String[] args3 = new String[]{"--NOgui"};

        // when
        LicenceApplication application1 = chooser.choose(args1);
        LicenceApplication application2 = chooser.choose(args2);
        LicenceApplication application3 = chooser.choose(args3);

        // then
        assertThat(application1).isInstanceOf(ConsoleApplication.class);
        assertThat(application2).isInstanceOf(ConsoleApplication.class);
        assertThat(application3).isInstanceOf(ConsoleApplication.class);
    }

    @Test
    public void shouldIgnoreSecondAndLaterArguments() throws Exception {
        // given
        String[] args1 = new String[]{"arg", "nogui"};
        String[] args2 = new String[]{"nogui", "q", "w", "r"};
        String[] args3 = new String[]{"---NOgui", "-nogui"};

        // when
        LicenceApplication application1 = chooser.choose(args1);
        LicenceApplication application2 = chooser.choose(args2);
        LicenceApplication application3 = chooser.choose(args3);

        // then
        assertThat(application1).isInstanceOf(JavafxApplication.class);
        assertThat(application2).isInstanceOf(ConsoleApplication.class);
        assertThat(application3).isInstanceOf(JavafxApplication.class);
    }
}
