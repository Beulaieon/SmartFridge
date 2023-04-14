package com.mobdeve.s11.salangsang.brian.smartfridge

class Food {

    var id: Long = 0
        private set
    var foodname: String
        private set
    var description: String
        private set
    var expiry: String
        private set
    var imageUri: String
        private set


    constructor(foodname: String, description: String, expiry: String, imageUri: String){
        this.foodname = foodname
        this.description = description
        this.expiry = expiry
        this.imageUri = imageUri
    }

    constructor(foodname: String, description: String, expiry: String, imageUri: String, id: Long) {
        this.foodname = foodname
        this.description = description
        this.expiry = expiry
        this.imageUri = imageUri
        this.id = id
    }

    fun getFoodId(): Long {
        return id
    }

    fun getFoodName(): String? {
        return foodname
    }

    fun getFoodDescription(): String? {
        return description
    }

    fun getFoodExpiry(): String? {
        return expiry
    }

    fun getFoodImageUri(): String? {
        return imageUri
    }

    override fun toString(): String {
        return "Food{" +
                "id=" + id +
                ", foodname='" + foodname + '\'' +
                ", description='" + description + '\'' +
                ", expiry='" + expiry + '\'' +
                ", imageUri='" + imageUri + '\'' +
                '}'
    }

}