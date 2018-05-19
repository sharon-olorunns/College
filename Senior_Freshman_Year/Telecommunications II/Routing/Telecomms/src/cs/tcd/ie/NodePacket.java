package cs.tcd.ie;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class NodePacket implements PacketContent {

    byte[] flag;
    byte[] nodeAddress;
    byte[] dstAddress;
    byte[] nextHopAddress;
    byte[] hopCounter;


    public NodePacket(DatagramPacket packet) {
        byte[] buffer= null;

        this.flag = new byte[FLAG_LENGTH];
        this.nodeAddress = new byte[NODE_ADDRESS_LENGTH];
        this.dstAddress = new byte[DST_ADDRESS_LENGTH];
        this.nextHopAddress = new byte[NEXT_HOP_LENGTH];
        this.hopCounter = new byte[HOP_COUNT_LENGTH];
        buffer= packet.getData();

        System.arraycopy(buffer, 0, this.flag, 0, FLAG_LENGTH);
        System.arraycopy(buffer, FLAG_LENGTH, this.nodeAddress, 0, NODE_ADDRESS_LENGTH);
        System.arraycopy(buffer, FLAG_LENGTH + NODE_ADDRESS_LENGTH, this.dstAddress, 0, DST_ADDRESS_LENGTH);
        System.arraycopy(buffer, FLAG_LENGTH + NODE_ADDRESS_LENGTH + DST_ADDRESS_LENGTH, this.nextHopAddress, 0, NEXT_HOP_LENGTH);
        System.arraycopy(buffer, FLAG_LENGTH + NODE_ADDRESS_LENGTH + DST_ADDRESS_LENGTH + NEXT_HOP_LENGTH, this.hopCounter, 0, HOP_COUNT_LENGTH);
    }

    public NodePacket(int nodeAddr, int dstAddr, int nextHopAddr, int hopCounter) {

        this.flag = new byte[FLAG_LENGTH];
        this.nodeAddress = new byte[NODE_ADDRESS_LENGTH];
        this.dstAddress = new byte[DST_ADDRESS_LENGTH];
        this.nextHopAddress = new byte[NEXT_HOP_LENGTH];
        this.hopCounter = new byte[HOP_COUNT_LENGTH];

        //NodeInform Packet Protocol = [FLAG] | [NODE_ADDRESS] | [DST_ADDRESS] | [NEXT_HOP_ADDRESS] | [HOP_COUNT]
        //Flag = 3 for NodeInform Packet
        this.flag = ByteBuffer.allocate(PacketContent.FLAG_LENGTH).putInt(3).array();
        this.nodeAddress = ByteBuffer.allocate(PacketContent.NODE_ADDRESS_LENGTH).putInt(nodeAddr).array();
        this.dstAddress = ByteBuffer.allocate(PacketContent.DST_ADDRESS_LENGTH).putInt(dstAddr).array();
        this.nextHopAddress = ByteBuffer.allocate(PacketContent.NEXT_HOP_LENGTH).putInt(nextHopAddr).array();
        this.hopCounter = ByteBuffer.allocate(PacketContent.HOP_COUNT_LENGTH).putInt(hopCounter).array();
    }


    public DatagramPacket toDatagramPacket() {
        DatagramPacket packet= null;
        byte[] buffer= null;
        try {
            //NodeInform Packet Protocol = [FLAG] | [NODE_ADDRESS] | [DST_ADDRESS] | [NEXT_HOP_ADDRESS] | [HOP_COUNT]

            buffer= new byte[NODE_INFORM_PACKET_LENGTH];

            System.arraycopy(this.flag, 0, buffer, 0, FLAG_LENGTH);
            System.arraycopy(this.nodeAddress, 0, buffer, FLAG_LENGTH, NODE_ADDRESS_LENGTH);
            System.arraycopy(this.dstAddress, 0, buffer, NODE_ADDRESS_LENGTH+FLAG_LENGTH, DST_ADDRESS_LENGTH);
            System.arraycopy(this.nextHopAddress, 0, buffer, NODE_ADDRESS_LENGTH+FLAG_LENGTH+DST_ADDRESS_LENGTH, NEXT_HOP_LENGTH);
            System.arraycopy(this.hopCounter, 0, buffer, NODE_ADDRESS_LENGTH+FLAG_LENGTH+DST_ADDRESS_LENGTH+NEXT_HOP_LENGTH, HOP_COUNT_LENGTH);

            packet= new DatagramPacket(buffer, buffer.length);
        }
        catch(Exception e) {e.printStackTrace();}
        return packet;
    }


    public int getNodeAddress(){
        return ByteBuffer.wrap(this.nodeAddress).getInt();
    }

    public int getFlag() {
        return ByteBuffer.wrap(this.flag).getInt();
    }

    public int getDestinationAddress() {
        return ByteBuffer.wrap(this.dstAddress).getInt();
    }

    public int getNextHopAddress() {
        return ByteBuffer.wrap(this.nextHopAddress).getInt();
    }

    public int gethopCounter(){
        return ByteBuffer.wrap(this.hopCounter).getInt();
    }
}
