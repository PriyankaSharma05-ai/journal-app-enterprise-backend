package net.engineeringdigest.journalApp.entity;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
//@NoArgsConstructor is needed because: 👉 Spring first creates empty object 👉 Then fills data inside it
//Without empty constructor → Spring cannot create object.

@AllArgsConstructor
//Contains all the lambok package classes(Equivalent to @Getters , @Setters , @NoArgaArgument , @AllArgsArgument, @Builder etc.  -->AApko individually generate karne ki need nahi hai.)
public class JournalEntry { //yeh hai POJO Class , humne isme likh diya hai ki kesa dikhega journalEntry.

    //Now we want to take Id as an objectId that's why I changed here
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;  //Automatically date set ho jaaye isliye hum use kar rahe hai "LocalDateTime".

}

