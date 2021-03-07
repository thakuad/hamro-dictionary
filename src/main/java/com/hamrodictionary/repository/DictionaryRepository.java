package com.hamrodictionary.repository;

import com.hamrodictionary.model.DictionaryModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DictionaryRepository extends MongoRepository<DictionaryModel, String> {
    Boolean existsByEnglishWord(String englishWord);
    Boolean existsByNepaliWord(String nepaliWord);
    Optional<?> findByEnglishWord(String word);

}
