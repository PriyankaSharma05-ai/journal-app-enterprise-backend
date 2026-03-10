package net.engineeringdigest.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {
	public static void main(String[] args) {

		SpringApplication.run(JournalApplication.class, args);
	}

	@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory dbFactory) { //MongoDatabaseFactory help karti hai database ke saath connection banane me. //Jitna bhi hum kaam kar rahe hai  database me isi ke(MongoDatabaseFactory) instance se kar rahe hai
		return new MongoTransactionManager(dbFactory);
	}

}



//PlatformTransactionManager
//MongodbTransactionManager
//@Configuration = "This class defines Spring Beans"

//@Bean = "Hey Spring, manage this object"
//Without @Bean:
//❌ Spring will NOT know about this object
//
//With @Bean:
//✅ Spring creates
//✅ Stores in ApplicationContext
//✅ Injects wherever needed