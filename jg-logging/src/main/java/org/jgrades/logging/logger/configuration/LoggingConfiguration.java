package org.jgrades.logging.logger.configuration;


public enum LoggingConfiguration {

    LOG_PER_TYPE{
        public String toString(){
            return "LOG_PER_TYPE";
        }
    },

    LOG_PER_MODULE{
        public String toString(){
            return "LOG_PER_MODULE";
        }
    },

    LOG_PER_TYPE_MODULE {
        public String toString(){
            return "LOG_PER_TYPE_MODULE";
        }
    };
}
