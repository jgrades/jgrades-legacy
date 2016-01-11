/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.logging;

import com.project.StrangerClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class JgLoggerTest {
    @Mock
    private Logger loggerMock;

    private JgLogger jgLogger = new JgLogger(JgLoggerTest.class);

    @Test
    public void shouldSetJGradesModuleName_whenConstructWithJGradesClass() throws Exception {
        // given
        Class clazz = JgLoggerTest.class;

        // when
        JgLogger jgLogger = new JgLogger(clazz);

        // then
        String moduleName = (String) ReflectionTestUtils.getField(jgLogger, "moduleName");
        assertThat(moduleName).isEqualTo("logging");
    }

    @Test
    public void shouldSetExternalLibModuleName_whenConstructNotWithJGradesClass() throws Exception {
        // given
        Class clazz = StrangerClass.class;

        // when
        JgLogger jgLogger = new JgLogger(clazz);

        // then
        String moduleName = (String) ReflectionTestUtils.getField(jgLogger, "moduleName");
        assertThat(moduleName).isEqualTo("external-lib");
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forTraceLogging1() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);

        // when
        jgLogger.trace("msg");

        // then
        verify(loggerMock).trace("msg");
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forTraceLogging2() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Object object = new Object();

        // when
        jgLogger.trace("msg", object);

        // then
        verify(loggerMock).trace("msg", object);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forTraceLogging3() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Object object1 = new Object();
        Object object2 = new Object();

        // when
        jgLogger.trace("msg", object1, object2);

        // then
        verify(loggerMock).trace("msg", object1, object2);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forTraceLogging4() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Object[] objects = new Object[]{new Object(), new Object(), new Object(), new Object()};

        // when
        jgLogger.trace("msg", objects);

        // then
        verify(loggerMock).trace("msg", objects);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forTraceLogging5() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Throwable throwable = new Throwable();

        // when
        jgLogger.trace("msg", throwable);

        // then
        verify(loggerMock).trace("msg", throwable);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forTraceLoggingWithMarker1() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);

        // when
        jgLogger.trace(markerMock, "msg");

        // then
        verify(loggerMock).trace(markerMock, "msg");
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forTraceLoggingWithMarker2() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);
        Object object = new Object();

        // when
        jgLogger.trace(markerMock, "msg", object);

        // then
        verify(loggerMock).trace(markerMock, "msg", object);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forTraceLoggingWithMarker3() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);
        Object object1 = new Object();
        Object object2 = new Object();

        // when
        jgLogger.trace(markerMock, "msg", object1, object2);

        // then
        verify(loggerMock).trace(markerMock, "msg", object1, object2);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forTraceLoggingWithMarker4() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);
        Object[] objects = new Object[]{new Object(), new Object(), new Object(), new Object()};

        // when
        jgLogger.trace(markerMock, "msg", objects);

        // then
        verify(loggerMock).trace(markerMock, "msg", objects);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forTraceLoggingWithMarker5() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);
        Throwable throwable = new Throwable();

        // when
        jgLogger.trace(markerMock, "msg", throwable);

        // then
        verify(loggerMock).trace(markerMock, "msg", throwable);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forDebugLogging1() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);

        // when
        jgLogger.debug("msg");

        // then
        verify(loggerMock).debug("msg");
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forDebugLogging2() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Object object = new Object();

        // when
        jgLogger.debug("msg", object);

        // then
        verify(loggerMock).debug("msg", object);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forDebugLogging3() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Object object1 = new Object();
        Object object2 = new Object();

        // when
        jgLogger.debug("msg", object1, object2);

        // then
        verify(loggerMock).debug("msg", object1, object2);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forDebugLogging4() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Object[] objects = new Object[]{new Object(), new Object(), new Object(), new Object()};

        // when
        jgLogger.debug("msg", objects);

        // then
        verify(loggerMock).debug("msg", objects);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forDebugLogging5() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Throwable throwable = new Throwable();

        // when
        jgLogger.debug("msg", throwable);

        // then
        verify(loggerMock).debug("msg", throwable);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forDebugLoggingWithMarker1() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);

        // when
        jgLogger.debug(markerMock, "msg");

        // then
        verify(loggerMock).debug(markerMock, "msg");
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forDebugLoggingWithMarker2() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);
        Object object = new Object();

        // when
        jgLogger.debug(markerMock, "msg", object);

        // then
        verify(loggerMock).debug(markerMock, "msg", object);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forDebugLoggingWithMarker3() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);
        Object object1 = new Object();
        Object object2 = new Object();

        // when
        jgLogger.debug(markerMock, "msg", object1, object2);

        // then
        verify(loggerMock).debug(markerMock, "msg", object1, object2);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forDebugLoggingWithMarker4() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);
        Object[] objects = new Object[]{new Object(), new Object(), new Object(), new Object()};

        // when
        jgLogger.debug(markerMock, "msg", objects);

        // then
        verify(loggerMock).debug(markerMock, "msg", objects);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forDebugLoggingWithMarker5() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);
        Throwable throwable = new Throwable();

        // when
        jgLogger.debug(markerMock, "msg", throwable);

        // then
        verify(loggerMock).debug(markerMock, "msg", throwable);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forInfoLogging1() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);

        // when
        jgLogger.info("msg");

        // then
        verify(loggerMock).info("msg");
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forInfoLogging2() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Object object = new Object();

        // when
        jgLogger.info("msg", object);

        // then
        verify(loggerMock).info("msg", object);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forInfoLogging3() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Object object1 = new Object();
        Object object2 = new Object();

        // when
        jgLogger.info("msg", object1, object2);

        // then
        verify(loggerMock).info("msg", object1, object2);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forInfoLogging4() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Object[] objects = new Object[]{new Object(), new Object(), new Object(), new Object()};

        // when
        jgLogger.info("msg", objects);

        // then
        verify(loggerMock).info("msg", objects);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forInfoLogging5() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Throwable throwable = new Throwable();

        // when
        jgLogger.info("msg", throwable);

        // then
        verify(loggerMock).info("msg", throwable);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forInfoLoggingWithMarker1() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);

        // when
        jgLogger.info(markerMock, "msg");

        // then
        verify(loggerMock).info(markerMock, "msg");
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forInfoLoggingWithMarker2() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);
        Object object = new Object();

        // when
        jgLogger.info(markerMock, "msg", object);

        // then
        verify(loggerMock).info(markerMock, "msg", object);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forInfoLoggingWithMarker3() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);
        Object object1 = new Object();
        Object object2 = new Object();

        // when
        jgLogger.info(markerMock, "msg", object1, object2);

        // then
        verify(loggerMock).info(markerMock, "msg", object1, object2);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forInfoLoggingWithMarker4() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);
        Object[] objects = new Object[]{new Object(), new Object(), new Object(), new Object()};

        // when
        jgLogger.info(markerMock, "msg", objects);

        // then
        verify(loggerMock).info(markerMock, "msg", objects);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forInfoLoggingWithMarker5() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);
        Throwable throwable = new Throwable();

        // when
        jgLogger.info(markerMock, "msg", throwable);

        // then
        verify(loggerMock).info(markerMock, "msg", throwable);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forWarnLogging1() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);

        // when
        jgLogger.warn("msg");

        // then
        verify(loggerMock).warn("msg");
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forWarnLogging2() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Object object = new Object();

        // when
        jgLogger.warn("msg", object);

        // then
        verify(loggerMock).warn("msg", object);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forWarnLogging3() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Object object1 = new Object();
        Object object2 = new Object();

        // when
        jgLogger.warn("msg", object1, object2);

        // then
        verify(loggerMock).warn("msg", object1, object2);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forWarnLogging4() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Object[] objects = new Object[]{new Object(), new Object(), new Object(), new Object()};

        // when
        jgLogger.warn("msg", objects);

        // then
        verify(loggerMock).warn("msg", objects);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forWarnLogging5() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Throwable throwable = new Throwable();

        // when
        jgLogger.warn("msg", throwable);

        // then
        verify(loggerMock).warn("msg", throwable);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forWarnLoggingWithMarker1() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);

        // when
        jgLogger.warn(markerMock, "msg");

        // then
        verify(loggerMock).warn(markerMock, "msg");
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forWarnLoggingWithMarker2() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);
        Object object = new Object();

        // when
        jgLogger.warn(markerMock, "msg", object);

        // then
        verify(loggerMock).warn(markerMock, "msg", object);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forWarnLoggingWithMarker3() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);
        Object object1 = new Object();
        Object object2 = new Object();

        // when
        jgLogger.warn(markerMock, "msg", object1, object2);

        // then
        verify(loggerMock).warn(markerMock, "msg", object1, object2);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forWarnLoggingWithMarker4() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);
        Object[] objects = new Object[]{new Object(), new Object(), new Object(), new Object()};

        // when
        jgLogger.warn(markerMock, "msg", objects);

        // then
        verify(loggerMock).warn(markerMock, "msg", objects);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forWarnLoggingWithMarker5() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);
        Throwable throwable = new Throwable();

        // when
        jgLogger.warn(markerMock, "msg", throwable);

        // then
        verify(loggerMock).warn(markerMock, "msg", throwable);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forErrorLogging1() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);

        // when
        jgLogger.error("msg");

        // then
        verify(loggerMock).error("msg");
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forErrorLogging2() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Object object = new Object();

        // when
        jgLogger.error("msg", object);

        // then
        verify(loggerMock).error("msg", object);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forErrorLogging3() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Object object1 = new Object();
        Object object2 = new Object();

        // when
        jgLogger.error("msg", object1, object2);

        // then
        verify(loggerMock).error("msg", object1, object2);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forErrorLogging4() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Object[] objects = new Object[]{new Object(), new Object(), new Object(), new Object()};

        // when
        jgLogger.error("msg", objects);

        // then
        verify(loggerMock).error("msg", objects);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forErrorLogging5() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Throwable throwable = new Throwable();

        // when
        jgLogger.error("msg", throwable);

        // then
        verify(loggerMock).error("msg", throwable);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forErrorLoggingWithMarker1() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);

        // when
        jgLogger.error(markerMock, "msg");

        // then
        verify(loggerMock).error(markerMock, "msg");
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forErrorLoggingWithMarker2() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);
        Object object = new Object();

        // when
        jgLogger.error(markerMock, "msg", object);

        // then
        verify(loggerMock).error(markerMock, "msg", object);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forErrorLoggingWithMarker3() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);
        Object object1 = new Object();
        Object object2 = new Object();

        // when
        jgLogger.error(markerMock, "msg", object1, object2);

        // then
        verify(loggerMock).error(markerMock, "msg", object1, object2);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forErrorLoggingWithMarker4() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);
        Object[] objects = new Object[]{new Object(), new Object(), new Object(), new Object()};

        // when
        jgLogger.error(markerMock, "msg", objects);

        // then
        verify(loggerMock).error(markerMock, "msg", objects);
    }

    @Test
    public void shouldInvokeOriginalMethodLogger_forErrorLoggingWithMarker5() throws Exception {
        // given
        ReflectionTestUtils.setField(jgLogger, "logger", loggerMock);
        Marker markerMock = Mockito.mock(Marker.class);
        Throwable throwable = new Throwable();

        // when
        jgLogger.error(markerMock, "msg", throwable);

        // then
        verify(loggerMock).error(markerMock, "msg", throwable);
    }
}
