import java.io.IOException;
import java.util.Set;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.DB;

import javax.jws.WebService;

@WebService
public class database {
	
	public static void main(String[] args) {
		// Starting with connecting to database (topic pairs are kept)		
		MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		DB database = mongoClient.getDB("topicPairs");
		
		if(database == null)
		{	
			System.out.println("Database not found!");
			// We cannot create a new database here so it must be done in the console.
			return;
		}
		
		// check if the pairs table already exists (it normally does)
		Set<String> names =  database.getCollectionNames();
		
		if(names.isEmpty() || names.contains("topics") == false)
		{	
			database.createCollection("topics", null);
		}
	
		
		//deneme, local subscriber
		Subscriber subscriber = new Subscriber(1);
		Subscriber subscriber2 = new Subscriber(2);
		
		Event.operation.subscribe("action#create", subscriber);
		Event.operation.subscribe("action#create", subscriber2);
		
		Message message = new Message("Create Action");
		Message message2 = new Message("Update Action");

		Event.operation.publish("action#create", message);
		Event.operation.publish("action#update", message2);
		
	}
	

	
}
