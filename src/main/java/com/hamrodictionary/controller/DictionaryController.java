package com.hamrodictionary.controller;

import com.hamrodictionary.model.DictionaryModel;
import com.hamrodictionary.service.DictionaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/hd")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveValue(@Valid @RequestBody DictionaryModel dictionaryModel){
        return dictionaryService.save(dictionaryModel);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody DictionaryModel dictionaryModel){
        return dictionaryService.save(dictionaryModel);
    }
}
