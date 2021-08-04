package com.example.weatherapp.models

import com.google.gson.annotations.SerializedName

data class PincodeDetails(

    @SerializedName("Message")
    public var message:String,

    @SerializedName("Status")
    var status:String,

    @SerializedName("PostOffice")
    var postOfficeDetails:ArrayList<PostOffice>
    ){

    data class PostOffice(
            @SerializedName("State")
            var state:String,
            @SerializedName("District")
            var district:String
    )

}