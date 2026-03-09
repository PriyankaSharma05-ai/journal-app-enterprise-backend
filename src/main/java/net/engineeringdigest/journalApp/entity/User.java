package net.engineeringdigest.journalApp.entity;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

//user me hmaari details honi chahiye.
@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private ObjectId id;

    @Indexed(unique = true) //It's mean aapka username Unique hona chahiye.
                            //But yeh automatically create nahi hoga .Ise auto-create kraane ke liye aapko application.properties me yeh likhna hoga "spring.data.mongodb.auto-index-creation=true".
    @NonNull    //UserName & Password Should not be null.
    private String username;
    @NonNull
    private String password;



    //@DBRef is used to create a reference (link) between two MongoDB documents in different collections, similar to a foreign key, by storing the referenced document’s _id instead of embedding the whole object.
    //👉 Example idea:
    //A JournalEntry can reference a User document using @DBRef so both stay separate but linked.
    @DBRef(lazy = true)
    private List<JournalEntry> journalEntries = new ArrayList<>();  //humne sirf ek dabba banaya hai journal Entries ka.
    private List<String> roles;  //Is used to store different roles (like USER, ADMIN) assigned to a user for authorization purposes. 🔐

}

