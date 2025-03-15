package com.example.TicketMovieOnline.repository.Auth;

import com.example.TicketMovieOnline.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("""
    select t from Token t
    where t.user.Id = :userId and t.loggedOut = false
""")
    List<Token> findAllAccessTokensByUser(Long userId);

    Optional<Token> findByAccessToken(String token);

    Optional<Token> findByRefreshToken(String token);
}
