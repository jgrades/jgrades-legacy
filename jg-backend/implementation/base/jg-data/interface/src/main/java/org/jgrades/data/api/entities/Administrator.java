package org.jgrades.data.api.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "JG_DATA_ADMINISTRATOR")
@PrimaryKeyJoinColumn(name = "USER_ID")
@Data
public class Administrator extends User implements Serializable {
}
