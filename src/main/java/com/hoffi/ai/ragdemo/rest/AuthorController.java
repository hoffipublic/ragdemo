package com.hoffi.ai.ragdemo.rest;

import com.hoffi.ai.ragdemo.dbmodel.Author;
import com.hoffi.ai.ragdemo.dbrepository.AuthorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("authors")
public class AuthorController {

  @Autowired
  private AuthorRepository authorRepository;

  @GetMapping("/{authorId}/")
  @Operation(summary = "Get author details")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the author",
          content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Author.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid name (authorId) supplied", content = @Content),
      @ApiResponse(responseCode = "404", description = "Author not found", content = @Content)})
  public @ResponseBody ResponseEntity<Author> detail(
      @Parameter(name = "authorId", description = "Author id", required = true)
      @PathVariable("authorId") Integer authorId)
  {
    System.out.println("Leggo " + authorId);
    Optional<Author> author=authorRepository.findById(authorId);
    if (author.isPresent()) {
      System.out.println("Cercando "+authorId+" ho trovato "+author.get().getUsername());
      return new ResponseEntity<>(author.get(), HttpStatus.OK);
    } else {
      throw new RuntimeException();
    }
  }

  @GetMapping("/username/{username}/")
  @Operation(summary = "Get author details by username")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the author",
          content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Author.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid name (username) supplied", content = @Content),
      @ApiResponse(responseCode = "404", description = "Author not found", content = @Content)})
  public @ResponseBody ResponseEntity<Author> detailByUsername(
      @Parameter(name = "username", description = "Author username", required = true)
      @PathVariable("username") String username)
  {
    System.out.println("Leggo " + username);
    Optional<Author> author=authorRepository.findByUsername(username);
    if (author.isPresent()) {
      System.out.println("Cercando "+username+" ho trovato "+author.get().getId());
      return new ResponseEntity<>(author.get(), HttpStatus.OK);
    } else {
      throw new RuntimeException();
    }
  }

  @GetMapping
  @Operation(summary = "Get all authors")
  public @ResponseBody ResponseEntity<List<Author>> getAll() {
    return new ResponseEntity<>(authorRepository.findAll(), HttpStatus.OK);
  }

  @PostMapping
  @Operation(summary = "Save new author")
  public @ResponseBody ResponseEntity<Author> save(@RequestBody Author author) {
    return new ResponseEntity<>(authorRepository.save(author), HttpStatus.OK);
  }

}
