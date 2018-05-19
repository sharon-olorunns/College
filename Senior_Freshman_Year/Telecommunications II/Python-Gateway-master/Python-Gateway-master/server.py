import socket
import sys

# Create a TCP/IP socket
sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

# Bind the socket to the port
server_address = ('localhost', 50002)
gateway_address = ('localhost', 50001)
buffer = 1024
print("Server starting up on port ", server_address)

sock.bind(server_address)

while True:
    print("\nServer waiting to receive message")
    data, address = sock.recvfrom(buffer)
    message = data.split()
    print("Server received ", (len(message[0])), " bytes from ", address)
    print(message[0])
    print(message[1])

    if data:
    	if int(message[0]) == 1:
        	ack_bit = str(0) + " " + message[1]
        elif int(message[0]) == 0:
        	ack_bit = str(1) + " " + message[1]

        sent = sock.sendto(str(ack_bit), gateway_address)
        print("Server sent ", sent, " bytes back to ", gateway_address)