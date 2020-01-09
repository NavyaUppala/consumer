package com.capitalone.dept.consumer.deserializer;

import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.core.io.ClassPathResource;

import com.capitalone.dept.consumer.model.Customer;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;

public class CustomerAvroDeserializer implements Deserializer<Customer>{

	@Override
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
