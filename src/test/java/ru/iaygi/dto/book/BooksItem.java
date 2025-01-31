package ru.iaygi.dto.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BooksItem {

	private String website;
	private Integer pages;
	private String subTitle;
	private String author;
	private String isbn;
	private String publisher;
	private String description;
	private String title;
	@JsonProperty("publish_date")
	private String publishDate;
}