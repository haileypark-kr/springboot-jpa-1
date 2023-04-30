package com.example.jpa.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "M")
public class Movie extends Item {
	private String director;
	private String actor;
}
