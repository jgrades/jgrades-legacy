@XmlJavaTypeAdapters({@XmlJavaTypeAdapter(type = DateTime.class, value = LicenceDateTimeAdapter.class)}) package org.jgrades.lic.api.model;

import org.joda.time.DateTime;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
