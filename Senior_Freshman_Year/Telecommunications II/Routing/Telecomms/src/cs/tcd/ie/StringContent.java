package cs.tcd.ie;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class StringContent implements PacketContent {
    String string;
    byte[] destination;
    byte[] source;
    byte[] hopCounter;
    byte[] payload;
    byte[] buffer;

    public StringContent(DatagramPacket packet) {
        payload= null;
        buffer= null;

        buffer= packet.getData();
        payload= new byte[packet.getLength()-HEADER_LENGTH];

        this.destination = new byte[DST_ADDRESS_LENGTH];
        this.source = new byte[SOURCE_ADDRESS_LENGTH];
        this.hopCounter = new byte[HOP_COUNT_LENGTH];

        System.arraycopy(buffer, 0, this.destination, 0, DST_ADDRESS_LENGTH);
        System.arraycopy(buffer, DST_ADDRESS_LENGTH, this.source, 0, SOURCE_ADDRESS_LENGTH);
        System.arraycopy(buffer, (DST_ADDRESS_LENGTH+SOURCE_ADDRESS_LENGTH), this.hopCounter, 0, HOP_COUNT_LENGTH);
        System.arraycopy(buffer, HEADER_LENGTH, payload, 0, packet.getLength()-HEADER_LENGTH);

        this.string = new String(payload);
    }

    public StringContent(String string) {
        this.string = string;
    }

    public String toString() {
        return this.string;
    }

    public int getDestination(){
        return ByteBuffer.wrap(this.destination).getInt();
    }

    public int getSource(){
        return ByteBuffer.wrap(this.source).getInt();
    }

    public int gethopCounter(){
        return ByteBuffer.wrap(this.hopCounter).getInt();
    }

    public void increasehopCounter() {
        int x = this.gethopCounter();
        x++;
        this.hopCounter = ByteBuffer.allocate(4).putInt(x).array();
    }


    public DatagramPacket toDatagramPacket() {
        DatagramPacket packet= null;
        byte[] buffer= null;
        byte[] payload= null;
        byte[] header= null;

        try {
            payload= string.getBytes();
            header= new byte[HEADER_LENGTH];
            buffer= new byte[header.length+payload.length];

            System.arraycopy(payload, 0, buffer, HEADER_LENGTH, payload.length);
            System.arraycopy(this.destination, 0, buffer, 0, this.destination.length);
            System.arraycopy(this.source, 0, buffer, this.source.length, this.source.length);
            System.arraycopy(this.hopCounter, 0, buffer, (this.source.length+this.destination.length), this.hopCounter.length);

            packet= new DatagramPacket(buffer, buffer.length);
        }
        catch(Exception e) {e.printStackTrace();}
        return packet;
    }
}
