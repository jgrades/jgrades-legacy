package org.jgrades.admin.accounts;

import org.jgrades.admin.api.accounts.MassAccountCreatorService;
import org.jgrades.admin.api.model.StudentCsvEntry;
import org.jgrades.admin.api.model.StudentMassCreatingSettings;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MassAccountCreatorServiceImpl implements MassAccountCreatorService {

    @Override//TODO
    public void createStudents(Set<StudentCsvEntry> students, StudentMassCreatingSettings settings) {

    }
}
