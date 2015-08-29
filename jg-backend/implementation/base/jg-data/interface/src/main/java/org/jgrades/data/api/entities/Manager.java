package org.jgrades.data.api.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "JG_DATA_MANAGER")
@PrimaryKeyJoinColumn(name = "USER_ID")
@Data
public class Manager extends User {
}
