package com.example.madassignment

import java.io.Serializable

class User(

     var FirstName: String = "",
     var LastName: String = "",
     var Gender: String = "",
     var PhoneNumber: String = "",
     var Email: String = "",
     var DateofBirth: String = "",
     var Address: String = "",
     var Town: String = "",
     var PostalCode: String = "",
     var State: String = "",

     ) : Serializable {

}
