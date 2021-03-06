package cs.tcd.ie;

import java.net.DatagramSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

import tcdIO.*;

/**
 *
 * Client class
 *
 * An instance accepts user input
 *
 */
public class EndUser extends Node {

    byte[] flag;
    boolean isResponse;
    int responseAddr;
    Terminal terminal;
    InetSocketAddress routerAddress;
    InetSocketAddress controllerAddress;
    int sourcePortNumber;
    int connectedRouterPort;
    int connectionCount;
    int[] connections;
    ControllerPacket controllerPacket;

    /**
     * Constructor
     *
     * Attempts to create socket at given port and create an InetSocketAddress for the destinations
     */
    EndUser(Terminal terminal, String host, int sourcePort, int routerPort) {
        try {
            this.terminal= terminal;
            this.sourcePortNumber = sourcePort;
            this.connectedRouterPort = routerPort;
            this.routerAddress = new InetSocketAddress(host, routerPort);
            this.controllerAddress = new InetSocketAddress(host, Node.CONTROLLER_PORT);

            this.isResponse = false;
            this.responseAddr = 0;

            this.connectionCount = 1;
            this.connections = new int[connectionCount];
            this.connections[0] = this.connectedRouterPort;
            this.controllerPacket = null
            ;
            //Creates socket at sourcePort (EndUser)
            socket= new DatagramSocket(sourcePortNumber);

            //Set flag =0, coming from EndUser (entering network)
            this.flag = ByteBuffer.allocate(4).putInt(0).array();
            listener.go();
        }
        catch(java.lang.Exception e) {e.printStackTrace();}
    }


    /**
     * Assume that incoming packets contain a String and print the string.
     * @throws Exception
     */
    public synchronized void onReceipt(DatagramPacket packet) throws Exception {
        StringContent content= new StringContent(packet);
        terminal.println("\nPacket received from router...");

        terminal.println("Packet contents = " + content.string);

        String choice = terminal.readString("\nWould you like to send a response? [y/n] ");

        if(choice.equalsIgnoreCase("y")) {
            this.isResponse = true;
            this.responseAddr = content.getSource();
            sendMessage();
        }

        else {
            terminal.println("Goodbye.");
            this.notify();
        }


    }


    /**
     * Sender Method - Sends packet from EndUser to connectedRouter
     *
     */
    /**
     * @throws Exception
     */
    public synchronized void start() throws Exception, SocketTimeoutException {

        //Inform controller of direct connections
        terminal.println("Informing controller of connections...");
        informController();

        String choice = terminal.readString("Enter 'send' to send  a message: ");

        if(choice.equalsIgnoreCase("send"))
            sendMessage();

        else
            terminal.println("Waiting for End User (" + this.sourcePortNumber + ")...");
        this.wait();
    }

    public synchronized void informController() throws IOException {

        this.controllerPacket = new ControllerPacket(this.sourcePortNumber, this.connections);
        DatagramPacket packet = controllerPacket.toDatagramPacket();
        packet.setSocketAddress(controllerAddress);
        socket.send(packet);

    }

    public synchronized void sendMessage() throws IOException {

        DatagramPacket packet = null;

        byte[] dstAddress = null;
        byte[] sourceAddress = null;
        byte[] hopCounter = null;
        byte[] payload = null;
        byte[] buffer = null;
        int dst;

        dstAddress = new byte[PacketContent.DST_ADDRESS_LENGTH];
        sourceAddress = new byte[PacketContent.SOURCE_ADDRESS_LENGTH];
        hopCounter = new byte[PacketContent.HOP_COUNT_LENGTH];

        if(!isResponse) {
            String dstStr = terminal.readString("Destination address of end user: ");
            dst = Integer.parseInt ( dstStr );
        }

        else
            dst = this.responseAddr;

        dstAddress = ByteBuffer.allocate(PacketContent.DST_ADDRESS_LENGTH).putInt(dst).array();

        payload = (terminal.readString("String to send: ")).getBytes();
        System.out.print(ByteBuffer.wrap(dstAddress).getInt());
        sourceAddress = ByteBuffer.allocate(PacketContent.SOURCE_ADDRESS_LENGTH).putInt(this.sourcePortNumber).array();
        hopCounter = ByteBuffer.allocate(PacketContent.HOP_COUNT_LENGTH).putInt(0).array();

        // Creates a buffer to contain the information
        buffer = new byte[PacketContent.HEADER_LENGTH + payload.length];

        // Encloses the above information into a buffer containing an array of bytes
        System.arraycopy(dstAddress, 0, buffer, 0, PacketContent.DST_ADDRESS_LENGTH);
        System.arraycopy(sourceAddress, 0, buffer, PacketContent.DST_ADDRESS_LENGTH, PacketContent.SOURCE_ADDRESS_LENGTH);
        System.arraycopy(hopCounter, 0, buffer, (PacketContent.DST_ADDRESS_LENGTH + PacketContent.SOURCE_ADDRESS_LENGTH), PacketContent.HOP_COUNT_LENGTH);
        System.arraycopy(payload, 0, buffer, PacketContent.HEADER_LENGTH, payload.length);


        terminal.println("\nSending packet to router at : " + this.connectedRouterPort + "...");
        packet = new DatagramPacket(buffer, buffer.length, this.routerAddress);
        socket.send(packet);
        terminal.println("Packet sent to router\n");
    }

    /**
     * Test method
     *
     * Sends a packet to a given address
     */
    public static void main(String[] args) {
        try {
            //Establish end user details
            System.out.print ( "Enter the port for end user to be set on: " );
            BufferedReader input = new BufferedReader ( new InputStreamReader ( System.in ));
            String inputString = input.readLine();
            int endUserPortNumber = Integer.parseInt ( inputString );

            //Set connected port for end user
            int routerPortNumber=0;

            //Set connected router address
            switch(endUserPortNumber) {
                case NETWORK_18_PORT:
                    routerPortNumber = ROUTER_A;
                    break;
                case NETWORK_15_PORT:
                    routerPortNumber = ROUTER_B;
                    break;
                case NETWORK_28_PORT:
                    routerPortNumber = ROUTER_B;
                    break;
                case NETWORK_7_PORT:
                    routerPortNumber = ROUTER_D;
                    break;
                case NETWORK_5_PORT:
                    routerPortNumber = ROUTER_F;
                    break;
                case NETWORK_1_PORT:
                    routerPortNumber = ROUTER_F;
                    break;
                case NETWORK_16_PORT:
                    routerPortNumber = ROUTER_E;
                    break;
                case NETWORK_2_PORT:
                    routerPortNumber = ROUTER_E;
                    break;
                case NETWORK_10_PORT:
                    routerPortNumber = ROUTER_C;
                    break;
            }

            Terminal terminal = new Terminal("End User (" + endUserPortNumber + ")");
            (new EndUser(terminal, DEFAULT_DST_NODE, endUserPortNumber, routerPortNumber)).start();
            terminal.println("Program completed");
        } catch(java.lang.Exception e) {e.printStackTrace();}
    }
}
