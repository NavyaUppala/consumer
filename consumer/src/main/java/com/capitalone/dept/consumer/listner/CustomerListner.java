package com.capitalone.dept.consumer.listner;

import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.utils.Bytes;
import org.springframework.core.io.ClassPathResource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.capitalone.dept.consumer.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;

@Component
public class CustomerListner {
	
    @KafkaListener(topics = "${kafka.topic.avro.customer}", groupId = "cust", containerFactory = "custKafkaListenerContainerFactory")
    public void listenToCustomerTopic(Bytes data, final Acknowledgment acknowledgment) throws JsonProcessingException {
    	System.out.println(data);
     //   System.out.println("Received Messasge: " + new ObjectMapper().writeValueAsString(deserialize(null, data)));
      
    }
    
    public Customer deserialize(String topic, byte[] data) {
		Customer customer = null ;
		try {
			Schema schema = new Schema.Parser()
					.parse(new ClassPathResource("avro/customer-v1.avsc").getInputStream());
			
			AvroSchema avroSchema = new AvroSchema(schema);
			AvroMapper avroMapper = new AvroMapper();
		 customer =	avroMapper.reader(avroSchema).readValue(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return customer;
	}

}
