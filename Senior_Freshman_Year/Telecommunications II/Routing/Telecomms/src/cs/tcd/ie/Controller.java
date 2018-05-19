package cs.tcd.ie;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import tcdIO.Terminal;

public class Controller extends Node {

    Terminal terminal;
    int controlPort;
    boolean InfoReceived;

    /*
     * Hashmap to reference node names
     */
    HashMap<Integer, String> nodeNames;

    /*
     * Hashmap to store routing table within
     * - <Destination> = routeID#
     * - HashMap<Node, NextHop> = maps flow for each router for routeID
     */
    HashMap<Integer, HashMap<Integer,Integer>> routingMap;

    /*
     * Hashmap to store network connections for each node
     * - <Integer> = node address
     * - ArrayList<Integer> = nodes connections
     */
    HashMap<Integer, ArrayList<Integer>> networkConnections;
    ArrayList<Integer> shortestPath;
    ArrayList<Integer> endUsers;
    ArrayList<Integer> routers;
    int nodeCount;

    /*
     *
     */
    Controller(Terminal terminal, int controlPort) {
        try {
            //Initialise variables
            this.terminal= terminal;
            this.controlPort = controlPort;
            this.InfoReceived = false;
            this.nodeCount = 0;

            //Initialise maps & array lists
            this.routingMap = new HashMap<Integer, HashMap<Integer,Integer>>();
            this.networkConnections = new HashMap<Integer, ArrayList<Integer>>();
            this.nodeNames = new HashMap<Integer, String>();
            this.endUsers = new ArrayList<Integer>();
            this.routers = new ArrayList<Integer>();
            this.shortestPath = new ArrayList<Integer>();
            fillNodeNames();

            //Creates router socket at defined address
            socket= new DatagramSocket(controlPort);
            listener.go();
        }
        catch(java.lang.Exception e) {e.printStackTrace();}
    }


    public synchronized void start() throws Exception {
        terminal.println("A Controller has been initialised at (" + this.controlPort + ")...");
        terminal.println("Waiting for controller...");
        this.wait();
    }

    /*
     * Method that does Distance Vector Routing calculation.
     */
    public void distanceVectoring() throws IOException {
        //Calculate route from each end user to end user
        ArrayList<Integer> otherEndUsers;
        ArrayList<Integer> path;
        terminal.println("Finding shortest path between end users in network...\n");
        InetSocketAddress nodeAddress;
        //For each end user, find routes to each other end user
        for(int source : this.endUsers) {
            otherEndUsers = new ArrayList<Integer>();
            path = new ArrayList<Integer>();
            //Get all other end users in the network (routes need to be established for each)
            for(int otherUser : this.endUsers) {
                if(otherUser != source)
                    otherEndUsers.add(otherUser);
            }
            //For each possible other end user node, find quickest route
            for(int dst : otherEndUsers) {
                terminal.println("Shortest path from " + source + nodeNames.get(source) + " to "+ dst + nodeNames.get(dst) + " is as follows:");
                path = getQuickestRouteBetween(source,dst);
                //When route found, inform affected nodes of their respective next hop
                int i = 0;
                for(int node : path) {
                    //If node is not an end user, do this (don't inform end users as only one connection - that to router)
                    if(!endUsers.contains(node)) {
                        int nodesNextHop = path.get(i+1);
                        int hopCounter = path.size()-(i+1);
                        i++;
                        terminal.println(node + nodeNames.get(node));
                        NodePacket informPacket = new NodePacket(node,dst,nodesNextHop,hopCounter);
                        DatagramPacket packet = informPacket.toDatagramPacket();
                        nodeAddress = new InetSocketAddress(Node.DEFAULT_DST_NODE, node);
                        packet.setSocketAddress(nodeAddress);
                        socket.send(packet);
                    }
                    else
                        i++;
                }

                terminal.println("\n");
            }

        }
    }

    /*
     * Gets quickest route between node x and node y using Breadth-First-Search (BFS)
     * Inspiration from (https://codereview.stackexchange.com/questions/84717/shortest-path-using-breadth-first-search)
     *
     * @param source	source node address
     * @param dst	destination node address
     *
     * @returns route	HashMap representing quickest route for each node in route HashMap<Node><NextHop>
     */
    public ArrayList<Integer> getQuickestRouteBetween(int source, int dst){
        ArrayList<Integer> path = new ArrayList<Integer>();
        this.shortestPath.clear();

        // If the source is the same as destination, I'm done.
        if (source == dst) {
            path.add(source);
            return path;
        }

        // A queue to store the un-visited nodes.
        ArrayDeque<Integer> queue = new ArrayDeque<Integer>();

        // A queue to store the visited nodes.
        ArrayDeque<Integer> visited = new ArrayDeque<Integer>();

        queue.offer(source);
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            visited.offer(vertex);

            //Get connections for current vertex
            ArrayList<Integer> neighboursList = this.networkConnections.get(vertex);
            int index = 0;
            int neighboursSize = neighboursList.size();
            while (index != neighboursSize) {
                int neighbour = neighboursList.get(index);

                path.add(neighbour);
                path.add(vertex);

                if (neighbour == dst) {
                    return processPath(source, dst, path);
                } else {
                    if (!visited.contains(neighbour)) {
                        queue.offer(neighbour);
                    }
                }
                index++;
            }
        }
        return null;
    }


    /**
     * Adds the nodes involved in the shortest path.
     *
     * @param src         The source node.
     * @param destination The destination node.
     * @param path        The path that has nodes and their neighbours.
     * @return The shortest path.
     */
    private ArrayList<Integer> processPath(int src, int destination,
                                           ArrayList<Integer> path) {


        // Finds out where the destination node directly comes from.
        int index = path.indexOf(destination);
        Integer source = path.get(index + 1);

        // Adds the destination node to the shortestPath.
        this.shortestPath.add(0, destination);

        if (source.equals(src)) {
            // The original source node is found.
            shortestPath.add(0, source);
            return this.shortestPath;
        } else {
            // We find where the source node of the destination node
            // comes from.
            // We then set the source node to be the destination node.
            return processPath(source, source, path);
        }
    }

    /*
     * Populates endUsers and routers ArrayLists, also populates nodeNames HashMap with respective names for each node
     */
    public void fillNodeNames() {
        //Handle network end users names and addresses
        this.nodeNames.put(NETWORK_18_PORT, "(NET 18)");
        this.endUsers.add(NETWORK_18_PORT);
        this.nodeNames.put(NETWORK_15_PORT, "(NET 15)");
        this.endUsers.add(NETWORK_15_PORT);
        this.nodeNames.put(NETWORK_28_PORT, "(NET 28)");
        this.endUsers.add(NETWORK_28_PORT);
        this.nodeNames.put(NETWORK_7_PORT, "(NET 7)");
        this.endUsers.add(NETWORK_7_PORT);
        this.nodeNames.put(NETWORK_5_PORT, "(NET 5)");
        this.endUsers.add(NETWORK_5_PORT);
        this.nodeNames.put(NETWORK_1_PORT, "(NET 1)");
        this.endUsers.add(NETWORK_1_PORT);
        this.nodeNames.put(NETWORK_16_PORT, "(NET 16)");
        this.endUsers.add(NETWORK_16_PORT);
        this.nodeNames.put(NETWORK_2_PORT, "(NET 2)");
        this.endUsers.add(NETWORK_2_PORT);
        this.nodeNames.put(NETWORK_10_PORT, "(NET 10)");
        this.endUsers.add(NETWORK_10_PORT);

        //Handle network router names and addresses
        this.nodeNames.put(ROUTER_A, "(ROUTER A)");
        this.routers.add(ROUTER_A);
        this.nodeNames.put(ROUTER_B, "(ROUTER B)");
        this.routers.add(ROUTER_B);
        this.nodeNames.put(ROUTER_C, "(ROUTER C)");
        this.routers.add(ROUTER_C);
        this.nodeNames.put(ROUTER_D, "(ROUTER D)");
        this.routers.add(ROUTER_D);
        this.nodeNames.put(ROUTER_E, "(ROUTER E)");
        this.routers.add(ROUTER_E);
        this.nodeNames.put(ROUTER_F, "(ROUTER F)");
        this.routers.add(ROUTER_F);
    }


    public void printNetworkConnections() {
        int nodeAddress;
        ArrayList<Integer> connections;

        terminal.println("Node Address    ----->   Connections" );

        for (Entry<Integer, ArrayList<Integer>> entry : this.networkConnections.entrySet()) {

            nodeAddress = entry.getKey();
            connections = entry.getValue();
            terminal.println("" + nodeAddress + " " + nodeNames.get(nodeAddress) + "    |       ");

            for(Integer i : connections)
                terminal.println("                                |       " + i + " " + nodeNames.get(i));

        }

    }

    /*
   * Handles a connection inform  packet from a node in the network
   *
   */
    public void handleConnectionInform(DatagramPacket packet) {
        terminal.println("Handling connection packet...");
        ControllerPacket info = new ControllerPacket(packet);
        int nodeAddress = info.getSource();
        int[] nodeConnections = info.getConnectionAddr();
        ArrayList<Integer> connections = new ArrayList<Integer>();

        //Iterate through connections storing them to arraylist
        for(int i=0;i<nodeConnections.length;i++) {
            connections.add(nodeConnections[i]);
        }

        //Store connections for given node
        this.networkConnections.put(nodeAddress, connections);

        this.nodeCount++;
        terminal.println("Node count = " + this.nodeCount);
        terminal.println("Network connections updated...\n");
    }



    public void onReceipt(DatagramPacket packet) {
        try {

            terminal.println("\nPacket recieved at controller (" + this.controlPort + ")...\n");

            //Check flag to see what type of packet it is
            byte[] buffer = null;
            buffer = packet.getData();
            byte[] flagBuf = new byte[PacketContent.FLAG_LENGTH];
            System.arraycopy(buffer, 0, flagBuf, 0, PacketContent.FLAG_LENGTH);
            int flag = ByteBuffer.wrap(flagBuf).getInt();

            //Process flag
            switch(flag) {

                case 1:
                    terminal.println("Packet is from router seeking flow update...");
                    //handleUpdateRequest();
                    break;
                //If flag = 2, packet is from a node informing of connections
                case 2:
                    terminal.println("Packet is from node informing of connections...");
                    handleConnectionInform(packet);
                    printNetworkConnections();
                    if(this.nodeCount == NETWORK_NODE_COUNT) {
                        distanceVectoring();
                    }
                    break;
                default:
                    terminal.println("Unknown flag...");
                    break;
            }


        }
        catch(Exception e) {e.printStackTrace();}
    }


    public static void main(String[] args) {
        try {
            //Create router at defined port number
            Terminal terminal= new Terminal("Controller");
            (new Controller(terminal, CONTROLLER_PORT)).start();
            terminal.println("Program completed");
        } catch(java.lang.Exception e) {e.printStackTrace();}
    }
}

