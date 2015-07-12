@XmlJavaTypeAdapters({@XmlJavaTypeAdapter(type = DateTime.class, value = DateTimeAdapter.class)}) package org.jgrades.api.lic.model;

import org.joda.time.DateTime;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
