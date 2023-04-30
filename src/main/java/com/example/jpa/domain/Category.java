package com.example.jpa.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.example.jpa.domain.item.Item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

	@Id
	@GeneratedValue
	@Column(name = "CATEGORY_ID")
	private Long id;

	private String name;

	@ManyToMany(mappedBy = "categories")
	private List<Item> items = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PARENT_CATEGORY")
	private Category parent;

	@OneToMany(mappedBy = "parent")
	private List<Category> children;

	// === 연관 관계 편의 메소드 ===
	public void addChildCategory(Category category) {
		getChildren().add(category);
		category.setParent(parent);
	}
}
