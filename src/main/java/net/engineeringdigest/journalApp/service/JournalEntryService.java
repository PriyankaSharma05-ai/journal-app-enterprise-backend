package net.engineeringdigest.journalApp.service;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired    //It's a dependency injection.
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional //performs ACID Properties
    public void saveEntry(JournalEntry journalEntry , String username){//we want to save something
        try {
            User user = userService.findByUserName(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved; //jo entry database me save hui hogi vo mil jaayegi isese.
            saved = journalEntryRepository.save(journalEntry);
            //To me user ko journalEntry me patak dega.
            boolean add = user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }catch (Exception e){
            throw new RuntimeException("An error occured while saving the entry"); //If error occur then it will perform rollback next.
        }
    }
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();

    }
    public Optional<JournalEntry> findById(ObjectId id){   //Optional means -> isme data ho bhi sakta hai aur nahi bhi ho sakta.

        return journalEntryRepository.findById(id);
    }
    public void deleteById(ObjectId id, String username){
        User user = userService.findByUserName(username);
        //jab humne journal_entries me se entries ko delete ki to ho gayi but unka references user entries me tha to vo bhi hum remove karna chahte hai.Isliye yeh syntax likha hai.
        //cuser.getJournalEntries().removeIf(x -> x.getId().equals(id)); //It's a lambda Expression.
        boolean b;
        b = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);
        journalEntryRepository.deleteById(id);

    }
}


// controller ---> service ---> repository










