package com.hamrodictionary.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Document(collection = "dictionary")
public class DictionaryModel {

    @Id
    private String id;

    @NotBlank
    private String englishWord;

    @NotBlank
    private String nepaliWord;

    private Date createdDate;
    private Date lastUpdated;

    public DictionaryModel(){};

    public DictionaryModel(@NotBlank String englishWord, @NotBlank String nepaliWord, Date createdDate, Date lastUpdated) {
        this.englishWord = englishWord;
        this.nepaliWord = nepaliWord;
        this.createdDate = createdDate;
        this.lastUpdated = lastUpdated;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public String getNepaliWord() {
        return nepaliWord;
    }

    public void setNepaliWord(String nepaliWord) {
        this.nepaliWord = nepaliWord;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
