package org.example.igotthese.service;

import java.util.List;
import org.example.igotthese.data.dto.UserDto;
import org.example.igotthese.data.dto.UserDto.Create;
import org.example.igotthese.data.dto.UserDto.Response;
import org.example.igotthese.data.dto.UserDto.Update;

public interface UserService {
    Long saveUser(Create create);
    Long updateUser(Update update);
    UserDto.Response getUserById(Long id);
    List<Response> getAllUser();
    UserDto.Response getUserByUserName(String userName);
    void deleteUserById(Long id);
}
