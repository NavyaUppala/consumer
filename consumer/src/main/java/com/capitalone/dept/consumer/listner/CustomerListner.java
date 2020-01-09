package com.capitalone.dept.consumer.listner;

import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.utils.Bytes;
import org.springframework.core.io.ClassPathResource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.capitalone.dept.consumer.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;

@Component
public class CustomerListner {
	
	ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	
    @KafkaListener(topics = "${kafka.topic.avro.customer}", groupId = "group1", containerFactory = "custKafkaListenerContainerFactory")
    public void listenToCustomerTopic(
    		@Header(name = KafkaHeaders.RECEIVED_PARTITION_ID) int partitionId,
    		@Header(name = KafkaHeaders.OFFSET) int offset,
    		@Payload Customer customer, Acknowledgment acknowledgment) throws JsonProcessingException {
    	System.out.println("Partition Id " + partitionId);
    	System.out.println("offset " + offset);
    	System.out.println(mapper.writeValueAsString(customer));

    //	acknowledgment.acknowledge();
      
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
