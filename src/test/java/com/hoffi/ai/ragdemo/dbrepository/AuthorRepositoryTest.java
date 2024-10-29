package com.hoffi.ai.ragdemo.dbrepository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.hoffi.ai.ragdemo.TestcontainersConfiguration;
import com.hoffi.ai.ragdemo.dbmodel.Author;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

//@DataJpaTest // not if using testcontainers
@SpringBootTest
@Import(TestcontainersConfiguration.class)
@ActiveProfiles({"test", "testcontainers"})
public class AuthorRepositoryTest {
  @Autowired
  AuthorRepository authorRepository;

  @Test
  public void testSave() {
    Author newAuthor = Author.builder()
        .username("unittest")
        .email("unittest@unittest.com")
        .bio("used for unittest")
        .build();
    Author insertedAuthor = authorRepository.save(newAuthor);
    Author retrievedAuthor = authorRepository.findById(insertedAuthor.getId())
        .orElseThrow();

    assertAll(
        () -> assertEquals(insertedAuthor.getId(), retrievedAuthor.getId()),
        () -> assertEquals("unittest", retrievedAuthor.getUsername())
    );

    // cleanup
    authorRepository.delete(retrievedAuthor);
  }

  @Test
  public void testFlywayTestMigrations() {
    List<Author> allAuthors = authorRepository.findAll();
    // System.err.println("=== HERE HOFFI: " + allAuthors.size());
    // ObjectMapper mapper = new ObjectMapper();
    // for (Author author: allAuthors) {
    //   try {
    //     System.err.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(author));
    //   } catch (JsonProcessingException e) {
    //     throw new RuntimeException(e);
    //   }
    // }

    assertAll(
        () -> assertEquals(4, allAuthors.size()),
        () -> assertEquals(2, allAuthors.stream()
            .filter(author -> author.getUsername().matches("^test\\d+")).toList().size()
        )
    );
  }
}
