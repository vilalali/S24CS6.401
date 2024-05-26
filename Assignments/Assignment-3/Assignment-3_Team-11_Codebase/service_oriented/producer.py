from flask import Flask, request, jsonify
from kafka import KafkaProducer
import json

app = Flask(__name__)
kafka_host = 'localhost:9092'
booking_topic = 'booking_requests'

producer = KafkaProducer(bootstrap_servers=[kafka_host],
                         value_serializer=lambda x: json.dumps(x).encode('utf-8'))

@app.route('/book_ticket', methods=['POST'])
def book_ticket():
    data = request.get_json()
    visitor_id = data['visitor_id']
    event_id = data['event_id']
    num_tickets = data['num_tickets']

    booking_details = {
        'visitor_id': visitor_id,
        'event_id': event_id,
        'num_tickets': num_tickets
    }
    producer.send(booking_topic, value=booking_details)
    return jsonify({"message": "Booking request sent successfully"}), 200

if __name__ == "__main__":
    app.run(port=5000)

