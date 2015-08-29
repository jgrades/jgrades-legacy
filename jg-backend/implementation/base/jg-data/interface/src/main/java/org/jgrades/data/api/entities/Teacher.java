package org.jgrades.data.api.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "JG_DATA_TEACHER")
@PrimaryKeyJoinColumn(name = "USER_ID")
@Data
public class Teacher extends User {
}
