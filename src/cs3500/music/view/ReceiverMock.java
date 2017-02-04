package cs3500.music.view;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import jdk.nashorn.internal.objects.annotations.Getter;

/**
 * a mock receiver class
 */
public class ReceiverMock {

  private StringBuilder sb;
  private int notesPlayed;

  public ReceiverMock(){
    this.notesPlayed = 0;
    this.sb = new StringBuilder();
  }

  // mock send
  public void send (ShortMessage m, long timeStamp) {
    notesPlayed++;
    //System.out.println(sb);
    String status = "Note turned off";
    if (Integer.valueOf(m.getCommand()) == 144){
      status = "Note played";
    }
    sb.append(status+": " + m.getData1()+"\n");
  }

  public void close() {
    sb.append("Fin\n");
  }

  @Getter
  public StringBuilder getStringBuilder(){
    return this.sb;
  }

  @Getter
  public int getNotesPlayed() {
    return this.notesPlayed;
  }
}
