package com.monefy.builders;

import com.monefy.requests.Category;
import com.monefy.requests.CreatePetRequest;
import com.monefy.requests.Tags;

public class CreatePetRequestBuilder {

    private CreatePetRequest createPetRequest = new CreatePetRequest();

    public CreatePetRequestBuilder withTags(Tags[] tags){
        createPetRequest.setTags(tags);
        return this;
    }

    public CreatePetRequestBuilder withCategory(Category category){
        createPetRequest.setCategory(category);
        return this;
    }

    public CreatePetRequestBuilder withId(int id){
        createPetRequest.setId(id);
        return this;
    }

    public CreatePetRequestBuilder withStatus(String status){
        createPetRequest.setStatus(status);
        return this;
    }

    public CreatePetRequestBuilder withName(String name){
        createPetRequest.setName(name);
        return this;
    }

    public CreatePetRequestBuilder withPhotoUrls(String[] photoUrls){
        createPetRequest.setPhotoUrls(photoUrls);
        return this;
    }

    public CreatePetRequest build(){
        return createPetRequest;
    }

}
