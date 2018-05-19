import socket
import sys

# Create a TCP/IP socket
sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
server_address = ('localhost', 50002)
gateway_address = ('localhost', 50001)
buffer = 1024
# Bind the socket to the port
print("Gateway starting up on port ", gateway_address)

sock.bind(gateway_address)

while True:
	print("\nGateway waiting to receive message")
	data, address = sock.recvfrom(buffer)
	message = data.split()
	if int(message[1]) > 50000:
		print("Gateway received ", (len(message[0])), " bytes from ", address)
		print(message[0])
		print(message[1])
		print(message[2])

		data = message[0] + " " + message[2]
		server_address = ('localhost', int(message[1]))
		if data:
			sent = sock.sendto(str(data), server_address)
			print("Gateway sent ", sent, " bytes to ", server_address)
	else:
		print("Gateway received ", (len(message[0])), " bytes from ", address)
		print(message[0])
		print(message[1])
		
		data = message[0] 
		client_address = ('localhost', int(message[1]))
		if data:    
			sent = sock.sendto(str(data), client_address)
			print("Gateway sent ", sent, " bytes to ", client_address)