import socket
import sys
from random import randint
import time

# Create a UDP socket
sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
randomGenerator = randint(5000, 50000)
buffer = 1024

client_address = ('localhost', randomGenerator)
gateway_address = ('localhost', 50001)

server_port = 50002
sock.bind(client_address)
message = 0

while True:
	# Send data
	message = str(message) + " " + str(server_port) + " " + str(randomGenerator)
	print ("Client is sending" , message)
	sock.sendto(str(message), gateway_address)

	# Receive response
	print("Client is waiting to receive")
	data, server = sock.recvfrom(buffer)
	print("Client received ", data)
	if int(data) == 0:
		message = 0
	elif int(data) == 1:
		message = 1
	else:
		print("Client received faulty bit", data)
		sock.close()

	time.sleep(2)