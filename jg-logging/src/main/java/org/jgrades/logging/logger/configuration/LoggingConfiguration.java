package org.jgrades.logging.logger.configuration;


public enum LoggingConfiguration {

    LOG_PER_TYPE{

        @Override
        public String toString(){
            return "LOG_PER_TYPE";
        }
    },

    LOG_PER_MODULE{

        @Override
        public String toString(){
            return "LOG_PER_MODULE";
        }
    },

    LOG_PER_TYPE_MODULE {

        @Override
        public String toString(){
            return "LOG_PER_TYPE_MODULE";
        }
    };
}
