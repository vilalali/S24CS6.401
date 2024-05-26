from kafka import KafkaConsumer
import json

kafka_host = 'localhost:9092'
booking_topic = 'booking_requests'

consumer = KafkaConsumer(booking_topic, bootstrap_servers=[kafka_host],
                         value_deserializer=lambda x: json.loads(x.decode('utf-8')))

def process_booking_requests():
    for message in consumer:
        booking_details = message.value
        event_id = booking_details['event_id']
        num_tickets = booking_details['num_tickets']
        print(f"Processing booking request for event {event_id} ({num_tickets} tickets)")

if __name__ == "__main__":
    process_booking_requests()

