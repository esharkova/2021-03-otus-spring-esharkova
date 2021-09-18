package ru.diasoft.library.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.diasoft.library.models.Users;
import ru.diasoft.library.repositories.UsersRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Users> users = Optional.ofNullable(usersRepository.findByLogin(s)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь " + s + " не зарегистрирован")));
        return new User(users.get().getLogin(), users.get().getPassword(), AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
    }
}
