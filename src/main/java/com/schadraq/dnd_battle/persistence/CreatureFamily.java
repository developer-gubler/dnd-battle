package com.schadraq.dnd_battle.persistence;

import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "creature_family", uniqueConstraints={@UniqueConstraint(columnNames = {"classification_id", "name"})
	})
@Data
public class CreatureFamily extends BaseEntity {

	@Id @GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "classification_id", referencedColumnName = "id", nullable = false)
	@NotNull
	private CreatureClassification classication;

	@Column(nullable = false)
	@NotNull(message = "Name must be between 1 to 32 characters")
	@Size(min = 1, max = 32)
	private String name;

	protected CreatureFamily() {
		
	}

	public CreatureFamily(CreatureClassification classification, String name) {
		this.classication = classification;
		this.name = name;
	}

	public CreatureFamily(UUID id, CreatureClassification classification, String name) {
		this(classification, name);
		this.id = id;
	}
}
