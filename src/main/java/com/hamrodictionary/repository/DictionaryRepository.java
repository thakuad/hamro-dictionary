package com.hamrodictionary.repository;

import com.hamrodictionary.model.DictionaryModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DictionaryRepository extends MongoRepository<DictionaryModel, String> {
    Boolean existsByEnglishWord(String englishWord);
    Boolean existsByNepaliWord(String nepaliWord);

}
