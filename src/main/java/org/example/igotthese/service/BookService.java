package org.example.igotthese.service;

import java.util.List;
import org.example.igotthese.data.dto.BookDto;
import org.example.igotthese.data.dto.BookDto.Response;

public interface
BookService {
    BookDto.Response getBookByUserId(Long userId);
    BookDto.MyBook getRankerBook(Long userId);
}