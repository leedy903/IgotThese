package org.example.igotthese.service.impl;

import static org.example.igotthese.common.exhandler.ErrorCode.DUPLICATE_USERNAME;
import static org.example.igotthese.common.exhandler.ErrorCode.POKEMON_NOT_FOUND;
import static org.example.igotthese.common.exhandler.ErrorCode.USER_NOT_FOUND;
import static org.example.igotthese.data.dto.UserDto.Create;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.igotthese.common.exception.DataDuplicationException;
import org.example.igotthese.common.exception.NoSuchDataException;
import org.example.igotthese.data.dao.HoldingSealDao;
import org.example.igotthese.data.dao.PokemonDao;
import org.example.igotthese.data.dao.UserDao;
import org.example.igotthese.data.dto.HoldingSealDto;
import org.example.igotthese.data.dto.PokemonDto;
import org.example.igotthese.data.dto.UserDto.Response;
import org.example.igotthese.data.dto.UserDto.Update;
import org.example.igotthese.data.entity.HoldingSeal;
import org.example.igotthese.data.entity.Pokemon;
import org.example.igotthese.data.entity.User;
import org.example.igotthese.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final HoldingSealDao holdingSealDao;
    private final PokemonDao pokemonDao;

    @Override
    @Transactional
    public Long saveUser(Create create) {
        // 중복 회원 조회
        validateDuplicateUserName(create.getUserName());
        // 보유 씰 N:M Entity 생성
        List<HoldingSeal> holdingSeals = getHoldingSeals(create.getHoldingSeals());
        // Dto -> Entity
        User user = create.toEntity(holdingSeals);
        User savedUser = userDao.saveUser(user);
        // holdingSeals save
        holdingSeals.stream()
                .forEach(holdingSeal -> holdingSealDao.saveHoldingSeal(holdingSeal));
        return savedUser.getId();
    }

    @Override
    @Transactional
    public Long updateUser(Update update) {
        // 유저 검증
        User user = userDao.getUserById(update.getUserId())
                .orElseThrow(() -> new NoSuchDataException(USER_NOT_FOUND));
        // 새로운 이름 중복 확인
        if (!update.getUserName().equals(user.getUserName())) {
            validateDuplicateUserName(update.getUserName());
        }
        // 새로운 포켓몬 정보 찾기
        List<HoldingSeal> holdingSeals = getHoldingSeals(update.getHoldingSeals());
        // 유저 업데이트
        user.change(update.getUserName(), holdingSeals);
        // 새로 업데이트 한 holdingSeals save
        user.getHoldingSeals().stream()
                .forEach(holdingSeal -> holdingSealDao.saveHoldingSeal(holdingSeal));
        return user.getId();
    }

    @Override
    public Response getUserById(Long id) {
        // 유저 조회
        User user = userDao.getUserById(id)
                .orElseThrow(() -> new NoSuchDataException(USER_NOT_FOUND));
        Response response = new Response(user);
        return response;
    }

    @Override
    public Response getUserByUserName(String userName) {
        // 유저 조회
        User user = userDao.getUserByUserName(userName)
                .orElseThrow(() -> new NoSuchDataException(USER_NOT_FOUND));
        Response response = new Response(user);
        return response;
    }

    @Override
    public List<Response> getAllUser() {
        List<User> allUser = userDao.getAllUser();
        return allUser.stream()
                .map(user -> new Response(user))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        User user = userDao.getUserById(id).orElseThrow(() -> new NoSuchDataException(USER_NOT_FOUND));
        user.getHoldingSeals().stream()
                .forEach(holdingSeal -> holdingSealDao.deleteHoldingSealById(holdingSeal.getId()));
        userDao.deleteUserById(id);
    }

    private void validateDuplicateUserName(String userName) {
        if (userDao.getUserByUserName(userName).isPresent()) {
            throw new DataDuplicationException(DUPLICATE_USERNAME);
        }
    }

    private List<HoldingSeal> getHoldingSeals(List<PokemonDto.Search> pokemons) {
        List<HoldingSeal> holdingSeals = new ArrayList<>();
        if (pokemons.isEmpty()) {
            return holdingSeals;
        }
        for (PokemonDto.Search pokemon : pokemons) {
            // 포켓몬 조회
            Pokemon pokemonByPokemonName = pokemonDao.getPokemonByPokemonName(pokemon.getPokemonName())
                    .orElseThrow(() -> new NoSuchDataException(POKEMON_NOT_FOUND));
            HoldingSeal holdingSeal = HoldingSeal.create(pokemonByPokemonName);
            holdingSeals.add(holdingSeal);
        }
        return holdingSeals;
    }

}
