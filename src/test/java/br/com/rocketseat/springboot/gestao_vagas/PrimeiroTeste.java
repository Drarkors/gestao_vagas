package br.com.rocketseat.springboot.gestao_vagas;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrimeiroTeste {


  @Test
  public void shouldTest() {
    var user1 = new User();
    user1.setAge(10);
    user1.setName("Test");

    var user2 = new User();
    user2.setAge(10);
    user2.setName(new String("Test"));

    assertTrue(user1.equals(user2));
  }

  @Getter
  @Setter
  @NoArgsConstructor
  class User {
    private String name;
    private int age;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;

      if (o == null || getClass() != o.getClass()) return false;

      User user = (User) o;

      return age == user.age && name.equals(user.name);
    }

    @Override
    public int hashCode() {
      return Objects.hash(name, age);
    }
  }

}
