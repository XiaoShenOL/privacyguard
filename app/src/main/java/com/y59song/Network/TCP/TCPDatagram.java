package com.y59song.Network.TCP;

import android.util.Log;
import com.y59song.Network.IP.IPPayLoad;

import java.util.Arrays;

/**
 * Created by frank on 2014-03-26.
 */
public class TCPDatagram extends IPPayLoad {
  private static final String TAG = "TCPDatagram";
  private static final boolean DEBUG = false;

  public static TCPDatagram create(byte[] data) {
    TCPHeader header = new TCPHeader(data);
    return new TCPDatagram(header, Arrays.copyOfRange(data, header.offset(), data.length));
  }

  public TCPDatagram(TCPHeader header, byte[] data) {
    this.header = header;
    this.data = data;
    if(DEBUG) debugInfo();
  }

  public TCPDatagram(TCPHeader header, byte[] data, int start, int end) {
    this.header = header;
    this.data = Arrays.copyOfRange(data, start, end);
    if(DEBUG) debugInfo();
  }

  public void debugInfo() {
    //if(header.getDstPort() == 80 || header.getSrcPort() == 80)
      Log.d(TAG, "Flag : " + (((TCPHeader)header).getFlag() & 0xFF) + " SrcPort : "
        + header.getSrcPort() + " DstPort : " + header.getDstPort() + " Seq : " + ((TCPHeader)header).getSeq_num()
        + " Ack : " + ((TCPHeader)header).getAck_num()
        + " Data Length : " + dataLength());
  }

  @Override
  public int virtualLength() {
    byte flag = ((TCPHeader)header).getFlag();
    if((flag & (TCPHeader.SYN | TCPHeader.FIN)) != 0) return 1;
    else return this.dataLength();
  }
}
