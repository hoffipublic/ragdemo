package com.hoffi.ai.ragdemo.rest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

  private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(UploadController.class);

  private final VectorStore vectorStore;

  public UploadController(VectorStore vectorStore) {
    this.vectorStore = vectorStore;
  }

  @PostMapping("/upload")
  public UploadResponse upload(@RequestParam("file") MultipartFile file) throws IOException {

    //Read file to a temporary location
    Path destinationFile = Paths.get("/temp").resolve(
        Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();

    try (InputStream inputStream = file.getInputStream()) {
      Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
    }

    //Read and split the document contents
    TikaDocumentReader documentReader = new TikaDocumentReader(destinationFile.toUri().toString());
    List<Document> documents = documentReader.get();
    List<Document> splitDocuments = new TokenTextSplitter().apply(documents);

    vectorStore.add(splitDocuments);  // generate and store embeddings in vector store
    return new UploadResponse(file.getOriginalFilename(), file.getContentType(), file.getSize());
  }

  private static record UploadResponse(String fileName, String fileType, long fileSize) {
  }
}
