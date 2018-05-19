package cs.tcd.ie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import tcdIO.Terminal;

public class Router extends Node {

    Terminal terminal;
    int routePort;
    DatagramPacket message;

    InetSocketAddress controllerAddr;
    int connectionCounter;
    int[] connections;
    ControllerPacket controllerPacket;

    /*
     * Hashmap to reference node names (for printing use)
     */
    HashMap<Integer, String> nodeNames;

    /*
     * routingMap == Hashmap to store routing table within
     * - <Integer> = destination address
     * - <RoutingElementKey> = object that holds hopCount (cost) and nextHop for each respective destination address;
     */
    HashMap<Integer, RouteKey> routingMap;

    /*
     *
     */
    Router(Terminal terminal, int routePort) {
        try {
            this.terminal= terminal;
            this.routePort = routePort;
            this.message = null;
            this.routingMap = new HashMap<Integer, RouteKey>();
            this.nodeNames = new HashMap<Integer, String>();

            this.controllerAddr = new InetSocketAddress(Node.DEFAULT_DST_NODE, Node.CONTROLLER_PORT);
            this.connectionCounter = 0;

            //Creates router socket at defined address
            socket= new DatagramSocket(routePort);
            listener.go();
        }
        catch(java.lang.Exception e) {e.printStackTrace();}
    }


    public synchronized void start() throws Exception {
        terminal.println("Initialize routing map at router (" + this.routePort + ")...");
        this.initialiseMap();
        this.addNodeNames();
        this.printRoutingMap();

        //Inform controller of direct connections
        terminal.println("\nInform controller of connections...");
        informController();

        terminal.println("\nWaiting for router contact(" + this.routePort + ")...");
        this.wait();
    }

    public void addNodeNames() {

        //Handle network end users names and addresses
        this.nodeNames.put(NETWORK_18_PORT, "(NET 18)");
        this.nodeNames.put(NETWORK_15_PORT, "(NET 15)");
        this.nodeNames.put(NETWORK_28_PORT, "(NET 28)");
        this.nodeNames.put(NETWORK_7_PORT, "(NET 7)");
        this.nodeNames.put(NETWORK_5_PORT, "(NET 5)");
        this.nodeNames.put(NETWORK_1_PORT, "(NET 1)");
        this.nodeNames.put(NETWORK_16_PORT, "(NET 16)");
        this.nodeNames.put(NETWORK_2_PORT, "(NET 2)");
        this.nodeNames.put(NETWORK_10_PORT, "(NET 10)");

        //Handle network router names and addresses
        this.nodeNames.put(ROUTER_A, "(ROUTER A)");
        this.nodeNames.put(ROUTER_B, "(ROUTER B)");
        this.nodeNames.put(ROUTER_C, "(ROUTER C)");
        this.nodeNames.put(ROUTER_D, "(ROUTER D)");
        this.nodeNames.put(ROUTER_E, "(ROUTER E)");
        this.nodeNames.put(ROUTER_F, "(ROUTER F)");

        this.nodeNames.put(0, "(NULL)");
    }


    public synchronized void informController() throws IOException {

        initialiseConnections();
        this.controllerPacket = new ControllerPacket(this.routePort, this.connections);
        DatagramPacket packet = controllerPacket.toDatagramPacket();
        packet.setSocketAddress(controllerAddr);
        socket.send(packet);
        terminal.println("Controller informed of connections...");
    }


    /*
     * Initialises the routing map's of each respective router
     */
    public void initialiseMap() {

        //Initialise the distanceMap table for the router
        switch(this.routePort) {
            //For router A do this
            case ROUTER_A:
                this.routingMap.put(NETWORK_18_PORT, new RouteKey(0,0));
                this.routingMap.put(ROUTER_B, new RouteKey(0,0));
                this.routingMap.put(ROUTER_C, new RouteKey(0,0));
                break;
            //For router B do this
            case ROUTER_B:
                this.routingMap.put(NETWORK_15_PORT, new RouteKey(0,0));
                this.routingMap.put(NETWORK_28_PORT, new RouteKey(0,0));
                this.routingMap.put(ROUTER_A, new RouteKey(0,0));
                this.routingMap.put(ROUTER_D, new RouteKey(0,0));
                break;
            //For router 3 do this
            case ROUTER_C:
                this.routingMap.put(NETWORK_10_PORT, new RouteKey(0,0));
                this.routingMap.put(ROUTER_A, new RouteKey(0,0));
                this.routingMap.put(ROUTER_D, new RouteKey(0,0));
                this.routingMap.put(ROUTER_E, new RouteKey(0,0));
                break;
            case ROUTER_D:
                this.routingMap.put(NETWORK_7_PORT, new RouteKey(0,0));
                this.routingMap.put(ROUTER_C, new RouteKey(0,0));
                this.routingMap.put(ROUTER_B, new RouteKey(0,0));
                break;
            case ROUTER_E:
                this.routingMap.put(NETWORK_2_PORT, new RouteKey(0,0));
                this.routingMap.put(NETWORK_16_PORT, new RouteKey(0,0));
                this.routingMap.put(ROUTER_C, new RouteKey(0,0));
                this.routingMap.put(ROUTER_F, new RouteKey(0,0));
                break;
            case ROUTER_F:
                this.routingMap.put(NETWORK_1_PORT, new RouteKey(0,0));
                this.routingMap.put(NETWORK_5_PORT, new RouteKey(0,0));
                this.routingMap.put(ROUTER_E, new RouteKey(0,0));
                break;
        }
    }

    /*
     * Hard codes local connections for each router node
     */
    public void initialiseConnections() {

        //Initialise the connections for the router
        switch (this.routePort) {
            // For router A do this
            case ROUTER_A:
                this.connectionCounter = 3;
                this.connections = new int[connectionCounter];
                this.connections[0] = NETWORK_18_PORT;
                this.connections[1] = ROUTER_B;
                this.connections[2] = ROUTER_C;
                break;
            // For router B do this
            case ROUTER_B:
                this.connectionCounter = 4;
                this.connections = new int[connectionCounter];
                this.connections[0] = NETWORK_15_PORT;
                this.connections[1] = NETWORK_28_PORT;
                this.connections[2] = ROUTER_A;
                this.connections[3] = ROUTER_D;
                break;
            // For router C do this
            case ROUTER_C:
                this.connectionCounter = 4;
                this.connections = new int[connectionCounter];
                this.connections[0] = NETWORK_10_PORT;
                this.connections[1] = ROUTER_A;
                this.connections[2] = ROUTER_D;
                this.connections[3] = ROUTER_E;
                break;
            // For router D do this
            case ROUTER_D:
                this.connectionCounter = 3;
                this.connections = new int[connectionCounter];
                this.connections[0] = NETWORK_7_PORT;
                this.connections[1] = ROUTER_B;
                this.connections[2] = ROUTER_C;
                break;
            // For router E do this
            case ROUTER_E:
                this.connectionCounter = 4;
                this.connections = new int[connectionCounter];
                this.connections[0] = NETWORK_2_PORT;
                this.connections[1] = NETWORK_16_PORT;
                this.connections[2] = ROUTER_C;
                this.connections[3] = ROUTER_F;
                break;
            // For router F do this
            case ROUTER_F:
                this.connectionCounter = 3;
                this.connections = new int[connectionCounter];
                this.connections[0] = NETWORK_1_PORT;
                this.connections[1] = NETWORK_5_PORT;
                this.connections[2] = ROUTER_E;
                break;
        }
    }


    public void printRoutingMap() {
        RouteKey routingKey;
        int destination;
        int nextHop;
        int hopCount;

        terminal.println("\n***Routing Map for Router" + this.nodeNames.get(this.routePort) + "***\n");
        terminal.println("Destination   --->   NextHop    --->   NUmber of Hops" );

        for(Entry<Integer, RouteKey> entry : this.routingMap.entrySet()) {

            destination = entry.getKey();
            routingKey = entry.getValue();
            nextHop = routingKey.nextDest;
            hopCount = routingKey.hopCounter;

            terminal.println("" + this.nodeNames.get(destination) + "       |       " + this.nodeNames.get(nextHop) + "              |         " + hopCount);
        }
    }

    public void onReceipt(DatagramPacket packet) {
        try {

            terminal.println("\nPacket recieved at router (" + this.routePort + ")...");

            //If packet is from controller, process flow update
            if(packet.getPort() == CONTROLLER_PORT) {
                terminal.println("\nPacket came from controller...");
                processControllerUpdate(packet);
                terminal.println("\nWaiting for contact at router(" + this.routePort + ")...");
            }

            //If packet is not from controller, continue with flow
            else {
                this.message = packet;
                continueTransmission(packet);
            }
        }
        catch(Exception e) {e.printStackTrace();}
    }

    /*
     * continueTransmission()
     */
    public void continueTransmission(DatagramPacket packet) throws IOException, InterruptedException {

        StringContent content = new StringContent(packet);

        //If router has routing knowledge of how to get to destination, send packet to next router
        if(this.routingMap.get(content.getDestination()).nextDest != 0){
            terminal.println("Router knows how to get to destination...");
            RouteKey key = routingMap.get(content.getDestination());
            int nextHop = key.nextDest;

            //Set dst port of packet to that of the next router
            InetSocketAddress nextAddr = new InetSocketAddress(Node.DEFAULT_DST_NODE, nextHop);
            packet.setSocketAddress(nextAddr);
            socket.send(packet);

            //If next hop is an end user do this
            if(nextHop == NETWORK_18_PORT || nextHop == NETWORK_15_PORT || nextHop == NETWORK_28_PORT || nextHop == NETWORK_7_PORT ||
                    nextHop == NETWORK_5_PORT || nextHop == NETWORK_1_PORT || nextHop == NETWORK_16_PORT || nextHop == NETWORK_2_PORT ||nextHop == NETWORK_10_PORT)
                terminal.println("\nPacket sent to end user(" + nextHop + ")...");

            else
                terminal.println("\nPacket sent to next router(" + nextHop + ")...");
            terminal.println("\nWaiting for contact at router(" + this.routePort + ")...");
        }
    }

    /*
     * Processes an update packet received from controller (updates tables etc)
     *
     * @param packet - Update packet from controller
     * @returns void
     */
    public void processControllerUpdate(DatagramPacket packet) throws InterruptedException, IOException{

        NodePacket content = new NodePacket(packet);
        terminal.println("Processing controller update...");

        //Destination is the end goal of the packet
        int dst = content.getDestinationAddress();

        int nextHop = content.getNextHopAddress();


        int hopCount = content.gethopCounter();
        RouteKey key = new RouteKey(hopCount,nextHop);

        this.routingMap.put(dst, key);
        terminal.println("Controller update processed successfully...");
        terminal.println("Updating maps...\n");
        printRoutingMap();
    }

    /*
     * Creates controller at defined port
     */
    public static void main(String[] args) {
        try {
            //Read port number from  console
            System.out.print ( "Enter the port for router set on: " );
            BufferedReader input = new BufferedReader ( new InputStreamReader ( System.in ));
            String inputString = input.readLine();
            int routePortNumber = Integer.parseInt ( inputString );

            //Create router at defined port number
            Terminal terminal = new Terminal("Router (" + routePortNumber + ")");
            (new Router(terminal, routePortNumber)).start();
            terminal.println("Program completed");
        } catch(java.lang.Exception e) {e.printStackTrace();}
    }
}
