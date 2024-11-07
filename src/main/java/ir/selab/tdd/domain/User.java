package ir.selab.tdd.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    private final String username;
    private final String password;
    private String email;

    @Override
    public boolean equals(Object other) {
        if (other.getClass() != getClass()) 
            return false;
        return username == ((User) other).username && 
                password == ((User) other).password && 
                email == ((User) other).email;
    }
}
