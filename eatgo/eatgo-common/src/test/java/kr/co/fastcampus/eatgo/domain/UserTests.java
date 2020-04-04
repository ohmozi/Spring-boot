package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

class UserTests {

    @Test
    public void creation(){

        User user = User.builder()
                .email("gown5@gmail.com")
                .name("ohmozi")
                .level(101L)
                .build();

        assertThat(user.getName(), is("ohmozi"));
        assertThat(user.isAdmin(), is(true));
        assertThat(user.isActive(), is(true));

        user.deactivate();
        assertThat(user.isActive(), is(false));
    }

    @Test
    public void accessTokenWithPassword(){

        User user = User.builder().password("ACCESSTOKEN").build();
        assertThat(user.getAccessToken(), is("ACCESSTOKE"));

    }

    @Test
    public void accessTokenWithoutPassword(){

        User user = new User();
        assertThat(user.getAccessToken(), is(""));
    }
}