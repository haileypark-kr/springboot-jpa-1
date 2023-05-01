package com.example.jpa.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = "B")
public class Book extends Item {
	private String author;
	private String isbn;
}
