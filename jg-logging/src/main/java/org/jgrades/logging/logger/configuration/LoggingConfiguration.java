package org.jgrades.logging.logger.configuration;

/**
 * Created by Piotr on 2015-07-15.
 */
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
