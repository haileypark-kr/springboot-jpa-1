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
import com.example.jpa.exception.NotEnoughStockException;

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

	// === 비즈니스 로직 메소드 ===
	public void removeStock(int count) {

		if (count > stockQuantity) {
			throw new NotEnoughStockException("재고가 충분하지 않습니다.");
		}

		stockQuantity -= count;
	}

	public void change(int price, String name, int stockQuantity) {
		setPrice(price);
		setName(name);
		setStockQuantity(stockQuantity);
	}
}
