package com.thoughtworks.ecombackend.services;

import com.thoughtworks.ecombackend.dto.UserDto;
import com.thoughtworks.ecombackend.models.User;
import com.thoughtworks.ecombackend.repositories.UserRepository;
import com.thoughtworks.ecombackend.utils.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map((user) -> mapToDTO(user, new UserDto()))
                .collect(Collectors.toList());
    }

    public UserDto get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDto()))
                .orElseThrow(() -> new NotFoundException());
    }

    public Long create(final UserDto userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDto userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDto mapToDTO(final User user, final UserDto userDTO) {
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setContactNumber(user.getContactNumber());
        return userDTO;
    }

    private void mapToEntity(final UserDto userDTO, final User user) {
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setContactNumber(userDTO.getContactNumber());
    }

}
