package com.VishalGhanghav.productservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
//We got test containers dependancy to work with dao objects
@Testcontainers
class ProductServiceApplicationTests {
	//As it's deprecated we need to pass docker image name for software i am using
	@Container//For junit5 to understand that it is mongodb container
	static MongoDBContainer mongoDBContainer=new MongoDBContainer("mongo:4.4.2");
	
	//Now we want to set the mongodb uri dynamically.1st we had hardcoded in application.properties
	//To do this we are using dynamic property registry and telling it to use mongo 4.4.2 for tests
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
		//spring.data.mongodb.uri is standard path we had set before in application.properties
	}
	
	@Test
	void contextLoads() {
	}

}
