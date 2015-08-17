package org.jgrades.logging.model;

import org.jgrades.logging.model.updater.PerLevelUpdater;
import org.jgrades.logging.model.updater.PerModuleAndLevelUpdater;
import org.jgrades.logging.model.updater.PerModuleUpdater;

public enum LoggingStrategy {
    LOG_FILE_PER_MODULE(new PerModuleUpdater()),
    LOG_FILE_PER_LEVEL(new PerLevelUpdater()),
    LOG_FILE_PER_MODULE_AND_LEVEL(new PerModuleAndLevelUpdater());

    private final XmlFileNameTagsUpdater updater;

    LoggingStrategy(XmlFileNameTagsUpdater updater) {
        this.updater = updater;
    }

    public XmlFileNameTagsUpdater getUpdater() {
        return updater;
    }
}
