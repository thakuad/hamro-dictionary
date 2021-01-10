package com.hamrodictionary.service;

import com.hamrodictionary.model.DictionaryModel;
import com.hamrodictionary.repository.DictionaryRepository;
import com.hamrodictionary.security.payload.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class DictionaryService {

    private final DictionaryRepository dictionaryRepository;

    public DictionaryService(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    public ResponseEntity<?> save(DictionaryModel dictionaryModel){
        var model = new DictionaryModel();
        var isWordExist = isWordExist(dictionaryModel.getEnglishWord(), dictionaryModel.getNepaliWord());
        if (!isWordExist){
            dictionaryModel.setCreatedDate(new Date());
            dictionaryModel.setLastUpdated(new Date());
            model = dictionaryRepository.save(dictionaryModel);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new MessageResponse("The Word exist in db"));
        }

        if (!StringUtils.isEmpty(dictionaryModel.getId()) ){
            model  = update(dictionaryModel);
        }

        return ResponseEntity.ok(model);
    }

    private Boolean isWordExist(String englishWord, String nepaliWord){
        return dictionaryRepository.existsByEnglishWord(englishWord) || dictionaryRepository.existsByNepaliWord(nepaliWord);
    }

    private DictionaryModel update(DictionaryModel dictionaryModel){
        dictionaryModel.setLastUpdated(new Date());
        return dictionaryRepository.save(dictionaryModel);
    }




    public ResponseEntity<?> delete(DictionaryModel dictionaryModel){
         dictionaryRepository.delete(dictionaryModel);
         return ResponseEntity.ok(new MessageResponse("The word is deleted successfully"));
    }
}
