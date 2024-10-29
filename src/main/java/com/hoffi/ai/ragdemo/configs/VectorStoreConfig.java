package com.hoffi.ai.ragdemo.configs;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VectorStoreConfig {

  @Bean
  VectorStore simleVectorStore(EmbeddingModel embeddingModel) {
    return new SimpleVectorStore(embeddingModel);
  }
}
