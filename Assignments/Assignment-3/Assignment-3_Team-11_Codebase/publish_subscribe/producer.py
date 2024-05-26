from kafka import KafkaProducer
import json
import time
import sys

kafka_host = 'localhost:9092'
booking_topic = 'booking_events'

producer = KafkaProducer(bootstrap_servers=[kafka_host],
                         value_serializer=lambda x: json.dumps(x).encode('utf-8'))

def book_ticket(visitor_id, event_id, num_tickets):
 
    booking_details = {
        'visitor_id': visitor_id,
        'event_id': event_id,
        'num_tickets': num_tickets,
        'timestamp': time.time()
    }
    producer.send(booking_topic, value=booking_details)
    print(f"Booking successful: {booking_details}")

if __name__ == "__main__":

    if len(sys.argv) != 4:
        print("Usage: python3 producer.py <visitor_id> <event_id> <num_tickets>")
        sys.exit(1)


    visitor_id = sys.argv[1]
    event_id = sys.argv[2]
    num_tickets = int(sys.argv[3]) 

    book_ticket(visitor_id, event_id, num_tickets)

