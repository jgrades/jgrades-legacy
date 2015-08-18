package org.jgrades.data.api.entities;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "JG_DATA_ADMINISTRATOR")
@PrimaryKeyJoinColumn(name = "USER_ID")
public class Administrator extends User {
}
