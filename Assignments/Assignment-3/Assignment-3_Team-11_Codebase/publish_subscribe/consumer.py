from kafka import KafkaConsumer
import json

kafka_host = 'localhost:9092'
booking_topic = 'booking_events'

consumer = KafkaConsumer(booking_topic, bootstrap_servers=[kafka_host],
                         value_deserializer=lambda x: json.loads(x.decode('utf-8')))

def send_confirmation():
    for message in consumer:
        booking_details = message.value
        visitor_id = booking_details['visitor_id']
        event_id = booking_details['event_id']
        num_tickets = booking_details['num_tickets']
        print(f"Sending confirmation to visitor {visitor_id} for event {event_id} ({num_tickets} tickets)")

if __name__ == "__main__":
    send_confirmation()

