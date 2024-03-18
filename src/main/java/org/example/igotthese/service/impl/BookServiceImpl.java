package org.example.igotthese.service.impl;

import static org.example.igotthese.common.exhandler.ErrorCode.POKEMON_NOT_FOUND;
import static org.example.igotthese.common.exhandler.ErrorCode.USER_FORBIDDEN;
import static org.example.igotthese.common.exhandler.ErrorCode.USER_NOT_FOUND;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.igotthese.common.exception.ForbiddenException;
import org.example.igotthese.common.exception.NoSuchDataException;
import org.example.igotthese.common.exhandler.ErrorCode;
import org.example.igotthese.data.dao.UserDao;
import org.example.igotthese.data.dto.BookDto.MyBook;
import org.example.igotthese.data.dto.BookDto.Ranker;
import org.example.igotthese.data.dto.BookDto.Ranking;
import org.example.igotthese.data.dto.BookDto.Response;
import org.example.igotthese.data.entity.User;
import org.example.igotthese.service.BookService;
import org.geolatte.geom.M;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final UserDao userDao;

    @Override
    public Response getBookByUserId(Long userId) {
        User user = userDao.getUserById(userId)
                .orElseThrow(() -> new NoSuchDataException(USER_NOT_FOUND));
        MyBook myBook = new MyBook(user);
        List<User> top10User = userDao.getTop10User();
        List<Ranker> rankers = top10User.stream()
                .map(topUser -> new Ranker(topUser))
                .collect(Collectors.toList());
        Response response = new Response(myBook, rankers);
        return response;
    }

    @Override
    public MyBook getRankerBook(Long userId) {
        List<User> top10User = userDao.getTop10User();
        User ranker = top10User.stream()
                .filter(topUser -> topUser.getId() == userId)
                .findFirst()
                .orElseThrow(() -> new ForbiddenException(USER_FORBIDDEN));
        MyBook rankerBook = new MyBook(ranker);
        return rankerBook;
    }

}
