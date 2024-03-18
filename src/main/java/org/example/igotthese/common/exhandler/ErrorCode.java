package org.example.igotthese.common.exhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "사용자 입력에 대한 유효성 검증이 실패하였습니다."),

    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "중복된 유저 이름입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    USER_FORBIDDEN(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN, "해당 유저는 권한이 없습니다."),
    USER_POST_FORBIDDEN(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN, "해당 유저는 권한이 없습니다."),
    USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, "해당 유저는 권한이 없습니다."),


    POST_NOT_FOUND(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, "해당 게시판을 찾을 수 없습니다."),

    POKEMON_NOT_FOUND(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, "해당 포켓몬을 찾을 수 없습니다."),

    ;

    private final int value;
    private final HttpStatus status;
    private final String message;
}
