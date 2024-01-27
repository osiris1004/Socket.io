package com.chatapp.chatapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatappApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatappApplication.class, args);
	}

}

//websocket is a communication protocol which full duplex communication channels over a sing tcp connection 
// web socket are real time data exchange on application
//web socket is a bidirectional. mean serve can now push data to the client when it available
//web socket is a persistence connection. that is the connection between the client an the server remain opens once establish an open connection un tile when the client a the serve decide to close it

