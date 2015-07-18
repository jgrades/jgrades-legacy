package org.jgrades.logging.service.impl;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.jgrades.logging.JGLoggingFactory;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;


/**
 * Created by Piotr on 2015-07-16.
 */
public class JGradesLoggingMainteneceServiceImplTest {

    private JGradesLoggingMaintenaceServiceImpl loggingServiceImpl;

    @Before
    public void init(){
        loggingServiceImpl = new JGradesLoggingMaintenaceServiceImpl();
    }

    @Test(expected =  IllegalArgumentException.class)
    public void changeLogFileSizeToWrongValue() throws Exception{
        int incorrectSize = -1;

        loggingServiceImpl.changeLogFileSize(incorrectSize);
    }

    @Test
    public void changeLogFileSizeToCorrectValue()throws Exception{

        Logger logger = JGLoggingFactory.getLogger(JGLoggingFactory.class);
        loggingServiceImpl.changeLogFileSize(2);
    }

    @Test
    public void changeLogStoreLimitToWrongValue(){
        int incorrectDayValue = 100;

        loggingServiceImpl.changeLogStoreTimeLimit(incorrectDayValue);
    }

    @Test
    public void changeLogStoreLimitToCorrectValue(){

    }




}

