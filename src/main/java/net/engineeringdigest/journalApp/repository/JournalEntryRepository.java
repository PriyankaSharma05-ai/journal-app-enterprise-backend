package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {

    //MongoRepository do chiz leti hai
              // 1.Entity -> jo aapka collection hai ,jise aap map kar rahe ho vo POJO  2. us ID ka type -> String
    //JournalEntry -> jise aap map kar rahe ho POJO ,
    //String -> id ka datatype (String Id)
    //Then I changed String Id --> ObjectId.


}
