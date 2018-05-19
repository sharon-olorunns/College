package cs.tcd.ie;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class ControllerPacket implements PacketContent {

    byte[] source;
    byte[] connectionCount;
    byte[] flag;
    int[] connectionAddresses;


    public ControllerPacket(DatagramPacket packet) {
        byte[] buffer= null;

        this.source = new byte[SOURCE_ADDRESS_LENGTH];
        this.flag = new byte[FLAG_LENGTH];
        this.connectionCount = new byte[CONNECTION_COUNT_LENGTH];
        buffer= packet.getData();

        //ControllerInformPacket Protocol = [FLAG] | [SOURCE_ADDRESS] | [CONNECTION_COUNT (N)] | [CONNECTION_ADDRESS_1] | ... | [CONNECTION_N]

        System.arraycopy(buffer, 0, this.flag, 0, FLAG_LENGTH);
        //Extract source address
        System.arraycopy(buffer, FLAG_LENGTH, this.source, 0, SOURCE_ADDRESS_LENGTH);
        //Extract connection count
        System.arraycopy(buffer, SOURCE_ADDRESS_LENGTH+FLAG_LENGTH, this.connectionCount, 0, CONNECTION_COUNT_LENGTH);
        this.connectionAddresses = new int[ByteBuffer.wrap(this.connectionCount).getInt()];

        //Extract connections
        int connectionAddress;
        byte[] tmp = new byte[CONNECTION_ADDRESS_LENGTH];
        int index = SOURCE_ADDRESS_LENGTH+CONNECTION_COUNT_LENGTH+FLAG_LENGTH;

        for(int i=0; i<ByteBuffer.wrap(this.connectionCount).getInt();i++) {
            System.arraycopy(buffer, index, tmp, 0, CONNECTION_ADDRESS_LENGTH);
            index += CONNECTION_ADDRESS_LENGTH;
            connectionAddress = ByteBuffer.wrap(tmp).getInt();
            this.connectionAddresses[i] = connectionAddress;
        }
    }

    public ControllerPacket(int source, int[] connections) {

        this.source = new byte[SOURCE_ADDRESS_LENGTH];
        this.flag = new byte[FLAG_LENGTH];
        //Flag = 2 for ControllerInformPacket
        this.flag = ByteBuffer.allocate(PacketContent.FLAG_LENGTH).putInt(2).array();

        this.connectionCount = new byte[CONNECTION_COUNT_LENGTH];
        this.connectionAddresses = new int[connections.length];

        //ControllerInformPacket Protocol = [FLAG] | [SOURCE_ADDRESS] | [CONNECTION_COUNT (N)] | [CONNECTION_ADDRESS_1] | ... | [CONNECTION_N]
        this.flag = ByteBuffer.allocate(PacketContent.FLAG_LENGTH).putInt(2).array();
        this.source = ByteBuffer.allocate(PacketContent.SOURCE_ADDRESS_LENGTH).putInt(source).array();
        this.connectionCount = ByteBuffer.allocate(PacketContent.CONNECTION_COUNT_LENGTH).putInt(connections.length).array();
        this.connectionAddresses = connections;
    }

    public DatagramPacket toDatagramPacket() {
        DatagramPacket packet= null;
        byte[] buffer= null;
        int count = ByteBuffer.wrap(this.connectionCount).getInt();
        try {
            buffer= new byte[FLAG_LENGTH + SOURCE_ADDRESS_LENGTH + CONNECTION_COUNT_LENGTH + (count*CONNECTION_ADDRESS_LENGTH)];

            System.arraycopy(this.flag, 0, buffer, 0, FLAG_LENGTH);
            System.arraycopy(this.source, 0, buffer, FLAG_LENGTH, SOURCE_ADDRESS_LENGTH);
            System.arraycopy(connectionCount, 0, buffer, SOURCE_ADDRESS_LENGTH+FLAG_LENGTH, CONNECTION_COUNT_LENGTH);

            //Insert connections into buffer
            int connectionAddress;
            byte[] tmp = new byte[CONNECTION_ADDRESS_LENGTH];
            int index = FLAG_LENGTH+SOURCE_ADDRESS_LENGTH+CONNECTION_COUNT_LENGTH;

            System.out.println("Connections= " + ByteBuffer.wrap(this.connectionCount).getInt());
            for(int i=0; i<count;i++) {
                connectionAddress = this.connectionAddresses[i];
                tmp = ByteBuffer.allocate(PacketContent.CONNECTION_ADDRESS_LENGTH).putInt(connectionAddress).array();
                System.arraycopy(tmp, 0, buffer, index, CONNECTION_ADDRESS_LENGTH);
                index += CONNECTION_ADDRESS_LENGTH;
            }

            packet= new DatagramPacket(buffer, buffer.length);
        }
        catch(Exception e) {e.printStackTrace();}
        return packet;
    }


    public int[] getConnectionAddr(){
        return this.connectionAddresses;
    }

    public int getSource(){
        return ByteBuffer.wrap(this.source).getInt();
    }

    public int getFlag() {
        return ByteBuffer.wrap(this.flag).getInt();
    }

    public int getConnectionCount(){
        return ByteBuffer.wrap(this.connectionCount).getInt();
    }

}