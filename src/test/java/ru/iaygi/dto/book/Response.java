package ru.iaygi.dto.book;

import lombok.Data;

import java.util.List;

@Data
public class Response {

	private List<BooksItem> books;
}