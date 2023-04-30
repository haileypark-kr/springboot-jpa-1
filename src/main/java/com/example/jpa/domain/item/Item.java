package com.example.jpa.domain.item;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.example.jpa.domain.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {

	@Id
	@GeneratedValue
	@Column(name = "ITEM_ID")
	private Long id;

	private String name;

	private int price;

	private int stockQuantity;

	@ManyToMany
	@JoinTable(name = "CATEGORY_ITEM",
		joinColumns = @JoinColumn(name = "FK_CATEGORY"),
		inverseJoinColumns = @JoinColumn(name = "FK_ITEM")
	)
	private List<Category> categories = new ArrayList<>();
}
