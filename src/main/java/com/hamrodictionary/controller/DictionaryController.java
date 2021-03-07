package com.hamrodictionary.controller;

import com.hamrodictionary.model.DictionaryModel;
import com.hamrodictionary.service.DictionaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/hd")
public class DictionaryController {

    String USER_ROLE = "ROLE_USER";
    private final DictionaryService dictionaryService;

    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> saveValue(@Valid @RequestBody DictionaryModel dictionaryModel){
        return dictionaryService.save(dictionaryModel);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> update(@Valid @RequestBody DictionaryModel dictionaryModel){
        return dictionaryService.save(dictionaryModel);
    }

    @GetMapping(value = "/listOfWord")
    public ResponseEntity<?> getAll(){
        return dictionaryService.listOfAllWord();
    }

    @GetMapping(value = "/getByWord/{word}")
    public ResponseEntity<?> findByWord(@PathVariable String word){
        return dictionaryService.findByWord(word);
    }
}
