package org.jgrades.lic.service;

import org.jgrades.lic.BaseTest;
import org.junit.Test;

public class LicenceManagingServiceImplTest extends BaseTest {
    @Test
    public void shouldInstallCorrectly_whenLicenceAndSignatureAreCorrect() throws Exception {
        //TODO
    }

    @Test
    public void shouldThrow_whenLicenceFileNotFound() throws Exception {
        //TODO
    }

    @Test
    public void shouldThrow_whenSignatureFileNotFound() throws Exception {
        //TODO
    }

    @Test
    public void shouldThrow_whenSignatureIsNotValid() throws Exception {
        //TODO
    }

    @Test
    public void shouldThrow_whenKeystoreNotFound() throws Exception {
        //TODO
    }

    @Test
    public void shouldThrow_whenKeystoreIsIncorrect() throws Exception {
        //TODO
    }

    @Test
    public void shouldThrow_whenSecDataFileNotMatchedToKeystore() throws Exception {
        //TODO
    }

    @Test
    public void shouldUninstallCorrectlyAndRemoveFiles_whenLicenceWasInstalled() throws Exception {
        //TODO
    }

    @Test
    public void shouldReturnAllLicencesInSystem() throws Exception {
        //TODO
    }

    @Test
    public void shouldReturnEmptyList_whenThereIsNoLicencesInSystem() throws Exception {
        //TODO
    }
}
